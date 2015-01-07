package com.itcs.helpdesk.persistence.entities.metadata;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Documento;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Documento.class)
public class Documento_ { 

    public static volatile SingularAttribute<Documento, String> solucion;
    public static volatile SingularAttribute<Documento, String> problema;
    public static volatile SingularAttribute<Documento, Boolean> visible;
    public static volatile SingularAttribute<Documento, Usuario> createdBy;
    public static volatile SingularAttribute<Documento, Date> updatedDate;
    public static volatile SingularAttribute<Documento, Integer> idDocumento;
    public static volatile SingularAttribute<Documento, Date> createdDate;
    public static volatile SingularAttribute<Documento, String> causa;
    public static volatile ListAttribute<Documento, Caso> casoList;

}