package practica3.mutex;

import practica1.Enteros;

public class IncSem extends Thread{

	int N;
	Enteros res;
	LockSem alg;
	
	public IncSem(int n, Enteros res, LockSem alg) {
		this.N = n;
		this.res = res;
		this.alg = alg;
	}

	@Override
	public void run() {
		for(int i = 1; i <= N; i++) {
			alg.takeSem();
			res.incrementa();
			alg.releaseSem();
		}
	}
	
}
