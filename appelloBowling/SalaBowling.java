package appelloBowling;

public abstract class SalaBowling {

    protected int amici = 5;
    protected final int numFile = 2;
    protected  int numTiri = 10;

    protected final int giocare = 0, consegna = 1;

    

    protected abstract String fornisciInformazioni() throws InterruptedException;
    protected abstract void preparaPartita() throws InterruptedException;
    protected abstract void gioca(String info) throws InterruptedException;
    protected abstract void deposita() throws InterruptedException;


   public void test(int amici){
    Giocatore[] giocatori = new Giocatore[amici];
    for(int i = 0; i < amici; i++){
        new Thread(new Giocatore(this)).start();
    }

    for(int i = 0; i < amici; i++){
        try{
            giocatori[i].join();
        }catch(InterruptedException e ){
            e.printStackTrace();
        }
    }

    Thread t = new Thread(new Operatore(this));
    t.setDaemon(true);
    t.start();

   }
}
