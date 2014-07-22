/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.Sesiones;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class SesionesJpaController implements Serializable {

    public SesionesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sesiones sesiones) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(sesiones);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSesiones(sesiones.getIdSesion()) != null) {
                throw new PreexistingEntityException("Sesiones " + sesiones + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sesiones sesiones) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            sesiones = em.merge(sesiones);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = sesiones.getIdSesion();
                if (findSesiones(id) == null) {
                    throw new NonexistentEntityException("The sesiones with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sesiones sesiones;
            try {
                sesiones = em.getReference(Sesiones.class, id);
                sesiones.getIdSesion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sesiones with id " + id + " no longer exists.", enfe);
            }
            em.remove(sesiones);
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

    public List<Sesiones> findSesionesEntities() {
        return findSesionesEntities(true, -1, -1);
    }

    public List<Sesiones> findSesionesEntities(int maxResults, int firstResult) {
        return findSesionesEntities(false, maxResults, firstResult);
    }

    private List<Sesiones> findSesionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sesiones.class));
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

    public Sesiones findSesiones(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sesiones.class, id);
        } finally {
            em.close();
        }
    }

    public int getSesionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sesiones> rt = cq.from(Sesiones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
