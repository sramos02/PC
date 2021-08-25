package practica5.conexion;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import practica5.controller.CONSTANTES;
import practica5.controller.Fichero;

public class Receptor extends Thread{
	private int puerto;
	private String ip;
	private Socket sC;
	private Fichero file;
	
	public Receptor(int port,String ip,Fichero file) {
		puerto = port;
		this.ip = ip;
		this.file = file;
	}
	
	public void run() {
		try {
			InetAddress host = InetAddress.getByName("localhost"); 
			this.sC = new Socket(host,111); //Para abrir la comunicación
			ObjectOutputStream fout = new ObjectOutputStream(sC.getOutputStream());
			fout.flush();
			ObjectInputStream fin = new ObjectInputStream(sC.getInputStream());
			String resul = (String) fin.readObject();
			
			System.out.println();
			System.out.println(resul);
			
			FileWriter fw = new FileWriter(CONSTANTES.RUTA + "test.txt");
			BufferedWriter b = new BufferedWriter(fw);
			b.write(resul);
			b.close();
			System.out.println("Archivo compartido");
			
		} catch (Exception e) {
			System.err.println("Error socket receptor");
		}
	}
}