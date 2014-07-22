package com.itcs.helpdesk.persistence.entities;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Sesiones.class)
public class Sesiones_ { 

    public static volatile SingularAttribute<Sesiones, String> rutUsuario;
    public static volatile SingularAttribute<Sesiones, Long> idSesion;
    public static volatile SingularAttribute<Sesiones, Boolean> usado;
    public static volatile SingularAttribute<Sesiones, Date> fechaIngreso;

}