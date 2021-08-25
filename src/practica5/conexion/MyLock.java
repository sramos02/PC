package practica5.conexion;

public class MyLock {
	
	private volatile int last;
	private volatile boolean lockCliente = false;
	private volatile boolean lockOyente = false;


	public int getLast(){
		return last;
	}

	public void takeLock(int i) {
		if(i == 1) lockCliente = true;
		else lockOyente = true;
		last = i;
		
		
		while(getLock((i+1)%2) && last == i);
	}

	public void releaseLock(int i) {
		if(i == 1) lockCliente = false;
		else lockOyente = false;
	}

	public boolean getLock(int i) {
		if(i == 1) return lockCliente;
		else return lockOyente;
	}
}
