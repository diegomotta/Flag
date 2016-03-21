/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Lote implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLote;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaLote;
    private Double saldoInicial;
    private Double saldoFinal;
    private String horaApertura;
    private String horaCierre;
    private Double totalRetirado;
    @OneToMany
    private Map<Integer,Movimiento> movimientos;
    private boolean cerrado;

    public Lote() {
    }

    public Lote(Double saldoInicial, Double saldoFinal, String horaApertura, String horaCierre, Double totalRetirado, boolean estaCerrado) {
        this.idLote = 0;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
        this.horaApertura = horaApertura;
        this.horaCierre = horaCierre;
        this.totalRetirado = totalRetirado;
        this.movimientos = new HashMap();
        this.fechaLote = new Date();
        this.cerrado = false;

        Remiseria.persistencia.insert(this);
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }

    public Date getFechaLote() {
        return fechaLote;
    }

    public void setFechaLote(Date fechaLote) {
        this.fechaLote = fechaLote;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public Double getTotalRetirado() {
        return totalRetirado;
    }

    public void setTotalRetirado(Double totalRetirado) {
        this.totalRetirado = totalRetirado;
    }

    public Map<Integer, Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Map<Integer, Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public Double getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(Double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public Double getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(Double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
//Date fecha_dia, String hora_mov, Double entrada, Double salida, String tipo, Conceptos concepto

    public void altaMovimiento(Date fecha_dia, String hora_mov, Double importe, String tipo, String concepto, String fuente) {
        Movimiento unMovimiento = new Movimiento(fecha_dia, hora_mov, importe, tipo, concepto, fuente);
        this.movimientos.put(unMovimiento.getIdMovimiento(),unMovimiento);
        Remiseria.persistencia.update(this);

    }
}
