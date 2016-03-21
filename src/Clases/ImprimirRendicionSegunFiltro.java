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
public class ImprimirRendicionSegunFiltro {
    private String numero;
    private String fecha;
    private String hora;
    private String viajes;
    private String total;
    private String movil;
    private String chofer;
    public ImprimirRendicionSegunFiltro(){}
    
    public ImprimirRendicionSegunFiltro(String numero, String movil,String chofer,String fecha, String hora, String viajes, String total) {
        this.numero = numero;
        this.chofer = chofer;
        this.movil = movil;
        this.fecha = fecha;
        this.hora = hora;
        this.viajes = viajes;
        this.total = total;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getViajes() {
        return viajes;
    }

    public void setViajes(String viajes) {
        this.viajes = viajes;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
    
           
}
