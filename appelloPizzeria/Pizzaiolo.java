package appelloPizzeria;

import java.util.concurrent.TimeUnit;

public class Pizzaiolo extends Thread {
    private Pizzeria pizzeria;
    protected final int prepara = 10;

    public Pizzaiolo(Pizzeria pizzeria){
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
            try{
                while (true) {
                    pizzeria.preparaPizza();
                    preparaP();
                    pizzeria.serviPizza();
                }
            }catch(InterruptedException e){
                e.printStackTrace();
            }     
    }

    private void preparaP() throws InterruptedException{
        TimeUnit.SECONDS.sleep(prepara);
    }
    
}
