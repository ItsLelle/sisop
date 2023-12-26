package cinqueFilosofiSemaphore;

import java.util.concurrent.TimeUnit;

public class Filosofi extends Thread{
    private Tavolo tavolo;
    private int posizione;

    public Filosofi(Tavolo t, int p){
        tavolo = t;
        posizione = p;
    }

    @Override
    public void run() {
        try{
            while (true) {
                tavolo.prendiBacchetta(posizione);
                System.out.format("Il filosofo %d ha iniziato a mangiare", posizione);
                mangia();
                System.out.format("Il filosofo %d ha finito di mangiare", posizione);
                tavolo.rilasciaBacchetta(posizione);
                pensa();
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void mangia() {
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void pensa(){
        try{
            TimeUnit.SECONDS.sleep(10);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    
}
