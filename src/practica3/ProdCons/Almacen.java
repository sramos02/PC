package practica3.ProdCons;

import java.util.concurrent.Semaphore;

public class Almacen {

	private volatile Semaphore empty, full;
	private volatile Producto buffer;


	public Almacen(){		
		empty = new Semaphore(1);
		full = new Semaphore(0);
	}

	//--------------------------------------------------------------
	
	public void almacenar(Producto producto) {
		buffer = producto;
	}

	public Producto extraer() {
		return buffer;
	}

	//--------------------------------------------------------------

	public void lockProductor() {
		try {
			empty.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void unlockProductor() {
		full.release();
	}

	//--------------------------------------------------------------

	public void lockConsumidor() {
		try {
			full.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}
	}

	public void unlockConsumidor() {
		empty.release();
	}
	
	
}