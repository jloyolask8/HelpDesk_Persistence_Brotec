package com.itcs.helpdesk.persistence.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itcs.helpdesk.persistence.entities.Item;
import com.itcs.helpdesk.persistence.entities.Area;
import java.util.ArrayList;
import java.util.List;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

/**
 *
 * @author jonathan
 */
public class ItemJpaController implements Serializable {

    public ItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) throws PreexistingEntityException, RollbackFailureException, Exception {

        if (item.getItemList() == null) {
            item.setItemList(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item idItemPadre = item.getIdItemPadre();
            if (idItemPadre != null) {
                idItemPadre = em.getReference(idItemPadre.getClass(), idItemPadre.getIdItem());
                item.setIdItemPadre(idItemPadre);
            }
            Area idArea = item.getIdArea();
            if (idArea != null) {
                idArea = em.getReference(idArea.getClass(), idArea.getIdArea());
                item.setIdArea(idArea);
            }
            List<Item> attachedItemList = new ArrayList<Item>();
            for (Item itemListItemToAttach : item.getItemList()) {
                itemListItemToAttach = em.getReference(itemListItemToAttach.getClass(), itemListItemToAttach.getIdItem());
                attachedItemList.add(itemListItemToAttach);
            }
            item.setItemList(attachedItemList);
            em.persist(item);
            if (idItemPadre != null) {
                idItemPadre.getItemList().add(item);
                idItemPadre = em.merge(idItemPadre);
            }
            if (idArea != null) {
                idArea.getItemList().add(item);
                idArea = em.merge(idArea);
            }

            for (Item itemListItem : item.getItemList()) {
                Item oldIdItemPadreOfItemListItem = itemListItem.getIdItemPadre();
                itemListItem.setIdItemPadre(item);
                itemListItem = em.merge(itemListItem);
                if (oldIdItemPadreOfItemListItem != null) {
                    oldIdItemPadreOfItemListItem.getItemList().remove(itemListItem);
                    oldIdItemPadreOfItemListItem = em.merge(oldIdItemPadreOfItemListItem);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            if (ex instanceof ConstraintViolationException) {
                printOutContraintViolation((ConstraintViolationException) ex);
            }

            if (ex.getCause() instanceof ConstraintViolationException) {
                printOutContraintViolation((ConstraintViolationException) (ex.getCause()));
            }
            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (item != null && item.getIdItem() != null) {
                if (findItem(item.getIdItem()) != null) {
                    throw new PreexistingEntityException("Item " + item + " already exists.", ex);
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    private void printOutContraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> set = (ex).getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : set) {
            System.out.println("leafBean class: " + constraintViolation.getLeafBean().getClass());
            Iterator<Path.Node> iter = constraintViolation.getPropertyPath().iterator();
            System.out.println("constraintViolation.getPropertyPath(): ");
            while (iter.hasNext()) {
                System.out.print(iter.next().getName() + "/");
            }
            System.out.println("anotacion: " + constraintViolation.getConstraintDescriptor().getAnnotation().toString() + " value:" + constraintViolation.getInvalidValue());
        }
    }
    
    public void edit(Item item) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item persistentItem = em.find(Item.class, item.getIdItem());
            Item idItemPadreOld = persistentItem.getIdItemPadre();
            Item idItemPadreNew = item.getIdItemPadre();
            Area idAreaOld = persistentItem.getIdArea();
            Area idAreaNew = item.getIdArea();
            List<Item> itemListOld = persistentItem.getItemList();
            List<Item> itemListNew = item.getItemList();
            if (idItemPadreNew != null) {
                idItemPadreNew = em.getReference(idItemPadreNew.getClass(), idItemPadreNew.getIdItem());
                item.setIdItemPadre(idItemPadreNew);
            }
            if (idAreaNew != null) {
                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getIdArea());
                item.setIdArea(idAreaNew);
            }

            List<Item> attachedItemListNew = new ArrayList<Item>();
            for (Item itemListNewItemToAttach : itemListNew) {
                itemListNewItemToAttach = em.getReference(itemListNewItemToAttach.getClass(), itemListNewItemToAttach.getIdItem());
                attachedItemListNew.add(itemListNewItemToAttach);
            }
            itemListNew = attachedItemListNew;
            item.setItemList(itemListNew);
            item = em.merge(item);
            if (idItemPadreOld != null && !idItemPadreOld.equals(idItemPadreNew)) {
                idItemPadreOld.getItemList().remove(item);
                idItemPadreOld = em.merge(idItemPadreOld);
            }
            if (idItemPadreNew != null && !idItemPadreNew.equals(idItemPadreOld)) {
                idItemPadreNew.getItemList().add(item);
                idItemPadreNew = em.merge(idItemPadreNew);
            }
            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
                idAreaOld.getItemList().remove(item);
                idAreaOld = em.merge(idAreaOld);
            }
            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
                idAreaNew.getItemList().add(item);
                idAreaNew = em.merge(idAreaNew);
            }
            for (Item itemListOldItem : itemListOld) {
                if (!itemListNew.contains(itemListOldItem)) {
                    itemListOldItem.setIdItemPadre(null);
                    itemListOldItem = em.merge(itemListOldItem);
                }
            }
            for (Item itemListNewItem : itemListNew) {
                if (!itemListOld.contains(itemListNewItem)) {
                    Item oldIdItemPadreOfItemListNewItem = itemListNewItem.getIdItemPadre();
                    itemListNewItem.setIdItemPadre(item);
                    itemListNewItem = em.merge(itemListNewItem);
                    if (oldIdItemPadreOfItemListNewItem != null && !oldIdItemPadreOfItemListNewItem.equals(item)) {
                        oldIdItemPadreOfItemListNewItem.getItemList().remove(itemListNewItem);
                        oldIdItemPadreOfItemListNewItem = em.merge(oldIdItemPadreOfItemListNewItem);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = item.getIdItem();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getIdItem();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            Item idItemPadre = item.getIdItemPadre();
            if (idItemPadre != null) {
                idItemPadre.getItemList().remove(item);
                idItemPadre = em.merge(idItemPadre);
            }
            Area idArea = item.getIdArea();
            if (idArea != null) {
                idArea.getItemList().remove(item);
                idArea = em.merge(idArea);
            }
            List<Item> itemList = item.getItemList();
            for (Item itemListItem : itemList) {
                itemListItem.setIdItemPadre(null);
                itemListItem = em.merge(itemListItem);
            }
            em.remove(item);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
