/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Recinto;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jorge
 */
public class RecintoJpaController implements Serializable {

    public RecintoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recinto recinto) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx.begin();
           
            em.persist(recinto);
          
            utx.commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recinto recinto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx.begin();
         
            recinto = em.merge(recinto);
           
            utx.commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = recinto.getIdRecinto();
                if (null == em.find(Recinto.class, id)) {
                    throw new NonexistentEntityException("The recinto with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            utx.begin();
            Recinto recinto;
            try {
                recinto = em.getReference(Recinto.class, id);
                recinto.getIdRecinto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recinto with id " + id + " no longer exists.", enfe);
            }
           
            em.remove(recinto);
            utx.commit();
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recinto> findRecintoEntities() {
        return findRecintoEntities(true, -1, -1);
    }

    public List<Recinto> findRecintoEntities(int maxResults, int firstResult) {
        return findRecintoEntities(false, maxResults, firstResult);
    }

    private List<Recinto> findRecintoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recinto.class));
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

    public int getRecintoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recinto> rt = cq.from(Recinto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
