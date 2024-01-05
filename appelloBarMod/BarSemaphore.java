package appelloBarMod;

import java.util.concurrent.Semaphore;

public class BarSemaphore extends Bar {

    private Semaphore mutex = new Semaphore(1);
    private Semaphore[] fila = new Semaphore[numFile];

    protected int[]numPersoneFila = new int[numFile];

    public BarSemaphore(){
        super();
        for(int i = 0; i < numFile; i++){
            fila[i] = new Semaphore(numPersoneFila[i], true);
            numPersoneFila[i] = 0; //all'inizio nessuna persona è in fila

        }
        System.out.println(this);
    }

    @Override
    protected int scegli() throws InterruptedException {
       int i = -1; //0
       mutex.acquire();

       if(numPostiLiberi[cassa] > 0)
        i = cassa;
       else if(numPersoneFila[bancone]>0){
        i = bancone;
       }else if(numPersoneFila[cassa] <= numPersoneFila[bancone]){
        i = cassa;
       }else{
        i = bancone;
       }
       System.out.format("Persona[%s] vuole andare %s%n", Thread
				.currentThread().getName(), (i == cassa ? "in CASSA"
				: "al BANCONE"));
        mutex.release();
        return i;
    }

    @Override
    protected void inizia(int i) throws InterruptedException {
        mutex.acquire();
        numPersoneFila[i]++;
        mutex.release();

        fila[i].acquire();
        mutex.acquire();
        numPersoneFila[i]--;
        numPostiLiberi[i]--;
        mutex.release();
    }

    @Override
    protected void finisci(int i) throws InterruptedException {
       mutex.acquire();
       numPostiLiberi[i]++;
       fila[i].release();
       System.out.format("Persona[%s] è uscita %s%n", Thread.currentThread().getName(), (i == cassa ? "dalla CASSA" : "dal BANCONE"));
	   System.out.println(this);
       mutex.release();
    }

    public static void main(String[] args) throws InterruptedException{
        Bar bar = new BarSemaphore();
        int numPersone = 100;
        bar.test(numPersone);
    }
    
}
