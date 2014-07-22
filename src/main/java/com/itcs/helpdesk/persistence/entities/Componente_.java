package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Producto;
import com.itcs.helpdesk.persistence.entities.SubComponente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Componente.class)
public class Componente_ { 

    public static volatile SingularAttribute<Componente, String> nombre;
    public static volatile SingularAttribute<Componente, String> descripcion;
    public static volatile SingularAttribute<Componente, Producto> idProducto;
    public static volatile SingularAttribute<Componente, String> idComponente;
    public static volatile ListAttribute<Componente, SubComponente> subComponenteList;
    public static volatile ListAttribute<Componente, Caso> casoList;

}