package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.NombreAccion;

/**
 *
 * @author jorge
 */
public enum EnumNombreAccion {
    
//    CAMBIO_CAT(new NombreAccion("CAMBIO CAT","Cambio de categoría", "El caso se asocia a la categoría seleccionada")),
//    ASIGNAR_A_USUARIO(new NombreAccion("ASIGNAR A USUARIO","Asignar caso", "El caso se le asigna al usuario seleccionado")),
//    ASIGNAR_A_GRUPO(new NombreAccion("ASIGNAR A GRUPO","Asignar caso a un grupo", "El caso se le asigna al usuario "
//            + "disponible que tenga menos casos asignados en el grupo seleccionado")),
//    ASIGNAR_A_AREA(new NombreAccion("ASIGNAR A AREA","Asignar caso a un Área", "El caso se asigna al area seleccionada.")),
//    CUSTOM(new NombreAccion("CUSTOM ACTION CLASS","Accion Custom (class)", "se ejecutara la funcion execute() de la Accion custom..")),
////    ENVIAR_CASO_POR_EMAIL(new NombreAccion("ENVIAR_CASO_POR_EMAIL","Enviar los datos del caso por email", "Se envia un correo electronico.")),
//    ENVIAR_EMAIL(new NombreAccion("ENVIAR EMAIL","Enviar email", "Se envia un correo electronico")),
//    CAMBIAR_PRIORIDAD(new NombreAccion("CAMBIA PRIORIDAD","Cambio de prioridad", "Se cambia la prioridad del caso")),
//    RECALCULAR_SLA(new NombreAccion("RECALC SLA","Recalcular SLA", "Se recalcula el tiempo de SLA (Service Level Agreement)")),
//    DEFINIR_SLA_FECHA_COMPRA(new NombreAccion("REDEFINIR SLA FECHA COMPRA","Redefine SLA a la fecha de compra", "Se redefine el tiempo de SLA a la fecha definida como fecha de compra"));

    DEFINIR_SLA_FECHA_COMPRA(new NombreAccion("REDEFINIR SLA FECHA COMPRA","Redefine SLA a la fecha de compra", "Se redefine el tiempo de SLA a la fecha definida como fecha de compra", null)),
    //CAMBIO_CAT(new NombreAccion("CAMBIO CAT", "Cambio de categoría", "El caso se asocia a la categoría seleccionada", null)),
    ASIGNAR_A_USUARIO(new NombreAccion("ASIGNAR A USUARIO", "Asignar caso", "El caso se le asigna al usuario seleccionado", null)),
    ASIGNAR_A_GRUPO(new NombreAccion("ASIGNAR A GRUPO", "Asignar caso a un grupo", "El caso se le asigna al usuario "
            + "disponible que tenga menos casos asignados en el grupo seleccionado", null)),
    ASIGNAR_A_AREA(new NombreAccion("ASIGNAR A AREA", "Asignar caso a un Área", "El caso se asigna al area seleccionada.", null)),
    CUSTOM(new NombreAccion("CUSTOM ACTION CLASS", "Accion Custom (class)", "se ejecutara la funcion execute() de la Accion custom..", null)),
    ENVIAR_EMAIL(new NombreAccion("ENVIAR EMAIL", "Enviar email", "Se envia un correo electronico", null)),
    RECALCULAR_SLA(new NombreAccion("RECALC SLA", "Recalcular SLA", "Se recalcula el tiempo de SLA (Service Level Agreement)", null)),
    CAMBIAR_PRIORIDAD(new NombreAccion("CAMBIA PRIORIDAD", "Cambio de prioridad", "Se cambia la prioridad del caso", null)),
    /**
     * actions *
     */
    ENVIAR_CASO_POR_EMAIL(new NombreAccion("ENVIAR_CASO_POR_EMAIL", "Enviar los datos del caso por email", "Se envia un correo electronico.",
            "com.itcs.helpdesk.rules.actionsimpl.SendCaseByEmailAction")),
    NOTIFICAR_CASO_AL_GRUPO(new NombreAccion("NOTIFICAR_CASO_AL_GRUPO", "Notificar del caso a los miembros del grupo", "Notificar del caso a los miembros del grupo que corresponda atender el caso , correo electronico.",
            "com.itcs.helpdesk.rules.actionsimpl.NotifyGroupCasoReceivedAction")),
    CREAR_CASO_VISITA_REP_SELLOS(new NombreAccion("CREAR_CASO_VISITA_REP_SELLOS", "CREAR_CASO_VISITA_REP_SELLOS", "",
            "com.itcs.helpdesk.rules.actionsimpl.CrearCasoVisitaRepSellosAction"));

    private NombreAccion accion;

    EnumNombreAccion(NombreAccion accion) {
        this.accion = accion;
    }

    public NombreAccion getNombreAccion() {
        return accion;
    }
}
