package practica1;

public class Matriz {

	private int N;
	private int[][] matriz;
	private int filas; 
	
	public Matriz(int n) {
		this.N = n;
		filas = 0;
		matriz = new int[N][N];
	}
	
	public Matriz(int[][] m) {
		matriz = m;
		filas = 0;
		N = m.length;
	}

	
	public int getSize() {
		return matriz.length;
	}

	public int[][] getValor() {
		return matriz;
	}

	public void print() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++)
				System.out.print(matriz[i][j] + " ");
			System.out.println();
		}
	}

	public void addValor(int[] fila) {
		matriz[filas] = fila;
		filas++;
	}
}
