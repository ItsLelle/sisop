package esercitazioni.cronometro;

public class Cronometro extends Thread {

    @Override
    public void run() {
        int numSecondi = 1;
        while(!isInterrupted()){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
                break;
            }
            System.out.println("\n" + numSecondi);
            numSecondi++;
        }
    }
}
