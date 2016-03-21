package Log_Auditoria;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionType;

public class RevisionListener implements EntityTrackingRevisionListener  {

    @Override
    public void entityChanged(Class type, String string, Serializable srlzbl, RevisionType rt, Object o) {
//con esto veo si fue un ADD, MOD o DEL        
        String tipo = type.getName();     
        
//ak ago un casting del objeto a Entidad_Revisada, que seria tu ObjetoPersistido y le seteo las variables
//que me interesan sacar de ak, en mi caso que clase fue, el tipo de operacion y bue una fecha q ni se porque
//esta ak        
        ((Entidad_Revisada) o).setEntityClassName(String.valueOf(type));
        ((Entidad_Revisada) o).setAccion(String.valueOf(rt));
        ((Entidad_Revisada) o).setMomento(new Date());
        
    }

    @Override
    public void newRevision(Object o) {

    }
}
