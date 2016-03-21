/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Modelo implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idModelo;
    private String nombreModelo;
    @ManyToOne
    private Marca unaMarca;
    
    public Modelo (){}
    
    public Modelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
        Remiseria.persistencia.insert(this);
    }

    public Marca getUnaMarca() {
        return unaMarca;
    }

    public void setUnaMarca(Marca unaMarca) {
        this.unaMarca = unaMarca;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
       
    }

    public String getNombreModelo() {
        return nombreModelo;
    }

    public void setNombreModelo(String nombreModelo) {
        this.nombreModelo = nombreModelo;
    }
    
     @Override
    public String toString() {
        return "Modelo{" + "idModelo=" + this.idModelo + "nombreModelo=" + this.nombreModelo + '}';
    }
}
