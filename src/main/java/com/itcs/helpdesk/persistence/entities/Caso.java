/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumEstadoCaso;
import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Caso.findAll", query = "SELECT c FROM Caso c"),
    @NamedQuery(name = "Caso.findByIdCaso", query = "SELECT c FROM Caso c WHERE c.idCaso = :idCaso"),
    @NamedQuery(name = "Caso.findByEmailCliente", query = "SELECT c FROM Caso c WHERE c.emailCliente.emailCliente = :emailCliente"),
    @NamedQuery(name = "Caso.findByEsPrioritario", query = "SELECT c FROM Caso c WHERE c.esPrioritario = :esPrioritario")})
public class Caso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caso")
    private Long idCaso;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Asunto", fieldIdFull = "tema", fieldTypeFull = String.class)
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    private String tema;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Descripción", fieldIdFull = "descripcion", fieldTypeFull = String.class)
    @Lob
    @Size(max = 2147483647)
    private String descripcion;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "descripcion_txt")
    private String descripcionTxt;
    @Column(name = "tranfer_count")
    private Integer tranferCount;
    @Column(name = "init_response_due")
    @Temporal(TemporalType.TIMESTAMP)
    private Date initResponseDue;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "Fecha vencimiento respuesta", fieldIdFull = "nextResponseDue", fieldTypeFull = Date.class)
    @Column(name = "next_response_due")
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextResponseDue;
    @Size(max = 40)
    @Column(name = "estado_escalacion")
    private String estadoEscalacion;
    @Column(name = "es_preg_conocida")
    private Boolean esPregConocida;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "fecha Creacion", fieldIdFull = "fechaCreacion", fieldTypeFull = Date.class)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "fecha Modificacion", fieldIdFull = "fechaModif", fieldTypeFull = Date.class)
    @Column(name = "fecha_modif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "fecha Cierre", fieldIdFull = "fechaCierre", fieldTypeFull = Date.class)
    @Column(name = "fecha_cierre")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCierre;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "fecha Respuesta", fieldIdFull = "fechaRespuesta", fieldTypeFull = Date.class)
    @Column(name = "fecha_respuesta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRespuesta;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Respuesta", fieldIdFull = "respuesta", fieldTypeFull = String.class)
    @Lob
    @Size(max = 2147483647)
    private String respuesta;
    @FilterField(fieldTypeId = EnumFieldType.CHECKBOX, label = "esPrioritario", fieldIdFull = "esPrioritario", fieldTypeFull = Boolean.class)
    @Column(name = "es_prioritario")
    private Boolean esPrioritario;
    @JoinTable(name = "CASO_RELACIONADO", joinColumns = {
        @JoinColumn(name = "id_caso_1", referencedColumnName = "id_caso")}, inverseJoinColumns = {
        @JoinColumn(name = "id_caso_2", referencedColumnName = "id_caso")})
    @ManyToMany
//    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Caso Relacionado", fieldIdFull = "casosRelacionadosList", fieldTypeFull = List.class, listGenericTypeFieldId = "idCaso")
    private List<Caso> casosRelacionadosList;
//    @ManyToMany(mappedBy = "casosRelacionadosList")
//    private List<Caso> casoList1;
    @JoinTable(name = "CASO_DOCUMENTO", joinColumns = {
        @JoinColumn(name = "id_caso", referencedColumnName = "id_caso")}, inverseJoinColumns = {
        @JoinColumn(name = "id_documento", referencedColumnName = "id_documento")})
    @ManyToMany
    private List<Documento> documentoList;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Agente (Propietario)", fieldIdFull = "owner.idUsuario", fieldTypeFull = String.class)
    @JoinColumn(name = "owner", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario owner;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Estado de Alerta", fieldIdFull = "estadoAlerta.idalerta", fieldTypeFull = Integer.class)
    @JoinColumn(name = "estado_alerta", referencedColumnName = "idalerta")
    @ManyToOne
    private TipoAlerta estadoAlerta;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Sub Estado", fieldIdFull = "idSubEstado.idSubEstado", fieldTypeFull = String.class)
    @JoinColumn(name = "id_sub_estado", referencedColumnName = "id_sub_estado")
    @ManyToOne(fetch = FetchType.EAGER)
//    @NotNull
    private SubEstadoCaso idSubEstado;
    @JoinColumn(name = "ID_SUB_COMPONENTE", referencedColumnName = "ID_SUB_COMPONENTE")
    @ManyToOne
    private SubComponente idSubComponente;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Proyecto", fieldIdFull = "idProducto.idProducto", fieldTypeFull = String.class)
    @JoinColumn(name = "ID_PRODUCTO", referencedColumnName = "ID_PRODUCTO")
    @ManyToOne
    private Producto idProducto;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Prioridad", fieldIdFull = "idPrioridad.idPrioridad", fieldTypeFull = String.class)
    @JoinColumn(name = "id_prioridad", referencedColumnName = "id_prioridad")
    @ManyToOne
    private Prioridad idPrioridad;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Estado Caso", fieldIdFull = "idEstado.idEstado", fieldTypeFull = String.class)
    @JoinColumn(name = "id_estado", referencedColumnName = "id_estado")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private EstadoCaso idEstado;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Email Cliente", fieldIdFull = "emailCliente.emailCliente", fieldTypeFull = String.class)
    @JoinColumn(name = "email_cliente", referencedColumnName = "EMAIL_CLIENTE")
    @ManyToOne
    private EmailCliente emailCliente;
    @JoinColumn(name = "ID_COMPONENTE", referencedColumnName = "ID_COMPONENTE")
    @ManyToOne
    private Componente idComponente;
//    @JoinColumn(name = "id_modelo", referencedColumnName = "id_modelo")
//    @ManyToOne
//    private ModeloProducto idModelo;
    @JoinColumns({
        @JoinColumn(name = "id_modelo", referencedColumnName = "id_modelo"),
        @JoinColumn(name = "id_producto", referencedColumnName = "id_producto", insertable = false, updatable = false)})
    @ManyToOne
    private ModeloProducto idModelo;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Categoria", fieldIdFull = "idCategoria.idCategoria", fieldTypeFull = Integer.class)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    @ManyToOne
    private Categoria idCategoria;
    @OneToMany(mappedBy = "idCasoPadre")
    private List<Caso> casosHijosList;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_PLACE_HOLDER, label = "Caso padre", fieldIdFull = "idCasoPadre.idCaso", fieldTypeFull = Long.class)
    @JoinColumn(name = "id_caso_padre", referencedColumnName = "id_caso")
    @ManyToOne
    private Caso idCasoPadre;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Canal", fieldIdFull = "idCanal.idCanal", fieldTypeFull = String.class)
    @JoinColumn(name = "id_canal", referencedColumnName = "id_canal")
    @ManyToOne
    private Canal idCanal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCaso", fetch = FetchType.EAGER)
    @CascadeOnDelete
    private List<Nota> notaList;
    @OneToMany(mappedBy = "idCaso")
    @CascadeOnDelete
    private List<Attachment> attachmentList;
    @FilterField(fieldTypeId = EnumFieldType.CHECKBOX, label = "Revisar Actualizacion", fieldIdFull = "revisarActualizacion", fieldTypeFull = Boolean.class)
    @Column(name = "revisar_actualizacion")
    private Boolean revisarActualizacion;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Area", fieldIdFull = "idArea.idArea", fieldTypeFull = String.class)
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne(optional = false)
    private Area idArea;
    @FilterField(fieldTypeId = EnumFieldType.COMMA_SEPARATED_VALUELIST, label = "Etiquetado como", fieldIdFull = "etiquetaList", fieldTypeFull = List.class)
    @JoinTable(name = "etiqueta_caso", joinColumns = {
        @JoinColumn(name = "id_caso", referencedColumnName = "id_caso")}, inverseJoinColumns = {
        @JoinColumn(name = "tag_id", referencedColumnName = "tag_id")})
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<Etiqueta> etiquetaList;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Tipo", fieldIdFull = "tipoCaso.idTipoCaso", fieldTypeFull = String.class)
    @JoinColumn(name = "tipo_caso", referencedColumnName = "id_tipo_caso")
    @ManyToOne
    private TipoCaso tipoCaso;
    @FilterField(fieldTypeId = EnumFieldType.CHECKBOX, label = "Tiene credito Pre-Aprobado", fieldIdFull = "creditoPreAprobado", fieldTypeFull = Boolean.class)
    @Column(name = "credito_preaprobado")
    private Boolean creditoPreAprobado;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "Fecha Estimada de Compra", fieldIdFull = "fechaEstimadaCompra", fieldTypeFull = Date.class)
    @Column(name = "fecha_estimada_compra")
    @Temporal(TemporalType.DATE)
    private Date fechaEstimadaCompra;
    @Column(name = "id_recinto")
    private String idRecinto;
    @JoinColumn(name = "id_item", referencedColumnName = "id_item")
    @ManyToOne
    private Item idItem;

    public Caso() {
    }

    public Caso(Long idCaso) {
        this.idCaso = idCaso;
    }

    public Caso(Long idCaso, String tema) {
        this.idCaso = idCaso;
        this.tema = tema;
    }

    public Long getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Long idCaso) {
        this.idCaso = idCaso;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTranferCount() {
        return tranferCount;
    }

    public void setTranferCount(Integer tranferCount) {
        this.tranferCount = tranferCount;
    }

    public Date getInitResponseDue() {
        return initResponseDue;
    }

    public void setInitResponseDue(Date initResponseDue) {
        this.initResponseDue = initResponseDue;
    }

    public Date getNextResponseDue() {
        return nextResponseDue;
    }

    public void setNextResponseDue(Date nextResponseDue) {
        this.nextResponseDue = nextResponseDue;
    }

    public String getEstadoEscalacion() {
        return estadoEscalacion;
    }

    public void setEstadoEscalacion(String estadoEscalacion) {
        this.estadoEscalacion = estadoEscalacion;
    }

    public Boolean getEsPregConocida() {
        return esPregConocida;
    }

    public void setEsPregConocida(Boolean esPregConocida) {
        this.esPregConocida = esPregConocida;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public Boolean getEsPrioritario() {
        return esPrioritario;
    }

    public void setEsPrioritario(Boolean esPrioritario) {
        this.esPrioritario = esPrioritario;
    }

    @XmlTransient
    public List<Caso> getCasosRelacionadosList() {
        return casosRelacionadosList;
    }

    public void setCasosRelacionadosList(List<Caso> casoList) {
        this.casosRelacionadosList = casoList;
    }

//    @XmlTransient
//    public List<Caso> getCasoList1() {
//        return casoList1;
//    }
//
//    public void setCasoList1(List<Caso> casoList1) {
//        this.casoList1 = casoList1;
//    }
    @XmlTransient
    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
    }

    public Usuario getOwner() {
        return owner;
    }

    public void setOwner(Usuario owner) {
        this.owner = owner;
    }

    public TipoAlerta getEstadoAlerta() {
        return estadoAlerta;
    }

    public void setEstadoAlerta(TipoAlerta estadoAlerta) {
        this.estadoAlerta = estadoAlerta;
    }

    public SubEstadoCaso getIdSubEstado() {
        return idSubEstado;
    }

    public void setIdSubEstado(SubEstadoCaso idSubEstado) {
        this.idSubEstado = idSubEstado;
    }

    public SubComponente getIdSubComponente() {
        return idSubComponente;
    }

    public void setIdSubComponente(SubComponente idSubComponente) {
        this.idSubComponente = idSubComponente;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public Prioridad getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Prioridad idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public EstadoCaso getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(EstadoCaso idEstado) {
        this.idEstado = idEstado;
    }

    public EmailCliente getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(EmailCliente emailCliente) {
        this.emailCliente = emailCliente;
    }

    public Componente getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(Componente idComponente) {
        this.idComponente = idComponente;
    }

    public Categoria getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Categoria idCategoria) {
        this.idCategoria = idCategoria;
    }

    @XmlTransient
    public List<Caso> getCasosHijosList() {
        return casosHijosList;
    }

    public void setCasosHijosList(List<Caso> casosHijosList) {
        this.casosHijosList = casosHijosList;
    }

    public Caso getIdCasoPadre() {
        return idCasoPadre;
    }

    public void setIdCasoPadre(Caso idCasoPadre) {
        this.idCasoPadre = idCasoPadre;
    }

    public Canal getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(Canal idCanal) {
        this.idCanal = idCanal;
    }

    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @XmlTransient
    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaso != null ? idCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caso)) {
            return false;
        }
        Caso other = (Caso) object;
        if ((this.idCaso == null && other.idCaso != null) || (this.idCaso != null && !this.idCaso.equals(other.idCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Caso [");
        builder.append("idCaso=");
        builder.append(idCaso);
        builder.append(", idEstado=");
        builder.append(idEstado);
        builder.append(", idSubEstado=");
        builder.append(idSubEstado);
        builder.append(", tema=");
        builder.append(tema);
        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the revisarActualizacion
     */
    public Boolean getRevisarActualizacion() {
        return revisarActualizacion;
    }

    /**
     * @param revisarActualizacion the revisarActualizacion to set
     */
    public void setRevisarActualizacion(Boolean revisarActualizacion) {
        this.revisarActualizacion = revisarActualizacion;
    }

    /**
     * @return the idArea
     */
    public Area getIdArea() {
        return idArea;
    }

    /**
     * @param idArea the idArea to set
     */
    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    /**
     * @return the etiquetaList
     */
    public List<Etiqueta> getEtiquetaList() {
        return etiquetaList;
    }

    /**
     * @param etiquetaList the etiquetaList to set
     */
    public void setEtiquetaList(List<Etiqueta> etiquetaList) {
        this.etiquetaList = etiquetaList;
    }

    public TipoCaso getTipoCaso() {
        return tipoCaso;
    }

    public void setTipoCaso(TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }

    /**
     * @return the valoresList
     */
    public String getEtiquetas() {

        String commaSeparatedValues = "";
        boolean first = true;
        if (etiquetaList != null) {
            for (Etiqueta e : this.etiquetaList) {
                if (first) {
                    first = false;
                    commaSeparatedValues += e.getTagId();
                } else {
                    commaSeparatedValues += "," + e.getTagId();
                }
            }
        }

        return commaSeparatedValues;

    }

    /**
     * @param valoresList the valoresList to set
     */
    public void setEtiquetas(String valoresList) {
        if (valoresList != null && !valoresList.trim().isEmpty()) {
            final List<Etiqueta> result = new ArrayList<Etiqueta>();
            for (String value : valoresList.split(",", -1)) {
                final String trimmedValue = value.trim();
                result.add(new Etiqueta(trimmedValue));
            }
            this.etiquetaList = result;
        } else {
            etiquetaList = null;
        }

    }

    /**
     * @return the etiquetaList
     */
    public List<String> getEtiquetaStringList() {
        List<String> list = new ArrayList<String>();
        if (etiquetaList != null && !etiquetaList.isEmpty()) {
            for (Etiqueta etiqueta : etiquetaList) {
                list.add(etiqueta.getTagId());
            }
        }
        return list;
    }

    /**
     * @param etiquetaList the etiquetaList to set
     */
    public void setEtiquetaStringList(List<String> etiquetaStringList) {
        if (etiquetaStringList != null && !etiquetaStringList.isEmpty()) {
            this.etiquetaList = new ArrayList<Etiqueta>();
            for (String string : etiquetaStringList) {
                this.etiquetaList.add(new Etiqueta(string));
            }
        } else {
            this.etiquetaList = null;
        }

    }

    public boolean hasOpenSubCasos() {
        if (this.getCasosHijosList() != null) {
            for (Caso caso : this.getCasosHijosList()) {
                if (caso.isOpen()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isOpen() {
        if (this.getIdEstado() != null) {
            if (this.getIdEstado().equals(EnumEstadoCaso.ABIERTO.getEstado())) {
                return true;
            }

        }
        return false;
    }

    public boolean isClosed() {
        if (this.getIdEstado() != null) {
            if (this.getIdEstado().equals(EnumEstadoCaso.CERRADO.getEstado())) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAnOwner() {
        if (this.getOwner() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the idModelo
     */
    public ModeloProducto getIdModelo() {
        return idModelo;
    }

    /**
     * @param idModelo the idModelo to set
     */
    public void setIdModelo(ModeloProducto idModelo) {
        this.idModelo = idModelo;
    }

    /**
     * @return the descripcionTxt
     */
    public String getDescripcionTxt() {
        return descripcionTxt;
    }

    /**
     * @param descripcionTxt the descripcionTxt to set
     */
    public void setDescripcionTxt(String descripcionTxt) {
        this.descripcionTxt = descripcionTxt;
    }

    /**
     * @return the creditoPreAprobado
     */
    public Boolean getCreditoPreAprobado() {
        return creditoPreAprobado;
    }

    /**
     * @param creditoPreAprobado the creditoPreAprobado to set
     */
    public void setCreditoPreAprobado(Boolean creditoPreAprobado) {
        this.creditoPreAprobado = creditoPreAprobado;
    }

    /**
     * @return the fechaEstimadaCompra
     */
    public Date getFechaEstimadaCompra() {
        return fechaEstimadaCompra;
    }

    /**
     * @param fechaEstimadaCompra the fechaEstimadaCompra to set
     */
    public void setFechaEstimadaCompra(Date fechaEstimadaCompra) {
        this.fechaEstimadaCompra = fechaEstimadaCompra;
    }

    public String getIdRecinto() {
        return idRecinto;
    }

    public void setIdRecinto(String idRecinto) {
        this.idRecinto = idRecinto;
    }

    public Item getIdItem() {
        return idItem;
    }

    public void setIdItem(Item idItem) {
        this.idItem = idItem;
    }
}
