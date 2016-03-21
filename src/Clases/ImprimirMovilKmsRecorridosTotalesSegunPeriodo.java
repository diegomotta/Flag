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
public class ImprimirMovilKmsRecorridosTotalesSegunPeriodo {
private String numeroMovil;
private String kmsTotal;
private String kmsViajes;
private String diferencia;
    public ImprimirMovilKmsRecorridosTotalesSegunPeriodo(){}
    
    public ImprimirMovilKmsRecorridosTotalesSegunPeriodo(String numeroMovil,String kmsTotal,String kmsViajes,String diferencia)
    {
        this.numeroMovil = numeroMovil;
        this.kmsTotal = kmsTotal;
        this.kmsViajes = kmsViajes;
        this.diferencia = diferencia;
    }

    public String getKmsViajes() {
        return kmsViajes;
    }

    public void setKmsViajes(String kmsViajes) {
        this.kmsViajes = kmsViajes;
    }

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }

    public String getNumeroMovil() {
        return numeroMovil;
    }

    public void setNumeroMovil(String numeroMovil) {
        this.numeroMovil = numeroMovil;
    }

    public String getKmsTotal() {
        return kmsTotal;
    }

    public void setKmsTotal(String kmsTotal) {
        this.kmsTotal = kmsTotal;
    }
    
}
