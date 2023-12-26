package semafori;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ABABC { //ABABC ABABC...

    private static Semaphore semA = new Semaphore(1);
    private static Semaphore semB = new Semaphore(0);
    private static Semaphore semC = new Semaphore(0);
    


    static class A extends Thread{
        @Override
        public void run() {
            try{
                semA.acquire();
                System.out.print("A");
                semB.release();
                semC.release();
            }catch(InterruptedException e){
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
                semA.release();
                semC.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }    
    }

    static class C extends Thread {
        @Override
        public void run() {
            try{
                semC.acquire(4);//perchè prima di lui ci sono 4 da eseguire
                System.out.print("C ");
                semA.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        while (true) {
            new A().start();
            new B().start();
            new C().start();
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    
}
