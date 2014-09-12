package com.itcs.helpdesk.persistence.entityenums;

/**
 *
 * @author jorge
 */
public enum EnumTipoAsocClienteProducto {

    PROPIETARIO("Propietario"),
    FAMILIAR_PROPIETARIO("Familiar del Propietario"),
    COTIZANTE("Cotizante"),
    OTRO("Otro");

    private final String tipoAsociacion;

    EnumTipoAsocClienteProducto(String tipoAsociacion) {
        this.tipoAsociacion = tipoAsociacion;
    }

    public String getTipoAsociacion() {
        return tipoAsociacion;
    }
}
