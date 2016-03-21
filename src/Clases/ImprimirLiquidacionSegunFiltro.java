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
public class ImprimirLiquidacionSegunFiltro {
    private String numeroLiq;
    private String tipo;
    private String numDocumento;
    private String nombreYapellido;
    private String fechaDePago;
    private String totalRendido;
    private String totalLiquidado;
    public ImprimirLiquidacionSegunFiltro(){}

    public ImprimirLiquidacionSegunFiltro(String numeroLiq, String tipo, String numDocumento, String nombreYapellido, String fechaDePago, String totalRendido, String totalLiquidado) {
        this.numeroLiq = numeroLiq;
        this.tipo = tipo;
        this.numDocumento = numDocumento;
        this.nombreYapellido = nombreYapellido;
        this.fechaDePago = fechaDePago;
        this.totalRendido = totalRendido;
        this.totalLiquidado = totalLiquidado;
    }

    public String getNumeroLiq() {
        return numeroLiq;
    }

    public void setNumeroLiq(String numeroLiq) {
        this.numeroLiq = numeroLiq;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumDocumento() {
        return numDocumento;
    }

    public void setNumDocumento(String numDocumento) {
        this.numDocumento = numDocumento;
    }

    public String getNombreYapellido() {
        return nombreYapellido;
    }

    public void setNombreYapellido(String nombreYapellido) {
        this.nombreYapellido = nombreYapellido;
    }

    public String getFechaDePago() {
        return fechaDePago;
    }

    public void setFechaDePago(String fechaDePago) {
        this.fechaDePago = fechaDePago;
    }

    public String getTotalRendido() {
        return totalRendido;
    }

    public void setTotalRendido(String totalRendido) {
        this.totalRendido = totalRendido;
    }

    public String getTotalLiquidado() {
        return totalLiquidado;
    }

    public void setTotalLiquidado(String totalLiquidado) {
        this.totalLiquidado = totalLiquidado;
    }

}
