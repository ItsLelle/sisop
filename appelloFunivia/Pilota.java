package appelloFunivia;

import java.util.concurrent.TimeUnit;

public class Pilota extends Thread {
    private Funivia funivia;

    private int tempoSalita = 5;
    private int tempoDIscesa = 2;


    public Pilota(Funivia funivia){
        this.funivia = funivia;
    }

    @Override
    public void run() {
        try{
            while (true) {
                funivia.pilotaStart();
                attendi(tempoSalita);
                funivia.pilotaEnd();
                attendi(tempoDIscesa);
                
            }
        }catch(InterruptedException e ){
            e.printStackTrace();
        }
    }

    public void attendi(int tempo)throws InterruptedException{
        TimeUnit.SECONDS.sleep(tempo);

    }
    
}
