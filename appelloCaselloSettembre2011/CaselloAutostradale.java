package appelloCaselloSettembre2011;

public abstract class CaselloAutostradale {
    protected int n;
    protected int[] porte = new int[n];
    protected float tariffa;
    protected float incasso = 0;


    public CaselloAutostradale(){
        for(int i = 0; i < porte.length; i++){
            porte[i] = n;
        }
    }

    protected abstract void percorri(int km) throws InterruptedException;
    protected abstract int scegli()throws InterruptedException;
    protected abstract void accedi(int porta)throws InterruptedException;
    protected abstract void paga(int km, float tariffa)throws InterruptedException;
    protected abstract void rilascia(int porta)throws InterruptedException;

    public void test(int numVeicoli)throws InterruptedException{
        for(int i = 0; i < numVeicoli; i++){
            new Thread(new Veicolo(this)).start();
        }
    }
    

}
