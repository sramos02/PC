package practica2.nProcesos.ticket;

import practica1.Enteros;

public class IncTicket extends Thread {

	private Enteros res;
	private LockTicket alg;
	private int id, N;
	
	public IncTicket(int id, int N, LockTicket algoritmo, Enteros res) {
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
			res.incrementa();
			alg.releaseLock();
		}
	}
}
