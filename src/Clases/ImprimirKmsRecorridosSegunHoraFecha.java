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
public class ImprimirKmsRecorridosSegunHoraFecha {
private String movil;
private String chofer;
private String inicial;
private String finall;
private String total;
private String kmsViaje;
private String diferencia;
    public ImprimirKmsRecorridosSegunHoraFecha(){}

    public ImprimirKmsRecorridosSegunHoraFecha(String movil, String chofer, String inicial, String finall, String total, String kmsViaje, String diferencia) {
        this.movil = movil;
        this.chofer = chofer;
        this.inicial = inicial;
        this.finall = finall;
        this.total = total;
        this.kmsViaje = kmsViaje;
        this.diferencia = diferencia;
    }

    public String getKmsViaje() {
        return kmsViaje;
    }

    public void setKmsViaje(String kmsViaje) {
        this.kmsViaje = kmsViaje;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
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

    public String getInicial() {
        return inicial;
    }

    public void setInicial(String inicial) {
        this.inicial = inicial;
    }

    public String getFinall() {
        return finall;
    }

    public void setFinall(String finall) {
        this.finall = finall;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

        
        
}
