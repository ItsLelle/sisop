package semafori;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;



public class AAAAB {//AAAAB AAAB AAB AB
    private static int N = 4;
    private static Semaphore semA = new Semaphore(N);
    private static Semaphore semB = new Semaphore(0);
    private static int conta = 0;
    private static Semaphore mutexA = new Semaphore(1);
    private static Semaphore mutex = new Semaphore(1);

    static class A extends Thread{
        @Override
        public void run() {
            try{
                semA.acquire();
                System.out.print("A");
                mutexA.acquire();
                conta++;
                if(conta == N){
                    conta = 0;
                    semB.release();
                }
                mutexA.release();
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
                System.out.print("B ");
                mutex.acquire();
                N--;
                mutex.release();
                semA.release(N);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        while(true){
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
