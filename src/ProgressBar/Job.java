package ProgressBar;
/**
 * @web http://www.jc-mouse.net
 * @author Mouse
 */
public class Job implements Runnable{
    
    public static boolean band=false;    
    private int tiempo ;
    
    /**
     * Constructor de clase
     */
    public Job( int tiempo ){
        this.tiempo = tiempo;
    }
    
    @Override
    public void run() {
        band=false;
        for (int i=1; i <= this.tiempo ; i++){
           // System.out.println("Haciendo algo divertido... -> " + i + " segundo transcurrido.");
            try{Thread.sleep( 1000 );}
            catch (InterruptedException e){
                System.err.println( e.getMessage() );
            }
        }
        band=true;
    }
    
}
