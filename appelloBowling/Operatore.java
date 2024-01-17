package appelloBowling;

import java.util.concurrent.TimeUnit;

public class Operatore extends Thread {
    private SalaBowling sala;
    private int maxTempo = 10;
    private int minTempo = 5;


    public Operatore(SalaBowling sala){
        this.sala = sala;
    }

    @Override
    public void run() {
        try{
            sala.preparaPartita();
            prepara();
            sala.deposita();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
    }

    private void prepara()throws InterruptedException {
       TimeUnit.SECONDS.sleep((maxTempo - minTempo + 1)+ minTempo);
    }
    
}
