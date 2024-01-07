package appelloCaselloSettembre2011;

import java.util.concurrent.Semaphore;


public class CaselloSem extends CaselloAutostradale {

    private Semaphore[] porta = new Semaphore[nPorte];
    private Semaphore mutex = new Semaphore(1);
   
    public CaselloSem(int nPorte, int tariffa){
        super(nPorte,tariffa);
        for(int i = 0; i < nPorte; i++){
            porta[i] = new Semaphore(1, true);//fifo
        }
        System.out.println(this);
    }


    @Override
    protected void accedi(int p) throws InterruptedException {
        porta[p].acquire();
        System.out.format("il veicolo %d ha avuto accesso alla porta %d ", Thread.currentThread().getId(), p);
        System.out.println(this);
    }

    @Override
    protected void paga(int p, int km) throws InterruptedException {
       mutex.acquire();
       incasso += km * tariffa;
       System.out.println("Il veicolo "+Thread.currentThread().getId()+" ha appena pagato ");
       System.out.println(this);
       porta[p].release();
       mutex.release();
    }



    @Override
    public String toString() {
        return new String("Incasso del casello: "+incasso);
    }

    public static void main(String[] args) throws InterruptedException {
        int N = 5;
        int T = 10;
        int V = 10;

        CaselloAutostradale casello = new CaselloSem(N, T);
        casello.test(V);
       
    }
}
