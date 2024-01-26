package lettoriScrittori;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MemoriaCondivisaLC extends MemoriaCondivisa {

	private int numLettoriInLettura = 0;
	private boolean scrittoreInScrittura = false;

	private Lock l = new ReentrantLock();
	private Condition possoScrivere = l.newCondition();
	private Condition possoLeggere = l.newCondition();
	
	public void inizioScrittura() throws InterruptedException {
		l.lock();
		try {
			while (numLettoriInLettura > 0 || scrittoreInScrittura) {
				possoScrivere.await();
			}
			scrittoreInScrittura = true;
		} finally {
			System.out.format("Thread %d ha iniziato a scrivere.%n", Thread.currentThread().getId());
			l.unlock();
		}
	}

	public void fineScrittura() throws InterruptedException {
		l.lock();
		try {
			scrittoreInScrittura = false;
			possoLeggere.signalAll();
			possoScrivere.signal();
		} finally {
			System.out.format("Thread %d ha finito di scrivere.%n", Thread.currentThread().getId());
			l.unlock();
		}
	}

	public void inizioLettura() throws InterruptedException {
		l.lock();
		try {
			while (scrittoreInScrittura) {
				possoLeggere.await();
			}
			numLettoriInLettura++;
		} finally {
			System.out.format("Thread %d ha iniziato a leggere.%n", Thread.currentThread().getId());
			l.unlock();
		}
	}

	public void fineLettura() throws InterruptedException {
		l.lock();
		try {
			numLettoriInLettura--;
			if (numLettoriInLettura == 0) {
				possoScrivere.signal();
			}
		} finally {
			System.out.format("Thread %d ha finito di leggere.%n", Thread.currentThread().getId());
			l.unlock();
		}
	}

	
	
	public static void main(String[] args) {
		MemoriaCondivisa memoria = new MemoriaCondivisaLC();
		memoria.test(10, 4);
	}
}