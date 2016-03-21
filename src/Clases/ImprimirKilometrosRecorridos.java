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
public class ImprimirKilometrosRecorridos {
  private String kilometroInicialServicio;
  private String kilometroFinalServicio;
  private String totalKms;
  private String fechaHoraInicio;
  private String fechaHoraFinal;
  private String unChofer;
  private String movil;
    public ImprimirKilometrosRecorridos(String movil,String kilometroInicialServicio, String kilometroFinalServicio, String totalKms, String fechaHoraInicio, String fechaHoraFinal, String unChofer) {
        this.movil = movil;
        this.kilometroInicialServicio = kilometroInicialServicio;
        this.kilometroFinalServicio = kilometroFinalServicio;
        this.totalKms = totalKms;
        this.fechaHoraInicio = fechaHoraInicio;
        this.fechaHoraFinal = fechaHoraFinal;
        this.unChofer = unChofer;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getKilometroInicialServicio() {
        return kilometroInicialServicio;
    }

    public void setKilometroInicialServicio(String kilometroInicialServicio) {
        this.kilometroInicialServicio = kilometroInicialServicio;
    }

    public String getKilometroFinalServicio() {
        return kilometroFinalServicio;
    }

    public void setKilometroFinalServicio(String kilometroFinalServicio) {
        this.kilometroFinalServicio = kilometroFinalServicio;
    }

    public String getTotalKms() {
        return totalKms;
    }

    public void setTotalKms(String totalKms) {
        this.totalKms = totalKms;
    }

    public String getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(String fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public String getFechaHoraFinal() {
        return fechaHoraFinal;
    }

    public void setFechaHoraFinal(String fechaHoraFinal) {
        this.fechaHoraFinal = fechaHoraFinal;
    }

    public String getUnChofer() {
        return unChofer;
    }

    public void setUnChofer(String unChofer) {
        this.unChofer = unChofer;
    }
    
    
}
