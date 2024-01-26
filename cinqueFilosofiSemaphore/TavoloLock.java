package cinqueFilosofiSemaphore;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TavoloLock extends Tavolo{
	private Lock lock = new ReentrantLock();
	private Condition[] possoMangiare = new Condition[numFilosofi];
	
	public TavoloLock() {
		for (int i=0; i<bacchetta.length; i++) {
			possoMangiare[i] = lock.newCondition();
		}
	}

	public void prendiBacchetta(int i) throws InterruptedException {
		lock.lock();
		try{
			while(bacchetta[i] || bacchetta[(i+1)%numFilosofi]){
				if(bacchetta[i])
					possoMangiare[i].await();
				else
					possoMangiare[(i+1)%numFilosofi].await();
			}
			bacchetta[i] = true;
			bacchetta[(i+1)%numFilosofi] = true;
		}finally{
			lock.unlock();
		}
		
	}
	
	public void rilasciaBacchetta(int i) throws InterruptedException {
		lock.lock();
		try{
			bacchetta[i] = false;
			bacchetta[(i+1)%numFilosofi] = false;
			possoMangiare[i].signal();
			possoMangiare[(i+1)%numFilosofi].signal();
		}finally{
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		TavoloLock tavolo = new TavoloLock();
		tavolo.test();
	}
}
