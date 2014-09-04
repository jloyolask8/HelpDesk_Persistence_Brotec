/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Grupo;
import com.itcs.helpdesk.persistence.jpa.exceptions.IllegalOrphanException;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class AreaJpaController implements Serializable {

    public AreaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Area area) throws PreexistingEntityException, RollbackFailureException, Exception {
       
        if (area.getGrupoList() == null) {
            area.setGrupoList(new ArrayList<Grupo>());
        }
//        if (area.getReglaTriggerList() == null) {
//            area.setReglaTriggerList(new ArrayList<ReglaTrigger>());
//        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
           
            List<Grupo> attachedGrupoList = new ArrayList<Grupo>();
            for (Grupo grupoListGrupoToAttach : area.getGrupoList()) {
                grupoListGrupoToAttach = em.getReference(grupoListGrupoToAttach.getClass(), grupoListGrupoToAttach.getIdGrupo());
                attachedGrupoList.add(grupoListGrupoToAttach);
            }
            area.setGrupoList(attachedGrupoList);
//            List<ReglaTrigger> attachedReglaTriggerList = new ArrayList<ReglaTrigger>();
//            for (ReglaTrigger reglaTriggerListReglaTriggerToAttach : area.getReglaTriggerList()) {
//                reglaTriggerListReglaTriggerToAttach = em.getReference(reglaTriggerListReglaTriggerToAttach.getClass(), reglaTriggerListReglaTriggerToAttach.getIdTrigger());
//                attachedReglaTriggerList.add(reglaTriggerListReglaTriggerToAttach);
//            }
//            area.setReglaTriggerList(attachedReglaTriggerList);
            em.persist(area);
          
            for (Grupo grupoListGrupo : area.getGrupoList()) {
                Area oldIdAreaOfGrupoListGrupo = grupoListGrupo.getIdArea();
                grupoListGrupo.setIdArea(area);
                grupoListGrupo = em.merge(grupoListGrupo);
                if (oldIdAreaOfGrupoListGrupo != null) {
                    oldIdAreaOfGrupoListGrupo.getGrupoList().remove(grupoListGrupo);
                    oldIdAreaOfGrupoListGrupo = em.merge(oldIdAreaOfGrupoListGrupo);
                }
            }

            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArea(area.getIdArea()) != null) {
                throw new PreexistingEntityException("Area " + area + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Area area) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Area persistentArea = em.find(Area.class, area.getIdArea());
            List<Grupo> grupoListOld = persistentArea.getGrupoList();
            List<Grupo> grupoListNew = area.getGrupoList();
//            List<ReglaTrigger> reglaTriggerListOld = persistentArea.getReglaTriggerList();
//            List<ReglaTrigger> reglaTriggerListNew = area.getReglaTriggerList();
            List<String> illegalOrphanMessages = null;
           
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Grupo " + grupoListOldGrupo + " since its idArea field is not nullable.");
                }
            }
//            for (ReglaTrigger reglaTriggerListOldReglaTrigger : reglaTriggerListOld) {
//                if (!reglaTriggerListNew.contains(reglaTriggerListOldReglaTrigger)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain ReglaTrigger " + reglaTriggerListOldReglaTrigger + " since its idArea field is not nullable.");
//                }
//            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
         
            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getIdGrupo());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            area.setGrupoList(grupoListNew);
//            List<ReglaTrigger> attachedReglaTriggerListNew = new ArrayList<ReglaTrigger>();
//            for (ReglaTrigger reglaTriggerListNewReglaTriggerToAttach : reglaTriggerListNew) {
//                reglaTriggerListNewReglaTriggerToAttach = em.getReference(reglaTriggerListNewReglaTriggerToAttach.getClass(), reglaTriggerListNewReglaTriggerToAttach.getIdTrigger());
//                attachedReglaTriggerListNew.add(reglaTriggerListNewReglaTriggerToAttach);
//            }
//            reglaTriggerListNew = attachedReglaTriggerListNew;
//            area.setReglaTriggerList(reglaTriggerListNew);
            area = em.merge(area);
           
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    Area oldIdAreaOfGrupoListNewGrupo = grupoListNewGrupo.getIdArea();
                    grupoListNewGrupo.setIdArea(area);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                    if (oldIdAreaOfGrupoListNewGrupo != null && !oldIdAreaOfGrupoListNewGrupo.equals(area)) {
                        oldIdAreaOfGrupoListNewGrupo.getGrupoList().remove(grupoListNewGrupo);
                        oldIdAreaOfGrupoListNewGrupo = em.merge(oldIdAreaOfGrupoListNewGrupo);
                    }
                }
            }
//            for (ReglaTrigger reglaTriggerListNewReglaTrigger : reglaTriggerListNew) {
//                if (!reglaTriggerListOld.contains(reglaTriggerListNewReglaTrigger)) {
//                    Area oldIdAreaOfReglaTriggerListNewReglaTrigger = reglaTriggerListNewReglaTrigger.getIdArea();
//                    reglaTriggerListNewReglaTrigger.setIdArea(area);
//                    reglaTriggerListNewReglaTrigger = em.merge(reglaTriggerListNewReglaTrigger);
//                    if (oldIdAreaOfReglaTriggerListNewReglaTrigger != null && !oldIdAreaOfReglaTriggerListNewReglaTrigger.equals(area)) {
//                        oldIdAreaOfReglaTriggerListNewReglaTrigger.getReglaTriggerList().remove(reglaTriggerListNewReglaTrigger);
//                        oldIdAreaOfReglaTriggerListNewReglaTrigger = em.merge(oldIdAreaOfReglaTriggerListNewReglaTrigger);
//                    }
//                }
//            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = area.getIdArea();
                if (findArea(id) == null) {
                    throw new NonexistentEntityException("The area with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Area area;
            try {
                area = em.getReference(Area.class, id);
                area.getIdArea();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The area with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
           
            List<Grupo> grupoListOrphanCheck = area.getGrupoList();
            for (Grupo grupoListOrphanCheckGrupo : grupoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the Grupo " + grupoListOrphanCheckGrupo + " in its grupoList field has a non-nullable idArea field.");
            }
//            List<ReglaTrigger> reglaTriggerListOrphanCheck = area.getReglaTriggerList();
//            for (ReglaTrigger reglaTriggerListOrphanCheckReglaTrigger : reglaTriggerListOrphanCheck) {
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This Area (" + area + ") cannot be destroyed since the ReglaTrigger " + reglaTriggerListOrphanCheckReglaTrigger + " in its reglaTriggerList field has a non-nullable idArea field.");
//            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(area);
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

    public List<Area> findAreaEntities() {
        return findAreaEntities(true, -1, -1);
    }

    public List<Area> findAreaEntities(int maxResults, int firstResult) {
        return findAreaEntities(false, maxResults, firstResult);
    }

    private List<Area> findAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Area.class));
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

    public Area findArea(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Area.class, id);
        } finally {
            em.close();
        }
    }

    public int getAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Area> rt = cq.from(Area.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
