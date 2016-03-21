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
public class ImprimirAuditoria {
     private String entityClassName;
     private String usuario;
     private String accion;
     private Date momento;  

    public ImprimirAuditoria(String entityClassName, String usuario, String accion, Date momento) {
        this.entityClassName = entityClassName;
        this.usuario = usuario;
        this.accion = accion;
        this.momento = momento;
    }

    public String getEntityClassName() {
        return entityClassName;
    }

    public void setEntityClassName(String entityClassName) {
        this.entityClassName = entityClassName;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Date getMomento() {
        return momento;
    }

    public void setMomento(Date momento) {
        this.momento = momento;
    }




    
}
