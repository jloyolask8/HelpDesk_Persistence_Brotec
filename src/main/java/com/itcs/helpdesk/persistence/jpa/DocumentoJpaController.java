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
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Documento;
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
public class DocumentoJpaController implements Serializable {

    public DocumentoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Documento documento) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (documento.getCasoList() == null) {
            documento.setCasoList(new ArrayList<Caso>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario createdBy = documento.getCreatedBy();
            if (createdBy != null) {
                createdBy = em.getReference(createdBy.getClass(), createdBy.getIdUsuario());
                documento.setCreatedBy(createdBy);
            }
            List<Caso> attachedCasoList = new ArrayList<Caso>();
            for (Caso casoListCasoToAttach : documento.getCasoList()) {
                casoListCasoToAttach = em.getReference(casoListCasoToAttach.getClass(), casoListCasoToAttach.getIdCaso());
                attachedCasoList.add(casoListCasoToAttach);
            }
            documento.setCasoList(attachedCasoList);
            em.persist(documento);
            if (createdBy != null) {
                createdBy.getDocumentoList().add(documento);
                createdBy = em.merge(createdBy);
            }
            for (Caso casoListCaso : documento.getCasoList()) {
                casoListCaso.getDocumentoList().add(documento);
                casoListCaso = em.merge(casoListCaso);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findDocumento(documento.getIdDocumento()) != null) {
                throw new PreexistingEntityException("Documento " + documento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Documento documento) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Documento persistentDocumento = em.find(Documento.class, documento.getIdDocumento());
            Usuario createdByOld = persistentDocumento.getCreatedBy();
            Usuario createdByNew = documento.getCreatedBy();
            List<Caso> casoListOld = persistentDocumento.getCasoList();
            List<Caso> casoListNew = documento.getCasoList();
            if (createdByNew != null) {
                createdByNew = em.getReference(createdByNew.getClass(), createdByNew.getIdUsuario());
                documento.setCreatedBy(createdByNew);
            }
            List<Caso> attachedCasoListNew = new ArrayList<Caso>();
            for (Caso casoListNewCasoToAttach : casoListNew) {
                casoListNewCasoToAttach = em.getReference(casoListNewCasoToAttach.getClass(), casoListNewCasoToAttach.getIdCaso());
                attachedCasoListNew.add(casoListNewCasoToAttach);
            }
            casoListNew = attachedCasoListNew;
            documento.setCasoList(casoListNew);
            documento = em.merge(documento);
            if (createdByOld != null && !createdByOld.equals(createdByNew)) {
                createdByOld.getDocumentoList().remove(documento);
                createdByOld = em.merge(createdByOld);
            }
            if (createdByNew != null && !createdByNew.equals(createdByOld)) {
                createdByNew.getDocumentoList().add(documento);
                createdByNew = em.merge(createdByNew);
            }
            for (Caso casoListOldCaso : casoListOld) {
                if (!casoListNew.contains(casoListOldCaso)) {
                    casoListOldCaso.getDocumentoList().remove(documento);
                    casoListOldCaso = em.merge(casoListOldCaso);
                }
            }
            for (Caso casoListNewCaso : casoListNew) {
                if (!casoListOld.contains(casoListNewCaso)) {
                    casoListNewCaso.getDocumentoList().add(documento);
                    casoListNewCaso = em.merge(casoListNewCaso);
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
                Integer id = documento.getIdDocumento();
                if (findDocumento(id) == null) {
                    throw new NonexistentEntityException("The documento with id " + id + " no longer exists.");
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
            Documento documento;
            try {
                documento = em.getReference(Documento.class, id);
                documento.getIdDocumento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The documento with id " + id + " no longer exists.", enfe);
            }
            Usuario createdBy = documento.getCreatedBy();
            if (createdBy != null) {
                createdBy.getDocumentoList().remove(documento);
                createdBy = em.merge(createdBy);
            }
            List<Caso> casoList = documento.getCasoList();
            for (Caso casoListCaso : casoList) {
                casoListCaso.getDocumentoList().remove(documento);
                casoListCaso = em.merge(casoListCaso);
            }
            em.remove(documento);
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

    public List<Documento> findDocumentoEntities() {
        return findDocumentoEntities(true, -1, -1);
    }

    public List<Documento> findDocumentoEntities(int maxResults, int firstResult) {
        return findDocumentoEntities(false, maxResults, firstResult);
    }

    private List<Documento> findDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Documento.class));
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

    public Documento findDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Documento.class, id);
        } finally {
            em.close();
        }
    }

    public int getDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Documento> rt = cq.from(Documento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
