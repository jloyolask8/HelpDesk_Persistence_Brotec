package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoNota;

/**
 *
 * @author jorge
 */
public enum EnumTipoNota {

    NOTA(new TipoNota(1, "Comentario", "fa fa-comment"), "comentario"),
    NOTA_CIERRE(new TipoNota(12, "Nota de Cierre", "fa fa-lock"), "notacierre"),
    RESPUESTA_A_CLIENTE(new TipoNota(2, "Respuesta", "fa fa-envelope"), "resptoclient"),
    RESPUESTA_DE_CLIENTE(new TipoNota(3, "Comentario del Solicitante", "fa fa-comments"), "respfromclient"),
    BORRADOR_RESPUESTA_A_CLIENTE(new TipoNota(4, "Borrador", "fa fa-disk"), "borrador"),
    RESPUESTA_SERVIDOR(new TipoNota(5, "Respuesta del servidor", "fa fa-terminal"), "respfromserv"),
    LLAMADA_A_CLIENTE(new TipoNota(6, "LLamada", "fa fa-phone"), "calltoclient"),
    TRANSFERENCIA_CASO(new TipoNota(7, "Transferencia", "fa fa-plane"), "nota"),
    NOTIFICACION_UPDATE_CASO(new TipoNota(8, "Notificación por email", "fa fa-envelope"), "notificacion"),
    REG_ENVIO_CORREO(new TipoNota(9, "Reg. de envío de correo", "fa fa-envelope"), "regenviocorreo"),
    RESPUESTA_AUT_CLIENTE(new TipoNota(10, "Respuesta Automática", "fa fa-terminal"), "respauttoclient"),
    SUSCRIPCION_EVENTO(new TipoNota(11, "Suscripción a Evento", "fa fa-calendar-o"), ""),
    COMENTARIO_AGENTE(new TipoNota(12, "Comentario del Agente", "fa fa-comment"), "agentcomment");
    private TipoNota tipoNota;
    private String style;

    EnumTipoNota(TipoNota tipoNota, String style) {
        this.tipoNota = tipoNota;
        this.style = style;
    }

    public TipoNota getTipoNota() {
        return tipoNota;
    }

    public String getStyle() {
        return style;
    }
}
