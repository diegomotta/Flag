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
public class TiempoEspera2 implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int idTiempoEspera;
    private int valorTiempo;
    
    public TiempoEspera2(){}
    
    public TiempoEspera2(int valorTiempo)
    {
        this.valorTiempo = valorTiempo;
        Remiseria.persistencia.insert(this);
    }

    public int getIdTiempoEspera() {
        return idTiempoEspera;
    }

    public void setIdTiempoEspera(int idTiempoEspera) {
        this.idTiempoEspera = idTiempoEspera;
    }

    public int getValorTiempo() {
        return valorTiempo;
    }

    public void setValorTiempo(int valorTiempo) {
        this.valorTiempo = valorTiempo;
    }
    
    
}
