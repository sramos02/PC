package practica2.dosProcesos.rompeEmpate;

import practica1.Enteros;

public class DecRompeEmpate extends Thread{
	
	int n;
	Enteros res;
	LockRompeEmpate alg;
	
	public DecRompeEmpate(int len, Enteros res, LockRompeEmpate algoritmo) {
		super();
		this.res = res;
		this.alg = algoritmo;
		this.n = len;
	}

	@Override
	public void run() {
		for(int i = 0; i < n; i++) {
			alg.releaseLock(2);
			res.decrementa();	
			alg.takeLock(2);
		}
	}
}
