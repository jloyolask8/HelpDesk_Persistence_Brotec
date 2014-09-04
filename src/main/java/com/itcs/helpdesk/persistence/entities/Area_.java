package com.itcs.helpdesk.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Area.class)
public class Area_ { 

    public static volatile SingularAttribute<Area, String> nombre;
    public static volatile ListAttribute<Area, Grupo> grupoList;
    public static volatile SingularAttribute<Area, String> idArea;
    public static volatile SingularAttribute<Area, String> descripcion;
    public static volatile ListAttribute<Area, ReglaTrigger> reglaTriggerList;
    public static volatile SingularAttribute<Area, Boolean> editable;

}