package appelloGarascii;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Collections;

public class GaraLC extends Gara{


    private Lock l = new ReentrantLock();

    private Condition possoPartire = l.newCondition();
    private Condition possoTagliareTraguardo = l.newCondition();

    private ArrayList<Long>idMaglie = new ArrayList<>();
    private ArrayList<String>tempi = new ArrayList<>();

    private int sciatoriChePossonoPartire = 0;
    private int sciatoriChePossonoTagliareIlTraguardo = 0;

    @Override
    protected void partenza(Sciatore s) throws InterruptedException {
        l.lock();
        try{
            while(sciatoriChePossonoPartire == 0){
                possoPartire.await();
            }
            sciatoriChePossonoPartire--;
            System.out.println("Lo sciatore con maglia "+Thread.currentThread().getId()+"è partito");
            idMaglie.add(Thread.currentThread().getId());
            possoTagliareTraguardo.signal();
        }finally{
            l.unlock();
        }
    }

    @Override
    protected void arrivo(Sciatore s) throws InterruptedException {
        l.lock();
        try{
            if(sciatoriChePossonoTagliareIlTraguardo == 0){
                possoTagliareTraguardo.await();
            }
            sciatoriChePossonoTagliareIlTraguardo--;
            idMaglie.remove(Thread.currentThread().getId());
            //tempi.add(s.getPos());
            System.out.println("Classifica temporanea: "+tempi);
            possoPartire.signalAll();
        }finally{
            l.unlock();
        }
    }

    @Override
    protected boolean prossimo() throws InterruptedException {
        l.lock();
        try{
            while(idMaglie.size() == 0){
                possoPartire.await();
                return false;
            }
            possoPartire.signalAll();
            return true;

        }finally{
            l.unlock();
        }
    }

    @Override
    protected void classificaFinale() throws InterruptedException {
        Collections.sort(tempi);
        l.lock();
        try{
            while (!ciSonoSciatori()) {
                System.out.println("Classifica: "+tempi);
                

            }
            possoPartire.signalAll();

        }finally{
            l.unlock();
        }
    }

    private boolean ciSonoSciatori() {
        for(int i = 0; i < tempi.size(); i++){
            if(tempi.size() == 0){
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) throws InterruptedException{
        Gara ga = new GaraLC();
        ga.test(ga);
        
    }
    
}
