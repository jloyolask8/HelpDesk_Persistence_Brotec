/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itcs.helpdesk.persistence.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author jonathan
 */
@Embeddable
public class ScheduleEventClientPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "event_id")
    private int eventId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_cliente")
    private int idCliente;

    public ScheduleEventClientPK() {
    }

    public ScheduleEventClientPK(int eventId, int idCliente) {
        this.eventId = eventId;
        this.idCliente = idCliente;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) eventId;
        hash += (int) idCliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ScheduleEventClientPK)) {
            return false;
        }
        ScheduleEventClientPK other = (ScheduleEventClientPK) object;
        if (this.eventId != other.eventId) {
            return false;
        }
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ScheduleEventClient PK[ eventId=" + eventId + ", idCliente=" + idCliente + " ]";
    }
    
}
