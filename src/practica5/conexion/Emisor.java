package practica5.conexion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import practica5.controller.CONSTANTES;
import practica5.controller.Fichero;


public class Emisor extends Thread {
	private ServerSocket serverS;
	private int port;
	private Fichero file;
	private String user;
	
	public Emisor(int puerto,Fichero file,String user) {
		port = puerto;
		this.file = file;
		this.user = user;
		try {
			serverS = new ServerSocket(111);
		} catch (IOException e) {
			System.err.println("Error creación socket");
		}
	}
	
	public void run() {
		try {
			Socket sC = serverS.accept();
			
			//Parametros de salida
			ObjectOutputStream out = new ObjectOutputStream(sC.getOutputStream());
			out.flush();
			
			//Parametros de entrada
			ObjectInputStream in = new ObjectInputStream(sC.getInputStream());
			
			try {
								
				System.out.println(file.getName());
				FileReader f = new FileReader(CONSTANTES.RUTA + file.getName());
				BufferedReader b = new BufferedReader(f);
				
				String resul = "";
				String lin;
				while((lin = b.readLine())!= null)
					resul = resul + lin;
				
				out.writeObject(resul);
				b.close();
				System.out.println("Archivo cargado con exito y enviado");
			} catch (FileNotFoundException e) {
				System.err.println("Archivo no encontrado");
			}


		} catch (IOException e) {
			System.err.println("Error run del emisor");
		}
	}
}