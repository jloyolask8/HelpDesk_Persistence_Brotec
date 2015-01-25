package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoCanal;

/**
 *
 * @author jorge
 */
public enum EnumTipoCanal {

    EMAIL(new TipoCanal("EMAIL", "Email", "fa fa-envelope")),
    MANUAL(new TipoCanal("MANUAL", "Manually inside Godesk", "fa fa-list-alt")),
    FORMULARIO(new TipoCanal("FORMULARIO", "Formulario web online", "fa fa-link")),
    CHAT(new TipoCanal("CHAT", "Godesk LiveChat", "fa fa-comments")),
    PHONE(new TipoCanal("PHONE", "Telephone", "fa fa-phone-square")),
    APPLICATION(new TipoCanal("APPLICATION", "Godesk Web Portal", "fa fa-list-alt")),
    TWITTER(new TipoCanal("TWITTER", "Twitter", "fa fa-twitter-square")),
    FACEBOOK(new TipoCanal("FACEBOOK", "Facebook", "fa fa-facebook-square"));

    private TipoCanal canal;

    EnumTipoCanal(TipoCanal canal) {
        this.canal = canal;
    }

    public TipoCanal getTipoCanal() {
        return canal;
    }
}
