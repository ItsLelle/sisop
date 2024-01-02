package appelloAziendaAgricola;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AziendaAgricolaLC extends AziendaAgricola {
    private Lock l = new ReentrantLock();
    private Condition paga = l.newCondition();
    private Condition preleva = l.newCondition();
    private Condition carica = l.newCondition();
    private LinkedList<Thread> cassa = new LinkedList<Thread>();
    private LinkedList<Thread> magazzino = new LinkedList<Thread>();


    public AziendaAgricolaLC(int sacchettiIn) {
        super(sacchettiIn);
    }

    @Override
    public void paga(int numSacchetti) throws InterruptedException {
        l.lock();
        try{
            cassa.add(Thread.currentThread());
            while(!possoPagare()){
                paga.await();
            }
            cassa.remove();
            incasso += numSacchetti * 3;
            paga.signalAll();

        }finally{
            l.unlock();
        }
    }

    public boolean possoPagare(){
        return Thread.currentThread() == cassa.getFirst();
    }

    @Override
    public void ritira() throws InterruptedException {
        l.lock();
        try{
            magazzino.add(Thread.currentThread());
            while(!possoRitirare()){
                preleva.await();
            }
            magazzino.remove();
            sacchetti--;
            System.out.println("Sacchetti "+sacchetti);
            if(sacchetti == 0){
                carica.signal();
            }
            preleva.signalAll();

        }finally{
            l.unlock();
        }
    }


    public boolean possoRitirare(){
        return Thread.currentThread() == magazzino.getFirst() && sacchetti > 0;
    }

    @Override
    public void carica() throws InterruptedException {
        l.lock();

        try{
            while (sacchetti > 0) {
                carica.await();
            }
            sacchetti += sacchettiIniziali;
            System.out.println("sacchetti "+sacchetti);
            preleva.signalAll();
        }finally{
            l.unlock();
        }
    }

    public static void main(String[] args) {
        AziendaAgricola aziendaAgricola = new AziendaAgricolaLC(200);
        int numClienti = 100;
        aziendaAgricola.test(numClienti);
    }
    
}
