/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "usuario_session_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioSessionLog.findAll", query = "SELECT u FROM UsuarioSessionLog u"),
    @NamedQuery(name = "UsuarioSessionLog.findByIp", query = "SELECT u FROM UsuarioSessionLog u WHERE u.ip = :ip"),
    @NamedQuery(name = "UsuarioSessionLog.findByBrowser", query = "SELECT u FROM UsuarioSessionLog u WHERE u.browser = :browser"),
    @NamedQuery(name = "UsuarioSessionLog.findByPlatform", query = "SELECT u FROM UsuarioSessionLog u WHERE u.platform = :platform"),
    @NamedQuery(name = "UsuarioSessionLog.findByUserAgent", query = "SELECT u FROM UsuarioSessionLog u WHERE u.userAgent = :userAgent"),
    @NamedQuery(name = "UsuarioSessionLog.findByLanguages", query = "SELECT u FROM UsuarioSessionLog u WHERE u.languages = :languages"),
    @NamedQuery(name = "UsuarioSessionLog.findByUserLocation", query = "SELECT u FROM UsuarioSessionLog u WHERE u.userLocation = :userLocation"),
    @NamedQuery(name = "UsuarioSessionLog.findByIdSessionLog", query = "SELECT u FROM UsuarioSessionLog u WHERE u.idSessionLog = :idSessionLog")})
public class UsuarioSessionLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_session_log", nullable = false)
    private Integer idSessionLog;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "ip", nullable = false, length = 64)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "IP", fieldIdFull = "ip", fieldTypeFull = String.class)
    private String ip;
    @Column(name = "timestamp_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampLogin;
    @Column(name = "timestamp_logout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestampLogout;
    @Size(max = 2147483647)
    @Column(name = "browser", length = 2147483647)
    private String browser;
    @Size(max = 2147483647)
    @Column(name = "platform", length = 2147483647)
    private String platform;
    @Size(max = 2147483647)
    @Column(name = "user_agent", length = 2147483647)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "userAgent", fieldIdFull = "userAgent", fieldTypeFull = String.class)
    private String userAgent;
    @Size(max = 2147483647)
    @Column(name = "languages", length = 2147483647)
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "languages", fieldIdFull = "languages", fieldTypeFull = String.class)
    private String languages;
    @Size(max = 2147483647)
    @Column(name = "user_location", length = 2147483647)
    private String userLocation;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario", nullable = false)
    @ManyToOne(optional = false)
     @FilterField(fieldTypeId = EnumFieldType.SELECTONE_ENTITY, label = "Usuario", fieldIdFull = "idUsuario.idUsuario", fieldTypeFull = String.class)
    private Usuario idUsuario;

    public UsuarioSessionLog() {
    }

    public UsuarioSessionLog(Integer idSessionLog) {
        this.idSessionLog = idSessionLog;
    }

    public UsuarioSessionLog(Integer idSessionLog, String ip) {
        this.idSessionLog = idSessionLog;
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTimestampLogin() {
        return timestampLogin;
    }

    public void setTimestampLogin(Date timestampLogin) {
        this.timestampLogin = timestampLogin;
    }

    public Date getTimestampLogout() {
        return timestampLogout;
    }

    public void setTimestampLogout(Date timestampLogout) {
        this.timestampLogout = timestampLogout;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public Integer getIdSessionLog() {
        return idSessionLog;
    }

    public void setIdSessionLog(Integer idSessionLog) {
        this.idSessionLog = idSessionLog;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSessionLog != null ? idSessionLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioSessionLog)) {
            return false;
        }
        UsuarioSessionLog other = (UsuarioSessionLog) object;
        if ((this.idSessionLog == null && other.idSessionLog != null) || (this.idSessionLog != null && !this.idSessionLog.equals(other.idSessionLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsuarioSessionLog[ idSessionLog=" + idSessionLog + " ]";
    }

}
