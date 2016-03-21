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
public class ImprimirViajes {
    private String movil;
    private String chofer;
    private String origen;
    private String destino;
    private String utilidad;
    private String fechaHora;
    private String inicio;
    private String costo;
    private String numeroViaje;
    
    public ImprimirViajes(String numeroViaje,String movil, String chofer, String origen, String destino, String utilidad, String fechaHora, String inicio,String costo) {
        this.numeroViaje = numeroViaje;
        this.movil = movil;
        this.chofer = chofer;
        this.origen = origen;
        this.destino = destino;
        this.utilidad = utilidad;
        this.fechaHora = fechaHora;
        this.inicio = inicio;
        this.costo = costo;
    }

    public String getNumeroViaje() {
        return numeroViaje;
    }

    public void setNumeroViaje(String numeroViaje) {
        this.numeroViaje = numeroViaje;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getUtilidad() {
        return utilidad;
    }

    public void setUtilidad(String utilidad) {
        this.utilidad = utilidad;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }
    
}
