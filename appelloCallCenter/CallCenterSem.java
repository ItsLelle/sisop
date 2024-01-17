package appelloCallCenter;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CallCenterSem extends CallCenter{
    private int clientiSoddisfatti = 0;

    private int[] clientiAttesa = new int[n];
    
    private Semaphore mutex = new Semaphore(1);

    private Semaphore possoRichiedereAssistenza = new Semaphore(0,true);
    private Semaphore possoTerminareAssistenza = new Semaphore(0);

    private Semaphore possoFornireAssistenza = new Semaphore(0);

     private LinkedList<Long> idClienti = new LinkedList<>();

    


    public CallCenterSem(){
        super();
        for(int i = 0; i < personeFila; i++){
            clientiAttesa[i] = 0;
        }
        System.out.println(this);
    }


    @Override
    protected void richiediAssistenza() throws InterruptedException {
        possoRichiedereAssistenza.acquire();
        mutex.acquire();
        idClienti.addLast(Thread.currentThread().getId());
        clientiAttesa[filaAttuale]++;
        System.out.println("Il cliente "+Thread.currentThread().getId()+" è entrato in coda");
        mutex.release();
        possoFornireAssistenza.release();
       
    }

    @Override
    protected void fornisciAssistenza() throws InterruptedException {
        possoFornireAssistenza.acquire();
        mutex.acquire();
        if(Thread.currentThread().getId() == idClienti.getLast()){
            System.out.println("L'operatore ha fornito assistenza");
        }
        mutex.release();
        possoTerminareAssistenza.release();
    }

    @Override
    protected void terminaChiamata() throws InterruptedException {
        possoTerminareAssistenza.acquire();
        mutex.acquire();
        clientiSoddisfatti++;
        clientiAttesa[filaAttuale]--;
        idClienti.removeLast();
        mutex.release();
        possoRichiedereAssistenza.release();
    }

    @Override
    protected void prossimoCliente() throws InterruptedException {
        mutex.acquire();
        if(clientiSoddisfatti == 15){
            System.out.println("L'operatore ha servito 15 clienti e prende una pausa caffè");
            TimeUnit.SECONDS.sleep(5);
        }
        mutex.release();
        possoRichiedereAssistenza.acquire(); 
    }

    public static void main(String[] args) {
        CallCenter c = new CallCenterSem();
        c.test(5,6);
    }

    
}
