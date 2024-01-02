package appelloFunivia;

public abstract class Funivia {
    protected final int turistaPiedi = 0;
    protected final int turistaBici = 1;
    protected final int postiLiberi = 6;
    
    public abstract void pilotaStart() throws InterruptedException;
    public abstract void pilotaEnd() throws InterruptedException;
    public abstract void turistaSali(int t) throws InterruptedException;
    public abstract void turistaScendi(int t)throws InterruptedException;

    public void test(int numTuristiBici, int numTuristiPiedi){
        for(int i = 0; i < numTuristiBici; i++){
            new Thread(new Turista(turistaBici, this)).start();
        }

        for(int i = 0; i < numTuristiPiedi; i++){
            new Thread(new Turista(turistaPiedi, this)).start();
        }

        Thread t = new Thread(new Pilota(this));
        t.setDaemon(true);
        t.start();
    }

}
