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
public class Configuracion implements Serializable{
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idSkin;
    private String skin;

    public Configuracion() { }
    
    Configuracion(String skin)
    {
        this.idSkin = 0;
        this.skin = skin;
        Remiseria.persistencia.insert(this);
    }
    
    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public int getIdSkin() {
        return idSkin;
    }

    public void setIdSkin(int idSkin) {
        this.idSkin = idSkin;
    }
   
   
   
}
