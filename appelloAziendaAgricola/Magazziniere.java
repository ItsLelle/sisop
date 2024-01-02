package appelloAziendaAgricola;

import java.util.concurrent.TimeUnit;

public class Magazziniere extends Thread {
    private AziendaAgricola aziendaAgricola;

    public Magazziniere(AziendaAgricola aziendaAgricola){
        this.aziendaAgricola = aziendaAgricola;
    }
    

    @Override
    public void run() {
        while(true){
            try{
                aziendaAgricola.carica();
                TimeUnit.MINUTES.sleep(10);
                System.out.println(this);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public String toString(){
        return "Magazzienire ha caricato "+aziendaAgricola.sacchettiIniziali+ " sacchetti nel magazzino";
    }
}
