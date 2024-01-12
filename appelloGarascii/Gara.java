package appelloGarascii;

import java.util.concurrent.TimeUnit;

public abstract class Gara {

    protected abstract void partenza(Sciatore s) throws InterruptedException;
    protected abstract void arrivo(Sciatore s) throws InterruptedException;
    protected abstract boolean prossimo() throws InterruptedException;
    protected abstract void classificaFinale() throws InterruptedException;

    protected void test(Gara gara) throws InterruptedException{
        Thread addetto = new Addetto(gara);
        addetto.start();
        while (true) {
            new Sciatore(gara, 0).start();
            TimeUnit.SECONDS.sleep(1);
            
        }
    }
    
}
