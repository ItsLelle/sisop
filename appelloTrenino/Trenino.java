package appelloTrenino;

public abstract class Trenino {
    protected final int numPostiCabina = 10;
    protected final int cabine[] = new int[10];
    protected final int tempoGiroCompleto = 10;
    protected final int tempoScattoAvanti = 30;
    

    abstract void tourSali()throws InterruptedException;
    abstract void tourScendi()throws InterruptedException;
    abstract void impFaiScendere()throws InterruptedException;
    abstract void impFaiSalire()throws InterruptedException;
    abstract void impMuovi() throws InterruptedException;

    public void test(int numPostiCabina, int cabine){
        for(int i = 0; i < numPostiCabina; i++){
            new Thread(new Turista(this)).start();
        }

        for(int i = 0; i < cabine; i++){
            new Thread(new Turista(this)).start();
        }

        Thread t = new Thread(new Impiegato(this));
        t.setDaemon(true);
        t.start();
    }

}
