package practica5.conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import practica5.controller.CONSTANTES;
import practica5.controller.Usuario;
import practica5.mensajes.OyenteCliente;


//ESTA PRACTICA HA SIDO REALIZADA POR DOS ESTUDIANTES
//SERGIO RAMOS MESA
//DAVID DEL CERRO DOMINGUEZ

public class Servidor {

	private List<Usuario> usuariosConectados;
	private List<ObjectInputStream> in;
	private List<ObjectOutputStream> out;
	private List<OyenteCliente> listOc;

	//Declaracion semaforos
	private ReaderWriterSemaphore semOut = new ReaderWriterSemaphore(); //Controla el acceso a la lista de out.
	private ReaderWriterSemaphore semListOc = new ReaderWriterSemaphore();
	
	//Declaracion monitor
	private MyMonitor monitor;
	public Servidor(int puerto) {
		in = new ArrayList<ObjectInputStream>();
		out = new ArrayList<ObjectOutputStream>();
		listOc = new ArrayList<OyenteCliente>();
		usuariosConectados = new ArrayList<Usuario>();
		
		monitor = new MyMonitor();

		try {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Introduce la ip del servidor: ");
			String nombre = br.readLine();

			ServerSocket ss = new ServerSocket(puerto);
			System.out.println("Esperando conexión...");
			while(true) {



				Socket s = ss.accept();

				//Parametros de salida
				ObjectOutputStream act_out = new ObjectOutputStream(s.getOutputStream());
				semOut.startWriteSem();
				out.add(act_out);
				act_out.flush();
				semOut.endWriteSem();
				
			

				//Parametros de entrada
				ObjectInputStream act_in = new ObjectInputStream(s.getInputStream());		
				in.add(act_in);

				OyenteCliente oc = new OyenteCliente(this,usuariosConectados, act_in, out,semOut,monitor);
				semListOc.startWriteSem();
				listOc.add(oc);
				oc.start();
				semListOc.endWriteSem();
				
			}
		}
		catch (IOException e) {System.err.println("Error al conectar con el servidor");}
	}


	public void removeUsers(List<Usuario> usuarios,List<ObjectOutputStream> out,int indice) {
		this.out = out;
		semListOc.startWriteSem();
		this.listOc.remove(indice);
		for(int i = 0; i < listOc.size();i++) {
			listOc.get(i).deleteUser(usuarios, out, indice);
		}
		semListOc.endWriteSem();
		
	}

	public static void main(String[] args) {
		new Servidor(CONSTANTES.PUERTO);
	}

}
