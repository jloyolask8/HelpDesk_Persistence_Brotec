/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class TipoAlertaJpaController implements Serializable {

    public TipoAlertaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoAlerta tipoAlerta) throws PreexistingEntityException, RollbackFailureException, Exception {
        
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            
            em.persist(tipoAlerta);
            
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findTipoAlerta(tipoAlerta.getIdalerta()) != null) {
                throw new PreexistingEntityException("TipoAlerta " + tipoAlerta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoAlerta tipoAlerta) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            
            tipoAlerta = em.merge(tipoAlerta);
            
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoAlerta.getIdalerta();
                if (findTipoAlerta(id) == null) {
                    throw new NonexistentEntityException("The tipoAlerta with id " + id + " no longer exists.");
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
            TipoAlerta tipoAlerta;
            try {
                tipoAlerta = em.getReference(TipoAlerta.class, id);
                tipoAlerta.getIdalerta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoAlerta with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoAlerta);
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

    public List<TipoAlerta> findTipoAlertaEntities() {
        return findTipoAlertaEntities(true, -1, -1);
    }

    public List<TipoAlerta> findTipoAlertaEntities(int maxResults, int firstResult) {
        return findTipoAlertaEntities(false, maxResults, firstResult);
    }

    private List<TipoAlerta> findTipoAlertaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoAlerta.class));
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

    public TipoAlerta findTipoAlerta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoAlerta.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoAlertaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoAlerta> rt = cq.from(TipoAlerta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
