/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.CanalSetting;
import com.itcs.helpdesk.persistence.entities.CanalSettingPK;
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
public class CanalSettingJpaController implements Serializable {

    public CanalSettingJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public void create(CanalSetting canalSetting) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(canalSetting);
            utx.commit();
        } catch (Exception ex) {
            if (findCanalSetting(canalSetting.getCanalSettingPK()) != null) {
                throw new PreexistingEntityException("CanalSetting " + canalSetting + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CanalSetting canalSetting) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            canalSetting = em.merge(canalSetting);
            utx.commit();


        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                CanalSettingPK id = canalSetting.getCanalSettingPK();
                if (findCanalSetting(id) == null) {
                    throw new NonexistentEntityException("The canalSetting with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(CanalSettingPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();

            CanalSetting canalSetting;
            try {
                canalSetting = em.getReference(CanalSetting.class, id);
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The canalSetting with id " + id + " no longer exists.", enfe);
            }
            em.remove(canalSetting);
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

    public List<CanalSetting> findCanalSettingEntities() {
        return findCanalSettingEntities(true, -1, -1);
    }

    public List<CanalSetting> findCanalSettingEntities(int maxResults, int firstResult) {
        return findCanalSettingEntities(false, maxResults, firstResult);
    }

    private List<CanalSetting> findCanalSettingEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CanalSetting.class));
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

    public CanalSetting findCanalSetting(CanalSettingPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CanalSetting.class, id);
        } finally {
            em.close();
        }
    }

    public int getCanalSettingCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CanalSetting> rt = cq.from(CanalSetting.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
