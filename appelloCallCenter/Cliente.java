package appelloCallCenter;

import java.util.concurrent.TimeUnit;

public class Cliente extends Thread {
    private CallCenter callCenter;
    private final int maxAssitenza = 10;
    private final int minAssistenza = 1;

    public Cliente(CallCenter callCenter){
        this.callCenter = callCenter;
    }

    @Override
    public void run() {
        try{
            callCenter.richiediAssistenza();
            attendiAssistenza();
            callCenter.terminaChiamata();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    private void attendiAssistenza() throws InterruptedException{
       TimeUnit.SECONDS.sleep((maxAssitenza - minAssistenza + 1)+minAssistenza);
    }
    
}
