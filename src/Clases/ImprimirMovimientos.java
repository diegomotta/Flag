/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.util.Date;

/**
 *
 * @author garba
 */
public class ImprimirMovimientos {
private String fecha_dia;
private String hora_mov;
private String entrada;
private String salida; 
private String tipo;
private String concepto;    
private String operario;

    public ImprimirMovimientos()
    {}

    public ImprimirMovimientos(String fecha_dia, String hora_mov, String entrada, String salida, String tipo, String concepto,String operario) {
        this.fecha_dia = fecha_dia;
        this.hora_mov = hora_mov;
        this.entrada = entrada;
        this.salida = salida;
        this.tipo = tipo;
        this.concepto = concepto;
        this.operario = operario;
    }

    public String getOperario() {
        return operario;
    }

    public void setOperario(String operario) {
        this.operario = operario;
    }

    public String getFecha_dia() {
        return fecha_dia;
    }

    public void setFecha_dia(String fecha_dia) {
        this.fecha_dia = fecha_dia;
    }

    public String getHora_mov() {
        return hora_mov;
    }

    public void setHora_mov(String hora_mov) {
        this.hora_mov = hora_mov;
    }

    public String getEntrada() {
        return entrada;
    }

    public void setEntrada(String entrada) {
        this.entrada = entrada;
    }

    public String getSalida() {
        return salida;
    }

    public void setSalida(String salida) {
        this.salida = salida;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }
    
    
}
