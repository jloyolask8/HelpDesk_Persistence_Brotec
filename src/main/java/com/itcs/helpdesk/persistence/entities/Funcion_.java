package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Rol;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Funcion.class)
public class Funcion_ { 

    public static volatile SingularAttribute<Funcion, String> nombre;
    public static volatile SingularAttribute<Funcion, String> descripcion;
    public static volatile ListAttribute<Funcion, Rol> rolList;
    public static volatile SingularAttribute<Funcion, Integer> idFuncion;
    public static volatile SingularAttribute<Funcion, String> outcome;

}