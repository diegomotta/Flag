/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.persistence.Transient;

/**
 *
 * @author Owner
 */
public class Utilidad {
    static long  milisegundos_dia = 24 * 60 * 60 * 1000;
    public Utilidad() {
    }
    
    
    public Date getDate(String date){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return df.parse(date);
        } catch (ParseException ex) {
        }
        return null;
    }
    
    public Date getHour(String time){
        DateFormat df = new SimpleDateFormat("HH:mm");
        try {
            return df.parse(time);
        } catch (ParseException ex) {
        }
        return null;
    }
    
    public String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        return formateador.format(ahora);
    }
    
    public String getHoraActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        return formateador.format(ahora);
    }
    
    public Date getFechaHoraActual(){
        Date ahora = new Date();
        return ahora;
        
    }
    
    public String getHora(Date date) {
        SimpleDateFormat formateador = new SimpleDateFormat("HH:mm");
        return formateador.format(date);
    }
    
    public String getFecha(Date date) {
        DateFormat formateador = new SimpleDateFormat("dd-MM-yyyy");
        String valor = formateador.format(date);
        return valor;       
    }
    
     public int calcularEdad(String fecha)
    {
       Date fechaNac=null;
       try
       {
          /*Se puede cambiar la mascara por el formato de la fecha que se
	  //quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
	  en este caso es día mes año*/
          fechaNac = new SimpleDateFormat("dd-MM-yyyy").parse(fecha);
       } 
       catch (Exception ex) 
       {
            System.out.println("Error:"+ex);
       }
       Calendar fechaNacimiento = Calendar.getInstance();
       //Se crea un objeto con la fecha actual
       Calendar fechaActual = Calendar.getInstance();
       //Se asigna la fecha recibida a la fecha de nacimiento.
       fechaNacimiento.setTime(fechaNac);
       //Se restan la fecha actual y la fecha de nacimiento
       int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
       int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
       int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
       //Se ajusta el año dependiendo el mes y el día
       if(mes<0 || (mes==0 && dia<0))
       {
	    año--;
           
       }
       //Regresa la edad en base a la fecha de nacimiento
       System.out.println(año);
       return año;
      
    }
    
    public boolean isNumber(String string)
    {
        try{ 
            Long.parseLong(string);
        }
        catch(Exception e)
        {
            return false;
        }
        return true;
    }
    
    public boolean isDouble (String string)
    {
        try 
        {
            Double.parseDouble(string);
            return true;
        } 
        catch (NumberFormatException exc) 
        {
            return false;
        } 
    }
    
    public double calcularPagoOperario (Date total,Date fechaInicio, Date fechaFin, Remiseria unaRemiseria,MarcarTarjeta unMarcarTarjeta)
    {  
//        Date fechaInicial= this.StringToDate(fechaAntes+" "+ horaAntes, "/", 0);//yyyy-MM-dd 
//        Date fechaFinal= this.StringToDate(fechaDespues+" "+horaDespues, "/", 0); /**Creamos una instancia de la clase calendar*/
//        Date fechaInicial=  fechaInicio;
//        Date fechaFinal= fechaFin;
//        Calendar calFechaInicial=Calendar.getInstance();
//        Calendar calFechaFinal=Calendar.getInstance();
//        calFechaInicial.setTime(fechaInicial);
//        calFechaFinal.setTime(fechaFinal);
//        System.out.println("Numero Total de Horas" + " Entre las dos Fechas: "+ cantidadTotalHoras(calFechaInicial,calFechaFinal));
//        System.out.println("Numero Total de Minutos" + " Entre las dos Fechas: "+ cantidadTotalMinutos(calFechaInicial,calFechaFinal));
//        System.out.println("Numero Total de segundos" + " Entre las dos Fechas: "+ cantidadTotalSegundos(calFechaInicial,calFechaFinal));
        int  horas= total.getHours();//diferenciaHorasDias(calFechaInicial, calFechaFinal)+diferenciaHoras(calFechaInicial,calFechaFinal); 
        int  minutos=total.getMinutes();//diferenciaMinutos(calFechaInicial,calFechaFinal);    
        /** si los minutos son iguales menores a cero hay que restarle 1 hora al total que dio las horas */
        if(minutos<0)
        {
            System.out.println("Horas: "+(horas-1)+" Minutos: " +(minutos+60));
            unMarcarTarjeta.setHoras(horas);
            unMarcarTarjeta.setMinutos(minutos);
            return (unaRemiseria.getUnPagoOperario().getPrecio()*(horas-1))+ (unaRemiseria.getUnPagoOperario().getPrecio()/60)* (minutos+60);
        } 
        else
        { 
            System.out.println("Horas: "+(horas)+" Minutos : "+minutos);
            unMarcarTarjeta.setHoras(horas);
            unMarcarTarjeta.setMinutos(minutos);
            return (unaRemiseria.getUnPagoOperario().getPrecio()*(horas))+ (unaRemiseria.getUnPagoOperario().getPrecio()/60)* (minutos);
        } 
    
    }
    
    
    public static long  diferenciaHorasDias(Calendar fechaInicial ,Calendar fechaFinal)
    { 
    //Milisegundos al día 
        long  diferenciaHoras=0; //Restamos a la fecha final la fecha inicial y lo dividimos entre el numero de milisegundos al dia 
        diferenciaHoras=(fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/milisegundos_dia; 
        if(diferenciaHoras>0)
        { 
    // Lo Multiplicaos por 24 por que estamos utilizando el formato militar 
            diferenciaHoras*=24;
        } 
        return diferenciaHoras; 
    }
    /*Metodo que calcula la diferencia de los minutos entre dos fechas*/    
    public static long  diferenciaMinutos(Calendar fechaInicial ,Calendar fechaFinal)
    {
        long  diferenciaHoras=0;
        diferenciaHoras=(fechaFinal.get(Calendar.MINUTE)-fechaInicial.get(Calendar.MINUTE));
        return diferenciaHoras;
    }    
        /*Metodo que devuelve el Numero total de minutos que hay entre las dos Fechas */
    public static long  cantidadTotalMinutos(Calendar fechaInicial ,Calendar fechaFinal)
    {  
        long  totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60);
        return totalMinutos;
    }
    /*Metodo que devuelve el Numero total de horas que hay entre las dos Fechas */
    public static long  cantidadTotalHoras(Calendar fechaInicial ,Calendar fechaFinal)
    {  
        long  totalMinutos=0 ;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000/60/60);
        return totalMinutos; 
    }

    /*Metodo que devuelve el Numero total de Segundos que hay entre las dos Fechas */
    public static long  cantidadTotalSegundos(Calendar fechaInicial ,Calendar fechaFinal)
    { 
        long  totalMinutos=0;
        totalMinutos=((fechaFinal.getTimeInMillis()-fechaInicial.getTimeInMillis())/1000);
        return totalMinutos;
    }

    /*Metodo que calcula la diferencia de las horas entre dos fechas*/
    public static long  diferenciaHoras(Calendar fechaInicial ,Calendar fechaFinal)
    { 
        long  diferenciaHoras=0;
        diferenciaHoras=(fechaFinal.get(Calendar.HOUR_OF_DAY)-fechaInicial.get(Calendar.HOUR_OF_DAY)); 
        return diferenciaHoras; 
    }


    public static String DateToString(Date fecha,String caracter,int  op)
    {   
        String formatoHora=" HH:mm:ss";//Formato de hora //caracter hace referencia al separador / - 
        String formato="yyyy"+caracter+"MM"+caracter+"dd"+formatoHora; 
        if(op==1) // 
            formato="yyyy"+caracter+"dd"+caracter+"MM"+formatoHora;
        else if(op==2) 
            formato="MM"+caracter+"yyyy"+caracter+"dd"+formatoHora; 
        else if(op==3) formato="MM"+caracter+"dd"+caracter+"yyyy"+formatoHora;

        else if(op==4) formato="dd"+caracter+"yyyy"+caracter+"MM"+formatoHora; 
        else if(op==5) formato="dd"+caracter+"MM"+caracter+"yyyy"+formatoHora;  
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
        String fechaFormato=null;   sdf.setLenient(false); 
        fechaFormato=sdf.format(fecha);  return fechaFormato; 
    }

    /*Convertir fecha date en string*/
    public static Date StringToDate(String fecha,String caracter,int  op)
    {   
        String formatoHora=" HH:mm:ss"; 
        String formato="yyyy"+caracter+"MM"+caracter+"dd"+formatoHora;
        if(op==1) // 
        formato="yyyy"+caracter+"dd"+caracter+"MM"+formatoHora; 
        else if(op==2) formato="MM"+caracter+"yyyy"+caracter+"dd"+formatoHora;
        else if(op==3) formato="MM"+caracter+"dd"+caracter+"yyyy"+formatoHora; 
        else if(op==4) formato="dd"+caracter+"yyyy"+caracter+"MM"+formatoHora; 
        else if(op==5) formato="dd"+caracter+"MM"+caracter+"yyyy"+formatoHora; 
        SimpleDateFormat sdf = new SimpleDateFormat(formato, Locale.getDefault());
        Date fechaFormato=null; 
        try 
        { 
            sdf.setLenient(false); 
            fechaFormato=sdf.parse(fecha);
        } 
        catch (ParseException ex)
        {  
        } 
        return fechaFormato; 
    }
    

    public String compararFechasConDate(Date fechaIngresada)
    {  
        Date fechaActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
        String fechaSistema=formateador.format(fechaActual);
        String resultado="";
        try 
        {
            /**Obtenemos las fechas enviadas en el formato a comparar*/
            Date fechaDate1 = fechaIngresada;
            Date fechaDate2 = formateador.parse(fechaSistema);
            if ( fechaDate1.before(fechaDate2) )
            {
                resultado= "Menor";
            }
            else
            {
                if ( fechaDate2.before(fechaDate1) )
                {
                   resultado= "Mayor";
                }
                else
                {
                   resultado= "Igual";
                } 
            }
        }
        catch (ParseException e) 
        {
             System.out.println("Se Produjo un Error!!!  "+e.getMessage());
        }  
        return resultado;
     }    
    
    
    public String compararHoras(Date horaInicial , Date horaFinal)
    {
        long hora1 = horaInicial.getTime();
        long hora2 = horaFinal.getTime();
        if(hora1 < hora2)
        {
            return "Menor";
        }
        else if (hora1 == hora2)
        {
            return "Igual";
        }
        else if(hora1 > hora2)
        {
            return "Mayor";
        }
        return null;
    }

}
