package cinqueFilosofiSemaphore;
import java.util.HashSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TavoloLockPC extends Tavolo {
	private Lock lock = new ReentrantLock();
	private Condition[] possoMangiare = new Condition[numFilosofi];

	private boolean[] filosofiATavola = new boolean[numFilosofi];
	private HashSet<String> possibiliCombinazioni = new HashSet<String>();

	public TavoloLockPC() {
		for (int i = 0; i < bacchetta.length; i++) {
			possoMangiare[i] = lock.newCondition();
		}
		aggiungiCombinazione();
	}

	public void prendiBacchetta(int i) throws InterruptedException {
		lock.lock();
		try {
			while (bacchetta[i] || bacchetta[(i + 1) % numFilosofi]) {
				if (bacchetta[i])
					possoMangiare[i].await();
				else
					possoMangiare[(i + 1) % numFilosofi].await();
			}
			bacchetta[i] = true;
			bacchetta[(i + 1) % numFilosofi] = true;
			filosofiATavola[i] = true;
			aggiungiCombinazione();
			stampaCombinazioni();
		} finally {
			lock.unlock();
		}

	}

	private void stampaCombinazioni() {
		System.out.print("{");
		for (String s : possibiliCombinazioni) {
			System.out.print(s);
		}
		System.out.println("}");

	}

	private void aggiungiCombinazione() {
		String ret = "[";
		for (int i = 0; i < numFilosofi; i++) {
			if (filosofiATavola[i])
				ret += i;
			else
				ret += "-";
		}
		ret += "]";
		possibiliCombinazioni.add(ret);
	}

	public void rilasciaBacchetta(int i) {
		lock.lock();
		try {
			bacchetta[i] = false;
			bacchetta[(i + 1) % numFilosofi] = false;
			possoMangiare[i].signal();
			possoMangiare[(i + 1) % numFilosofi].signal();
			filosofiATavola[i] = false;
			aggiungiCombinazione();
			stampaCombinazioni();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) {
		TavoloLockPC tavolo = new TavoloLockPC();
		tavolo.test();
	}
}
