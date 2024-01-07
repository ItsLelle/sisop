package appelloCaselloSettembre2011;


import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Veicolo extends Thread{
    private CaselloAutostradale casello;
    private Random r = new Random();
    private float tariffa;
    private int km;

    public Veicolo(CaselloAutostradale casello){
        this.casello = casello;
    }

    @Override
    public void run() {
        try{
            casello.percorri(r.nextInt(100 - 50 + 1)+50);
            TimeUnit.SECONDS.sleep(40);
            int porta = casello.scegli();
            casello.accedi(porta);
            casello.paga(km, tariffa);
            TimeUnit.SECONDS.sleep(r.nextInt(6 - 3 + 1)+3);
            casello.rilascia(porta);
        }catch(InterruptedException e){
        }
    }
    
}
