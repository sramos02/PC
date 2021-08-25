package practica3.ProdCons;

public class Prod extends Thread{

	private int N;
	private Almacen alm;
	private AlmacenN almN;
	private boolean bufferN = false;

	public Prod(int N, Almacen alm) {
		this. N = N;
		this.alm = alm;
	}

	public Prod(int N, AlmacenN almN) {
		this.bufferN = true;
		this. N = N;
		this.almN = almN;
	}

	@Override
	public void run() {
		if(!bufferN) {
			for(int i = 0; i < N; i++){
				Producto nuevo = new Producto();
				alm.lockProductor();
				alm.almacenar(nuevo);
				alm.unlockProductor();
			}
		}
		
		
		else {
			for(int i = 0; i < N; i++){
				Producto nuevo = new Producto();
				almN.lockProductor();
				almN.almacenar(nuevo);
				almN.unlockProductor();
			}
		}
	}
}
