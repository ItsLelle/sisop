package esercitazioni.barbiereAddormentato;

import java.util.concurrent.Semaphore;

public class SalaSemaphore extends Sala{

    Semaphore mutex = new Semaphore(1);
    Semaphore clienteDisponibile = new Semaphore(0,true);//significa che vale la coda fifo
    Semaphore poltrona = new Semaphore(0,true);

    public SalaSemaphore(int sedie) {
        super(sedie);
    }

    @Override
    public void tagliaCapelli() throws InterruptedException {
        clienteDisponibile.acquire();
        poltrona.release();
    }

    @Override
    public boolean attendiTaglio() throws InterruptedException {
        mutex.acquire();
        if(sedieLibere == 0){//se non ci sono sedie libere rilascio il mutex
            mutex.release();
            return false;
        }//se le sedie ci sono, si siede e decrementano i posti liberi
        sedieLibere--;
        mutex.release();//rilascia il mutex e il cliente disponibile
        clienteDisponibile.release();
        poltrona.acquire();//si acquisisce la poltrona perchè si sedie il cliente
        mutex.acquire();//acquisisco il mutex e le sedie libere incrementano avendo servito un cliente
        sedieLibere++;
        mutex.release();//rilascio il mutex
        return true;
    }

    public static void main(String[] args) {
        try{
            Sala s = new SalaSemaphore(5);
            s.test(30);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
}
