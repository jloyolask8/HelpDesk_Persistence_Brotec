package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoCaso;

/**
 *
 * @author jorge
 */
public enum EnumTipoCaso {

    INTERNO(new TipoCaso("interno", "Colaboración Interna", "Caso Interno.")),//TODO delete this tipo caso, use Colab instead
    COTIZACION(new TipoCaso("cotizacion", "Cotización", "Caso donde el cliente solicita una Cotización de productos.")),
    PREVENTA(new TipoCaso("preventa", "Pre Venta", "Caso donde el cliente solicita una Cotización de productos y se inicia el proceso de venta.")),
    REPARACION_ITEM(new TipoCaso("Reparación de Item", "Reparación de Item", "Caso especifico para la Reparación de un Item")),
    POSTVENTA(new TipoCaso("postventa", "Soporte/Post Venta", "Caso donde el cliente solicita servicio de postventa.")),
    PREVENTIVO(new TipoCaso("preventivo", "Visita Preventiva", "Caso donde la inmobiliaria ofrece una visita preventiva.")),
    CONTACTO(new TipoCaso("contacto", "Contacto", "Caso donde el cliente solicita un contacto.")),
    PREENTREGA(new TipoCaso("preentrega", "Pre-entrega", "Caso donde especifican todos los detalles de pre-entrega."));

    private TipoCaso tipoCaso;

    EnumTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }
}
