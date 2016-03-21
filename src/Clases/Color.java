/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
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
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int idColor;
    private String nombreColor;

    public Color(){}
    
    public Color(String nombreColor) {
        this.nombreColor = nombreColor;
        Remiseria.persistencia.insert(this);
    }

    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }
    

 @Override
    public String toString() {
        return "Color{" + "idColor=" + this.idColor + "nombreColor=" + this.nombreColor + '}';
    }
 
           
}
