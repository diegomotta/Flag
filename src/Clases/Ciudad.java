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
public class Ciudad implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private int idCiudad;
    private String nombreCiudad;
    @ManyToOne
    private Provincia unaProvincia;
    @OneToMany
    private Map<Integer,Barrio> barrios;

    public Ciudad(){}
    
    public Ciudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
        this.barrios = new HashMap();
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, Barrio> getBarrios() {
        return barrios;
    }

    public void setBarrios(Map<Integer, Barrio> barrios) {
        this.barrios = barrios;
    }


    public Provincia getUnaProvincia() {
        return unaProvincia;
    }

    public void setUnaProvincia(Provincia unaProvincia) {
        this.unaProvincia = unaProvincia;
    }


    public int getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
    
    
    public Barrio agregarBarrio(String nombreBarrio)
    {
        Barrio unBarrio = null;
        unBarrio = new Barrio(nombreBarrio);
        int numero = unBarrio.getIdBarrio();
        this.barrios.put(numero, unBarrio);
        Remiseria.persistencia.update(this);
        return unBarrio;
    
    }
    
    public List buscarBarrios(){
        List barriosActivos = new LinkedList();
        Collection barrios = this.getBarrios().values();
        Barrio aux = null;
        Iterator iter = barrios.iterator();
        while (iter.hasNext()){
            aux = (Barrio) iter.next();
            barriosActivos.add(aux);
            
        }
        return barriosActivos;
    }
    
    public void modificarBarrio(Barrio unBarrio,String nombreBarrio){
        unBarrio.setNombreBarrio(nombreBarrio); 
        Remiseria.persistencia.update(this);
    }
    
     public void eliminarBarrio(String nombreBarrio,Remiseria remiseria){
         try
         {
             Utilidad utilidades = new Utilidad();
            Barrio unBarrio = this.buscarBarrio(nombreBarrio);
            this.barrios.remove(unBarrio.getIdBarrio());
            //Remiseria.persistencia.delete(unBarrio);
            Remiseria.persistencia.update(this);
            remiseria.crearAuditoria("DEL", "class Clase.Barrio", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
         }
         catch (Exception ex)
         {
             JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
         }
    }
    
        public Barrio buscarBarrio(String nombreBarrio){
        Collection barrios = this.getBarrios().values();
        Barrio aux = null;
        Barrio retorno = null;
        Iterator iter = barrios.iterator();
        while (iter.hasNext()){
            aux = (Barrio) iter.next();
            if (aux.getNombreBarrio().equals(nombreBarrio)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    @Override
    public String toString() {
        return this.getNombreCiudad() ;
    }
    
    
}
