package practica4;

import practica4.ej1.Dec;
import practica4.ej1.Inc;
import practica4.ej1.Monitor;

import practica4.ej2.Dec2;
import practica4.ej2.Inc2;
import practica4.ej2.Monitor2;

public class main {

	public static void ej1(int M, int N) throws InterruptedException {
		
		Monitor bb = new Monitor();
		Inc[] incre = new Inc[M];
		Dec[] decre = new Dec[M];
		
		for(int i = 0; i < M; i++) {
			incre[i] = new Inc(N, bb);
			decre[i] = new Dec(N, bb);
			
			incre[i].start();
			decre[i].start();
		}		
		
		for(int i = 0; i < M; i++) {
			incre[i].join();
			decre[i].join();
		}
				
		System.out.println("Valor: " + bb.getData());
	}

	public static void ej2(int M, int N) throws InterruptedException {
		
		Monitor2 bb = new Monitor2();
		Inc2[] incre = new Inc2[M];
		Dec2[] decre = new Dec2[M];
		
		for(int i = 0; i < M; i++) {
			incre[i] = new Inc2(N, bb);
			decre[i] = new Dec2(N, bb);
			
			incre[i].start();
			decre[i].start();
		}		
		
		for(int i = 0; i < M; i++) {
			incre[i].join();
			decre[i].join();
		}
				
		System.out.println("Valor: " + bb.getData());
	}
	
	public static void main(String[] args) {
		int M = 10000;		
		int N = 1000;

		try {
			ej1(M,N);
			ej2(M,N);
		}
		catch (InterruptedException e) {e.printStackTrace();}
	}
}