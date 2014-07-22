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
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Producto.findAll", query = "SELECT p FROM Producto p"),
    @NamedQuery(name = "Producto.findByIdProducto", query = "SELECT p FROM Producto p WHERE p.idProducto = :idProducto"),
    @NamedQuery(name = "Producto.findByNombre", query = "SELECT p FROM Producto p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "Producto.findByDescripcion", query = "SELECT p FROM Producto p WHERE p.descripcion = :descripcion")})
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ID_PRODUCTO")
    private String idProducto;
    @Size(max = 200)
    private String nombre;
    @Lob
    @Size(max = 2147483647)
    private String descripcion;
    @OneToMany(mappedBy = "idProducto")
    private List<Caso> casoList;
    @OneToMany(mappedBy = "idProducto", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Componente> componenteList;
    @OneToMany(mappedBy = "producto")
    private List<ModeloProducto> modeloProductoList;
    //Areas que soportan este producto, ej: PreVenta, PostVenta
//    @OneToMany(mappedBy = "producto")
//    private List<Area> areaList;
    @ManyToMany(mappedBy = "productoList", fetch = FetchType.EAGER)
    private List<Grupo> grupoList;
    //
    @Column(name = "id_logo")
    private Long idLogo;
    
    @JoinColumn(name = "id_out_canal", referencedColumnName = "id_canal")
    @ManyToOne
    private Canal idOutCanal;

    public Producto() {
    }

    public Producto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
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
    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    @XmlTransient
    public List<Componente> getComponenteList() {
        return componenteList;
    }

    public void setComponenteList(List<Componente> componenteList) {
        this.componenteList = componenteList;
    }

    @XmlTransient
    public List<ModeloProducto> getModeloProductoList() {
        if (modeloProductoList == null) {
            modeloProductoList = new ArrayList<ModeloProducto>();
        }
        return modeloProductoList;
    }

    public void setModeloProductoList(List<ModeloProducto> modeloProductoList) {
        this.modeloProductoList = modeloProductoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProducto != null ? idProducto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.idProducto == null && other.idProducto != null) || (this.idProducto != null && !this.idProducto.equals(other.idProducto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * @return the grupoList
     */
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    /**
     * @param grupoList the grupoList to set
     */
    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    /**
     * @return the idLogo
     */
    public Long getIdLogo() {
        return idLogo;
    }

    /**
     * @param idLogo the idLogo to set
     */
    public void setIdLogo(Long idLogo) {
        this.idLogo = idLogo;
    }

    /**
     * @return the idOutCanal
     */
    public Canal getIdOutCanal() {
        return idOutCanal;
    }

    /**
     * @param idOutCanal the idOutCanal to set
     */
    public void setIdOutCanal(Canal idOutCanal) {
        this.idOutCanal = idOutCanal;
    }

}
