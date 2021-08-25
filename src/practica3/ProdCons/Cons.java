package practica3.ProdCons;

public class Cons extends Thread{
	private int N;
	private Almacen alm;
	private AlmacenN almN;
	private boolean bufferN = false;

	
	public Cons(int N, Almacen alm) {
		this. N = N;
		this.alm = alm;
	}
	
	public Cons(int N, AlmacenN almN) {
		this.bufferN = true;
		this. N = N;
		this.almN = almN;
	}

	@Override
	public void run() {
		if(!bufferN) {
		for(int i = 0; i < N; i++){
			alm.lockConsumidor();
			alm.extraer();
			alm.unlockConsumidor();
		}
		}
		
		else {
			for(int i = 0; i < N; i++){
				almN.lockConsumidor();
				almN.extraer();
				almN.unlockConsumidor();
			}
		}
	}
}
