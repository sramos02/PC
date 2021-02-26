package practica1;

public class Enteros {
	private volatile int num;
	
	public Enteros(int num) {
		this.num = num;
	}

	public void incrementa() {
		num++;
	}
	
	public void decrementa() {
		num--;
	}

	public int getValor() {
		return num;
	}
}
