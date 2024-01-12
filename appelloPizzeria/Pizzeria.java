package appelloPizzeria;


public abstract class Pizzeria {
    protected final int postiSedere = 5;
    protected final int numFile = 1;
    


    

    protected abstract void entra() throws InterruptedException;
    protected abstract void serviPizza() throws InterruptedException;
    protected abstract void mangiaPizza() throws InterruptedException;
    protected abstract void preparaPizza() throws InterruptedException;
   

    public void test(int numClienti){
        for(int i = 0; i < numClienti; i++){
            new Thread(new Cliente(this)).start();
        }

        Thread t = new Thread(new Pizzaiolo(this));
        t.setDaemon(true);
        t.start();

    }
}
