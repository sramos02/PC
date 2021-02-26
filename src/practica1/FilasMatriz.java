package practica1;

public class FilasMatriz{

	int N;
	int[][] A, B;
	public int[] filas = new int[N];
	
	public FilasMatriz(int n, int[][] A, int[][] B) {
		this.N = n;
		this.A = A;
		this.B = B;
		init();
	}
	
	private void init() {
		for(int i = 0; i < N; i++)
			filas[i] = 0;
	}

	public int[] getValor() {
		return filas;
	}
}
