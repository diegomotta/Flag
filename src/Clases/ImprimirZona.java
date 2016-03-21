/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

/**
 *
 * @author garba
 */
public class ImprimirZona {
private String nombreZona;
private String inmediaciones;

    public ImprimirZona(String nombreZona, String inmediaciones) {
        this.nombreZona = nombreZona;
        this.inmediaciones = inmediaciones;
    }

    public String getNombreZona() {
        return nombreZona;
    }

    public void setNombreZona(String nombreZona) {
        this.nombreZona = nombreZona;
    }

    public String getInmediaciones() {
        return inmediaciones;
    }

    public void setInmediaciones(String inmediaciones) {
        this.inmediaciones = inmediaciones;
    }


}
