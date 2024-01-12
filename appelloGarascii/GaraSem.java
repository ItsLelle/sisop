package appelloGarascii;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;


public class GaraSem extends Gara{

    private int numSciatori = 5;
    private int numSciatoriArrivati = 0;
    private int maglia = 0;
    private LinkedList<Integer>idMaglie = new LinkedList<>();
    private boolean[] partito = new boolean[numSciatori];

    private Semaphore mutex = new Semaphore(1);

    private Semaphore possoPartire = new Semaphore(0);
    private Semaphore possoTagliareTraguardo = new Semaphore(0);


    public GaraSem(){
        for(int i = 0; i < numSciatori; i++){
            partito[i] = false;
        }

    }


    @Override
    protected void partenza(Sciatore s) throws InterruptedException {
        mutex.acquire();
        if(!partito[maglia]){
            idMaglie.add(maglia);
            possoPartire.release();
            System.out.println("Lo sciatore con maglia "+maglia+"è partito");
            partito[maglia] = true;
            maglia += 1;
        }
        mutex.release();
    }

    @Override
    protected void arrivo(Sciatore s) throws InterruptedException {
        possoTagliareTraguardo.acquire();
        mutex.acquire();
        numSciatoriArrivati++;
        System.out.println("Lo sciatore con maglia "+maglia+" è arrivato in posizione  "+numSciatoriArrivati);
        mutex.release();
        
        
    }

    @Override
    protected boolean prossimo() throws InterruptedException {
        mutex.acquire();
        if(numSciatoriArrivati < idMaglie.size()){
            possoPartire.acquire(1);
            return true;
        }
        classificaFinale();
        return false;
    }

    @Override
    protected void classificaFinale() throws InterruptedException {
        System.out.print("Classifica finale: \n");
        for (int i = 0; i < idMaglie.size(); i++) {
            System.out.print( "id maglia "+idMaglie.get(i)+": posto "+i+"\n");
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        Gara g = new GaraSem();
        Thread addetto = new Addetto(g);
        addetto.start();
        for(int i = 0; i < 5; i++){
            new Sciatore(g, i).start();
        }
    }
    
}