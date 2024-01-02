package appelloAziendaAgricola;

import java.util.concurrent.Semaphore;

public class AziendaAgricolaSem  extends AziendaAgricola{
    Semaphore cassa = new Semaphore(1,true);
    Semaphore magazzino;
    Semaphore magazziniere = new Semaphore(0);
    Semaphore mutex = new Semaphore(1);

    public AziendaAgricolaSem(int sacchettiIn) {
        super(sacchettiIn);
        magazzino = new Semaphore(sacchetti,true);
    }

    @Override
    public void paga(int numSacchetti) throws InterruptedException {
        cassa.acquire();
        incasso += numSacchetti * 3;
        cassa.release();
    }

    @Override
    public void ritira() throws InterruptedException {
        magazziniere.acquire();
        mutex.acquire();
        sacchetti--;
        if(sacchetti == 0){
            magazziniere.release();
        }
        System.out.println("Sacchetti: "+sacchetti);
        mutex.release();
    }

    @Override
    public void carica() throws InterruptedException {
        magazziniere.acquire();
        mutex.acquire();
        sacchetti += sacchettiIniziali;
        System.out.println("sacchetti "+sacchetti);
        mutex.release();
        magazziniere.release(200);
    }

    public int getIncasso(){
        return incasso;
    }

    public static void main(String[] args) {
        AziendaAgricola aziendaAgricola = new AziendaAgricolaSem(200);
        int numClienti = 100;
        aziendaAgricola.test(numClienti);
    }
    
}
