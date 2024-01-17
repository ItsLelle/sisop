package appelloBowling;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaBowlingLC  extends SalaBowling{

    private Lock l = new ReentrantLock();

    private Condition possoFornireInfo = l.newCondition();
    private Condition possoGiocare = l.newCondition();


    private Condition preparoPartita = l.newCondition();
    private Condition possoDepositare = l.newCondition();

    private ArrayList<Long>nomiGiocatori = new ArrayList<>();

    @Override
    protected String fornisciInformazioni() throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'preparaPartita'");
    }

    @Override
    protected void preparaPartita() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'preparaPartita'");
    }

    @Override
    protected void gioca(String info) throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'gioca'");
    }

    @Override
    protected void deposita() throws InterruptedException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deposita'");
    }
    
}
