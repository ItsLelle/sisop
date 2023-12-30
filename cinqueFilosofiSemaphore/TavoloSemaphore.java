package cinqueFilosofiSemaphore;

import java.util.concurrent.Semaphore;

public class TavoloSemaphore extends Tavolo{
    Semaphore mutex = new Semaphore(1);//uso il mutex per gestire la presa della bacchetta
    Semaphore bacchetta[] = new Semaphore[numFilosofi];//un array di semafori per le bacchette pari a 5 che è il numero dei filosofi


    public TavoloSemaphore(int numFilosofi){
        super();
        for(int i = 0; i <numFilosofi; i++ ){//scorro i filosofi e mi vado a creare per ogni singola bacchetta un semaforo con un permesso
            bacchetta[i] = new Semaphore(1);
        }
    }

    @Override
    public void prendiBacchetta(int i) throws InterruptedException {
        mutex.acquire();//il mutex mi aiuta a gestire l'acquisizione della bacchetta
        bacchetta[(i+1)%numFilosofi].acquire();//sx
        bacchetta[i].acquire();//dx
        mutex.release();//qui rilascio
    }

    @Override
    public void rilasciaBacchetta(int i) throws InterruptedException {
        bacchetta[(i+1)%numFilosofi].release();
        bacchetta[i].release();//rilascio le bacchette dx e sx
        
    }

    public static void main(String[] args) {
        int numFilosofi = 5;
        TavoloSemaphore t = new TavoloSemaphore(numFilosofi);
        Filosofi[]filosofi = new Filosofi[numFilosofi];//mi creo un array di filosofi
        for(int i = 0; i < numFilosofi; i++){//scorro i filosofi
            filosofi[i] = new Filosofi(t, i);//mi creo il filosofo grazie al tavolo e alla posizione
            filosofi[i].start();//parte il thread
        }
        for(Thread f: filosofi){//per ogni filosofo thread
            try{
                f.join();//joina così che ogni filosofo debba finire prima di poter far iniziare un altro a mangiare
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
