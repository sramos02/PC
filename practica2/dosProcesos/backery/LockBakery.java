package practica2.dosProcesos.backery;

public class LockBakery {

	private volatile int turnoInc; //turno1
	private volatile int turnoDec; //turno2

	
	public LockBakery() {
		turnoInc = 0;
		turnoDec = 0;
	}
	
	public void takeLockInc() {
		turnoInc = turnoDec + 1;
	}
	
	public void takeLockDec() {
		turnoDec = turnoInc + 1;
	}

	public int getLockInc() {
		return turnoInc;
	}
	
	public int getLockDec() {
		return turnoDec;
	}

	public void setDec(int i) {
		turnoDec = i;
	}
	
	public void setInc(int i) {
		turnoInc = i;
	}

}
