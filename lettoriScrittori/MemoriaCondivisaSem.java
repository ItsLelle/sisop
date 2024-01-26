package lettoriScrittori;

import java.util.concurrent.Semaphore;

public class MemoriaCondivisaSem extends MemoriaCondivisa {

	private int numLettori = 0;

	private Semaphore mutex = new Semaphore(1, true);
	private Semaphore scrittura = new Semaphore(1, true);

	public void inizioScrittura() throws InterruptedException {
		scrittura.acquire();
		System.out.format("Thread %d ha iniziato a scrivere.%n", Thread.currentThread().getId());
	}

	public void fineScrittura() {
		scrittura.release();
		System.out.format("Thread %d ha finito di scrivere.%n", Thread.currentThread().getId());
	}

	public void inizioLettura() throws InterruptedException {
		mutex.acquire();
		if (numLettori == 0) {
			scrittura.acquire();
		}
		numLettori++;
		System.out.format("Thread %d ha iniziato a leggere.%n", Thread.currentThread().getId());
		mutex.release();
	}

	public void fineLettura() throws InterruptedException {
		mutex.acquire();
		numLettori--;
		if (numLettori == 0) {
			scrittura.release();
		}
		System.out.format("Thread %d ha finito di leggere.%n", Thread.currentThread().getId());
		mutex.release();
	}
	
	public static void main(String[] args) {
		MemoriaCondivisa memoria = new MemoriaCondivisaSem();
		memoria.test(10, 4);
	}

}
