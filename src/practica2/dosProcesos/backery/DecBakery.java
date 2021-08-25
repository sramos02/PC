package practica2.dosProcesos.backery;

import practica1.Enteros;

public class DecBakery extends Thread{
	
	int n;
	Enteros res;
	LockBakery alg;
	
	public DecBakery(int len, Enteros res, LockBakery algoritmo) {
		super();
		this.res = res;
		this.alg = algoritmo;
		this.n = len;
	}

	@Override
	public void run() {
		alg.setDec(1);
		alg.takeLockDec();
		while(alg.getLockInc() != 0 && alg.getLockDec() >= alg.getLockInc());
		
		res.decrementa();
		
		alg.setDec(0);
	}
}
