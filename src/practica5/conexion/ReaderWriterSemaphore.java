package practica5.conexion;

import java.util.concurrent.Semaphore;

public class ReaderWriterSemaphore {
	
	private Semaphore numReadersLock= new Semaphore(1);
	private Semaphore writeLock = new Semaphore(1);
	private int numReaders = 0;
	
	public void startReadSem() {
		try {
			numReadersLock.acquire();
			numReaders++;
			if(numReaders == 1) {
				writeLock.acquire();
			}
			numReadersLock.release();
		} catch (InterruptedException e) {
			System.err.println("Error reader semaphore");
		}
		
	}
	
	public void endReadSem() {
		try {
			numReadersLock.acquire();
			numReaders--;
			if(numReaders == 0) {
				writeLock.release();
			}
			numReadersLock.release();
		} catch (InterruptedException e) {
			System.err.println("Error reader semaphore");
		}
		
	}
	
	public void startWriteSem() {
		try {
			writeLock.acquire();
		} catch (InterruptedException e) {
			System.err.println("Error writer semaphore");
		}
	}
	
	public void endWriteSem() {
		writeLock.release();
	}
}
