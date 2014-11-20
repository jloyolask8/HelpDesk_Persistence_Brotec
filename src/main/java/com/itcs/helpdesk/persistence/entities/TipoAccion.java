/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "NOMBRE_ACCION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoAccion.findAll", query = "SELECT n FROM TipoAccion n"),
    @NamedQuery(name = "TipoAccion.findByIdNombreAccion", query = "SELECT n FROM TipoAccion n WHERE n.idNombreAccion = :idNombreAccion"),
    @NamedQuery(name = "TipoAccion.findByNombre", query = "SELECT n FROM TipoAccion n WHERE n.nombre = :nombre")})
public class TipoAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "id_nombre_accion")
    private String idNombreAccion;
    @Size(max = 64)
    private String nombre;
    @Lob
    @Size(max = 2147483647)
    private String descripcion;
    @Size(max = 1000)
    @Column(name = "implementation_class_name")
    private String implementationClassName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idNombreAccion")
    private List<Accion> accionList;

    public TipoAccion() {
    }

    public TipoAccion(String idNombreAccion) {
        this.idNombreAccion = idNombreAccion;
    }

    public TipoAccion(String idTipoAccion, String nombre, String descripcion, String implementationClassName) {
        this.idNombreAccion = idTipoAccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.implementationClassName = implementationClassName;
    }

    public String getIdNombreAccion() {
        return idNombreAccion;
    }

    public void setIdNombreAccion(String idNombreAccion) {
        this.idNombreAccion = idNombreAccion;
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

    @XmlTransient
    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNombreAccion != null ? idNombreAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoAccion)) {
            return false;
        }
        TipoAccion other = (TipoAccion) object;
        if ((this.idNombreAccion == null && other.idNombreAccion != null) || (this.idNombreAccion != null && !this.idNombreAccion.equals(other.idNombreAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * @return the implementationClassName
     */
    public String getImplementationClassName() {
        return implementationClassName;
    }

    /**
     * @param implementationClassName the implementationClassName to set
     */
    public void setImplementationClassName(String implementationClassName) {
        this.implementationClassName = implementationClassName;
    }

}
