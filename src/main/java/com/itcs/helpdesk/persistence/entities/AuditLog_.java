package com.itcs.helpdesk.persistence.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(AuditLog.class)
public class AuditLog_ { 

    public static volatile SingularAttribute<AuditLog, String> newValue;
    public static volatile SingularAttribute<AuditLog, String> campo;
    public static volatile SingularAttribute<AuditLog, Date> fecha;
    public static volatile SingularAttribute<AuditLog, Long> idLog;
    public static volatile SingularAttribute<AuditLog, String> owner;
    public static volatile SingularAttribute<AuditLog, String> idUser;
    public static volatile SingularAttribute<AuditLog, String> oldValue;
    public static volatile SingularAttribute<AuditLog, String> tabla;
    public static volatile SingularAttribute<AuditLog, Long> idCaso;

}