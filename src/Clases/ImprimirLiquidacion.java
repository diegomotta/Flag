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
public class ImprimirLiquidacion {
    private String numeroLiq;
    private String total;
    private String totalRendido;
    private String porcentaje;
    private String fechaLiquidado;
    
    public ImprimirLiquidacion(){}
    
    public ImprimirLiquidacion(String numeroLiq, String total, String totalRendido, String porcentaje, String fechaLiquidado) {
        this.numeroLiq = numeroLiq;
        this.total = total;
        this.totalRendido = totalRendido;
        this.porcentaje = porcentaje;
        this.fechaLiquidado = fechaLiquidado;
    }

    public String getNumeroLiq() {
        return numeroLiq;
    }

    public void setNumeroLiq(String numeroLiq) {
        this.numeroLiq = numeroLiq;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTotalRendido() {
        return totalRendido;
    }

    public void setTotalRendido(String totalRendido) {
        this.totalRendido = totalRendido;
    }

    public String getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(String porcentaje) {
        this.porcentaje = porcentaje;
    }

    public String getFechaLiquidado() {
        return fechaLiquidado;
    }

    public void setFechaLiquidado(String fechaLiquidado) {
        this.fechaLiquidado = fechaLiquidado;
    }

}
