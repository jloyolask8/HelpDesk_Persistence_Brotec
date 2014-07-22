package com.itcs.helpdesk.persistence.entities;

import com.itcs.helpdesk.persistence.entities.Caso;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2013-04-26T17:38:15")
@StaticMetamodel(Attachment.class)
public class Attachment_ { 

    public static volatile SingularAttribute<Attachment, Long> idAttachment;
    public static volatile SingularAttribute<Attachment, String> nombreArchivo;
    public static volatile SingularAttribute<Attachment, Boolean> enRespuesta;
    public static volatile SingularAttribute<Attachment, String> mimeType;
    public static volatile SingularAttribute<Attachment, String> contentId;
    public static volatile SingularAttribute<Attachment, Caso> idCaso;

}