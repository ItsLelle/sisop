package appelloGarascii;


import java.util.*;
import java.util.concurrent.TimeUnit;

public class Sciatore extends Thread {
    private Gara gara;
    private final int idMaglia;
    private Random r = new Random();
    private final int maxAttesa = 7;
    private final int minAttesa = 5;
    private int durata = 0;


    public Sciatore(Gara gara, int idMaglia){
        this.gara = gara;
        this.idMaglia = idMaglia;
    }

    public int getNumeroMaglia(){
        return this.idMaglia;
    }
    

    @Override
    public void run() {
        try{
            tempoImpiegato();
            gara.partenza(this);
            attendi();
            gara.arrivo(this);

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void attendi() throws InterruptedException{
        TimeUnit.SECONDS.sleep(durata);
    }

    private void tempoImpiegato() throws InterruptedException{
        durata = r.nextInt((maxAttesa - minAttesa + 1)+minAttesa);
    }

    public int getAttesa(){
        return durata;
    }

}
