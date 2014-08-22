/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "REGLA_TRIGGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReglaTrigger.findAll", query = "SELECT r FROM ReglaTrigger r ORDER BY r.orden ASC"),
    @NamedQuery(name = "ReglaTrigger.findByEvento", query = "SELECT r FROM ReglaTrigger r WHERE r.evento like :evento ORDER BY r.orden ASC"),
    @NamedQuery(name = "ReglaTrigger.findByIdTrigger", query = "SELECT r FROM ReglaTrigger r WHERE r.idTrigger = :idTrigger")})
public class ReglaTrigger implements Serializable, Comparable<ReglaTrigger> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "id_trigger")
    private String idTrigger;
    @Lob
    @Size(max = 2147483647)
    private String desccripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTrigger")
    private List<Condicion> condicionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTrigger")
    private List<Accion> accionList;
//    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
//    @ManyToOne(optional = false)
//    private Area idArea;
    @Column(name = "regla_activa")
    private Boolean reglaActiva = true;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "evento")
    private String evento;
    @Size(min = 1, max = 40)
    @Column(name = "any_or_all")
    private String anyOrAll;
    //ALTER TABLE regla_trigger ADD COLUMN "orden" integer NOT NULL DEFAULT 0;
    @Basic(optional = false)
    @NotNull
    private Integer orden;
    

    public ReglaTrigger() {
        condicionList = new ArrayList<Condicion>();
    }

    public ReglaTrigger(String idTrigger) {
        this.idTrigger = idTrigger;
    }

    public String getIdTrigger() {
        return idTrigger;
    }

    public void setIdTrigger(String idTrigger) {
        this.idTrigger = idTrigger;
    }

    public String getDesccripcion() {
        return desccripcion;
    }

    public void setDesccripcion(String desccripcion) {
        this.desccripcion = desccripcion;
    }

    @XmlTransient
    public List<Condicion> getCondicionList() {
        return condicionList;
    }

    public void setCondicionList(List<Condicion> condicionList) {
        this.condicionList = condicionList;
    }

    @XmlTransient
    public List<Accion> getAccionList() {
        return accionList;
    }

    public void setAccionList(List<Accion> accionList) {
        this.accionList = accionList;
    }

//    public Area getIdArea() {
//        return idArea;
//    }
//
//    public void setIdArea(Area idArea) {
//        this.idArea = idArea;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTrigger != null ? idTrigger.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReglaTrigger)) {
            return false;
        }
        ReglaTrigger other = (ReglaTrigger) object;
        if ((this.idTrigger == null && other.idTrigger != null) || (this.idTrigger != null && !this.idTrigger.equals(other.idTrigger))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        return (evento.equalsIgnoreCase("CREATE") ? "<b>Cuando se cree un caso</b>":
                (evento.equalsIgnoreCase("UPDATE OR CREATE")?"<b>Cuando se cree o modifique un caso</b>":"<b>Cuando se modifique un caso</b>")) 
                + " y se cumplan <b>"+((anyOrAll == null)?"todas":(anyOrAll.equals("ANY")?"cualquiera de":"todas"))+" las Condiciones </b>(" + getCondicionList().size() + "):<br/>"+
                formatList(getCondicionList()) + " <br/><b>Ejecutar Acciones</b> (" + getAccionList().size() + "):<br/>" + formatList(getAccionList());
    }
    
    public String formatList(List lista)
    {
        StringBuilder sb = new StringBuilder("<ul>");
        for (Object object : lista) {
            sb.append("<li>");
            sb.append(object.toString());
            sb.append("</li>");
        }
        sb.append("</ul>");
        return sb.toString();
    }

  

    /**
     * @return the reglaActiva
     */
    public Boolean getReglaActiva() {
        return reglaActiva;
    }

    /**
     * @param reglaActiva the reglaActiva to set
     */
    public void setReglaActiva(Boolean reglaActiva) {
        this.reglaActiva = reglaActiva;
    }

    /**
     * @return the evento
     */
    public String getEvento() {
        return evento;
    }

    /**
     * @param evento the evento to set
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * @return the orden
     */
    public Integer getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public int compareTo(ReglaTrigger o) {
        //ascending order
       return this.orden - o.orden;
    }

    /**
     * @return the anyOrAll
     */
    public String getAnyOrAll() {
        return anyOrAll;
    }

    /**
     * @param anyOrAll the anyOrAll to set
     */
    public void setAnyOrAll(String anyOrAll) {
        this.anyOrAll = anyOrAll;
    }
}
