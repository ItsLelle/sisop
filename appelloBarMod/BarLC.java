package appelloBarMod;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BarLC extends Bar{

    private Lock l = new ReentrantLock();
    private Condition[] occupato = new Condition[numFile];
    @SuppressWarnings("unchecked")
    private LinkedList<Thread>[] fila = new LinkedList[numFile];


    public BarLC(){
        super();
        for(int i = 0; i < numFile; i++){
            occupato[i] = l.newCondition();
            fila[i] = new LinkedList<Thread>();
        }
        System.out.println(this);
    }

    @Override
    protected int scegli() throws InterruptedException {
        int i = -1;
        l.lock();
        try{
            if(numPostiLiberi[cassa] > 0){
                i = cassa;
            }else if(numPostiLiberi[bancone]>0){
                i = bancone;
            }else if(fila[cassa].size() <= fila[bancone].size()){
                i = cassa;
            }else{
                i = bancone;
            }
            System.out.format("Persona[%s] vuole andare %s%n", Thread.currentThread().getName(), ( i == cassa ? "In cassa" : "al bancone"));
        }finally{
            l.unlock();
        }
        return i;  
    }

    @Override
    protected void inizia(int i) throws InterruptedException {
        Thread t = Thread.currentThread();
        l.lock();
        try{
            fila[i].add(t);
            while (!mioTurno(t,i)) {
                occupato[i].await();
            }
            fila[i].remove();
            numPostiLiberi[i]--;
            System.out.format("Persona[%s] si trova in %s%n", Thread
					.currentThread().getName(), (i == cassa ? "in CASSA"
					: "al BANCONE"));
			System.out.println(this);
        }finally{
            l.unlock();
        }
    }

    private boolean mioTurno(Thread t, int i) {
		return fila[i].indexOf(t) < numPostiLiberi[i];
	}

    @Override
    protected void finisci(int i) throws InterruptedException {
        l.lock();
        try{
            numPostiLiberi[i]++;
            if(fila[i].size() > 0){
                occupato[i].signalAll();
            }
            System.out.format("Persona[%s] � uscita %s%n", Thread
					.currentThread().getName(), (i == cassa ? "dalla CASSA"
					: "dal BANCONE"));
            System.out.println(this);
        }finally{
            l.unlock();
        }
    }
    
}
