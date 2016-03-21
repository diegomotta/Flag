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
public class Provincia implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idProvincia;
    private String nombreProvincia;
    @ManyToOne
    private Pais unPais;
    @OneToMany
    private Map<Integer,Ciudad> ciudades;
    
    public Provincia (){};

    public Provincia(String nombreProvincia) {
        this.ciudades = new HashMap();
        this.nombreProvincia = nombreProvincia;
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, Ciudad> getCiudades() {
        return ciudades;
    }

    public void setCiudades(Map<Integer, Ciudad> ciudades) {
        this.ciudades = ciudades;
    }


    public Pais getUnPais() {
        return unPais;
    }

    public void setUnPais(Pais unPais) {
        this.unPais = unPais;
    }

    public int getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(int idProvincia) {
        this.idProvincia = idProvincia;
    }

    public String getNombreProvincia() {
        return nombreProvincia;
    }

    public void setNombreProvincia(String nombreProvincia) {
        this.nombreProvincia = nombreProvincia;
    }
    
    
     
     public Ciudad agregarCiudad(String nombreCiudad)
    {
        Ciudad unaCiudad = null;
        unaCiudad = new Ciudad(nombreCiudad);
        int numero = unaCiudad.getIdCiudad();
        this.ciudades.put(numero, unaCiudad);
        Remiseria.persistencia.update(this);
        return unaCiudad;
    
    }
    
    public List buscarCiudades(){
        List ciudadesActivos = new LinkedList();
        Collection ciudades = this.getCiudades().values();
        Ciudad aux = null;
        Iterator iter = ciudades.iterator();
        while (iter.hasNext()){
            aux = (Ciudad) iter.next();
            ciudadesActivos.add(aux);
            
        }
        return ciudadesActivos;
    }
    
    public void modificarCiudad(Ciudad unaCiudad,String nombreCiudad){
        unaCiudad.setNombreCiudad(nombreCiudad);
        Remiseria.persistencia.update(this);
    }
    
     public void eliminarCiudad(String nombreCiudad,Remiseria remiseria){
         try
         {
            if(!nombreCiudad.equals(remiseria.getUnDomicilio().getUnaCiudad().getNombreCiudad()))
            {
                Utilidad utilidades = new Utilidad();
                Ciudad unaCiudad = this.buscarCiudad(nombreCiudad);
                this.ciudades.remove(unaCiudad.getIdCiudad());
                //Remiseria.persistencia.delete(unaCiudad);
                Remiseria.persistencia.update(this); 
                remiseria.crearAuditoria("DEL", "class Clase.Modelo", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
            }
            else
            {
                JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);

            }
         }
         catch(Exception ex)
         {
              JOptionPane.showMessageDialog(null,"El dato esta siendo utilizado en otra instancia, no se puede eliminar", null, JOptionPane.WARNING_MESSAGE);
         }
       
    }
    
        public Ciudad buscarCiudad(String nombreCiudad){
        Collection ciudades = this.getCiudades().values();
        Ciudad aux = null;
        Ciudad retorno = null;
        Iterator iter = ciudades.iterator();
        while (iter.hasNext()){
            aux = (Ciudad) iter.next();
            if (aux.getNombreCiudad().equals(nombreCiudad)){
                retorno = aux;
            }
        }
        return retorno;
    }
    
    
    
    @Override
    public String toString() {
        return  this.nombreProvincia ;
    }
    
}
