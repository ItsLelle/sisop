package appelloCasaDiCura;

import java.util.concurrent.TimeUnit;
import java.util.*;

public abstract class CasaDiCura {
    protected Random r = new Random();

    protected abstract void chiamaEIniziaOperazione()throws InterruptedException;
    protected abstract void fineOperazione()throws InterruptedException;
    protected abstract void pazienteEntra()throws InterruptedException;
    protected abstract void pazienteEsci()throws InterruptedException;

    protected void opera() throws InterruptedException{
        TimeUnit.SECONDS.sleep(r.nextInt(4 - 2 + 1)+2);
    }

    protected void preparaSala() throws InterruptedException{
        TimeUnit.SECONDS.sleep(2);
    }

    protected void test(CasaDiCura casa) throws InterruptedException{
        Thread d=new Medico(casa);
		d.start();
		while(true)
		{
			new Paziente(casa).start();
			TimeUnit.SECONDS.sleep(1);
		}
    }
}
