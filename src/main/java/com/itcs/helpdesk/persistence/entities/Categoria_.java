package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Area;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Categoria;
import com.itcs.helpdesk.persistence.entities.Grupo;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Categoria.class)
public class Categoria_ { 

    public static volatile SingularAttribute<Categoria, Integer> idCategoria;
    public static volatile SingularAttribute<Categoria, String> nombre;
    public static volatile SingularAttribute<Categoria, Integer> orden;
    public static volatile ListAttribute<Categoria, Grupo> grupoList;
    public static volatile ListAttribute<Categoria, Categoria> categoriaList;
    public static volatile SingularAttribute<Categoria, Area> idArea;
    public static volatile ListAttribute<Categoria, SubEstadoCaso> subEstadoCasoList;
    public static volatile SingularAttribute<Categoria, Categoria> idCategoriaPadre;
    public static volatile SingularAttribute<Categoria, Boolean> editable;
    public static volatile ListAttribute<Categoria, Caso> casoList;

}