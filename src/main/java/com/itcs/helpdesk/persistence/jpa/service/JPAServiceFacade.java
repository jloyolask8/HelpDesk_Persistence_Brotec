package com.itcs.helpdesk.persistence.jpa.service;

import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.AppSetting;
import com.itcs.helpdesk.persistence.entities.Archivo;
import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Attachment;
import com.itcs.helpdesk.persistence.entities.AuditLog;
import com.itcs.helpdesk.persistence.entities.Canal;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Caso_;
import com.itcs.helpdesk.persistence.entities.Categoria;
import com.itcs.helpdesk.persistence.entities.Cliente;
import com.itcs.helpdesk.persistence.entities.Componente;
import com.itcs.helpdesk.persistence.entities.Condicion;
import com.itcs.helpdesk.persistence.entities.Documento;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.Etiqueta;
import com.itcs.helpdesk.persistence.entities.FieldType;
import com.itcs.helpdesk.persistence.entities.FiltroVista;
import com.itcs.helpdesk.persistence.entities.Funcion;
import com.itcs.helpdesk.persistence.entities.Grupo;
import com.itcs.helpdesk.persistence.entities.Item;
import com.itcs.helpdesk.persistence.entities.Item_;
import com.itcs.helpdesk.persistence.entities.NombreAccion;
import com.itcs.helpdesk.persistence.entities.Nota;
import com.itcs.helpdesk.persistence.entities.Prioridad;
import com.itcs.helpdesk.persistence.entities.Producto;
import com.itcs.helpdesk.persistence.entities.Recinto;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import com.itcs.helpdesk.persistence.entities.Resource;
import com.itcs.helpdesk.persistence.entities.Rol;
import com.itcs.helpdesk.persistence.entities.Sesiones;
import com.itcs.helpdesk.persistence.entities.SubComponente;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.entities.TipoCaso;
import com.itcs.helpdesk.persistence.entities.TipoComparacion;
import com.itcs.helpdesk.persistence.entities.TipoNota;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Vista;
import com.itcs.helpdesk.persistence.entityenums.EnumEstadoCaso;
import com.itcs.helpdesk.persistence.jpa.AbstractJPAController;
import com.itcs.helpdesk.persistence.jpa.AccionJpaController;
import com.itcs.helpdesk.persistence.jpa.AppSettingJpaController;
import com.itcs.helpdesk.persistence.jpa.ArchivoJpaController;
import com.itcs.helpdesk.persistence.jpa.AreaJpaController;
import com.itcs.helpdesk.persistence.jpa.AttachmentJpaController;
import com.itcs.helpdesk.persistence.jpa.CanalJpaController;
import com.itcs.helpdesk.persistence.jpa.CanalSettingJpaController;
import com.itcs.helpdesk.persistence.jpa.CategoriaJpaController;
import com.itcs.helpdesk.persistence.jpa.ClienteJpaController;
import com.itcs.helpdesk.persistence.jpa.ClippingJpaController;
import com.itcs.helpdesk.persistence.jpa.ComponenteJpaController;
import com.itcs.helpdesk.persistence.jpa.CondicionJpaController;
import com.itcs.helpdesk.persistence.jpa.DocumentoJpaController;
import com.itcs.helpdesk.persistence.jpa.EstadoCasoJpaController;
import com.itcs.helpdesk.persistence.jpa.FiltroVistaJpaController;
import com.itcs.helpdesk.persistence.jpa.FuncionJpaController;
import com.itcs.helpdesk.persistence.jpa.GrupoJpaController;
import com.itcs.helpdesk.persistence.jpa.ItemJpaController;
import com.itcs.helpdesk.persistence.jpa.NombreAccionJpaController;
import com.itcs.helpdesk.persistence.jpa.NotaJpaController;
import com.itcs.helpdesk.persistence.jpa.PrioridadJpaController;
import com.itcs.helpdesk.persistence.jpa.ProductoJpaController;
import com.itcs.helpdesk.persistence.jpa.RecintoJpaController;
import com.itcs.helpdesk.persistence.jpa.ReglaTriggerJpaController;
import com.itcs.helpdesk.persistence.jpa.RolJpaController;
import com.itcs.helpdesk.persistence.jpa.SesionesJpaController;
import com.itcs.helpdesk.persistence.jpa.SubComponenteJpaController;
import com.itcs.helpdesk.persistence.jpa.SubEstadoCasoJpaController;
import com.itcs.helpdesk.persistence.jpa.TipoAlertaJpaController;
import com.itcs.helpdesk.persistence.jpa.TipoComparacionJpaController;
import com.itcs.helpdesk.persistence.jpa.TipoNotaJpaController;
import com.itcs.helpdesk.persistence.jpa.UsuarioJpaController;
import com.itcs.helpdesk.persistence.jpa.VistaJpaController;
import com.itcs.helpdesk.persistence.jpa.custom.AuditLogJpaCustomController;
import com.itcs.helpdesk.persistence.jpa.custom.CasoJPACustomController;
import com.itcs.helpdesk.persistence.jpa.custom.EmailClienteJpaCustomController;
import com.itcs.helpdesk.persistence.jpa.custom.UsuarioJpaCustomController;
import com.itcs.helpdesk.persistence.jpa.exceptions.IllegalOrphanException;
import com.itcs.helpdesk.persistence.jpa.exceptions.NonexistentEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import com.itcs.helpdesk.persistence.utils.CasoChangeListener;
import com.itcs.helpdesk.persistence.utils.ConstraintViolationExceptionHelper;
import com.itcs.helpdesk.persistence.utils.OrderBy;
import com.itcs.helpdesk.persistence.utils.vo.AuditLogVO;
import com.itcs.jpautils.EasyCriteriaQuery;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.resource.NotSupportedException;
import javax.transaction.UserTransaction;

public class JPAServiceFacade extends AbstractJPAController {

    public static final String CASE_CUSTOM_FIELD = "case";
    public static final String CLIENT_CUSTOM_FIELD = "client";

    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;
    //--------------specific controllers wrapped by this service------------------------
    private CasoJPACustomController casoJpa;
    private AuditLogJpaCustomController auditLogJpa;
    private UsuarioJpaController usuarioJpaController;
    private UsuarioJpaCustomController usuarioJpaCustomController;
    private RolJpaController rolJpaController;
    private NotaJpaController notaJpaController;
    private EmailClienteJpaCustomController emailClienteJpaController;
    private ClienteJpaController clienteJpaController;
    private PrioridadJpaController prioridadJpaController;
    private GrupoJpaController grupoJpaController;
    private CategoriaJpaController categoriaJpaController;
    private AreaJpaController areaJpaController;
    private CanalJpaController canalJpaController;
    private TipoNotaJpaController tipoNotaJpaController;
    private AccionJpaController accionJpaController;
    private ArchivoJpaController archivoJpaController;
    private AttachmentJpaController attachmentJpaController;
    private ComponenteJpaController componenteJpaController;
    private CondicionJpaController condicionJpaController;
    private DocumentoJpaController documentoJpaController;
    private EstadoCasoJpaController estadoCasoJpaController;
    private FuncionJpaController funcionJpaController;
    private NombreAccionJpaController nombreAccionJpaController;
    private ProductoJpaController productoJpaController;
    private ReglaTriggerJpaController reglaTriggerJpaController;
    private SubComponenteJpaController subComponenteJpaController;
    private SubEstadoCasoJpaController subEstadoCasoJpaController;
    private TipoAlertaJpaController tipoAlertaJpaController;
    private VistaJpaController vistaJpaController;
    private FiltroVistaJpaController filtroVistaJpaController;
    private AppSettingJpaController appSettingJpaController;
    private TipoComparacionJpaController tipoComparacionJpaController;
    private SesionesJpaController sesionesJpaController;
    private ClippingJpaController clippingJpaController;
    //--------------end of specific controllers wrapped by this service------------------------
    private CasoChangeListener casoChangeListener;
    private ItemJpaController itemJpaController;
    private RecintoJpaController recintoJpaController;
    private CanalSettingJpaController canalSettingJpaController;

    public JPAServiceFacade(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }

    @Override
    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public <T extends Object> T find(Class<T> entityClass, Object id) {
        return getEntityManager().find(entityClass, id);
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

    public void persistInTx(Object o) throws Exception {
        EntityManager em = null;//
        try {
            em = getEntityManager();
            em.persist(o);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void remove(Class clazz, Object o) throws Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            //em.remove(emf.getPersistenceUnitUtil().getIdentifier(o));
            em.remove(em.getReference(clazz, emf.getPersistenceUnitUtil().getIdentifier(o)));
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

    public void mergeInTx(Object o) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.merge(o);
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
     * <
     * code>SELECT count(*) from o</code>
     */
    public Long count(Class entityClass) {
        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, entityClass);
        return q.count();
    }

    public Long countEntities(Vista vista) throws ClassNotFoundException {
        return countEntities(vista, null);
    }

    public Long countEntities(Vista vista, Usuario who) throws ClassNotFoundException {
        EntityManager em = getEntityManager();
        em.setProperty("javax.persistence.cache.storeMode", javax.persistence.CacheRetrieveMode.USE);

        try {
            final Class<?> clazz = Class.forName(vista.getBaseEntityType());
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = em.getCriteriaBuilder().createQuery();
            Root root = criteriaQuery.from(clazz);
            Predicate predicate = createPredicate(em, criteriaBuilder, root, vista, who);

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

    public List<?> findAllEntities(Class entityClass, Vista vista, OrderBy orderBy, Usuario who) throws IllegalStateException, NotSupportedException, ClassNotFoundException {
        return findEntities(entityClass, vista, true, -1, -1, orderBy, who);
    }

    public List<?> findEntities(Class entityClass, Vista vista, int maxResults, int firstResult, OrderBy orderBy, Usuario who) throws IllegalStateException, NotSupportedException, ClassNotFoundException {
        return findEntities(entityClass, vista, false, maxResults, firstResult, orderBy, who);
    }

    private List<?> findEntities(Class entityClass, Vista vista, boolean all, int maxResults, int firstResult, OrderBy orderBy, Usuario who) throws IllegalStateException, ClassNotFoundException {
        EntityManager em = getEntityManager();

        try {

            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClass);
            Root root = criteriaQuery.from(entityClass);

            Predicate predicate = createPredicate(em, criteriaBuilder, root, vista, who);

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

    public Long countCasosByCreatedBetween(Date from, Date to) {
        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, Caso.class);
        q.addBetweenPredicate(Caso_.fechaCreacion, from, to);

        return q.count();
    }

    public Long countCasosByCreatedBetween(Date from, Date to, Usuario owner) {
        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, Caso.class);
        q.addBetweenPredicate(Caso_.fechaCreacion, from, to);
        if (owner
                != null) {
            q.addEqualPredicate("owner", owner);
        }

        return q.count();
    }

    public Caso findCasoByIdEmailCliente(String email, Long idCaso) {
        return getCasoJpa().findCasoByIdEmailCliente(email, idCaso);
    }

//    public Long countByCreatedBetween(Class entityClass, Date from, Date to, Area idArea, Grupo idGrupo, Usuario owner) {
//        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, entityClass);
//        q.addBetweenPredicate(Caso_.fechaCreacion, from, to);
//        if (owner != null) {
//            q.addEqualPredicate("owner", owner);
//        } else if (idGrupo != null) {
//            q.addEqualPredicate("owner.idGrupo", idGrupo);
//        } else if (idArea != null) {
//            q.addEqualPredicate("owner.idGrupo.idArea", idArea);
//        }
//        return q.count();
//    }
    public Long countCasosByClosedBetween(Date from, Date to) {
        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, Caso.class);
        q.addBetweenPredicate(Caso_.fechaCierre, from, to);
        q.addEqualPredicate("idEstado", EnumEstadoCaso.CERRADO.getEstado());
        return q.count();
    }

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

//    public List<Etiqueta> findEtiquetasLike(String etiquetaPattern) {
//        EntityManager em = getEntityManager();
//        try {
//
//            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<Etiqueta> criteriaQuery = criteriaBuilder.createQuery(Etiqueta.class);
//            Root<Etiqueta> root = criteriaQuery.from(Etiqueta.class);
//            Expression<String> exp = root.get("tagId");
//
//            criteriaQuery = criteriaQuery.orderBy(criteriaBuilder.desc(root.get("tagId")));
//            Predicate predicate = criteriaBuilder.like(criteriaBuilder.lower(exp), etiquetaPattern.toLowerCase() + "%");
//            criteriaQuery.where(predicate);
//            Query q = em.createQuery(criteriaQuery);
//            return q.getResultList();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Collections.EMPTY_LIST;
//
//        } finally {
//            em.close();
//        }
//    }
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

    public Long countCasosByClosedBetween(Date from, Date to/*, Area idArea, ,Grupo idGrupo*/, Usuario owner) {
        EasyCriteriaQuery q = new EasyCriteriaQuery(emf, Caso.class);
        q.addBetweenPredicate(Caso_.fechaCierre, from, to);

        q.addEqualPredicate(
                "idEstado", EnumEstadoCaso.CERRADO.getEstado());

        if (owner
                != null) {
            q.addEqualPredicate("owner", owner);
        }

        return q.count();
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
//    public Object queryByRange(String jpqlStmt, int firstResult, int maxResults) {
//        Query query = getEntityManager().createQuery(jpqlStmt);
//        if (firstResult > 0) {
//            query = query.setFirstResult(firstResult);
//        }
//        if (maxResults > 0) {
//            query = query.setMaxResults(maxResults);
//        }
//        return query.getResultList();
//    }
    /**
     * @deprecated 
     * @param entityClazz
     * @param maxResults
     * @param firstResult
     * @return 
     */
    public List queryByRange(Class entityClazz, int maxResults, int firstResult) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(entityClazz));
//            Root root = cq.from(entityClazz);
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(entityClazz);
            Query q = em.createQuery(criteriaQuery);

            q.setMaxResults(maxResults);
            q.setFirstResult(firstResult);

            return q.getResultList();
        } finally {
            em.close();
        }
    }

//
//    public List queryByRange(Class entityClazz, int firstResult, int maxResults) {
//
//        EasyCriteriaQuery easyCQ = new EasyCriteriaQuery(getEntityManagerFactory(), entityClazz);
//        return easyCQ.queryByRange(entityClazz, firstResult, maxResults);
//
//    }
    /*
     * Caso helper Methods
     */
    public List<Caso> findCasoEntities(Vista view, Usuario userWhoIsApplying, int maxResults, int firstResult, OrderBy orderBy) throws IllegalStateException, javax.resource.NotSupportedException, ClassNotFoundException {
        return findCasoEntities(view, userWhoIsApplying, false, maxResults, firstResult, orderBy);
    }

    public List<Caso> findCasoEntities(Vista view, Usuario userWhoIsApplying, OrderBy orderBy) throws IllegalStateException, ClassNotFoundException {
        return findCasoEntities(view, userWhoIsApplying, true, 0, 0, orderBy);
    }

    public int countCasoEntities(Vista view, Usuario userWhoIsApplying) throws javax.resource.NotSupportedException, ClassNotFoundException {
        return countEntities(view, userWhoIsApplying).intValue();
    }

    private List<Caso> findCasoEntities(Vista view, Usuario userWhoIsApplying, boolean all, int maxResults, int firstResult, OrderBy orderBy) throws IllegalStateException, ClassNotFoundException {
        return (List<Caso>) findEntities(Caso.class, view, all, maxResults, firstResult, orderBy, userWhoIsApplying);

    }

    public int getCasoCount(Usuario usuario, TipoAlerta tipo_alerta) {
        return getCasoJpa().getCasoCountByTipoAlerta(usuario, tipo_alerta);
    }

    public int getCasoCountOpen(Usuario usuario) {
        return getCasoJpa().getCasoCountByEstado(usuario, EnumEstadoCaso.ABIERTO.getEstado());
    }

    public int getCasoCountClosed(Usuario usuario) {
        return getCasoJpa().getCasoCountByEstado(usuario, EnumEstadoCaso.CERRADO.getEstado());
    }

    public int getCasoCountActualizados(Usuario usuario) {
        return getCasoJpa().getCountCasosActualizados(usuario);
    }

//    public int countByCatFilter(Categoria cat, Usuario user) {
//        return getCasoJpa().countCasoCatFilter(cat, user);
//    }
    public void persistCaso(Caso caso, List<AuditLog> changeList) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (caso.getAttachmentList() != null && !caso.getAttachmentList().isEmpty()) {
            caso.setHasAttachments(true);
        }
        if (caso.getScheduleEventList() != null && !caso.getScheduleEventList().isEmpty()) {
            caso.setHasScheduledEvents(true);
        }
        getCasoJpa().create(caso);
        if (changeList != null) {
            for (AuditLog auditLog : changeList) {
                auditLog.setIdCaso(caso.getIdCaso());
                persistAuditLog(auditLog);
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
        List<AuditLog> changeList = new LinkedList<AuditLog>();
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
                persistAuditLog(auditLog);
            }
        }
        if (notifyListeners) {
            notifyCasoEventListeners(caso, false, changeList);
        }

        return (Caso) getCasoJpa().getEntityManager().getReference(Caso.class, caso.getIdCaso());
    }

    /**
     * Notify to all event listeners
     * @param caso caso que cambia
     * @param create true if is a caso creation, or false is update
     * @param changeList list of changes
    */
    public void notifyCasoEventListeners(Caso caso, boolean create, List<AuditLog> changeList) {
        if (getCasoChangeListener() != null) {
            if(create)
            {
                getCasoChangeListener().casoCreated(caso);
            }
            else
            {
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

    public void removeCaso(Caso caso) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        getCasoJpa().destroy(caso.getIdCaso());
    }

    /**
     * <
     * code>SELECT c FROM Caso c</code>
     */
    public List<Caso> getCasoFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getCasoJpa().findCasoEntities();
    }

    /**
     * <
     * code>SELECT c FROM Caso c WHERE c.idCaso = :idCaso</code>
     */
    public Caso getCasoFindByIdCaso(Long idCaso) {
        return getCasoJpa().findCaso(idCaso);
    }

    /**
     * <
     * code>SELECT c FROM Caso c WHERE c.emailCliente = :emailCliente</code>
     */
    public List<Caso> getCasoFindByEmailCliente(String emailCliente) {
        return getCasoJpa().findByEmailCliente(emailCliente);
    }

    /**
     * <
     * code>SELECT c FROM Caso c WHERE c.rutCliente = :rutCliente</code>
     */
    public List<Caso> getCasoFindByRutCliente(String rutCliente) {
        return getCasoJpa().findByRutCliente(rutCliente);
    }

    /*
     * Audit Log Methods
     */
    public int getAuditLogCount(AuditLogVO alert, boolean log) {
        alert.setAlertLevel(!log);
        return getAuditLogJpaCustomController().countByFilterForAudit(alert);
    }

    /*
     * Audit Log Methods
     */
    public List<AuditLog> findAuditLogEntities(AuditLogVO alert) {
        return getAuditLogJpaCustomController().findAuditLogEntities(true, -1, -1, alert, true);
    }

    /*
     * Audit Log Methods
     */
    public List<AuditLog> findAuditLogEntities(int maxResults, int firstResult, AuditLogVO alert, boolean log) {
        return getAuditLogJpaCustomController().findAuditLogEntities(false, maxResults, firstResult, alert, log);
    }

//    /**
//     * <
//     * code>SELECT c FROM CampoCompCaso c WHERE c.tipo = :tipo</code>
//     */
//    public List<CampoCompCaso> getCampoCompCasoFindByTipo(String tipo) {
//        return getEntityManager().createNamedQuery("CampoCompCaso.findByTipo").setParameter("tipo", tipo).getResultList();
//    }
//    /**
//     * <
//     * code>SELECT c FROM CampoCompCaso c WHERE c.nombreTablaValores =
//     * :nombreTablaValores</code>
//     */
//    public List<CampoCompCaso> getCampoCompCasoFindByNombreTablaValores(String nombreTablaValores) {
//        return getEntityManager().createNamedQuery("CampoCompCaso.findByNombreTablaValores").setParameter("nombreTablaValores", nombreTablaValores).getResultList();
//    }
//    /**
//     * <
//     * code>SELECT c FROM CampoCompCaso c WHERE c.nombreColValor =
//     * :nombreColValor</code>
//     */
//    public List<CampoCompCaso> getCampoCompCasoFindByNombreColValor(String nombreColValor) {
//        return getEntityManager().createNamedQuery("CampoCompCaso.findByNombreColValor").setParameter("nombreColValor", nombreColValor).getResultList();
//    }
    public void persistUsuario(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        getUsuarioJpaController().create(usuario);
    }

    public void mergeUsuarioFull(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        getUsuarioJpaController().edit(usuario);
    }

    public void mergeUsuarioLight(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();

            usuario = em.merge(usuario);

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

                if (em.find(Usuario.class, id) == null) {
                    throw new NonexistentEntityException(
                            "The usuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void removeUsuario(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        getUsuarioJpaController().destroy(usuario.getIdUsuario());
    }

    /**
     * <
     * code>SELECT u FROM Usuario u</code>
     */
    public List<Usuario> getUsuarioFindAll() {
        return getUsuarioJpaController().findUsuarioEntities();

    }

    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario</code>
     */
    public Usuario getUsuarioFindByIdUsuario(String idUsuario) {
        return getUsuarioJpaController().findUsuario(idUsuario);
    }

    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.nombres = :nombres</code>
     */
    public List<Usuario> getUsuarioFindByNombres(String nombres) {
        return getUsuarioJpaController().getEntityManager().createNamedQuery("Usuario.findByNombres").setParameter("nombres", nombres).getResultList();
    }

    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.apellidos = :apellidos</code>
     */
    public List<Usuario> getUsuarioFindByApellidos(String apellidos) {
        return getUsuarioJpaController().getEntityManager().createNamedQuery("Usuario.findByApellidos").setParameter("apellidos", apellidos).getResultList();
    }

    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.email = :email</code>
     */
    public List<Usuario> getUsuarioFindByEmail(String email) {
        if (email == null) {
            return null;
        }
        return getUsuarioJpaController().getEntityManager().createNamedQuery("Usuario.findByEmail").setParameter("email", email).getResultList();
    }

    /**
     * <
     * code>SELECT u FROM Usuario u WHERE u.rut = :rut</code>
     */
    public List<Usuario> getUsuarioFindByRut(String rut) {
        return (List<Usuario>) getUsuarioJpaController().getEntityManager().createNamedQuery("Usuario.findByRut").setParameter("rut", rut).getResultList();
    }

    public void persistGrupo(Grupo grupo) throws PreexistingEntityException, RollbackFailureException, Exception {
        getGrupoJpaController().create(grupo);
    }

    public void mergeGrupo(Grupo grupo) throws NonexistentEntityException, RollbackFailureException, Exception {
        getGrupoJpaController().edit(grupo);
    }

    public void removeGrupo(Grupo grupo) throws NonexistentEntityException, RollbackFailureException, Exception {
        getGrupoJpaController().destroy(grupo.getIdGrupo());
    }

    /**
     * <
     * code>SELECT g FROM Grupo g</code>
     */
    public List<Grupo> getGrupoFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getGrupoJpaController().findGrupoEntities();
    }

    /**
     * <
     * code>SELECT g FROM Grupo g WHERE g.idGrupo = :idGrupo</code>
     */
    public Grupo getGrupoFindByIdGrupo(String idGrupo) {
        return getGrupoJpaController().findGrupo(idGrupo);
    }

    /**
     * <
     * code>SELECT g FROM Grupo g WHERE g.nombre = :nombre</code>
     */
    public List<Grupo> getGrupoFindByNombre(String nombre) {
        return getGrupoJpaController().getEntityManager().createNamedQuery("Grupo.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    public void persistCanal(Canal canal) throws PreexistingEntityException, RollbackFailureException, Exception {
        getCanalJpaController().create(canal);
    }

    public void mergeCanal(Canal canal) throws NonexistentEntityException, RollbackFailureException, Exception {
        getCanalJpaController().edit(canal);
    }

    public void removeCanal(Canal canal) throws NonexistentEntityException, RollbackFailureException, Exception {
        getCanalJpaController().destroy(canal.getIdCanal());
    }

    /**
     * <
     * code>SELECT c FROM Canal c</code>
     */
    public List<Canal> getCanalFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getCanalJpaController().findCanalEntities();
    }

    /**
     * <
     * code>SELECT c FROM Canal c WHERE c.idCanal = :idCanal</code>
     */
    public Canal getCanalFindByIdCanal(String idCanal) {
        return getCanalJpaController().findCanal(idCanal);
    }

    /**
     * <
     * code>SELECT c FROM Canal c WHERE c.nombre = :nombre</code>
     */
    public List<Canal> getCanalFindByNombre(String nombre) {
        return getCanalJpaController().getEntityManager().createNamedQuery("Canal.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    /**
     * @deprecated @param nota
     * @throws PreexistingEntityException
     * @throws RollbackFailureException
     * @throws Exception
     */
    public void persistNota(Nota nota) throws PreexistingEntityException, RollbackFailureException, Exception {
        getNotaJpaController().create(nota);
    }

    public void mergeNota(Nota nota) throws NonexistentEntityException, RollbackFailureException, Exception {
        getNotaJpaController().edit(nota);
    }

//    public void removeNota(Nota nota) throws NonexistentEntityException, RollbackFailureException, Exception {
//        getNotaJpaController().destroy(nota.getIdNota());
//    }
    /**
     * <
     * code>SELECT n FROM Nota n WHERE n.idNota = :idNota</code>
     */
    public Nota getNotaFindByIdNota(Integer idNota) {
        return getNotaJpaController().findNota(idNota);
    }

    public List<Nota> getNotaFindByIdCaso(Caso idCaso) {
        return getNotaJpaController().getEntityManager().createNamedQuery("Nota.findByIdCaso").setParameter("idCaso", idCaso).getResultList();
    }

    public List<Nota> getNotasPublicasFindByIdCaso(Caso idCaso) {
        return getNotaJpaController().getEntityManager().createNamedQuery("Nota.findByIdCasoPublic").setParameter("idCaso", idCaso)
                .setParameter("visible", Boolean.TRUE).getResultList();
    }

    /**
     * <
     * code>SELECT n FROM Nota n WHERE n.tipoNota = :tipoNota</code>
     */
    public List<Nota> getNotaFindByTipoNota(String tipoNota) {
        return getNotaJpaController().getEntityManager().createNamedQuery("Nota.findByTipoNota").setParameter("tipoNota", tipoNota).getResultList();
    }

    public void persistComponente(Componente componente) throws PreexistingEntityException, RollbackFailureException, Exception {
        getComponenteJpaController().create(componente);
    }

    public void mergeComponente(Componente componente) throws NonexistentEntityException, RollbackFailureException, Exception {
        getComponenteJpaController().edit(componente);
    }

    public void removeComponente(Componente componente) throws NonexistentEntityException, RollbackFailureException, Exception {
        getComponenteJpaController().destroy(componente.getIdComponente());
    }

    /**
     * <
     * code>SELECT c FROM Componente c</code>
     */
    public List<Componente> getComponenteFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getComponenteJpaController().findComponenteEntities();
    }

    /**
     * <
     * code>SELECT c FROM Componente c WHERE c.idComponente =
     * :idComponente</code>
     */
    public Componente getComponenteFindByIdComponente(String idComponente) {
        return getComponenteJpaController().findComponente(idComponente);
    }

    public void persistNombreAccion(NombreAccion nombreAccion) throws PreexistingEntityException, RollbackFailureException, Exception {
        getNombreAccionJpaController().create(nombreAccion);
    }

    public void mergeNombreAccion(NombreAccion nombreAccion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getNombreAccionJpaController().edit(nombreAccion);
    }

    public void removeNombreAccion(NombreAccion nombreAccion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getNombreAccionJpaController().destroy(nombreAccion.getIdNombreAccion());
    }

    /**
     * <
     * code>SELECT n FROM NombreAccion n</code>
     */
    public List<NombreAccion> getNombreAccionFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getNombreAccionJpaController().findNombreAccionEntities();
    }

    /**
     * <
     * code>SELECT n FROM NombreAccion n WHERE n.idNombreAccion =
     * :idNombreAccion</code>
     */
    public NombreAccion getNombreAccionFindByIdNombreAccion(String idNombreAccion) {
        return getNombreAccionJpaController().findNombreAccion(idNombreAccion);
    }

    public void persistAttachment(Attachment attachment) throws PreexistingEntityException, RollbackFailureException, Exception {
        getAttachmentJpaController().create(attachment);
    }

    public void mergeAttachment(Attachment attachment) throws NonexistentEntityException, RollbackFailureException, Exception {
        getAttachmentJpaController().edit(attachment);
    }

    public void removeAttachment(Attachment attachment) throws NonexistentEntityException, RollbackFailureException, Exception {
        getAttachmentJpaController().destroy(attachment.getIdAttachment());
    }

    /**
     * <
     * code>SELECT a FROM Attachment a</code>
     */
    public List<Attachment> getAttachmentFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getAttachmentJpaController().findAttachmentEntities();
    }

    /**
     * <
     * code>SELECT a FROM Attachment a WHERE a.idAttachment =
     * :idAttachment</code>
     */
    public Attachment getAttachmentFindByIdAttachment(Long idAttachment) {
        return getAttachmentJpaController().findAttachment(idAttachment);
    }

//    public List<Attachment> getAttachmentWOContentId(Caso caso) {
//        return getAttachmentJpaController().findAttachmentsWOContentId(caso);
//    }
    public Long countAttachmentWOContentId(Caso caso) {
        return getAttachmentJpaController().countAttachmentsWOContentId(caso);
    }

    public Long countAttachmentWContentId(Caso caso) {
        return getAttachmentJpaController().countAttachmentsWContentId(caso);
    }

    public Attachment getAttachmentFindByContentId(String contentId, Caso caso) {
        return getAttachmentJpaController().findByContentId(contentId, caso);
    }

    public void persistFuncion(Funcion funcion) throws PreexistingEntityException, RollbackFailureException, Exception {
        getFuncionJpaController().create(funcion);
    }

    public void mergeFuncion(Funcion funcion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getFuncionJpaController().edit(funcion);
    }

    public void removeFuncion(Funcion funcion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getFuncionJpaController().destroy(funcion.getIdFuncion());
    }

    /**
     * <
     * code>SELECT f FROM Funcion f</code>
     */
    public List<Funcion> getFuncionFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getFuncionJpaController().findFuncionEntities();
    }

    /**
     * <
     * code>SELECT f FROM Funcion f WHERE f.idFuncion = :idFuncion</code>
     */
    public Funcion getFuncionFindByIdFuncion(Integer idFuncion) {
        return getFuncionJpaController().findFuncion(idFuncion);
    }

    public void persistTipoAlerta(TipoAlerta tipoAlerta) throws PreexistingEntityException, RollbackFailureException, Exception {
        getTipoAlertaJpaController().create(tipoAlerta);
    }

    public void mergeTipoAlerta(TipoAlerta tipoAlerta) throws NonexistentEntityException, RollbackFailureException, Exception {
        getTipoAlertaJpaController().edit(tipoAlerta);
    }

    public void removeTipoAlerta(TipoAlerta tipoAlerta) throws NonexistentEntityException, RollbackFailureException, Exception {
        getTipoAlertaJpaController().destroy(tipoAlerta.getIdalerta());
    }

    /**
     * <
     * code>SELECT t FROM TipoAlerta t</code>
     */
    public List<TipoAlerta> getTipoAlertaFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getTipoAlertaJpaController().findTipoAlertaEntities();
    }

    /**
     * <
     * code>SELECT t FROM TipoAlerta t WHERE t.idalerta = :idalerta</code>
     */
    public TipoAlerta getTipoAlertaFindByIdTipoAlerta(Integer idalerta) {
        return getTipoAlertaJpaController().findTipoAlerta(idalerta);
    }

    public Long nextVal(String seq) throws PreexistingEntityException, RollbackFailureException, Exception {
        return (Long) getArchivoJpaController().getEntityManager().createNativeQuery("select nextval('" + seq + "')").getSingleResult();
    }

    public void persistArchivo(Archivo archivo) throws PreexistingEntityException, RollbackFailureException, Exception {
        getArchivoJpaController().create(archivo);
    }

    public void mergeArchivo(Archivo archivo) throws NonexistentEntityException, RollbackFailureException, Exception {
        getArchivoJpaController().edit(archivo);
    }

    public void removeArchivo(Archivo archivo) throws NonexistentEntityException, RollbackFailureException, Exception {
        getArchivoJpaController().destroy(archivo.getIdAttachment());
    }

    /**
     * <
     * code>SELECT a FROM Archivo a WHERE a.idAttachment = :idAttachment</code>
     */
    public Archivo getArchivoFindByIdAttachment(Long idAttachment) {
        return getArchivoJpaController().findArchivo(idAttachment);
    }

    public void persistEstadoCaso(EstadoCaso estadoCaso) throws PreexistingEntityException, RollbackFailureException, Exception {
        getEstadoCasoJpaController().create(estadoCaso);
    }

    public void mergeEstadoCaso(EstadoCaso estadoCaso) throws NonexistentEntityException, RollbackFailureException, Exception {
        getEstadoCasoJpaController().edit(estadoCaso);
    }

    public void removeEstadoCaso(EstadoCaso estadoCaso) throws NonexistentEntityException, RollbackFailureException, Exception {
        getEstadoCasoJpaController().destroy(estadoCaso.getIdEstado());
    }

    /**
     * <
     * code>SELECT e FROM EstadoCaso e</code>
     */
    public List<EstadoCaso> getEstadoCasoFindAll() {
        return getEstadoCasoJpaController().findEstadoCasoEntities();
    }

    /**
     * <
     * code>SELECT e FROM EstadoCaso e WHERE e.idEstado = :idEstado</code>
     */
    public EstadoCaso getEstadoCasoFindByIdEstado(String idEstado) {
        return getEstadoCasoJpaController().findEstadoCaso(idEstado);
    }

    /**
     * <
     * code>SELECT e FROM EstadoCaso e WHERE e.nombre = :nombre</code>
     */
    public List<EstadoCaso> getEstadoCasoFindByNombre(String nombre) {
        return getEstadoCasoJpaController().getEntityManager().createNamedQuery("EstadoCaso.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    public void persistSubEstadoCaso(SubEstadoCaso subEstadoCaso) throws PreexistingEntityException, RollbackFailureException, Exception {
        getSubEstadoCasoJpaController().create(subEstadoCaso);
    }

    public void mergeSubEstadoCaso(SubEstadoCaso subEstadoCaso) throws NonexistentEntityException, RollbackFailureException, Exception {
        getSubEstadoCasoJpaController().edit(subEstadoCaso);
    }

    public void removeSubEstadoCaso(SubEstadoCaso subEstadoCaso) throws NonexistentEntityException, RollbackFailureException, Exception {
        getSubEstadoCasoJpaController().destroy(subEstadoCaso.getIdSubEstado());
    }

    /**
     * <
     * code>SELECT s FROM SubEstadoCaso s</code>
     */
    public List<SubEstadoCaso> getSubEstadoCasoFindAll() {
        return getEntityManager().createNamedQuery("SubEstadoCaso.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT s FROM SubEstadoCaso s WHERE s.idSubEstado =
     * :idSubEstado</code>
     */
    public SubEstadoCaso getSubEstadoCasoFindByIdSubEstadoCaso(String idSubEstado) {
        return (SubEstadoCaso) getSubEstadoCasoJpaController().getEntityManager().createNamedQuery("SubEstadoCaso.findByIdSubEstado").setParameter("idSubEstado", idSubEstado).getSingleResult();
    }

    /**
     * <
     * code>SELECT s FROM SubEstadoCaso s WHERE s.nombre = :nombre</code>
     */
    public List<SubEstadoCaso> getSubEstadoCasoFindByNombre(String nombre) {
        return getEntityManager().createNamedQuery("SubEstadoCaso.findByNombre").setParameter("nombre", nombre).getResultList();
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

//    public List<Prioridad> findPrioridadByTipoCaso(TipoCaso tipo) {
//        return getEntityManager().createNamedQuery("Prioridad.findByTipoCaso").setParameter("tipoCaso", tipo).getResultList();
//    }
    public void persistProducto(Producto producto) throws PreexistingEntityException, RollbackFailureException, Exception {
        getProductoJpaController().create(producto);
    }

    public void mergeProducto(Producto producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        getProductoJpaController().edit(producto);
    }

    public void removeProducto(Producto producto) throws NonexistentEntityException, RollbackFailureException, Exception {
        getProductoJpaController().destroy(producto.getIdProducto());
    }

    /**
     * <
     * code>SELECT p FROM Producto p</code>
     */
    public List<Producto> getProductoFindAll() {
        return getEntityManager().createNamedQuery("Producto.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT p FROM Producto p WHERE p.idProducto = :idProducto</code>
     */
    public Producto getProductoFindByIdProducto(String idProducto) {
        return getProductoJpaController().findProducto(idProducto);
    }

    /**
     * <
     * code>SELECT p FROM Producto p WHERE p.nombre = :nombre</code>
     */
    public Producto getProductoFindByNombre(String nombre) {
        EasyCriteriaQuery<Producto> ecq = new EasyCriteriaQuery<Producto>(emf, Producto.class);
        ecq.addLikePredicate("nombre", nombre);
        return ecq.getSingleResult();
        //return (Producto) getEntityManager().createNamedQuery("Producto.findByNombre").setParameter("nombre", nombre).getSingleResult();
    }

    public void persistAuditLog(AuditLog auditLog) throws PreexistingEntityException, RollbackFailureException, Exception {
        getAuditLogJpaCustomController().create(auditLog);
    }

    public void mergeAuditLog(AuditLog auditLog) throws NonexistentEntityException, RollbackFailureException, Exception {
        getAuditLogJpaCustomController().edit(auditLog);
    }

    public void removeAuditLog(AuditLog auditLog) throws NonexistentEntityException, RollbackFailureException, Exception {
        getAuditLogJpaCustomController().destroy(auditLog.getIdLog());
    }

    /**
     * <
     * code>SELECT a FROM AuditLog a</code>
     */
    public List<AuditLog> getAuditLogFindAll() {
        return getEntityManager().createNamedQuery("AuditLog.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT a FROM AuditLog a WHERE a.idLog = :idLog</code>
     */
    public List<AuditLog> getAuditLogFindByIdLog(Long idLog) {
        return getEntityManager().createNamedQuery("AuditLog.findByIdLog").setParameter("idLog", idLog).getResultList();
    }

    /**
     * <
     * code>SELECT a FROM AuditLog a WHERE a.tabla = :tabla</code>
     */
    public List<AuditLog> getAuditLogFindByTabla(String tabla) {
        return getEntityManager().createNamedQuery("AuditLog.findByTabla").setParameter("tabla", tabla).getResultList();
    }

    /**
     * <
     * code>SELECT a FROM AuditLog a WHERE a.campo = :campo</code>
     */
    public List<AuditLog> getAuditLogFindByCampo(String campo) {
        return getEntityManager().createNamedQuery("AuditLog.findByCampo").setParameter("campo", campo).getResultList();
    }

    /**
     * <
     * code>SELECT a FROM AuditLog a WHERE a.fecha = :fecha</code>
     */
    public List<AuditLog> getAuditLogFindByFecha(Date fecha) {
        return getEntityManager().createNamedQuery("AuditLog.findByFecha").setParameter("fecha", fecha).getResultList();
    }

    public void persistPrioridad(Prioridad prioridad) throws PreexistingEntityException, RollbackFailureException, Exception {
        getPrioridadJpaController().create(prioridad);
//        return (Prioridad) throws PreexistingEntityException, RollbackFailureException, Exception(prioridad);
    }

    public void mergePrioridad(Prioridad prioridad) throws NonexistentEntityException, RollbackFailureException, Exception {
        getPrioridadJpaController().edit(prioridad);
    }

    public void removePrioridad(Prioridad prioridad) throws NonexistentEntityException, RollbackFailureException, Exception {
        getPrioridadJpaController().destroy(prioridad.getIdPrioridad());
    }

    /**
     * <
     * code>SELECT p FROM Prioridad p</code>
     */
    public List<Prioridad> getPrioridadFindAll() {
        return getEntityManager().createNamedQuery("Prioridad.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT p FROM Prioridad p WHERE p.idPrioridad = :idPrioridad</code>
     */
    public Prioridad getPrioridadFindByIdPrioridad(String idPrioridad) {
        return getPrioridadJpaController().findPrioridad(idPrioridad);
    }

    /**
     * <
     * code>SELECT p FROM Prioridad p WHERE p.nombre = :nombre</code>
     */
    public List<Prioridad> getPrioridadFindByNombre(String nombre) {
        return getEntityManager().createNamedQuery("Prioridad.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    public void persistCondicion(Condicion condicion) throws PreexistingEntityException, RollbackFailureException, Exception {
        getCondicionJpaController().create(condicion);
    }

    public void mergeCondicion(Condicion condicion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getCondicionJpaController().edit(condicion);
    }

    public void removeCondicion(Condicion condicion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getCondicionJpaController().destroy(condicion.getIdCondicion());
    }

    /**
     * <
     * code>SELECT c FROM Condicion c</code>
     */
    public List<Condicion> getCondicionFindAll() {
        return getEntityManager().createNamedQuery("Condicion.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT c FROM Condicion c WHERE c.idCondicion = :idCondicion</code>
     */
    public List<Condicion> getCondicionFindByIdCondicion(Integer idCondicion) {
        return getEntityManager().createNamedQuery("Condicion.findByIdCondicion").setParameter("idCondicion", idCondicion).getResultList();
    }

    public void persistReglaTrigger(ReglaTrigger reglaTrigger) throws PreexistingEntityException, RollbackFailureException, Exception {
        getReglaTriggerJpaController().create(reglaTrigger);
    }

    public void mergeReglaTrigger(ReglaTrigger reglaTrigger) throws NonexistentEntityException, RollbackFailureException, Exception {
        getReglaTriggerJpaController().edit(reglaTrigger);
    }

    public void removeReglaTrigger(ReglaTrigger reglaTrigger) throws NonexistentEntityException, RollbackFailureException, Exception {
        getReglaTriggerJpaController().destroy(reglaTrigger.getIdTrigger());
    }

    /**
     * <
     * code>SELECT r FROM ReglaTrigger r</code>
     */
    public List<ReglaTrigger> getReglaTriggerFindAll() {
        return getEntityManager().createNamedQuery("ReglaTrigger.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT r FROM ReglaTrigger r WHERE r.idTrigger = :idTrigger</code>
     */
    public ReglaTrigger getReglaTriggerFindByIdTrigger(String idTrigger) {
        return (ReglaTrigger) getEntityManager().createNamedQuery("ReglaTrigger.findByIdTrigger").setParameter("idTrigger", idTrigger).getSingleResult();
    }

    public List<ReglaTrigger> getReglaTriggerFindByEvento(String event) {
        return getEntityManager().createNamedQuery("ReglaTrigger.findByEvento").setParameter("evento", event).getResultList();
    }

//   public List<ReglaTrigger> getReglaTriggerFindByEvento(String event, Area idArea) {
//        return getEntityManager().createNamedQuery("ReglaTrigger.findByEventoArea").setParameter("evento", event).setParameter("idArea", idArea).getResultList();
//    }
    public void persistSesiones(Sesiones sesiones) throws PreexistingEntityException, RollbackFailureException, Exception {
        getSesionesJpaController().create(sesiones);
    }

    public void mergeSesiones(Sesiones sesiones) throws NonexistentEntityException, RollbackFailureException, Exception {
        getSesionesJpaController().edit(sesiones);
    }

    public void removeSesiones(Sesiones sesiones) throws NonexistentEntityException, RollbackFailureException, Exception {
        getSesionesJpaController().destroy(sesiones.getIdSesion());
    }

    /**
     * <
     * code>SELECT s FROM Sesiones s</code>
     */
    public List<Sesiones> getSesionesFindAll() {
        return getEntityManager().createNamedQuery("Sesiones.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT s FROM Sesiones s WHERE s.idSesion = :idSesion</code>
     */
    public Sesiones getSesionesFindByIdSesion(Long idSesion) {
        return getSesionesJpaController().findSesiones(idSesion);
    }

    /**
     * <
     * code>SELECT s FROM Sesiones s WHERE s.rutUsuario = :rutUsuario</code>
     */
    public List<Sesiones> getSesionesFindByRutUsuario(String rutUsuario) {
        return getEntityManager().createNamedQuery("Sesiones.findByRutUsuario").setParameter("rutUsuario", rutUsuario).getResultList();
    }

    /**
     * <
     * code>SELECT s FROM Sesiones s WHERE s.fechaIngreso = :fechaIngreso</code>
     */
    public List<Sesiones> getSesionesFindByFechaIngreso(Date fechaIngreso) {
        return getEntityManager().createNamedQuery("Sesiones.findByFechaIngreso").setParameter("fechaIngreso", fechaIngreso).getResultList();
    }

    public void persistTipoComparacion(TipoComparacion tipoComparacion) throws PreexistingEntityException, RollbackFailureException, Exception {
        getTipoComparacionJpaController().create(tipoComparacion);
    }

    public void mergeTipoComparacion(TipoComparacion tipoComparacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getTipoComparacionJpaController().edit(tipoComparacion);
    }

    public void removeTipoComparacion(TipoComparacion tipoComparacion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getTipoComparacionJpaController().destroy(tipoComparacion.getIdComparador());
    }

    /**
     * <
     * code>SELECT t FROM TipoComparacion t</code>
     */
    public List<TipoComparacion> getTipoComparacionFindAll() {
        return getTipoComparacionJpaController().findTipoComparacionEntities();
    }

    /**
     * <
     * code>SELECT t FROM TipoComparacion t WHERE t.idComparador =
     * :idComparador</code>
     */
    public TipoComparacion getTipoComparacionFindByIdComparador(String idComparador) {
        return getTipoComparacionJpaController().findTipoComparacion(idComparador);
    }

    /**
     * <
     * code>SELECT t FROM TipoComparacion t WHERE t.simbolo = :simbolo</code>
     */
    public List<TipoComparacion> getTipoComparacionFindBySimbolo(String simbolo) {
        return getEntityManager().createNamedQuery("TipoComparacion.findBySimbolo").setParameter("simbolo", simbolo).getResultList();
    }

    public void persistSubComponente(SubComponente subComponente) throws PreexistingEntityException, RollbackFailureException, Exception {
        getSubComponenteJpaController().create(subComponente);
    }

    public void mergeSubComponente(SubComponente subComponente) throws NonexistentEntityException, RollbackFailureException, Exception {
        getSubComponenteJpaController().edit(subComponente);
    }

    public void removeSubComponente(SubComponente subComponente) throws NonexistentEntityException, RollbackFailureException, Exception {
        getSubComponenteJpaController().destroy(subComponente.getIdSubComponente());
    }

    /**
     * <
     * code>SELECT s FROM SubComponente s</code>
     */
    public List<SubComponente> getSubComponenteFindAll() {
        return getEntityManager().createNamedQuery("SubComponente.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT s FROM SubComponente s WHERE s.idSubComponente =
     * :idSubComponente</code>
     */
    public SubComponente getSubComponenteFindByIdSubComponente(String idSubComponente) {
        return getSubComponenteJpaController().findSubComponente(idSubComponente);
    }

    /**
     * <
     * code>SELECT s FROM SubComponente s WHERE s.nombre = :nombre</code>
     */
    public List<SubComponente> getSubComponenteFindByNombre(String nombre) {
        return getEntityManager().createNamedQuery("SubComponente.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    public void persistDocumento(Documento documento) throws PreexistingEntityException, RollbackFailureException, Exception {
        getDocumentoJpaController().create(documento);
    }

    public void mergeDocumento(Documento documento) throws NonexistentEntityException, RollbackFailureException, Exception {
        getDocumentoJpaController().edit(documento);
    }

    public void removeDocumento(Documento documento) throws NonexistentEntityException, RollbackFailureException, Exception {
        getDocumentoJpaController().destroy(documento.getIdDocumento());
    }

    /**
     * <
     * code>SELECT f FROM Documento f</code>
     */
    public List<Documento> getDocumentoFindAll() {
        return getEntityManager().createNamedQuery("Documento.findAll").getResultList();
    }

    /**
     * <
     * code>SELECT f FROM Documento f WHERE f.idQuestion = :idQuestion</code>
     */
    public Documento getDocumentoFindByPK(Integer idQuestion) {
        return getDocumentoJpaController().findDocumento(idQuestion);
    }

    public void persistAccion(Accion accion) throws PreexistingEntityException, RollbackFailureException, Exception {
        getAccionJpaController().create(accion);
    }

    public void mergeAccion(Accion accion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getAccionJpaController().edit(accion);
    }

    public void removeAccion(Accion accion) throws NonexistentEntityException, RollbackFailureException, Exception {
        getAccionJpaController().destroy(accion.getIdAccion());
    }

    /**
     * <
     * code>SELECT a FROM Accion a</code>
     */
    public List<Accion> getAccionFindAll() {
        return getAccionJpaController().findAccionEntities();
    }

    public List<Item> getItemFindAll() {
        return getItemJpaController().findItemEntities();
    }

    /**
     * <
     * code>SELECT a FROM Accion a WHERE a.idAccion = :idAccion</code>
     */
    public Accion getAccionFindById(Integer idAccion) {
        return getAccionJpaController().findAccion(idAccion);
    }

    public void persistArea(Area area) throws PreexistingEntityException, RollbackFailureException, Exception {
        getAreaJpaController().create(area);
//        return (Area) throws PreexistingEntityException, RollbackFailureException, Exception(area);
    }

    public void persistSetting(AppSetting s) throws PreexistingEntityException, RollbackFailureException, Exception {
        getAppSettingJpaController().create(s);
//        return (Area) throws PreexistingEntityException, RollbackFailureException, Exception(area);
    }

    public void mergeArea(Area area) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        getAreaJpaController().edit(area);
//        return (Area) mergeEntity(area);
    }

    public void removeArea(Area area) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        getAreaJpaController().destroy(area.getIdArea());
//        area = getEntityManager().find(Area.class, area.getIdArea());
//        removeEntity(area);
    }

    /**
     * <
     * code>SELECT a FROM Area a</code>
     */
    public List<Area> getAreaFindAll() {
        return getAreaJpaController().findAreaEntities();
    }

    /**
     * <
     * code>SELECT a FROM Area a WHERE a.idArea = :idArea</code>
     */
    public Area getAreaFindByIdArea(String idArea) {
        return getAreaJpaController().findArea(idArea);
    }

    /**
     * <
     * code>SELECT a FROM Area a WHERE a.nombre = :nombre</code>
     */
    public List<Area> getAreaFindByNombre(String nombre) {
        return getEntityManager().createNamedQuery("Area.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    public List<Caso> getCasoFindByEstadoAndAlerta(EstadoCaso estado, TipoAlerta tipoAlerta) {
        return getCasoJpa().getCasoFindByEstadoAndAlerta(estado, tipoAlerta);
    }

    public void persistRol(Rol rol) throws PreexistingEntityException, RollbackFailureException, Exception {
        getRolJpaController().create(rol);
    }

    public void persistRecinto(Recinto recinto) throws PreexistingEntityException, RollbackFailureException, Exception {
        getRecintoJpaController().create(recinto);
    }

    public void mergeRecinto(Recinto recinto) throws PreexistingEntityException, RollbackFailureException, Exception {
        getRecintoJpaController().edit(recinto);
    }

    public void removeRecinto(Recinto recinto) throws PreexistingEntityException, RollbackFailureException, Exception {
        getRecintoJpaController().destroy(recinto.getIdRecinto());
    }

    public void mergeRol(Rol rol) throws NonexistentEntityException, RollbackFailureException, Exception {
        getRolJpaController().edit(rol);
    }

    public void removeRol(Rol rol) throws NonexistentEntityException, RollbackFailureException, Exception {
        getRolJpaController().destroy(rol.getIdRol());
    }

    /**
     * <
     * code>SELECT r FROM Rol r</code>
     */
    public List<Rol> getRolFindAll() {
        return getRolJpaController().findRolEntities();
    }

    /**
     * <
     * code>SELECT r FROM Rol r WHERE r.idRol = :idRol</code>
     */
    public Rol getRolFindByIdRol(String idRol) {
        return getRolJpaController().findRol(idRol);
    }

    /**
     * <
     * code>SELECT r FROM Rol r WHERE r.nombre = :nombre</code>
     */
    public List<Rol> getRolFindByNombre(String nombre) {
        return getEntityManager().createNamedQuery("Rol.findByNombre").setParameter("nombre", nombre).getResultList();
    }

    public void persistCategoria(Categoria current) throws PreexistingEntityException, RollbackFailureException, Exception {
        current.setIdCategoria(current.getOrden().intValue());
        getCategoriaJpaController().create(current);
    }

    public void mergeCategoria(Categoria current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getCategoriaJpaController().edit(current);
    }

    public void removeCategoria(Categoria current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getCategoriaJpaController().destroy(current.getIdCategoria());
    }

    public List<Categoria> getCategoriaFindByNombreLike(String nombre) {
        return getEntityManager().createNamedQuery("Categoria.findByNombreLike").setParameter("nombre", "%" + nombre + "%").getResultList();
    }

    public void persistItem(Item current) throws PreexistingEntityException, RollbackFailureException, Exception {
        getItemJpaController().create(current);
    }

    public void mergeItem(Item current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getItemJpaController().edit(current);
    }

    public void removeItem(Item current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getItemJpaController().destroy(current.getIdItem());
    }

    public List<Item> getItemFindByNombreLike(String nombre) {
        EasyCriteriaQuery<Item> ecq = new EasyCriteriaQuery<Item>(emf, Item.class);
        ecq.addLikePredicate(Item_.nombre.getName(), '%' + nombre + '%');
        return ecq.getAllResultList();
//        return getEntityManager().createNamedQuery("Item.findByNombreLike").setParameter("nombre", "%" + nombre + "%").getResultList();
    }

    /**
     * <
     * code>SELECT c FROM Categoria c</code>
     */
    public List<Categoria> getCategoriaFindAll() {
        return getCategoriaJpaController().findCategoriaEntities();
    }

    /**
     * <
     * code>SELECT c FROM Categoria c WHERE c.idCategoria = :idCategoria</code>
     */
    public Categoria getCategoriaFindByIdCategoria(Integer idCategoria) {
        return getCategoriaJpaController().findCategoria(idCategoria);
//        return getEntityManager().createNamedQuery("Categoria.findByIdCategoria").setParameter("idCategoria", idCategoria).getResultList();
    }

    public Item getItemFindByIdItem(Integer idItem) {
        return getItemJpaController().findItem(idItem);
//        return getEntityManager().createNamedQuery("Categoria.findByIdCategoria").setParameter("idCategoria", idCategoria).getResultList();
    }

    public Categoria getCategoriaFindByName(String nombreCategoria) {
        return (Categoria) getEntityManager().createNamedQuery("Categoria.findByNombre").setParameter("nombre", nombreCategoria).getSingleResult();
//        return getEntityManager().createNamedQuery("Categoria.findByIdCategoria").setParameter("idCategoria", idCategoria).getResultList();
    }

//    public Object findById(Class aClass, Object key) {
//    }
    public void persistCliente(Cliente current) throws PreexistingEntityException, RollbackFailureException, Exception {
        getClienteJpaController().create(current);
    }

    public Cliente findCliente(Integer idCliente) {
        return getClienteJpaController().findCliente(idCliente);
    }

    public void mergeCliente(Cliente current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getClienteJpaController().edit(current);
    }

    public void removeCliente(Cliente current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getClienteJpaController().destroy(current.getIdCliente());
    }

    public List<Cliente> getClienteFindAll() {
        return getClienteJpaController().findClienteEntities();
    }

    public void persistEmailCliente(EmailCliente current) throws PreexistingEntityException, RollbackFailureException, Exception {
        getEmailClienteJpaController().create(current);
    }

    public void mergeEmailCliente(EmailCliente current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getEmailClienteJpaController().edit(current);
    }

    public void removeEmailCliente(EmailCliente current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getEmailClienteJpaController().destroy(current.getEmailCliente());
    }

    public List<EmailCliente> getEmailClienteFindAll() {
        return getEmailClienteJpaController().findEmailClienteEntities();
    }

    public EmailCliente getEmailClienteFindByEmail(String id) throws NoResultException {
        return getEmailClienteJpaController().findEmailCliente(id);
    }

    public Caso getCasoFindByEmailCreationTimeAndType(String email, Date creationTime, TipoCaso tipoCaso) {
        EasyCriteriaQuery<Caso> ecq = new EasyCriteriaQuery<Caso>(emf, Caso.class);
        ecq.addLikePredicate("emailCliente.emailCliente", email);
        ecq.addEqualPredicate(Caso_.fechaCreacion.getName(), creationTime);
        ecq.addDistinctPredicate("tipoCaso", tipoCaso);
        return ecq.getSingleResult();
    }

    public List<EmailCliente> getEmailClienteFindByEmailLike(String id, int maxResults) {
        return getEmailClienteJpaController().findEmailClienteEntitiesLike(id, maxResults, 0);
    }

    public void persistTipoNota(TipoNota current) throws PreexistingEntityException, RollbackFailureException, Exception {
        getTipoNotaJpaController().create(current);
    }

    public void mergeTipoNota(TipoNota current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getTipoNotaJpaController().edit(current);
    }

    public void removeTipoNota(TipoNota current) throws NonexistentEntityException, RollbackFailureException, Exception {
        getTipoNotaJpaController().destroy(current.getIdTipoNota());
    }

    public List<TipoNota> getTipoNotaFindAll() {
//        throw new UnsupportedOperationException("Not implemented... You shoud use a paginated or parcial query instead of quering all the tuples in a table.");
        return getTipoNotaJpaController().findTipoNotaEntities();
    }

    public TipoNota getTipoNotaFindById(Integer idTipoNota) {
        return getTipoNotaJpaController().findTipoNota(idTipoNota);
    }

    public List<FieldType> getCustomFieldTypes() {
        return getUsuarioJpaController().getEntityManager().createNamedQuery("FieldType.findByIsCustomField").setParameter("isCustomField", Boolean.TRUE).getResultList();
    }

//    public List<CustomField> getCustomFieldsForCaso() {
//        return getUsuarioJpaController().getEntityManager().createNamedQuery("CustomField.findByEntity").setParameter("entity", CASE_CUSTOM_FIELD).getResultList();
//    }
//    
//
//    public List<CustomField> getClientsCustomFieldsForCaso() {
//        return getUsuarioJpaController().getEntityManager().createNamedQuery("CustomField.findByEntityForCustomers").setParameter("entity", CASE_CUSTOM_FIELD).getResultList();
//    }
//
//    public List<CustomField> getAgentsCustomFieldsForClient() {
//        return getUsuarioJpaController().getEntityManager().createNamedQuery("CustomField.findByEntityForAgents").setParameter("entity", CLIENT_CUSTOM_FIELD).getResultList();
//    }
//
//    public List<CustomField> getClientsCustomFieldsForClient() {
//        return getUsuarioJpaController().getEntityManager().createNamedQuery("CustomField.findByEntityForCustomers").setParameter("entity", CLIENT_CUSTOM_FIELD).getResultList();
//    }
    /**
     * @return the usuarioJpaController
     */
    public UsuarioJpaController getUsuarioJpaController() {
        if (usuarioJpaController == null) {
            usuarioJpaController = new UsuarioJpaController(utx, emf);
        }
        return usuarioJpaController;
    }

    /**
     * @return the rolJpaController
     */
    public RolJpaController getRolJpaController() {
        if (rolJpaController == null) {
            rolJpaController = new RolJpaController(utx, emf);
        }
        return rolJpaController;
    }

    public RecintoJpaController getRecintoJpaController() {
        if (null == recintoJpaController) {
            recintoJpaController = new RecintoJpaController(utx, emf);
        }
        return recintoJpaController;
    }

    /**
     * @return the notaJpaController
     */
    public NotaJpaController getNotaJpaController() {
        if (notaJpaController == null) {
            notaJpaController = new NotaJpaController(utx, emf);
        }
        return notaJpaController;
    }

    /**
     * @return the emailClienteJpaController
     */
    public EmailClienteJpaCustomController getEmailClienteJpaController() {
        if (emailClienteJpaController == null) {
            emailClienteJpaController = new EmailClienteJpaCustomController(utx, emf);
        }
        return emailClienteJpaController;
    }

    /**
     * @return the clienteJpaController
     */
    public ClienteJpaController getClienteJpaController() {
        if (clienteJpaController == null) {
            clienteJpaController = new ClienteJpaController(utx, emf);
        }
        return clienteJpaController;
    }

    /**
     * @return the prioridadJpaController
     */
    public PrioridadJpaController getPrioridadJpaController() {
        if (prioridadJpaController == null) {
            prioridadJpaController = new PrioridadJpaController(utx, emf);
        }
        return prioridadJpaController;
    }

    /**
     * @return the grupoJpaController
     */
    public GrupoJpaController getGrupoJpaController() {
        if (grupoJpaController == null) {
            grupoJpaController = new GrupoJpaController(utx, emf);
        }
        return grupoJpaController;
    }

    /**
     * @return the categoriaJpaController
     */
    public ItemJpaController getItemJpaController() {
        if (itemJpaController == null) {
            itemJpaController = new ItemJpaController(utx, emf);
        }
        return itemJpaController;
    }

    /**
     * @return the categoriaJpaController
     */
    public CategoriaJpaController getCategoriaJpaController() {
        if (categoriaJpaController == null) {
            categoriaJpaController = new CategoriaJpaController(utx, emf);
        }
        return categoriaJpaController;
    }

    /**
     * @return the areaJpaController
     */
    public AreaJpaController getAreaJpaController() {
        if (areaJpaController == null) {
            areaJpaController = new AreaJpaController(utx, emf);
        }
        return areaJpaController;
    }

    /**
     * @return the areaJpaController
     */
    public CanalSettingJpaController getCanalSettingJpaController() {
        if (canalSettingJpaController == null) {
            canalSettingJpaController = new CanalSettingJpaController(utx, emf);
        }
        return canalSettingJpaController;
    }

    /**
     * @return the casoJpa
     */
    public CasoJPACustomController getCasoJpa() {
        if (casoJpa == null) {
            casoJpa = new CasoJPACustomController(utx, emf);
        }
        return casoJpa;
    }

    /**
     * @return the casoJpa
     */
    public AuditLogJpaCustomController getAuditLogJpaCustomController() {
        if (auditLogJpa == null) {
            auditLogJpa = new AuditLogJpaCustomController(utx, emf);
        }
        return auditLogJpa;
    }

    public CanalJpaController getCanalJpaController() {
        if (canalJpaController == null) {
            canalJpaController = new CanalJpaController(utx, emf);
        }
        return canalJpaController;
    }

    /**
     * @return the tipoNotaJpaController
     */
    public TipoNotaJpaController getTipoNotaJpaController() {
        if (tipoNotaJpaController == null) {
            tipoNotaJpaController = new TipoNotaJpaController(utx, emf);
        }
        return tipoNotaJpaController;
    }

    /**
     * @return the accionJpaController
     */
    public AccionJpaController getAccionJpaController() {
        if (accionJpaController == null) {
            accionJpaController = new AccionJpaController(utx, emf);
        }
        return accionJpaController;
    }

    /**
     * @return the archivoJpaController
     */
    public ArchivoJpaController getArchivoJpaController() {
        if (archivoJpaController == null) {
            archivoJpaController = new ArchivoJpaController(utx, emf);
        }
        return archivoJpaController;
    }

    /**
     * @return the attachmentJpaController
     */
    public AttachmentJpaController getAttachmentJpaController() {
        if (attachmentJpaController == null) {
            attachmentJpaController = new AttachmentJpaController(utx, emf);
        }
        return attachmentJpaController;
    }

    /**
     * @return the componenteJpaController
     */
    public ComponenteJpaController getComponenteJpaController() {
        if (componenteJpaController == null) {
            componenteJpaController = new ComponenteJpaController(utx, emf);
        }
        return componenteJpaController;
    }

    /**
     * @return the condicionJpaController
     */
    public CondicionJpaController getCondicionJpaController() {
        if (condicionJpaController == null) {
            condicionJpaController = new CondicionJpaController(utx, emf);
        }
        return condicionJpaController;
    }

    /**
     * @return the documentoJpaController
     */
    public DocumentoJpaController getDocumentoJpaController() {
        if (documentoJpaController == null) {
            documentoJpaController = new DocumentoJpaController(utx, emf);
        }
        return documentoJpaController;
    }

    /**
     * @return the estadoCasoJpaController
     */
    public EstadoCasoJpaController getEstadoCasoJpaController() {
        if (estadoCasoJpaController == null) {
            estadoCasoJpaController = new EstadoCasoJpaController(utx, emf);
        }
        return estadoCasoJpaController;
    }

    /**
     * @return the funcionJpaController
     */
    public FuncionJpaController getFuncionJpaController() {
        if (funcionJpaController == null) {
            funcionJpaController = new FuncionJpaController(utx, emf);
        }
        return funcionJpaController;
    }

    /**
     * @return the nombreAccionJpaController
     */
    public NombreAccionJpaController getNombreAccionJpaController() {
        if (nombreAccionJpaController == null) {
            nombreAccionJpaController = new NombreAccionJpaController(utx, emf);
        }
        return nombreAccionJpaController;
    }

    /**
     * @return the productoJpaController
     */
    public ProductoJpaController getProductoJpaController() {
        if (productoJpaController == null) {
            productoJpaController = new ProductoJpaController(utx, emf);
        }
        return productoJpaController;
    }

    /**
     * @return the reglaTriggerJpaController
     */
    public ReglaTriggerJpaController getReglaTriggerJpaController() {
        if (reglaTriggerJpaController == null) {
            reglaTriggerJpaController = new ReglaTriggerJpaController(utx, emf);
        }
        return reglaTriggerJpaController;
    }

    /**
     * @return the subComponenteJpaController
     */
    public SubComponenteJpaController getSubComponenteJpaController() {
        if (subComponenteJpaController == null) {
            subComponenteJpaController = new SubComponenteJpaController(utx, emf);
        }
        return subComponenteJpaController;
    }

    /**
     * @return the subEstadoCasoJpaController
     */
    public SubEstadoCasoJpaController getSubEstadoCasoJpaController() {
        if (subEstadoCasoJpaController == null) {
            subEstadoCasoJpaController = new SubEstadoCasoJpaController(utx, emf);
        }
        return subEstadoCasoJpaController;
    }

    /**
     * @return the tipoAlertaJpaController
     */
    public TipoAlertaJpaController getTipoAlertaJpaController() {
        if (tipoAlertaJpaController == null) {
            tipoAlertaJpaController = new TipoAlertaJpaController(utx, emf);
        }
        return tipoAlertaJpaController;
    }

    /**
     * @return the tipoComparacionJpaController
     */
    public TipoComparacionJpaController getTipoComparacionJpaController() {
        if (tipoComparacionJpaController == null) {
            tipoComparacionJpaController = new TipoComparacionJpaController(utx, emf);
        }
        return tipoComparacionJpaController;
    }

    /**
     * @return the sesionesJpaController
     */
    public SesionesJpaController getSesionesJpaController() {
        if (sesionesJpaController == null) {
            sesionesJpaController = new SesionesJpaController(utx, emf);
        }
        return sesionesJpaController;
    }

    public void setCasoChangeListener(CasoChangeListener casoChangeListener) {
        this.casoChangeListener = casoChangeListener;
    }

    public CasoChangeListener getCasoChangeListener() {
        return casoChangeListener;
    }

    /**
     * @return the vistaJpaController
     */
    public VistaJpaController getVistaJpaController() {
        if (vistaJpaController == null) {
            vistaJpaController = new VistaJpaController(utx, emf);
        }
        return vistaJpaController;
    }

    /**
     * @return the filtroVistaJpaController
     */
    public FiltroVistaJpaController getFiltroVistaJpaController() {
        if (filtroVistaJpaController == null) {
            filtroVistaJpaController = new FiltroVistaJpaController(utx, emf);
        }
        return filtroVistaJpaController;
    }

    /**
     * @return the appSettingJpaController
     */
    public AppSettingJpaController getAppSettingJpaController() {
        if (appSettingJpaController == null) {
            appSettingJpaController = new AppSettingJpaController(utx, emf);
        }
        return appSettingJpaController;
    }

    /**
     * @return the clippingJpaController
     */
    public ClippingJpaController getClippingJpaController() {
        if (clippingJpaController == null) {
            clippingJpaController = new ClippingJpaController(utx, emf);
        }
        return clippingJpaController;
    }

    public UsuarioJpaCustomController getUsuarioJpaCustomController() {
        if (usuarioJpaCustomController == null) {
            usuarioJpaCustomController = new UsuarioJpaCustomController(utx, emf);
        }
        return usuarioJpaCustomController;
    }

    @Override
    protected Predicate createSpecialPredicate(FiltroVista filtro) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean isThereSpecialFiltering(FiltroVista filtro) {
        return false;
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
}
