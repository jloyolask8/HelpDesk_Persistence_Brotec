/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author jonathan
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c"),
    @NamedQuery(name = "Cliente.findAllByQuery", query = "SELECT distinct c FROM Cliente c INNER JOIN EmailCliente e WHERE ((LOWER(c.rut) LIKE CONCAT(LOWER(:q), '%')) "
            + "OR (CONCAT(LOWER(c.nombres), ' ', LOWER(c.apellidos)) LIKE CONCAT(LOWER(:q), '%')) OR (LOWER(e.emailCliente) LIKE CONCAT(LOWER(:q), '%'))) AND e.cliente = c"),
    @NamedQuery(name = "Cliente.findByIdCliente", query = "SELECT c FROM Cliente c WHERE c.idCliente = :idCliente"),
    @NamedQuery(name = "Cliente.findByRut", query = "SELECT c FROM Cliente c WHERE c.rut = :rut"),
    @NamedQuery(name = "Cliente.findByNombres", query = "SELECT c FROM Cliente c WHERE c.nombres = :nombres"),
    @NamedQuery(name = "Cliente.findByApellidos", query = "SELECT c FROM Cliente c WHERE c.apellidos = :apellidos")
})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENTE")
    private Integer idCliente;
    @Size(max = 40)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "rut", fieldIdFull = "rut", fieldTypeFull = String.class)
    private String rut;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "nombres", fieldIdFull = "nombres", fieldTypeFull = String.class)
    private String nombres;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "apellidos", fieldIdFull = "apellidos", fieldTypeFull = String.class)
    private String apellidos;
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Sexo (Hombre/Mujer)", fieldIdFull = "sexo", fieldTypeFull = String.class)
    private String sexo;
    private String fono1;
    private String fono2;
    @Size(max = 400)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Dirección Particular", fieldIdFull = "dirParticular", fieldTypeFull = String.class)
    @Column(name = "DIR_PARTICULAR")
    private String dirParticular;
    @Size(max = 400)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Dirección Comercial", fieldIdFull = "dirComercial", fieldTypeFull = String.class)
    @Column(name = "DIR_COMERCIAL")
    private String dirComercial;
    //emails
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_PLACE_HOLDER, label = "Emails", fieldIdFull = "emailClienteList", fieldTypeFull = List.class)
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<EmailCliente> emailClienteList;
    //casos
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_PLACE_HOLDER, label = "Lista de Casos", fieldIdFull = "casoList", fieldTypeFull = List.class)
    @OneToMany(mappedBy = "idCliente", fetch = FetchType.EAGER)
    private List<Caso> casoList;
    //productos Contratados
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_PLACE_HOLDER, label = "productos Contratados", fieldIdFull = "productoContratadoList", fieldTypeFull = List.class)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente")
    private List<ProductoContratado> productoContratadoList;

    private String theme;

    public Cliente() {
    }

    public Cliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFono1() {
        return fono1;
    }

    public void setFono1(String fono1) {
        this.fono1 = fono1;
    }

    public String getFono2() {
        return fono2;
    }

    public void setFono2(String fono2) {
        this.fono2 = fono2;
    }

    public String getDirParticular() {
        return dirParticular;
    }

    public void setDirParticular(String dirParticular) {
        this.dirParticular = dirParticular;
    }

    public String getDirComercial() {
        return dirComercial;
    }

    public void setDirComercial(String dirComercial) {
        this.dirComercial = dirComercial;
    }

    public List<EmailCliente> getEmailClienteList() {
        if (emailClienteList == null) {
            emailClienteList = new LinkedList<EmailCliente>();
        }
        return emailClienteList;
    }

    public void setEmailClienteList(List<EmailCliente> emailClienteList) {
        this.emailClienteList = emailClienteList;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + (this.rut != null ? this.rut.hashCode() : 0);
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
        final Cliente other = (Cliente) obj;
        if ((this.rut == null) ? (other.rut != null) : !this.rut.equals(other.rut)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getCapitalName()).append(" (").append(rut).append(")");
        return builder.toString();
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return the sexo
     */
    public String getSexo() {
        return sexo;
    }

    /**
     * @param sexo the sexo to set
     */
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public List<ProductoContratado> getProductoContratadoList() {
        return productoContratadoList;
    }

    public void setProductoContratadoList(List<ProductoContratado> productoContratadoList) {
        this.productoContratadoList = productoContratadoList;
    }

    public String getCapitalName() {
        String capitalName = "";
        final String trimmedName = StringUtils.trim(nombres);
        final String trimmedApellidos = StringUtils.trim(apellidos);

        String[] names = new String[]{StringUtils.isEmpty(trimmedName) ? "" : trimmedName.split(" ")[0], StringUtils.isEmpty(trimmedApellidos) ? "" : trimmedApellidos.split(" ")[0]};
        for (int i = 0; i < names.length; i++) {
            if (!(names[i].trim().isEmpty())) {
                if (i > 0) {
                    capitalName += " ";
                }
                if (!names[i].isEmpty()) {
                    capitalName += names[i].substring(0, 1).toUpperCase() + names[i].substring(1).toLowerCase();
                }
            }
        }
        return capitalName;
    }

    public String getCapitalFullName() {
        String capitalName = "";
        final String trimmed = StringUtils.trim(nombres) + " " + StringUtils.trim(apellidos);

        String[] names = StringUtils.isEmpty(trimmed) ? null : trimmed.split(" ");
        if (names != null) {
            for (int i = 0; i < names.length; i++) {
                if (!(names[i].trim().isEmpty())) {
                    if (i > 0) {
                        capitalName += " ";
                    }
                    if (!names[i].isEmpty()) {
                        capitalName += names[i].substring(0, 1).toUpperCase() + names[i].substring(1).toLowerCase();
                    }
                }
            }
        }

        return capitalName;
    }

    /**
     * @return the casoList
     */
    public List<Caso> getCasoList() {
        return casoList;
    }

    /**
     * @param casoList the casoList to set
     */
    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }
}
