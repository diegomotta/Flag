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
public class Edad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEdad;
    private int edadMinimaChofer;
    private int edadMinimaOperario;    

    public Edad() {}
    
    Edad(int edadMinimaChofer,int edadMinimaOperario)
    {
          this.idEdad = 0;
          this.edadMinimaChofer = edadMinimaChofer;
          this.edadMinimaOperario = edadMinimaOperario;
          Remiseria.persistencia.insert(this);
    }

    public int getIdEdad() {
        return idEdad;
    }

    public void setIdEdad(int idEdad) {
        this.idEdad = idEdad;
    }

    public int getEdadMinimaChofer() {
        return edadMinimaChofer;
    }

    public void setEdadMinimaChofer(int edadMinimaChofer) {
        this.edadMinimaChofer = edadMinimaChofer;
    }

    public int getEdadMinimaOperario() {
        return edadMinimaOperario;
    }

    public void setEdadMinimaOperario(int edadMinimaOperario) {
        this.edadMinimaOperario = edadMinimaOperario;
    }

    
}
