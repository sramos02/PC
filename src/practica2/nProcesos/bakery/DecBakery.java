package practica2.nProcesos.bakery;

import practica1.Enteros;

public class DecBakery extends Thread{

	LockBakery alg;
	Enteros res;
	int N, id;
	
	public DecBakery(int id, int N, LockBakery alg, Enteros res) {
		this.id = id;
		this.N = N;
		this.res = res;
		this.alg = alg;
	}
	
	@Override
	public void run() {
		alg.takeLock(id);
		res.decrementa();
		alg.releaseLock(id);
		System.out.println("DEC" + id);
	}
}
