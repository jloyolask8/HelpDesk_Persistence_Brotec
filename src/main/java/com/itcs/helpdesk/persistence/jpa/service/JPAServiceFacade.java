package com.itcs.helpdesk.persistence.jpa.service;

import com.itcs.helpdesk.persistence.jpa.EasyCriteriaQuery;
import com.itcs.helpdesk.persistence.entities.Attachment;
import com.itcs.helpdesk.persistence.entities.metadata.Attachment_;
import com.itcs.helpdesk.persistence.entities.AuditLog;
import com.itcs.helpdesk.persistence.entities.metadata.AuditLog_;
import com.itcs.helpdesk.persistence.entities.Canal;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.metadata.Caso_;
import com.itcs.helpdesk.persistence.entities.Cliente;
import com.itcs.helpdesk.persistence.entities.Componente;
import com.itcs.helpdesk.persistence.entities.Documento;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.metadata.EmailCliente_;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.Etiqueta;
import com.itcs.helpdesk.persistence.entities.FieldType;
import com.itcs.helpdesk.persistence.entities.FiltroVista;
import com.itcs.helpdesk.persistence.entities.Grupo;
import com.itcs.helpdesk.persistence.entities.Item;
import com.itcs.helpdesk.persistence.entities.metadata.Item_;
import com.itcs.helpdesk.persistence.entities.Nota;
import com.itcs.helpdesk.persistence.entities.Producto;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import com.itcs.helpdesk.persistence.entities.Resource;
import com.itcs.helpdesk.persistence.entities.Rol;
import com.itcs.helpdesk.persistence.entities.SubComponente;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.entities.TipoCaso;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Vista;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoCanal;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoCaso;
import com.itcs.helpdesk.persistence.jpa.AbstractJPAController;
import com.itcs.helpdesk.persistence.jpa.custom.CriteriaQueryHelper;
import com.itcs.helpdesk.persistence.jpa.exceptions.IllegalOrphanException;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import com.itcs.helpdesk.persistence.utils.CasoChangeListener;
import com.itcs.helpdesk.persistence.utils.ConstraintViolationExceptionHelper;
import com.itcs.helpdesk.persistence.utils.OrderBy;
import com.itcs.helpdesk.persistence.utils.vo.AuditLogVO;
import com.itcs.helpdesk.persistence.utils.vo.RegistrationVO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.resource.NotSupportedException;
import javax.transaction.UserTransaction;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.internal.helper.DatabaseField;
import org.eclipse.persistence.internal.sessions.AbstractSession;
import org.eclipse.persistence.sessions.Session;

public class JPAServiceFacade extends AbstractJPAController {

    public static final String CASE_CUSTOM_FIELD = "case";
    public static final String CLIENT_CUSTOM_FIELD = "client";

    private CasoChangeListener casoChangeListener;

    //old
//    public JPAServiceFacade(UserTransaction utx, EntityManagerFactory emf) {
//        this.utx = utx;
//        this.emf = emf;
//    }
    /**
     * Multitenant jpa service
     *
     * @param utx
     * @param emf
     * @param schema
     */
    public JPAServiceFacade(UserTransaction utx, EntityManagerFactory emf, String schema) {
        super(utx, emf, schema);
    }

//    /**
//     * defaults to public schema in multitenant usage.
//     *
//     * @param utx
//     * @param emf
//     */
//    public JPAServiceFacade(UserTransaction utx, EntityManagerFactory emf) {
//        super(utx, emf, PUBLIC_SCHEMA_NAME);//THIS WILL CREATE A JPA INSTANCE POINTING TO THE PUBLIC SCHEMA.
//    }
    public String findSchemaByName(String schema) {
        if (!StringUtils.isEmpty(schema)) {
            EntityManager em = null;
            try {
                em = getEntityManager();
                Query query = em.createNativeQuery("select schema_name \n"
                        + "from information_schema.schemata WHERE schema_name = ?");

                Object o = query.setParameter(1, schema).getSingleResult();
                System.out.println("*********** " + o);
                return (String) o;
            } catch (NoResultException no) {
                return null;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }

        return null;
    }

    /**
     * Register a new tenant. We assume data has been validated at this point
     *
     * @param registrationVO
     * @return
     */
    public String createTheSchema(RegistrationVO registrationVO) {
        if (!StringUtils.isEmpty(registrationVO.getCompanyName())) {
            EntityManager em = null;
//            EntityManager emNew = null;
            try {
                utx.begin();
                em = getEntityManager();
                final String schemaName = registrationVO.getCompanyName().trim().toLowerCase().replace("\u0020", "_");
                registrationVO.setCompanyName(schemaName);
                //1. Create the new schema
                Query query = em.createNativeQuery("SELECT create_new_schema('base_schema', ?)");
                Object o = query.setParameter(1, schemaName).getSingleResult();

                System.out.println("*** Schema created: " + schemaName);

                utx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }

        return null;
    }

//    /**
//     * NON-Multitenant usage.
//     * @param utx
//     * @param emf
//     */
//    public JPAServiceFacade(UserTransaction utx, EntityManagerFactory emf) {
//        super(utx, emf, null/*"public"*/);//THIS WILL CREATE A JPA INSTANCE POINTING TO THE PUBLIC SCHEMA.
//    }
    public <T extends Object> T find(Class<T> entityClass, Object id) {
        return find(entityClass, id, false);
    }

    /**
     * Finds an Entity and Refresh the state of the instance from the database,
     * overwriting changes made to the entity, if any.
     *
     * @param <T>
     * @param entityClass
     * @param primaryKey
     * @param refresh set to true if you want to Refresh the state of the
     * instance from the database. False otherwise goes to the cache.
     * @return
     */
    public <T extends Object> T find(Class<T> entityClass, Object primaryKey, boolean refresh) {
        if (refresh) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            properties.put(QueryHints.REFRESH, HintValues.TRUE);
            return getEntityManager().find(entityClass, primaryKey, properties);
        } else {
            return getEntityManager().find(entityClass, primaryKey);
        }
    }

    public <T extends Object> T getReference(Class<T> entityClass, Object id) throws EntityNotFoundException {
        return getEntityManager().getReference(entityClass, id);
    }

    public void refresh(Object o) {
        getEntityManager().refresh(o);
    }

    public void persist(Object o) throws Exception {
        EntityManager em = null;//
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(o);
            utx.commit();
        } catch (Exception ex) {
            ConstraintViolationExceptionHelper.handleError(ex);
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

//    public void persistInTx(Object o) throws Exception {
//        EntityManager em = null;//
//        try {
//            em = getEntityManager();
//            em.persist(o);
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    public void remove(Class clazz, Object o) throws Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            //em.remove(emf.getPersistenceUnitUtil().getIdentifier(o));
            em.remove(em.getReference(clazz, getIdentifier(em, o)));
            utx.commit();
        } catch (Exception ex) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

    public void removeFromPK(Class clazz, Object pk) throws Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            //em.remove(emf.getPersistenceUnitUtil().getIdentifier(o));
            em.remove(em.getReference(clazz, pk));
            utx.commit();
        } catch (Exception ex) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

//    public void mergeInTx(Object o) throws Exception {
//        EntityManager em = null;
//        try {
//            em = getEntityManager();
//            em.merge(o);
//        } catch (Exception ex) {
//            ConstraintViolationExceptionHelper.handleError(ex);
//            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, null, ex);
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    public void merge(Object o) throws Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.merge(o);
            utx.commit();
        } catch (Exception ex) {
            ConstraintViolationExceptionHelper.handleError(ex);
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Returns the id of the entity. A generated id is not guaranteed to be
     * available until after the database insert has occurred. Returns null if
     * the entity does not yet have an id
     *
     * This method changed to be used as multitenant
     *
     * @param entity
     * @return id of the entity
     * @throws IllegalStateException if the entity is found not to be an entity.
     */
    public Object getIdentifier(Object entity) {
        EntityManager em = getEntityManager();

        try {
            AbstractSession session = (AbstractSession) em.unwrap(Session.class);
            ClassDescriptor descriptor = session.getDescriptor(entity);
            if (descriptor.getPrimaryKeyFields().size() != 1) {
                throw new NotSupportedException("sorry dude Composite PK is not supported yet!");
            }
            String methodName = null;
            for (DatabaseField databaseField : descriptor.getPrimaryKeyFields()) {
                methodName = createGetIdentifierMethodName(databaseField.getName());
            }

            java.beans.Expression expresion;
            expresion = new java.beans.Expression(entity, methodName, new Object[0]);

            expresion.execute();
            return expresion.getValue();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

        return null;
    }

    /**
     * multitenant version of get entity Identifier
     *
     * @param em
     * @param entity
     * @return
     */
    public Object getIdentifier(EntityManager em, Object entity) {
        try {
            AbstractSession session = (AbstractSession) em.unwrap(Session.class);
            ClassDescriptor descriptor = session.getDescriptor(entity);
            if (descriptor.getPrimaryKeyFields().size() != 1) {
                throw new NotSupportedException("sorry dude Composite PK is not supported yet!");
            }
            String methodName = null;
            for (DatabaseField databaseField : descriptor.getPrimaryKeyFields()) {
                methodName = createGetIdentifierMethodName(databaseField.getName());
            }

            java.beans.Expression expresion;
            expresion = new java.beans.Expression(entity, methodName, new Object[0]);

            expresion.execute();
            return expresion.getValue();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String createGetIdentifierMethodName(String name) { // "MY_COLUMN"
        String name0 = name.replace("_", " "); // to "MY COLUMN"
        name0 = WordUtils.capitalizeFully(name0); // to "My Column"
        name0 = name0.replace(" ", ""); // to "MyColumn"
//        name0 = WordUtils.uncapitalize(name0); // to "myColumn"
        return "get" + name0;
    }

    /**
     * <
     * code>SELECT count(*) from o</code>
     */
    public Long count(Class entityClass) {
        EasyCriteriaQuery q = new EasyCriteriaQuery(this, entityClass);
        return q.count();
    }

    public Long countEntities(Vista vista) throws ClassNotFoundException {
        return countEntities(vista, false, null, null);
    }

    public Long countEntities(Vista vista, Usuario who, String query) throws ClassNotFoundException {
        return countEntities(vista, true, who, query);
    }

    public Long countEntities(Vista vista, boolean useNonPersistentFilters, Usuario who, String query) throws ClassNotFoundException {
        EntityManager em = getEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", javax.persistence.CacheRetrieveMode.USE);

        try {
            if (vista == null || vista.getBaseEntityType() == null) {
                return 0L;
            }
            final String baseEntityType = vista.getBaseEntityType();
            final Class<?> entityClass = Class.forName(baseEntityType);

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root root = criteriaQuery.from(entityClass);

            Predicate predicate = createPredicate(em, criteriaBuilder, root, vista, useNonPersistentFilters, who, query);

            if (predicate != null) {
                criteriaQuery.select(criteriaBuilder.count(root)).where(predicate).distinct(true);
            } else {
                criteriaQuery.select(criteriaBuilder.count(root));
            }
            Query q = em.createQuery(criteriaQuery);
            q.setHint("eclipselink.query-results-cache", true);
            return ((Long) q.getSingleResult());
        } catch (ClassNotFoundException e) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, "ClassNotFoundException countEntities by view of class " + vista.getBaseEntityType(), e);
            throw e;
        } catch (IllegalStateException e) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, "countEntities by view " + vista, e);
            return 0L;
        } finally {
            em.close();
        }
    }

    public List<?> findAllEntities(Vista vista, OrderBy orderBy, Usuario who) throws IllegalStateException, NotSupportedException, ClassNotFoundException {
        return findEntities(vista, true, true, -1, -1, orderBy, null, who);
    }

    public List<?> findEntities(Vista vista, int maxResults, int firstResult, OrderBy orderBy, Usuario who) throws IllegalStateException, NotSupportedException, ClassNotFoundException {
        return findEntities(vista, true, false, maxResults, firstResult, orderBy, null, who);
    }

    public List<?> findEntities(Vista vista, int maxResults, int firstResult, OrderBy orderBy, Usuario who, String query) throws IllegalStateException, NotSupportedException, ClassNotFoundException {
        return findEntities(vista, true, false, maxResults, firstResult, orderBy, query, who);
    }

    public List<?> findEntities(Vista vista, boolean useNonPersistentFilters, int maxResults, int firstResult, OrderBy orderBy, Usuario who, String query) throws IllegalStateException, NotSupportedException, ClassNotFoundException {
        return findEntities(vista, useNonPersistentFilters, false, maxResults, firstResult, orderBy, query, who);
    }

    private List<?> findEntities(Vista vista, boolean useNonPersistentFilters, boolean all, int maxResults, int firstResult, OrderBy orderBy, String query, Usuario who) throws IllegalStateException, ClassNotFoundException {
        EntityManager em = getEntityManager();

        try {

            final String baseEntityType = vista.getBaseEntityType();
            final Class<?> entityClass = Class.forName(baseEntityType);

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root root = criteriaQuery.from(entityClass);

            Predicate predicate = createPredicate(em, criteriaBuilder, root, vista, useNonPersistentFilters, who, query);

            if (predicate != null) {
                criteriaQuery.where(predicate).distinct(true);
            }
            if (orderBy != null && orderBy.getFieldName() != null) {
                if (orderBy.getOrderType().equals(OrderBy.OrderType.ASC)) {
                    criteriaQuery.orderBy(criteriaBuilder.asc(root.get(orderBy.getFieldName())));
                } else {
                    criteriaQuery.orderBy(criteriaBuilder.desc(root.get(orderBy.getFieldName())));
                }

            }
            Query q = em.createQuery(criteriaQuery);

            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();

        } catch (IllegalStateException ex) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, ex.getMessage());
//            throw ex;
            return Collections.EMPTY_LIST;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.SEVERE, "ClassNotFoundException at findEntities", ex);
//            throw ex;
            return Collections.EMPTY_LIST;
        } finally {
            em.close();
        }
    }

    public Attachment findAttachmentByContentId(String contentId, Caso caso) {
        EasyCriteriaQuery<Attachment> easyCriteriaQuery = new EasyCriteriaQuery<>(this, Attachment.class);
        easyCriteriaQuery.addEqualPredicate(Attachment_.contentId.getName(), contentId);
        easyCriteriaQuery.addEqualPredicate(Attachment_.idCaso.getName(), caso);
        System.out.println("buscando contentId: " + contentId);
        List<Attachment> res = easyCriteriaQuery.getAllResultList();
        if (res.size() > 0) {
            return res.get(0);
        }
        return null;
    }

    public Caso findCasoByIdEmailCliente(String email, Long idCaso) {
        EntityManager em = getEntityManager();

        try {
            TypedQuery<Caso> query
                    = em.createQuery("SELECT c FROM Caso c WHERE c.idCaso = :idCaso AND c.emailCliente.emailCliente = :email", Caso.class);
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

    public Cliente findClienteByEmail(String email) {
        final EmailCliente find = find(EmailCliente.class, email);
        if (find != null) {
            return find.getCliente();
        } else {
            return null;
        }
    }

    public Cliente findClienteByRut(String rut) {
        try {
            if (rut != null && !rut.isEmpty()) {
//                 properties.put(QueryHints.REFRESH, HintValues.TRUE);
                return (Cliente) getEntityManager().createNamedQuery("Cliente.findByRut").setParameter("rut", rut).getSingleResult();
            } else {
                return null;
            }

        } catch (Exception e) {
            return null;
        }
    }

    public List<Caso> findDuplicatedCasosPreventaByClient(Cliente cliente) {
        EasyCriteriaQuery<Caso> easyCriteriaQuery = new EasyCriteriaQuery<Caso>(this, Caso.class);
        easyCriteriaQuery.addEqualPredicate("emailCliente.cliente", cliente);
        easyCriteriaQuery.addEqualPredicate("tipoCaso", EnumTipoCaso.PREVENTA.getTipoCaso());
        if (easyCriteriaQuery.count() > 1) {
            return easyCriteriaQuery.getAllResultList();
        } else {
            return null;
        }
    }

//    public Long countCasosByClosedBetween(Date from, Date to) {
//        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, Caso.class);
//        q.addBetweenPredicate(Caso_.fechaCierre, from, to);
//        q.addEqualPredicate("idEstado", EnumEstadoCaso.CERRADO.getEstado());
//        return q.count();
//    }
    public Long countCasosByEtiqueta(Etiqueta etiqueta) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caso> root = cq.from(Caso.class);
            Expression<List<Etiqueta>> exp = root.get("etiquetaList");
            Predicate predicate = cb.isMember(etiqueta, exp);

            if (predicate != null) {
                cq.select(cb.count(root)).where(predicate);
                Query q = em.createQuery(cq);
                return (Long) q.getSingleResult();
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            em.close();
        }
        return 0L;
    }

    /**
     *
     * @param idUsuario
     * @return
     */
    public List<Etiqueta> findEtiquetasByUsuario(String idUsuario) {

        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Etiqueta> criteriaQuery = criteriaBuilder.createQuery(Etiqueta.class);
            Root<Etiqueta> root = criteriaQuery.from(Etiqueta.class);
            Expression<String> idUsuarioExp = root.get("owner").get("idUsuario");
            Expression<String> ownerExp = root.get("owner");

            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(root.get("tagId")));
            Predicate predicate = criteriaBuilder.equal(idUsuarioExp, idUsuario);
            Predicate predicate2 = criteriaBuilder.isNull(ownerExp);
            criteriaQuery.where(criteriaBuilder.or(predicate, predicate2));
            Query q = em.createQuery(criteriaQuery);
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;

        } finally {
            em.close();
        }
    }

    public List<Etiqueta> findEtiquetasLike(String etiquetaPattern, String idUsuario) {

        EntityManager em = getEntityManager();

        try {
            return em.createNamedQuery("Etiqueta.findByTagIdAndIdUsuario")
                    .setParameter("tagId", etiquetaPattern + "%")
                    .setParameter("idUsuario", idUsuario)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        } finally {
            em.close();
        }
    }

//
//    public Object persist(Object entity) {
//        return (Object) throws PreexistingEntityException, RollbackFailureException, Exception(entity);
//    }
//
//    public Object merge(Object entity) {
//        return (Object) mergeEntity(entity);
//    }
//
//    public void commitTransaction() {
//        commitTransaction();
//    }
//
//    public void rollbackTransaction() {
//        rollbackTransaction();
//    }
//
//    public boolean isTransactionDirty() {
//        return isTransactionDirty();
//    }
//    /**
//     * @deprecated @param entityClazz
//     * @param maxResults
//     * @param firstResult
//     * @return
//     */
//    public List queryByRange(Class entityClazz, int maxResults, int firstResult) {
//        EntityManager em = getEntityManager();
//        try {
//            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
//            cq.select(cq.from(entityClazz));
////            Root root = cq.from(entityClazz);
//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClazz);
//            Query q = em.createQuery(criteriaQuery);
//
//            q.setMaxResults(maxResults);
//            q.setFirstResult(firstResult);
//
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
    public void persistCaso(Caso caso, List<AuditLog> changeList) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (caso.getAttachmentList() != null && !caso.getAttachmentList().isEmpty()) {
            caso.setHasAttachments(true);
        }
        if (caso.getScheduleEventList() != null && !caso.getScheduleEventList().isEmpty()) {
            caso.setHasScheduledEvents(true);
        }
        persist(caso);//getCasoJpa().create(caso);
        if (changeList != null) {
            for (AuditLog auditLog : changeList) {
                auditLog.setIdCaso(caso.getIdCaso());
                persist(auditLog);
            }
        }
        notifyCasoEventListeners(caso, true, changeList);

    }

    public Caso mergeCaso(Caso caso, AuditLog log) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        return mergeCaso(caso, log, true);
    }

    public Caso mergeCasoWithoutNotify(Caso caso, AuditLog log) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        return mergeCaso(caso, log, false);
    }

    public Caso mergeCaso(Caso caso, AuditLog log, boolean notifyListeners) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        List<AuditLog> changeList = new LinkedList<>();
        changeList.add(log);
        return mergeCaso(caso, changeList, notifyListeners);
    }

    public Caso mergeCaso(Caso caso, List<AuditLog> changeList) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        return mergeCaso(caso, changeList, true);
    }

    public Caso mergeCaso(Caso caso, List<AuditLog> changeList, boolean notifyListeners) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        caso.setFechaModif(Calendar.getInstance().getTime());

        if (caso.getAttachmentList() != null && !caso.getAttachmentList().isEmpty()) {
            caso.setHasAttachments(true);
        }
        if (caso.getScheduleEventList() != null && !caso.getScheduleEventList().isEmpty()) {
            caso.setHasScheduledEvents(true);
        }

        merge(caso);
        if (changeList != null) {
            for (AuditLog auditLog : changeList) {
                persist(auditLog);
            }
        }
        if (notifyListeners) {
            notifyCasoEventListeners(caso, false, changeList);
        }

        return (Caso) getEntityManager().getReference(Caso.class, caso.getIdCaso());
    }

    /**
     * Notify to all event listeners
     *
     * @param caso caso que cambia
     * @param create true if is a caso creation, or false is update
     * @param changeList list of changes
     */
    public void notifyCasoEventListeners(Caso caso, boolean create, List<AuditLog> changeList) {
        if (getCasoChangeListener() != null) {
            if (create) {
                getCasoChangeListener().casoCreated(caso);
            } else {
                getCasoChangeListener().casoChanged(caso, changeList);
            }
        } else {
            Logger.getLogger(JPAServiceFacade.class.getName()).log(Level.WARNING, "\n*** WARNING *** CasoChangeListener NOT configured!\n");
        }
    }

    public Caso mergeCasoWithoutNotify(Caso caso) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        merge(caso);
        return (Caso) getEntityManager().getReference(Caso.class, caso.getIdCaso());
    }

    public Caso mergeCasoWithoutNotify(Caso caso, List<AuditLog> changeList) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        return mergeCaso(caso, changeList, false);
    }

    /*
     * Audit Log Methods
     */
    public int getAuditLogCount(AuditLogVO filter, boolean log) {
        filter.setAlertLevel(!log);
        EasyCriteriaQuery<AuditLog> criteriaQueryCasos = new EasyCriteriaQuery<>(this, AuditLog.class);
        createAuditLogCountPredicate(filter, criteriaQueryCasos);
        return criteriaQueryCasos.count().intValue();

//        return getAuditLogJpaCustomController().countByFilterForAudit(alert);
    }

    private void createAuditLogCountPredicate(AuditLogVO filter, EasyCriteriaQuery<AuditLog> criteriaQueryCasos) {
        if (filter.isAlertLevel()) {
            criteriaQueryCasos.addEqualPredicate(AuditLog_.campo.getName(), "EstadoAlerta");
        }

        if (filter.getIdCaso() != null) {
            criteriaQueryCasos.addEqualPredicate(AuditLog_.idCaso.getName(), filter.getIdCaso());
        }

        if (filter.getFechaFin() != null && filter.getFechaInicio() != null) {
            criteriaQueryCasos.addBetweenPredicate(AuditLog_.fecha, filter.getFechaInicio(), filter.getFechaFin());
        }
        if (filter.getIdOwner() != null) {
            criteriaQueryCasos.addEqualPredicate(AuditLog_.owner.getName(), filter.getIdOwner().getIdUsuario());
        }
        if (filter.getIdUser() != null) {
            criteriaQueryCasos.addEqualPredicate(AuditLog_.idUser.getName(), filter.getIdUser().getIdUsuario());
        }
    }
    /*
     * Audit Log Methods
     */

    public List<AuditLog> findAuditLogEntities(AuditLogVO alert) {
        return findAuditLogEntities(true, -1, -1, alert, true);
    }

    private List<AuditLog> findAuditLogEntities(boolean all, int maxResults, int firstResult, AuditLogVO filter, boolean log) {

        EasyCriteriaQuery<AuditLog> criteriaQueryCasos = new EasyCriteriaQuery<>(this, AuditLog.class);
        filter.setAlertLevel(!log);
        createAuditLogCountPredicate(filter, criteriaQueryCasos);
        criteriaQueryCasos.orderBy(AuditLog_.fecha.getName(), false);

        if (!all) {
            criteriaQueryCasos.setFirstResult(firstResult);
            criteriaQueryCasos.setMaxResults(maxResults);
            return criteriaQueryCasos.next();
        }

        return criteriaQueryCasos.getAllResultList();
    }

    /*
     * Audit Log Methods
     */
    public List<AuditLog> findAuditLogEntities(int maxResults, int firstResult, AuditLogVO alert, boolean log) {
        return findAuditLogEntities(false, maxResults, firstResult, alert, log);
    }

    /**
     * I think one better way to do this is with transactions. If you begin a
     * new transaction and then persist a large number of objects, they won't
     * actually be inserted into the DB until you commit the transaction. This
     * can gain you some efficiencies if you have a large number of items to
     * commit.
     *
     * @param list
     * @return Integer[] with Integer[] {persistedClients, existingClients,
     * errorClients}
     * @throws java.lang.Exception
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
                for (Cliente clienteVo : list) {
                    try {
                        if (clienteVo != null) {
                            if (clienteVo.getIdCliente() != null) {
                                clienteVo = em.find(clienteVo.getClass(), clienteVo.getIdCliente());
                                existingClients++;
                            } else {
                                //buscarlo por el rut
                                List<Cliente> clienteByRut = em.createNamedQuery("Cliente.findByRut").setParameter("rut", clienteVo.getRut()).getResultList();
                                if (clienteByRut != null && !clienteByRut.isEmpty()) {
                                    Cliente cliente = clienteByRut.get(0);
                                    cliente.setNombres(clienteVo.getNombres());
                                    cliente.setApellidos(clienteVo.getApellidos());
                                    cliente.setDirParticular(clienteVo.getDirParticular());
                                    cliente.setDirComercial(clienteVo.getDirComercial());
                                    cliente.setFono1(clienteVo.getFono1());
                                    cliente.setFono2(clienteVo.getFono2());
                                    cliente.setSexo(clienteVo.getSexo());

                                    em.merge(cliente);

                                    existingClients++;
                                } else {
                                    boolean exists = false;
                                    //not found by rut
                                    //Buscar por el email.
                                    for (EmailCliente emailCliente : clienteVo.getEmailClienteList()) {
                                        EmailCliente email = em.find(EmailCliente.class, emailCliente.getEmailCliente());
                                        if (email != null) {
                                            if (email.getCliente() != null) {
                                                Cliente cliente = email.getCliente();
                                                cliente.setRut(clienteVo.getRut());
                                                cliente.setNombres(clienteVo.getNombres());
                                                cliente.setApellidos(clienteVo.getApellidos());
                                                cliente.setDirParticular(clienteVo.getDirParticular());
                                                cliente.setDirComercial(clienteVo.getDirComercial());
                                                cliente.setFono1(clienteVo.getFono1());
                                                cliente.setFono2(clienteVo.getFono2());
                                                cliente.setSexo(clienteVo.getSexo());

                                                em.merge(cliente);
                                                existingClients++;
                                                exists = true;
                                                break;
                                            }
                                        }
                                    }

//                                    List<EmailCliente> attachedEmailClienteList = new ArrayList<>();
//                                    for (EmailCliente emailClienteListEmailClienteToAttach : cliente.getEmailClienteList()) {
//                                        if (em.find(EmailCliente.class, emailClienteListEmailClienteToAttach.getEmailCliente()) == null) {
//                                            em.persist(emailClienteListEmailClienteToAttach);
//                                            //emailClienteListEmailClienteToAttach = em.getReference(emailClienteListEmailClienteToAttach.getClass(), emailClienteListEmailClienteToAttach.getEmailCliente());
//                                        } else {
//                                            System.out.println(emailClienteListEmailClienteToAttach.getEmailCliente() + " existe");
//                                        }
//
//                                        attachedEmailClienteList.add(emailClienteListEmailClienteToAttach);
//                                    }
//                                    cliente.setEmailClienteList(attachedEmailClienteList);
                                    if (!exists) {
                                        em.persist(clienteVo);
                                        persistedClients++;
                                    }

                                }
                            }
                        } else {
                            errorClients++;
                        }
                        em.flush();
                    } catch (Exception e) {
                        ConstraintViolationExceptionHelper.handleError(e);
                        System.out.println("ERORR en " + clienteVo + " -- " + clienteVo.getEmailClienteList());
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

            EasyCriteriaQuery<Caso> ecq = new EasyCriteriaQuery<>(this, Caso.class);
            ecq.addEqualPredicate(Caso_.idEstado.getName(), idEstado);
            ecq.addEqualPredicate("emailCliente", emailCliente);
            ecq.addEqualPredicate("tipoCaso", tipoCaso);
            if (ecq.count() > 0) {
                return ecq.next().get(0);
            } else {
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

    public void mergeUsuarioFull(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
//        getUsuarioJpaController().edit(usuario);

        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Usuario supervisorOld = persistentUsuario.getSupervisor();
            Usuario supervisorNew = usuario.getSupervisor();
            List<Grupo> grupoListOld = persistentUsuario.getGrupoList();
            List<Grupo> grupoListNew = usuario.getGrupoList();
            List<Rol> rolListOld = persistentUsuario.getRolList();
            List<Rol> rolListNew = usuario.getRolList();
            List<Caso> casoListOld = persistentUsuario.getCasoList();
            List<Caso> casoListNew = usuario.getCasoList();
            List<Documento> documentoListOld = persistentUsuario.getDocumentoList();
            List<Documento> documentoListNew = usuario.getDocumentoList();
            List<Usuario> usuarioListOld = persistentUsuario.getUsuarioList();
            List<Usuario> usuarioListNew = usuario.getUsuarioList();
            List<Nota> notaListOld = persistentUsuario.getNotaList();
            List<Nota> notaListNew = usuario.getNotaList();
            List<String> illegalOrphanMessages = null;
            for (Documento documentoListOldDocumento : documentoListOld) {
                if (!documentoListNew.contains(documentoListOldDocumento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Documento " + documentoListOldDocumento + " since its createdBy field is not nullable.");
                }
            }
            for (Nota notaListOldNota : notaListOld) {
                if (!notaListNew.contains(notaListOldNota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nota " + notaListOldNota + " since its creadaPor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (supervisorNew != null) {
                supervisorNew = em.getReference(supervisorNew.getClass(), supervisorNew.getIdUsuario());
                usuario.setSupervisor(supervisorNew);
            }

            List<Grupo> attachedGrupoListNew = new ArrayList<Grupo>();
            for (Grupo grupoListNewGrupoToAttach : grupoListNew) {
                grupoListNewGrupoToAttach = em.getReference(grupoListNewGrupoToAttach.getClass(), grupoListNewGrupoToAttach.getIdGrupo());
                attachedGrupoListNew.add(grupoListNewGrupoToAttach);
            }
            grupoListNew = attachedGrupoListNew;
            usuario.setGrupoList(grupoListNew);

            List<Rol> attachedRolListNew = new ArrayList<Rol>();
            for (Rol rolListNewRolToAttach : rolListNew) {
                rolListNewRolToAttach = em.getReference(rolListNewRolToAttach.getClass(), rolListNewRolToAttach.getIdRol());
                attachedRolListNew.add(rolListNewRolToAttach);
            }
            rolListNew = attachedRolListNew;
            usuario.setRolList(rolListNew);
            List<Caso> attachedCasoListNew = new ArrayList<Caso>();
            for (Caso casoListNewCasoToAttach : casoListNew) {
                casoListNewCasoToAttach = em.getReference(casoListNewCasoToAttach.getClass(), casoListNewCasoToAttach.getIdCaso());
                attachedCasoListNew.add(casoListNewCasoToAttach);
            }
            casoListNew = attachedCasoListNew;
            usuario.setCasoList(casoListNew);
            List<Documento> attachedDocumentoListNew = new ArrayList<Documento>();
            for (Documento documentoListNewDocumentoToAttach : documentoListNew) {
                documentoListNewDocumentoToAttach = em.getReference(documentoListNewDocumentoToAttach.getClass(), documentoListNewDocumentoToAttach.getIdDocumento());
                attachedDocumentoListNew.add(documentoListNewDocumentoToAttach);
            }
            documentoListNew = attachedDocumentoListNew;
            usuario.setDocumentoList(documentoListNew);

            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            usuario.setUsuarioList(usuarioListNew);
            List<Nota> attachedNotaListNew = new ArrayList<Nota>();
            for (Nota notaListNewNotaToAttach : notaListNew) {
                notaListNewNotaToAttach = em.getReference(notaListNewNotaToAttach.getClass(), notaListNewNotaToAttach.getIdNota());
                attachedNotaListNew.add(notaListNewNotaToAttach);
            }
            notaListNew = attachedNotaListNew;
            usuario.setNotaList(notaListNew);
            usuario = em.merge(usuario);
            if (supervisorOld != null && !supervisorOld.equals(supervisorNew)) {
                supervisorOld.getUsuarioList().remove(usuario);
                supervisorOld = em.merge(supervisorOld);
            }
            if (supervisorNew != null && !supervisorNew.equals(supervisorOld)) {
                supervisorNew.getUsuarioList().add(usuario);
                supervisorNew = em.merge(supervisorNew);
            }
            for (Grupo grupoListOldGrupo : grupoListOld) {
                if (!grupoListNew.contains(grupoListOldGrupo)) {
                    grupoListOldGrupo.getUsuarioList().remove(usuario);
                    grupoListOldGrupo = em.merge(grupoListOldGrupo);
                }
            }
            for (Grupo grupoListNewGrupo : grupoListNew) {
                if (!grupoListOld.contains(grupoListNewGrupo)) {
                    grupoListNewGrupo.getUsuarioList().add(usuario);
                    grupoListNewGrupo = em.merge(grupoListNewGrupo);
                }
            }
            for (Rol rolListOldRol : rolListOld) {
                if (!rolListNew.contains(rolListOldRol)) {
                    rolListOldRol.getUsuarioList().remove(usuario);
                    rolListOldRol = em.merge(rolListOldRol);
                }
            }
            for (Rol rolListNewRol : rolListNew) {
                if (!rolListOld.contains(rolListNewRol)) {
                    rolListNewRol.getUsuarioList().add(usuario);
                    rolListNewRol = em.merge(rolListNewRol);
                }
            }
            for (Caso casoListOldCaso : casoListOld) {
                if (!casoListNew.contains(casoListOldCaso)) {
                    casoListOldCaso.setOwner(null);
                    casoListOldCaso = em.merge(casoListOldCaso);
                }
            }
            for (Caso casoListNewCaso : casoListNew) {
                if (!casoListOld.contains(casoListNewCaso)) {
                    Usuario oldOwnerOfCasoListNewCaso = casoListNewCaso.getOwner();
                    casoListNewCaso.setOwner(usuario);
                    casoListNewCaso = em.merge(casoListNewCaso);
                    if (oldOwnerOfCasoListNewCaso != null && !oldOwnerOfCasoListNewCaso.equals(usuario)) {
                        oldOwnerOfCasoListNewCaso.getCasoList().remove(casoListNewCaso);
                        oldOwnerOfCasoListNewCaso = em.merge(oldOwnerOfCasoListNewCaso);
                    }
                }
            }
            for (Documento documentoListNewDocumento : documentoListNew) {
                if (!documentoListOld.contains(documentoListNewDocumento)) {
                    Usuario oldCreatedByOfDocumentoListNewDocumento = documentoListNewDocumento.getCreatedBy();
                    documentoListNewDocumento.setCreatedBy(usuario);
                    documentoListNewDocumento = em.merge(documentoListNewDocumento);
                    if (oldCreatedByOfDocumentoListNewDocumento != null && !oldCreatedByOfDocumentoListNewDocumento.equals(usuario)) {
                        oldCreatedByOfDocumentoListNewDocumento.getDocumentoList().remove(documentoListNewDocumento);
                        oldCreatedByOfDocumentoListNewDocumento = em.merge(oldCreatedByOfDocumentoListNewDocumento);
                    }
                }
            }

            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.setSupervisor(null);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    Usuario oldSupervisorOfUsuarioListNewUsuario = usuarioListNewUsuario.getSupervisor();
                    usuarioListNewUsuario.setSupervisor(usuario);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                    if (oldSupervisorOfUsuarioListNewUsuario != null && !oldSupervisorOfUsuarioListNewUsuario.equals(usuario)) {
                        oldSupervisorOfUsuarioListNewUsuario.getUsuarioList().remove(usuarioListNewUsuario);
                        oldSupervisorOfUsuarioListNewUsuario = em.merge(oldSupervisorOfUsuarioListNewUsuario);
                    }
                }
            }
            for (Nota notaListNewNota : notaListNew) {
                if (!notaListOld.contains(notaListNewNota)) {
                    Usuario oldCreadaPorOfNotaListNewNota = notaListNewNota.getCreadaPor();
                    notaListNewNota.setCreadaPor(usuario);
                    notaListNewNota = em.merge(notaListNewNota);
                    if (oldCreadaPorOfNotaListNewNota != null && !oldCreadaPorOfNotaListNewNota.equals(usuario)) {
                        oldCreadaPorOfNotaListNewNota.getNotaList().remove(notaListNewNota);
                        oldCreadaPorOfNotaListNewNota = em.merge(oldCreadaPorOfNotaListNewNota);
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
                String id = usuario.getIdUsuario();
                if (find(Usuario.class, id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }

    }

//    public void mergeUsuarioLight(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
//        EntityManager em = null;
//        try {
//            utx.begin();
//            em = getEntityManager();
//
//            usuario = em.merge(usuario);
//
//            utx.commit();
//        } catch (Exception ex) {
//            try {
//                utx.rollback();
//            } catch (Exception re) {
//                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
//            }
//            String msg = ex.getLocalizedMessage();
//            if (msg == null || msg.length() == 0) {
//                String id = usuario.getIdUsuario();
//
//                if (em.find(Usuario.class, id) == null) {
//                    throw new NonexistentEntityException(
//                            "The usuario with id " + id + " no longer exists.");
//                }
//            }
//            throw ex;
//        } finally {
//            if (em != null) {
//                em.close();
//            }
//        }
//    }
    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.email = :email</code>
     *
     * @param email
     * @return List<Usuario>
     */
    public List<Usuario> getUsuarioFindByEmail(String email) {
        if (email == null) {
            return null;
        }
        return getEntityManager().createNamedQuery("Usuario.findByEmail").setParameter("email", email).getResultList();
    }

    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.rut = :rut</code>
     *
     * @param rut
     * @return
     */
    public List<Usuario> getUsuarioFindByRut(String rut) {
        return (List<Usuario>) getEntityManager().createNamedQuery("Usuario.findByRut").setParameter("rut", rut).getResultList();
    }

    public Long countAttachmentWOContentId(Caso caso) {
//        return getAttachmentJpaController().countAttachmentsWOContentId(caso);
        EasyCriteriaQuery<Attachment> easyCriteriaQuery = new EasyCriteriaQuery<>(this, Attachment.class);
        easyCriteriaQuery.addEqualPredicate(Attachment_.contentId.getName(), (Attachment) null);
        easyCriteriaQuery.addEqualPredicate(Attachment_.idCaso.getName(), caso);
        return easyCriteriaQuery.count();
    }

    public Long nextVal(String seq) throws PreexistingEntityException, RollbackFailureException, Exception {
        return (Long) getEntityManager().createNativeQuery("select nextval(?)").setParameter(1, seq).getSingleResult();
    }

    /**
     * <
     * code>SELECT s FROM SubEstadoCaso s WHERE s.nombre = :nombre</code>
     */
    public List<SubEstadoCaso> getSubEstadoCasofindByIdEstado(String idEstado) {
        return getEntityManager().createNamedQuery("SubEstadoCaso.findByIdEstado").setParameter("idEstado", idEstado).getResultList();
    }

    public List<SubEstadoCaso> getSubEstadoCasofindByIdEstadoAndTipoCaso(EstadoCaso idEstado, TipoCaso tipo) {
        return getEntityManager().createNamedQuery("SubEstadoCaso.findByIdEstadoTipoCaso").setParameter("idEstado", idEstado).setParameter("tipoCaso", tipo).getResultList();
    }

    /**
     * <
     * code>SELECT p FROM Producto p WHERE p.nombre = :nombre</code>
     */
    public Producto getProductoFindByNombre(String nombre) {
        EasyCriteriaQuery<Producto> ecq = new EasyCriteriaQuery<Producto>(this, Producto.class);
        ecq.addLikePredicate("nombre", nombre);
        return ecq.getSingleResult();
        //return (Producto) getEntityManager().createNamedQuery("Producto.findByNombre").setParameter("nombre", nombre).getSingleResult();
    }

    public List<ReglaTrigger> getReglaTriggerFindByEvento(String event) {
        return getEntityManager().createNamedQuery("ReglaTrigger.findByEvento").setParameter("evento", event).getResultList();
    }

    public List<Caso> getCasoFindByEstadoAndAlerta(EstadoCaso estado, TipoAlerta tipoAlerta) {
//        return getCasoJpa().getCasoFindByEstadoAndAlerta(estado, tipoAlerta);
        EasyCriteriaQuery<Caso> easyquery = new EasyCriteriaQuery<>(this, Caso.class);
        easyquery.addEqualPredicate(Caso_.idEstado.getName(), estado);
        easyquery.addEqualPredicate(Caso_.estadoAlerta.getName(), tipoAlerta);
        return easyquery.getAllResultList();
    }

    public List<Item> getItemFindByNombreLike(String nombre) {
        EasyCriteriaQuery<Item> ecq = new EasyCriteriaQuery<>(this, Item.class);
        ecq.addLikePredicate(Item_.nombre.getName(), '%' + nombre + '%');
        return ecq.getAllResultList();
//        return getEntityManager().createNamedQuery("Item.findByNombreLike").setParameter("nombre", "%" + nombre + "%").getResultList();
    }

    public Caso getCasoFindByEmailCreationTimeAndType(String email, Date creationTime, TipoCaso tipoCaso) {
        EasyCriteriaQuery<Caso> ecq = new EasyCriteriaQuery<>(this, Caso.class);
        ecq.addLikePredicate("emailCliente.emailCliente", email);
        ecq.addEqualPredicate(Caso_.fechaCreacion.getName(), creationTime);
        ecq.addDistinctPredicate("tipoCaso", tipoCaso);
        return ecq.getSingleResult();
    }

    public List<EmailCliente> getEmailClienteFindByEmailLike(String id, int maxResults) {
        return findEmailClienteEntitiesLike(id, maxResults, 0);
    }

    private List<EmailCliente> findEmailClienteEntitiesLike(String searchPart, int maxResults, int firstResult) {
        return findEmailClienteEntitiesLike(searchPart, false, maxResults, firstResult);
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

    public List<FieldType> getCustomFieldTypes() {
        return getEntityManager().createNamedQuery("FieldType.findByIsCustomField").setParameter("isCustomField", Boolean.TRUE).getResultList();
    }

    public void setCasoChangeListener(CasoChangeListener casoChangeListener) {
        this.casoChangeListener = casoChangeListener;
    }

    public CasoChangeListener getCasoChangeListener() {
        return casoChangeListener;
    }

    /**
     * @param filtro
     * @return the vistaJpaController
     */
    @Override
    protected Predicate createSpecialPredicate(FiltroVista filtro) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean isThereSpecialFiltering(FiltroVista filtro) {
        return false;
    }

    public void createOrMergeProducto(Producto producto) throws RollbackFailureException, Exception {
//        if (producto.getCasoList() == null) {
//            producto.setCasoList(new ArrayList<Caso>());
//        }
        if (producto.getComponenteList() == null) {
            producto.setComponenteList(new ArrayList<Componente>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();

            List<Componente> attachedComponenteList = new ArrayList<>();
            System.out.println("producto.getComponenteList():" + producto.getComponenteList());
            for (Componente componente : producto.getComponenteList()) {
                Componente persistentComponent = em.find(componente.getClass(), componente.getIdComponente());
                if (persistentComponent == null) {
                    //component do not exist!
                    System.out.println("(Persist) component do not exist in local DB: " + componente);
                    em.persist(componente);
                    List<SubComponente> subComponentList = new ArrayList<>();
                    System.out.println("componente.getSubComponenteList():" + componente.getSubComponenteList());
                    for (SubComponente subComponent : componente.getSubComponenteList()) {
                        SubComponente persistentSubComponent = em.find(SubComponente.class, subComponent.getIdSubComponente());
                        if (persistentSubComponent == null) {
                            //sub component do not exist!
                            System.out.println("(Persist) sub component do not exist!:" + subComponent);
                            em.persist(subComponent);
                        }
                        subComponentList.add(subComponent);
                    }
                    componente.setSubComponenteList(subComponentList);
                    componente = em.merge(componente);
                }
                attachedComponenteList.add(componente);
            }
            producto.setComponenteList(attachedComponenteList);

            if (find(Producto.class, producto.getIdProducto()) != null) {
                //exists
                em.merge(producto);
            } else {
                em.persist(producto);
            }

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

    public List<Usuario> findUsuariosEntitiesLike(String searchPart, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Usuario> criteriaQuery = criteriaBuilder.createQuery(Usuario.class);
            Root<Usuario> from = criteriaQuery.from(Usuario.class);
            Expression<String> exp1 = from.get("email");
            Expression<String> exp2 = from.get("nombres");
            Expression<String> exp3 = from.get("apellidos");
            Expression<String> exp4 = from.get("idUsuario");

            Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal("%" + (String) searchPart + "%"));
            Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.upper(exp1), literal);
            Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.upper(exp2), literal);
            Predicate predicate3 = criteriaBuilder.like(criteriaBuilder.upper(exp3), literal);
            Predicate predicate4 = criteriaBuilder.like(criteriaBuilder.upper(exp4), literal);

            criteriaQuery.where(criteriaBuilder.or(predicate1, predicate2, predicate3, predicate4));

            TypedQuery<Usuario> typedQuery = em.createQuery(criteriaQuery);
            if (!all) {
                typedQuery.setMaxResults(maxResults);
                typedQuery.setFirstResult(firstResult);
            }
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Cliente> findClientesEntitiesLike(String searchPart, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
            Root<Cliente> from = criteriaQuery.from(Cliente.class);
            Expression<String> exp1 = from.get("rut");
            Expression<String> exp2 = from.get("nombres");
            Expression<String> exp3 = from.get("apellidos");

            Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal("%" + (String) searchPart + "%"));
            Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.upper(exp1), literal);
            Predicate predicate2 = criteriaBuilder.like(criteriaBuilder.upper(exp2), literal);
            Predicate predicate3 = criteriaBuilder.like(criteriaBuilder.upper(exp3), literal);

            criteriaQuery.where(criteriaBuilder.or(predicate1, predicate2, predicate3));

            TypedQuery<Cliente> typedQuery = em.createQuery(criteriaQuery);
            if (!all) {
                typedQuery.setMaxResults(maxResults);
                typedQuery.setFirstResult(firstResult);
            }
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Resource> findResourcesEntitiesLike(String searchPart, boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Resource> criteriaQuery = criteriaBuilder.createQuery(Resource.class);
            Root<Resource> from = criteriaQuery.from(Resource.class);
            Expression<String> exp1 = from.get("nombre");

            Expression<String> literal = criteriaBuilder.upper(criteriaBuilder.literal("%" + (String) searchPart + "%"));
            Predicate predicate1 = criteriaBuilder.like(criteriaBuilder.upper(exp1), literal);

            criteriaQuery.where(criteriaBuilder.or(predicate1));

            TypedQuery<Resource> typedQuery = em.createQuery(criteriaQuery);
            if (!all) {
                typedQuery.setMaxResults(maxResults);
                typedQuery.setFirstResult(firstResult);
            }
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Vista> findVistaEntitiesVisibleForAll(String baseEntityType) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Vista> criteriaQuery = criteriaBuilder.createQuery(Vista.class);
            Root<Vista> root = criteriaQuery.from(Vista.class);
            //Add Criteria here
            Predicate predicate = criteriaBuilder.equal(root.get("visibleToAll"), Boolean.TRUE);
            Predicate predicate2 = criteriaBuilder.equal(root.get("baseEntityType"), baseEntityType);
            predicate = CriteriaQueryHelper.addPredicate(predicate, predicate2, criteriaBuilder);

            criteriaQuery.where(predicate);
            //Get Results
            Query q = em.createQuery(criteriaQuery);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Vista> findVistaEntitiesCreatedByUser(Usuario user, String baseEntityType) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Vista> criteriaQuery = criteriaBuilder.createQuery(Vista.class);
            Root<Vista> root = criteriaQuery.from(Vista.class);
            //Add Criteria here
            Predicate predicate = criteriaBuilder.equal(root.get("idUsuarioCreadaPor"), user);
            Predicate predicate2 = criteriaBuilder.equal(root.get("visibleToAll"), Boolean.FALSE);
            Predicate predicate3 = criteriaBuilder.isNull(root.get("idGrupo"));
            Predicate predicate4 = criteriaBuilder.isNull(root.get("idArea"));
            Predicate predicate5 = criteriaBuilder.equal(root.get("baseEntityType"), baseEntityType);

            predicate = CriteriaQueryHelper.addPredicate(predicate, predicate2, criteriaBuilder);
            predicate = CriteriaQueryHelper.addPredicate(predicate, predicate3, criteriaBuilder);
            predicate = CriteriaQueryHelper.addPredicate(predicate, predicate4, criteriaBuilder);
            predicate = CriteriaQueryHelper.addPredicate(predicate, predicate5, criteriaBuilder);

            criteriaQuery.where(predicate);
            //Get Results
            Query q = em.createQuery(criteriaQuery);

            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Vista> findVistaEntitiesVisibleForGroupsOfUser(Usuario user, String baseEntityType) {
        if (user == null) {
            return null;
        }

        EntityManager em = getEntityManager();
        try {
            List<String> gruposUser = new ArrayList<>();
            for (Grupo g : user.getGrupoList()) {
                if (!gruposUser.contains(g.getIdGrupo())) {
                    gruposUser.add(g.getIdGrupo());
                }
            }
            if (!gruposUser.isEmpty()) {
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Vista> criteriaQuery = criteriaBuilder.createQuery(Vista.class);
                Root<Vista> root = criteriaQuery.from(Vista.class);
                //Add Criteria here
//                Predicate predicate = criteriaBuilder.isTrue(root.get("idGrupo").get("idGrupo").in(gruposUser));
                Predicate predicate = criteriaBuilder.equal(root.get("idGrupo").get("idGrupo").in(gruposUser), Boolean.TRUE);
                Predicate predicate2 = criteriaBuilder.equal(root.get("baseEntityType"), baseEntityType);
                predicate = CriteriaQueryHelper.addPredicate(predicate, predicate2, criteriaBuilder);

                criteriaQuery.where(predicate);
//            Expression<Date> expresion = root.get("visibleToAll");
                //Get Results
                Query q = em.createQuery(criteriaQuery);
                return q.getResultList();
            } else {
                return null;
            }

        } finally {
            em.close();
        }
    }

    public List<Vista> findVistaEntitiesVisibleForAreasOfUser(Usuario user, String baseEntityType) {
        if (user == null) {
            return null;
        }
        EntityManager em = getEntityManager();
        try {
            List<String> areasUser = new ArrayList<>();
            for (Grupo g : user.getGrupoList()) {
                if (!areasUser.contains(g.getIdArea().getIdArea())) {
                    areasUser.add(g.getIdArea().getIdArea());
                }
            }
            if (!areasUser.isEmpty()) {
                CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
                CriteriaQuery<Vista> criteriaQuery = criteriaBuilder.createQuery(Vista.class);
                Root<Vista> root = criteriaQuery.from(Vista.class);
                //Add Criteria here
//                Predicate predicate = criteriaBuilder.isTrue(root.get("idArea").get("idArea").in(areasUser));
                Predicate predicate = criteriaBuilder.equal(root.get("idArea").get("idArea").in(areasUser), Boolean.TRUE);
                Predicate predicate2 = criteriaBuilder.equal(root.get("baseEntityType"), baseEntityType);
                predicate = CriteriaQueryHelper.addPredicate(predicate, predicate2, criteriaBuilder);

                criteriaQuery.where(predicate);
//            Expression<Date> expresion = root.get("visibleToAll");
                //Get Results
                Query q = em.createQuery(criteriaQuery);
                return q.getResultList();
            } else {
                return null;
            }

        } finally {
            em.close();
        }
    }

    public List<Canal> findCanalTipoEmail() {
        EasyCriteriaQuery<Canal> ecq = new EasyCriteriaQuery<>(this, Canal.class);
        ecq.addEqualPredicate("idTipoCanal", EnumTipoCanal.EMAIL.getTipoCanal());
        return ecq.getAllResultList();
    }

}
