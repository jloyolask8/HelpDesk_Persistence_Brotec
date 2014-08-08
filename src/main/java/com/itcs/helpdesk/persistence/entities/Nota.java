/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
    @NamedQuery(name = "Nota.findByIdCaso", query = "SELECT n FROM Nota n WHERE n.idCaso = :idCaso ORDER BY n.fechaCreacion DESC"),
    @NamedQuery(name = "Nota.findByIdNota", query = "SELECT n FROM Nota n WHERE n.idNota = :idNota"),
    @NamedQuery(name = "Nota.findByFechaCreacion", query = "SELECT n FROM Nota n WHERE n.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Nota.findByFechaModificacion", query = "SELECT n FROM Nota n WHERE n.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "Nota.findByVisible", query = "SELECT n FROM Nota n WHERE n.visible = :visible")})
public class Nota implements Serializable, Comparable<Nota> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_nota")
    private Integer idNota;
    @Lob
    @Size(max = 2147483647)
    private String texto;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModificacion;
    private Boolean visible;
    @JoinColumn(name = "creada_por", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario creadaPor;
    @JoinColumn(name = "ID_TIPO_NOTA", referencedColumnName = "ID_TIPO_NOTA")
    @ManyToOne
    private TipoNota idTipoNota;
    @JoinColumn(name = "id_caso", referencedColumnName = "id_caso")
    @ManyToOne
    private Caso idCaso;
    @Column(name = "enviado_por")
    @Size(max = 200)
    private String enviadoPor;
    
     @Column(name = "enviado_a")
    private String enviadoA;
    
    @Column(name = "fecha_envio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    
    @Column(name = "enviado")
    private Boolean enviado;
    
    @JoinTable(name = "nota_attachments", joinColumns = {  @JoinColumn(name = "id_nota", referencedColumnName = "id_nota")
        }, inverseJoinColumns = {@JoinColumn(name = "id_attachment", referencedColumnName = "id_attachment")
       })
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Attachment> attachmentList;

    public Nota() {
    }

    public Nota(Integer idNota) {
        this.idNota = idNota;
    }

    public Integer getIdNota() {
        return idNota;
    }

    public void setIdNota(Integer idNota) {
        this.idNota = idNota;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Usuario getCreadaPor() {
        return creadaPor;
    }

    public void setCreadaPor(Usuario creadaPor) {
        this.creadaPor = creadaPor;
    }

    public TipoNota getTipoNota() {
        return idTipoNota;
    }

    public void setTipoNota(TipoNota idTipoNota) {
        this.idTipoNota = idTipoNota;
    }

    public Caso getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Caso idCaso) {
        this.idCaso = idCaso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNota != null ? idNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.idNota == null && other.idNota != null) || (this.idNota != null && !this.idNota.equals(other.idNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.cnsv.referidos.persistence.entities.Nota[ idNota=" + idNota + " ]";
    }

    @Override
    public int compareTo(Nota o) {
        if (equals(o)) {
            return 0;
        }
        long resultado = (o.getFechaCreacion().getTime() - this.getFechaCreacion().getTime());
        return (resultado < 0 ? -1 : 1);
    }

    /**
     * @return the enviadoPor
     */
    public String getEnviadoPor() {
        return enviadoPor;
    }

    /**
     * @param enviadoPor the enviadoPor to set
     */
    public void setEnviadoPor(String enviadoPor) {
        this.enviadoPor = enviadoPor;
    }

    /**
     * @return the fechaEnvio
     */
    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    /**
     * @param fechaEnvio the fechaEnvio to set
     */
    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    /**
     * @return the enviado
     */
    public Boolean getEnviado() {
        return enviado;
    }

    /**
     * @param enviado the enviado to set
     */
    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    /**
     * @return the enviadoA
     */
    public String getEnviadoA() {
        return enviadoA;
    }

    /**
     * @param enviadoA the enviadoA to set
     */
    public void setEnviadoA(String enviadoA) {
        this.enviadoA = enviadoA;
    }

    /**
     * @return the attachmentList
     */
    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    /**
     * @param attachmentList the attachmentList to set
     */
    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
