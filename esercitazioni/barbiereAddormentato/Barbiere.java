package esercitazioni.barbiereAddormentato;

import java.util.concurrent.TimeUnit;

public class Barbiere extends Thread{

    private Sala sala;

    public Barbiere(Sala s){
        sala = s;
    }
    

    @Override
    public void run() {
        while (true) {
            try{
                sala.tagliaCapelli();
                System.out.println("Taglio in corso...");
                taglio();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public void taglio() throws InterruptedException{
        TimeUnit.SECONDS.sleep(3);
    }
}
