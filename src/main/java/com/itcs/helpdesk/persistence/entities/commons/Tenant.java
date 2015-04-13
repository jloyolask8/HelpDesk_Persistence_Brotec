package com.itcs.helpdesk.persistence.entities.commons;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery(name = "Tenant.findAll", query = "SELECT t FROM Tenant t"),
    @NamedQuery(name = "Tenant.findById", query = "SELECT t FROM Tenant t WHERE t.tenantUUID = :tenantUUID"),
    @NamedQuery(name = "Tenant.findByDomain", query = "SELECT t FROM Tenant t WHERE t.subDomainName = :subDomainName")})
public class Tenant implements Serializable {

    @Transient
    public static final String STATUS_VALIDATION_EMAIL_SENT = "ValidationEmailSent";
     @Transient
    public static final String STATUS_VALIDATED = "Validated";

    @Id
    private String tenantUUID;
    @Column(unique = true, length = 1024)
    @Basic
    private String apiKey;
    @Basic
    private String passw;
    
    @Basic
    @NotNull
    private String companyName;
    @Basic
    private String userPhone;
    @Basic
    @NotNull
    private String userFullName;
    @Basic
    @NotNull
    @Pattern(regexp = "[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[A-Za-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?\\.)+[A-Za-z0-9](?:[A-Za-z0-9-]*[A-Za-z0-9])?", message = "Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(min = 1, max = 255)
    private String userEmail;
    @Column(unique = true, updatable = false, nullable = false, length = 63)
    @Basic
    @NotNull
    private String schemaName;
    @Column(unique = true, nullable = false, length = 63)
    @Basic
    @NotNull
    /**
     * Above pattern makes sure domain name matches the following criteria :
     *
     * The domain name should be a-z | A-Z | 0-9 and hyphen(-) The domain name
     * should between 1 and 63 characters long Last Tld must be at least two
     * characters, and a maximum of 6 characters The domain name should not
     * start or end with hyphen (-) (e.g. -google.com or google-.com) The domain
     * name can be a subdomain (e.g. mkyong.blogspot.com)
     */
    @Size(min = 1, max = 63)
    private String subDomainName;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date creationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date expirationDate;

    @Basic
    private String plan;

    @Basic
    private String status;

    @PrePersist
    public void prePersist() {
        this.creationDate = new Date();
//        this.expirationDate = new Date();
    }

    public Tenant() {

    }

    public String getTenantUUID() {
        return this.tenantUUID;
    }

    public void setTenantUUID(String tenantUUID) {
        this.tenantUUID = tenantUUID;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserFullName() {
        return this.userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getSubDomainName() {
        return this.subDomainName;
    }

    public void setSubDomainName(String subDomainName) {
        this.subDomainName = subDomainName;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the passw
     */
    public String getPassw() {
        return passw;
    }

    /**
     * @param passw the passw to set
     */
    public void setPassw(String passw) {
        this.passw = passw;
    }
}
