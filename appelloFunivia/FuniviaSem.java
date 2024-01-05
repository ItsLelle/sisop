package appelloFunivia;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class FuniviaSem extends Funivia{
    private int numPostiOccupati = 0;
    private int viaggio = 0;
    private Semaphore mutex = new Semaphore(1);
    private final int numPersonePiedi = 6;
    private final int numPersoneBici = 3;

    private Semaphore permettiEntrataBici = new Semaphore(0);
    private Semaphore permettiEntrataPiedi = new Semaphore(0);
    private Semaphore parti = new Semaphore(0);
    private Semaphore fermo = new Semaphore(0);
    private Semaphore uscitaPiedi = new Semaphore(0);
    private Semaphore uscitaBici = new Semaphore(0);
    private Semaphore permettiStampa = new Semaphore(0);
    private ArrayList<Long> idTuristi = new ArrayList<>();


    @Override
    public void pilotaStart() throws InterruptedException {
       viaggio++;
       if(viaggio % 2 == 0){
        permettiEntrataPiedi.release(numPersonePiedi);
       }else{
        permettiEntrataBici.release(numPersoneBici);
       }

       parti.acquire();
       permettiStampa.release();
    }

    @Override
    public void pilotaEnd() throws InterruptedException {
        permettiStampa.acquire();
		System.out.println("Viaggio numero: " + (viaggio + 1));
		System.out.print("ID turisti presenti: ");

		for (int i = 0; i < idTuristi.size(); i++) {
			System.out.print(idTuristi.get(i) + " ");
		}

		System.out.println("\n");

		if (viaggio % 2 == 0) {
			uscitaPiedi.release(turistaPiedi);
		} else {
			uscitaBici.release(turistaBici);
		}

		fermo.acquire();
		idTuristi.clear();

    }

    @Override
    public void turistaSali(int t) throws InterruptedException {
        if(t == turistaPiedi ){
            permettiEntrataPiedi.acquire();
            mutex.acquire();
            numPostiOccupati++;
            idTuristi.add(Thread.currentThread().getId());
            if(numPostiOccupati == postiLiberi){
                parti.release();
            }
            mutex.release();
        }else{
            permettiEntrataBici.acquire();
            mutex.acquire();
            numPostiOccupati++;
            idTuristi.add(Thread.currentThread().getId());
            if(numPostiOccupati == postiLiberi){
                parti.acquire();
            }
            mutex.release();
        }
    }

    @Override
    public void turistaScendi(int t) throws InterruptedException {
        if(t == turistaBici){
            uscitaBici.acquire();
            mutex.acquire();
            numPostiOccupati--;
            if(numPostiOccupati == postiLiberi){
                fermo.release();
            }
            mutex.release();
        }else{
            uscitaPiedi.acquire();
            mutex.acquire();
            numPostiOccupati--;
            if(numPostiOccupati == postiLiberi){
                fermo.release();
            }
            mutex.release();
        }
    }

    public static void main(String[] args) {
        Funivia funivia = new FuniviaSem();
        int turistaPiedi = 18;
        int turistaBici = 9;
        funivia.test(turistaPiedi, turistaBici);
    }
    
}
