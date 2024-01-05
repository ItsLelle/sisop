package appelloTrenino;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;

public class TreninoLC extends Trenino{
    private int numPostiCabina = 10;
    private int numPostiOccupati = 0;
    private int tempoScattoAvanti = 30;

    private boolean permessoEntrataIm = false;
    private boolean permessoUscitaIm = false;

    private Lock l = new ReentrantLock();
    private Condition permettiScattoCabina = l.newCondition();
    private Condition permettiSalitaTurista = l.newCondition();
    private Condition permettiDiscesaTurista = l.newCondition();


    private Condition permettiSalitaImp = l.newCondition();
    private Condition permettiDiscesaImp = l.newCondition();
    private Condition permettiPartenzaImp = l.newCondition();

    private LinkedList<Integer> cabine = new LinkedList<>();

    @Override
    void tourSali() throws InterruptedException {
        l.lock();
        try{
            for(int c: cabine){
                while(!PossoSalire()){
                    permettiSalitaTurista.await();
                }
                numPostiOccupati++;
            }
            if(numPostiOccupati == numPostiCabina){
                permessoEntrataIm = false;
                permettiPartenzaImp.signal();
            }

        }finally{
            l.unlock();
        }
    }

    private boolean PossoSalire() {
        return numPostiOccupati == numPostiCabina;
    }

    @Override
    void tourScendi() throws InterruptedException {
         l.lock();
        try{
            for(int c: cabine){
                while(!PossoScendere()){
                    permettiDiscesaTurista.await();
                }
                numPostiOccupati--;
            }
            if(numPostiOccupati == 0){
                permessoUscitaIm = false;
                
            }

        }finally{
            l.unlock();
        }
    }

    private boolean PossoScendere() {
        return permessoUscitaIm;
    }

    @Override
    void impFaiScendere() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'impFaiScendere'");
    }

    @Override
    void impFaiSalire() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'impFaiSalire'");
    }

    @Override
    void impMuovi() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'impMuovi'");
    }

    public void setPostiOccupati(int posti){
        numPostiOccupati = posti;

    }
    
}
