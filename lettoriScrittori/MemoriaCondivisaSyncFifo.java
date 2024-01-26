package lettoriScrittori;

import java.util.LinkedList;

public class MemoriaCondivisaSyncFifo extends MemoriaCondivisa {

	private int numLettoriInLettura = 0;

	private boolean scrittoreInScrittura = false;

	private LinkedList<Thread> codaThread = new LinkedList<Thread>();

	public synchronized void inizioScrittura() throws InterruptedException {
		codaThread.add(Thread.currentThread());
		while (!possoScrivere()) {
			wait();
		}
		codaThread.remove();
		scrittoreInScrittura = true;
		System.out.format("Thread %d ha iniziato a scrivere.%n", Thread.currentThread().getId());
	}

	public synchronized void fineScrittura() throws InterruptedException {
		scrittoreInScrittura = false;
		if (!codaThread.isEmpty()) {
			notifyAll();
		}
		System.out.format("Thread %d ha finito di scrivere.%n", Thread.currentThread().getId());
	}

	private boolean possoScrivere() {
		return numLettoriInLettura == 0 && possoLeggere();
	}
	
	public synchronized void inizioLettura() throws InterruptedException {
		codaThread.add(Thread.currentThread());
		while (!possoLeggere()) {
			wait();
		}
		codaThread.remove();
		numLettoriInLettura++;
		System.out.format("Thread %d ha iniziato a leggere.%n", Thread.currentThread().getId());
	}

	public synchronized void fineLettura() throws InterruptedException {
		numLettoriInLettura--;
		if (!codaThread.isEmpty()) {
			notifyAll();
		}
		System.out.format("Thread %d ha finito di leggere.%n", Thread.currentThread().getId());
	}

	private boolean possoLeggere() {
		return !scrittoreInScrittura && codaThread.getFirst() == Thread.currentThread();
	}
	
	public static void main(String[] args) {
		MemoriaCondivisa memoria = new MemoriaCondivisaSyncFifo();
		memoria.test(10, 4);
	}

}
