package appelloBowling;

public class Giocatore extends Thread {
    private SalaBowling sala;

    public Giocatore(SalaBowling sala){
        this.sala = sala;
    }

    @Override
    public void run() {
        try {
            String infor = sala.fornisciInformazioni();
            sala.gioca(infor);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
