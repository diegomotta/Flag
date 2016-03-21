/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
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
public class BajadaBandera implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int idBajadaBandera;
    private double valorBajadaBandera;
    
    public BajadaBandera(){}
    
    public BajadaBandera (double valorBajadaBandera)
    {
        this.valorBajadaBandera = valorBajadaBandera;
        Remiseria.persistencia.insert(this);
    }

    public int getIdBajadaBandera() {
        return idBajadaBandera;
    }

    public void setIdBajadaBandera(int idBajadaBandera) {
        this.idBajadaBandera = idBajadaBandera;
    }

    public double getValorBajadaBandera() {
        return valorBajadaBandera;
    }

    public void setValorBajadaBandera(double valorBajadaBandera) {
        this.valorBajadaBandera = valorBajadaBandera;
    }
    
    
}
