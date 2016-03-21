/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.swing.JOptionPane;
import org.hibernate.envers.Audited;


/**
 *
 * @author garba
 */
@Audited
@Entity
public class Caja implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Integer idCaja;
    private String nombre;
    @OneToMany
    private Map<Integer,Lote> lotes;
    @OneToMany
    private Map<Integer,Concepto> conceptos;

//    private List<Cupon> cupones;
//
//    private List<Tarjeta> tarjetas;
//    @
//    private Map comprobantes;

    public Caja() {
    }

    public Caja(String nombre) {
        this.idCaja = 0;
        this.nombre = nombre;
        this.lotes = new HashMap();
        this.conceptos = new HashMap();
//        this.comprobantes = new HashMap();
        

        conceptos.put(0,new Concepto("Entrada", "Inicio de Caja"));
        conceptos.put(1,new Concepto("Entrada", "Pago de radio"));
        conceptos.put(2,new Concepto("Entrada", "Rendición del móvil"));

        conceptos.put(4,new Concepto("Salida", "Pago a chofer"));
        conceptos.put(5,new Concepto("Salida", "Pago a operador"));
        conceptos.put(6,new Concepto("Salida", "Pagos de servicios público"));



        Remiseria.persistencia.insert(this);
    }

    public Integer getIdCaja() {
        return idCaja;
    }

    public void setIdCaja(Integer idCaja) {
        this.idCaja = idCaja;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

//    public Map getComprobantes() {
//        return comprobantes;
//    }
//
//    public void setComprobantes(Map comprobantes) {
//        this.comprobantes = comprobantes;
//    }

    public Map<Integer, Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(Map<Integer, Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    public Map<Integer, Lote> getLotes() {
        return lotes;
    }

    public void setLotes(Map<Integer, Lote> lotes) {
        this.lotes = lotes;
    }



    private boolean existeLote(Lote unLote) {
        boolean existe = false;


        return existe;
    }


    private boolean existeConcepto(Concepto unCon) {
        boolean existe = false;
        Concepto unConcepto = null;
        Iterator iter = (Iterator) this.getConceptos().values();
        while(iter.hasNext()){
        unConcepto = (Concepto) iter.next();

            if (unCon.equals(unConcepto)) {
                existe = true;
            }
        }
        return existe;
    }

    public Concepto obtenerConcepto(String tipo, String detalle) {
        Concepto unConcepto = null;
 
        Iterator iter = (Iterator) this.getConceptos().values();
        while(iter.hasNext()){
        unConcepto = (Concepto) iter.next();
        
            if (unConcepto.getTipo().toUpperCase().equals(tipo.toUpperCase()) && unConcepto.getDetalle().toUpperCase().equals(detalle.toUpperCase())) {
                return unConcepto;
            }
        }
        return unConcepto;
    }

    public Lote abrirLote(Double saldoInicial, Double saldoFinal, String horaApertura, String horaCierre, Double totalRetirado/*, Date fechaLote*/) throws Exception {
        Lote nuevoLote = new Lote(saldoInicial, saldoFinal, horaApertura, horaCierre, totalRetirado, false);
        this.lotes.put(nuevoLote.getIdLote(),nuevoLote);
        Remiseria.persistencia.update(this);
        return nuevoLote;
    }

    public void cerrarLote(Double saldoFinal, String horaCierre, Double totalRetirado, Lote unLote/*, Date fechaLote*/) throws Exception {
        if (unLote.isCerrado() != false)
        {
            JOptionPane.showMessageDialog(null, "La suma entre el total retirado y el saldo final no puede ser distinto al final Teorico");
        }

        unLote.setHoraCierre(horaCierre);
        unLote.setSaldoFinal(saldoFinal);
        unLote.setTotalRetirado(totalRetirado);
        unLote.setCerrado(true);
        Remiseria.persistencia.update(this);

    }

    public void reabrirLote(Lote unLote) {
        if (unLote instanceof Lote) {
            unLote.setCerrado(false);
            Remiseria.persistencia.update(this);
//            setChanged();
//            notifyObservers(unLote);
        }
    }

    
          
    public Concepto agregarConcepto(String tipoConcepto, String detalleConcepto) throws Exception {
        boolean existe = false;
        Concepto nuevoConcepto = null;
        Concepto unConcepto = null;
        Collection conceptoss = this.getConceptos().values();
        Iterator iter = conceptoss.iterator();
        while (iter.hasNext()){
            unConcepto = (Concepto) iter.next();
            if (unConcepto.getDetalle().toUpperCase().equals(detalleConcepto.toUpperCase()) && unConcepto.getTipo().toUpperCase().equals(tipoConcepto.toUpperCase())) {
                existe = true;
                throw new Exception("El concepto que quiere agregar ya existe");

            }
            break;
        }
        if (!existe) {
            nuevoConcepto = new Concepto(tipoConcepto, detalleConcepto);
            this.conceptos.put(nuevoConcepto.getIdConcepto(),nuevoConcepto);
//            setChanged();
//            notifyObservers(nuevoConcepto);
            Remiseria.persistencia.update(this);
        }
        return nuevoConcepto;
    }

    public void modificarConcepto(Concepto unConcepto, String tipoConcepto, String detalleConcepto) throws Exception {
        if (!existeConcepto(unConcepto)) {
            throw new Exception("El Concepto que desea modificar no existe en el Sistema");
        } else {
            if (obtenerConcepto(tipoConcepto, detalleConcepto) != null) {
                throw new Exception("Las Modificaciones ingresadas son identicas a otro concepto cargado previamente");
            } else {
                unConcepto.setTipo(tipoConcepto);
                unConcepto.setDetalle(detalleConcepto);
                Remiseria.persistencia.update(this);
            }
        }
    }

    public boolean utilizaConcepto(Concepto unConcepto) {
        boolean utiliza = false;
        Collection losLotes = this.getLotes().values();
        Collection movim = null;
        Iterator iter = losLotes.iterator();
        
        while(iter.hasNext()){
            Lote unLote = (Lote) iter.next();
            movim =  unLote.getMovimientos().values();
            Iterator iter2 = movim.iterator();
            while(iter2.hasNext()){
              Movimiento unMovimiento = (Movimiento) iter2.next();
              if (unMovimiento.getTipo().equals(unConcepto.getTipo()) && unMovimiento.getConcepto().equals(unConcepto.getDetalle())) {
                    utiliza = true;
                }
            }
        }
       
        
        return utiliza;
    }


    public void eliminarConcepto(Concepto unConcepto)throws Exception {
        if (utilizaConcepto(unConcepto)) {
            throw new Exception("El Concepto seleccionado no puede ser Eliminado");
        } else {
           // this.getConceptos().remove(unConcepto.getIdConcepto());
            this.conceptos.remove(unConcepto.getIdConcepto());
            //Remiseria.persistencia.delete(unConcepto);
            Remiseria.persistencia.update(this);
        }
    }
         

    public Lote obtenerLote_Vigente() {
         Lote vigente = null;
        Collection lotesAnteriores = this.getLotes().values();
        //antes solamente List
        LinkedList<Lote> lotesAnteriores2 = new LinkedList();
        Iterator iter = lotesAnteriores.iterator();
        Lote lote = null;
        while(iter.hasNext()){
            lote = (Lote) iter.next();
            lotesAnteriores2.add(lote);
        }
        Collections.sort(lotesAnteriores2, new Comparator<Lote>() {
                @Override
                public int compare(Lote p1, Lote p2) {                
                        return new Long(p1.getFechaLote().getTime()).compareTo(new Long(p2.getFechaLote().getTime()));
                }
        });        
//        if (!lotesAnteriores2.isEmpty()) {
//            vigente = (Lote) lotesAnteriores2.get(lotesAnteriores2.size() - 1);
//        }
        if (!lotesAnteriores2.isEmpty()) {
            vigente = (Lote) lotesAnteriores2.getLast();
        }
        return vigente;       
//        Lote vigente = null;
//        Lote vig = null;
//        Collection lotesAnterior = this.getLotes().values();
//        List<Lote> lotesAnterioresC = new LinkedList();
//        Iterator iter = lotesAnterior.iterator();
//        while(iter.hasNext())
//        {
//            vig =(Lote) iter.next();
//            lotesAnterioresC.add(vig);
//        }
//        if(!lotesAnterioresC.isEmpty())
//        {
//             vigente =  lotesAnterioresC.get(lotesAnterioresC.size() - 1);   
//           
//        }
//        
//        return vigente; 
    }

    public Lote loteAnterior() {
        Lote anterior = null;
        List<Lote> lotesAnteriores = (List<Lote>) this.getLotes();
        if (!lotesAnteriores.isEmpty()) {
            anterior = lotesAnteriores.get(lotesAnteriores.size() - 2);
        }
        return anterior;

    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idCaja);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Caja)) {
            return false;
        }
        final Caja other = (Caja) obj;
        if (!Objects.equals(this.idCaja, other.idCaja)) {
            return false;
        }
        return true;
    }
}

