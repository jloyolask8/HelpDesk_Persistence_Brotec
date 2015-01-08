/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
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
@Table(name = "filtro_vista")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FiltroVista.findAll", query = "SELECT f FROM FiltroVista f"),
    @NamedQuery(name = "FiltroVista.findByIdFiltro", query = "SELECT f FROM FiltroVista f WHERE f.idFiltro = :idFiltro"),
    @NamedQuery(name = "FiltroVista.findByValor", query = "SELECT f FROM FiltroVista f WHERE f.valor = :valor")})
public class FiltroVista implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_filtro", nullable = false)
    private Integer idFiltro;
    @JoinColumn(name = "id_vista", referencedColumnName = "id_vista")
    @ManyToOne
    private Vista idVista;
    //1. El campo a comparar
    @Size(max = 255)
    @Column(name = "id_campo", length = 255)
    private String idCampo;
    //2. el comparador a utilizar
    @JoinColumn(name = "id_comparador", referencedColumnName = "id_comparador")
    @ManyToOne
    private TipoComparacion idComparador;
    //3. El valor a comparar con el campo.
    @Size(max = 5000)
    @Column(name = "valor", length = 5000)
    private String valor;
    @Size(max = 5000)
    @Column(name = "valor2", length = 5000)
    private String valor2;
    @Column(name = "visible_to_agents", nullable = false)
    private boolean visibleToAgents;

    @Column(name = "valor_label")
    private String valorLabel;
    @Column(name = "valor2_label")
    private String valor2Label;

    public FiltroVista() {
    }

    public FiltroVista(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public Vista getIdVista() {
        return idVista;
    }

    public void setIdVista(Vista idVista) {
        this.idVista = idVista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFiltro != null ? idFiltro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FiltroVista)) {
            return false;
        }
        FiltroVista other = (FiltroVista) object;
        if ((this.idFiltro == null && other.idFiltro != null) || (this.idFiltro != null && !this.idFiltro.equals(other.idFiltro))) {
            return false;
        }
        return true;
    }

    public TipoComparacion getIdComparador() {
        return idComparador;
    }

    public void setIdComparador(TipoComparacion idComparador) {
        this.idComparador = idComparador;
    }

    public String getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(String idCampo) {
        this.idCampo = idCampo;
    }

    @Override
    public String toString() {
        return "FiltroVista [ id= " + idFiltro + "[" + idCampo + " " + idComparador + " " + valor + " " + valor2 + "]]";
    }

    /**
     * @return the valor2
     */
    public String getValor2() {
        return valor2;
    }

    /**
     * @param valor2 the valor2 to set
     */
    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    /**
     * @return the visibleToAgents
     */
    public boolean isVisibleToAgents() {
        return visibleToAgents;
    }

    /**
     * @param visibleToAgents the visibleToAgents to set
     */
    public void setVisibleToAgents(boolean visibleToAgents) {
        this.visibleToAgents = visibleToAgents;
    }

    /**
     * @return the valoresList
     */
    public List<String> getValoresList() {
        final List<String> result = new ArrayList<String>();
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
    
    /**
     * @return the valoresList
     */
    public List<SelectItem> getValoresAsSelectItemList() {
        final List<SelectItem> result = new ArrayList<>();
        if (valor != null && valorLabel != null ) {
            final String[] splitValues = valor.split(",", -1);
            final String[] splitLabels = valorLabel.split(",", -1);
            
            for (int i = 0; i< splitValues.length ; i++ ) {
                String value = splitValues[i].trim();
                String label = splitLabels[i].trim();
                
                result.add(new SelectItem(value, label));
            }
        }
        return result;
    }

    /**
     * @param valoresList the valoresList to set
     */
    public void setValoresAsSelectItemList(List<SelectItem> valoresList) {
        valor = "";
        valorLabel = "";
        boolean first = true;
        for (SelectItem selectItem : valoresList) {
            if (first) {
                first = false;
                valor += selectItem.getValue().toString();
                valorLabel += selectItem.getLabel();
            } else {
                valor += "," + selectItem.getValue().toString();
                valorLabel += "," + selectItem.getLabel();
            }
        }
    }

    /**
     * @return the valorAsSelectItem
     */
    @Transient
    public SelectItem getValorAsSelectItem() {
        return new SelectItem(valor, valorLabel);
    }

    /**
     * @param valorAsSelectItem the valorAsSelectItem to set
     */
    @Transient
    public void setValorAsSelectItem(SelectItem valorAsSelectItem) {
        this.valor = valorAsSelectItem.getValue().toString();
        this.valorLabel = valorAsSelectItem.getLabel();
    }

    /**
     * @return the valor2AsSelectItem
     */
    @Transient
    public SelectItem getValor2AsSelectItem() {
        return new SelectItem(valor2, valor2Label);
    }

    /**
     * @param valor2AsSelectItem the valor2AsSelectItem to set
     */
    @Transient
    public void setValor2AsSelectItem(SelectItem valor2AsSelectItem) {
        this.valor2 = valor2AsSelectItem.getValue().toString();
        this.valor2Label = valor2AsSelectItem.getLabel();
    }

    /**
     * @return the valorLabel
     */
    public String getValorLabel() {
        return valorLabel;
    }

    /**
     * @param valorLabel the valorLabel to set
     */
    public void setValorLabel(String valorLabel) {
        this.valorLabel = valorLabel;
    }

    /**
     * @return the valor2Label
     */
    public String getValor2Label() {
        return valor2Label;
    }

    /**
     * @param valor2Label the valor2Label to set
     */
    public void setValor2Label(String valor2Label) {
        this.valor2Label = valor2Label;
    }
}
