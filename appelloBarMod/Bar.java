package appelloBarMod;

public abstract class Bar {

    protected static final int cassa = 0, bancone = 1;
    protected static final int numFile = 2;
    protected static final int[] banconeFila = {1,4};

    protected int[] numPostiLiberi = new int[numFile];
    
    
    public Bar(){
        for(int i = 0; i < numFile; i++){
            numPostiLiberi[i] = banconeFila[i];
        }
    }

    protected abstract int scegli()throws InterruptedException;
    protected abstract void inizia(int i)throws InterruptedException;
    protected abstract void finisci(int i)throws InterruptedException;

    public void test(int numPersone) throws InterruptedException{
        for(int i = 0; i < numPersone; i++){
            new Thread(new Persona(this),"T "+i).start();
        }

    }
}
