package appelloCallCenter;

public abstract class CallCenter {

    protected int n;
    protected final int operatori = n;
    protected final int numFila = n;
    protected int personeFila;
    protected int numFile = 1;
    protected int filaAttuale = 0;
    
    protected abstract void richiediAssistenza() throws InterruptedException;
    protected abstract void fornisciAssistenza() throws InterruptedException;
    protected abstract void terminaChiamata() throws InterruptedException;
    protected abstract void prossimoCliente() throws InterruptedException;

    public void test(int operatori, int clienit){
        for(int i = 0; i < operatori; i++){
            new Thread(new Cliente(this)).start();
        }

        for(int i = 0; i < clienit; i++){
            new Thread(new Cliente(this)).start();
        }

        Thread t = new Thread(new Operatore(this));
        t.setDaemon(true);
        t.start();
    }
}
