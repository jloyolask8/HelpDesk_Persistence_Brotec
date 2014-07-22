package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoNota;

/**
 *
 * @author jorge
 */
public enum EnumTipoNota {

    NOTA(new TipoNota(1, "Comentario"),"comentario"),
    RESPUESTA_A_CLIENTE(new TipoNota(2, "Respuesta"),"resptoclient"),
    RESPUESTA_DE_CLIENTE(new TipoNota(3, "Comentario del Solicitante"), "respfromclient"),
    BORRADOR_RESPUESTA_A_CLIENTE(new TipoNota(4, "Borrador"),"borrador"),
    RESPUESTA_SERVIDOR(new TipoNota(5, "Respuesta del servidor"),"respfromserv"),
    LLAMADA_A_CLIENTE(new TipoNota(6, "LLamada"),"calltoclient"),
    TRANSFERENCIA_CASO(new TipoNota(7, "Transferencia"),"nota"),
    NOTIFICACION_UPDATE_CASO(new TipoNota(8, "Notificación por email"),"notificacion");
    private TipoNota tipoNota;
    private String style;

    EnumTipoNota(TipoNota tipoNota, String style) {
        this.tipoNota = tipoNota;
        this.style = style;
    }

    public TipoNota getTipoNota() {
        return tipoNota;
    }
    
    public String getStyle()
    {
        return style;
    }
}
