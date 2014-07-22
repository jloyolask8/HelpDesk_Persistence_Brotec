package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Categoria;
import com.itcs.helpdesk.persistence.entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Grupo.class)
public class Grupo_ { 

    public static volatile SingularAttribute<Grupo, String> nombre;
    public static volatile ListAttribute<Grupo, Categoria> categoriaList;
    public static volatile SingularAttribute<Grupo, String> idGrupo;
    public static volatile SingularAttribute<Grupo, Area> idArea;
    public static volatile ListAttribute<Grupo, Usuario> usuarioList;
    public static volatile SingularAttribute<Grupo, String> descripcion;
    public static volatile SingularAttribute<Grupo, Boolean> editable;

}