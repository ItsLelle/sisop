package appelloAziendaAgricola;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Cliente extends Thread{
    private Random random = new Random();
    private int sacchettiPrelevare = 0;
    private AziendaAgricola aziendaAgricola;
    private int id;

    public Cliente(AziendaAgricola aziendaAgricola, int id){
        this.aziendaAgricola = aziendaAgricola;
        this.id = id;
    }

    @Override
    public void run() {
        sacchettiPrelevare = random.nextInt(10)+1;
        try{
            aziendaAgricola.paga(sacchettiPrelevare);
            while(sacchettiPrelevare > 0){
                aziendaAgricola.ritira();
                System.out.println(this);
                TimeUnit.MINUTES.sleep(1);
                sacchettiPrelevare--;
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public String toString(){
        return "Cliente ["+id+"] ha prelevato 1 sacco dal magazzino, deve ancora prelevare "+sacchettiPrelevare+ "sacchetti";
    }
    
}
