package appelloCasaDiCura;

public class Paziente extends Thread{
    private CasaDiCura casaC;


    public Paziente(CasaDiCura c){
        this.casaC = c;
    }

    @Override
    public void run() {
        try{
            casaC.pazienteEntra();
            casaC.pazienteEsci();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
}
