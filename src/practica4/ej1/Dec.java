package practica4.ej1;

public class Dec extends Thread{

	private int N;
	private Monitor bb;
	
	public Dec(int N, Monitor bb) {
		this. N = N;
		this.bb = bb;
	}

	@Override
	public void run() {
		for(int i = 0; i < N; i++)
			bb.decrementa();
	}
}