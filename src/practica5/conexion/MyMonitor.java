package practica5.conexion;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyMonitor {
	int numReaders = 0;
	int waitingReaders = 0;
	int numWriters = 0; //Maximo un escritor 
	int waitingWriters = 0;
	private Lock mutex = new ReentrantLock(true);
	
	
	Condition canRead = mutex.newCondition();
	Condition canWrite = mutex.newCondition();
	
	
	public void startWrite() {
		mutex.lock();
		if(numWriters == 1 || numReaders > 0) {
			try {
				waitingWriters++;
				mutex.unlock();
				
				canWrite.wait(); //Esperamos a que termine de escribir otro thread o de leer.
				
				mutex.lock();
				waitingWriters--;
				mutex.unlock();
			} catch (InterruptedException e) {
				System.err.println("Error en monitor startWrite");
			}
		}else {
			mutex.unlock();
		}
		
		mutex.lock();
		numWriters = 1;
		mutex.unlock();
	}
	
	public void endWrite() {
		mutex.lock();
		numWriters = 0;
		if(waitingReaders > 0) {
			canRead.notify();
		}else {
			if(waitingWriters > 0) {
				canWrite.notify();
			}
		}
		mutex.unlock();
	}
	
	public void startRead() {
		mutex.lock();
		if(numWriters == 1 || waitingWriters > 0) {
			try {
				waitingReaders++;
				mutex.unlock();
				
				canRead.wait(); //Esperamos a que acabe de escribir
				
				mutex.lock();
				waitingReaders--;
			} catch (InterruptedException e) {
				System.err.println("Error en el monitor startRead");
			}
		}
		numReaders++;
		mutex.unlock();
	}
	
	public void endRead() {
		mutex.lock();
		numReaders--;
		if(numReaders == 0) {
			if(numWriters > 0) {
				canWrite.notify();
			}
			
		}else {
			canRead.notify();
		}
		
		mutex.unlock();
	}
}
