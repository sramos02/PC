package practica4.ej2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor2 {

	private int data[];
	private int ini, fin;
	private int nInc, nDec;
	
	private Lock mutex;
	private Condition lleno, vacio;
	
	
    public Monitor2(int n){
    	mutex = new ReentrantLock(); //Lock, mejor que crear nosotros la clase
	    lleno= mutex.newCondition();
	    vacio= mutex.newCondition();
	    
	    ini = 0; fin = 0;
	    nInc = 0; nDec = 0;
	
	    data=new int[n];
	    for(int i = 0; i < n; i++){
	    	data[i] = 0;
	    }
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
