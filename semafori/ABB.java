package semafori;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ABB {//ABB AABB AAABB AAAABB...

    private static int NA = 1;
    private static int NB = 2;
    private static Semaphore semA = new Semaphore(NA);
    private static Semaphore semB = new Semaphore(0);
    private static int conta = 0;
    private static int contaB = 0;
    private static Semaphore mutexA = new Semaphore(1);
    private static Semaphore mutexB = new Semaphore(1);
    private static Semaphore mutexB2 = new Semaphore(1);


    static class A extends Thread {
        @Override
        public void run() {
            try{
                semA.acquire();
                System.out.print("A");
                mutexA.acquire();
                conta++;
                if(conta == NA){
                    conta = 0;
                    semB.release(NB);
                }
                mutexA.release();
            }catch(InterruptedException e ){
                e.printStackTrace();
            }
        } 
    }

    static class B extends Thread {
        @Override
        public void run() {
            try{
                semB.acquire();
                System.out.print("B");
                mutexB.acquire();
                contaB++;
                if(contaB == NB){
                    System.out.print(" ");
                    contaB = 0;
                    mutexB2.acquire();
                    NA++;
                    mutexB2.release();
                    semA.release(NA);
                }
                mutexB.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }    
    }

    public static void main(String[] args) {
        while (true) {
            new A().start();
            new B().start();
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
}
