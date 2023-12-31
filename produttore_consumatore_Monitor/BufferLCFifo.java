package produttore_consumatore_Monitor;

import java.util.LinkedList;

public class BufferLCFifo extends BufferLC {
    private LinkedList<Thread> codaProduttori = new LinkedList<Thread>();

	private LinkedList<Thread> codaConsumatori = new LinkedList<Thread>();

	public BufferLCFifo(int dimensione) {
		super(dimensione);
	}

	public void put(Elemento el) throws InterruptedException {
		l.lock();
		try {
			codaProduttori.add(Thread.currentThread());//aggiungo il thread corrente alla coda
			while (!possoInserire()) {//se non posso inserire mi fermo in attesa
				bufferPieno.await();
			}
			codaProduttori.remove();//altrimenti rimuovo, mi calcolo in e incremento il numero di elementi
			buffer[in] = el;
			in = (in + 1) % buffer.length;
			numElementi++;
			System.out.println(this);
			bufferVuoto.signalAll();//li libera tutti
		} finally {
			l.unlock();
		}
	}

	private boolean possoInserire() {
		return numElementi < buffer.length && Thread.currentThread() == codaProduttori.getFirst();
	}

	public Elemento get() throws InterruptedException {
		Elemento el = null;
		l.lock();
		try {
			codaConsumatori.add(Thread.currentThread());
			while (!possoPrelevare()) {
				bufferVuoto.await();
			}
			codaConsumatori.remove();
			el = buffer[out];
			buffer[out] = null;
			out = (out + 1) % buffer.length;
			numElementi--;
			System.out.println(this);
			bufferPieno.signalAll();
		} finally {
			l.unlock();
		}
		return el;
	}

	private boolean possoPrelevare() {
		return numElementi > 0 && Thread.currentThread() == codaConsumatori.getFirst();
	}

	public static void main(String[] args) {
		try{
			int dimensione = 10;
			Buffer buffer = new BufferLCFifo(dimensione);
			int numProduttori = 10;
			int numConsumatori = 10;
			buffer.test(numProduttori, numConsumatori);
		} catch (Exception e) {
			System.err.println("Errore!");
			System.exit(-1);
		}
	}

    
}
