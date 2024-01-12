package appelloCasaDiCura;

import java.util.concurrent.Semaphore;

import java.util.*;

public class CasaDiCuraSemaphore extends CasaDiCura{

	private Semaphore filaStanzaDiPreparazione=new Semaphore(3);
	private Semaphore filaStanzaDiOperazione=new Semaphore(0,true);
	
	private Semaphore puoIniziareOperazione=new Semaphore(0);
	private Semaphore possoLasciareOspedale=new Semaphore(0);
	
	private Semaphore mutex=new Semaphore(1);
	
	private LinkedList<Long> listaAttesa=new LinkedList<>();
	private long utenteSottoOperazione;
	
	@Override
	protected void chiamaEIniziaOperazione() throws InterruptedException {
		filaStanzaDiOperazione.release();
		puoIniziareOperazione.acquire();
		System.out.println("Il paziente "+utenteSottoOperazione+" è sotto operazione");
	
	}

	@Override
	protected void fineOperazione() throws InterruptedException {
		System.out.println("Il paziente "+utenteSottoOperazione+" ha concluso l'operazione");
		possoLasciareOspedale.release();
	}

	@Override
	protected void pazienteEntra() throws InterruptedException {
		//entra in fila per standa da tre RANDOM
		filaStanzaDiPreparazione.acquire();
		
		mutex.acquire();
		listaAttesa.addLast(Thread.currentThread().getId());
		mutex.release();
		
		//entra in fila per stanza da 1 FIFO
		filaStanzaDiOperazione.acquire();
		
		mutex.acquire();
		System.out.println("Pazienti in fila: "+listaAttesa);
		utenteSottoOperazione=Thread.currentThread().getId();
		listaAttesa.remove(Thread.currentThread().getId());
		mutex.release();
		
		//Visto che è entrato nella stanza da 1, nella stanza da 3 si libera un posto
		filaStanzaDiPreparazione.release();
		
		//segnaliamo al medico di iniziare
		puoIniziareOperazione.release();
	}

	@Override
	protected void pazienteEsci() throws InterruptedException {
		possoLasciareOspedale.acquire();
	}

	public static void main(String[] args) throws InterruptedException
	{
		CasaDiCura cdc=new CasaDiCuraSemaphore();
		cdc.test(cdc);
	}
}
