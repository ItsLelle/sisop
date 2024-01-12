package appelloCasaDiCura;

import java.util.concurrent.locks.*;
import java.util.LinkedList;

public class CasaDiCuraLC extends CasaDiCura {

    private Lock lock=new ReentrantLock();
	
	private Condition permessoStanzaAttesa=lock.newCondition();
	private Condition permessoStanzaOperazione=lock.newCondition();
	
	private Condition permessoInizioOperazione=lock.newCondition();
	private Condition permessoLasciareOspedale=lock.newCondition();
	
	private LinkedList<Long> listaAttesa=new LinkedList<>();
	private long idUtenteSottoOperazione=-2;  //inizia a -2, così che il medico, per la prima volta, possa settarlo a -1 e quindi � il medico che dice quando entrare
	
	private int numPersoneCheDevonoUscire=0;
	
	@Override
	protected void chiamaEIniziaOperazione() throws InterruptedException {
		lock.lock();
		try {
			idUtenteSottoOperazione=-1;
			permessoStanzaOperazione.signalAll();
			while(idUtenteSottoOperazione==-1)
			{
				permessoInizioOperazione.await();
			}
			System.out.println("Il paziente "+idUtenteSottoOperazione+" è sotto operazione");
		}finally {
			lock.unlock();
		}
		
	}

	@Override
	protected void fineOperazione() throws InterruptedException {
		lock.lock();
		try {
			System.out.println("Il paziente "+idUtenteSottoOperazione+" ha concluso l'operazione");
			numPersoneCheDevonoUscire++;
			permessoLasciareOspedale.signalAll();
		}finally {
			lock.unlock();
		}
		
	}

	@Override
	protected void pazienteEntra() throws InterruptedException {
		lock.lock();
		try {
			while(listaAttesa.size()==3)
			{
				permessoStanzaAttesa.await();
			}
			listaAttesa.add(Thread.currentThread().getId());
			while(idUtenteSottoOperazione!=-1 || Thread.currentThread().getId()!=listaAttesa.getFirst()) //visto che gli id vanno da 1 in su, modelliamo l'assenza di utente con un id==-1
			{
				permessoStanzaOperazione.await();
			}
			
			System.out.println("Pazienti in fila: "+listaAttesa);
			
			idUtenteSottoOperazione=Thread.currentThread().getId();
			listaAttesa.remove(Thread.currentThread().getId());
			
			permessoStanzaAttesa.signal();
			permessoInizioOperazione.signal();
		}finally {
			lock.unlock();
		}
	}

	@Override
	protected void pazienteEsci() throws InterruptedException {
		lock.lock();
		try {
			while(numPersoneCheDevonoUscire==0)
			{
				permessoLasciareOspedale.await();
			}
			numPersoneCheDevonoUscire--;
		}finally {
			lock.unlock();
		}
		
	}
	
	public static void main(String[] args) throws InterruptedException
	{
		CasaDiCura cdc=new CasaDiCuraLC();
		cdc.test(cdc);
	}

}
