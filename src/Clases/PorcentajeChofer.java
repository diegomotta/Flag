/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class PorcentajeChofer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPorcentaje;
    private double porcentajeChofer;

    public PorcentajeChofer() {
    }

    public PorcentajeChofer(double porcentajeChofer) {
        this.idPorcentaje = 0;
        this.porcentajeChofer = porcentajeChofer;

        Remiseria.persistencia.insert(this);
    }

    public int getIdPorcentaje() {
        return idPorcentaje;
    }

    public void setIdPorcentaje(int idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public double getPorcentajeChofer() {
        return porcentajeChofer;
    }

    public void setPorcentajeChofer(double porcentajeChofer) {
        this.porcentajeChofer = porcentajeChofer;
    }

}
