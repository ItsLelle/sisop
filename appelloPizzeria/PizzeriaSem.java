package appelloPizzeria;

import java.util.concurrent.Semaphore;

public class PizzeriaSem extends Pizzeria {

    private final int postiSedere = 5;
    private final int filaEsterna = 0;

    private Semaphore mutex = new Semaphore(1);

    private Semaphore possoEntrare = new Semaphore(5,true);
    private Semaphore possoUscire = new Semaphore(0);

    private Semaphore permettiMangiare = new Semaphore(0);
    private Semaphore permettiPrepararePizza = new Semaphore(0);
    private Semaphore permettiServire = new Semaphore(0);


    private int[] numPersoneFila = new int[numFile];
    private int numPostiOccupati = 0;


    public PizzeriaSem(){
        super();
        for(int i = 0; i < numFile; i++){
            numPersoneFila[i] = 0;
        }
        System.out.println(this);
    }

    @Override
    protected void entra() throws InterruptedException {
        possoEntrare.acquire();
        mutex.acquire();
        numPostiOccupati++;
        System.out.println("Il cliente "+Thread.currentThread().getId()+" è entrato nella pizzeria");
        if(numPostiOccupati == 5){
            numPersoneFila[filaEsterna]++;
            permettiPrepararePizza.release();
            System.out.println("Il cliente si mette in coda");
        }
        mutex.release();
    }

    @Override
    protected void serviPizza() throws InterruptedException {
        permettiServire.acquire();
        System.out.println("la pizza è servita");
        permettiMangiare.release(5);
       
    }

    @Override
    protected void mangiaPizza() throws InterruptedException {
        permettiMangiare.acquire(5);
        mutex.acquire();
        System.out.println("Il cliente "+Thread.currentThread().getId()+" ha iniziato a mangiare");
        numPostiOccupati--;
        possoUscire.release();
        if(numPostiOccupati == 0){
            possoEntrare.release(5);
            numPersoneFila[filaEsterna]-=5;
        }
        System.out.println("Il cliente è uscito");
        mutex.release();
        
    }

    @Override
    protected void preparaPizza() throws InterruptedException {
        permettiPrepararePizza.acquire();
        mutex.acquire();
        if(numPostiOccupati == postiSedere){
            System.out.println("Il pizzaiolo sta preparando la pizza");
        }
        mutex.release();
        permettiServire.release();
    }

    public static void main(String[] args) {
        Pizzeria p = new PizzeriaSem();
        p.test(30);
    }
    
}
