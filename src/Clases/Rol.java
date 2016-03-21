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
public class Rol implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idRol;
    private String nombreRol;
    @OneToMany
    private Map<String, Permisos> permisos;    
    
    public Rol(){}

    public Rol(String nombreRol, Map<String, Permisos> permisos) {
        this.nombreRol = nombreRol;
        this.permisos = permisos;
        Remiseria.persistencia.insert(this);
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public Map<String, Permisos> getPermisos() {
        return permisos;
    }

    public void setPermisos(Map<String, Permisos> permisos) {
        this.permisos = permisos;
    }

   
    @Override
    public String toString() {
        return this.getNombreRol();
    }
    
       
    public Object[] toVector() {
        Object fila[] = {this};
        return fila;
    }
    
      public void EliminarUnPermiso (Rol unRol,Remiseria remiseria)
    {
        try
        {
            
            Collection permisosSeleccionados = unRol.getPermisos().values();
            Iterator iter = permisosSeleccionados.iterator();
            Permisos aux = null;
             while(iter.hasNext()){
                   aux = (Permisos) iter.next();
                   this.permisos.remove(aux.getTipo());
   //                Remiseria.persistencia.delete(aux); 
                    remiseria.crearAuditoria("DEL", "class Clase.Permiso", VentanaLogueo.getUsuario(),"");
             }
             Remiseria.persistencia.update(this);   
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex, null, JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
}
