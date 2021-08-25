package practica2.nProcesos.rompeEmpate;

import practica1.Enteros;

public class DecRompeEmpate extends Thread{

	int id, N;
	Enteros res;
	LockRompeEmpate alg;

	public DecRompeEmpate(int N, int id, Enteros res, LockRompeEmpate algoritmo) {
		super();
		this.res = res;
		this.alg = algoritmo;
		this.id = id;
		this.N = N;
	}

	@Override
	public void run() {
		for(int i = 1; i <= N; i++) { //while(true)
			alg.takeLock(id);
			res.decrementa();
			alg.releaseLock(id);
		}
	}	

}
