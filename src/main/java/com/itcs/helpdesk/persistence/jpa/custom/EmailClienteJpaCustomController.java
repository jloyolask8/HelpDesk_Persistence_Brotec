/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa.custom;

import com.itcs.helpdesk.persistence.entities.Cliente;
import com.itcs.helpdesk.persistence.entities.Cliente_;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.EmailCliente_;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Vista;
import com.itcs.helpdesk.persistence.jpa.EmailClienteJpaController;
import com.itcs.helpdesk.persistence.jpa.service.JPAServiceFacade;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class EmailClienteJpaCustomController extends EmailClienteJpaController {

    public EmailClienteJpaCustomController(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }

    public List<EmailCliente> findEmailClienteEntitiesLike(String searchPart) {
        return findEmailClienteEntitiesLike(searchPart, true, -1, -1);
    }

    public List<EmailCliente> findEmailClienteEntitiesLike(String searchPart, int maxResults, int firstResult) {
        return findEmailClienteEntitiesLike(searchPart, false, maxResults, firstResult);
    }

    protected Expression<?> createExpression(Root<?> root, String field) {
        Path<?> path = null;
        String[] fields = field.split("\\.");

        for (String string : fields) {
            if (null == path) {
                path = root.get(string);
            } else {
                path = path.get(string);
            }
        }

        return path;
    }

    public Long countSearchEntities(String searchPattern) throws ClassNotFoundException {
        EntityManager em = getEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", javax.persistence.CacheRetrieveMode.USE);

        try {
//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<EmailCliente> criteriaQuery = criteriaBuilder.createQuery(EmailCliente.class);
//            Root<EmailCliente> root = criteriaQuery.from(EmailCliente.class);

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
//            Root root = criteriaBuilder.from(EmailCliente.class);
            Root<EmailCliente> root = criteriaQuery.from(EmailCliente.class);

            Join<EmailCliente, Cliente> cliente = root.join(EmailCliente_.cliente);

            Expression<String> expresionNombre = root.get(EmailCliente_.cliente).get(Cliente_.nombres);
            Expression<String> expresionApellido = root.get(EmailCliente_.cliente).get(Cliente_.apellidos);
            Expression<String> expresionRut = root.get(EmailCliente_.cliente).get(Cliente_.rut);
            Expression<String> expresionEmail = root.get(EmailCliente_.emailCliente);
            Expression<String> expresionDireccionM = cliente.joinList("productoContratadoList").get("subComponente").get("direccionMunicipal");

            Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(expresionNombre), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionApellido), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionRut), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionEmail), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionDireccionM), "%" + searchPattern.toUpperCase() + "%"));

            if (predicate != null) {
                criteriaQuery.select(criteriaBuilder.count(root)).where(predicate).distinct(true);
            } else {
                criteriaQuery.select(criteriaBuilder.count(root));
            }
            Query q = em.createQuery(criteriaQuery);
            q.setHint("eclipselink.query-results-cache", true);
            return ((Long) q.getSingleResult());
        } catch (Exception e) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, "countSearchEntities " + searchPattern, e);
            return 0L;
        } finally {
            em.close();
        }
    }

    public List<EmailCliente> searchEntities(String searchPattern, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<EmailCliente> criteriaQuery = criteriaBuilder.createQuery(EmailCliente.class);
            Root<EmailCliente> from = criteriaQuery.from(EmailCliente.class);
            Join<EmailCliente, Cliente> cliente = from.join(EmailCliente_.cliente);

            Expression<String> expresionNombre = from.get(EmailCliente_.cliente).get(Cliente_.nombres);
            Expression<String> expresionApellido = from.get(EmailCliente_.cliente).get(Cliente_.apellidos);
            Expression<String> expresionRut = from.get(EmailCliente_.cliente).get(Cliente_.rut);
            Expression<String> expresionEmail = from.get(EmailCliente_.emailCliente);
            Expression<String> expresionDireccionM = cliente.joinList("productoContratadoList").get("subComponente").get("direccionMunicipal");

            Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(expresionNombre), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionApellido), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionRut), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionEmail), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionDireccionM), "%" + searchPattern.toUpperCase() + "%"));

            criteriaQuery.where(predicate);

            Query q = em.createQuery(criteriaQuery);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }

    }

    private List<EmailCliente> findEmailClienteEntitiesLike(String searchPart, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
//            CriteriaBuilder qb = em.getCriteriaBuilder();
//            CriteriaQuery query = qb.createQuery();
//            Root<EmailCliente> root = query.from(EmailCliente.class);
//            query.where(qb.like(root.get(EmailCliente_.emailCliente), searchPart + "%"));
//
//////            CriteriaBuilder cb = em.getCriteriaBuilder();
//////            CriteriaQuery cq = cb.createQuery();
//////            cq.select(cq.from(EmailCliente.class));
//////            cq.add(cb.like(EmailCliente_.emailCliente, searchPart + "%"));
//////            Query q = em.createQuery(cq);

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<EmailCliente> criteriaQuery = criteriaBuilder.createQuery(EmailCliente.class);
            Root<EmailCliente> from = criteriaQuery.from(EmailCliente.class);
            CriteriaQuery<EmailCliente> select = criteriaQuery.select(from);

            Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal((String) searchPart + "%"));
            Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(from.get(EmailCliente_.emailCliente)), literal);

            criteriaQuery.where(predicate);

            TypedQuery<EmailCliente> typedQuery = em.createQuery(select);
            if (!all) {
                typedQuery.setMaxResults(maxResults);
                typedQuery.setFirstResult(firstResult);
            }
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }
}
