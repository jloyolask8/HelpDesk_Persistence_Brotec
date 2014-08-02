/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.Responsable;

/**
 *
 * @author jonathan
 */
public enum EnumResponsables {
    
      INMOBILIARIA(new Responsable(1, "Inmobiliaria")),
      CONSTRUCTORA(new Responsable(2, "Constructora"));
    
    private final Responsable responsable;
    
    EnumResponsables(Responsable responsable)
    {
        this.responsable = responsable;
    }
    
    public Responsable getResponsable()
    {
        return this.responsable;
    }
    
}
