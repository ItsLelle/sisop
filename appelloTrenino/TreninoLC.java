package appelloTrenino;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.LinkedList;

public class TreninoLC extends Trenino{
    private int[] turistiNellaCabina=new int[10];
    private int cabinaAttuale = 0;

    private boolean scatto = false;
  

    private Lock l = new ReentrantLock();
    private Condition cabinaVuota = l.newCondition();
    private Condition cabinaPiena = l.newCondition();
    private Condition possoScendere = l.newCondition();
    private Condition possoSalire = l.newCondition();


    private Condition cambioCabina = l.newCondition();
    private int turistiChePossonoSalire=0;
    private int turistiChePossonoScendere=0;

    private LinkedList<Long> cabina = new LinkedList<>();

    public TreninoLC(){
        for(int i = 0; i< turistiNellaCabina.length; i++){
            turistiNellaCabina[i] = 0;

        }
    }

    @Override
    void tourSali() throws InterruptedException {
        l.lock();
        try{
            while (turistiChePossonoSalire == 0) {//modello il fatto che l'impiegato dà il permesso di salire
                possoSalire.await(); 
            }
            turistiChePossonoSalire--;
            while (turistiNellaCabina[cabinaAttuale] == 10) {
                cabinaVuota.await();  
            }
            turistiNellaCabina[cabinaAttuale]++;
            System.out.println("turista "+Thread.currentThread().getId()+"è salito");
            cabina.addLast(Thread.currentThread().getId());
            if(turistiNellaCabina[cabinaAttuale] == 10){
                scatto = true;
                cabinaPiena.signal();
            }
        }finally{
            l.unlock();
        }
    }

    @Override
    void tourScendi() throws InterruptedException {
         l.lock();
        try{
           while (turistiChePossonoScendere == 0) {
                possoScendere.await();
           }
           turistiChePossonoScendere--;
           while (turistiNellaCabina[cabinaAttuale] == 0 || Thread.currentThread().getId() != cabina.getLast()) {
            possoScendere.await();
           }
           turistiNellaCabina[cabinaAttuale]--;
           System.out.println("turista "+Thread.currentThread().getId()+ "è sceso");
           if(turistiNellaCabina[cabinaAttuale]==0){
            cabinaVuota.signalAll();
           }

        }finally{
            l.unlock();
        }
    }

    @Override
    void impFaiScendere() throws InterruptedException {
        l.lock();
        try{
            if(turistiNellaCabina[cabinaAttuale] == 0){
                //non fa nulla
            }else{
                possoScendere.signalAll();
            }


        }finally{
            l.unlock();
        }
    }

    @Override
    void impFaiSalire() throws InterruptedException {
        l.lock();
        try{
            turistiChePossonoSalire = 10;
            possoSalire.signalAll();
            
        }finally{
            l.unlock();
        }
    }

    @Override
    void impMuovi() throws InterruptedException {
        l.lock();
        try{
            while ((!scatto)) {
                cabinaPiena.await();
            }
            impMuovi();
            scatto = false;
            cabinaAttuale = (cabinaAttuale+1)%10;
            turistiChePossonoScendere = 10;
            cambioCabina.signalAll();
        }finally{
            l.unlock();
        }
    }

    public static void main(String[] args) {
        Trenino trenino = new TreninoLC();
        Thread impiegato = new Impiegato(trenino);
        impiegato.setDaemon(true);
        impiegato.start();
        for(int i = 0; i < 120; i++){
            new Turista(trenino).start();;
        }
    }
    
}
