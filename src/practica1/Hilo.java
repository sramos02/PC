package practica1;

public class Hilo extends Thread{

	int id;
	int tiempo;
	
	public Hilo(int id, int tiempo) {
		super();
		this.id = id;
		this.tiempo = tiempo;
	}
	
	public void run() {
		System.out.println(id);
		try {
			sleep(tiempo);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(id);
	}
}
