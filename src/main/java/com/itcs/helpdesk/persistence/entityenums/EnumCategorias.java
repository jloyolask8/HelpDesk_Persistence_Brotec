/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.Categoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public enum EnumCategorias {
    CATEGORIA_EJEMPLO(new Categoria(0, "TODAS", true, 0, EnumAreas.DEFAULT_AREA.getArea()), true);
//    CLIENTE_CATEGORIA_A(new Categoria(1111, "CLIENTE_CATEGORIA_A", true, 1, EnumAreas.DEFAULT_AREA.getArea()), true),
//    CLIENTE_CATEGORIA_B(new Categoria(2222, "CLIENTE_CATEGORIA_B", true, 2, EnumAreas.DEFAULT_AREA.getArea()), true),
//    CLIENTE_CATEGORIA_C(new Categoria(3333, "CLIENTE_CATEGORIA_C", true, 3, EnumAreas.DEFAULT_AREA.getArea()), true);
    //public Categoria(Integer idCategoria, String nombre, boolean editable, Integer orden, Area idArea) {
    
    private Categoria categoria;
    private boolean persistente;
    
    EnumCategorias(Categoria categoria, boolean persistente)
    {
        this.categoria = categoria;
        this.persistente = persistente;
    }
    
    public Categoria getCategoria()
    {
        return this.categoria;
    }
    
    public static List<Categoria> getAll()
    {
        ArrayList<Categoria> lista = new ArrayList<Categoria>(values().length);
        for (EnumCategorias enumCat : values()) {
            if(enumCat.isPersistente()){
            lista.add(enumCat.getCategoria());
            }
        }
        return lista;
    }

    /**
     * @return the persistente
     */
    public boolean isPersistente() {
        return persistente;
    }
}
