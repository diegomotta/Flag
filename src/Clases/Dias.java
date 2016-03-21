/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Clases;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
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
public class Dias implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int idDia;
    public final static String diaLunes = "lunes";
    public final static String diaMartes = "martes";
    public final static String diaMiercoles = "miercoles";
    public final static String diaJueves = "jueves";
    public final static String diaViernes = "viernes";
    public final static String diaSabado = "sabado";
    public final static String diaDomingo = "domingo";
    private String diaSemana;


    public Dias(){}
    
    
    public Dias(String diaSemana)
    {
        this.diaSemana = diaSemana;
        Remiseria.persistencia.insert(this);
    }

    public static String getDiaLunes() {
        return diaLunes;
    }

    public static String getDiaMartes() {
        return diaMartes;
    }

    public static String getDiaMiercoles() {
        return diaMiercoles;
    }

    public static String getDiaJueves() {
        return diaJueves;
    }

    public static String getDiaViernes() {
        return diaViernes;
    }

    public static String getDiaSabado() {
        return diaSabado;
    }

    public static String getDiaDomingo() {
        return diaDomingo;
    }

    public int getIdDia() {
        return idDia;
    }

    public void setIdDia(int idDia) {
        this.idDia = idDia;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }
    



}