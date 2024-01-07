package appelloTrenino;

import java.util.concurrent.Semaphore;

public class TreninoSem extends Trenino {
    private int cabinaAttuale = 0;
    private int[] turistiNellaCabina=new int[10];

    private Semaphore[] cabina = new Semaphore[10];
    private Semaphore mutex = new Semaphore(1);
    private Semaphore permettiScattoCabina = new Semaphore(0);

    private Semaphore permettiDiscesaTurista = new Semaphore(0);
    private Semaphore permettiDiscesaImp = new Semaphore(0);
   
    private Semaphore filaCabina=new Semaphore(10);

    public TreninoSem(){
        for(int i =0; i < numPostiCabina; i++){
            cabina[i] = new Semaphore(0);
            turistiNellaCabina[i] = 0;

        }
    }

    @Override
    void tourSali() throws InterruptedException {
        filaCabina.acquire(1);
        cabina[cabinaAttuale].acquire(1);
        mutex.acquire();
        System.out.println("Turista "+Thread.currentThread().getId()+ "è salito");
        turistiNellaCabina[cabinaAttuale]++;
        if(turistiNellaCabina[cabinaAttuale] == 10){
            permettiScattoCabina.release(1);
        }
        mutex.release();        
    }

    @Override
    void tourScendi() throws InterruptedException {
       permettiDiscesaTurista.acquire(1);
       System.out.println("Il turista "+Thread.currentThread().getId()+"è sceso");
    }

    @Override
    void impFaiScendere() throws InterruptedException {
      mutex.acquire();
      if(turistiNellaCabina[cabinaAttuale] == 0){
        //non fa nulla
      }else{
        permettiDiscesaImp.release(10);
      }
      mutex.release();
    }

    @Override
    void impFaiSalire() throws InterruptedException {
      cabina[cabinaAttuale].release(10);
    }

    @Override
    void impMuovi() throws InterruptedException {
        permettiScattoCabina.acquire(1);
        mutex.acquire();
        impMuovi();
        cabinaAttuale = (cabinaAttuale+1)%10;
        mutex.release();
        filaCabina.release(10);
    }

    public static void main(String[] args) {
      Trenino trenino=new TreninoSem();
		  Thread impiegato=new Impiegato(trenino);
		  impiegato.setDaemon(true);
		  impiegato.start();
		  for(int i=0;i<120;i++){
			  new Turista(trenino).start();
		  }
	  }
}
