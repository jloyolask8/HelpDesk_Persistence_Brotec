/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumEstadoCaso;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "tipo_caso")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCaso.findAll", query = "SELECT o FROM TipoCaso o"),
    @NamedQuery(name = "TipoCaso.findAllByQuery", query = "SELECT o FROM TipoCaso o WHERE (LOWER(o.nombre) LIKE CONCAT(LOWER(:q), '%')) ")
})
public class TipoCaso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "id_tipo_caso")
    private String idTipoCaso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
//    @OneToMany(mappedBy = "tipoCaso")
//    private List<Caso> casoList;
    @OneToMany(mappedBy = "tipoCaso", cascade = CascadeType.ALL)
    private List<SubEstadoCaso> subEstadoCasoList;
//    @OneToMany(mappedBy = "tipoCaso")
//    private List<Prioridad> prioridadList;

    @ManyToMany(mappedBy = "tipoCasoList", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CustomField> customFieldList;
    
    @Transient
    public SubEstadoCaso getSubEstadoInicial(){
        if(subEstadoCasoList != null){
            for (SubEstadoCaso subEstado : subEstadoCasoList) {
                if(subEstado.isFirst()){
                    return subEstado;
                }
            }
        }
        return null;
    }
    
    @Transient
    public List<SubEstadoCaso> getSubEstadosAbierto(){
        
        if(subEstadoCasoList != null){
            List<SubEstadoCaso> listOpenSubEstados = new LinkedList<>();
            for (SubEstadoCaso subEstado : subEstadoCasoList) {
                if(subEstado.getIdEstado().equals(EnumEstadoCaso.ABIERTO.getEstado())){
                    listOpenSubEstados.add(subEstado);
                }
            }
            return listOpenSubEstados;
        }
        return Collections.EMPTY_LIST;
    }
    
    @Transient
    public List<SubEstadoCaso> getSubEstadosCerrado(){
         if(subEstadoCasoList != null){
            List<SubEstadoCaso> listSubEstados = new LinkedList<>();
            for (SubEstadoCaso subEstado : subEstadoCasoList) {
                if(subEstado.getIdEstado().equals(EnumEstadoCaso.CERRADO.getEstado())){
                    listSubEstados.add(subEstado);
                }
            }
            return listSubEstados;
        }
        return Collections.EMPTY_LIST;
    }

    public TipoCaso() {
    }

    public TipoCaso(String idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
    }

    public TipoCaso(String idTipoCaso, String nombre) {
        this.idTipoCaso = idTipoCaso;
        this.nombre = nombre;
    }

    public TipoCaso(String idTipoCaso, String nombre, String descripcion) {
        this.idTipoCaso = idTipoCaso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getIdTipoCaso() {
        return idTipoCaso;
    }

    public void setIdTipoCaso(String idTipoCaso) {
        this.idTipoCaso = idTipoCaso;
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

//    @XmlTransient
//    public List<Caso> getCasoList() {
//        return casoList;
//    }
//
//    public void setCasoList(List<Caso> casoList) {
//        this.casoList = casoList;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoCaso != null ? idTipoCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoCaso)) {
            return false;
        }
        TipoCaso other = (TipoCaso) object;
        if ((this.idTipoCaso == null && other.idTipoCaso != null) || (this.idTipoCaso != null && !this.idTipoCaso.equals(other.idTipoCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + nombre;
    }

    /**
     * @return the subEstadoCasoList
     */
    public List<SubEstadoCaso> getSubEstadoCasoList() {
        return subEstadoCasoList;
    }

    /**
     * @param subEstadoCasoList the subEstadoCasoList to set
     */
    public void setSubEstadoCasoList(List<SubEstadoCaso> subEstadoCasoList) {
        this.subEstadoCasoList = subEstadoCasoList;
    }

    /**
     * @return the customFieldList
     */
    public List<CustomField> getCustomFieldList() {
        return customFieldList;
    }

    /**
     * @param customFieldList the customFieldList to set
     */
    public void setCustomFieldList(List<CustomField> customFieldList) {
        this.customFieldList = customFieldList;
    }

}
