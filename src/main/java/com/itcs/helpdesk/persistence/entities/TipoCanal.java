/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;

/**
 *
 * @author jorge
 */
@Entity
@Table(name = "tipo_canal")
/*@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")*/
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCanal.findAll", query = "SELECT t FROM TipoCanal t"),
    @NamedQuery(name = "TipoCanal.findByNombre", query = "SELECT t FROM TipoCanal t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "TipoCanal.findByIdTipo", query = "SELECT t FROM TipoCanal t WHERE t.idTipo = :idTipo")})
public class TipoCanal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "id_tipo", nullable = false, length = 2147483647)
    private String idTipo;

    @Size(max = 2147483647)
    @Column(name = "nombre", length = 2147483647)
    private String nombre;

    @OneToMany(mappedBy = "idTipoCanal")
    private List<Canal> canalList;

    public TipoCanal() {
    }

    public TipoCanal(String idTipo) {
        this.idTipo = idTipo;
    }

    public TipoCanal(String idTipo, String nombre) {
        this.nombre = nombre;
        this.idTipo = idTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(String idTipo) {
        this.idTipo = idTipo;
    }

    @XmlTransient
    public List<Canal> getCanalList() {
        return canalList;
    }

    public void setCanalList(List<Canal> canalList) {
        this.canalList = canalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipo != null ? idTipo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCanal)) {
            return false;
        }
        TipoCanal other = (TipoCanal) object;
        if ((this.idTipo == null && other.idTipo != null) || (this.idTipo != null && !this.idTipo.equals(other.idTipo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itcs.helpdesk.persistence.entities.TipoCanal[ idTipo=" + idTipo + " ]";
    }

}
