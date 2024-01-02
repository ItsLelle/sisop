package appelloFunivia;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FuniviaLC extends Funivia{

    private int numPostiOccupati = 0;
    private int viaggio = 0;

    private boolean permessoEntrata = false;
    private boolean permessoUscita = false;

    private ArrayList<Long> iDTuristi = new ArrayList<>();

    private Lock l = new ReentrantLock();
    private Condition permettiEntrataBici = l.newCondition();
    private Condition permettiEntrataPiedi = l.newCondition();
    private Condition uscitaBici = l.newCondition();
    private Condition uscitaPiedi = l.newCondition();
    
    private Condition parti = l.newCondition();
    private Condition fermo = l.newCondition();



    @Override
    public void pilotaStart() throws InterruptedException {
        l.lock();
        try{
            permessoEntrata = true;
            if(viaggio %2 == 0){
                permettiEntrataPiedi.signalAll();
            }else{
                permettiEntrataBici.signalAll();
            }
            while(!possoIniziareIlViaggio()){
                parti.await();
            }


        }finally{
            l.unlock();
        }
    }

    private boolean possoIniziareIlViaggio(){
        return postiLiberi == numPostiOccupati;

    }

    @Override
    public void pilotaEnd() throws InterruptedException {
       l.lock();
       try{
        System.out.println("Viaggio n. " +viaggio);
        System.out.println("Id turisti presenti: "+iDTuristi);

        for(int i = 0; i < iDTuristi.size(); i++){
            System.out.println("Id turisti: "+iDTuristi.get(i));

        }
        permessoUscita = true;
        if(viaggio % 2 == 0){
            permettiEntrataPiedi.signalAll();
        }else{
            permettiEntrataBici.signalAll();
        }
        while (!possoFinireIlViaggio()) {
            fermo.await();
            
        }
        iDTuristi.clear();
        viaggio++;

       }finally{
        l.unlock();
       }
    }

    private boolean possoFinireIlViaggio() {
        return numPostiOccupati == 0;
    }

    @Override
    public void turistaSali(int t) throws InterruptedException {
        l.lock();
        try{
            if(t == turistaPiedi){
                while (!possoSalireAPiedi()) {
                    permettiEntrataPiedi.await();
                }
                numPostiOccupati++;
            }else{
                while (!possoSalireinBici()) {
                    permettiEntrataBici.await();
                }
                numPostiOccupati++;
            }
            iDTuristi.add(Thread.currentThread().getId());
            if(numPostiOccupati == postiLiberi){
                permessoEntrata = false;
                parti.signal();
            }

        }finally{
            l.unlock();
        }
    }

    private boolean possoSalireinBici() {
        return (permessoEntrata && viaggio % 2 == 1 && numPostiOccupati < postiLiberi);
    }

    private boolean possoSalireAPiedi() {
        return (permessoEntrata && viaggio % 2 == 0 && numPostiOccupati < postiLiberi);
    }

    @Override
    public void turistaScendi(int t) throws InterruptedException {
        l.lock();
        try{
            if(t == turistaPiedi){
                while(!possoscenderePiedi()){
                    uscitaPiedi.await();
                }
                numPostiOccupati--;
            }else{
                while (!possoScendereBici()) {
                    uscitaBici.await();
                }
                numPostiOccupati-=2;
            }

            if ( numPostiOccupati == 0){
                permessoUscita = false;
                fermo.signal();
            }

        }finally{l.unlock();}
    }

    private boolean possoscenderePiedi() {
        return (permessoUscita && viaggio%2 == 0 && numPostiOccupati > 0);
    }

    private boolean possoScendereBici() {
        return (permessoUscita && viaggio%2 == 1 && numPostiOccupati > 0);
    }
    

    public static void main(String[] args) {
        Funivia funivia = new FuniviaLC();
        int numPersoneBici = 3;
        int numPersonePiedi = 6;
        funivia.test(numPersoneBici, numPersonePiedi);
    }
}
