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
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.eclipse.persistence.annotations.CascadeOnDelete;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantTableDiscriminator;
import org.eclipse.persistence.annotations.TenantTableDiscriminatorType;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "usuario")
@Multitenant(MultitenantType.TABLE_PER_TENANT)
@TenantTableDiscriminator(type = TenantTableDiscriminatorType.SCHEMA, contextProperty = "eclipselink.tenant-id")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findAllByQuery", query = "SELECT o FROM Usuario o WHERE (LOWER(o.idUsuario) LIKE CONCAT(LOWER(:q), '%')) "
            + "OR (CONCAT(LOWER(o.nombres), ' ', LOWER(o.apellidos)) LIKE CONCAT(LOWER(:q), '%'))"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByNombres", query = "SELECT u FROM Usuario u WHERE u.nombres = :nombres"),
    @NamedQuery(name = "Usuario.findByApellidos", query = "SELECT u FROM Usuario u WHERE u.apellidos = :apellidos"),
    @NamedQuery(name = "Usuario.findByEmail", query = "SELECT u FROM Usuario u WHERE LOWER(u.email) = LOWER(:email)"),
    @NamedQuery(name = "Usuario.findByActivo", query = "SELECT u FROM Usuario u WHERE u.activo = :activo"),
    @NamedQuery(name = "Usuario.findByRut", query = "SELECT u FROM Usuario u WHERE u.rut = :rut")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "id_usuario")
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Nombre de Usuario", fieldIdFull = "idUsuario", fieldTypeFull = String.class)
    private String idUsuario;
    @Size(max = 100)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Nombres", fieldIdFull = "nombres", fieldTypeFull = String.class)
    private String nombres;
    @Size(max = 100)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Apellidos", fieldIdFull = "apellidos", fieldTypeFull = String.class)
    private String apellidos;
    @Pattern(regexp = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 100)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Email", fieldIdFull = "email", fieldTypeFull = String.class)
    private String email;
    @Size(max = 40)
    @Column(name = "tel_fijo")
    private String telFijo;
    @Size(max = 40)
    @Column(name = "tel_movil")
    private String telMovil;
    @Basic(optional = false)
    @NotNull
    @FilterField(fieldTypeId = EnumFieldType.CHECKBOX, label = "Activo", fieldIdFull = "activo", fieldTypeFull = Boolean.class)
    private boolean activo;
    @Basic(optional = false)
    @NotNull
    private boolean editable;
    @Size(max = 32)
    private String pass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 0, max = 14)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Rut", fieldIdFull = "rut", fieldTypeFull = String.class)
    private String rut;
//    @ManyToMany(mappedBy = "usuarioList")
    @ManyToMany
    @JoinTable(name = "USER_ROL", joinColumns = {
        @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")}, inverseJoinColumns = {
        @JoinColumn(name = "id_rol", referencedColumnName = "id_rol")})
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Rol(es)", fieldIdFull = "rolList", fieldTypeFull = List.class, listGenericTypeFieldId = "idRol")
    private List<Rol> rolList;
    @OneToMany(mappedBy = "owner")
    private List<Caso> casoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "createdBy")
    private List<Documento> documentoList;
    @OneToMany(mappedBy = "supervisor")
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Supervisor de", fieldIdFull = "usuarioList", fieldTypeFull = List.class, listGenericTypeFieldId = "idUsuario")
    private List<Usuario> usuarioList;
    @JoinColumn(name = "supervisor", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario supervisor;
//    @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")
//    @ManyToOne
//    @NotNull
//    private Grupo idGrupo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creadaPor")
    private List<Nota> notaList;
    @ManyToMany(mappedBy = "usuarioList", cascade = CascadeType.ALL)
//    @JoinTable(name = "USUARIO_GRUPO", joinColumns = {
//        @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")}, inverseJoinColumns = {
//        @JoinColumn(name = "id_grupo", referencedColumnName = "id_grupo")})
//    @ManyToMany
    @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Grupo(s)", fieldIdFull = "grupoList", fieldTypeFull = List.class, listGenericTypeFieldId = "idGrupo")
    private List<Grupo> grupoList;
    @Column(name = "theme")
    @Size(max = 40)
    private String theme;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioCreadaPor")
    private List<Vista> vistaList;
    @Column(name = "page_layout_state")
    @Size(max = 2147483647)
    private String pageLayoutState;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioCreadaPor")
    private List<Clipping> clippingList;

    @Transient
    private String randomColor;

    //notifications
    @Column(name = "notify_when_ticket_assigned")
    private boolean notifyWhenTicketAssigned;
    @Column(name = "notify_when_new_ticket_in_group")
    private boolean notifyWhenNewTicketInGroup;
    @Column(name = "notify_when_ticket_alert")
    private boolean notifyWhenTicketAlert;
    @Column(name = "notify_when_ticket_is_updated")
    private boolean notifyWhenTicketIsUpdated;
    //---
    @Column(name = "email_notifications_enabled")
    private boolean emailNotificationsEnabled;
    @Column(name = "desktop_notifications_enabled")
    private boolean desktopNotificationsEnabled;

    @Column(name = "template_theme")
    @Size(max = 64)
    private String templateTheme;
    @Column(name = "main_menu_changepos")
    private boolean mainMenuChangePos;

    @Column(name = "tenant_id")
    private String tenantId;

    /**
     * ALTER TABLE usuario ADD COLUMN prefer_firma_enabled boolean; ALTER TABLE
     * usuario ADD COLUMN firma text;
     */
//    @Column(name = "prefer_firma_enabled")
//    private boolean firmaEnabled;
    @Column(name = "firma")
    @Size(max = 2147483647)
    private String firma;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuario")
    @CascadeOnDelete
    private List<UsuarioSessionLog> usuarioSessionLogList;

    public Usuario() {
    }

    public void addUsuarioSessionLog(UsuarioSessionLog sessionLog) {
        if (usuarioSessionLogList == null) {
            usuarioSessionLogList = new LinkedList<>();
        }
        usuarioSessionLogList.add(sessionLog);
    }

    public Usuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(String idUsuario, String nombres, String apellidos) {
        this.idUsuario = idUsuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Usuario(String idUsuario, boolean activo, String pass, String rut, String email, boolean editable, String nombres, String apellidos) {
        this.idUsuario = idUsuario;
        this.activo = activo;
        this.pass = pass;
        this.rut = rut;
        this.email = email;
        this.editable = editable;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public Usuario(String idUsuario, boolean activo, String rut) {
        this.idUsuario = idUsuario;
        this.activo = activo;
        this.rut = rut;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelFijo() {
        return telFijo;
    }

    public void setTelFijo(String telFijo) {
        this.telFijo = telFijo;
    }

    public String getTelMovil() {
        return telMovil;
    }

    public void setTelMovil(String telMovil) {
        this.telMovil = telMovil;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    @XmlTransient
    public List<Rol> getRolList() {
        return rolList;
    }

    public void setRolList(List<Rol> rolList) {
        this.rolList = rolList;
    }

    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    @XmlTransient
    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
    }

    @XmlTransient
    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public Usuario getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Usuario supervisor) {
        this.supervisor = supervisor;
    }

    @XmlTransient
    public List<Nota> getNotaList() {
        return notaList;
    }

    public void setNotaList(List<Nota> notaList) {
        this.notaList = notaList;
    }

    @XmlTransient
    public List<Vista> getVistaList() {
        return vistaList;
    }

    public void setVistaList(List<Vista> vistaList) {
        this.vistaList = vistaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    public String getCapitalName() {
        String capitalName = "";
        try {
            String[] names = new String[]{nombres == null ? "" : nombres.split(" ")[0], apellidos == null ? "" : apellidos.split(" ")[0]};
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
        } catch (Exception e) {
            capitalName = nombres + " " + apellidos;
        }
        return capitalName;
    }

    @Override
    public String toString() {
        //return "cl.cnsv.referidos.persistence.entities.Usuario[ idUsuario=" + idUsuario + " ]";
        if (((nombres == null) || (nombres.isEmpty())) && ((apellidos == null) || (apellidos.isEmpty()))) {
            return idUsuario;
        }
        return nombres + " " + apellidos + " (" + idUsuario + ")";
    }

    /**
     * @return the editable
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * @param editable the editable to set
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
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
     * @return the grupoList
     */
    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    /**
     * @param grupoList the grupoList to set
     */
    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }

    /**
     * @return the pageLayoutState
     */
    public String getPageLayoutState() {
        return pageLayoutState;
    }

    /**
     * @param pageLayoutState the pageLayoutState to set
     */
    public void setPageLayoutState(String pageLayoutState) {
        this.pageLayoutState = pageLayoutState;
//        System.out.println("setPageLayoutState:" + pageLayoutState);
    }

    /**
     * @return the clippingList
     */
    public List<Clipping> getClippingList() {
        return clippingList;
    }

    /**
     * @param clippingList the clippingList to set
     */
    public void setClippingList(List<Clipping> clippingList) {
        this.clippingList = clippingList;
    }

    /**
     * @return the randomColor
     */
    public String getRandomColor() {
        return randomColor;
    }

    /**
     * @param randomColor the randomColor to set
     */
    public void setRandomColor(String randomColor) {
        this.randomColor = randomColor;
    }

    /**
     * @return the notifyWhenTicketAssigned
     */
    public boolean getNotifyWhenTicketAssigned() {
        return notifyWhenTicketAssigned;
    }

    /**
     * @param notifyWhenTicketAssigned the notifyWhenTicketAssigned to set
     */
    public void setNotifyWhenTicketAssigned(boolean notifyWhenTicketAssigned) {
        this.notifyWhenTicketAssigned = notifyWhenTicketAssigned;
    }

    /**
     * @return the notifyWhenNewTicketInGroup
     */
    public boolean getNotifyWhenNewTicketInGroup() {
        return notifyWhenNewTicketInGroup;
    }

    /**
     * @param notifyWhenNewTicketInGroup the notifyWhenNewTicketInGroup to set
     */
    public void setNotifyWhenNewTicketInGroup(boolean notifyWhenNewTicketInGroup) {
        this.notifyWhenNewTicketInGroup = notifyWhenNewTicketInGroup;
    }

    /**
     * @return the notifyWhenTicketAlert
     */
    public boolean getNotifyWhenTicketAlert() {
        return notifyWhenTicketAlert;
    }

    /**
     * @param notifyWhenTicketAlert the notifyWhenTicketAlert to set
     */
    public void setNotifyWhenTicketAlert(boolean notifyWhenTicketAlert) {
        this.notifyWhenTicketAlert = notifyWhenTicketAlert;
    }

    /**
     * @return the notifyWhenTicketIsUpdated
     */
    public boolean getNotifyWhenTicketIsUpdated() {
        return notifyWhenTicketIsUpdated;
    }

    /**
     * @param notifyWhenTicketIsUpdated the notifyWhenTicketIsUpdated to set
     */
    public void setNotifyWhenTicketIsUpdated(boolean notifyWhenTicketIsUpdated) {
        this.notifyWhenTicketIsUpdated = notifyWhenTicketIsUpdated;
    }

    /**
     * @return the emailNotificationsEnabled
     */
    public boolean getEmailNotificationsEnabled() {
        return emailNotificationsEnabled;
    }

    /**
     * @param emailNotificationsEnabled the emailNotificationsEnabled to set
     */
    public void setEmailNotificationsEnabled(boolean emailNotificationsEnabled) {
        this.emailNotificationsEnabled = emailNotificationsEnabled;
    }

    /**
     * @return the desktopNotificationsEnabled
     */
    public boolean getDesktopNotificationsEnabled() {
        return desktopNotificationsEnabled;
    }

    /**
     * @param desktopNotificationsEnabled the desktopNotificationsEnabled to set
     */
    public void setDesktopNotificationsEnabled(boolean desktopNotificationsEnabled) {
        this.desktopNotificationsEnabled = desktopNotificationsEnabled;
    }

//    /**
//     * @return the firmaEnabled
//     */
//    public boolean isFirmaEnabled() {
//        return firmaEnabled;
//    }
//
//    /**
//     * @param firmaEnabled the firmaEnabled to set
//     */
//    public void setFirmaEnabled(boolean firmaEnabled) {
//        this.firmaEnabled = firmaEnabled;
//    }
    /**
     * @return the firma
     */
    public String getFirma() {
        return firma;
    }

    /**
     * @param firma the firma to set
     */
    public void setFirma(String firma) {
        this.firma = firma;
    }

    /**
     * @return the usuarioSessionLogList
     */
    public List<UsuarioSessionLog> getUsuarioSessionLogList() {
        return usuarioSessionLogList;
    }

    /**
     * @param usuarioSessionLogList the usuarioSessionLogList to set
     */
    public void setUsuarioSessionLogList(List<UsuarioSessionLog> usuarioSessionLogList) {
        this.usuarioSessionLogList = usuarioSessionLogList;
    }

    /**
     * @return the templateTheme
     */
    public String getTemplateTheme() {
        return templateTheme;
    }

    /**
     * @param templateTheme the templateTheme to set
     */
    public void setTemplateTheme(String templateTheme) {
        this.templateTheme = templateTheme;
    }

    /**
     * @return the mainMenuChangePos
     */
    public boolean isMainMenuChangePos() {
        return mainMenuChangePos;
    }

    /**
     * @param mainMenuChangePos the mainMenuChangePos to set
     */
    public void setMainMenuChangePos(boolean mainMenuChangePos) {
        this.mainMenuChangePos = mainMenuChangePos;
    }

    /**
     * @return the tenantId
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * @param tenantId the tenantId to set
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
