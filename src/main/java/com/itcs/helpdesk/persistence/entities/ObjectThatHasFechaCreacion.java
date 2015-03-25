/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entities;

import java.util.Date;

/**
 *
 * @author jonathan
 */
public interface ObjectThatHasFechaCreacion {

    public Date getFechaCreacion();

    /**
     * @param fechaCreacion the fechaCreacion to set
     */
    public void setFechaCreacion(Date fechaCreacion);

}
