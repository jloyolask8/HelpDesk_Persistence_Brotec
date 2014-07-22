/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa.custom;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Caso_;
import com.itcs.helpdesk.persistence.entities.Cliente;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.FiltroVista;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.entities.TipoCaso;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entityenums.EnumEstadoCaso;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoCaso;
import com.itcs.helpdesk.persistence.jpa.CasoJpaController;
import com.itcs.jpautils.EasyCriteriaQuery;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author jonathan
 */
public class CasoJPACustomController extends CasoJpaController {

    public CasoJPACustomController(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);

    }

    public List<Caso> findByRutCliente(String rut) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Caso.findByRutCliente").setParameter("rutCliente", rut).getResultList();
        } finally {
            em.close();
        }

    }

    public List<Caso> findByEmailCliente(String emailCliente) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery("Caso.findByEmailCliente").setParameter("emailCliente", emailCliente).getResultList();
        } finally {
            em.close();
        }

    }

    /*
     * @deprecated 
     * improve this code. 
     */
    public List<Caso> getCasoFindByEstadoAndAlerta(EstadoCaso estado, TipoAlerta tipoAlerta) {
        EasyCriteriaQuery<Caso> easyquery = new EasyCriteriaQuery<Caso>(emf, Caso.class);
        easyquery.addEqualPredicate(Caso_.idEstado.getName(), estado);
        easyquery.addEqualPredicate(Caso_.estadoAlerta.getName(), tipoAlerta);
        return easyquery.getAllResultList();
    }

    public int getCountCasosActualizados(Usuario usuario) {
        EasyCriteriaQuery<Caso> easyCriteriaQuery = new EasyCriteriaQuery<Caso>(emf, Caso.class);
        easyCriteriaQuery.addEqualPredicate(Caso_.revisarActualizacion.getName(), Boolean.TRUE);
        easyCriteriaQuery.addEqualPredicate(Caso_.owner.getName(), usuario);
        return easyCriteriaQuery.count().intValue();
    }

    /*
     * @deprecated 
     * improve this code. 
     */
    public int getCasoCountByTipoAlerta(Usuario usuario, TipoAlerta tipo_alerta) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();

            Root<Caso> rt = cq.from(Caso.class);
//            ParameterExpression<TipoAlerta> pAlerta = cb.parameter(TipoAlerta.class);
            cq.select(cb.count(rt)).where(
                    cb.equal(rt.get("owner"), usuario),
                    cb.equal(rt.get("estadoAlerta"), tipo_alerta),
                    cb.equal(rt.get("idEstado"), EnumEstadoCaso.ABIERTO.getEstado()));
            Query q = em.createQuery(cq);
            int retorno = ((Long) q.getSingleResult()).intValue();
            return retorno;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            em.close();
        }
        return 0;
    }

    public int getCasoCountByEstado(Usuario usuario, EstadoCaso estadoCaso) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();

            Root<Caso> rt = cq.from(Caso.class);
//            ParameterExpression<TipoAlerta> pAlerta = cb.parameter(TipoAlerta.class);
            cq.select(cb.count(rt)).where(
                    cb.equal(rt.get("owner"), usuario),
                    cb.equal(rt.get("idEstado"), estadoCaso));
            Query q = em.createQuery(cq);
            int retorno = ((Long) q.getSingleResult()).intValue();
            return retorno;
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            em.close();
        }
        return 0;
    }

    public int countCasos(Predicate predicate, EntityManager em, CriteriaBuilder cb, CriteriaQuery cq, Root<Caso> rt) {
        if (predicate == null) {
            cq.select(cb.count(rt));
        } else {
            cq.select(cb.count(rt)).where(predicate);
        }
        Query q = em.createQuery(cq);
        int retorno = ((Long) q.getSingleResult()).intValue();
        return retorno;
    }
    
    public List<Caso> findDuplicatedCasosPreventaByClient(Cliente cliente)
    {
        EasyCriteriaQuery<Caso> easyCriteriaQuery = new EasyCriteriaQuery<Caso>(emf, Caso.class);
        easyCriteriaQuery.addEqualPredicate("emailCliente.cliente", cliente);
        easyCriteriaQuery.addEqualPredicate("tipoCaso", EnumTipoCaso.PREVENTA.getTipoCaso());
        if(easyCriteriaQuery.count() > 1)
        {
            return easyCriteriaQuery.getAllResultList();
        }
        else
        {
            return null;
        }
    }

//    public List<Caso> findCasoCatFilter(Categoria cat, Usuario usuario, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<Caso> criteriaQuery = criteriaBuilder.createQuery(Caso.class);
//            Root<Caso> root = criteriaQuery.from(Caso.class);
//
//            Predicate predicate = createPredicateForCatFilter(em, criteriaBuilder, root, cat, usuario);
//            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Caso_.fechaCreacion)));
//
//            Query q = em.createQuery(criteriaQuery.where(predicate));
//            q.setMaxResults(maxResults);
//            q.setFirstResult(firstResult);
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
    public Caso findCasoBy(EmailCliente emailCliente, EstadoCaso idEstado, TipoCaso tipoCaso) {
        EntityManager em = getEntityManager();
        try {
//            TypedQuery<Caso> query =
//                    em.createQuery("SELECT c FROM Caso c WHERE c.idEstado.idEstado = :idEstado AND c.emailCliente.emailCliente = :emailCliente AND c.tipoCaso = :tipoCaso", Caso.class);
//            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
//            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
//            query.setParameter("idEstado", idEstado.getIdEstado());
//            query.setParameter("emailCliente", emailCliente);
//            query.setParameter("tipoCaso", tipoCaso.getIdTipoCaso());
            //query.setParameter("idProducto", idProducto);

            EasyCriteriaQuery<Caso> ecq = new EasyCriteriaQuery<Caso>(emf, Caso.class);
            ecq.addEqualPredicate(Caso_.idEstado.getName(), idEstado);
            ecq.addEqualPredicate("emailCliente", emailCliente);
            ecq.addEqualPredicate("tipoCaso", tipoCaso);
            if(ecq.count()>0)
            {
                return ecq.next().get(0);
            }
            else
            {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Caso pre-venta no encontrado para {0}", emailCliente);
            }
            return null;
        } catch (NoResultException no) {
            return null;
        } catch (NonUniqueResultException noU) {
            noU.printStackTrace();
            return null;
        } catch (IllegalStateException ill) {//- if called for a Java Persistence query language UPDATE or DELETE statement
            ill.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public Caso findCasoByIdEmailCliente(String email, Long idCaso) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Caso> query =
                    em.createQuery("SELECT c FROM Caso c WHERE c.idCaso = :idCaso AND c.emailCliente.emailCliente = :email", Caso.class);
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setHint(QueryHints.REFRESH, HintValues.TRUE);
            query.setParameter("idCaso", idCaso);
            query.setParameter("email", email);

            return query.getSingleResult();

        } catch (NoResultException no) {
            return null;
        } catch (NonUniqueResultException noU) {
            noU.printStackTrace();
            return null;
        } catch (IllegalStateException ill) {//- if called for a Java Persistence query language UPDATE or DELETE statement
            ill.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

//    private Predicate createPredicateForCatFilter(EntityManager em, CriteriaBuilder criteriaBuilder, Root<Caso> root, Categoria cat, Usuario usuario) {
//        /*
//         * predicado 1 que es cuando es de la categoria buscada y están los casos abiertos
//         */
//        Predicate predicate = null;
////        if (!(EnumCategorias.TODOS.getCategoria().equals(cat))) {
////            Predicate localPredicate = criteriaBuilder.equal(root.get(Caso_.idCategoria), cat);
////            predicate = CriteriaQueryHelper.addPredicate(predicate, localPredicate, criteriaBuilder);
////        }
//
//        Predicate localPredicateAbierto = criteriaBuilder.equal(root.get(Caso_.idEstado), EnumEstadoCaso.ABIERTO.getEstado());
//        predicate = CriteriaQueryHelper.addPredicate(predicate, localPredicateAbierto, criteriaBuilder);
//
//        /*
//         * predicado 2 que es cuando es de la categoria buscada y están los casos cerrados pero con el flag revisar actualización
//         */
//
//        //Predicate localPredicateOwner = criteriaBuilder.equal(root.get(Caso_.owner), usuario);
//        Predicate predicateCategoria = null;
////        if (!(EnumCategorias.TODOS.getCategoria().equals(cat))) {
////            predicateCategoria = criteriaBuilder.equal(root.get(Caso_.idCategoria), cat);
////        }
//
//        Predicate localPredicateActualizacion = criteriaBuilder.equal(root.get(Caso_.revisarActualizacion), Boolean.TRUE);
//        Predicate predicate2 = CriteriaQueryHelper.addPredicate(predicateCategoria, localPredicateActualizacion, criteriaBuilder);
//        Predicate localPredicateCerrado = criteriaBuilder.equal(root.get(Caso_.idEstado), EnumEstadoCaso.CERRADO.getEstado());
//        predicate2 = CriteriaQueryHelper.addPredicate(predicate2, localPredicateCerrado, criteriaBuilder);
//
//        /*
//         * Se crea un predicado del tipo predicado 1 o predicado 2
//         */
//
//        predicate = CriteriaQueryHelper.addOrPredicate(predicate, predicate2, criteriaBuilder);
//
//        return predicate;
//    }
    @Override
    protected Predicate createSpecialPredicate(FiltroVista filtro) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean isThereSpecialFiltering(FiltroVista filtro) {
        return false;
    }
}
