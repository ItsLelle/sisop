package appelloTrenino;

import java.util.concurrent.Semaphore;

public class TreninoSem extends Trenino {
    private int numPostiCabina = 10;
    private int numPostiOccupati = 0;
    private int tempoScattoAvanti = 30;

    private Semaphore[] cabine = new Semaphore[10];
    private Semaphore mutex = new Semaphore(1);
    private Semaphore permettiScattoCabina = new Semaphore(0);

    private Semaphore permettiSalitaTurista = new Semaphore(0);
    private Semaphore permettiDiscesaTurista = new Semaphore(0);

    private Semaphore permettiSalitaImp = new Semaphore(0);
    private Semaphore permettiDiscesaImp = new Semaphore(0);
    private Semaphore permettiPartenzaImp = new Semaphore(0);

    @Override
    void tourSali() throws InterruptedException {
        for(int i = 0; i < cabine.length; i++){
            if(numPostiOccupati == 0){
                permettiSalitaTurista.acquire();
                mutex.acquire();
                numPostiOccupati++;
                if(numPostiOccupati == numPostiCabina){
                    permettiPartenzaImp.release();
                }
                mutex.release();
            }
        }      
    }

    @Override
    void tourScendi() throws InterruptedException {
       for(int i = 0; i < cabine.length; i++){
        if(numPostiOccupati == numPostiCabina){
            permettiDiscesaTurista.acquire();
            mutex.acquire();
            numPostiOccupati--;
            if (numPostiOccupati == 0){
                permettiDiscesaImp.release();
            }
            mutex.release();
        }
       }
    }

    @Override
    void impFaiScendere() throws InterruptedException {
       for(int i = 0; i < cabine.length; i++){
        if(numPostiOccupati > 0){
            permettiDiscesaImp.acquire();
            mutex.acquire();
            numPostiOccupati--;
            if(numPostiOccupati == 0){
                permettiPartenzaImp.release();
            }
            mutex.release();
        }
       }
    }

    @Override
    void impFaiSalire() throws InterruptedException {
       for(int i = 0; i < cabine.length; i++){
        if(numPostiOccupati == 0){
            permettiSalitaImp.acquire();
            mutex.acquire();
            numPostiOccupati++;
            if (numPostiCabina == numPostiOccupati){
                permettiSalitaImp.release();
            }
            mutex.release();
        }
      }
    }

    @Override
    void impMuovi() throws InterruptedException {
        for(int i = 0; i < cabine.length; i++){
            permettiScattoCabina.release(tempoScattoAvanti);
        }
    }

    public static void main(String[] args) {
        Trenino trenino = new TreninoSem();
        trenino.test(10, 10);
    }
}
