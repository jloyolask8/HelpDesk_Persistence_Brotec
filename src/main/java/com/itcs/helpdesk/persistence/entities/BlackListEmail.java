/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.utils.FilterField;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "black_list_email")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BlackListEmail.findAll", query = "SELECT b FROM BlackListEmail b"),
    @NamedQuery(name = "BlackListEmail.findByEmailAddress", query = "SELECT b FROM BlackListEmail b WHERE b.emailAddress = :emailAddress"),
    @NamedQuery(name = "BlackListEmail.findByDescription", query = "SELECT b FROM BlackListEmail b WHERE b.description = :description")})
public class BlackListEmail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "email_address")
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "dirección de email", fieldIdFull = "emailAddress", fieldTypeFull = String.class)
    private String emailAddress;
    @Size(max = 2147483647)
    @Column(name = "description")
    @FilterField(fieldTypeId = EnumFieldType.TEXT, label = "Descripción", fieldIdFull = "description", fieldTypeFull = String.class)
    private String description;

    public BlackListEmail() {
    }

    public BlackListEmail(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emailAddress != null ? emailAddress.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BlackListEmail)) {
            return false;
        }
        BlackListEmail other = (BlackListEmail) object;
        if ((this.emailAddress == null && other.emailAddress != null) || (this.emailAddress != null && !this.emailAddress.equals(other.emailAddress))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BlackListEmail[ emailAddress=" + emailAddress + " ]";
    }
}
