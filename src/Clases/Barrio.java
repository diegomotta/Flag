/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Interface.VentanaLogueo;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import javax.swing.JOptionPane;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Barrio implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private int idBarrio;
    private String nombreBarrio;
    @ManyToOne
    private Ciudad unaCiudad;
    @OneToMany
    private Map<Integer,DireccionViaje> direcciones;
    public Barrio(){}
    
    public Barrio(String nombreBarrio) {
        this.nombreBarrio = nombreBarrio;
        this.direcciones = new HashMap();
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, DireccionViaje> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(Map<Integer, DireccionViaje> direcciones) {
        this.direcciones = direcciones;
    }

    public Ciudad getUnaCiudad() {
        return unaCiudad;
    }

    public void setUnaCiudad(Ciudad unaCiudad) {
        this.unaCiudad = unaCiudad;
    }

    public int getIdBarrio() {
        return idBarrio;
    }

    public void setIdBarrio(int idBarrio) {
        this.idBarrio = idBarrio;
    }

    public String getNombreBarrio() {
        return nombreBarrio;
    }

    public void setNombreBarrio(String nombreBarrio) {
        this.nombreBarrio = nombreBarrio;
    }
    
    public DireccionViaje agregarDireccion(String direccion)
    {
        DireccionViaje unDireccionViaje = null;
        unDireccionViaje = new DireccionViaje(direccion);
        int numero = unDireccionViaje.getIdDireccion();
        this.direcciones.put(numero, unDireccionViaje);
        Remiseria.persistencia.update(this);
        return unDireccionViaje;
    
    }
    
    public List buscarDirecciones(){
        List direccionesActivos = new LinkedList();
        Collection direccioness = this.getDirecciones().values();
        DireccionViaje aux = null;
        Iterator iter = direccioness.iterator();
        while (iter.hasNext()){
            aux = (DireccionViaje) iter.next();
            direccionesActivos.add(aux);
            
        }
        return direccionesActivos;
    }
    
    public void modificarDireccion(DireccionViaje unDireccionViaje,String direccion){
        unDireccionViaje.setDireccion(direccion); 
        Remiseria.persistencia.update(this);
    }
    
     public void eliminarDireccion(String direccion,Remiseria remiseria)
     {
         try
         {      Utilidad utilidades = new Utilidad();
                DireccionViaje unaDireccionViaje = this.buscarDireccionViaje(direccion);
                this.direcciones.remove(unaDireccionViaje.getIdDireccion());
                //Remiseria.persistencia.delete(unaDireccionViaje);
                Remiseria.persistencia.update(this);
                remiseria.crearAuditoria("DEL", "class Clase.DireccionViaje", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
         }
         catch (Exception ex)
         {
             JOptionPane.showConfirmDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
         }
    }
    
        public DireccionViaje buscarDireccionViaje(String direccion){
        Collection dires = this.getDirecciones().values();
        DireccionViaje aux = null;
        DireccionViaje retorno = null;
        Iterator iter = dires.iterator();
        while (iter.hasNext()){
            aux = (DireccionViaje) iter.next();
            if (aux.getDireccion().equals(direccion)){
                retorno = aux;
            }
        }
        return retorno;
    }
       @Override
    public String toString() {
        return this.nombreBarrio ;
    }
    
}
