package practica3.mutex;

import java.util.concurrent.Semaphore;

public class LockSem {

	private volatile Semaphore s;
	
	public LockSem() {
		s = new Semaphore(1);
	}
	

	public void takeSem() {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void releaseSem() {
		s.release();
	}
}
