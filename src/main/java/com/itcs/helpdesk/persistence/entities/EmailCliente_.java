package com.itcs.helpdesk.persistence.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(EmailCliente.class)
public class EmailCliente_ { 

    public static volatile SingularAttribute<EmailCliente, String> emailCliente;
    public static volatile SingularAttribute<EmailCliente, String> tipoEmail;
    public static volatile SingularAttribute<EmailCliente, Cliente> cliente;
    public static volatile ListAttribute<EmailCliente, Caso> casoList;

}