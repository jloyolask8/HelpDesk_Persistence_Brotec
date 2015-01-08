/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "recinto")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")
@XmlRootElement
@NamedQueries(
        {
            @NamedQuery(name = "Recinto.findAll", query = "SELECT r FROM Recinto r"),
            @NamedQuery(name = "Recinto.findByIdRecinto", query = "SELECT r FROM Recinto r WHERE r.idRecinto = :idRecinto"),
            @NamedQuery(name = "Recinto.findByNombre", query = "SELECT r FROM Recinto r WHERE r.nombre = :nombre"),
            @NamedQuery(name = "Recinto.findByDescripcion", query = "SELECT r FROM Recinto r WHERE r.descripcion = :descripcion")
        })
public class Recinto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_recinto")
    private String idRecinto;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;

    public Recinto() {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getIdRecinto() != null ? getIdRecinto().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recinto)) {
            return false;
        }
        Recinto other = (Recinto) object;
        if ((this.getIdRecinto() == null && other.getIdRecinto() != null) || (this.getIdRecinto() != null && !this.idRecinto.equals(other.idRecinto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idRecinto + " [" + nombre + "]";
    }

    /**
     * @return the idRecinto
     */
    public String getIdRecinto() {
        return idRecinto;
    }

    /**
     * @param idRecinto the idRecinto to set
     */
    public void setIdRecinto(String idRecinto) {
        this.idRecinto = idRecinto;
    }

}
