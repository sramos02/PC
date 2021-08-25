package practica5.conexion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import practica5.controller.CONSTANTES;
import practica5.controller.Fichero;
import practica5.controller.Usuario;
import practica5.mensajes.OyenteServidor;
import practica5.mensajes.tipos.MensajeCerrarConexion;
import practica5.mensajes.tipos.MensajeConexion;
import practica5.mensajes.tipos.MensajeListaFicheros;
import practica5.mensajes.tipos.MensajeListaUsuarios;
import practica5.mensajes.tipos.MensajePedirFichero;

public class Cliente {

	private Usuario user;
	private Socket s;
	private MyLock miLock;


	public Cliente(Usuario user, String ip_server) {
		try {
			InetAddress host = InetAddress.getByName("localhost"); 
			this.s = new Socket(host,CONSTANTES.PUERTO);
			this.user = user;
			ObjectOutputStream fout = new ObjectOutputStream(s.getOutputStream());
			fout.flush();
			ObjectInputStream fin = new ObjectInputStream(s.getInputStream());

			//Creo mi lock propio para este cliente y controlar la salida por consola
			miLock = new MyLock();

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			OyenteServidor os = new OyenteServidor(user, fin , fout,miLock);
			os.start();

			//fout.writeObject(new MensajeConexion(user.getId(), "server", this.user));

			boolean end = false;
			while(!end) { //Hasta que el programa no termine
				Thread.sleep(50);
				miLock.takeLock(1);
				String opciones = "1 - Mostrar lista de usuarios conectados\n"
						+ "2 - Pedir archivo\n"
						+ "3 - Cerrar conexión\n";
				System.out.println(opciones);
				System.out.println("Introduce una opción: ");

				miLock.releaseLock(1);
				int opc = Integer.parseInt(br.readLine());
				if(opc == 0) {
					fout.writeObject(new MensajeConexion(user.getId(), "server", this.user));
				}
				if(opc == 1) {
					fout.writeObject(new MensajeListaUsuarios(user.getId(), "server"));
				}
				else if(opc == 2) {
					fout.writeObject(new MensajeListaFicheros(user.getId(), "server"));
					String fichero = br.readLine(); 

					List<String> allFiles = getAllFiles();
					if (allFiles.contains(fichero)) {
						fout.writeObject(new MensajePedirFichero(user.getId(), "server", new Fichero(fichero, false), this.user.getId()));
						addFile(user, fichero);
					}
				}
				else if (opc == 3) {
						fout.writeObject(new MensajeCerrarConexion(user.getId(), "server"));
						end = true;
					}
					else return;
					fout.flush();
				}

			

		} catch (Exception e) { System.err.println("Se ha terminado la conexión de manera abrupta");}
	}

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			System.out.println("Bienvenido. Introducza su nombre de usuario: ");
			String nombre = br.readLine();
			System.out.println("Introduzca su IP pública: ");
			String ip = br.readLine();

			showFiles(nombre);
			System.out.println("¿Desea añadir algún archivo nuevo? [S/N]: ");
			String opt = br.readLine();

			while(!opt.equals("N") && !opt.equals("n") && !opt.equals("S") && !opt.equals("s")) {
				System.err.println("Entrada invalida [S/N]: ");
				opt = br.readLine();
			}


			int numNuevos = -1;
			List<Fichero> ficheros = new ArrayList<Fichero>();			
			if(opt.equals("N") || opt.equals("n"));
			else {
				System.out.println("Para terminar introduzca una linea vacía.");


				System.out.println("Introduzca el nombre del archivo: ");
				String file = br.readLine();

				while(!file.equals(""
						+ "")) {
					ficheros.add(new Fichero(CONSTANTES.RUTA + file));
					System.out.println("Introduzca el nombre del archivo: ");
					file = br.readLine();
					numNuevos++;
				}

			}

			System.out.println("¿A que servidor desea conectarse?: ");
			String server = br.readLine();
			System.out.println();

			ficheros = updateFicheros(ficheros, nombre);
			Usuario user = new Usuario(nombre, ip, ficheros);
			addInfoToFile(user);
			new Cliente(user, server);

		} catch (IOException e) {System.err.println("Error al cargar los archivos");}

	}


	private List<String> getAllFiles() {
		List<String> ficheros = new ArrayList<String>();
		File fileToBeModified = new File(CONSTANTES.RUTA + "users.txt");

		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();

			while (line != null){
				if(!line.equals(user.getId())) {

					line = reader.readLine();

					String[] parts = line.split(" ");
					for(int i = 0; i < parts.length ;i++)
						ficheros.add(parts[i]);

				}
				else line = reader.readLine();

				line = reader.readLine();

			}
			reader.close();
		}
		catch (IOException e){ e.printStackTrace();}
		return ficheros;
	}

	private static void showFiles(String user) {
		List<Fichero> ficheros = updateFicheros(new ArrayList<Fichero>(), user);

		if(ficheros.isEmpty()) System.out.println("No tiene archivos cargados");
		else {
			System.out.println("Tus ficheros: ");
			for(int i = 0; i < ficheros.size(); i++)
				System.out.println(ficheros.get(i).getName() + " ");
		}
	}

	private static List<Fichero> updateFicheros(List<Fichero> n, String user) {
		List<Fichero> ficheros = n;
		File fileToBeModified = new File(CONSTANTES.RUTA + "users.txt");

		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();

			while (line != null){
				if(line.equals(user)) {
					line = reader.readLine();

					String[] parts = line.split(" ");
					for(int i = 0; i < parts.length ;i++)
						if(!parts[i].equals(""))
							ficheros.add(new Fichero(CONSTANTES.RUTA + parts[i]));
				}
				line = reader.readLine();

			}
			reader.close();
		}
		catch (IOException e){ e.printStackTrace();}
		return ficheros;
	}
	
	private static void addFile(Usuario user, String newfile) {
		String newStr = user.getId() + System.lineSeparator() + newfile + " " + user.getFiles();
		updateFile(user, newStr);
	}

	private static void addInfoToFile(Usuario user) {
		try {
			BufferedReader fr = new BufferedReader(new FileReader(CONSTANTES.RUTA + "users.txt"));

			//Buscar el usuario en el fichero
			String line = fr.readLine();
			boolean found = false;

			while(line != null && !found) {
				if(line.equals(user.getId())) {
					found = true;
					line = fr.readLine();
				}
				else {
					line = fr.readLine();
					line = fr.readLine();
				}
				
			}
			fr.close();


			String newStr = user.getId() + System.lineSeparator() + user.getFiles();

			if(!found) addToFile(user, newStr);
			else updateFile(user, newStr);

		} catch (FileNotFoundException e) {System.err.println("Fichero base no encontrado");}
		catch (IOException e) {e.printStackTrace();}
	}

	private static void updateFile(Usuario user, String newStr){
		File fileToBeModified = new File(CONSTANTES.RUTA + "users.txt");
		String oldContent = "";
		String old = "";

		try{
			BufferedReader reader = new BufferedReader(new FileReader(fileToBeModified));
			String line = reader.readLine();

			while (line != null){
				if(line.equals(user.getId())) {
					oldContent = oldContent + line + System.lineSeparator();     
					old += line + System.lineSeparator();
					line = reader.readLine();

					oldContent = oldContent + line + System.lineSeparator();
					old += line + System.lineSeparator();
					//newStr += line + System.lineSeparator(); //Añadimos los ficheros que ya tenía
					line = reader.readLine();
				}
				else {
					oldContent = oldContent + line + System.lineSeparator();     
					line = reader.readLine();
				}
			}

			String newContent = oldContent.replaceAll(old, newStr);
			FileWriter writer = new FileWriter(fileToBeModified);
			writer.write(newContent);

			reader.close();
			writer.close();
		}
		catch (IOException e){ e.printStackTrace();}
	}

	private static void addToFile(Usuario user, String newStr) {
		File fileToBeModified = new File(CONSTANTES.RUTA + "users.txt");
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(fileToBeModified));

			newStr = newStr + System.lineSeparator();
			String old = "";
			String line;

			while((line = reader.readLine()) != null) {
				old += line + System.lineSeparator();
			}

			FileWriter writer = new FileWriter(fileToBeModified);
			writer.write(old + newStr);
			writer.close();
			reader.close();
		} catch (IOException e) {e.printStackTrace();};
	}
}
