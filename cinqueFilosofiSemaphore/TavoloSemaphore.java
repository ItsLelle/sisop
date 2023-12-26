package cinqueFilosofiSemaphore;

import java.util.concurrent.Semaphore;
//DA FARE   
public class TavoloSemaphore extends Tavolo{
    Semaphore mutex = new Semaphore(1);
    Semaphore bacchettaDisponibile = new Semaphore(0);
    Semaphore bacchettaRilasciata = new Semaphore(0);

    public TavoloSemaphore(int numFilosofi){
        super();
    }

    @Override
    public void prendiBacchetta(int i) throws InterruptedException {
        
        

    }

    @Override
    public void rilasciaBacchetta(int i) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rilasciaBacchetta'");
    }
    
}
