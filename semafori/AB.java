package semafori;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AB {
    private static Semaphore semA = new Semaphore(1);
    private static Semaphore semB = new Semaphore(0);

    static class A extends Thread{
        @Override
        public void run() {
            try{
                semA.acquire();
                System.out.print("A");
                semB.release();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    static class B extends Thread{
        @Override
        public void run() {
            try{
                semB.acquire();
                System.out.print("B");
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
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e){
                e.printStackTrace();
            } 
        }
    }
}
