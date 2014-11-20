/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "caso_custom_field")
@IdClass(CasoCustomFieldPK.class)
@NamedQueries({
    @NamedQuery(name = "CasoCustomField.findAll", query = "SELECT c FROM CasoCustomField c")})
public class CasoCustomField implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @JoinColumn(name = "id_caso", referencedColumnName = "id_caso")
    @ManyToOne(optional = false)
    private Caso idCaso;
    @Id
    @JoinColumn(name = "id_custom_field", referencedColumnName = "id_custom_field")
    @ManyToOne(optional = false)
    private CustomField customField;

    @Size(max = 2147483647)
    private String valor;
    @Size(max = 2147483647)
    private String valor2;

    public CasoCustomField() {
    }

    public CasoCustomField(CustomField customField, Caso caso) {
        this.customField = customField;
        this.idCaso = caso;
        this.valor = "";
        this.valor2 = "";
    }

//    public CasoCustomField(CasoCustomFieldPK casoCustomFieldPK) {
//        this.casoCustomFieldPK = casoCustomFieldPK;
//    }
//    public CasoCustomField(Long idCaso, String fieldKey, String entity) {
//        this.casoCustomFieldPK = new CasoCustomFieldPK(idCaso, fieldKey, entity);
//    }
//
//    public CasoCustomField(Caso idCaso, CustomFieldPK customFieldPK) {
//        this.casoCustomFieldPK = new CasoCustomFieldPK(idCaso.getIdCaso(), customFieldPK.getFieldKey(), customFieldPK.getEntity());
//        this.caso = idCaso;
//    }
//    public CasoCustomFieldPK getCasoCustomFieldPK() {
//        return casoCustomFieldPK;
//    }
//
//    public void setCasoCustomFieldPK(CasoCustomFieldPK casoCustomFieldPK) {
//        this.casoCustomFieldPK = casoCustomFieldPK;
//    }
    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getValor2() {
        return valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public CustomField getCustomField() {
        return customField;
    }

    public void setCustomField(CustomField customField) {
        this.customField = customField;
    }

    public Caso getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(Caso caso) {
        this.idCaso = caso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idCaso);
        hash = 53 * hash + Objects.hashCode(this.customField);
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
        final CasoCustomField other = (CasoCustomField) obj;
        if (!Objects.equals(this.idCaso, other.idCaso)) {
            return false;
        }
        return Objects.equals(this.customField, other.customField);
    }

    @Override
    public String toString() {
        return "CasoCustomField{" + "idCaso=" + idCaso + ", customField=" + customField + ", valor=" + valor + ", valor2=" + valor2 + '}';
    }

   

   

    /**
     * @return the valoresList
     */
    @Transient
    public List<String> getValoresList() {
        final List<String> result = new ArrayList<>();
        if (valor != null) {
            for (String value : valor.split(",", -1)) {
                final String trimmedValue = value.trim();
                result.add(trimmedValue);
            }
        }
        return result;
    }

    /**
     * @param valoresList the valoresList to set
     */
    @Transient
    public void setValoresList(List<String> valoresList) {
        valor = "";
        boolean first = true;
        for (String string : valoresList) {
            if (first) {
                first = false;
                valor += string;
            } else {
                valor += "," + string;
            }
        }
    }
}
