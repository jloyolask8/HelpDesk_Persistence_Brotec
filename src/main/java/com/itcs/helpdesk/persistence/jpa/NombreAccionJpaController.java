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
import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.NombreAccion;
import com.itcs.helpdesk.persistence.jpa.exceptions.IllegalOrphanException;
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
public class NombreAccionJpaController implements Serializable {

    public NombreAccionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(NombreAccion nombreAccion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (nombreAccion.getAccionList() == null) {
            nombreAccion.setAccionList(new ArrayList<Accion>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Accion> attachedAccionList = new ArrayList<Accion>();
            for (Accion accionListAccionToAttach : nombreAccion.getAccionList()) {
                accionListAccionToAttach = em.getReference(accionListAccionToAttach.getClass(), accionListAccionToAttach.getIdAccion());
                attachedAccionList.add(accionListAccionToAttach);
            }
            nombreAccion.setAccionList(attachedAccionList);
            em.persist(nombreAccion);
            for (Accion accionListAccion : nombreAccion.getAccionList()) {
                NombreAccion oldIdNombreAccionOfAccionListAccion = accionListAccion.getIdNombreAccion();
                accionListAccion.setIdNombreAccion(nombreAccion);
                accionListAccion = em.merge(accionListAccion);
                if (oldIdNombreAccionOfAccionListAccion != null) {
                    oldIdNombreAccionOfAccionListAccion.getAccionList().remove(accionListAccion);
                    oldIdNombreAccionOfAccionListAccion = em.merge(oldIdNombreAccionOfAccionListAccion);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findNombreAccion(nombreAccion.getIdNombreAccion()) != null) {
                throw new PreexistingEntityException("NombreAccion " + nombreAccion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(NombreAccion nombreAccion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            NombreAccion persistentNombreAccion = em.find(NombreAccion.class, nombreAccion.getIdNombreAccion());
            List<Accion> accionListOld = persistentNombreAccion.getAccionList();
            List<Accion> accionListNew = nombreAccion.getAccionList();
            List<String> illegalOrphanMessages = null;
            for (Accion accionListOldAccion : accionListOld) {
                if (!accionListNew.contains(accionListOldAccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Accion " + accionListOldAccion + " since its idNombreAccion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Accion> attachedAccionListNew = new ArrayList<Accion>();
            for (Accion accionListNewAccionToAttach : accionListNew) {
                accionListNewAccionToAttach = em.getReference(accionListNewAccionToAttach.getClass(), accionListNewAccionToAttach.getIdAccion());
                attachedAccionListNew.add(accionListNewAccionToAttach);
            }
            accionListNew = attachedAccionListNew;
            nombreAccion.setAccionList(accionListNew);
            nombreAccion = em.merge(nombreAccion);
            for (Accion accionListNewAccion : accionListNew) {
                if (!accionListOld.contains(accionListNewAccion)) {
                    NombreAccion oldIdNombreAccionOfAccionListNewAccion = accionListNewAccion.getIdNombreAccion();
                    accionListNewAccion.setIdNombreAccion(nombreAccion);
                    accionListNewAccion = em.merge(accionListNewAccion);
                    if (oldIdNombreAccionOfAccionListNewAccion != null && !oldIdNombreAccionOfAccionListNewAccion.equals(nombreAccion)) {
                        oldIdNombreAccionOfAccionListNewAccion.getAccionList().remove(accionListNewAccion);
                        oldIdNombreAccionOfAccionListNewAccion = em.merge(oldIdNombreAccionOfAccionListNewAccion);
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
                String id = nombreAccion.getIdNombreAccion();
                if (findNombreAccion(id) == null) {
                    throw new NonexistentEntityException("The nombreAccion with id " + id + " no longer exists.");
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
            NombreAccion nombreAccion;
            try {
                nombreAccion = em.getReference(NombreAccion.class, id);
                nombreAccion.getIdNombreAccion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nombreAccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Accion> accionListOrphanCheck = nombreAccion.getAccionList();
            for (Accion accionListOrphanCheckAccion : accionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This NombreAccion (" + nombreAccion + ") cannot be destroyed since the Accion " + accionListOrphanCheckAccion + " in its accionList field has a non-nullable idNombreAccion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(nombreAccion);
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

    public List<NombreAccion> findNombreAccionEntities() {
        return findNombreAccionEntities(true, -1, -1);
    }

    public List<NombreAccion> findNombreAccionEntities(int maxResults, int firstResult) {
        return findNombreAccionEntities(false, maxResults, firstResult);
    }

    private List<NombreAccion> findNombreAccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(NombreAccion.class));
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

    public NombreAccion findNombreAccion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(NombreAccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getNombreAccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<NombreAccion> rt = cq.from(NombreAccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
