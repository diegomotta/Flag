/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class CodArea implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int idCodArea;
    private String codArea;
    @ManyToOne
    private CodPais unCodPais;
    
    public CodArea(){}
    
    public CodArea(String codArea) {
        this.codArea = codArea;
        Remiseria.persistencia.insert(this);
    }

    public CodPais getUnCodPais() {
        return unCodPais;
    }

    public void setUnCodPais(CodPais unCodPais) {
        this.unCodPais = unCodPais;
    }

       
    
    public String getCodArea() {
        return codArea;
    }

    public void setCodArea(String codArea) {
        this.codArea = codArea;
    }

    public int getIdCodArea() {
        return idCodArea;
    }

    public void setIdCodArea(int idCodArea) {
        this.idCodArea = idCodArea;
    }
    
   
    
        @Override
    public String toString() {
        return "CodArea{" + "idCodArea=" + this.idCodArea + "codArea=" + this.codArea +'}';
    }
    
}
