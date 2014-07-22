/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa.custom;

import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Usuario_;
import com.itcs.helpdesk.persistence.jpa.UsuarioJpaController;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 */
public class UsuarioJpaCustomController extends UsuarioJpaController {

    public UsuarioJpaCustomController(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
    }

    public List<Usuario> searchEntities(String searchPattern, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> from = criteriaQuery.from(Usuario.class);

            Expression<String> expresionNombre = from.get(Usuario_.nombres);
            Expression<String> expresionApellido = from.get(Usuario_.apellidos);
            Expression<String> expresionRut = from.get(Usuario_.rut);
            Expression<String> expresionEmail = from.get(Usuario_.email);
            Expression<String> expresionIdUsuario = from.get(Usuario_.idUsuario);
            
//            Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal((String) searchPattern + "%"));
//            Predicate predicate = criteriaBuilder.like(criteriaBuilder.upper(from.get(EmailCliente_.emailCliente)), literal);

            Predicate predicate = criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.upper(expresionNombre), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionApellido), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionRut), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionEmail), searchPattern.toUpperCase() + "%"),
                    criteriaBuilder.like(criteriaBuilder.upper(expresionIdUsuario), searchPattern.toUpperCase() + "%"));

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

    
}
