package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Canal.class)
public class Canal_ { 

    public static volatile SingularAttribute<Canal, String> nombre;
    public static volatile SingularAttribute<Canal, String> descripcion;
    public static volatile SingularAttribute<Canal, String> idCanal;
    public static volatile ListAttribute<Canal, Caso> casoList;

}