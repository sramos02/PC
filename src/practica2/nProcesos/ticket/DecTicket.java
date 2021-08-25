package practica2.nProcesos.ticket;

import practica1.Enteros;

public class DecTicket extends Thread{

	private Enteros res;
	private LockTicket alg;
	private int id, N;

	public DecTicket(int id, int N, LockTicket algoritmo, Enteros res) {
		super();
		this.res = res;
		this.alg = algoritmo;
		this.id = id;
		this.N = N;
	}

	@Override
	public void run() {
		for(int i = 1; i <= N; i++) {
			alg.takeLock(id);
			res.decrementa();
			alg.releaseLock();
		}
	}

}