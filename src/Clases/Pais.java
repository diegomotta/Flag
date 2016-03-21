/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Interface.VentanaLogueo;
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
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPais;
    private String nombrePais;
    @OneToMany
    private Map<Integer,Provincia> provincias;

    public Pais(){}

    public Pais(String nombrePais) {
        this.nombrePais = nombrePais;
        this.provincias = new HashMap();
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, Provincia> getProvincias() {
        return provincias;
    }

    public void setProvincias(Map<Integer, Provincia> provincias) {
        this.provincias = provincias;
    }



    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }
    
    
     public Provincia agregarProvincia(String nombreProvincia)
    {
        Provincia unaProvincia = null;
        unaProvincia = new Provincia(nombreProvincia);
        int numero = unaProvincia.getIdProvincia();
        this.provincias.put(numero, unaProvincia);
        Remiseria.persistencia.update(this);
        return unaProvincia;
    
    }
    
    public List buscarProvincias(){
        List provinciasActivos = new LinkedList();
        Collection provincias = this.getProvincias().values();
        Provincia aux = null;
        Iterator iter = provincias.iterator();
        while (iter.hasNext()){
            aux = (Provincia) iter.next();
            provinciasActivos.add(aux);
            
        }
        return provinciasActivos;
    }
    
    public void modificarProvincia(Provincia unaProvincia,String nombreProvincia){
            unaProvincia.setNombreProvincia(nombreProvincia);
            Remiseria.persistencia.update(this);
    }
    
     public void eliminarProvincia(String nombreProvincia,Remiseria remiseria){
        try{
        Utilidad utilidades = new Utilidad();           
        if(!nombreProvincia.equals(remiseria.getUnDomicilio().getUnaProvincia().getNombreProvincia()))
        {
            Provincia unaProvincia = this.buscarProvincia(nombreProvincia);
            this.provincias.remove(unaProvincia.getIdProvincia());
            //Remiseria.persistencia.delete(unaProvincia);
            Remiseria.persistencia.update(this);
            remiseria.crearAuditoria("DEL", "class Clase.Provincia", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
        }
        else
        {
            JOptionPane.showMessageDialog(null, "El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
        
        }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, "El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
        }
       
    }
    
        public Provincia buscarProvincia(String nombreProvincia){
        Collection provincias = this.getProvincias().values();
        Provincia aux = null;
        Provincia retorno = null;
        Iterator iter = provincias.iterator();
        while (iter.hasNext()){
            aux = (Provincia) iter.next();
            if (aux.getNombreProvincia().equals(nombreProvincia)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    
    @Override
    public String toString() {
        return this.nombrePais ;
    }
    
}
