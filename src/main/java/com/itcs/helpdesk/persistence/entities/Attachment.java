/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.text.DecimalFormat;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attachment.findAll", query = "SELECT a FROM Attachment a"),
    @NamedQuery(name = "Attachment.findByIdAttachment", query = "SELECT a FROM Attachment a WHERE a.idAttachment = :idAttachment"),
    @NamedQuery(name = "Attachment.findByNombreArchivo", query = "SELECT a FROM Attachment a WHERE a.nombreArchivo = :nombreArchivo"),
    @NamedQuery(name = "Attachment.findByMimeType", query = "SELECT a FROM Attachment a WHERE a.mimeType = :mimeType"),
    @NamedQuery(name = "Attachment.findByEnRespuesta", query = "SELECT a FROM Attachment a WHERE a.enRespuesta = :enRespuesta")})
public class Attachment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_attachment")
    private Long idAttachment;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2000)
    @Column(name = "nombre_archivo")
    private String nombreArchivo;
    @Size(max = 200)
    @Column(name = "mime_type")
    private String mimeType;
    @Column(name = "en_respuesta")
    private Boolean enRespuesta;
    @JoinColumn(name = "id_caso", referencedColumnName = "id_caso")
    @ManyToOne
    private Caso idCaso;
    @Size(max = 200)
    @Column(name = "contentId")
    private String contentId;
    
    //this is for holding the phisical file for a while before creating the case.
    @Transient
    private Archivo archivo;

    public Attachment() {
    }

    public Attachment(Long idAttachment) {
        this.idAttachment = idAttachment;
    }

    public Attachment(Long idAttachment, String nombreArchivo) {
        this.idAttachment = idAttachment;
        this.nombreArchivo = nombreArchivo;
    }

//     @Transient
//    public String getReadableFileSize() {
//        if (fileSize <= 0) {
//            return "0";
//        }
//        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
//        int digitGroups = (int) (Math.log10(fileSize) / Math.log10(1024));
//        return new DecimalFormat("#,##0.#").format(fileSize / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
//    }
    
    @Transient
    public boolean isImageOrPDF() {
        if (getMimeType() == null) {
            return false;
        }
        String type = getMimeType().split("/")[0];
        if (type.equals("image") || getMimeType().equalsIgnoreCase("application/pdf")) {
            return true;
        } else {
            return false;
        }
    }
    
    public Long getIdAttachment() {
        return idAttachment;
    }

    public void setIdAttachment(Long idAttachment) {
        this.idAttachment = idAttachment;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Boolean getEnRespuesta() {
        return enRespuesta;
    }

    public void setEnRespuesta(Boolean enRespuesta) {
        this.enRespuesta = enRespuesta;
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
        hash += (idAttachment != null ? idAttachment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attachment)) {
            return false;
        }
        Attachment other = (Attachment) object;
        if ((this.idAttachment == null && other.idAttachment != null) || (this.idAttachment != null && !this.idAttachment.equals(other.idAttachment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Att[ idAttachment=" + idAttachment + " nombreArchivo="+nombreArchivo+"]";
    }

    /**
     * @return the contentId
     */
    public String getContentId() {
        return contentId;
    }

    /**
     * @param contentId the contentId to set
     */
    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    /**
     * @return the archivo
     */
    public Archivo getArchivo() {
        return archivo;
    }

    /**
     * @param archivo the archivo to set
     */
    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }
    
}
