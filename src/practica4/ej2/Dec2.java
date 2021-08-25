package practica4.ej2;

public class Dec2 extends Thread{

	private int N;
	private Monitor2 bb;
	
	public Dec2(int N, Monitor2 bb) {
		this. N = N;
		this.bb = bb;
	}

	@Override
	public void run() {
		for(int i = 0; i < N; i++)
			bb.decrementa();
	}
}