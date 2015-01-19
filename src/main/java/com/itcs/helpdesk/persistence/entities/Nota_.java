package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Nota;
import com.itcs.helpdesk.persistence.entities.TipoNota;
import com.itcs.helpdesk.persistence.entities.TipoNota;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Nota.class)
public class Nota_ { 

    public static volatile SingularAttribute<Nota, String> texto;
    public static volatile SingularAttribute<Nota, Boolean> visible;
    public static volatile SingularAttribute<Nota, Date> fechaCreacion;
    public static volatile SingularAttribute<Nota, Date> fechaModificacion;
    public static volatile SingularAttribute<Nota, Usuario> creadaPor;
    public static volatile SingularAttribute<Nota, String> enviadoPor;
    public static volatile SingularAttribute<Nota, Integer> idNota;
    public static volatile SingularAttribute<Nota, TipoNota> idTipoNota;
    public static volatile SingularAttribute<Nota, Caso> idCaso;

}