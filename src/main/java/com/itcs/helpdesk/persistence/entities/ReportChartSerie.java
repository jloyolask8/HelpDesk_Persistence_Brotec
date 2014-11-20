/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "report_chart_serie")
@NamedQueries({   
    @NamedQuery(name = "ReportChartSerie.findByIdChartSerie", query = "SELECT r FROM ReportChartSerie r WHERE r.idChartSerie = :idChartSerie"),   
    @NamedQuery(name = "ReportChartSerie.findByVisible", query = "SELECT r FROM ReportChartSerie r WHERE r.visible = :visible")})
public class ReportChartSerie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chart_serie")
    private Integer idChartSerie;
    @Size(max = 255)
    @Column(name = "id_campo")
    private String idCampo;
//    @Size(max = 2)
//    @Column(name = "id_comparador")
//    private String idComparador;
    @JoinColumn(name = "id_comparador", referencedColumnName = "id_comparador")
    @ManyToOne
    private TipoComparacion idComparador;
    @Size(max = 2147483647)
    @Column(name = "valor")
    private String valor;
    @Size(max = 2147483647)
    @Column(name = "valor2")
    private String valor2;
    @Size(max = 2147483647)
    @Column(name = "valor_label")
    private String valorLabel;
    @Size(max = 2147483647)
    @Column(name = "valor2_label")
    private String valor2Label;
    @Size(max = 128)
    @Column(name = "label")
    private String label;
    @Column(name = "visible")
    private Boolean visible;
    @JoinColumn(name = "id_chart_report", referencedColumnName = "id_chart_report")
    @ManyToOne
    private ReportChart idChartReport;

    public ReportChartSerie() {
    }

    public ReportChartSerie(Integer idChartSerie) {
        this.idChartSerie = idChartSerie;
    }

    public Integer getIdChartSerie() {
        return idChartSerie;
    }

    public void setIdChartSerie(Integer idChartSerie) {
        this.idChartSerie = idChartSerie;
    }

    public String getIdCampo() {
        return idCampo;
    }

    public void setIdCampo(String idCampo) {
        this.idCampo = idCampo;
    }

    public TipoComparacion getIdComparador() {
        return idComparador;
    }

    public void setIdComparador(TipoComparacion idComparador) {
        this.idComparador = idComparador;
    }

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

    public String getValorLabel() {
        return valorLabel;
    }

    public void setValorLabel(String valorLabel) {
        this.valorLabel = valorLabel;
    }

    public String getValor2Label() {
        return valor2Label;
    }

    public void setValor2Label(String valor2Label) {
        this.valor2Label = valor2Label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public ReportChart getIdChartReport() {
        return idChartReport;
    }

    public void setIdChartReport(ReportChart idChartReport) {
        this.idChartReport = idChartReport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChartSerie != null ? idChartSerie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportChartSerie)) {
            return false;
        }
        ReportChartSerie other = (ReportChartSerie) object;
        if ((this.idChartSerie == null && other.idChartSerie != null) || (this.idChartSerie != null && !this.idChartSerie.equals(other.idChartSerie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itcs.helpdesk.persistence.entities.ReportChartSerie[ idChartSerie=" + idChartSerie + " ]";
    }
    
}
