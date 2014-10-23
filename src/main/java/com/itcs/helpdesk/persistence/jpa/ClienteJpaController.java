/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa;

import com.itcs.helpdesk.persistence.entities.Cliente;
import com.itcs.helpdesk.persistence.entities.Cliente_;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.ProductoContratado;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import org.eclipse.persistence.config.QueryHints;

/**
 *
 * @author jonathan
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

//    public List<Cliente> findByRut(String rut) {
//        return (List<Cliente>) getEntityManager().createNamedQuery("Cliente.findByRut").setParameter("rut", rut).getResultList();
//    }
    public Cliente findByRut(String rut) {
        try {
            if (rut != null && !rut.isEmpty()) {
                return (Cliente) getEntityManager().createNamedQuery("Cliente.findByRut").setParameter("rut", rut).getSingleResult();
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    //TODO remove direccion municipal field from sub componente, use id as product code and nombre as product label or direccion
    public static Predicate createSearchExpression(Root<Cliente> root, CriteriaBuilder criteriaBuilder, String searchPattern) {
        Expression<String> expresionNombre = root.get(Cliente_.nombres);
        Expression<String> expresionApellido = root.get(Cliente_.apellidos);
        Expression<String> expresionRut = root.get(Cliente_.rut);
        Expression<String> expresionEmail = root.joinList(Cliente_.emailClienteList.getName(), JoinType.LEFT).get("emailCliente");//root.get(Cliente_.emailClienteList).(EmailCliente_.emailCliente);
        Expression<String> expresionDireccionM = root.joinList("productoContratadoList", JoinType.LEFT).join("subComponente", JoinType.LEFT).get("direccionMunicipal"); //.get("subComponente").get("direccionMunicipal");
        Predicate predicate = criteriaBuilder.or(
                criteriaBuilder.like(criteriaBuilder.upper(criteriaBuilder.concat(criteriaBuilder.concat(expresionNombre," "),expresionApellido)), "%" + searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(criteriaBuilder.concat(criteriaBuilder.concat(expresionApellido," "),expresionNombre)), "%" + searchPattern.toUpperCase() + "%"),
//                criteriaBuilder.like(criteriaBuilder.upper(), "%" + searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionRut), "%" + searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionEmail), "%" + searchPattern.toUpperCase() + "%"),
                criteriaBuilder.like(criteriaBuilder.upper(expresionDireccionM), "%" + searchPattern.toUpperCase() + "%"));
        return predicate;
    }

    public Long countSearchEntities(String searchPattern) throws ClassNotFoundException {
        EntityManager em = getEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", javax.persistence.CacheRetrieveMode.USE);

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            Root<Cliente> root = criteriaQuery.from(Cliente.class);

            Predicate predicate = createSearchExpression(root, criteriaBuilder, searchPattern);

            if (predicate != null) {
                criteriaQuery.select(criteriaBuilder.count(root)).where(predicate).distinct(true);
            } else {
                criteriaQuery.select(criteriaBuilder.count(root));
            }
            Query q = em.createQuery(criteriaQuery);
            q.setHint(QueryHints.QUERY_RESULTS_CACHE, true);
            return ((Long) q.getSingleResult());
        } catch (Exception e) {
            Logger.getLogger(ClienteJpaController.class.getName()).log(Level.SEVERE, "countSearchEntities " + searchPattern, e);
            return 0L;
        } finally {
            em.close();
        }
    }

    public List<Cliente> searchEntities(String searchPattern, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            Root<Cliente> root = criteriaQuery.from(Cliente.class);

            Predicate predicate = createSearchExpression(root, criteriaBuilder, searchPattern);

            criteriaQuery.where(predicate).distinct(true);

            criteriaQuery.orderBy(criteriaBuilder.asc(root.get("nombres")), criteriaBuilder.asc(root.get("apellidos")));

            Query q = em.createQuery(criteriaQuery);
            q.setHint("eclipselink.query-results-cache", true);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }

    }

    /**
     * I think one common way to do this is with transactions. If you begin a
     * new transaction and then persist a large number of objects, they won't
     * actually be inserted into the DB until you commit the transaction. This
     * can gain you some efficiencies if you have a large number of items to
     * commit.
     *
     */
    public Integer[] persistManyClients(List<Cliente> list) throws Exception {
        int persistedClients = 0;
        int existingClients = 0;
        int errorClients = 0;
        if (list != null && !list.isEmpty()) {

            EntityManager em = null;
            try {
                utx.begin();
                em = getEntityManager();
                int count = 0;
                for (Cliente cliente : list) {
                    try {
                        if (cliente != null) {
                            if (cliente.getIdCliente() != null) {
                                cliente = em.getReference(cliente.getClass(), cliente.getIdCliente());
                            } else {
                                List<Cliente> clientesRut = em.createNamedQuery("Cliente.findByRut").setParameter("rut", cliente.getRut()).getResultList();
                                if (clientesRut != null && !clientesRut.isEmpty()) {
                                    cliente = clientesRut.get(0);
                                    existingClients++;
                                } else {
                                    List<EmailCliente> attachedEmailClienteList = new ArrayList<EmailCliente>();
                                    for (EmailCliente emailClienteListEmailClienteToAttach : cliente.getEmailClienteList()) {
                                        if (em.find(EmailCliente.class, emailClienteListEmailClienteToAttach.getEmailCliente()) == null) {
                                            em.persist(emailClienteListEmailClienteToAttach);
                                            //emailClienteListEmailClienteToAttach = em.getReference(emailClienteListEmailClienteToAttach.getClass(), emailClienteListEmailClienteToAttach.getEmailCliente());
                                        } else {
                                            System.out.println(emailClienteListEmailClienteToAttach.getEmailCliente() + " existe");
                                        }

                                        attachedEmailClienteList.add(emailClienteListEmailClienteToAttach);
                                    }
                                    cliente.setEmailClienteList(attachedEmailClienteList);
                                    em.persist(cliente);
                                    persistedClients++;
                                }
                            }
                        } else {
                            errorClients++;
                        }
                        em.flush();
                    } catch (Exception e) {
                        System.out.println("ERORR en " + cliente + " -- " + cliente.getEmailClienteList());
                        e.printStackTrace();
                        errorClients++;
//                        break;
                    }
                    System.out.println(count++);
                }
                utx.commit();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (em != null) {
                    em.close();
                }
            }

        } else {
            throw new Exception("Nothing to persist dude.");
        }

        return new Integer[]{persistedClients, existingClients, errorClients};
    }

    public void create(Cliente cliente) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (cliente.getEmailClienteList() == null) {
            cliente.setEmailClienteList(new ArrayList<EmailCliente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<EmailCliente> attachedEmailClienteList = new ArrayList<EmailCliente>();
            for (EmailCliente emailClienteListEmailClienteToAttach : cliente.getEmailClienteList()) {
                if (em.find(EmailCliente.class, emailClienteListEmailClienteToAttach.getEmailCliente()) == null) {
                    em.persist(emailClienteListEmailClienteToAttach);
                    //emailClienteListEmailClienteToAttach = em.getReference(emailClienteListEmailClienteToAttach.getClass(), emailClienteListEmailClienteToAttach.getEmailCliente());
                }

                attachedEmailClienteList.add(emailClienteListEmailClienteToAttach);
            }
            cliente.setEmailClienteList(attachedEmailClienteList);
            em.persist(cliente);
            for (EmailCliente emailClienteListEmailCliente : cliente.getEmailClienteList()) {
                Cliente oldIdClienteOfEmailClienteListEmailCliente = emailClienteListEmailCliente.getCliente();
                emailClienteListEmailCliente.setCliente(cliente);
                emailClienteListEmailCliente = em.merge(emailClienteListEmailCliente);
                if (oldIdClienteOfEmailClienteListEmailCliente != null) {
                    oldIdClienteOfEmailClienteListEmailCliente.getEmailClienteList().remove(emailClienteListEmailCliente);
                    oldIdClienteOfEmailClienteListEmailCliente = em.merge(oldIdClienteOfEmailClienteListEmailCliente);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCliente(cliente.getIdCliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdCliente());
            List<EmailCliente> emailClienteListOld = persistentCliente.getEmailClienteList();
            List<EmailCliente> emailClienteListNew = cliente.getEmailClienteList();

            List<ProductoContratado> productoContratadoListOld = persistentCliente.getProductoContratadoList();
            List<ProductoContratado> productoContratadoListNew = cliente.getProductoContratadoList();

            Cliente existentCliente = null;
            try {
                if (cliente.getRut() != null && !cliente.getRut().isEmpty()) {
                    existentCliente = (Cliente) em.createNamedQuery("Cliente.findByRut").setParameter("rut", cliente.getRut()).getSingleResult();
                }
            } catch (NoResultException not) {
                existentCliente = null;
            }
            if (existentCliente != null && !existentCliente.getIdCliente().equals(cliente.getIdCliente())) {
                //There is another client using this RUT.
                throw new Exception("El Rut ya está asociado a otro cliente: " + existentCliente.getCapitalName());
            }

            List<EmailCliente> attachedEmailClienteListNew = new ArrayList<EmailCliente>();
            for (EmailCliente emailClienteListNewEmailClienteToAttach : emailClienteListNew) {
                emailClienteListNewEmailClienteToAttach = em.getReference(emailClienteListNewEmailClienteToAttach.getClass(), emailClienteListNewEmailClienteToAttach.getEmailCliente());
                attachedEmailClienteListNew.add(emailClienteListNewEmailClienteToAttach);
            }
            emailClienteListNew = attachedEmailClienteListNew;
            cliente.setEmailClienteList(emailClienteListNew);

            List<ProductoContratado> attachedProductoContratadoListNew = new ArrayList<ProductoContratado>();
            for (ProductoContratado toAttach : productoContratadoListNew) {
                toAttach = em.getReference(toAttach.getClass(), toAttach.getProductoContratadoPK());
                attachedProductoContratadoListNew.add(toAttach);
            }
            productoContratadoListNew = attachedProductoContratadoListNew;
            cliente.setProductoContratadoList(productoContratadoListNew);

            cliente = em.merge(cliente);

            for (EmailCliente emailClienteListOldEmailCliente : emailClienteListOld) {
                if (!emailClienteListNew.contains(emailClienteListOldEmailCliente)) {
                    emailClienteListOldEmailCliente.setCliente(null);
                    emailClienteListOldEmailCliente = em.merge(emailClienteListOldEmailCliente);
                }
            }
            for (EmailCliente emailClienteListNewEmailCliente : emailClienteListNew) {
                if (!emailClienteListOld.contains(emailClienteListNewEmailCliente)) {
                    Cliente oldIdClienteOfEmailClienteListNewEmailCliente = emailClienteListNewEmailCliente.getCliente();
                    emailClienteListNewEmailCliente.setCliente(cliente);
                    emailClienteListNewEmailCliente = em.merge(emailClienteListNewEmailCliente);
                    if (oldIdClienteOfEmailClienteListNewEmailCliente != null && !oldIdClienteOfEmailClienteListNewEmailCliente.equals(cliente)) {
                        oldIdClienteOfEmailClienteListNewEmailCliente.getEmailClienteList().remove(emailClienteListNewEmailCliente);
                        oldIdClienteOfEmailClienteListNewEmailCliente = em.merge(oldIdClienteOfEmailClienteListNewEmailCliente);
                    }
                }
            }
            
            for (ProductoContratado oldPc : productoContratadoListOld) {
                if (!productoContratadoListNew.contains(oldPc)) {
//                    oldPc.setCliente(null);
//                    oldPc = em.merge(oldPc);
                    em.remove(oldPc);
                }
            }
//            for (ProductoContratado newPc : productoContratadoListNew) {
//                if (!productoContratadoListOld.contains(newPc)) {
//                    if (oldIdClienteOfEmailClienteListNewEmailCliente != null && !oldIdClienteOfEmailClienteListNewEmailCliente.equals(cliente)) {
//                        oldIdClienteOfEmailClienteListNewEmailCliente.getEmailClienteList().remove(newPc);
//                        oldIdClienteOfEmailClienteListNewEmailCliente = em.merge(oldIdClienteOfEmailClienteListNewEmailCliente);
//                    }
//                }
//            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cliente.getIdCliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdCliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<EmailCliente> emailClienteList = cliente.getEmailClienteList();
            for (EmailCliente emailClienteListEmailCliente : emailClienteList) {
                emailClienteListEmailCliente.setCliente(null);
                emailClienteListEmailCliente = em.merge(emailClienteListEmailCliente);
            }
            em.remove(cliente);
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

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
