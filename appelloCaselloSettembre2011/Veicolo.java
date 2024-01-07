package appelloCaselloSettembre2011;


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Veicolo extends Thread{
    private CaselloAutostradale casello;
    private Random r = new Random();
   
    
    public Veicolo(CaselloAutostradale casello){
        this.casello = casello;
    }

    @Override
    public void run() {
        try{
            int km = r.nextInt(100 - 50 +1) +50;
            attendi1(km*40);

            //si accoda sulla porta
            int porta = r.nextInt(casello.getNumPorte());
            System.out.format("veicolo %d è entrato nella porta %d",Thread.currentThread().getId(), porta); 

            //entra nel casello
            casello.accedi(porta);
            System.out.format("veicolo %d è entrato nella porta %d%n",Thread.currentThread().getId(), km, porta);

            attendi(50,100);

            casello.paga(porta, km);
            System.out.format("veicolo %d paga e abbandona la porta %d%n",Thread.currentThread().getId(), porta);
        }catch(InterruptedException e){
        }
    }

    private void attendi1(int i) throws InterruptedException {
        TimeUnit.SECONDS.sleep(i);
    }

    private void attendi(int i, int j) throws InterruptedException {
        Thread.sleep(i,j);
    }
    
}
