/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.FieldType;

/**
 *
 * @author jonathan
 */
public enum EnumFieldType {

    NUMBER(new FieldType("NUMBER", "Numero")), //Long, Integer, etc
    TEXT(new FieldType("TEXT", "Campo de Texto")), //String
    TEXTAREA(new FieldType("TEXTAREA", "Area de Texto")), //String
    RADIO(new FieldType("RADIO", "Radio")),  //Entity or String
    SELECTONE_ENTITY(new FieldType("SELECTONE_ENTITY", "Lista Drop-down (Tabla)")), //Entity
    SELECT_MANY_ENTITIES(new FieldType("SELECT_MANY_ENTITIES", "Lista de seleccion múltiple (Entidad)")), //several Entities
    COMMA_SEPARATED_VALUELIST(new FieldType("COMMA_SEPARATED_VALUELIST", "Varios valores (separados por coma)")), //Entity or String
    SELECTONE(new FieldType("SELECTONE", "Lista Drop-down Simple")), //String
    CHECKBOX(new FieldType("CHECKBOX", "CheckBox")), //Boolean
    SELECTMANY(new FieldType("SELECTMANY", "Seleccion multiple")), //Entity or String
    SELECTONE_PLACE_HOLDER(new FieldType("SELECTONE_PLACE_HOLDER", "Lista selección simple")), //Entity
    CALENDAR(new FieldType("CALENDAR", "Fecha")); //Date or String Date
    //CALENDAR_PERIOD(new FieldType("CALENDAR_PERIOD", "Periodo de Fechas")),; //Date or String Date
    private FieldType fieldType;

    private EnumFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return the fieldType
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return this.name();
    }

   
}
