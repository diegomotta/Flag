/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Interface.VentanaLogueo;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.swing.JOptionPane;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class CodPais implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int idCodPais;
    private String codPais;
    @ManyToOne
    private Telefono unTelefono;
    @OneToMany
    private Map<Integer,CodArea> codsAreas;
    
    public CodPais(){}
    
    public CodPais(String codPais) {
        this.codPais = codPais;
        this.codsAreas = new HashMap();
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, CodArea> getCodsAreas() {
        return codsAreas;
    }

    public void setCodsAreas(Map<Integer, CodArea> codsAreas) {
        this.codsAreas = codsAreas;
    }

        
    public Telefono getUnTelefono() {
        return unTelefono;
    }

    public void setUnTelefono(Telefono unTelefono) {
        this.unTelefono = unTelefono;
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais;
    }

    public int getIdCodPais() {
        return idCodPais;
    }

    public void setIdCodPais(int idCodPais) {
        this.idCodPais = idCodPais;
    }
    
    
      public CodArea agregarCodArea(String codigoArea)
    {
        CodArea unCodArea = null;
        unCodArea = new CodArea(codigoArea);
        int numero = unCodArea.getIdCodArea();
        this.codsAreas.put(numero, unCodArea);
        Remiseria.persistencia.update(this);
        return unCodArea;
    
    }
    
       public List buscarCodigosAreas(){
        List codigosAreaActivos = new LinkedList();
        Collection codigosAreas = this.getCodsAreas().values();
        CodArea aux = null;
        Iterator iter = codigosAreas.iterator();
        while (iter.hasNext()){
            aux = (CodArea) iter.next();
            codigosAreaActivos.add(aux);
            
        }
        return codigosAreaActivos;
    }
    
    public void modificarCodArea(CodArea unCodArea,String codigoArea){
        unCodArea.setCodArea(codigoArea); 
        Remiseria.persistencia.update(this);
    }
    
     public void eliminarCodArea(String codigoArea,Remiseria remiseria){
         if(!codigoArea.equals(this.getUnTelefono().getUnCodArea().getCodArea()))
         {
            Utilidad utilidades = new Utilidad();
            CodArea unCodArea = this.buscarCodArea(codigoArea);
            this.codsAreas.remove(unCodArea.getIdCodArea());
            //Remiseria.persistencia.delete(unCodArea);
            Remiseria.persistencia.update(this);
            remiseria.crearAuditoria("DEL", "class Clase.Modelo", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
         }
         else
         {
             JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
         }
       
    }
    
        public CodArea buscarCodArea(String codigoArea){
        Collection codigoAreas = this.getCodsAreas().values();
        CodArea aux = null;
        CodArea retorno = null;
        Iterator iter = codigoAreas.iterator();
        while (iter.hasNext()){
            aux = (CodArea) iter.next();
            if (aux.getCodArea().equals(codigoArea) ){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    
    
     @Override
    public String toString() {
        return "CodPais{" + "idCodPais=" + this.idCodPais + "codPais=" + this.codPais +'}';
    }
    
}
