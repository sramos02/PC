package practica2.nProcesos.bakery;

import java.util.concurrent.atomic.AtomicInteger;

public class LockBakery {

	private int M;
	private volatile AtomicInteger maxTurno;
	private volatile int[] turno; 

	
	public LockBakery(int M) {
		this.M = M;
		turno = new int[M+1];
		maxTurno = new AtomicInteger(0);

		for(int i = 1; i <= M; i++) turno[i] = 0;
	}
	
	public void takeLock(int i) {		
		turno[i] = maxTurno.addAndGet(1);
		turno = turno;
		
		for(int j = 1; j <= M; j++) {
			if (j != i) 
				while(turno[j] == 0 || turno[i] < turno[j]);
		}
	}
	
	public void releaseLock(int i) {
		turno[i] = 0;
		turno = turno;
	}
}