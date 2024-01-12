package appelloPizzeria;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PizzeriaLC extends Pizzeria{
    private final int postiSedere = 5;
    private final int filaEsterna = 0;

    private Lock l = new ReentrantLock();

    private Condition possoEntrare = l.newCondition();
    private Condition possoUscire = l.newCondition();

    private Condition possoCucinare = l.newCondition();
    private Condition possoServire = l.newCondition();

    private Condition possoMangiare = l.newCondition();

    private int[] numPersoneFila = new int[numFile];
    private int numPostiOccupati = 0;

    private boolean pizzaPronta = false;

    public PizzeriaLC(){
        super();
        for(int i = 0; i < numFile; i++){
            numPersoneFila[i] = 0;
        }
        System.out.println(this);
    }

    @Override
    protected void entra() throws InterruptedException {
        l.lock();
        try{
            while (!possoEntrareC()) {
                possoEntrare.await();
                numPersoneFila[filaEsterna]++; 
                System.out.println("Il cliente attende in coda");
            }
            numPostiOccupati++;
            System.out.println("Il cliente entra e si siede al tavolo");
        }finally{
            l.unlock();
        }
    }

    private boolean possoEntrareC() {
        if(numPostiOccupati < 5){
            return true;
        }
        return false;
    }

    @Override
    protected void serviPizza() throws InterruptedException {
       l.lock();
       try{
        while(!possoServire()){
            possoServire.await();
        }
        possoMangiare.signal();
        System.out.println("Il pizzaiolo serve la maxi pizza al tavolo");
       }finally{
        l.unlock();
       }
    }

    private boolean possoServire() {
        if(pizzaPronta == true){
            return true; 
        }
        return false;
    }

    @Override
    protected void mangiaPizza() throws InterruptedException {
        l.lock();
        try{
            while(!pizzaPronta){
                possoMangiare.await();
            }
            numPostiOccupati--;
            possoUscire.signal();
            System.out.println("Il cliente ha mangiato e sta lasciando la pizzeria");
            numPersoneFila[filaEsterna]--;
            possoEntrare.signal();

        }finally{
            l.unlock();
        }
    }

    @Override
    protected void preparaPizza() throws InterruptedException {
        l.lock();
        try{
            while(!possoCucinare()){
                possoCucinare.await();
            }
            System.out.println("Il pizzaiolo sta cucinando la pizza");
            pizzaPronta = true;

        }finally{
            l.unlock();
        }
    }

    private boolean possoCucinare() {
        return numPostiOccupati == postiSedere;
    }

    public static void main(String[] args) {
        Pizzeria p = new PizzeriaLC();
        p.test(10);
    }
}
