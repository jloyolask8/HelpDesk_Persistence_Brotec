/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "producto_contratado")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductoContratado.findAll", query = "SELECT p FROM ProductoContratado p"),
    @NamedQuery(name = "ProductoContratado.findByIdCliente", query = "SELECT p FROM ProductoContratado p WHERE p.productoContratadoPK.idCliente = :idCliente"),
    @NamedQuery(name = "ProductoContratado.findByIdProducto", query = "SELECT p FROM ProductoContratado p WHERE p.productoContratadoPK.idProducto = :idProducto")})
public class ProductoContratado implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProductoContratadoPK productoContratadoPK;
    @Size(max = 64)
    @Column(name = "vendedor")
    private String vendedor;
    @Column(name = "fecha_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;
    @Size(max = 2147483647)
    @Column(name = "observaciones")
    private String observaciones;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "SubComponente", fieldIdFull = "subComponente.idSubComponente",
            fieldTypeFull = String.class)
    @JoinColumn(name = "id_sub_componente", referencedColumnName = "id_sub_componente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SubComponente subComponente;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Proyecto", fieldIdFull = "producto.idProducto",
            fieldTypeFull = String.class)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Producto producto;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Componente", fieldIdFull = "componente.idComponente",
            fieldTypeFull = String.class)
    @JoinColumn(name = "id_componente", referencedColumnName = "id_componente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Componente componente;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Cliente", fieldIdFull = "cliente.idCliente", fieldTypeFull = Integer.class)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;
//  EnumTipoAsocClienteProducto
    @Column(name = "tipo_asociacion")
    private String tipoAsociacion;

    public ProductoContratado() {
    }

    public ProductoContratado(ProductoContratadoPK productoContratadoPK) {
        this.productoContratadoPK = productoContratadoPK;
    }

    public ProductoContratado(int idCliente, String idProducto, String idComponente, String idSubComponente) {
        this.productoContratadoPK = new ProductoContratadoPK(idCliente, idProducto, idComponente, idSubComponente);
    }

    public ProductoContratadoPK getProductoContratadoPK() {
        return productoContratadoPK;
    }

    public void setProductoContratadoPK(ProductoContratadoPK productoContratadoPK) {
        this.productoContratadoPK = productoContratadoPK;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

//    public Date getFechaCompra() {
//        return fechaCompra;
//    }
//
//    public void setFechaCompra(Date fechaCompra) {
//        this.fechaCompra = fechaCompra;
//    }
    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public SubComponente getSubComponente() {
        return subComponente;
    }

    public void setSubComponente(SubComponente subComponente) {
        this.subComponente = subComponente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Componente getComponente() {
        return componente;
    }

    public void setComponente(Componente componente) {
        this.componente = componente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productoContratadoPK != null ? productoContratadoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductoContratado)) {
            return false;
        }
        ProductoContratado other = (ProductoContratado) object;
        if ((this.productoContratadoPK == null && other.productoContratadoPK != null) || (this.productoContratadoPK != null && !this.productoContratadoPK.equals(other.productoContratadoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cliente=" + cliente + ", tipoAsociacion=" + tipoAsociacion;
    }

    /**
     * @return the tipoAsociacion
     */
    public String getTipoAsociacion() {
        return tipoAsociacion;
    }

    /**
     * @param tipoAsociacion the tipoAsociacion to set
     */
    public void setTipoAsociacion(String tipoAsociacion) {
        this.tipoAsociacion = tipoAsociacion;
    }

    /**
     * @return the fechaCompra
     */
    public Date getFechaCompra() {
        return fechaCompra;
    }

    /**
     * @param fechaCompra the fechaCompra to set
     */
    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

}
