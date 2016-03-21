/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class KilometrosRecorridos implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idKilometrosRecorridos;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fecha;
    private int kilometrosInicial;
    private int kilometroFinal;
    private int totalKilometrajeVuelta;
    private double kmsEnUltimaViajeRealizado;
    private double diferencia;
    public KilometrosRecorridos(){}
    
    public KilometrosRecorridos(Date fecha, int kilometrosInicial, int kilometroFinal, int totalKilometrajeVuelta) {
        this.fecha = fecha;
        this.kilometrosInicial = kilometrosInicial;
        this.kilometroFinal = kilometroFinal;
        this.totalKilometrajeVuelta = totalKilometrajeVuelta;
        this.kmsEnUltimaViajeRealizado = 0.0;
        this.diferencia = 0.0;
        Remiseria.persistencia.insert(this);
    }

    public double getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(double diferencia) {
        this.diferencia = diferencia;
    }

    public double getKmsEnUltimaViajeRealizado() {
        return kmsEnUltimaViajeRealizado;
    }

    public void setKmsEnUltimaViajeRealizado(double kmsEnUltimaViajeRealizado) {
        this.kmsEnUltimaViajeRealizado = kmsEnUltimaViajeRealizado;
    }


    public int getIdKilometrosRecorridos() {
        return idKilometrosRecorridos;
    }

    public void setIdKilometrosRecorridos(int idKilometrosRecorridos) {
        this.idKilometrosRecorridos = idKilometrosRecorridos;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getKilometrosInicial() {
        return kilometrosInicial;
    }

    public void setKilometrosInicial(int kilometrosInicial) {
        this.kilometrosInicial = kilometrosInicial;
    }

    public int getKilometroFinal() {
        return kilometroFinal;
    }

    public void setKilometroFinal(int kilometroFinal) {
        this.kilometroFinal = kilometroFinal;
    }

    public int getTotalKilometrajeVuelta() {
        return totalKilometrajeVuelta;
    }

    public void setTotalKilometrajeVuelta(int totalKilometrajeVuelta) {
        this.totalKilometrajeVuelta = totalKilometrajeVuelta;
    }
    
}
