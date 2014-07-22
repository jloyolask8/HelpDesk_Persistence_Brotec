/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jorge
 */
@Embeddable
public class CanalSettingPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_canal", nullable = false, length = 20)
    private String idCanal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "canal_setting_key", nullable = false, length = 80)
    private String canalSettingKey;

    public CanalSettingPK() {
    }

    public CanalSettingPK(String idCanal, String canalSettingKey) {
        this.idCanal = idCanal;
        this.canalSettingKey = canalSettingKey;
    }

    public String getIdCanal() {
        return idCanal;
    }

    public void setIdCanal(String idCanal) {
        this.idCanal = idCanal;
    }

    public String getCanalSettingKey() {
        return canalSettingKey;
    }

    public void setCanalSettingKey(String canalSettingKey) {
        this.canalSettingKey = canalSettingKey;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCanal != null ? idCanal.hashCode() : 0);
        hash += (canalSettingKey != null ? canalSettingKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CanalSettingPK)) {
            return false;
        }
        CanalSettingPK other = (CanalSettingPK) object;
        if ((this.idCanal == null && other.idCanal != null) || (this.idCanal != null && !this.idCanal.equals(other.idCanal))) {
            return false;
        }
        if ((this.canalSettingKey == null && other.canalSettingKey != null) || (this.canalSettingKey != null && !this.canalSettingKey.equals(other.canalSettingKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itcs.helpdesk.persistence.entities.CanalSettingPK[ idCanal=" + idCanal + ", canalSettingKey=" + canalSettingKey + " ]";
    }
    
}
