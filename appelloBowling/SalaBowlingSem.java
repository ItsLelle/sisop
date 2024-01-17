package appelloBowling;

import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SalaBowlingSem extends SalaBowling{

    private Semaphore mutex = new Semaphore(1);

    private Semaphore possoEntrare = new Semaphore(amici);
    private Semaphore possoGiocare = new Semaphore(0,true);
    private Semaphore possoConsegnareScarpe = new Semaphore(0,true);


    private Semaphore possoPrepararePartita = new Semaphore(0);
    private Semaphore possoDepositare = new Semaphore(0);


    private int[]numPersoneFila = new int[amici];
    private LinkedList<String>informazioni = new LinkedList<>();
    private String inform = "";


    public SalaBowlingSem(){
        for(int i = 0; i < numFile; i++){
            numPersoneFila[i] = 0;
        }
    }

    @Override
    protected String fornisciInformazioni() throws InterruptedException {
        Scanner s = new Scanner(System.in);
        possoEntrare.acquire(amici);
        mutex.acquire();
        System.out.println("Inserisci il nome>>>>");
        String nome = s.nextLine();
        System.out.println(" inserisci la taglia delle scarpe>>>>");
        String scarpe = s.nextLine();
        inform = nome+"-"+scarpe;
        System.out.println("Informazione: "+inform);
        informazioni.addFirst(inform);
        numPersoneFila[giocare]++;
        mutex.release();
        possoPrepararePartita.release();
        s.close();
        return null;
    }

    @Override
    protected void preparaPartita() throws InterruptedException {
        possoPrepararePartita.acquire();
        mutex.acquire();
        if(informazioni.size() != amici){
            System.out.println("Errore nella preparazione della partita.");
        }else{
            possoGiocare.release(amici);
        }
        mutex.release();
    }

    @Override
    protected void gioca(String info) throws InterruptedException {
        mutex.acquire();
        for(int i = 0; i < numTiri; i++){
            possoGiocare.acquire();
            System.out.println(inform+": effettua il tiro "+i);
            TimeUnit.SECONDS.sleep(1);
            numTiri--;
            if(numTiri == 0){
                numPersoneFila[consegna]++;
                possoConsegnareScarpe.release();
            }else{
                possoGiocare.release();
            }
        }
        mutex.release();
        possoConsegnareScarpe.acquire();
        possoDepositare.release();

    }

    @Override
    protected void deposita() throws InterruptedException {
        mutex.acquire();
        if(numPersoneFila[consegna] == amici){
            possoDepositare.acquire();
        }
        mutex.release();
    }

    public static void main(String[] args) {
        SalaBowling s = new SalaBowlingSem();
        s.test(5);
    }
    
}
