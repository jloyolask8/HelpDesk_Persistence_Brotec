/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.AuditLog;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import com.itcs.helpdesk.persistence.utils.ConstraintViolationExceptionHelper;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class AuditLogJpaController implements Serializable {

    public AuditLogJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AuditLog auditLog) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(auditLog);

            utx.commit();
        }
        catch (Exception ex)
        {
           ConstraintViolationExceptionHelper.handleError(ex);
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAuditLog(auditLog.getIdLog()) != null) {
                throw new PreexistingEntityException("AuditLog " + auditLog + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AuditLog auditLog) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            auditLog = em.merge(auditLog);
            utx.commit();
        } catch (Exception ex) {
             ConstraintViolationExceptionHelper.handleError(ex);
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = auditLog.getIdLog();
                if (findAuditLog(id) == null) {
                    throw new NonexistentEntityException("The auditLog with id " + id + " no longer exists.");
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
            AuditLog auditLog;
            try {
                auditLog = em.getReference(AuditLog.class, id);
                auditLog.getIdLog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The auditLog with id " + id + " no longer exists.", enfe);
            }
                        
            em.remove(auditLog);
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

    public List<AuditLog> findAuditLogEntities() {
        return findAuditLogEntities(true, -1, -1);
    }

    public List<AuditLog> findAuditLogEntities(int maxResults, int firstResult) {
        return findAuditLogEntities(false, maxResults, firstResult);
    }

    private List<AuditLog> findAuditLogEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AuditLog.class));
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

    public AuditLog findAuditLog(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AuditLog.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuditLogCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AuditLog> rt = cq.from(AuditLog.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
