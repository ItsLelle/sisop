package appelloFunivia;

public class Turista extends Thread {
    private int tipo;
    private Funivia funivia;

    public Turista(int tipo, Funivia funivia){
        this.tipo = tipo;
        this.funivia = funivia;
    }

    @Override
    public void run() {
        try{
            funivia.turistaSali(tipo);
            funivia.turistaScendi(tipo);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public int getTipo(){
        return tipo;
    }

    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    public String toString(){
		return this.getId()+" ("+this.getTipo()+")";
	}
    
}
