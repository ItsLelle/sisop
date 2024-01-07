package appelloTrenino;


public class Impiegato extends Thread {
    private Trenino trenino;

    public Impiegato(Trenino trenino){
        this.trenino = trenino;
    }

    @Override
    public void run() {
        try{
            while (true) {
                trenino.impFaiScendere();
                trenino.impFaiSalire();
                trenino.impMuovi();         
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        
    }
    
}
