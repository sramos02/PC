package practica2.nProcesos;

import practica1.Enteros;
import practica2.nProcesos.bakery.DecBakery;
import practica2.nProcesos.bakery.IncBakery;
import practica2.nProcesos.bakery.LockBakery;
import practica2.nProcesos.rompeEmpate.DecRompeEmpate;
import practica2.nProcesos.rompeEmpate.IncRompeEmpate;
import practica2.nProcesos.rompeEmpate.LockRompeEmpate;
import practica2.nProcesos.ticket.DecTicket;
import practica2.nProcesos.ticket.IncTicket;
import practica2.nProcesos.ticket.LockTicket;

public class ejecucionesN {

//2M procesos que ejecutan N instrucciones cada uno
public static void ej1(int N, int M) throws InterruptedException {
	Enteros res = new Enteros(0);
	LockRompeEmpate alg = new LockRompeEmpate(2*M);	
	
	IncRompeEmpate[] incre = new IncRompeEmpate[2*M+1];
	DecRompeEmpate[] decre = new DecRompeEmpate[2*M+1];
	
	for(int i = 1; i <= 2*M; i+=2) {
		incre[i] = new IncRompeEmpate(N, i, res, alg);
		decre[i+1] = new DecRompeEmpate(N, i+1, res, alg);
		
		incre[i].start();
		decre[i+1].start();
	}
	
	for(int i = 1; i <= 2*M; i+=2) {
		incre[i].join();
		decre[i+1].join();
	}
	
	System.out.println("El valor es: " + res.getValor());
}
	
	//2M procesos que ejecutan N instrucciones cada uno
	public static void ej2(int N, int M) throws InterruptedException {
		Enteros res = new Enteros(0);
		LockTicket alg = new LockTicket(2*M);
	
		IncTicket[] incre = new IncTicket[2*M+1];
		DecTicket[] decre = new DecTicket[2*M+1];
		
		for(int i = 1; i <= 2*M; i+=2) {
			incre[i] = new IncTicket(i, N, alg, res);
			decre[i+1] = new DecTicket(i+1, N, alg, res);
	
			incre[i].start();
			decre[i+1].start();
		}
		
		for(int i = 1; i <= 2*M; i+=2) {
			incre[i].join();
			decre[i+1].join();
		}
		
		System.out.println("El valor es: " + res.getValor());
	}
	
	//2M procesos que ejecutan N instrucciones cada uno
	public static void ej3(int N, int M) throws InterruptedException {
		Enteros res = new Enteros(0);
		LockBakery alg = new LockBakery(2*M); //[0, 1..N]

		IncBakery[] incre = new IncBakery[2*M+1];
		DecBakery[] decre = new DecBakery[2*M+1];

		for(int i = 1; i <= 2*M; i+=2) {
			incre[i] = new IncBakery(i, N, alg, res);
			decre[i+1] = new DecBakery(i+1, N, alg, res);

			incre[i].start();
			decre[i+1].start();
		}

		for(int i = 1; i <= 2*M; i+=2) {
			incre[i].join();
			decre[i+1].join();
		}

		System.out.println("El valor es: " + res.getValor());
	}
	
}
