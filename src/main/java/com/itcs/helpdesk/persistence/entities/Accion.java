/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accion.findAll", query = "SELECT a FROM Accion a"),
    @NamedQuery(name = "Accion.findByIdAccion", query = "SELECT a FROM Accion a WHERE a.idAccion = :idAccion"),
    @NamedQuery(name = "Accion.findByParametros", query = "SELECT a FROM Accion a WHERE a.parametros = :parametros")})
public class Accion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_accion")
    private Integer idAccion;
    @Lob
    @Size(max = 2147483647)
    private String parametros;
    @JoinColumn(name = "id_trigger", referencedColumnName = "id_trigger")
    @ManyToOne(optional = false)
    private ReglaTrigger idTrigger;
    @JoinColumn(name = "id_nombre_accion", referencedColumnName = "id_nombre_accion")
    @ManyToOne(optional = false)
    private NombreAccion idNombreAccion;

    public Accion() {
    }

    public Accion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public void setIdAccion(Integer idAccion) {
        this.idAccion = idAccion;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public ReglaTrigger getIdTrigger() {
        return idTrigger;
    }

    public void setIdTrigger(ReglaTrigger idTrigger) {
        this.idTrigger = idTrigger;
    }

    public NombreAccion getIdNombreAccion() {
        return idNombreAccion;
    }

    public void setIdNombreAccion(NombreAccion idNombreAccion) {
        this.idNombreAccion = idNombreAccion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAccion != null ? idAccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accion)) {
            return false;
        }
        Accion other = (Accion) object;
        if ((this.idAccion == null && other.idAccion != null) || (this.idAccion != null && !this.idAccion.equals(other.idAccion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getIdNombreAccion().getNombre() + " '" + getParametros() + "'" ;
    }
}
