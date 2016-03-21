/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;
import Interface.VentanaLogueo;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Marca implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMarca;
    private String nombreMarca;
    @OneToMany
    private Map<Integer,Modelo> modelos;
   
    public Marca(){}

    public Marca(String nombreMarca) {
        this.modelos = new HashMap();
        this.nombreMarca = nombreMarca;
        Remiseria.persistencia.insert(this);
    }

    public Map<Integer, Modelo> getModelos() {
        return modelos;
    }

    public void setModelos(Map<Integer, Modelo> modelos) {
        this.modelos = modelos;
    }


    


    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public String getNombreMarca() {
        return nombreMarca;
    }

    public void setNombreMarca(String nombreMarca) {
        this.nombreMarca = nombreMarca;
    }


     public Modelo agregarModelo(String nombreModelo)
    {
        Modelo unModelo = null;
        unModelo = new Modelo(nombreModelo);
        int numero = unModelo.getIdModelo();
        this.modelos.put(numero, unModelo);
        Remiseria.persistencia.update(this);
        return unModelo;
    
    }
    
    public List buscarModelos(){
        List modelosActivas = new LinkedList();
        Collection modelos = this.getModelos().values();
        Modelo aux = null;
        Iterator iter = modelos.iterator();
        while (iter.hasNext()){
            aux = (Modelo) iter.next();
            modelosActivas.add(aux);
            
        }
        return modelosActivas;
    }
    
     public void modificarModelo(Modelo unModelo,String nombreModelo){
            unModelo.setNombreModelo(nombreModelo);
            Remiseria.persistencia.update(this);
    }
    
     public void eliminarModelo(String nombreModelo,Remiseria remiseria){
        Utilidad utilidades = new Utilidad();
         Modelo unModelo = this.buscarModelo(nombreModelo);
        this.modelos.remove(unModelo.getIdModelo());
       //Remiseria.persistencia.delete(unModelo);
        Remiseria.persistencia.update(this);
        remiseria.crearAuditoria("DEL", "class Clase.Modelo", VentanaLogueo.getUsuario(),utilidades.getFechaActual());
    }
    
        public Modelo buscarModelo(String nombreModelo){
        Collection modelos = this.getModelos().values();
        Modelo aux = null;
        Modelo retorno = null;
        Iterator iter = modelos.iterator();
        while (iter.hasNext()){
            aux = (Modelo) iter.next();
            if (aux.getNombreModelo().equals(nombreModelo)){
                retorno = aux;
            }
        }
        return retorno;
    }

 @Override
    public String toString() {
        return "Marca{" + "idMarca=" + this.idMarca + "nombreMarca=" + this.nombreMarca + '}';
    }

    
    
}
