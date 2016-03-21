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
public class Telefono implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idTelefono;
    private String nroTelefonico;
    @ManyToOne  
    private CodArea unCodArea;
    @ManyToOne
    private CodPais unCodPais;
    @ManyToOne
    private TipoTelefono unTipoTelefono;        

    public Telefono(){}



    public Telefono(String nroTelefonico, CodArea unCodArea, CodPais unCodPais, TipoTelefono unTipoTelefono) {
        this.nroTelefonico = nroTelefonico;
        this.unCodArea = unCodArea;
        this.unCodPais = unCodPais;
        this.unTipoTelefono = unTipoTelefono;
        Remiseria.persistencia.insert(this);
    }




    public CodArea getUnCodArea() {
        return unCodArea;
    }

    public void setUnCodArea(CodArea unCodArea) {
        this.unCodArea = unCodArea;
    }

    public CodPais getUnCodPais() {
        return unCodPais;
    }

    public void setUnCodPais(CodPais unCodPais) {
        this.unCodPais = unCodPais;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getNroTelefonico() {
        return nroTelefonico;
    }

    public void setNroTelefonico(String nroTelefonico) {
        this.nroTelefonico = nroTelefonico;
    }

    public TipoTelefono getUnTipoTelefono() {
        return unTipoTelefono;
    }

    public void setUnTipoTelefono(TipoTelefono unTipoTelefono) {
        this.unTipoTelefono = unTipoTelefono;
    }
    
       @Override
    public String toString() {
        return this.getUnCodPais().getCodPais().toString()+"-" +this.unCodArea.getCodArea().toString()+ "-"+this.nroTelefonico;
    }
    
}
