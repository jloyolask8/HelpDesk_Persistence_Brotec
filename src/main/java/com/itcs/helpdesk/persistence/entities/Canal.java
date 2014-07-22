/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
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
    @NamedQuery(name = "Canal.findAll", query = "SELECT c FROM Canal c"),
    @NamedQuery(name = "Canal.findByIdCanal", query = "SELECT c FROM Canal c WHERE c.idCanal = :idCanal"),
    @NamedQuery(name = "Canal.findByNombre", query = "SELECT c FROM Canal c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Canal.findByDescripcion", query = "SELECT c FROM Canal c WHERE c.descripcion = :descripcion")})
public class Canal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull    
    @Column(name = "id_canal")
    private String idCanal;   
    private String nombre;
    @Column(name = "enabled")
    private Boolean enabled = Boolean.TRUE;    
    private String descripcion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "canal")
    private List<CanalSetting> canalSettingList;
    @JoinColumn(name = "id_tipo_canal", referencedColumnName = "id_tipo")
    @ManyToOne
    private TipoCanal idTipoCanal;
    
    @Transient
    private Map<String, String> propertieSettings;
    
    @OneToMany(mappedBy = "idCanal")
    private List<Caso> casoList;

    public Canal() {
    }

    public Canal(String idCanal) {
        this.idCanal = idCanal;
    }

    public Canal(String idCanal, String nombre, String descripcion) {
        this.idCanal = idCanal;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Canal(String idCanal, String nombre, String descripcion, TipoCanal idTipoCanal) {
        this.idCanal = idCanal;
        this.nombre = nombre;
        this.enabled = true;
        this.descripcion = descripcion;
        this.idTipoCanal = idTipoCanal;
    }

    public String getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
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
    public List<CanalSetting> getCanalSettingList() {
        return canalSettingList;
    }

    public void setCanalSettingList(List<CanalSetting> canalSettingList) {
        this.canalSettingList = canalSettingList;
    }

    public TipoCanal getIdTipoCanal() {
        return idTipoCanal;
    }

    public void setIdTipoCanal(TipoCanal idTipoCanal) {
        this.idTipoCanal = idTipoCanal;
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
        hash += (idCanal != null ? idCanal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Canal)) {
            return false;
        }
        Canal other = (Canal) object;
        if ((this.idCanal == null && other.idCanal != null) || (this.idCanal != null && !this.idCanal.equals(other.idCanal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public String getSetting(String key)
    {
        return getMapSetting().get(key);
    }
    
    public boolean containsKey(String key)
    {
        return getMapSetting().containsKey(key);
    }
    
    private Map<String, String> getMapSetting()
    {
        if(propertieSettings == null)
        {
            propertieSettings = new HashMap<String, String>();
            for (CanalSetting canalSetting : getCanalSettingList()) {
                propertieSettings.put(canalSetting.getCanalSettingPK().getCanalSettingKey(),
                        canalSetting.getCanalSettingValue());
            }
        }
        return propertieSettings;
    }
}
