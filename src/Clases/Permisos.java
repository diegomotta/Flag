/*
 * To change this template, choose Tools | Templates
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
public class Permisos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int idPermiso;
    
    public final static String pUsuarios = "permitirGestionDeUsuarios";
    public final static String pChoferes = "permitirGestionDeChoferes";
    public final static String pMoviles = "permitirGestionDeMoviles";
    public final static String pTarifas = "permitirGestionDeTarifas";
    public final static String pZonas = "permitirGestionDeZonas";
    public final static String pViajes = "permitirGestionDeViajes";
    public final static String pClientes = "permitirGestionDeClientes";
    public final static String pCaja = "permitirGestionDeCaja";
    public final static String pLogs = "permitirGestionDeLogs";
    public final static String pRespaldo = "permitirGestionDeRespaldo";
    public final static String pCargo = "permitirGestionDeCargos";
    private String tipo;
    
    public Permisos(){}

    public Permisos(String tipo) {
        this.tipo = tipo;
        Remiseria.persistencia.insert(this);

    }

    public static String getpCargo() {
        return pCargo;
    }

    public int getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public static String getpChoferes() {
        return pChoferes;
    }

    public static String getpMoviles() {
        return pMoviles;
    }

    public static String getpTarifas() {
        return pTarifas;
    }

    public static String getpUsuarios() {
        return pUsuarios;
    }

    public static String getpViajes() {
        return pViajes;
    }

    public static String getpZonas() {
        return pZonas;
    }
    
    public static String getpCaja() {
        return pCaja;
    }

    public static String getpClientes() {
        return pClientes;
    }

    public static String getpLogs() {
        return pLogs;
    }

    public static String getpRespaldo() {
        return pRespaldo;
    }
    
}
