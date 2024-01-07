package appelloCaselloSettembre2011;

import java.util.concurrent.Semaphore;

public class CaselloSem extends CaselloAutostradale {
    private float incasso = 0;
    private Semaphore[] fila = new Semaphore[n];
    private Semaphore mutex = new Semaphore(1);
    private int[]numVeicoliFila = new int[n];


    public CaselloSem(){
        super();
        for(int i = 0; i < n; i++){
            fila[i] = new Semaphore(numVeicoliFila[i], true);//fifo
            numVeicoliFila[i] = 0;
        }
        System.out.println(this);
    }

    @Override
    protected void percorri(int km) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'percorri'");
    }

    @Override
    protected int scegli() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'scegli'");
    }

    @Override
    protected void accedi(int porta) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'accedi'");
    }

    @Override
    protected void paga(int km, float tariffa) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'paga'");
    }

    @Override
    protected void rilascia(int porta) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'rilascia'");
    }
    
}
