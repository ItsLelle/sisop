package appelloBarMod;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Persona implements Runnable {
    private Bar bar;
    private Random random = new Random();

    private static final int[] minAttesa = {5,20};
    private static final int[] maxAttesa = {10,40};
    

    public Persona(Bar bar){
        this.bar = bar;
    }

    @Override
    public void run() {
        try{
            attendi(0,20);
            int operazione = bar.scegli();

            bar.inizia(operazione);
            attendi(minAttesa[operazione], maxAttesa[operazione]);
            bar.finisci(operazione);

            operazione = 1 - operazione;//cambio operazione
            bar.inizia(operazione);
            attendi(minAttesa[operazione], maxAttesa[operazione]);
            bar.finisci(operazione);
        }catch(InterruptedException e ){
        }
    }


    public void attendi(int min, int max) throws InterruptedException{
        TimeUnit.SECONDS.sleep(random.nextInt(max - min+1)+min);
    }
}
