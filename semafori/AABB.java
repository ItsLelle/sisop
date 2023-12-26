package semafori;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AABB { //AA BB AA BB...
    private static int N = 2;
    private static Semaphore semA = new Semaphore(N);
    private static Semaphore semb = new Semaphore(0);
    private static Semaphore mutex = new Semaphore(1);
    private static int conta = 0;


    static class A extends Thread{
        @Override
        public void run() {
            try{
                semA.acquire();//acquisisco a
                System.out.print("A");//lo stampo
                mutex.acquire();//acquisisco il mutex così nessun altro può partire, uno alla volta
                conta++;//inremento il conta
                if(conta == N){//appena il conta arriva ad N ossia 2, stampo lo spazio, il conta viene ripristinato e rilascio n permessi a b
                    System.out.print(" ");
                    conta = 0;
                    semb.release(N);
                }
                mutex.release();//rilascio il mutex così può partire b
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    static class B extends Thread {
        @Override
        public void run() {
            try{ 
                semb.acquire();
                System.out.print("B");
                mutex.acquire();
                conta++;
                if(conta == N){
                    System.out.print(" ");
                    conta = 0;
                    semA.release(N);
                }
                mutex.release();
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
