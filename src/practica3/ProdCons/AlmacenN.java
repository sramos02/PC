package practica3.ProdCons;

import java.util.concurrent.Semaphore;

public class AlmacenN {

	int N, ini, fin;
	private volatile Semaphore empty, full;
	private volatile Semaphore mutexP, mutexC;
	private volatile Producto[] buffer;


	public AlmacenN(int N){		
		this.N = N;
		buffer = new Producto[N];
		
		empty = new Semaphore(N);
		full = new Semaphore(0);
		
		mutexP = new Semaphore(1);
		mutexC = new Semaphore(1);
		
		ini = 0;
		fin = 0;
	}

	//--------------------------------------------------------------
	
	public void almacenar(Producto producto) {
		buffer[fin] = producto;
		buffer = buffer;
	}

	public Producto extraer() {
		return buffer[ini];
	}

	//--------------------------------------------------------------

	public void lockProductor() {
		try {
			empty.acquire();
			mutexP.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void unlockProductor() {
		fin = (fin + 1) % N;
		mutexP.release();
		full.release();
	}

	//--------------------------------------------------------------

	public void lockConsumidor() {
		try {
			full.acquire();
			mutexC.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void unlockConsumidor() {
		ini = (ini + 1) % N;
		mutexC.release();
		empty.release();
	}
}