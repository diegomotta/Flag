package Log_Auditoria;

import Interface.VentanaLogueo;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@RevisionEntity(RevisionListener.class)// este ak es el que te enlaza con el Escuchador en mi caso se llama RevisionListener
public class Entidad_Revisada implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private Integer id;
    @RevisionTimestamp
    private long customTimestamp;
    private String entityClassName;
    private String usuario = VentanaLogueo.getUsuario();
    private String accion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date momento;

    public Entidad_Revisada() {
    }

    public Entidad_Revisada(String entityClassName, String usuario, String accion, Date unMomento) {
       // this.customTimestamp = customTimestamp;
        this.entityClassName = entityClassName;
        this.usuario = usuario;
        this.accion = accion;
        this.momento = unMomento;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getCustomTimestamp() {
        return customTimestamp;
    }

    public void setCustomTimestamp(long customTimestamp) {
        this.customTimestamp = customTimestamp;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getMomento() {
        return momento;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }
    
    
}
