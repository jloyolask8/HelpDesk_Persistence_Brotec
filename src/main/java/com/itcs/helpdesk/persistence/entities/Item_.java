package com.itcs.helpdesk.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Integer> idItem;
    public static volatile SingularAttribute<Item, String> nombre;
    public static volatile SingularAttribute<Item, Integer> orden;
    public static volatile ListAttribute<Item, Item> itemList;
    public static volatile SingularAttribute<Item, Area> idArea;
    public static volatile SingularAttribute<Item, Item> idItemPadre;
    public static volatile SingularAttribute<Item, Boolean> editable;

}