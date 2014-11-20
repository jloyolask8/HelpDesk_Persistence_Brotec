/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jonathan
 */
//@Embeddable
public class CasoCustomFieldPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_caso")
    private Long idCaso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_custom_field")
    private Long customField;

    public CasoCustomFieldPK() {
    }

    public CasoCustomFieldPK(Long idCaso, Long idCustomField) {
        this.idCaso = idCaso;
        this.customField = idCustomField;
    }

    public Long getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Long idCaso) {
        this.idCaso = idCaso;
    }

    public Long getCustomField() {
        return customField;
    }

    public void setCustomField(Long idCustomField) {
        this.customField = idCustomField;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idCaso);
        hash = 97 * hash + Objects.hashCode(this.customField);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CasoCustomFieldPK other = (CasoCustomFieldPK) obj;
        if (!Objects.equals(this.idCaso, other.idCaso)) {
            return false;
        }
        if (!Objects.equals(this.customField, other.customField)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CasoCustomFieldPK{" + "idCaso=" + idCaso + ", customField=" + customField + '}';
    }

   

    

   
    
}
