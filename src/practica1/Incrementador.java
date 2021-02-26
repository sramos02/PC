package practica1;

public class Incrementador extends Thread {
	
	Enteros res;
	
	public Incrementador(Enteros res) {
		super();
		this.res = res;
	}
	
	@Override
	public void run() {
		//int aux = res.getValor();
		res.incrementa();
		//System.out.println(aux + " + 1 = " + res.getValor());
	}
	
}
