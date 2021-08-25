package practica2.dosProcesos.rompeEmpate;

public class LockRompeEmpate {

	private volatile int last;
	private volatile boolean lockInc = false;
	private volatile boolean lockDec = false;


	public int getLast(){
		return last;
	}

	public void takeLock(int i) {
		if(i == 1) lockInc = false;
		else lockDec = false;
		last = i;
		
		if(i == 1)
			while(getLock((i+1)%2) && last == i); 
	}

	public void releaseLock(int i) {
		if(i == 1) lockInc = true;
		else lockDec = true;
	}

	public boolean getLock(int i) {
		if(i == 1) return lockInc;
		else return lockDec;
	}

}
