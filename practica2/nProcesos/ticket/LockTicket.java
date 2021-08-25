package practica2.nProcesos.ticket;

import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket {
	
	private volatile AtomicInteger number =  new AtomicInteger(1); //El número del ticket
	private volatile int next = 1;
	private volatile int[] turn; //[1..M]

	public LockTicket(int M) {
		turn = new int[M+1];
		for(int i = 1; i <= M; i++) turn[i] = 0;
	}
		
	public void releaseLock() {
		next = next + 1;
	}

	public void takeLock(int id) {
		turn[id] = number.getAndAdd(1);
		turn = turn;
		while(turn[id] != next);
	}
}
