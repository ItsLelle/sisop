package esercitazioni.barbiereAddormentato;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SalaLock extends Sala{
	Lock lock = new ReentrantLock();
	Condition clienteDisponibile = lock.newCondition();
	Condition poltrona = lock.newCondition();
	LinkedList<Cliente> clienti = new LinkedList<Cliente>();
	protected boolean poltronaLibera = false;
	
	public SalaLock(int sedie) {
		super(sedie);
	}

	@Override
	public void tagliaCapelli() throws InterruptedException {
		lock.lock();
		try{
			while(numSedie==sedieLibere)
				clienteDisponibile.await();
			poltronaLibera = true;
			poltrona.signalAll();
		}finally{
			lock.unlock();
		}		
	}

	public boolean attendiTaglio() throws InterruptedException {
		lock.lock();
		Cliente c = (Cliente) Thread.currentThread();
		try{
			if (sedieLibere==0) {
				 return false;
			 }
			 clienti.addLast(c);
			 sedieLibere--;
			 clienteDisponibile.signal();
			 while(!mioTurno(c.getID()))
				 poltrona.await();
			 clienti.removeFirst();
			 poltronaLibera=false;
			 sedieLibere++;
			 return true;
		}finally{
			lock.unlock();
		}
	}
	
	private boolean mioTurno(int id) {
		return clienti.getFirst().getID()==id && poltronaLibera;
	}

	public static void main(String[] args) {		
		try {
			Sala s = new SalaLock(5);
			s.test(30);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

