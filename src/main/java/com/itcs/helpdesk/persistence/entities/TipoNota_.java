package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Nota;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(TipoNota.class)
public class TipoNota_ { 

    public static volatile SingularAttribute<TipoNota, String> nombre;
    public static volatile SingularAttribute<TipoNota, String> descripcion;
    public static volatile ListAttribute<TipoNota, Nota> notaList;
    public static volatile SingularAttribute<TipoNota, Integer> idTipoNota;

}