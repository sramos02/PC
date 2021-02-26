package practica1;

public class main {
	
	public static void ej1() throws InterruptedException {
		int N = 5;
		Hilo[] lista = new Hilo[N];
		
		for(int i = 0; i < N; i++) {
			lista[i] = new Hilo(i, (int)Math.random()*2000);
			lista[i].start();
		}
		
		for(int i = 0; i < N; i++)
			lista[i].join();
		
		System.out.println("Se han ejecutado todos los procesos correctamente");	
	}
	
	public static void ej2() throws InterruptedException{
		Enteros res = new Enteros(0);
		int N = 10000; 
		int M = 1000;
		
		Incrementador[] incre = new Incrementador[N];
		Decrementador[] decre = new Decrementador[N];
		
		for(int i = 0; i < M; i++) {
			incre[i] = new Incrementador(res);
			decre[i] = new Decrementador(res);
	
			incre[i].start();
			decre[i].start();
		}
		
		for(int i = 0; i < M; i++) {
			incre[i].join();
			decre[i].join();
		}
		
		System.out.println("El valor es: " + res.getValor());
	}
	
	public static void ej3() throws InterruptedException {
		
		int N = 3;
		int[][] aux1 = {{1,2,3},{4,5,6},{7,8,9}};
		int[][] aux2 = {{1,0,0},{0,1,0},{0,0,1}};
		
		Matriz A = new Matriz(aux1);
		Matriz B = new Matriz(aux2);
		Matriz C = new Matriz(N);
		
		MultiplicaFilas[] filas = new MultiplicaFilas[N];
	
		for(int i = 0; i < N; i++) {			
			filas[i] = new MultiplicaFilas(i,A,B,C);
			filas[i].start();
		}
		
		for(int i = 0; i < N; i++)
			filas[i].join();
		
		C.print();
	}
	
	public static void main(String[] args) throws InterruptedException{
		ej1();
		ej2();
		ej3();
	}
}
