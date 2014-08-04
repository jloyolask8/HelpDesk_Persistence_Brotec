/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.Attachment;
import com.itcs.helpdesk.persistence.entities.Canal;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Categoria;
import com.itcs.helpdesk.persistence.entities.Componente;
import com.itcs.helpdesk.persistence.entities.Documento;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.Etiqueta;
import com.itcs.helpdesk.persistence.entities.Item;
import com.itcs.helpdesk.persistence.entities.Nota;
import com.itcs.helpdesk.persistence.entities.Prioridad;
import com.itcs.helpdesk.persistence.entities.Producto;
import com.itcs.helpdesk.persistence.entities.Recinto;
import com.itcs.helpdesk.persistence.entities.SubComponente;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.jpa.exceptions.IllegalOrphanException;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author jonathan
 */
public abstract class CasoJpaController extends AbstractJPAController implements Serializable {

    public CasoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    protected UserTransaction utx = null;
    protected EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caso caso) throws PreexistingEntityException, RollbackFailureException, Exception {
//        if (caso.getCasosRelacionadosList() == null) {
//            caso.setCasosRelacionadosList(new ArrayList<Caso>());
//        }
//        if (caso.getCasoList1() == null) {
//            caso.setCasoList1(new ArrayList<Caso>());
//        }
//        if (caso.getDocumentoList() == null) {
//            caso.setDocumentoList(new ArrayList<Documento>());
//        }
//        if (caso.getCasosHijosList() == null) {
//            caso.setCasosHijosList(new ArrayList<Caso>());
//        }
//        if (caso.getNotaList() == null) {
//            caso.setNotaList(new ArrayList<Nota>());
//        }
//        if (caso.getAttachmentList() == null) {
//            caso.setAttachmentList(new ArrayList<Attachment>());
//        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario owner = caso.getOwner();
            if (owner != null) {
                owner = em.getReference(owner.getClass(), owner.getIdUsuario());
                caso.setOwner(owner);
            }
            TipoAlerta estadoAlerta = caso.getEstadoAlerta();
            if (estadoAlerta != null) {
                estadoAlerta = em.getReference(estadoAlerta.getClass(), estadoAlerta.getIdalerta());
                caso.setEstadoAlerta(estadoAlerta);
            }
            SubEstadoCaso idSubEstado = caso.getIdSubEstado();
            if (idSubEstado != null) {
                idSubEstado = em.getReference(idSubEstado.getClass(), idSubEstado.getIdSubEstado());
                caso.setIdSubEstado(idSubEstado);
            }
            SubComponente idSubComponente = caso.getIdSubComponente();
            if (idSubComponente != null) {
                idSubComponente = em.getReference(idSubComponente.getClass(), idSubComponente.getIdSubComponente());
                caso.setIdSubComponente(idSubComponente);
            }
            Producto idProducto = caso.getIdProducto();
            if (idProducto != null) {
                idProducto = em.getReference(idProducto.getClass(), idProducto.getIdProducto());
                caso.setIdProducto(idProducto);
            }
            Prioridad idPrioridad = caso.getIdPrioridad();
            if (idPrioridad != null) {
                idPrioridad = em.getReference(idPrioridad.getClass(), idPrioridad.getIdPrioridad());
                caso.setIdPrioridad(idPrioridad);
            }
            EstadoCaso idEstado = caso.getIdEstado();
            if (idEstado != null) {
                idEstado = em.getReference(idEstado.getClass(), idEstado.getIdEstado());
                caso.setIdEstado(idEstado);
            }
            EmailCliente emailCliente = caso.getEmailCliente();
            if (emailCliente != null && emailCliente.getEmailCliente() != null) {
                emailCliente = em.getReference(emailCliente.getClass(), emailCliente.getEmailCliente());
                caso.setEmailCliente(emailCliente);
            }
            else
            {
                caso.setEmailCliente(null);
            }
            Componente idComponente = caso.getIdComponente();
            if (idComponente != null) {
                idComponente = em.getReference(idComponente.getClass(), idComponente.getIdComponente());
                caso.setIdComponente(idComponente);
            }
            Categoria idCategoria = caso.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getIdCategoria());
                caso.setIdCategoria(idCategoria);
            }
            Caso idCasoPadre = caso.getIdCasoPadre();
            if (idCasoPadre != null) {
                idCasoPadre = em.getReference(idCasoPadre.getClass(), idCasoPadre.getIdCaso());
                caso.setIdCasoPadre(idCasoPadre);
            }
            Canal idCanal = caso.getIdCanal();
            if (idCanal != null) {
                idCanal = em.getReference(idCanal.getClass(), idCanal.getIdCanal());
                caso.setIdCanal(idCanal);
            }
           
            Item idItem = caso.getIdItem();
            if (idItem != null)
            {
                idItem = em.getReference(idItem.getClass(), idItem.getIdItem());
                caso.setIdItem(idItem);
            }
            try {
                if (caso.getCasosRelacionadosList() != null) {
                    List<Caso> attachedCasoList = new ArrayList<Caso>();
                    for (Caso casoListCasoToAttach : caso.getCasosRelacionadosList()) {
                        casoListCasoToAttach = em.getReference(casoListCasoToAttach.getClass(), casoListCasoToAttach.getIdCaso());
                        attachedCasoList.add(casoListCasoToAttach);
                    }
                    caso.setCasosRelacionadosList(attachedCasoList);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }



//            List<Caso> attachedCasoList1 = new ArrayList<Caso>();
//            for (Caso casoList1CasoToAttach : caso.getCasoList1()) {
//                casoList1CasoToAttach = em.getReference(casoList1CasoToAttach.getClass(), casoList1CasoToAttach.getIdCaso());
//                attachedCasoList1.add(casoList1CasoToAttach);
//            }
//            caso.setCasoList1(attachedCasoList1);
//            List<Documento> attachedDocumentoList = new ArrayList<Documento>();
//            for (Documento documentoListDocumentoToAttach : caso.getDocumentoList()) {
//                documentoListDocumentoToAttach = em.getReference(documentoListDocumentoToAttach.getClass(), documentoListDocumentoToAttach.getIdDocumento());
//                attachedDocumentoList.add(documentoListDocumentoToAttach);
//            }
//            caso.setDocumentoList(attachedDocumentoList);
            if (caso.getCasosHijosList() != null) {
                List<Caso> attachedCasosHijosList = new ArrayList<Caso>();
                for (Caso casoList2CasoToAttach : caso.getCasosHijosList()) {
                    casoList2CasoToAttach = em.getReference(casoList2CasoToAttach.getClass(), casoList2CasoToAttach.getIdCaso());
                    attachedCasosHijosList.add(casoList2CasoToAttach);
                }
                caso.setCasosHijosList(attachedCasosHijosList);
            }


//            List<Nota> attachedNotaList = new ArrayList<Nota>();
//            for (Nota notaListNotaToAttach : caso.getNotaList()) {
//                notaListNotaToAttach = em.getReference(notaListNotaToAttach.getClass(), notaListNotaToAttach.getIdNota());
//                attachedNotaList.add(notaListNotaToAttach);
//            }
//            caso.setNotaList(attachedNotaList);

            try {
                if (caso.getAttachmentList() != null) {
                    List<Attachment> attachedAttachmentList = new ArrayList<Attachment>();
                    for (Attachment attachmentListAttachmentToAttach : caso.getAttachmentList()) {

                        attachmentListAttachmentToAttach.setIdAttachment(null);
                        em.persist(attachmentListAttachmentToAttach);
                        attachedAttachmentList.add(attachmentListAttachmentToAttach);
                    }
                    caso.setAttachmentList(attachedAttachmentList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



            try {
                if (caso.getEtiquetaList() != null) {
                    List<Etiqueta> attachedEtiquetaList = new ArrayList<Etiqueta>();
                    for (Etiqueta etiquetaListEtiquetaToAttach : caso.getEtiquetaList()) {

                        if (em.find(etiquetaListEtiquetaToAttach.getClass(), etiquetaListEtiquetaToAttach.getTagId()) == null) {
                            em.persist(etiquetaListEtiquetaToAttach);
                        } else {
                            etiquetaListEtiquetaToAttach = em.getReference(etiquetaListEtiquetaToAttach.getClass(), etiquetaListEtiquetaToAttach.getTagId());
                        }

                        attachedEtiquetaList.add(etiquetaListEtiquetaToAttach);
                    }
                    caso.setEtiquetaList(attachedEtiquetaList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            em.persist(caso);

            if (caso.getEtiquetaList() != null) {
                for (Etiqueta etiquetaListEtiqueta : caso.getEtiquetaList()) {
                    if (etiquetaListEtiqueta.getCasoList() == null) {
                        etiquetaListEtiqueta.setCasoList(new ArrayList<Caso>());
                    }
                    etiquetaListEtiqueta.getCasoList().add(caso);
                    etiquetaListEtiqueta = em.merge(etiquetaListEtiqueta);
                }
            }

            utx.commit();
        } catch (Exception ex) {
            if (ex instanceof ConstraintViolationException) {
                printOutContraintViolation((ConstraintViolationException) ex);
            }

            if (ex.getCause() instanceof ConstraintViolationException) {
                printOutContraintViolation((ConstraintViolationException) (ex.getCause()));
            }
//            ex.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (caso.getIdCaso() != null) {
                if (findCaso(caso.getIdCaso()) != null) {
                    throw new PreexistingEntityException("Caso " + caso + " already exists.", ex);
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caso caso;
            try {
                caso = em.getReference(Caso.class, id);
                caso.getIdCaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nota> notaListOrphanCheck = caso.getNotaList();
            for (Nota notaListOrphanCheckNota : notaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Caso (" + caso + ") cannot be destroyed since the Nota " + notaListOrphanCheckNota + " in its notaList field has a non-nullable idCaso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario owner = caso.getOwner();
            if (owner != null) {
                owner.getCasoList().remove(caso);
                owner = em.merge(owner);
            }
//            TipoAlerta estadoAlerta = caso.getEstadoAlerta();
//            if (estadoAlerta != null) {
//                estadoAlerta.getCasoList().remove(caso);
//                estadoAlerta = em.merge(estadoAlerta);
//            }
//            SubEstadoCaso idSubEstado = caso.getIdSubEstado();
//            if (idSubEstado != null) {
//                idSubEstado.getCasoList().remove(caso);
//                idSubEstado = em.merge(idSubEstado);
//            }
//            SubComponente idSubComponente = caso.getIdSubComponente();
//            if (idSubComponente != null) {
//                idSubComponente.getCasoList().remove(caso);
//                idSubComponente = em.merge(idSubComponente);
//            }
//            Producto idProducto = caso.getIdProducto();
//            if (idProducto != null) {
//                idProducto.getCasoList().remove(caso);
//                idProducto = em.merge(idProducto);
//            }
//            Prioridad idPrioridad = caso.getIdPrioridad();
//            if (idPrioridad != null) {
//                idPrioridad.getCasoList().remove(caso);
//                idPrioridad = em.merge(idPrioridad);
//            }
//            EstadoCaso idEstado = caso.getIdEstado();
//            if (idEstado != null) {
//                idEstado.getCasoList().remove(caso);
//                idEstado = em.merge(idEstado);
//            }
            EmailCliente emailCliente = caso.getEmailCliente();
            if (emailCliente != null) {
                emailCliente.getCasoList().remove(caso);
                emailCliente = em.merge(emailCliente);
            }
            Componente idComponente = caso.getIdComponente();
            if (idComponente != null) {
                idComponente.getCasoList().remove(caso);
                idComponente = em.merge(idComponente);
            }
            Categoria idCategoria = caso.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getCasoList().remove(caso);
                idCategoria = em.merge(idCategoria);
            }
            Caso idCasoPadre = caso.getIdCasoPadre();
            if (idCasoPadre != null) {
                idCasoPadre.getCasosRelacionadosList().remove(caso);
                idCasoPadre = em.merge(idCasoPadre);
            }
//            Canal idCanal = caso.getIdCanal();
//            if (idCanal != null) {
//                idCanal.getCasoList().remove(caso);
//                idCanal = em.merge(idCanal);
//            }
            List<Caso> casoList = caso.getCasosRelacionadosList();
            for (Caso casoListCaso : casoList) {
                casoListCaso.getCasosRelacionadosList().remove(caso);
                casoListCaso = em.merge(casoListCaso);
            }
//            List<Caso> casoList1 = caso.getCasoList1();
//            for (Caso casoList1Caso : casoList1) {
//                casoList1Caso.getCasosRelacionadosList().remove(caso);
//                casoList1Caso = em.merge(casoList1Caso);
//            }
            List<Documento> documentoList = caso.getDocumentoList();
            for (Documento documentoListDocumento : documentoList) {
                documentoListDocumento.getCasoList().remove(caso);
                documentoListDocumento = em.merge(documentoListDocumento);
            }
            
            
            Item idItem = caso.getIdItem();
            if (idItem != null)
            {
                idItem.getCasoList().remove(caso);
                idItem = em.merge(idItem);
            }
            
            List<Caso> casoList2 = caso.getCasosHijosList();
            for (Caso casoList2Caso : casoList2) {
                casoList2Caso.setIdCasoPadre(null);
                casoList2Caso = em.merge(casoList2Caso);
            }

            List<Attachment> attachmentList = caso.getAttachmentList();
            for (Attachment attachmentListAttachment : attachmentList) {
                attachmentListAttachment.setIdCaso(null);
                attachmentListAttachment = em.merge(attachmentListAttachment);
            }
            em.remove(caso);
            utx.commit();
        } catch (Exception ex) {
            if (ex instanceof ConstraintViolationException) {
                printOutContraintViolation((ConstraintViolationException) ex);
            }

            if (ex.getCause() instanceof ConstraintViolationException) {
                printOutContraintViolation((ConstraintViolationException) (ex.getCause()));
            }
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

    public List<Caso> findCasoEntities() {
        return findCasoEntities(true, -1, -1);
    }

    public List<Caso> findCasoEntities(int maxResults, int firstResult) {
        return findCasoEntities(false, maxResults, firstResult);
    }

    private List<Caso> findCasoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caso.class));
            Query q = em.createQuery(cq);
            q.setHint(QueryHints.REFRESH, HintValues.TRUE);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Caso findCaso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCasoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caso> rt = cq.from(Caso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    private void printOutContraintViolation(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> set = (ex).getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : set) {
            System.out.println("leafBean class: " + constraintViolation.getLeafBean().getClass());
            Iterator<Path.Node> iter = constraintViolation.getPropertyPath().iterator();
            System.out.println("constraintViolation.getPropertyPath(): ");
            while (iter.hasNext()) {
                System.out.print(iter.next().getName() + "/");
            }
            System.out.println("anotacion: " + constraintViolation.getConstraintDescriptor().getAnnotation().toString() + " value:" + constraintViolation.getInvalidValue());
        }
    }
}
