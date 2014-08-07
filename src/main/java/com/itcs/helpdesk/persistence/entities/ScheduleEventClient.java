/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jonathan
 */
@Entity
@Table(name = "schedule_event_client")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ScheduleEventClient.findAll", query = "SELECT s FROM ScheduleEventClient s"),
    @NamedQuery(name = "ScheduleEventClient.findByEventId", query = "SELECT s FROM ScheduleEventClient s WHERE s.scheduleEventClientPK.eventId = :eventId"),
    @NamedQuery(name = "ScheduleEventClient.findByIdCliente", query = "SELECT s FROM ScheduleEventClient s WHERE s.scheduleEventClientPK.idCliente = :idCliente"),
    @NamedQuery(name = "ScheduleEventClient.findByInvitationSent", query = "SELECT s FROM ScheduleEventClient s WHERE s.invitationSent = :invitationSent"),
    @NamedQuery(name = "ScheduleEventClient.findByWillAssist", query = "SELECT s FROM ScheduleEventClient s WHERE s.willAssist = :willAssist")})
public class ScheduleEventClient implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ScheduleEventClientPK scheduleEventClientPK;
    @Column(name = "invitation_sent")
    private Boolean invitationSent = Boolean.FALSE;
    @Size(max = 2147483647)
    @Column(name = "invitation_sent_to_address")
    private String invitationSentToAddress;
    @Column(name = "will_assist")
    private Boolean willAssist = null;
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ScheduleEvent scheduleEvent;
    @JoinColumn(name = "id_cliente", referencedColumnName = "id_cliente", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cliente cliente;

    public ScheduleEventClient() {
    }

    public ScheduleEventClient(ScheduleEventClientPK scheduleEventClientPK) {
        this.scheduleEventClientPK = scheduleEventClientPK;
    }

    public ScheduleEventClient(int eventId, int idCliente) {
        this.scheduleEventClientPK = new ScheduleEventClientPK(eventId, idCliente);
    }

    public ScheduleEventClientPK getScheduleEventClientPK() {
        return scheduleEventClientPK;
    }

    public void setScheduleEventClientPK(ScheduleEventClientPK scheduleEventClientPK) {
        this.scheduleEventClientPK = scheduleEventClientPK;
    }

    public Boolean getInvitationSent() {
        return invitationSent;
    }

    public void setInvitationSent(Boolean invitationSent) {
        this.invitationSent = invitationSent;
    }

    public String getInvitationSentToAddress() {
        return invitationSentToAddress;
    }

    public void setInvitationSentToAddress(String invitationSentToAddress) {
        this.invitationSentToAddress = invitationSentToAddress;
    }

    public Boolean getWillAssist() {
        return willAssist;
    }

    public void setWillAssist(Boolean willAssist) {
        this.willAssist = willAssist;
    }

    public ScheduleEvent getScheduleEvent() {
        return scheduleEvent;
    }

    public void setScheduleEvent(ScheduleEvent scheduleEvent) {
        this.scheduleEvent = scheduleEvent;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleEventClientPK != null ? scheduleEventClientPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduleEventClient)) {
            return false;
        }
        ScheduleEventClient other = (ScheduleEventClient) object;
        if ((this.scheduleEventClientPK == null && other.scheduleEventClientPK != null) || (this.scheduleEventClientPK != null && !this.scheduleEventClientPK.equals(other.scheduleEventClientPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itcs.helpdesk.persistence.entities.ScheduleEventClient[ scheduleEventClientPK=" + scheduleEventClientPK + " ]";
    }
    
}
