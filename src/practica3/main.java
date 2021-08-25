package practica3;

import practica1.Enteros;
import practica3.ProdCons.Almacen;
import practica3.ProdCons.AlmacenN;
import practica3.ProdCons.Cons;
import practica3.ProdCons.Prod;
import practica3.mutex.DecSem;
import practica3.mutex.IncSem;
import practica3.mutex.LockSem;

public class main {

	public static void ej1(int M, int N) throws InterruptedException {
		Enteros res = new Enteros(0);
		LockSem alg = new LockSem();	

		IncSem[] incre = new IncSem[M+1];
		DecSem[] decre = new DecSem[M+1];

		for(int i = 1; i <= M; i++) {
			incre[i] = new IncSem(N, res, alg);
			decre[i] = new DecSem(N, res, alg);

			incre[i].start();
			decre[i].start();
		}

		for(int i = 1; i <= M; i++) {
			incre[i].join();
			decre[i].join();
		}

		System.out.println("El valor es: " + res.getValor());
	}

	public static void ej2(int M, int K, int N) throws InterruptedException {
		Almacen alm = new Almacen();

		Prod[] productores = new Prod[M];
		Cons[] consumidores = new Cons[K];

		for(int i = 0; i < M; i++) {
			productores[i] = new Prod(N, alm);
			productores[i].start();
		}
		for(int i = 0; i < K; i++) {
			consumidores[i] = new Cons(N, alm);
			consumidores[i].start();
		}		
		
		
		for(int i = 0; i < M; i++) {
			productores[i].join();
		}
		for(int i = 0; i < K; i++) {
			consumidores[i].join();
		}
				
		System.out.println("Se han ejecutado todos los procesos");
	}


	public static void ej3(int M, int K, int N) throws InterruptedException {
		AlmacenN alm = new AlmacenN(N);

		Prod[] productores = new Prod[M];
		Cons[] consumidores = new Cons[K];

		for(int i = 0; i < M; i++) {
			productores[i] = new Prod(N, alm);
			productores[i].start();
		}
		for(int i = 0; i < K; i++) {
			consumidores[i] = new Cons(N, alm);
			consumidores[i].start();
		}		
		
		
		for(int i = 0; i < M; i++) {
			productores[i].join();
		}
		for(int i = 0; i < K; i++) {
			consumidores[i].join();
		}
				
		System.out.println("Se han ejecutado todos los procesos");
	}


	public static void main(String[] args) {
		int M = 100;
		int K = 100;
		
		int N = 1000;

		try {
			ej1(M,N);
			ej2(M,K,N);
			ej3(M,K,N);
		} catch (InterruptedException e) {e.printStackTrace();}
	}
}