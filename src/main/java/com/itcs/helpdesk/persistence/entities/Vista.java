/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.CascadeOnDelete;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "vista")
@NamedQueries({
    @NamedQuery(name = "Vista.findAll", query = "SELECT v FROM Vista v"),
    @NamedQuery(name = "Vista.findByIdVista", query = "SELECT v FROM Vista v WHERE v.idVista = :idVista"),
    @NamedQuery(name = "Vista.findByNombre", query = "SELECT v FROM Vista v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "Vista.findByDescripcion", query = "SELECT v FROM Vista v WHERE v.descripcion = :descripcion")
})
public class Vista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vista", nullable = false)
    private Integer idVista;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Nombre", fieldIdFull = "nombre", fieldTypeFull = String.class)
    @Basic(optional = false)
    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Descripción", fieldIdFull = "descripcion", fieldTypeFull = String.class)
    @Column(name = "descripcion")
    private String descripcion;
    @FilterField(fieldTypeId = EnumFieldType.CHECKBOX, label = "Visible a todos", fieldIdFull = "visibleToAll", fieldTypeFull = Boolean.class)
    @Basic(optional = false)
    @NotNull
    @Column(name = "visible_to_all", nullable = false)
    private boolean visibleToAll;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Creada por", fieldIdFull = "idUsuarioCreadaPor.capitalName", fieldTypeFull = String.class)
    @JoinColumn(name = "id_usuario_creada_por", referencedColumnName = "id_usuario", nullable = false)
    @ManyToOne(optional = false)
    private Usuario idUsuarioCreadaPor;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Grupo", fieldIdFull = "idGrupo.idGrupo", fieldTypeFull = String.class)
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    @ManyToOne
    private Grupo idGrupo;
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Area", fieldIdFull = "idArea.idArea", fieldTypeFull = String.class)
    @JoinColumn(name = "id_area", referencedColumnName = "id_area")
    @ManyToOne
    private Area idArea;
    @CascadeOnDelete
    @OneToMany(mappedBy = "idVista", cascade = CascadeType.MERGE)
    private List<FiltroVista> filtrosVistaList;
    @Size(max = 1000)
    @Column(name = "base_entity_type", nullable = false, length = 1000)
    private String baseEntityType;

    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "fecha Creacion", fieldIdFull = "fechaCreacion", fieldTypeFull = Date.class)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @FilterField(fieldTypeId = EnumFieldType.CALENDAR, label = "fecha Modificacion", fieldIdFull = "fechaModif", fieldTypeFull = Date.class)
    @Column(name = "fecha_modif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;

    public Vista() {
    }

    public Vista(Class clazz) {
        this.baseEntityType = clazz.getName();
        filtrosVistaList = new ArrayList<>();
    }

    public Vista(Integer idVista) {
        this.idVista = idVista;
    }

    public Vista(Integer idVista, String nombre, boolean visibleToAll) {
        this.idVista = idVista;
        this.nombre = nombre;
        this.visibleToAll = visibleToAll;
    }

    public Integer getIdVista() {
        return idVista;
    }

    public void setIdVista(Integer idVista) {
        this.idVista = idVista;
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

    public boolean getVisibleToAll() {
        return visibleToAll;
    }

    public void setVisibleToAll(boolean visibleToAll) {
        this.visibleToAll = visibleToAll;
    }

    public Usuario getIdUsuarioCreadaPor() {
        return idUsuarioCreadaPor;
    }

    public void setIdUsuarioCreadaPor(Usuario idUsuarioCreadaPor) {
        this.idUsuarioCreadaPor = idUsuarioCreadaPor;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Area getIdArea() {
        return idArea;
    }

    public void setIdArea(Area idArea) {
        this.idArea = idArea;
    }

    public List<FiltroVista> getFiltrosVistaList() {
        return filtrosVistaList;
    }

    public void setFiltrosVistaList(List<FiltroVista> filtrosVistaList) {
        this.filtrosVistaList = filtrosVistaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVista != null ? idVista.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vista)) {
            return false;
        }
        Vista other = (Vista) object;
        if ((this.idVista == null && other.idVista != null) || (this.idVista != null && !this.idVista.equals(other.idVista))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vista [" + "Criterios: " + filtrosVistaList + (idArea != null ? " Area: " + idArea : "") + (idArea != null ? " Grupo: " + idGrupo : "") + " visibleToAll: " + visibleToAll + " creadaPor:" + this.idUsuarioCreadaPor + "]";
    }

    public void addFiltroVista(FiltroVista filtro) {
        if (this.getFiltrosVistaList() == null || this.getFiltrosVistaList().isEmpty()) {
            this.setFiltrosVistaList(new ArrayList<FiltroVista>());
        }
        this.getFiltrosVistaList().add(filtro);
        filtro.setIdVista(this);
    }

    public void addNewFiltroVista(FiltroVista filtroActual) {
        FiltroVista filtro = new FiltroVista();
        Random randomGenerator = new Random();
        int n = randomGenerator.nextInt();
        if (n > 0) {
            n = n * (-1);
        }
        filtro.setIdFiltro(n);//Ugly patch to solve identifier unknown when new items are added to the datatable.
        if (this.getFiltrosVistaList() == null || this.getFiltrosVistaList().isEmpty()) {
            this.setFiltrosVistaList(new LinkedList<FiltroVista>());
        }
        if (filtroActual != null) {
            int index = this.getFiltrosVistaList().indexOf(filtroActual);
            if (index >= 0) {
                this.getFiltrosVistaList().add(index + 1, filtro);
            } else {
                this.getFiltrosVistaList().add(filtro);
            }
        } else {
            this.getFiltrosVistaList().add(filtro);
        }
        filtro.setIdVista(this);
    }

    public void addNewFiltroVista() {
        addNewFiltroVista(null);
    }

    public boolean canRemoveFiltroVista() {
        if (this.getFiltrosVistaList() != null && !this.getFiltrosVistaList().isEmpty()) {
            return this.getFiltrosVistaList().size() > 1;
        }
        return false;
    }

    public void removeFiltroFromVista(FiltroVista filtro) {
        if (this.getFiltrosVistaList() != null) {
            if (this.getFiltrosVistaList().contains(filtro)) {
                this.getFiltrosVistaList().remove(filtro);
            }
            filtro.setIdVista(null);
        }
    }

    /**
     * @return the baseEntityType
     */
    public String getBaseEntityType() {
        return baseEntityType;
    }

    /**
     * @param baseEntityType the baseEntityType to set
     */
    public void setBaseEntityType(String baseEntityType) {
        this.baseEntityType = baseEntityType;
    }

    /**
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    /**
     * @return the fechaModif
     */
    public Date getFechaModif() {
        return fechaModif;
    }

    /**
     * @param fechaModif the fechaModif to set
     */
    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }
}
