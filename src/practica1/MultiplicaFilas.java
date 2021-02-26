package practica1;

public class MultiplicaFilas extends Thread{
	int i, N;
	Matriz A, B, C;
	
	public MultiplicaFilas(int i, Matriz A, Matriz B, Matriz C){
		this.i = i;
		this.N = A.getSize();
		
		this.A = A;
		this.B = B;
		this.C = C;
	}
	
	public void run() {
		int[] fila = new int[N];
		for(int j = 0; j < N; j++)
			for(int k = 0; k < N; k++) 
				fila[j] = A.getValor()[i][k] + B.getValor()[k][j];
		C.addValor(fila);
	}
	
}
