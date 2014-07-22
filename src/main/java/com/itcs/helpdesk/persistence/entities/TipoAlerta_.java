package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(TipoAlerta.class)
public class TipoAlerta_ { 

    public static volatile SingularAttribute<TipoAlerta, String> nombre;
    public static volatile SingularAttribute<TipoAlerta, Integer> idalerta;
    public static volatile SingularAttribute<TipoAlerta, String> descripcion;
    public static volatile ListAttribute<TipoAlerta, Caso> casoList;

}