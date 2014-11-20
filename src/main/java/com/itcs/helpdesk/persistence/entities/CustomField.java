/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "custom_field")
@NamedQueries({
    @NamedQuery(name = "CustomField.findAll", query = "SELECT c FROM CustomField c"),
    @NamedQuery(name = "CustomField.findByEntity", query = "SELECT c FROM CustomField c WHERE c.entity = :entity"),
    @NamedQuery(name = "CustomField.findByEntityForCustomers", query = "SELECT c FROM CustomField c WHERE c.entity = :entity and c.visibleToCustomers = TRUE")})
public class CustomField implements Serializable {

//  @EmbeddedId
//  protected CustomFieldPK customFieldPK;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_custom_field")

    private Long idCustomField;

    //Entity types that can have custom fields: case, client, product, component, subComponent
    @Basic(optional = false)
    @NotNull
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Tipo Entidad", fieldIdFull = "entity", fieldTypeFull = String.class)
    private String entity;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    private String label;
    @Basic(optional = false)
    @NotNull
    private boolean required;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visible_to_customers")
    private boolean visibleToCustomers;
    //@BatchFetch(value = BatchFetchType.EXISTS)
    @JoinColumn(name = "field_type_id", referencedColumnName = "field_type_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private FieldType fieldTypeId;
    @Size(max = 2147483647)
    @Column(name = "list_options")
    private String listOptions;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customField")
    private List<CasoCustomField> casoCustomFieldList;
   
    @JoinTable(name = "custom_field_tipo_caso", joinColumns = {
        @JoinColumn(name = "id_custom_field", referencedColumnName = "id_custom_field", table = "custom_field")
        }, inverseJoinColumns = {
        @JoinColumn(name = "id_tipo_caso", referencedColumnName = "id_tipo_caso", table = "tipo_caso")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<TipoCaso> tipoCasoList;
    
//    @JoinTable(name = "custom_field_tipo_accion", joinColumns = {
//        @JoinColumn(name = "id_custom_field", referencedColumnName = "id_custom_field", table = "custom_field")}
//            , inverseJoinColumns = {
//        @JoinColumn(name = "id_tipo_accion", referencedColumnName = "id_nombre_accion", table = "nombre_accion")})//id_tipo_accion->tipo_accion
//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    private List<TipoAccion> tipoAccionList;

    public CustomField() {
    }

    @Transient
    public boolean isCasoCustomField() {
        return this.getEntity().equalsIgnoreCase("case");
    }

//    public CustomField(CustomFieldPK customFieldPK) {
//        this.customFieldPK = customFieldPK;
//    }

    public CustomField(String label, boolean required, boolean visibleToCustomers) {
        this.label = label;
        this.required = required;
        this.visibleToCustomers = visibleToCustomers;
    }

    public CustomField(String entity) {
        this.entity =  entity;
    }

//    public CustomFieldPK getCustomFieldPK() {
//        return customFieldPK;
//    }
//
//    public void setCustomFieldPK(CustomFieldPK customFieldPK) {
//        this.customFieldPK = customFieldPK;
//    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean getVisibleToCustomers() {
        return visibleToCustomers;
    }

    public void setVisibleToCustomers(boolean visibleToCustomers) {
        this.visibleToCustomers = visibleToCustomers;
    }

    public List<CasoCustomField> getCasoCustomFieldList() {
        return casoCustomFieldList;
    }

    public void setCasoCustomFieldList(List<CasoCustomField> casoCustomFieldList) {
        this.casoCustomFieldList = casoCustomFieldList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getIdCustomField());
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
        final CustomField other = (CustomField) obj;
        return Objects.equals(this.getIdCustomField(), other.getIdCustomField());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CustomField [");
        builder.append(", fieldTypeId=");
        builder.append(fieldTypeId);
        builder.append(", label=");
        builder.append(label);
        builder.append(", tipoCasoList=");
        builder.append(tipoCasoList);

        builder.append("]");
        return builder.toString();
    }

    /**
     * @return the fieldTypeId
     */
    public FieldType getFieldTypeId() {
        return fieldTypeId;
    }

    /**
     * @param fieldTypeId the fieldTypeId to set
     */
    public void setFieldTypeId(FieldType fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }

    public List<TipoCaso> getTipoCasoList() {
        return tipoCasoList;
    }

    public void setTipoCasoList(List<TipoCaso> tipoCasoList) {
        this.tipoCasoList = tipoCasoList;
    }

    /**
     * @return the listOptions
     */
    public String getListOptions() {
        return listOptions;
    }

    /**
     * @param listOptions the listOptions to set
     */
    public void setListOptions(String listOptions) {
        this.listOptions = listOptions;
    }

    /**
     * @return the valoresList
     */
    public List<String> getFieldOptionsList() {
        final List<String> result = new ArrayList<String>();
        if (listOptions != null) {
            for (String value : listOptions.split(",", -1)) {
                final String trimmedValue = value.trim();
                result.add(trimmedValue);
            }
        }
        return result;
    }

    /**
     * @param valoresList the valoresList to set
     */
    public void setFieldOptionsList(List<String> valoresList) {
        listOptions = "";
        boolean first = true;
        for (String string : valoresList) {
            if (first) {
                first = false;
                listOptions += string;
            } else {
                listOptions += "," + string;
            }
        }
    }

//    /**
//     * @return the tipoAccionList
//     */
//    public List<TipoAccion> getTipoAccionList() {
//        return tipoAccionList;
//    }
//
//    /**
//     * @param tipoAccionList the tipoAccionList to set
//     */
//    public void setTipoAccionList(List<TipoAccion> tipoAccionList) {
//        this.tipoAccionList = tipoAccionList;
//    }

    /**
     * @return the entity
     */
    public String getEntity() {
        return entity;
    }

    /**
     * @param entity the entity to set
     */
    public void setEntity(String entity) {
        this.entity = entity;
    }

    /**
     * @return the idCustomField
     */
    public Long getIdCustomField() {
        return idCustomField;
    }

    /**
     * @param idCustomField the idCustomField to set
     */
    public void setIdCustomField(Long idCustomField) {
        this.idCustomField = idCustomField;
    }
}
