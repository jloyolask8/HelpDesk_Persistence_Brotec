/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "etiqueta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Etiqueta.findAll", query = "SELECT e FROM Etiqueta e"),
    @NamedQuery(name = "Etiqueta.findByTagId", query = "SELECT e FROM Etiqueta e WHERE e.tagId = :tagId")})
public class Etiqueta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "tag_id")
    private String tagId;
    @Column(name = "descripcion")
    private String descripcion;
    @ManyToMany(mappedBy = "etiquetaList")
    private List<Caso> casoList;

    public Etiqueta() {
    }

    public Etiqueta(String tagId) {
        this.tagId = tagId;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tagId != null ? tagId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Etiqueta)) {
            return false;
        }
        Etiqueta other = (Etiqueta) object;
        if ((this.tagId == null && other.tagId != null) || (this.tagId != null && !this.tagId.equals(other.tagId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return tagId;
    }
    
}
