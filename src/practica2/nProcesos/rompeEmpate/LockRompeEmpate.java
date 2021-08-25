package practica2.nProcesos.rompeEmpate;

public class LockRompeEmpate {

	private int M;
	private volatile int last[]; //id del proceso que llegó ultimo a la etapa i
	private volatile int in[]; //Etapa en la que se ha parado cada proceso
	
	public LockRompeEmpate(int m) {	
		this.M = m;
		last = new int[M+1];
		in = new int[M+1];
		
		for(int i = 1; i <= M; i++) {
			last[i] = 0;
			in[i] = 0;
		}
	}
	
	public void takeLock(int i) {
		for(int j = 1; j <= M; j++) {
			in[i] = j;
			in = in;
			
		 	last[j] = i;
		 	last = last;
		 	
			//Me comparo con el resto de procesos y paro si debo
			for(int k = 1; k <= M; k++) {
				if(k != i)
					while((in[k] >= in[i]) && (last[j] == i));
			}
		}
	}

	public void releaseLock(int i) {
		in[i] = 0;
		in = in;
	}
	
}
