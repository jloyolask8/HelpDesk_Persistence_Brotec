/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.entityenums;

import com.itcs.helpdesk.persistence.entities.Rol;

/**
 *
 * @author jorge
 */
public enum EnumRoles 
{
    ADMINISTRADOR(new Rol("Admin", "Administrador", null, EnumFunciones.getAll(), null, false)),
    AGENT(new Rol("Agent", "Agent", null, EnumFunciones.getAgentDefaultFunctions(), null, true));
    
    private Rol rol;
    
    EnumRoles(Rol rol)
    {
        this.rol = rol;
    }
    
    public Rol getRol()
    {
        return this.rol;
    }
}
