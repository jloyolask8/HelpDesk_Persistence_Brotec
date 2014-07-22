package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.NombreAccion;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Accion.class)
public class Accion_ { 

    public static volatile SingularAttribute<Accion, ReglaTrigger> idTrigger;
    public static volatile SingularAttribute<Accion, String> parametros;
    public static volatile SingularAttribute<Accion, NombreAccion> idNombreAccion;
    public static volatile SingularAttribute<Accion, Integer> idAccion;

}