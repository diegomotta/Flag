/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;
import org.hibernate.envers.Audited;

/**
 *
 * @author garba
 */
@Audited
@Entity
public class Movimiento implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMovimiento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha_dia;
    private String hora_mov;
    private Double importe;
    private static final String EGRESO = "Egreso";
    private static final String INGRESO = "Ingreso";
    private String tipo;
    private String fuente;
    private String concepto;


    public Movimiento() {
    }

    public Movimiento(Date fecha_dia, String hora_mov, Double entrada, String tipo, String concepto, String fuente) {
        //this.idMovimiento = 0;
        this.fecha_dia = fecha_dia;
        this.hora_mov = hora_mov;
        this.importe = entrada;
        this.tipo = tipo;
        this.concepto = concepto;
       
        this.fuente = fuente;
        Remiseria.persistencia.insert(this);
    }

    public Integer getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Integer idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Date getFecha_dia() {
        return fecha_dia;
    }

    public void setFecha_dia(Date fecha_dia) {
        this.fecha_dia = fecha_dia;
    }

    public String getHora_mov() {
        return hora_mov;
    }

    public void setHora_mov(String hora_mov) {
        this.hora_mov = hora_mov;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
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

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }


    
    
    @Override
    public String toString() {
        return this.getTipo();
    }

    public Object[] toVector() {
         DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        DecimalFormat formateador = new DecimalFormat("###########.##",simbolos);
        if("Egreso".equals(this.tipo))
        {
           Object fila[] = {"","$ "+ Double.parseDouble(formateador.format(this.getImporte())), this.getConcepto(), new SimpleDateFormat("dd/MM/yyyy").format(this.getFecha_dia()), this.getHora_mov()};
           return fila;
        }
        else if("Ingreso".equals(this.tipo))
        {
           Object fila[] = {"$ "+ Double.parseDouble(formateador.format(this.getImporte())), "", this.getConcepto(), new SimpleDateFormat("dd/MM/yyyy").format(this.getFecha_dia()), this.getHora_mov()};
           return fila;
        }
           return null;
    }
}
