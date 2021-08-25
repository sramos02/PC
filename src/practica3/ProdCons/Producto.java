package practica3.ProdCons;

import java.util.Random;

public class Producto {
	private volatile int data;
	
	public Producto() {
		Random r = new Random();
		data = r.nextInt();
	}
}
