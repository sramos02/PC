package practica1;

public class Decrementador extends Thread{
	
	Enteros res;
	
	public Decrementador(Enteros res) {
		super();
		this.res = res;
	}
	
	public void run() {
		//int aux = res.getValor();
		res.decrementa();
		//System.out.println(aux + " - 1 = " + res.getValor());
	}
	
}
