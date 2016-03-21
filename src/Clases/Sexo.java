/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Sexo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idSexo;
    private String nombreSexo;

    public Sexo(){}
    
    public Sexo(String nombreSexo) {
        this.nombreSexo = nombreSexo;
        Remiseria.persistencia.insert(this);
    }

    public int getIdSexo() {
        return idSexo;
    }

    public void setIdSexo(int idSexo) {
        this.idSexo = idSexo;
    }

    public String getNombreSexo() {
        return nombreSexo;
    }

    public void setNombreSexo(String nombreSexo) {
        this.nombreSexo = nombreSexo;
    }
    
     @Override
    public String toString() {
        return "Sexo{" + "idSexo=" + this.idSexo + "nombreSexo=" + this.nombreSexo + '}';
    }
    
}
