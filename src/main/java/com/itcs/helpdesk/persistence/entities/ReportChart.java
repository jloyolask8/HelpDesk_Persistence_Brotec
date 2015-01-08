/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "report_chart")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type=TenantTableDiscriminatorType.SCHEMA, contextProperty="eclipselink.tenant-id")
@NamedQueries({
    @NamedQuery(name = "ReportChart.findAll", query = "SELECT r FROM ReportChart r"),
    @NamedQuery(name = "ReportChart.findByIdChartReport", query = "SELECT r FROM ReportChart r WHERE r.idChartReport = :idChartReport"),
    @NamedQuery(name = "ReportChart.findByTitulo", query = "SELECT r FROM ReportChart r WHERE r.titulo = :titulo"),
    @NamedQuery(name = "ReportChart.findByNombre", query = "SELECT r FROM ReportChart r WHERE r.nombre = :nombre"),
    @NamedQuery(name = "ReportChart.findByBaseEntityType", query = "SELECT r FROM ReportChart r WHERE r.baseEntityType = :baseEntityType"),
    @NamedQuery(name = "ReportChart.findByVisibleToAll", query = "SELECT r FROM ReportChart r WHERE r.visibleToAll = :visibleToAll"),
    @NamedQuery(name = "ReportChart.findByIdArea", query = "SELECT r FROM ReportChart r WHERE r.idArea = :idArea"),
    @NamedQuery(name = "ReportChart.findByFechaCreacion", query = "SELECT r FROM ReportChart r WHERE r.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "ReportChart.findByFechaModif", query = "SELECT r FROM ReportChart r WHERE r.fechaModif = :fechaModif")})
public class ReportChart implements Serializable, Comparable<ReportChart> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_chart_report")
    private Long idChartReport;
    @Size(max = 2147483647)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 2147483647)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "chart_type")
    private String chartType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "base_entity_type")
    private String baseEntityType;
    @Basic(optional = false)
    @NotNull
    @Column(name = "visible_to_all")
    private boolean visibleToAll;
    @Column(name = "column_index")
    private Integer columnIndex;
    @Column(name = "item_index")
    private Integer itemIndex;
    @Size(max = 40)
    @Column(name = "id_area")
    private String idArea;
    @Size(max = 255)
    @Column(name = "id_campo_base")
    private String idCampoBase;
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
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "fecha_modif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @OneToMany(mappedBy = "idChartReport")
    private List<ReportChartSerie> reportChartSerieList;
    @OneToMany(mappedBy = "idChartReport")
    private List<ReportChartFilter> reportChartFilterList;
    @JoinColumn(name = "id_usuario_createdby", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuarioCreatedby;
    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
    @ManyToOne
    private Grupo idGrupo;

    public ReportChart() {
    }

    public ReportChart(Long idChartReport) {
        this.idChartReport = idChartReport;
    }

    public ReportChart(Long idChartReport, String nombre, String chartType, String baseEntityType, boolean visibleToAll) {
        this.idChartReport = idChartReport;
        this.nombre = nombre;
        this.chartType = chartType;
        this.baseEntityType = baseEntityType;
        this.visibleToAll = visibleToAll;
    }

    public Long getIdChartReport() {
        return idChartReport;
    }

    public void setIdChartReport(Long idChartReport) {
        this.idChartReport = idChartReport;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getChartType() {
        return chartType;
    }

    public void setChartType(String chartType) {
        this.chartType = chartType;
    }

    public String getBaseEntityType() {
        return baseEntityType;
    }

    public void setBaseEntityType(String baseEntityType) {
        this.baseEntityType = baseEntityType;
    }

    public boolean getVisibleToAll() {
        return visibleToAll;
    }

    public void setVisibleToAll(boolean visibleToAll) {
        this.visibleToAll = visibleToAll;
    }

    public Integer getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(Integer columnIndex) {
        this.columnIndex = columnIndex;
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getIdArea() {
        return idArea;
    }

    public void setIdArea(String idArea) {
        this.idArea = idArea;
    }

    public String getIdCampoBase() {
        return idCampoBase;
    }

    public void setIdCampoBase(String idCampoBase) {
        this.idCampoBase = idCampoBase;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public List<ReportChartSerie> getReportChartSerieList() {
        return reportChartSerieList;
    }

    public void setReportChartSerieList(List<ReportChartSerie> reportChartSerieList) {
        this.reportChartSerieList = reportChartSerieList;
    }

    public Usuario getIdUsuarioCreatedby() {
        return idUsuarioCreatedby;
    }

    public void setIdUsuarioCreatedby(Usuario idUsuarioCreatedby) {
        this.idUsuarioCreatedby = idUsuarioCreatedby;
    }

    public Grupo getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Grupo idGrupo) {
        this.idGrupo = idGrupo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idChartReport != null ? idChartReport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReportChart)) {
            return false;
        }
        ReportChart other = (ReportChart) object;
        if ((this.idChartReport == null && other.idChartReport != null) || (this.idChartReport != null && !this.idChartReport.equals(other.idChartReport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReportChart{" + "idChartReport=" + idChartReport + ", titulo=" + titulo + ", nombre=" + nombre + ", descripcion=" + descripcion + ", chartType=" + chartType + ", baseEntityType=" + baseEntityType + ", visibleToAll=" + visibleToAll + ", columnIndex=" + columnIndex + ", itemIndex=" + itemIndex + ", idArea=" + idArea + ", idCampoBase=" + idCampoBase + ", idComparador=" + idComparador + ", valor=" + valor + ", valor2=" + valor2 + ", valorLabel=" + valorLabel + ", valor2Label=" + valor2Label + ", fechaCreacion=" + fechaCreacion + ", fechaModif=" + fechaModif + ", reportChartSerieList=" + reportChartSerieList + ", idUsuarioCreatedby=" + idUsuarioCreatedby + ", idGrupo=" + idGrupo + '}';
    }

    /**
     * @return the reportChartFilterList
     */
    public List<ReportChartFilter> getReportChartFilterList() {
        return reportChartFilterList;
    }

    /**
     * @param reportChartFilterList the reportChartFilterList to set
     */
    public void setReportChartFilterList(List<ReportChartFilter> reportChartFilterList) {
        this.reportChartFilterList = reportChartFilterList;
    }

    @Override
    public int compareTo(ReportChart o) {

        int colIndex1 = this.getColumnIndex() == null ? 0 : this.getColumnIndex();
        int rowIndex1 = this.getItemIndex() == null ? 0 : this.itemIndex;
        int colIndex2 = o.getColumnIndex() == null ? 0 : o.getColumnIndex();
        int rowIndex2 = o.getItemIndex() == null ? 0 : o.itemIndex;
        if ((colIndex1 - colIndex2) == 0) {
            return rowIndex1 - rowIndex2;
        } else {
            return (colIndex1 - colIndex2);
        }
    }

}
