/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
@Table(name = "SUB_COMPONENTE")
/*@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")*/
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubComponente.findAll", query = "SELECT s FROM SubComponente s"),
    @NamedQuery(name = "SubComponente.findAllByQuery", query = "SELECT o FROM SubComponente o WHERE (LOWER(o.nombre) LIKE CONCAT(LOWER(:q), '%')) "),
    @NamedQuery(name = "SubComponente.findByIdSubComponente", query = "SELECT s FROM SubComponente s WHERE s.idSubComponente = :idSubComponente"),
    @NamedQuery(name = "SubComponente.findByNombre", query = "SELECT s FROM SubComponente s WHERE s.nombre = :nombre"),
    @NamedQuery(name = "SubComponente.findByDescripcion", query = "SELECT s FROM SubComponente s WHERE s.descripcion = :descripcion")})
public class SubComponente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ID_SUB_COMPONENTE")
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "ID", fieldIdFull = "idSubComponente", fieldTypeFull = String.class)
    private String idSubComponente;
    @Size(max = 200)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Nombre", fieldIdFull = "nombre", fieldTypeFull = String.class)
    private String nombre;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Descripcion", fieldIdFull = "descripcion", fieldTypeFull = String.class)
    private String descripcion;
//    @OneToMany(mappedBy = "idSubComponente")
//    private List<Caso> casoList;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE")
    @ManyToOne(optional = false)
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Componente", fieldIdFull = "idComponente.idComponente", fieldTypeFull = String.class)
    private Componente idComponente;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Modelo", fieldIdFull = "modelo.modeloProductoPK", fieldTypeFull = ModeloProductoPK.class)
    @JoinColumns({
        @JoinColumn(name = "id_modelo", referencedColumnName = "id_modelo"),
        @JoinColumn(name = "id_producto", referencedColumnName = "id_producto")})
    @ManyToOne
    private ModeloProducto modelo;
    
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Proyecto::", fieldIdFull = "idProducto.idProducto", fieldTypeFull = String.class)
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)
    @ManyToOne
    private Producto idProducto;

      @FilterField(fieldTypeId = EnumFieldType.SELECTONE_PLACE_HOLDER, label = "Clientes relacionados", fieldIdFull = "productoContratadoList", fieldTypeFull = List.class)
    @OneToMany(mappedBy = "subComponente")
    private List<ProductoContratado> productoContratadoList;

    //TODO brotec specific =(
    @Column(name = "direccion_municipal")
    private String direccionMunicipal;
    @Column(name = "fecha_reserva")
    @Temporal(TemporalType.DATE)
    private Date fechaReserva;
    @Column(name = "fecha_promesa")
    @Temporal(TemporalType.DATE)
    private Date fechaPromesa;
    @Column(name = "fecha_escritura")
    @Temporal(TemporalType.DATE)
    private Date fechaEscritura;
    @Column(name = "fecha_desistimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaDesistimiento;
    @Column(name = "fecha_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaEntrega;
    @Column(name = "fecha_pre_entrega")
    @Temporal(TemporalType.DATE)
    private Date fechaPreEntrega;
    @Column(name = "id_archivo_acta_entrega")
    private Long idArchivoActaEntrega;
    @Column(name = "id_archivo_acta_pre_entrega")
    private Long idArchivoActaPreEntrega;
    @Column(name = "id_archivo_carta_entrega")
    private Long idArchivoCartaEntrega;

    public SubComponente() {
    }

    public SubComponente(String idSubComponente) {
        this.idSubComponente = idSubComponente;
    }

    public String getIdSubComponente() {
        return idSubComponente;
    }

    public void setIdSubComponente(String idSubComponente) {
        this.idSubComponente = idSubComponente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

//    @XmlTransient
//    public List<Caso> getCasoList() {
//        return casoList;
//    }
//
//    public void setCasoList(List<Caso> casoList) {
//        this.casoList = casoList;
//    }
    public Componente getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Componente idComponente) {
        this.idComponente = idComponente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSubComponente != null ? idSubComponente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubComponente)) {
            return false;
        }
        SubComponente other = (SubComponente) object;
        if ((this.idSubComponente == null && other.idSubComponente != null) || (this.idSubComponente != null && !this.idSubComponente.equals(other.idSubComponente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getNombre() + "(" + this.getIdComponente().getIdProducto().getNombre() + ")";
    }

    /**
     * @return the modelo
     */
    public ModeloProducto getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(ModeloProducto modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the direccionMunicipal
     */
    public String getDireccionMunicipal() {
        return direccionMunicipal;
    }

    /**
     * @param direccionMunicipal the direccionMunicipal to set
     */
    public void setDireccionMunicipal(String direccionMunicipal) {
        this.direccionMunicipal = direccionMunicipal;
    }

    /**
     * @return the fechaReserva
     */
    public Date getFechaReserva() {
        return fechaReserva;
    }

    /**
     * @param fechaReserva the fechaReserva to set
     */
    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    /**
     * @return the fechaPromesa
     */
    public Date getFechaPromesa() {
        return fechaPromesa;
    }

    /**
     * @param fechaPromesa the fechaPromesa to set
     */
    public void setFechaPromesa(Date fechaPromesa) {
        this.fechaPromesa = fechaPromesa;
    }

    /**
     * @return the fechaEscritura
     */
    public Date getFechaEscritura() {
        return fechaEscritura;
    }

    /**
     * @param fechaEscritura the fechaEscritura to set
     */
    public void setFechaEscritura(Date fechaEscritura) {
        this.fechaEscritura = fechaEscritura;
    }

    /**
     * @return the fechaDesistimiento
     */
    public Date getFechaDesistimiento() {
        return fechaDesistimiento;
    }

    /**
     * @param fechaDesistimiento the fechaDesistimiento to set
     */
    public void setFechaDesistimiento(Date fechaDesistimiento) {
        this.fechaDesistimiento = fechaDesistimiento;
    }

    /**
     * @return the fechaEntrega
     */
    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    /**
     * @param fechaEntrega the fechaEntrega to set
     */
    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    /**
     * @return the fechaPreEntrega
     */
    public Date getFechaPreEntrega() {
        return fechaPreEntrega;
    }

    /**
     * @param fechaPreEntrega the fechaPreEntrega to set
     */
    public void setFechaPreEntrega(Date fechaPreEntrega) {
        this.fechaPreEntrega = fechaPreEntrega;
    }

    /**
     * @return the idArchivoActaEntrega
     */
    public Long getIdArchivoActaEntrega() {
        return idArchivoActaEntrega;
    }

    /**
     * @param idArchivoActaEntrega the idArchivoActaEntrega to set
     */
    public void setIdArchivoActaEntrega(Long idArchivoActaEntrega) {
        this.idArchivoActaEntrega = idArchivoActaEntrega;
    }

    /**
     * @return the idArchivoActaPreEntrega
     */
    public Long getIdArchivoActaPreEntrega() {
        return idArchivoActaPreEntrega;
    }

    /**
     * @param idArchivoActaPreEntrega the idArchivoActaPreEntrega to set
     */
    public void setIdArchivoActaPreEntrega(Long idArchivoActaPreEntrega) {
        this.idArchivoActaPreEntrega = idArchivoActaPreEntrega;
    }

    /**
     * @return the idArchivoCartaEntrega
     */
    public Long getIdArchivoCartaEntrega() {
        return idArchivoCartaEntrega;
    }

    /**
     * @param idArchivoCartaEntrega the idArchivoCartaEntrega to set
     */
    public void setIdArchivoCartaEntrega(Long idArchivoCartaEntrega) {
        this.idArchivoCartaEntrega = idArchivoCartaEntrega;
    }

    /**
     * @return the productoContratadoList
     */
    public List<ProductoContratado> getProductoContratadoList() {
        return productoContratadoList;
    }

    /**
     * @param productoContratadoList the productoContratadoList to set
     */
    public void setProductoContratadoList(List<ProductoContratado> productoContratadoList) {
        this.productoContratadoList = productoContratadoList;
    }

    /**
     * @return the idProducto
     */
    public Producto getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }
}
