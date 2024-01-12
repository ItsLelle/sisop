package appelloTrenino;

import java.util.concurrent.TimeUnit;

public class Turista extends Thread {
    private Trenino trenino;
    protected final int tempoGiroCompleto = 1;
    protected final int tempoScattoAvanti = 3;

    public Turista(Trenino trenino){
        this.trenino = trenino;
    }

    @Override
    public void run() {
        try{
            trenino.tourSali();
            TimeUnit.SECONDS.sleep(tempoScattoAvanti*tempoGiroCompleto);
            trenino.tourScendi();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    
}
