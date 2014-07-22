package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Componente;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(SubComponente.class)
public class SubComponente_ { 

    public static volatile SingularAttribute<SubComponente, String> nombre;
    public static volatile SingularAttribute<SubComponente, String> idSubComponente;
    public static volatile SingularAttribute<SubComponente, String> descripcion;
    public static volatile SingularAttribute<SubComponente, Componente> idComponente;
    public static volatile ListAttribute<SubComponente, Caso> casoList;

}