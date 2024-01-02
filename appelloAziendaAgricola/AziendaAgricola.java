package appelloAziendaAgricola;

public abstract class AziendaAgricola {
    protected int incasso = 0;
    protected int sacchetti;
    protected final int sacchettiIniziali;

    public AziendaAgricola(int sacchettiIn){
        this.sacchetti = sacchettiIn;
        this.sacchettiIniziali = sacchetti;
    }

    public abstract void paga(int numSacchetti)throws InterruptedException;
    public abstract void ritira() throws InterruptedException;
    public abstract void carica() throws InterruptedException;

    public void test(int numClienti){
        Cliente[] threadClienti = new Cliente[numClienti];
        for(int i = 0; i < numClienti; i++){
            threadClienti[i] = new Cliente(this,i);
            threadClienti[i].start();
        }
        Magazziniere magazziniere = new Magazziniere(this);
        magazziniere.start();
        for(int i =0; i < numClienti; i++){
            try{
                threadClienti[i].join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("Incasso complessivo: "+this.incasso+" ");
    }
}

