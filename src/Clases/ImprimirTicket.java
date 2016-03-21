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
public class ImprimirTicket {
    private String movil;
    private String chofer;
    private String origen;
    private String destino;
    private String fechahora;
    private String inicio;
    private String costo;

    
    public ImprimirTicket(String movil, String chofer, String origen, String destino, String fechahora, String inicio, String costo) {
        this.movil = movil;
        this.chofer = chofer;
        this.origen = origen;
        this.destino = destino;
        this.fechahora = fechahora;
        this.inicio = inicio;
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

    public String getFechahora() {
        return fechahora;
    }

    public void setFechahora(String fechahora) {
        this.fechahora = fechahora;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }
    

}
