package appelloCallCenter;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CallCenterLC extends CallCenter{

    private Lock l = new ReentrantLock();

    private Condition possoRichiedereAssistenza = l.newCondition();
    private Condition possoTerminareAssistenza = l.newCondition();

    private Condition possoDareAssistenza = l.newCondition();

    private boolean operatoreDisponibile = true;
    private int numClientiServiti = 0;
    private int[] personeFila = new int[numFile];
    private LinkedList<Long> idClienti = new LinkedList<>();


    public CallCenterLC(){
        for(int i = 0; i < numFile; i++){
            personeFila[i] = 0;
        }
        System.out.println(this);
    }
    
    

    @Override
    protected void richiediAssistenza() throws InterruptedException {
        l.lock();
        try{
            while(!operatoreDisponibile()){
                personeFila[filaAttuale]++;
                System.out.println("Il cliente attende in coda, operatore non disponibile");
                possoRichiedereAssistenza.await();
            }
            personeFila[filaAttuale]++;
            idClienti.addLast(Thread.currentThread().getId());
            possoDareAssistenza.signal();
        }finally{
            l.unlock();
        }
    }

    private boolean operatoreDisponibile() {
        if(operatoreDisponibile == true){
            return true;
        }
        return false;
    }

    @Override
    protected void fornisciAssistenza() throws InterruptedException {
       l.lock();
       try{
        while(!possoAssistenza()){
            possoDareAssistenza.await();
        }
        System.out.println("L'operatore ha risolto il problema e attende il cliente mentre la applica");
        operatoreDisponibile = false;
        possoTerminareAssistenza.signalAll();
       }finally{
        l.unlock();
       }
    }

    private boolean possoAssistenza() {
        if(Thread.currentThread().getId() == idClienti.getLast()){
            return true;
        }
        return false;
       
    }



    @Override
    protected void terminaChiamata() throws InterruptedException {
        l.lock();
        try{
            while (!terminato()) {
                possoTerminareAssistenza.await();
            }
            System.out.println("L'operatore ha terminato ed è nuovamente disponibile");
            operatoreDisponibile = true;
            idClienti.remove(Thread.currentThread().getId());
            possoRichiedereAssistenza.signal();
        }finally{
            l.unlock();
        }
       
    }

    private boolean terminato() {
        return operatoreDisponibile == true;
    }



    @Override
    protected void prossimoCliente() throws InterruptedException {
       l.lock();
       try{
        while (!possoNuovoCliente()) {
            possoRichiedereAssistenza.await();   
        }
        if(numClientiServiti == 15){
            System.out.println("L'operatore ha servito 15 clienti e va in pausa caffè");
            TimeUnit.SECONDS.sleep(5);
        }
        idClienti.addLast(Thread.currentThread().getId());
        personeFila[filaAttuale]--;
        possoRichiedereAssistenza.signal();
       }finally{
        l.unlock();
       }
    }



    private boolean possoNuovoCliente() {
        return operatoreDisponibile == true;
    }


    public static void main(String[] args) {
        CallCenter c = new CallCenterLC();
        c.test(5, 6);
    }
    
}
