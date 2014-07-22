/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Sesiones.findAll", query = "SELECT s FROM Sesiones s"),
    @NamedQuery(name = "Sesiones.findByIdSesion", query = "SELECT s FROM Sesiones s WHERE s.idSesion = :idSesion"),
    @NamedQuery(name = "Sesiones.findByUsado", query = "SELECT s FROM Sesiones s WHERE s.usado = :usado"),
    @NamedQuery(name = "Sesiones.findByRutUsuario", query = "SELECT s FROM Sesiones s WHERE s.rutUsuario = :rutUsuario"),
    @NamedQuery(name = "Sesiones.findByFechaIngreso", query = "SELECT s FROM Sesiones s WHERE s.fechaIngreso = :fechaIngreso")})
public class Sesiones implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_sesion")
    private Long idSesion;
    @Basic(optional = false)
    @NotNull
    private boolean usado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 14)
    @Column(name = "rut_usuario")
    private String rutUsuario;
    @Column(name = "fecha_ingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaIngreso;

    public Sesiones() {
    }

    public Sesiones(Long idSesion) {
        this.idSesion = idSesion;
    }

    public Sesiones(Long idSesion, boolean usado, String rutUsuario) {
        this.idSesion = idSesion;
        this.usado = usado;
        this.rutUsuario = rutUsuario;
    }

    public Long getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Long idSesion) {
        this.idSesion = idSesion;
    }

    public boolean getUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public String getRutUsuario() {
        return rutUsuario;
    }

    public void setRutUsuario(String rutUsuario) {
        this.rutUsuario = rutUsuario;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSesion != null ? idSesion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sesiones)) {
            return false;
        }
        Sesiones other = (Sesiones) object;
        if ((this.idSesion == null && other.idSesion != null) || (this.idSesion != null && !this.idSesion.equals(other.idSesion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cl.cnsv.referidos.persistence.entities.Sesiones[ idSesion=" + idSesion + " ]";
    }
    
}
