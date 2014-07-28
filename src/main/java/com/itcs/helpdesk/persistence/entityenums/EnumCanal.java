package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.Canal;

/**
 *
 * @author jorge
 */
public enum EnumCanal
{
    //INTERNO(new Canal("INTERNO", "Interno", "Caso creado internamente o colaborativo creado por otro usuario", EnumTipoCanal.MANUAL.getTipoCanal())),
    WEBSERVICE(new Canal("WEBSERVICE", "Web Service", "Caso recibido desde un cliente del Web Service.", EnumTipoCanal.FORMULARIO.getTipoCanal())),
    CHAT(new Canal("CHAT", "Chat", "Caso recibido desde chat en pagina web", EnumTipoCanal.CHAT.getTipoCanal())),
    MANUAL(new Canal("MANUAL", "Manual", "Caso creado manualmente en el sistema", EnumTipoCanal.MANUAL.getTipoCanal()));

    private Canal canal;

    EnumCanal(Canal canal)
    {
        this.canal = canal;
    }

    public Canal getCanal()
    {
        return canal;
    }
}
