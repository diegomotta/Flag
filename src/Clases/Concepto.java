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
public class Concepto implements Comparable<Concepto>, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer idConcepto;
    private String tipo;
    private String detalle;

    public Concepto() {
    }

    public Concepto(String tipo, String detalle) {
        this.idConcepto = 0;
        this.tipo = tipo;
        this.detalle = detalle;
        Remiseria.persistencia.insert(this);
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }



    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return this.getDetalle();
    }

    public Object[] toVector() {
        Object fila[] = {this.getTipo(), this};
        return fila;
    }

    
    @Override
    public int compareTo(Concepto t) {
         if (this.tipo.compareToIgnoreCase(t.getTipo()) == 0) {
            return this.detalle.compareTo(getDetalle());
        } else {
            return this.tipo.compareToIgnoreCase(t.getTipo());
        }
    }
    
}
