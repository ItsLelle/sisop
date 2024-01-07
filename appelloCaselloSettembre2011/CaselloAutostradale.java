package appelloCaselloSettembre2011;


public abstract class CaselloAutostradale {
    protected int nPorte = 100;
    protected int tariffa = 1;
    protected int incasso = 0;

    


    public CaselloAutostradale(int nPorte, int tariffa){
        this.nPorte = nPorte;
        this.tariffa = tariffa;
    }

    protected abstract void accedi(int p)throws InterruptedException;
    protected abstract void paga(int p, int km)throws InterruptedException;

    public void test(int numVeicoli)throws InterruptedException{
        Veicolo[] veicoli = new Veicolo[numVeicoli];
        for(int i = 0; i < numVeicoli; i++){
            veicoli[i] = new Veicolo(this);
            veicoli[i].start();
        }
        for(int i = 0; i < numVeicoli; i++){
            try{
                veicoli[i].join();

            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Incasso complessivo: "+this.incasso+" ");
    }

    public int getNumPorte(){
        return nPorte;
    }

    public int getIncassoTotale() {
        return incasso;
    }
}
