import java.util.concurrent.Semaphore;

public class Prova3 {
    public static int c = 0, result = 0;
    public static int n = 3, k = 6;
    public static Semaphore sem = new Semaphore(0), mutex = new Semaphore(1);

    public static void main(String[] args) throws InterruptedException{
        MyThread[] threads = new MyThread[n];
        for(int i = 0; i < n; i++){
            threads[i] = new MyThread();
            threads[i].start();
        }
        for(int i = 0; i < n; i++){
            threads[i].join();
            System.out.println("The result is: "+result);
        }   
    }


    static class MyThread extends Thread{

        @Override
        public void run() {
            try{
                mutex.acquire();
                c++;
                sem.release(c);
                mutex.release();
                sem.acquire(k);
                result += c;
                sem.release(k);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

    }
}
