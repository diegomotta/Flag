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
public class ImprimirMóviles {
    private String numero;
    private String patente;
    private String año;
    private String unaMarca;
    private String unModelo;
    private String unMotor;
    private String unColor;
    private String unTipoUtilidad;
    private String unRodado;
    private String kilometro;
    private String aireAcondicionado;
    private String capacidadCarga;
    private String alquilado;
    
    public ImprimirMóviles(String numero, String patente, String año, String unaMarca, String unModelo, String unMotor, String unColor, String unTipoUtilidad, String unRodado, String kilometro, String aireAcondicionado, String capacidadCarga,String alquilado) {
        this.numero = numero;
        this.patente = patente;
        this.año = año;
        this.unaMarca = unaMarca;
        this.unModelo = unModelo;
        this.unMotor = unMotor;
        this.unColor = unColor;
        this.unTipoUtilidad = unTipoUtilidad;
        this.unRodado = unRodado;
        this.kilometro = kilometro;
        this.aireAcondicionado = aireAcondicionado;
        this.capacidadCarga = capacidadCarga;
        this.alquilado = alquilado;
    }

    public String getAlquilado() {
        return alquilado;
    }

    public void setAlquilado(String alquilado) {
        this.alquilado = alquilado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getAño() {
        return año;
    }

    public void setAño(String año) {
        this.año = año;
    }

    public String getUnaMarca() {
        return unaMarca;
    }

    public void setUnaMarca(String unaMarca) {
        this.unaMarca = unaMarca;
    }

    public String getUnModelo() {
        return unModelo;
    }

    public void setUnModelo(String unModelo) {
        this.unModelo = unModelo;
    }

    public String getUnMotor() {
        return unMotor;
    }

    public void setUnMotor(String unMotor) {
        this.unMotor = unMotor;
    }

    public String getUnColor() {
        return unColor;
    }

    public void setUnColor(String unColor) {
        this.unColor = unColor;
    }

    public String getUnTipoUtilidad() {
        return unTipoUtilidad;
    }

    public void setUnTipoUtilidad(String unTipoUtilidad) {
        this.unTipoUtilidad = unTipoUtilidad;
    }

    public String getUnRodado() {
        return unRodado;
    }

    public void setUnRodado(String unRodado) {
        this.unRodado = unRodado;
    }

    public String getKilometro() {
        return kilometro;
    }

    public void setKilometro(String kilometro) {
        this.kilometro = kilometro;
    }

    public String getAireAcondicionado() {
        return aireAcondicionado;
    }

    public void setAireAcondicionado(String aireAcondicionado) {
        this.aireAcondicionado = aireAcondicionado;
    }

    public String getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(String capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }
    
    
}
