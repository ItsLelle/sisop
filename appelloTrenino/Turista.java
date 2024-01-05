package appelloTrenino;

public class Turista extends Thread {
    private Trenino trenino;

    public Turista(Trenino trenino){
        this.trenino = trenino;
    }

    @Override
    public void run() {
        try{
            trenino.tourSali();
            trenino.tourScendi();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
    
}
