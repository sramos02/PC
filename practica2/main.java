package practica2;

import practica2.dosProcesos.ejecucionesD;
import practica2.nProcesos.ejecucionesN;

public class main {
		
	public static void main(String[] args) throws InterruptedException{
		
		int N = 1000; //Operaciones por proceso
		int M = 10;  //Procesos
		
		
		//Dos procesos
		//ejecucionesD.ej1(N);		//1. Rompe Empate
		//ejecucionesD.ej3(N); 		//2. Bakery
		
		//M procesos con N instrucciones cada uno
		//ejecucionesN.ej1(N,M);		//1. Rompe Empate
		//ejecucionesN.ej2(N,M); 		//2. Ticket
		ejecucionesN.ej3(N,M); 		//3. Bakery	
	}
}
