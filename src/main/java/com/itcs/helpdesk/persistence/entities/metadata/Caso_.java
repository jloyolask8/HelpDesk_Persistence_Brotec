package com.itcs.helpdesk.persistence.entities.metadata;

import com.itcs.helpdesk.persistence.entities.Attachment;
import com.itcs.helpdesk.persistence.entities.Canal;
import com.itcs.helpdesk.persistence.entities.Caso;
import com.itcs.helpdesk.persistence.entities.Componente;
import com.itcs.helpdesk.persistence.entities.Documento;
import com.itcs.helpdesk.persistence.entities.EmailCliente;
import com.itcs.helpdesk.persistence.entities.EstadoCaso;
import com.itcs.helpdesk.persistence.entities.Etiqueta;
import com.itcs.helpdesk.persistence.entities.Nota;
import com.itcs.helpdesk.persistence.entities.Prioridad;
import com.itcs.helpdesk.persistence.entities.Producto;
import com.itcs.helpdesk.persistence.entities.SubComponente;
import com.itcs.helpdesk.persistence.entities.SubEstadoCaso;
import com.itcs.helpdesk.persistence.entities.TipoAlerta;
import com.itcs.helpdesk.persistence.entities.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "EclipseLink-2.3.2.v20111125-r10461", date = "2013-04-26T17:38:15")
@StaticMetamodel(Caso.class)
public class Caso_ {

    public static final String ESTADO_FIELD_NAME = "idEstado";
    public static final String SUBESTADO_FIELD_NAME = "idSubEstado";
    public static final String OWNER_FIELD_NAME = "owner";
    public static final String REVISAR_ACTUALIZACION_FIELD_NAME = "revisarActualizacion";
    public static final String ESTADO_ALERTA_FIELD_NAME = "estadoAlerta";
    public static final String CATEGORIA_FIELD_NAME = "idCategoria";
    public static final String PRODUCTO_FIELD_NAME = "idProducto";
    public static final String AREA_FIELD_NAME = "idArea";
    public static final String GRUPO_FIELD_NAME = "idGrupo";
    public static final String FECHA_CREACION_FIELD_NAME = "fechaCreacion";
    public static final String ES_PRIORITARIO_FIELD_NAME = "esPrioritario";
    public static volatile SingularAttribute<Caso, Date> fechaModif;
    public static volatile ListAttribute<Caso, Caso> casosHijosList;
    public static volatile SingularAttribute<Caso, Prioridad> idPrioridad;
    public static volatile SingularAttribute<Caso, EmailCliente> emailCliente;
    public static volatile SingularAttribute<Caso, SubComponente> idSubComponente;
    public static volatile SingularAttribute<Caso, Producto> idProducto;
    public static volatile ListAttribute<Caso, Documento> documentoList;
    public static volatile ListAttribute<Caso, Caso> casosRelacionadosList;
    public static volatile SingularAttribute<Caso, Date> fechaCierre;
    public static volatile SingularAttribute<Caso, Caso> idCasoPadre;
    public static volatile SingularAttribute<Caso, String> estadoEscalacion;
    public static volatile SingularAttribute<Caso, Date> fechaCreacion;
    public static volatile SingularAttribute<Caso, Boolean> revisarActualizacion;
    public static volatile SingularAttribute<Caso, Integer> tranferCount;
    public static volatile SingularAttribute<Caso, Long> idCaso;
    public static volatile SingularAttribute<Caso, String> descripcion;
    public static volatile ListAttribute<Caso, Attachment> attachmentList;
    public static volatile SingularAttribute<Caso, Componente> idComponente;
    public static volatile SingularAttribute<Caso, String> tema;
    public static volatile SingularAttribute<Caso, Date> fechaRespuesta;
    public static volatile SingularAttribute<Caso, String> respuesta;
    public static volatile SingularAttribute<Caso, SubEstadoCaso> idSubEstado;
    public static volatile SingularAttribute<Caso, Date> initResponseDue;
    public static volatile SingularAttribute<Caso, TipoAlerta> estadoAlerta;
    public static volatile SingularAttribute<Caso, Boolean> esPrioritario;
    public static volatile SingularAttribute<Caso, Date> nextResponseDue;
    public static volatile SingularAttribute<Caso, Usuario> owner;
    public static volatile ListAttribute<Caso, Nota> notaList;
    public static volatile SingularAttribute<Caso, EstadoCaso> idEstado;
    public static volatile SingularAttribute<Caso, Boolean> esPregConocida;
    public static volatile SingularAttribute<Caso, Canal> idCanal;
    public static volatile ListAttribute<Caso, Etiqueta> etiquetaList;
}