/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.Funcion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jorge
 */
public enum EnumFunciones {

    AGREGAR_CASO(new Funcion(2, "AGREGAR CASO", "puede agregar casos")),
    EDITAR_CASO(new Funcion(3, "EDITAR CASO", "puede editar casos")),
    ELIMINAR_CASO(new Funcion(4, "ELIMINAR CASO", "puede eliminar casos")),
    CAMBIAR_CATEGORIA_CASO(new Funcion(5, "CAMBIAR CATEGORIA CASO", "puede cambiar categoria casos")),
    FILTRO_OWNER(new Funcion(7, "FILTRO OWNER", "")),
    ADMINISTRAR_VISTAS(new Funcion(22, "ADMINISTRAR VISTAS", "El usuario puede crear/editar/eliminar Vistas.")),
    SUPERVISOR(new Funcion(33, "SUPERVISOR", "El usuario puede supervisar a otros agentes.")),
    ASIGNAR_TRANSFERIR_CASO(new Funcion(8, "ASIGNAR/TRANSFERIR CASO", "puede asignar o transferir casos")),
    EDITAR_AJUSTES(new Funcion(9, "EDITAR AJUSTES", "puede editar ajustes")),
    RESPONDER_CUALQUIER_CASO(new Funcion(10, "RESPONDER CUALQUIER CASO", "puede responder cualquier caso")),
    EDITAR_CUALQUIER_CASO(new Funcion(11, "EDITAR CUALQUIER CASO", "puede editar cualquier caso")),
    ADMINISTRAR_ITEMS_PREENTREGA(new Funcion(12, "ADMINISTRAR ITEMS PREENTREGA", "Administrar items pre-entrega")),
    CREAR_CASO_PREENTREGA(new Funcion(13, "CREAR CASO PREENTREGA", "Crear caso pre-entrega"));
    
    private final Funcion funcion;

    EnumFunciones(Funcion funcion) {
        this.funcion = funcion;
    }

    public Funcion getFuncion() {
        return funcion;
    }

    public static List<Funcion> getAll() {
        ArrayList<Funcion> funciones = new ArrayList<Funcion>(values().length);
        for (EnumFunciones enumFuncion : values()) {
            funciones.add(enumFuncion.getFuncion());
        }
        return funciones;
    }
}