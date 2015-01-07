package com.itcs.helpdesk.persistence.entities.metadata;

import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.TipoAccion;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(TipoAccion.class)
public class NombreAccion_ { 

    public static volatile SingularAttribute<TipoAccion, String> nombre;
    public static volatile SingularAttribute<TipoAccion, String> idNombreAccion;
    public static volatile SingularAttribute<TipoAccion, String> descripcion;
    public static volatile ListAttribute<TipoAccion, Accion> accionList;

}