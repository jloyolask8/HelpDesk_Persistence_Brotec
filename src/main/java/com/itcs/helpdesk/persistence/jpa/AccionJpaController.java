/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.Accion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import com.itcs.helpdesk.persistence.entities.TipoAccion;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class AccionJpaController implements Serializable {

    public AccionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Accion accion) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ReglaTrigger idTrigger = accion.getIdTrigger();
            if (idTrigger != null) {
                idTrigger = em.getReference(idTrigger.getClass(), idTrigger.getIdTrigger());
                accion.setIdTrigger(idTrigger);
            }
            TipoAccion idNombreAccion = accion.getIdNombreAccion();
            if (idNombreAccion != null) {
                idNombreAccion = em.getReference(idNombreAccion.getClass(), idNombreAccion.getIdNombreAccion());
                accion.setIdNombreAccion(idNombreAccion);
            }
            em.persist(accion);
            if (idTrigger != null) {
                idTrigger.getAccionList().add(accion);
                idTrigger = em.merge(idTrigger);
            }
            if (idNombreAccion != null) {
                idNombreAccion.getAccionList().add(accion);
                idNombreAccion = em.merge(idNombreAccion);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAccion(accion.getIdAccion()) != null) {
                throw new PreexistingEntityException("Accion " + accion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Accion accion) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Accion persistentAccion = em.find(Accion.class, accion.getIdAccion());
            ReglaTrigger idTriggerOld = persistentAccion.getIdTrigger();
            ReglaTrigger idTriggerNew = accion.getIdTrigger();
            TipoAccion idNombreAccionOld = persistentAccion.getIdNombreAccion();
            TipoAccion idNombreAccionNew = accion.getIdNombreAccion();
            if (idTriggerNew != null) {
                idTriggerNew = em.getReference(idTriggerNew.getClass(), idTriggerNew.getIdTrigger());
                accion.setIdTrigger(idTriggerNew);
            }
            if (idNombreAccionNew != null) {
                idNombreAccionNew = em.getReference(idNombreAccionNew.getClass(), idNombreAccionNew.getIdNombreAccion());
                accion.setIdNombreAccion(idNombreAccionNew);
            }
            accion = em.merge(accion);
            if (idTriggerOld != null && !idTriggerOld.equals(idTriggerNew)) {
                idTriggerOld.getAccionList().remove(accion);
                idTriggerOld = em.merge(idTriggerOld);
            }
            if (idTriggerNew != null && !idTriggerNew.equals(idTriggerOld)) {
                idTriggerNew.getAccionList().add(accion);
                idTriggerNew = em.merge(idTriggerNew);
            }
            if (idNombreAccionOld != null && !idNombreAccionOld.equals(idNombreAccionNew)) {
                idNombreAccionOld.getAccionList().remove(accion);
                idNombreAccionOld = em.merge(idNombreAccionOld);
            }
            if (idNombreAccionNew != null && !idNombreAccionNew.equals(idNombreAccionOld)) {
                idNombreAccionNew.getAccionList().add(accion);
                idNombreAccionNew = em.merge(idNombreAccionNew);
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
                Integer id = accion.getIdAccion();
                if (findAccion(id) == null) {
                    throw new NonexistentEntityException("The accion with id " + id + " no longer exists.");
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
            Accion accion;
            try {
                accion = em.getReference(Accion.class, id);
                accion.getIdAccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The accion with id " + id + " no longer exists.", enfe);
            }
            ReglaTrigger idTrigger = accion.getIdTrigger();
            if (idTrigger != null) {
                idTrigger.getAccionList().remove(accion);
                idTrigger = em.merge(idTrigger);
            }
            TipoAccion idNombreAccion = accion.getIdNombreAccion();
            if (idNombreAccion != null) {
                idNombreAccion.getAccionList().remove(accion);
                idNombreAccion = em.merge(idNombreAccion);
            }
            em.remove(accion);
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

    public List<Accion> findAccionEntities() {
        return findAccionEntities(true, -1, -1);
    }

    public List<Accion> findAccionEntities(int maxResults, int firstResult) {
        return findAccionEntities(false, maxResults, firstResult);
    }

    private List<Accion> findAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Accion.class));
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

    public Accion findAccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Accion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Accion> rt = cq.from(Accion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
