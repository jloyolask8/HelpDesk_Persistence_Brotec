package com.itcs.helpdesk.persistence.entities.metadata;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(SubEstadoCaso.class)
public class SubEstadoCaso_ { 

    public static volatile SingularAttribute<SubEstadoCaso, String> nombre;
    public static volatile SingularAttribute<SubEstadoCaso, String> descripcion;
    public static volatile SingularAttribute<SubEstadoCaso, EstadoCaso> idEstado;
    public static volatile SingularAttribute<SubEstadoCaso, String> idSubEstado;
    public static volatile SingularAttribute<SubEstadoCaso, Boolean> editable;
    public static volatile ListAttribute<SubEstadoCaso, Caso> casoList;

}