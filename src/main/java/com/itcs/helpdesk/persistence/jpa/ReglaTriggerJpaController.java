/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Condicion;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import com.itcs.helpdesk.persistence.jpa.exceptions.IllegalOrphanException;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class ReglaTriggerJpaController implements Serializable {

    public ReglaTriggerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ReglaTrigger reglaTrigger) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (reglaTrigger.getCondicionList() == null) {
            reglaTrigger.setCondicionList(new ArrayList<Condicion>());
        }
        if (reglaTrigger.getAccionList() == null) {
            reglaTrigger.setAccionList(new ArrayList<Accion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
//            Area idArea = reglaTrigger.getIdArea();
//            if (idArea != null) {
//                idArea = em.getReference(idArea.getClass(), idArea.getIdArea());
//                reglaTrigger.setIdArea(idArea);
//            }
            List<Condicion> attachedCondicionList = new ArrayList<Condicion>();
            for (Condicion condicionListCondicionToAttach : reglaTrigger.getCondicionList()) {
                if (condicionListCondicionToAttach.getIdCondicion() != null) {
                    condicionListCondicionToAttach = em.getReference(condicionListCondicionToAttach.getClass(), condicionListCondicionToAttach.getIdCondicion());
                } else {
                    em.persist(condicionListCondicionToAttach);
                }
                attachedCondicionList.add(condicionListCondicionToAttach);
            }
            reglaTrigger.setCondicionList(attachedCondicionList);
            List<Accion> attachedAccionList = new ArrayList<Accion>();
            for (Accion accionListAccionToAttach : reglaTrigger.getAccionList()) {
                if (accionListAccionToAttach.getIdAccion() != null) {
                    accionListAccionToAttach = em.getReference(accionListAccionToAttach.getClass(), accionListAccionToAttach.getIdAccion());
                } else {
                    em.persist(accionListAccionToAttach);
                }
                attachedAccionList.add(accionListAccionToAttach);
            }
            reglaTrigger.setAccionList(attachedAccionList);
            em.persist(reglaTrigger);
//            if (idArea != null) {
//                idArea.getReglaTriggerList().add(reglaTrigger);
//                idArea = em.merge(idArea);
//            }
////            for (Condicion condicionListCondicion : reglaTrigger.getCondicionList()) {
////                ReglaTrigger oldIdTriggerOfCondicionListCondicion = condicionListCondicion.getIdTrigger();
////                condicionListCondicion.setIdTrigger(reglaTrigger);
////                condicionListCondicion = em.merge(condicionListCondicion);
////                if (oldIdTriggerOfCondicionListCondicion != null) {
////                    oldIdTriggerOfCondicionListCondicion.getCondicionList().remove(condicionListCondicion);
////                    oldIdTriggerOfCondicionListCondicion = em.merge(oldIdTriggerOfCondicionListCondicion);
////                }
////            }
////            for (Accion accionListAccion : reglaTrigger.getAccionList()) {
////                ReglaTrigger oldIdTriggerOfAccionListAccion = accionListAccion.getIdTrigger();
////                accionListAccion.setIdTrigger(reglaTrigger);
////                accionListAccion = em.merge(accionListAccion);
////                if (oldIdTriggerOfAccionListAccion != null) {
////                    oldIdTriggerOfAccionListAccion.getAccionList().remove(accionListAccion);
////                    oldIdTriggerOfAccionListAccion = em.merge(oldIdTriggerOfAccionListAccion);
////                }
////            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findReglaTrigger(reglaTrigger.getIdTrigger()) != null) {
                throw new PreexistingEntityException("ReglaTrigger " + reglaTrigger + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ReglaTrigger reglaTrigger) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ReglaTrigger persistentReglaTrigger = em.find(ReglaTrigger.class, reglaTrigger.getIdTrigger());
//            Area idAreaOld = persistentReglaTrigger.getIdArea();
//            Area idAreaNew = reglaTrigger.getIdArea();
            List<Condicion> condicionListOld = persistentReglaTrigger.getCondicionList();
            List<Condicion> condicionListNew = reglaTrigger.getCondicionList();
            List<Accion> accionListOld = persistentReglaTrigger.getAccionList();
            List<Accion> accionListNew = reglaTrigger.getAccionList();
            List<String> illegalOrphanMessages = null;
            for (Condicion condicionListOldCondicion : condicionListOld) {
                if (!condicionListNew.contains(condicionListOldCondicion)) {
                    em.remove(condicionListOldCondicion);
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Condicion " + condicionListOldCondicion + " since its idTrigger field is not nullable.");
                }
            }
            for (Accion accionListOldAccion : accionListOld) {
                if (!accionListNew.contains(accionListOldAccion)) {
                    em.remove(accionListOldAccion);
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Accion " + accionListOldAccion + " since its idTrigger field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
//            if (idAreaNew != null) {
//                idAreaNew = em.getReference(idAreaNew.getClass(), idAreaNew.getIdArea());
//                reglaTrigger.setIdArea(idAreaNew);
//            }
            List<Condicion> attachedCondicionListNew = new ArrayList<Condicion>();
            for (Condicion condicionListNewCondicionToAttach : condicionListNew) {
                if (condicionListNewCondicionToAttach.getIdCondicion() == null) {
                    em.persist(condicionListNewCondicionToAttach);
                }
//                else {
//                    condicionListNewCondicionToAttach = em.getReference(condicionListNewCondicionToAttach.getClass(), condicionListNewCondicionToAttach.getIdCondicion());
//
//                }
                attachedCondicionListNew.add(condicionListNewCondicionToAttach);
            }
            condicionListNew = attachedCondicionListNew;
            reglaTrigger.setCondicionList(condicionListNew);
            List<Accion> attachedAccionListNew = new ArrayList<Accion>();
            for (Accion accionListNewAccionToAttach : accionListNew) {
                if (accionListNewAccionToAttach.getIdAccion() == null) {
                    em.persist(accionListNewAccionToAttach);
                }
                attachedAccionListNew.add(accionListNewAccionToAttach);
            }
            accionListNew = attachedAccionListNew;
            reglaTrigger.setAccionList(accionListNew);
            reglaTrigger = em.merge(reglaTrigger);
//            if (idAreaOld != null && !idAreaOld.equals(idAreaNew)) {
//                idAreaOld.getReglaTriggerList().remove(reglaTrigger);
//                idAreaOld = em.merge(idAreaOld);
//            }
//            if (idAreaNew != null && !idAreaNew.equals(idAreaOld)) {
//                idAreaNew.getReglaTriggerList().add(reglaTrigger);
//                idAreaNew = em.merge(idAreaNew);
//            }
            for (Condicion condicionListNewCondicion : condicionListNew) {
                if (!condicionListOld.contains(condicionListNewCondicion)) {
                    ReglaTrigger oldIdTriggerOfCondicionListNewCondicion = condicionListNewCondicion.getIdTrigger();
                    condicionListNewCondicion.setIdTrigger(reglaTrigger);
                    condicionListNewCondicion = em.merge(condicionListNewCondicion);
                    if (oldIdTriggerOfCondicionListNewCondicion != null && !oldIdTriggerOfCondicionListNewCondicion.equals(reglaTrigger)) {
                        oldIdTriggerOfCondicionListNewCondicion.getCondicionList().remove(condicionListNewCondicion);
                        oldIdTriggerOfCondicionListNewCondicion = em.merge(oldIdTriggerOfCondicionListNewCondicion);
                    }
                }
            }
            for (Accion accionListNewAccion : accionListNew) {
                if (!accionListOld.contains(accionListNewAccion)) {
                    ReglaTrigger oldIdTriggerOfAccionListNewAccion = accionListNewAccion.getIdTrigger();
                    accionListNewAccion.setIdTrigger(reglaTrigger);
                    accionListNewAccion = em.merge(accionListNewAccion);
                    if (oldIdTriggerOfAccionListNewAccion != null && !oldIdTriggerOfAccionListNewAccion.equals(reglaTrigger)) {
                        oldIdTriggerOfAccionListNewAccion.getAccionList().remove(accionListNewAccion);
                        oldIdTriggerOfAccionListNewAccion = em.merge(oldIdTriggerOfAccionListNewAccion);
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
                String id = reglaTrigger.getIdTrigger();
                if (findReglaTrigger(id) == null) {
                    throw new NonexistentEntityException("The reglaTrigger with id " + id + " no longer exists.");
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
            ReglaTrigger reglaTrigger;
            try {
                reglaTrigger = em.getReference(ReglaTrigger.class, id);
                reglaTrigger.getIdTrigger();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reglaTrigger with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Condicion> condicionListOrphanCheck = reglaTrigger.getCondicionList();
            for (Condicion condicionListOrphanCheckCondicion : condicionListOrphanCheck) {
                em.remove(condicionListOrphanCheckCondicion);
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This ReglaTrigger (" + reglaTrigger + ") cannot be destroyed since the Condicion " + condicionListOrphanCheckCondicion + " in its condicionList field has a non-nullable idTrigger field.");
            }
            List<Accion> accionListOrphanCheck = reglaTrigger.getAccionList();
            for (Accion accionListOrphanCheckAccion : accionListOrphanCheck) {
                em.remove(accionListOrphanCheckAccion);
//                if (illegalOrphanMessages == null) {
//                    illegalOrphanMessages = new ArrayList<String>();
//                }
//                illegalOrphanMessages.add("This ReglaTrigger (" + reglaTrigger + ") cannot be destroyed since the Accion " + accionListOrphanCheckAccion + " in its accionList field has a non-nullable idTrigger field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
//            Area idArea = reglaTrigger.getIdArea();
//            if (idArea != null) {
//                idArea.getReglaTriggerList().remove(reglaTrigger);
//                idArea = em.merge(idArea);
//            }
            em.remove(reglaTrigger);
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

    public List<ReglaTrigger> findReglaTriggerEntities() {
        return findReglaTriggerEntities(true, -1, -1);
    }

    public List<ReglaTrigger> findReglaTriggerEntities(int maxResults, int firstResult) {
        return findReglaTriggerEntities(false, maxResults, firstResult);
    }

    private List<ReglaTrigger> findReglaTriggerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ReglaTrigger.class));
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

    public ReglaTrigger findReglaTrigger(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ReglaTrigger.class, id);
        } finally {
            em.close();
        }
    }

    public int getReglaTriggerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ReglaTrigger> rt = cq.from(ReglaTrigger.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }


    private Predicate createSearchExpression(Root<ReglaTrigger> root, CriteriaBuilder criteriaBuilder, String searchPattern) {
        Expression<String> expresionNombre = root.get("idTrigger");
        Expression<String> expresionDescripcion = root.get("desccripcion");
        Expression<String> expresionCampo = root.joinList("condicionList", JoinType.LEFT).get("idCampo");
        Expression<String> expresionCondicionValor = root.joinList("condicionList", JoinType.LEFT).get("valor");
        Expression<String> expresionAccionParams = root.joinList("accionList", JoinType.LEFT).get("parametros");
        Expression<String> expresionAccionNombre = root.joinList("accionList", JoinType.LEFT).get("idNombreAccion").get("idNombreAccion");

        Predicate predicate = criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.upper(expresionNombre), "%" +searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionDescripcion), "%" +searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionCampo), "%" +searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionCondicionValor), "%" +searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionAccionParams), "%" +searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionAccionNombre), "%" + searchPattern.toUpperCase() + "%"));
        return predicate;
    }

    public Long countSearchEntities(String searchPattern) throws ClassNotFoundException {
        EntityManager em = getEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", javax.persistence.CacheRetrieveMode.USE);

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            Root<ReglaTrigger> root = criteriaQuery.from(ReglaTrigger.class);

            Predicate predicate = createSearchExpression(root, criteriaBuilder, searchPattern);

            if (predicate != null) {
                criteriaQuery.select(criteriaBuilder.count(root)).where(predicate).distinct(true);
            } else {
                criteriaQuery.select(criteriaBuilder.count(root));
            }
            Query q = em.createQuery(criteriaQuery);
            q.setHint("eclipselink.query-results-cache", true);
            return ((Long) q.getSingleResult());
        } catch (Exception e) {
            Logger.getLogger(ReglaTriggerJpaController.class.getName()).log(Level.SEVERE, "countSearchEntities " + searchPattern, e);
            return 0L;
        } finally {
            em.close();
        }
    }

    public List<ReglaTrigger> searchEntities(String searchPattern, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            Root<ReglaTrigger> root = criteriaQuery.from(ReglaTrigger.class);

            Predicate predicate = createSearchExpression(root, criteriaBuilder, searchPattern);

            criteriaQuery.where(predicate).distinct(true);
            
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("orden")));

            Query q = em.createQuery(criteriaQuery);
            q.setHint("eclipselink.query-results-cache", true);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }

    }
}
