package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoCanal;

/**
 *
 * @author jorge
 */
public enum EnumTipoCanal
{
    EMAIL(new TipoCanal("EMAIL", "Email")),
    MANUAL(new TipoCanal("MANUAL", "Manual")),
    FORMULARIO(new TipoCanal("FORMULARIO", "Formulario")),
    CHAT(new TipoCanal("CHAT", "GoLiveChat")),
    TWITTER(new TipoCanal("TWITTER", "Twitter")),
    FACEBOOK(new TipoCanal("FACEBOOK", "Facebook")),
    ;

    private TipoCanal canal;

    EnumTipoCanal(TipoCanal canal)
    {
        this.canal = canal;
    }

    public TipoCanal getTipoCanal()
    {
        return canal;
    }
}
