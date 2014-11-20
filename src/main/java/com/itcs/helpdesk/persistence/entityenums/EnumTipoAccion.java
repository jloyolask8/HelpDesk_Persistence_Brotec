package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoAccion;

/**
 *
 * @author jorge
 */
public enum EnumTipoAccion {
    
    DEFINIR_SLA_FECHA_COMPRA(new TipoAccion("REDEFINIR SLA FECHA COMPRA","Redefine SLA a la fecha de compra", "Se redefine el tiempo de SLA a la fecha definida como fecha de compra", null)),
    ASIGNAR_A_USUARIO(new TipoAccion("ASIGNAR A USUARIO", "Asignar caso", "El caso se le asigna al usuario seleccionado", null)),
    ASIGNAR_A_GRUPO(new TipoAccion("ASIGNAR A GRUPO", "Asignar caso a un grupo", "El caso se le asigna al usuario "
            + "disponible que tenga menos casos asignados en el grupo seleccionado", null)),
    ASIGNAR_A_AREA(new TipoAccion("ASIGNAR A AREA", "Asignar caso a un Área", "El caso se asigna al area seleccionada.", null)),
    CUSTOM(new TipoAccion("CUSTOM ACTION CLASS", "Accion Custom (class)", "se ejecutara la funcion execute() de la Accion custom..", null)),
    ENVIAR_EMAIL(new TipoAccion("ENVIAR EMAIL", "Enviar email", "Se envia un correo electronico", null)),
    RECALCULAR_SLA(new TipoAccion("RECALC SLA", "Recalcular SLA", "Se recalcula el tiempo de SLA (Service Level Agreement)", null)),
    CAMBIAR_PRIORIDAD(new TipoAccion("CAMBIA PRIORIDAD", "Cambio de prioridad", "Se cambia la prioridad del caso", null)),
    DEFINIR_TIPO(new TipoAccion("DEFINE TIPO CASO", "Define tipo caso", "Se define el tipo de caso", null)),
    /**
     * custom class actions *
     */
    ENVIAR_CASO_POR_EMAIL(new TipoAccion("ENVIAR_CASO_POR_EMAIL", "Enviar los datos del caso por email", "Se envia un correo electronico.",
            "com.itcs.helpdesk.rules.actionsimpl.SendCaseByEmailAction")),
    NOTIFICAR_CASO_AL_GRUPO(new TipoAccion("NOTIFICAR_CASO_AL_GRUPO", "Notificar del caso a los miembros del grupo", "Notificar del caso a los miembros del grupo que corresponda atender el caso , correo electronico.",
            "com.itcs.helpdesk.rules.actionsimpl.NotifyGroupCasoReceivedAction")),
    CREAR_CASO_VISITA_REP_SELLOS(new TipoAccion("CREAR_CASO_VISITA_REP_SELLOS", "CREAR_CASO_VISITA_REP_SELLOS", "",
            "com.itcs.helpdesk.rules.actionsimpl.CrearCasoVisitaRepSellosAction"));

    private TipoAccion accion;

    EnumTipoAccion(TipoAccion accion) {
        this.accion = accion;
    }

    public TipoAccion getNombreAccion() {
        return accion;
    }
}
