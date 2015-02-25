/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa.service;

/**
 *
 * @author jonathan
 */
class TenantSetupFailedException extends Exception {

    public TenantSetupFailedException(String message) {
        super(message);
    }

    public TenantSetupFailedException(String message, Throwable cause) {
        super(message, cause);
    }
    
    
    
}
