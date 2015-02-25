/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itcs.helpdesk.persistence.jpa.service;

import com.itcs.helpdesk.persistence.entities.AppSetting;
import com.itcs.helpdesk.persistence.entities.Canal;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.FieldType;
import com.itcs.helpdesk.persistence.entities.Funcion;
import com.itcs.helpdesk.persistence.entities.Prioridad;
import com.itcs.helpdesk.persistence.entities.Responsable;
import com.itcs.helpdesk.persistence.entities.Rol;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import com.itcs.helpdesk.persistence.entities.TipoAccion;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.entities.TipoCanal;
import com.itcs.helpdesk.persistence.entities.TipoCaso;
import com.itcs.helpdesk.persistence.entities.TipoComparacion;
import com.itcs.helpdesk.persistence.entities.TipoNota;
import com.itcs.helpdesk.persistence.entities.Usuario;
import com.itcs.helpdesk.persistence.entityenums.EnumCanal;
import com.itcs.helpdesk.persistence.entityenums.EnumEstadoCaso;
import com.itcs.helpdesk.persistence.entityenums.EnumFieldType;
import com.itcs.helpdesk.persistence.entityenums.EnumFunciones;
import com.itcs.helpdesk.persistence.entityenums.EnumPrioridad;
import com.itcs.helpdesk.persistence.entityenums.EnumResponsables;
import com.itcs.helpdesk.persistence.entityenums.EnumRoles;
import com.itcs.helpdesk.persistence.entityenums.EnumSettingsBase;
import com.itcs.helpdesk.persistence.entityenums.EnumSubEstadoCaso;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoAccion;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoAlerta;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoCanal;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoCaso;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoComparacion;
import com.itcs.helpdesk.persistence.entityenums.EnumTipoNota;
import com.itcs.helpdesk.persistence.entityenums.EnumUsuariosBase;
import com.itcs.helpdesk.persistence.jpa.exceptions.PreexistingEntityException;
import com.itcs.helpdesk.persistence.jpa.exceptions.RollbackFailureException;
import com.itcs.helpdesk.persistence.utils.vo.RegistrationVO;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;

/**
 *
 * @author jonathan
 */
public class TenantDataPopulator {
    
    private final JPAServiceFacade jpaController;
    

    public TenantDataPopulator(JPAServiceFacade jpaController) {
        this.jpaController = jpaController;
    }
    
    private JPAServiceFacade getJpaController() {
        return this.jpaController;
    }
    
    /**
     * Genera codigo MD5 de un texto dado
     *
     * @param texto Texto a sacar el MD5
     * @return Retorno del texto MD5 del String dado.
     */
    synchronized public static String getMD5(String texto) {
        String output = "";
        try {

            byte[] textBytes = texto.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(textBytes);
            byte[] codigo = md.digest();

            for (int i = 0; i < codigo.length; i++) {
                output += Integer.toHexString((codigo[i] >> 4) & 0xf);
                output += Integer.toHexString(codigo[i] & 0xf);
            }

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TenantDataPopulator.class.getName()).log(Level.SEVERE, "getMD5", ex);
        }
        return output.trim();

    }
    
    public Usuario insertAdminUser(String v, RegistrationVO registrationVO) throws Exception{
        try {
            Usuario usuarioSistema = new Usuario(registrationVO.getUsername(), true, getMD5(registrationVO.getPasswordConfirm()), "", registrationVO.getEmail(), true, registrationVO.getFullName(), "");
        
            List<Rol> roles = new LinkedList<>();
            List<Usuario> usuarios = new LinkedList<>();
            final Rol rol = EnumRoles.ADMINISTRADOR.getRol();
            roles.add(rol);
            usuarioSistema.setRolList(roles);
            rol.setUsuarioList(usuarios);
            
            usuarioSistema.setTenantId(registrationVO.getCompanyName());
            usuarioSistema.setTelFijo(registrationVO.getPhoneNumber());
            
            usuarioSistema.setVerificationCode(v);
            
            this.jpaController.persist(usuarioSistema);
            
            return usuarioSistema;
            
        } catch (NoResultException ex) {
            try {
                Logger.getLogger(this.getClass().getName()).severe("No existe usuario SISTEMA, se creara ahora");
                
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
        return null;
    }
    
    public void populateBaseData() {
        System.out.println("populateBaseData()...");
        
//        verificarUsuarios();
//        verificarFunciones();
//        verificarRoles();
//        verificarTipoCaso();
//
//        verificarEstadosCaso();
        verificarSubEstadosCaso();
//
//        verificarTipoCanal();
//        verificarTiposAlerta();
        verificarTiposNota();
//        verificarTipoComparacion();
//
//        verificarPrioridades();
//
//        verificarFieldTypes();
//
//        verificarResponsables();
//        verificarNombreAcciones();
//        verificarSettingsBase();
//
//        verificarCanales();
//        fixClientesCasos();
//        fixNombreCliente();
        
    }
    
//     private void verificarUsuarios() {
//        try {
//            Usuario usuarioSistema = this.jpaController.find(Usuario.class, EnumUsuariosBase.SISTEMA.getUsuario().getIdUsuario());
//            //getUsuarioFindByIdUsuario(EnumUsuariosBase.SISTEMA.getUsuario().getIdUsuario());
//            if (null == usuarioSistema) {
//                throw new NoResultException("No existe usuario SISTEMA");
//            }
//        } catch (NoResultException ex) {
//            try {
//                Logger.getLogger(this.getClass().getName()).severe("No existe usuario SISTEMA, se creara ahora");
//                this.jpaController.persist(EnumUsuariosBase.SISTEMA.getUsuario());
//            } catch (Exception e) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//            }
//        }
//    }

//    private void fixClientesCasos() {
//        EasyCriteriaQuery<Caso> ecq = new EasyCriteriaQuery<>(getJpaController(), Caso.class);
//        ecq.addEqualPredicate("idCliente", null);
//        ecq.addDistinctPredicate("emailCliente", null);
//        List<Caso> casosToFix = ecq.getAllResultList();
//        
//        
//
//        for (Caso caso : casosToFix) {
//            caso.setIdCliente(caso.getEmailCliente().getCliente());
//            try {
//                this.jpaController.merge(caso);
//            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    private void fixNombreCliente() {
//        EasyCriteriaQuery<Cliente> ecq = new EasyCriteriaQuery<>(getJpaController(), Cliente.class);
//        ecq.addLikePredicate(Cliente_.nombres.getName(), "%=?ISO-8859-1%");
//        List<Cliente> clientsToFix = ecq.getAllResultList();
//
//        for (Cliente cliente : clientsToFix) {
//            try {
//                String from = MimeUtility.decodeText(cliente.getNombres().replace("\"", ""));
//                String[] nombres = from.split(" ");
//                if (nombres.length > 0) {
//                    cliente.setNombres(nombres[0]);
//                }
//                if (nombres.length > 2) {
//                    cliente.setApellidos(nombres[1] + " " + nombres[2]);
//                } else if (nombres.length > 1) {
//                    cliente.setApellidos(nombres[1]);
//                }
//                try {
//                    this.jpaController.merge(cliente);
//                } catch (Exception ex) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                }
//            } catch (UnsupportedEncodingException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

//    private void printOutContraintViolation(ConstraintViolationException ex, String classname) {
//        Set<ConstraintViolation<?>> set = (ex).getConstraintViolations();
//        for (ConstraintViolation<?> constraintViolation : set) {
//            Log.createLogger(classname).logInfo("leafBean class: " + constraintViolation.getLeafBean().getClass());
//            Log.createLogger(classname).logInfo("anotacion: " + constraintViolation.getConstraintDescriptor().getAnnotation().toString() + " value:" + constraintViolation.getInvalidValue());
//        }
//    }
//
//    public void exceptionThreatment(Exception ex, String classname) {
//        if (ex instanceof ConstraintViolationException) {
//            printOutContraintViolation((ConstraintViolationException) ex, classname);
//        }
//        if (ex.getCause() instanceof ConstraintViolationException) {
//            printOutContraintViolation((ConstraintViolationException) (ex.getCause()), classname);
//        }
//        Log.createLogger(classname).log(Level.SEVERE, "exceptionThreatment", ex);
//    }
//    private void verificarSettingsBase() {
//        for (EnumSettingsBase enumSettingsBase : EnumSettingsBase.values()) {
//            try {
//                if (null == this.jpaController.find(AppSetting.class, enumSettingsBase.getAppSetting().getSettingKey())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existen settings {0}, se creara ahora", enumSettingsBase.getAppSetting().getSettingKey());
//                try {
//                    this.jpaController.persist(enumSettingsBase.getAppSetting());
//                } catch (PreexistingEntityException pre) {
//                    //ignore if already exists
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

//    private void verificarTipoCanal() {
//        for (EnumTipoCanal enumTipoCanal : EnumTipoCanal.values()) {
//            try {
//                TipoCanal tipoCanal = this.jpaController.find(TipoCanal.class, enumTipoCanal.getTipoCanal().getIdTipo());
//                if (null == tipoCanal) {
//                    throw new NoResultException();
//                }
//            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe tipo canal {0}, se creara ahora", enumTipoCanal.getTipoCanal().getIdTipo());
//                try {
//                    this.jpaController.persist(enumTipoCanal.getTipoCanal());
//                } catch (PreexistingEntityException pre) {
//                    //ignore if already exists
//                } catch (Exception e) {
//                   Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

   

//    private void verificarTiposAlerta() {
//        for (EnumTipoAlerta tipoAlerta : EnumTipoAlerta.values()) {
//            try {
//                if (null == this.jpaController.find(TipoAlerta.class, tipoAlerta.getTipoAlerta().getIdalerta())) {
//                    throw new NoResultException();
//                }
//
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe tipo alerta {0}, se creara ahora", tipoAlerta.getTipoAlerta().getNombre());
//                try {
//                    this.jpaController.persist(tipoAlerta.getTipoAlerta());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

//    private void verificarFunciones() {
//        for (EnumFunciones funcion : EnumFunciones.values()) {
//
//            if (null == this.jpaController.find(Funcion.class, funcion.getFuncion().getIdFuncion())) {
//                try {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe funcion {0}, se creara ahora", funcion.getFuncion().getNombre());
//                    this.jpaController.persist(funcion.getFuncion());
//                } catch (PreexistingEntityException ex) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                } catch (RollbackFailureException ex) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                } catch (Exception ex) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }
//    }

//    private void verificarRoles() {
//        for (EnumRoles enumRol : EnumRoles.values()) {
//            try {
//                if (null == this.jpaController.find(Rol.class, enumRol.getRol().getIdRol())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe rol {0}, se creara ahora", enumRol.getRol().getNombre());
//                try {
//                    this.jpaController.persist(enumRol.getRol());
//                } catch (Exception e) {
//                    e.getCause().printStackTrace();
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

//    private void verificarResponsables() {
//        for (EnumResponsables enumResponsables : EnumResponsables.values()) {
//            try {
//                if (null == this.jpaController.find(Responsable.class, enumResponsables.getResponsable().getIdResponsable())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe el responsable {0}, se creara ahora", enumResponsables.getResponsable().getNombreResponsable());
//                try {
//                    this.jpaController.persist(enumResponsables.getResponsable());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "verificarResponsables", e);
//                }
//            }
//        }
//    }

//    private void verificarEstadosCaso() {
//        for (EnumEstadoCaso enumEstado : EnumEstadoCaso.values()) {
//            try {
//                if (null == this.jpaController.find(EstadoCaso.class, enumEstado.getEstado().getIdEstado())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe estado {0}, se creara ahora", enumEstado.getEstado().getNombre());
//                try {
//                    this.jpaController.persist(enumEstado.getEstado());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

    private void verificarSubEstadosCaso() {
        for (EnumSubEstadoCaso enumSubEstado : EnumSubEstadoCaso.values()) {
            try {
                if (null == this.jpaController.find(SubEstadoCaso.class, enumSubEstado.getSubEstado().getIdSubEstado())) {
                    throw new NoResultException();
                }
            } catch (NoResultException ex) {
                Logger.getLogger(this.getClass().getName()).severe("No existe sub estado !!!, se creara ahora");
                try {
                    this.jpaController.persist(enumSubEstado.getSubEstado());
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }

//    private void verificarTipoCaso() {
//        for (EnumTipoCaso enumTipoCaso : EnumTipoCaso.values()) {
//            try {
//                if (null == this.jpaController.find(TipoCaso.class, enumTipoCaso.getTipoCaso().getIdTipoCaso())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe el tipo caso {0}, se creara ahora", enumTipoCaso.getTipoCaso().getNombre());
//                try {
//                    this.jpaController.persist(enumTipoCaso.getTipoCaso());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "verificarTipoCaso", e);
//                }
//            }
//        }
//    }

//    private void verificarCanales() {
//        for (EnumCanal enumCanal : EnumCanal.values()) {
//            try {
//                if (null == this.jpaController.find(Canal.class, enumCanal.getCanal().getIdCanal())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe canal {0}, se creara ahora", enumCanal.getCanal().getNombre());
//                try {
//                    this.jpaController.persist(enumCanal.getCanal());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

//    private void verificarPrioridades() {
//        for (EnumPrioridad enumPrioridad : EnumPrioridad.values()) {
//            try {
//                Prioridad prioridad = this.jpaController.find(Prioridad.class, enumPrioridad.getPrioridad().getIdPrioridad());
//                if (prioridad == null) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe prioridad {0}, se creara ahora", enumPrioridad.getPrioridad().getNombre());
//                try {
//                    this.jpaController.persist(enumPrioridad.getPrioridad());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

    private void verificarTiposNota() {
        for (EnumTipoNota enumTipoNota : EnumTipoNota.values()) {
            try {
                if (null == this.jpaController.find(TipoNota.class, enumTipoNota.getTipoNota().getIdTipoNota())) {
                    throw new NoResultException();
                } else {
                    this.jpaController.merge(enumTipoNota.getTipoNota());
                }
            } catch (NoResultException ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe tipo nota {0}, se creara ahora", enumTipoNota.getTipoNota().getNombre());
                try {
                    this.jpaController.persist(enumTipoNota.getTipoNota());
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

//    private void verificarNombreAcciones() {
//        for (EnumTipoAccion enumNombreAccion : EnumTipoAccion.values()) {
//            try {
//                if (null == this.jpaController.find(TipoAccion.class, enumNombreAccion.getNombreAccion().getIdNombreAccion())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe nombre de accion {0}, se creara ahora", enumNombreAccion.getNombreAccion().getNombre());
//                try {
//                    this.jpaController.persist(enumNombreAccion.getNombreAccion());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

//    private void verificarFieldTypes() {
//        for (EnumFieldType fieldType : EnumFieldType.values()) {
//            try {
//                if (null == this.jpaController.find(FieldType.class, fieldType.getFieldType().getFieldTypeId())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe nombre de fieldType {0}, se creara ahora!!", fieldType.getFieldType().getFieldTypeId());
//                try {
//                    this.jpaController.persist(fieldType.getFieldType());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

//    private void verificarTipoComparacion() {
//        for (EnumTipoComparacion enumTipoComparacion : EnumTipoComparacion.values()) {
//            try {
//                if (null == this.jpaController.find(TipoComparacion.class, enumTipoComparacion.getTipoComparacion().getIdComparador())) {
//                    throw new NoResultException();
//                }
//            } catch (NoResultException ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "No existe nombre de tipo comparacion {0}, se creara ahora", enumTipoComparacion.getTipoComparacion().getIdComparador());
//                try {
//                    this.jpaController.persist(enumTipoComparacion.getTipoComparacion());
//                } catch (Exception e) {
//                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                }
//            }
//        }
//    }

   

  

    
    
}
