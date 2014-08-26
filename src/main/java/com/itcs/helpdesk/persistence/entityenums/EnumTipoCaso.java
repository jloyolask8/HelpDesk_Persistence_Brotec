package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.TipoCaso;

/**
 *
 * @author jorge
 */
public enum EnumTipoCaso {

    INTERNO(new TipoCaso("interno", "Colaboración Interna", "Caso de colaboración interna de la empresa.")),//TODO delete this tipo caso, use Colab instead
    COTIZACION(new TipoCaso("cotizacion", "Cotización", "Caso donde el cliente solicita una Cotización de productos.")),
    PREVENTA(new TipoCaso("preventa", "Pre Venta", "Caso que parte de una Cotización de productos y se inicia el proceso de venta.")),
    REPARACION_ITEM(new TipoCaso("Reparación", "Reparación", "Caso específico para la Reparación de un Item o Producto")),
    POSTVENTA(new TipoCaso("postventa", "Soporte/Post Venta", "Caso donde el cliente solicita servicio de postventa.")),
    PREVENTIVO(new TipoCaso("preventivo", "Acción Preventiva", "Caso donde la empresa ofrece una acción de mantención preventiva.")),
    CONTACTO(new TipoCaso("contacto", "Contacto", "Caso donde el cliente solicita un contacto.")),
    ENTREGA(new TipoCaso("preentrega", "Entrega", "Caso donde especifican todos los detalles de la entrega del producto."));//No se puede modificar el id del Tipo asi como asi ya que en BD hay datos unsando el id antiguo

    private TipoCaso tipoCaso;

    EnumTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }
}
