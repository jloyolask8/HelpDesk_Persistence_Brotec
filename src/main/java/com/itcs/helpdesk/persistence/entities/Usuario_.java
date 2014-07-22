package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Documento;
import com.itcs.helpdesk.persistence.entities.Grupo;
import com.itcs.helpdesk.persistence.entities.Nota;
import com.itcs.helpdesk.persistence.entities.Rol;
import com.itcs.helpdesk.persistence.entities.Usuario;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> telMovil;
    public static volatile ListAttribute<Usuario, Grupo> grupoList;
    public static volatile SingularAttribute<Usuario, String> apellidos;
    public static volatile SingularAttribute<Usuario, String> theme;
    public static volatile ListAttribute<Usuario, Documento> documentoList;
    public static volatile SingularAttribute<Usuario, String> telFijo;
    public static volatile SingularAttribute<Usuario, Boolean> activo;
    public static volatile SingularAttribute<Usuario, Boolean> editable;
    public static volatile SingularAttribute<Usuario, String> pass;
    public static volatile SingularAttribute<Usuario, String> idUsuario;
    public static volatile SingularAttribute<Usuario, String> nombres;
    public static volatile SingularAttribute<Usuario, Usuario> supervisor;
    public static volatile SingularAttribute<Usuario, String> email;
    public static volatile SingularAttribute<Usuario, String> rut;
    public static volatile ListAttribute<Usuario, Usuario> usuarioList;
    public static volatile ListAttribute<Usuario, Rol> rolList;
    public static volatile ListAttribute<Usuario, Nota> notaList;
    public static volatile ListAttribute<Usuario, Caso> casoList;

}