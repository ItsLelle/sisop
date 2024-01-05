package appelloTrenino;
import java.util.concurrent.TimeUnit;

public class Impiegato extends Thread {
    private Trenino trenino;
    private int tempoGiroCompleto = 10;
    private  int tempoScattoAvanti = 30;

    public Impiegato(Trenino trenino, int tempoGiroCompleto, int tempoScattoAvanti){
        this.trenino = trenino;
        this.tempoGiroCompleto = tempoGiroCompleto;
        this.tempoScattoAvanti = tempoScattoAvanti;
    }

    @Override
    public void run() {
        try{
            while (true) {
                trenino.impFaiScendere();
                trenino.impFaiSalire();
                trenino.impMuovi();
                attendi(tempoScattoAvanti * tempoGiroCompleto);            
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
    }

    private void attendi(int tempoScattoAvanti) throws InterruptedException{
        TimeUnit.SECONDS.sleep(tempoScattoAvanti);
    }
    
}
