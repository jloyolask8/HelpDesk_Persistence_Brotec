package com.itcs.helpdesk.persistence.jpa.custom;

import com.itcs.helpdesk.persistence.entities.AuditLog;
import com.itcs.helpdesk.persistence.entities.AuditLog_;
import com.itcs.helpdesk.persistence.jpa.AuditLogJpaController;
import com.itcs.helpdesk.persistence.utils.vo.AuditLogVO;
import com.itcs.jpautils.EasyCriteriaQuery;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author jonathan
 * 
 */
public class AuditLogJpaCustomController extends AuditLogJpaController {

    private EntityManagerFactory localEmf;

    public AuditLogJpaCustomController(UserTransaction utx, EntityManagerFactory emf) {
        super(utx, emf);
        this.localEmf = emf;
    }

    public int countByFilterForAudit(AuditLogVO filter) {
        
        EasyCriteriaQuery<AuditLog> criteriaQueryCasos = new EasyCriteriaQuery<AuditLog>(localEmf, AuditLog.class);
        createPredicate(filter, criteriaQueryCasos);
        return criteriaQueryCasos.count().intValue();
        
    }

    public List<AuditLog> findAuditLogEntities(boolean all, int maxResults, int firstResult, AuditLogVO filter, boolean log) {

        EasyCriteriaQuery<AuditLog> criteriaQueryCasos = new EasyCriteriaQuery<AuditLog>(localEmf, AuditLog.class);
        filter.setAlertLevel(!log);
        createPredicate(filter, criteriaQueryCasos);
        criteriaQueryCasos.orderBy(AuditLog_.fecha.getName(), false);

        if (!all) {
            criteriaQueryCasos.setFirstResult(firstResult);
            criteriaQueryCasos.setMaxResults(maxResults);
            return criteriaQueryCasos.next();
        }

        return criteriaQueryCasos.getAllResultList();
    }

    private void createPredicate(AuditLogVO filter, EasyCriteriaQuery<AuditLog> criteriaQueryCasos) {
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
}
