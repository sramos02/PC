package practica4.ej1;


public class Monitor {

	private int data;
	
    public Monitor(){
	     data = 0;
	}
    
    public synchronized void incrementa(){
    	data += 1;
    }

    public synchronized void decrementa(){
    	data -= 1;
    }

    public int getData() {
    	return data;
    }

}
