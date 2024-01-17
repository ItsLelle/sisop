package appelloCallCenter;

import java.util.concurrent.TimeUnit;

public class Operatore extends Thread{

    private CallCenter callCenter;
    private final int maxAttesa = 6;
    private final int minAttesa = 2;

    public Operatore(CallCenter callCenter){
        this.callCenter = callCenter;
    }

    @Override
    public void run() {
        try {
            while (true) {
                callCenter.fornisciAssistenza();
                fornisci();
                callCenter.prossimoCliente();
            }
            
        } catch (Exception e) {
            
        }
        
    }

    private void fornisci() throws InterruptedException{
       TimeUnit.SECONDS.sleep((maxAttesa - minAttesa + 1 )+ minAttesa);
    }
    
}
