package appelloCasaDiCura;

public class Medico extends Thread {
    private CasaDiCura casaC;


    public Medico(CasaDiCura casaC){
        this.casaC = casaC;
    }

    @Override
    public void run() {
        try{
            while (true) {
                casaC.chiamaEIniziaOperazione();
                casaC.opera();
                casaC.fineOperazione();
                casaC.preparaSala();   
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    
}
