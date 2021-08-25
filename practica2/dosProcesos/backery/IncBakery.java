package practica2.dosProcesos.backery;

import practica1.Enteros;

public class IncBakery extends Thread{

	int n;
	Enteros res;
	LockBakery alg;

	public IncBakery(int len, Enteros res, LockBakery algoritmo) {
		super();
		this.res = res;
		this.alg = algoritmo;
		this.n = len;
	}
	
	@Override
	public void run() {
		alg.setInc(1);
		alg.takeLockInc();
		while(alg.getLockDec() != 0 && alg.getLockInc() >= alg.getLockDec());
		
		res.incrementa();
		
		alg.setInc(0);
	}
}
