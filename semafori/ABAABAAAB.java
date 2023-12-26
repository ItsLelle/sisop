package semafori;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ABAABAAAB {//AB AAB AAAB AAAAB

    private static int N = 1;
    private static Semaphore semA = new Semaphore(N);
    private static Semaphore semB = new Semaphore(0);
    private static Semaphore mutexA = new Semaphore(1);
    private static Semaphore mutex = new Semaphore(1);
    private static int conta = 0;


    static class A extends Thread{
        @Override
        public void run() {
            try{
                semA.acquire();//acquisisco il semaforo di a
                System.out.print("A");//stampo
                mutexA.acquire();//acquisisco il mutex che mi fa fare una sola operazione per volta
                conta++;//conta incrementa se è pari ad uno entra nella condizione altrimenti rilascia 
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
                semB.acquire();//rilasciando il mutex arrivo ad acquisire semB
                System.out.print("B ");//stampo
                mutex.acquire();//acquisisco un mutex per b che mi porta ad incrementare n così non entrerà più nella condizione di A e andrà a stamapre incrementando la stessa
                N++;//incremento n
                mutex.release();//rilascio il mutex di b
                semA.release(N);//rilascio n a semA
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
