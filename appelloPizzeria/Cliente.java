package appelloPizzeria;

import java.util.concurrent.TimeUnit;

public class Cliente extends Thread {
    protected final int minMangia = 5;
    protected final int maxMangia = 10;
    private Pizzeria pizzeria;

    public Cliente(Pizzeria pizzeria){
        this.pizzeria = pizzeria;
    }

    @Override
    public void run() {
        try{
            pizzeria.entra();
            pizzeria.mangiaPizza();
            mangia();

        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void mangia() throws InterruptedException{
        TimeUnit.SECONDS.sleep((maxMangia - minMangia +1)+minMangia);
    }
    
}
