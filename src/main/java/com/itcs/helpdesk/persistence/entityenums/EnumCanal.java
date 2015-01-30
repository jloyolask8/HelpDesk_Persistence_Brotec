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
    SISTEMA(new Canal("SISTEMA", "Sistema", "Canal de comunicación del sistema", EnumTipoCanal.FORMULARIO.getTipoCanal())),
    GODESK_CUSTOMER_PORTAL(new Canal("GODESK_CUSTOMER_PORTAL", "Portal del consumidor", "Portal del consumidor - formulario portal web de godesk", EnumTipoCanal.FORMULARIO.getTipoCanal())),
    GODESK_CUSTOMER_PORTAL_EMBEDDED_FORM(new Canal("GODESK_CUSTOMER_PORTAL_EMBEDDED_FORM", "Formulario embebido", "Portal del consumidor - form embebido en su sitio web", EnumTipoCanal.FORMULARIO.getTipoCanal())),
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
