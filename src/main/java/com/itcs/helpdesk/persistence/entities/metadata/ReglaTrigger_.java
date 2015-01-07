package com.itcs.helpdesk.persistence.entities.metadata;

import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.Accion;
import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Condicion;
import com.itcs.helpdesk.persistence.entities.Condicion;
import com.itcs.helpdesk.persistence.entities.ReglaTrigger;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(ReglaTrigger.class)
public class ReglaTrigger_ { 

    public static volatile SingularAttribute<ReglaTrigger, String> idTrigger;
    public static volatile SingularAttribute<ReglaTrigger, Boolean> reglaActiva;
    public static volatile SingularAttribute<ReglaTrigger, Area> idArea;
    public static volatile ListAttribute<ReglaTrigger, Condicion> condicionList;
    public static volatile SingularAttribute<ReglaTrigger, String> desccripcion;
    public static volatile ListAttribute<ReglaTrigger, Accion> accionList;

}