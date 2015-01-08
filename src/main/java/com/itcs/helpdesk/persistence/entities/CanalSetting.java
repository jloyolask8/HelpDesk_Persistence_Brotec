/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "canal_setting")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CanalSetting.findAll", query = "SELECT c FROM CanalSetting c"),
    @NamedQuery(name = "CanalSetting.findByIdCanal", query = "SELECT c FROM CanalSetting c WHERE c.canalSettingPK.idCanal = :idCanal"),
    @NamedQuery(name = "CanalSetting.findByCanalSettingKey", query = "SELECT c FROM CanalSetting c WHERE c.canalSettingPK.canalSettingKey = :canalSettingKey"),
    @NamedQuery(name = "CanalSetting.findByCanalSettingValue", query = "SELECT c FROM CanalSetting c WHERE c.canalSettingValue = :canalSettingValue"),
    @NamedQuery(name = "CanalSetting.findByDescripcion", query = "SELECT c FROM CanalSetting c WHERE c.descripcion = :descripcion")})
public class CanalSetting implements Serializable {
    @JoinColumn(name = "id_canal", referencedColumnName = "id_canal", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Canal canal;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CanalSettingPK canalSettingPK;
    @Size(max = 2147483647)
    @Column(name = "canal_setting_value", length = 2147483647)
    private String canalSettingValue;
    @Size(max = 200)
    @Column(name = "descripcion", length = 200)
    private String descripcion;

    public CanalSetting() {
    }

    public CanalSetting(CanalSettingPK canalSettingPK) {
        this.canalSettingPK = canalSettingPK;
    }

    public CanalSetting(String idCanal, String canalSettingKey) {
        this.canalSettingPK = new CanalSettingPK(idCanal, canalSettingKey);
    }

    public CanalSetting(Canal canal, String canalSettingKey, String canalSettingValue, String descripcion) {
        this.canal = canal;
        this.canalSettingPK = new CanalSettingPK(canal.getIdCanal(), canalSettingKey);
        this.canalSettingValue = canalSettingValue;
        this.descripcion = descripcion;
    }

    public CanalSettingPK getCanalSettingPK() {
        return canalSettingPK;
    }

    public void setCanalSettingPK(CanalSettingPK canalSettingPK) {
        this.canalSettingPK = canalSettingPK;
    }

    public String getCanalSettingValue() {
        return canalSettingValue;
    }

    public void setCanalSettingValue(String canalSettingValue) {
        this.canalSettingValue = canalSettingValue;
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
        hash += (canalSettingPK != null ? canalSettingPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CanalSetting)) {
            return false;
        }
        CanalSetting other = (CanalSetting) object;
        if ((this.canalSettingPK == null && other.canalSettingPK != null) || (this.canalSettingPK != null && !this.canalSettingPK.equals(other.canalSettingPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itcs.helpdesk.persistence.entities.CanalSetting[ canalSettingPK=" + canalSettingPK + " ]";
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }
    
}
