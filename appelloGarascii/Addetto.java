package appelloGarascii;

public class Addetto extends Thread{
    Gara gara;

    public Addetto(Gara gara){
        this.gara = gara;
    }

    @Override
    public void run() {
        try {
            while (gara.prossimo()) {
            }
            gara.classificaFinale();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
