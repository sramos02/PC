package practica2.dosProcesos;

import practica1.Enteros;
import practica2.dosProcesos.backery.DecBakery;
import practica2.dosProcesos.backery.IncBakery;
import practica2.dosProcesos.backery.LockBakery;
import practica2.dosProcesos.rompeEmpate.DecRompeEmpate;
import practica2.dosProcesos.rompeEmpate.IncRompeEmpate;
import practica2.dosProcesos.rompeEmpate.LockRompeEmpate;

public class ejecucionesD {

	//Dos procesos que ejecutan N operaciones cada uno
	
	
	public static void ej1(int N) throws InterruptedException {
		Enteros res = new Enteros(0);
		LockRompeEmpate alg = new LockRompeEmpate();
	
		IncRompeEmpate incre = new IncRompeEmpate(N, res, alg);
		DecRompeEmpate decre = new DecRompeEmpate(N, res, alg);
		
		incre.start();
		decre.start();
		
		incre.join();
		decre.join();
	
		System.out.println("El valor es: " + res.getValor());
	}
		
	
	
	public static void ej2(int N) throws InterruptedException {
		//TO DO
	}
	
	
	
	public static void ej3(int N) throws InterruptedException {
		Enteros res = new Enteros(0);
		LockBakery alg = new LockBakery();
	
		IncBakery incre = new IncBakery(N, res, alg);
		DecBakery decre = new DecBakery(N, res, alg);
		
		incre.start();
		decre.start();
		
		incre.join();
		decre.join();
	
		System.out.println("El valor es: " + res.getValor());
	}


	
}
