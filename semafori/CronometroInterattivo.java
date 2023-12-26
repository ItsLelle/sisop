package semafori;

import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CronometroInterattivo extends Thread{

    private static Semaphore mutex = new Semaphore(1);//si usa per modificare la variabile numsecondi
    private static Semaphore avvio = new Semaphore(0);//rosso e non parte subito


    public static void main(String[] args) throws InterruptedException{
        Scanner sc = new Scanner(System.in);
        CronometroT cronometro = new CronometroT();
        cronometro.start();
        boolean finito = false;
        while(!finito){
            String input = sc.nextLine();
            switch (input) {
                case "A": {
                    avvio.release();// main rilascia il permesso
                    break;
                }
                case "F":{
                    avvio.acquire();//ferma il semaforo, il main perceprisce un permesso su avvio
                    break;
                }
                case "R": {
                    cronometro.reset();
                    break;
                }
                case "E":{
                    cronometro.interrupt();
                    sc.close();
                    finito = true;
                    break;
                }
                default:
                System.out.println("Comando non valido!");
            }//switch
        }//while
    }//main


    static class CronometroT extends Thread{
        private int numSecondi = 1;

        public void reset() throws InterruptedException{
            mutex.acquire();
            numSecondi = 1;
            mutex.release();
        }//reset


        @Override
        public void run() {
            while(!isInterrupted()){
                try{
                    avvio.acquire();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(numSecondi);
                    mutex.acquire();
                    numSecondi++;
                    mutex.release();
                    avvio.release();
                }catch(InterruptedException e){
                    break;
                } 
            }  
        }//run
    }//CronometroT
}//CronometroInterattivo
