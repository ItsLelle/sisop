package cinqueFilosofiSemaphore;

import java.util.concurrent.Semaphore;

public class TavoloSemaphore extends Tavolo{
    Semaphore mutex = new Semaphore(1);//uso il mutex per gestire la presa della bacchetta
    Semaphore bacchetta[] = new Semaphore[numFilosofi];//un array di semafori per le bacchette pari a 5 che è il numero dei filosofi


    public TavoloSemaphore(int numFilosofi){
        super();
        for(int i = 0; i <numFilosofi; i++ ){
            bacchetta[i] = new Semaphore(1);
        }
    }

    @Override
    public void prendiBacchetta(int i) throws InterruptedException {
        mutex.acquire();//il mutex mi aiuta a gestire l'acquisizione della bacchetta
        bacchetta[i].acquire();//dx
        bacchetta[(i+1)%numFilosofi].acquire();//sx
        mutex.release();//qui rilascio
    }

    @Override
    public void rilasciaBacchetta(int i) throws InterruptedException {
        bacchetta[i].release();
        bacchetta[(i+1)%numFilosofi].release();
    }

    public static void main(String[] args) {
        int numFilosofi = 5;
        TavoloSemaphore t = new TavoloSemaphore(numFilosofi);
        Filosofi[]filosofi = new Filosofi[numFilosofi];
        for(int i = 0; i < numFilosofi; i++){
            filosofi[i] = new Filosofi(t, i);
            filosofi[i].start();
        }
        for(Thread f: filosofi){
            try{
                f.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
