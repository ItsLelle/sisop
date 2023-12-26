package cinqueFilosofiSemaphore;

public abstract class Tavolo {
    public final int numFilosofi = 5;
    protected boolean[] bacchetta = new boolean[numFilosofi];
    
    public abstract void prendiBacchetta(int i)throws InterruptedException;
    public abstract void rilasciaBacchetta(int i)throws InterruptedException;

    protected void test(){
        for(int i = 0; i<numFilosofi; i++){
            new Filosofi(this, i).start();
        }
    }
}
