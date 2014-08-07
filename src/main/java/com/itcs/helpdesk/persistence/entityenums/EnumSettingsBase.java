/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.AppSetting;

/**
 *
 * @author jorge
 */
public enum EnumSettingsBase {

    //TODO fix the fucking bug
//TODO fix the fucking bug
    COMPANY_NAME(new AppSetting("COMPANY_NAME", "Nombre de su empresa", "", "app", "input", 1, "", true)),
    HELPDESK_TITLE(new AppSetting("HELPDESK_TITLE", "Titulo Helpdesk", "Itcs HelpDesk", "app", "input", 2, "", true)),
    COMPANY_LOGO_ID_ATTACHMENT(new AppSetting("COMPANY_LOGO_ID_ATTACHMENT", "Logo de su empresa", "0", "app", "inputfile", 3, "Logo a mostrar en la pagina del cliente.", false)),
    SHOW_COMPANY_LOGO(new AppSetting("SHOW_COMPANY_LOGO", "Mostrar el logo", "true", "app", "booleanchoice", 4, "", true)),
    COMPANY_LOGO_SIZE(new AppSetting("COMPANY_LOGO_SIZE", "Tamaño logo", "100", "app", "input", 4, "", false)),
    DEFAULT_THEME(new AppSetting("DEFAULT_THEME", "Tema por defecto", "itcs-theme", "app", "theme", 5, "", false)),
    PORTAL_WEB_URL_CASO(new AppSetting("PORTAL_WEB_URL_CASO", "Link al caso (clientes)", "", "app", "input", 6, "", true)),
    COMPANY_HELPDESK_SITE_URL(new AppSetting("COMPANY_HELPDESK_SITE_URL", "Url Sitio Web helpdesk", "", "app", "input", 7, "", true)),
    PRODUCT_DESCRIPTION(new AppSetting("PRODUCT_DESCRIPTION", "Producto -> Nombre", "", "app", "input", 8, "", true)),
    PRODUCT_COMP_DESCRIPTION(new AppSetting("PRODUCT_COMP_DESCRIPTION", "Producto -> Nombre Componente", "", "app", "input", 9, "", true)),
    PRODUCT_SUBCOMP_DESCRIPTION(new AppSetting("PRODUCT_SUBCOMP_DESCRIPTION", "Producto -> Nombre Sub Componente", "", "app", "input", 10, "", true)),
    SALUDO_CLIENTE_HOMBRE(new AppSetting("SALUDO_CLIENTE_HOMBRE", "Saludo al cliente (sexo masculino)", "Estimado", "app", "input", 11, "", true)),
    SALUDO_CLIENTE_MUJER(new AppSetting("SALUDO_CLIENTE_MUJER", "Saludo al cliente (sexo femenino)", "Estimada", "app", "input", 12, "", true)),
    SALUDO_CLIENTE_UNKNOWN(new AppSetting("SALUDO_CLIENTE_UNKNOWN", "Saludo al cliente (sexo desconocido)", "Estimad@", "app", "input", 13, "", true)),
    //--
    SEND_NOTIFICATION_ON_TRANSFER(new AppSetting("SEND_NOTIFICATION_ON_TRANSFER", "Notificar al agente cuando se asigne un caso", "true", "app", "booleanchoice", 14, "", true)),
    NOTIFICATION_SUBJECT_TEXT(new AppSetting("NOTIFICATION_SUBJECT_TEXT", "Asunto Nofiticación", "Se le ha asignado un caso.", "app", "input", 15, "", true)),
    NOTIFICATION_BODY_TEXT(new AppSetting("NOTIFICATION_BODY_TEXT", "Cuerpo mensaje Nofiticación",
            "<div>\n"
            + "<p>Estimad@ Agente <strong>${NombreAgente}</strong>,</p>\n"
            + "\n"
            + "<p>Le notificamos que le han asignado el caso ${TipoCaso} N&deg;#[${NumeroCaso}]: ${Asunto}</p>\n"
            + "\n"
            + "<hr />\n"
            + "<p><span style=\"color:rgb(68, 68, 68)\">${Descripcion}</span>&nbsp;</p>\n"
            + "\n"
            + "<hr />\n"
            + "<div style=\"background:#eee;border:1px solid #ccc;padding:5px 10px;\"><small>Para una pronta atenci&oacute;n favor ingrese a nuestro portal de servicio al cliente <a href=\"\" target=\"_blank\">GoDesk</a>. O si lo desea puede responder a este correo directamente y esta respuesta sera enviada al cliente &nbsp;${NombreCliente} a su direcci&oacute;n ${EmailCliente}. Tambi&eacute;n est&aacute; permitido atachar documentos.</small><br />\n"
            + "&nbsp;</div>\n"
            + "\n"
            + "<p>Atentamente,</p>\n"
            + "\n"
            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\n"
            + "	<tbody>\n"
            + "		<tr>\n"
            + "			<td>logo</td>\n"
            + "			<td>\n"
            + "			<p>Su Equipo de Servicio al Cliente.</p>\n"
            + "\n"
            + "			<p><br />\n"
            + "			<a href=\"\">www.suempresa.cl</a></p>\n"
            + "			</td>\n"
            + "		</tr>\n"
            + "	</tbody>\n"
            + "</table>\n"
            + "\n"
            + "<hr />\n"
            + "<p><small>Powered by <strong>GoDesk</strong>, <a href=\"http://www.godesk.cl\" target=\"_blank\">www.godesk.cl</a></small></p>\n"
            + "</div>", "app", "inputhtml", 16, "", true)),
    //--
    SEND_NOTIFICATION_TOCLIENT_ON_NEW_TICKET(new AppSetting("SEND_NOTIFICATION_TOCLIENT_ON_NEW_TICKET", "Notificar acuse de recibo al cliente cuando llegue un caso", "true", "app", "booleanchoice", 17, "", true)),
    NOTIFICATION_NEW_TICKET_CLIENT_SUBJECT_TEXT(new AppSetting("NOTIFICATION_NEW_TICKET_CLIENT_SUBJECT_TEXT", "Asunto Nofiticación al cliente", "${TipoCaso}:${Asunto}", "app", "input", 18, "", true)),
    NOTIFICATION_NEW_TICKET_CLIENT_BODY_TEXT(new AppSetting("NOTIFICATION_NEW_TICKET_CLIENT_BODY_TEXT", "Cuerpo mensaje Nofiticación Cliente",
            "<div>\n"
            + "<p>Estimad@ <strong>${NombreCliente}</strong>,</p>\n"
            + "\n"
            + "<p>Hemos procesado su solicitud y se ha creado un caso con n&uacute;mero de servicio #<strong>${NumeroCaso}</strong>.<br />\n"
            + "Uno de nuestros ejecutivos le enviar&aacute; una respuesta a la brevedad.</p>\n"
            + "Si desea ver el estado de su caso, o agregar comentarios, favor visite nuestro portal de servicio al cliente:<br />\n"
            + "<a href=\"\" target=\"_blank\">aqu&iacute;</a>. O si lo desea puede respondernos a este correo directamente.<br />\n"
            + "&nbsp;\n"
            + "<p>Atentamente,</p>\n"
            + "\n"
            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\n"
            + "	<tbody>\n"
            + "		<tr>\n"
            + "			<td>logo</td>\n"
            + "			<td>\n"
            + "			<p>Equipo de Servicio al Cliente.</p>\n"
            + "\n"
            + "			<p><Empresa><br/>\n"
            + "			<a href=\"\">www.suempresa.cl</a></p>\n"
            + "			</td>\n"
            + "		</tr>\n"
            + "	</tbody>\n"
            + "</table>\n"
            + "\n"
            + "<hr />\n"
            + "<p><small>Powered by <strong>GoDesk</strong>, <a href=\"http://www.godesk.cl\" target=\"_blank\">www.godesk.cl</a></small></p>\n"
            + "</div>", "app", "inputhtml", 19, 
            "Cuando un nuevo caso llegue este mensaje de recepción será enviado. Nota: Los departamentos o areas podrían tener su propio texto, se prioriza el texto del Area que corresponda.", true)),
    NOTIFICATION_UPDATE_CLIENT_SUBJECT_TEXT(new AppSetting("NOTIFICATION_UPDATE_CLIENT_SUBJECT_TEXT", "Asunto Nofiticación de actualización de caso, al Cliente", "su caso ha sido actualizado.", "app", "input", 20, "", true)),
    NOTIFICATION_UPDATE_CLIENT_BODY_TEXT(new AppSetting("NOTIFICATION_UPDATE_CLIENT_BODY_TEXT", "Cuerpo mensaje Nofiticación de actualización de caso, al Cliente",
            "<div>\n"
            + "<p>${SaludoCliente} <strong>${NombreCliente}</strong>,</p>\n"
            + "\n"
            + "<p>Le informamos que su caso N°#${NumeroCaso} ha sido actualizado.</p>\n"
            + "Si desea ver el estado de su caso, o agregar comentarios, favor visite nuestro portal de servicio al cliente:<br />\n"
            + "<a href=\"\" target=\"_blank\">aqu&iacute;</a>.<br />\n"
            + "&nbsp;\n"
            + "<p>Atentamente,</p>\n"
            + "\n"
            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\n"
            + "	<tbody>\n"
            + "		<tr>\n"
            + "			<td>logo</td>\n"
            + "			<td>\n"
            + "			<p>Equipo de Servicio al Cliente.</p>\n"
            + "\n"
            + "			<p><Empresa><br/>\n"
            + "			<a href=\"\">www.suempresa.cl</a></p>\n"
            + "			</td>\n"
            + "		</tr>\n"
            + "	</tbody>\n"
            + "</table>\n"
            + "\n"
            + "<hr />\n"
            + "<p><small>Powered by <strong>GoDesk</strong>, <a href=\"http://www.godesk.cl\" target=\"_blank\">www.godesk.cl</a></small></p>\n"
            + "</div>", "app", "inputhtml", 21, "", true)),
    
    SEND_NOTIFICATION_TOCLIENT_ON_SUBSCRIBED_TO_EVENT(new AppSetting("SEND_NOTIFICATION_TOCLIENT_ON_SUBSCRIBED_TO_EVENT", "Notificar acuse de recibo cuando un cliente se inscriba a un evento", "true", "app", "booleanchoice", 22, "", true)),
    NOTIF_SUBSCRIBED_EVENT_CLIENT_SUBJECT(new AppSetting("NOTIF_SUBSCRIBED_EVENT_CLIENT_SUBJECT", "Asunto Nofiticación al cliente (Inscripción Evento)", "${TipoCaso}:${Asunto}", "app", "input", 23, "", true)),
    NOTIFICATION_SUBSCRIBED_TO_EVENT_CLIENT_BODY_TEXT(new AppSetting("NOTIFICATION_SUBSCRIBED_TO_EVENT_CLIENT_BODY_TEXT", "Cuerpo mensaje Nofiticación Cliente (Inscripción Evento)",
            "<div>\n"
            + "<p>Estimad@ <strong>${NombreCliente}</strong>,</p>\n"
            + "\n"
            + "<p>su #{TipoCaso} se realizó con éxito. Nuestros ejecutivos lo contactarán para coordinar su asistencia a la actividad.</p>\n"
            + "<p>Atentamente,</p>\n"
            + "\n"
            + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"10\">\n"
            + "	<tbody>\n"
            + "		<tr>\n"
            + "			<td>logo</td>\n"
            + "			<td>\n"
            + "			<p>Equipo de Servicio al Cliente.</p>\n"
            + "\n"
            + "			<p><Empresa><br/>\n"
            + "			<a href=\"\">www.suempresa.cl</a></p>\n"
            + "			</td>\n"
            + "		</tr>\n"
            + "	</tbody>\n"
            + "</table>\n"
            + "\n"
            + "<hr />\n"
            + "<p><small>Powered by <strong>GoDesk</strong>, <a href=\"http://www.godesk.cl\" target=\"_blank\">www.godesk.cl</a></small></p>\n"
            + "</div>", "app", "inputhtml", 24, 
            "Cuando un cliente se inscriba en un evento este mensaje de recepción será enviado.", true)),
    
    
    DEBUG_ENABLED(new AppSetting("DEBUG_ENABLED", "AppDebug", "false", "admin", "booleanchoice", 50, "Habilitar esta opcion para realizar un diagnostico de la ejecucion del sistema (Herramienta de diagnostico de problemas para Soporte).", true));
//    SEND_GROUP_NOTIFICATION_ON_NEW_CASE(new AppSetting("SEND_GROUP_NOTIFICATION_ON_NEW_CASE", "Notificar a el/los Grupo(s)", "true", "app", "booleanchoice", 22, "Notificar a el/los Grupo(s) encargado(s) del producto cuando llegue un nuevo caso", true));
    private AppSetting appSetting;

    EnumSettingsBase(AppSetting appSetting) {
        this.appSetting = appSetting;
    }

    public AppSetting getAppSetting() {
        return this.appSetting;
    }

    @Override
    public String toString() {
        return appSetting.getSettingKey();
    }
}
