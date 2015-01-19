package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Condicion;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import com.itcs.helpdesk.persistence.entities.TipoComparacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Condicion.class)
public class Condicion_ { 

    public static volatile SingularAttribute<Condicion, ReglaTrigger> idTrigger;
    public static volatile SingularAttribute<Condicion, String> valor;
    public static volatile SingularAttribute<Condicion, TipoComparacion> idComparador;
    public static volatile SingularAttribute<Condicion, String> idCampo;
    public static volatile SingularAttribute<Condicion, Integer> idCondicion;

}