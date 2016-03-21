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
public class ImprimirViajesRendiciones {
private String detalle;
private String costo;
private String fechaViaje;
private String horaViaje;

    public ImprimirViajesRendiciones(){}

    public ImprimirViajesRendiciones(String detalle, String costo, String fechaViaje, String horaViaje) {
        this.detalle = detalle;
        this.costo = costo;
        this.fechaViaje = fechaViaje;
        this.horaViaje = horaViaje;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(String fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public String getHoraViaje() {
        return horaViaje;
    }

    public void setHoraViaje(String horaViaje) {
        this.horaViaje = horaViaje;
    }


    
    
    
}
