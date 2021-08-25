package practica5.mensajes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

import practica5.conexion.MyMonitor;
import practica5.conexion.ReaderWriterSemaphore;
import practica5.conexion.Servidor;
import practica5.controller.Usuario;
import practica5.mensajes.tipos.Mensaje;
import practica5.mensajes.tipos.MensajeCerrarConexion;
import practica5.mensajes.tipos.MensajeConexion;
import practica5.mensajes.tipos.MensajeConfirmacion;
import practica5.mensajes.tipos.MensajeEmitirFichero;
import practica5.mensajes.tipos.MensajeListaFicheros;
import practica5.mensajes.tipos.MensajeListaUsuarios;
import practica5.mensajes.tipos.MensajePedirFichero;
import practica5.mensajes.tipos.MensajePreparadoCS;
import practica5.mensajes.tipos.MensajePreparadoSC;
import practica5.mensajes.tipos.MensajeUsuariosConFicheros;

public class OyenteCliente extends Thread {

	private ObjectInputStream in;

	private List<Usuario> conectados;
	private Servidor server;
	private List<ObjectOutputStream> out;
	private ReaderWriterSemaphore semOut;
	private MyMonitor monitor;
	
	private int pos;

	public OyenteCliente(Servidor server, List<Usuario> conectados, ObjectInputStream in, List<ObjectOutputStream> out,ReaderWriterSemaphore sem,MyMonitor monitor) {
		this.server = server;
		this.in = in;
		this.conectados = conectados;
		this.out = out;
		this.semOut = sem;
		this.monitor = monitor;
	}

	public void run() {

		try {
			while (true) {
				Mensaje msg = (Mensaje) in.readObject();

				switch (msg.getTipo()) {
				case -1:
					System.out.println(msg.getOrigen() + " se ha conectado");
					this.pos = mensajeConexion(msg);
					break;
				case 1:
					System.out.println(msg.getOrigen() + " ha pedido la lista de usuarios");
					mensajeListaUsuarios(msg, pos);
					break;
				case 2:
					System.out.println(msg.getOrigen() + " se ha desconectado");
					mensajeCierraConexion(msg);
					return;
				case 3:
					System.out.println(msg.getOrigen() + " ha pedido un archivo");
					mensajePedirFichero(msg);
					break;
				case 4:
					mensajePreparadoClienteServidor(msg);
					break;
				case 6:
					mensajeListaFicheros(msg, pos);
					break;
				default:
					System.out.println("Mensaje no tratado");
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println("Error clase");
		} catch (IOException e) {
			System.err.println("El usuario se ha desconectado abruptamente");
		}
	}

	// ------------------- MENSAJES ---------------------------

	private int mensajeConexion(Mensaje msg) throws IOException {
		/*
		 * guardar informacion del usuario (en las tablas) envio mensaje confirmacion
		 * conexion fout
		 */

		// Guarda la información en las tablas
		MensajeConexion msg0 = (MensajeConexion) msg;

		monitor.startRead();
		if(conectados.contains(msg0.getUsuario())) {
			System.err.println("Usuario ya conectado");
			monitor.endRead();
			return out.size() - 1;
		}
		else {
			monitor.endRead();
			monitor.startWrite();
			conectados.add(msg0.getUsuario());
			monitor.endWrite();
		}

		semOut.startReadSem();
		out.get(out.size() - 1)
		.writeObject(
				new MensajeConfirmacion(msg0.getUsuario().getId() + " conectado satisfactoriamente \n"
						+ "Actualmene hay " + conectados.size() + " usuarios conectados.\n\n",
						msg0.getOrigen(), msg0.getDestino()));
		int dev = out.size() - 1;
		semOut.endReadSem();
		return dev;

	}

	private void mensajeListaUsuarios(Mensaje msg, int lastPos) throws IOException {
		/*
		 * crear un mensaje con la informacion de usuarios en sistema envio mensaje
		 * confirmacion lista usuarios fout
		 */

		MensajeListaUsuarios msg1 = (MensajeListaUsuarios) msg;

		semOut.startReadSem();
		out.get(pos).writeObject(
				new MensajeConfirmacion("Usuarios en la red: " + listaUsuarios(), msg1.getOrigen(), msg1.getDestino()));
		out.get(pos).flush();
		semOut.endReadSem();

		
	}

	private void mensajeCierraConexion(Mensaje msg) throws IOException {
		/*
		 * eliminar informacion del usuario (en las tablas) envio mensaje confirmacion
		 * cerrar conexion fout
		 */

		MensajeCerrarConexion msg2 = (MensajeCerrarConexion) msg;
		// Busco el usuario y lo elimino si existe
		int pos = -1;
		monitor.startRead();
		for(int i = 0; i < conectados.size() && pos == -1; i++) { 
			if(conectados.get(i).getId().equals(msg2.getOrigen())) pos = i;
		}
		monitor.endRead();
		semOut.startReadSem();
		out.get(pos).writeObject(new MensajeCerrarConexion(msg2.getOrigen(), msg2.getDestino()));
		out.get(pos).flush();
		out.get(pos).close();
		semOut.endReadSem();
		
		semOut.startWriteSem();
		out.remove(pos);
		semOut.endWriteSem();

		
		monitor.startWrite();
		conectados.remove(pos);
		monitor.endWrite();
		
		server.removeUsers(conectados, out, pos);
	}


	private void mensajePedirFichero(Mensaje msg) throws IOException {
		/*
		 * buscar usuario que contiene el fichero y obtener fout2 envio mensaje MENSAJE_
		 * EMITIR_FICHERO por fout2
		 */

		MensajePedirFichero msg3 = (MensajePedirFichero) msg;
		int pos = -1;
		monitor.startRead();
		for(int i = 0; i < conectados.size() && pos == -1; i++) {  //Este siempre lo va a contrar, por eso no controlamos
			if(conectados.get(i).getId().equals(msg3.getOrigen())) pos = i;
			
		}
		monitor.endRead();
		int posDestino = -1;
		monitor.startRead();
		for(int i = 0; i < conectados.size() && posDestino == -1; i++) { 
			if(conectados.get(i).getId().equals(msg3.getUser())) posDestino = i;
		}
		if(posDestino == -1) {
			System.err.println("Usuario no encontrado");
			return;
		}
		monitor.endRead();

		monitor.startWrite();
		conectados.get(pos).addFile(msg3.getFich());
		monitor.endWrite();
		
		semOut.startReadSem();
		out.get(pos).writeObject(
				new MensajeEmitirFichero(msg3.getUser(), msg3.getFich(), msg3.getOrigen(), msg3.getUser()));
		out.get(pos).flush();
		semOut.endReadSem();

	}


	private void mensajePreparadoClienteServidor(Mensaje msg) throws IOException {
		/*
		 * buscar fout1 (flujo del cliente al que hay que enviar la informacion) envio
		 * fout1 mensaje MENSAJE_PREPARADO_SERVIDORCLIENTE
		 */

		MensajePreparadoCS msg4 = (MensajePreparadoCS) msg;

		monitor.startRead();
		int pos = -1;
		for(int i = 0; i < conectados.size() && pos == -1; i++) {  //Este siempre lo va a contrar, por eso no controlamos
			if(conectados.get(i).getId().equals(msg4.getCliente())) pos = i;
		}
		monitor.endRead();

		semOut.startReadSem();
		out.get(pos).writeObject(
				new MensajePreparadoSC(msg4.getDestino(), msg4.getOrigen(), msg4.getIp(), msg4.getPuerto(), msg4.getFichero()));
		out.get(pos).flush();
		semOut.endReadSem();


	}

	private void mensajeListaFicheros(Mensaje msg, int lastPos) throws IOException {
		MensajeListaFicheros msg5 = (MensajeListaFicheros) msg;

		semOut.startReadSem();
		out.get(pos).writeObject(
				new MensajeConfirmacion("Archivos disponibles: \n" + listaFicheros(msg5.getOrigen()) + "\nCual desea pedir?: ", msg5.getOrigen(), msg5.getDestino()));
		out.get(pos).flush();
		semOut.endReadSem();
	
	}

	//	private void mensajeUsuariosConFicheros(Mensaje msg, int lastPos) throws IOException {
	//		MensajeUsuariosConFicheros msg6 = (MensajeUsuariosConFicheros) msg;
	//
	//		out.get(pos).writeObject(
	//				new MensajeConfirmacion("Usuarios disponibles: " + listaUsuariosConFicheros(msg.getOrigen()) + "\nNombre del usuario emisor: ", msg6.getOrigen(), msg6.getDestino()));
	//		out.get(pos).flush();
	//	}

	private String listaUsuarios() {
		String lista = "\n";

		monitor.startRead();
		for(int i = 0; i < conectados.size(); i++) {
			lista += conectados.get(i).getId() + ": ";
			Usuario u = conectados.get(i);

			for(int j = 0; j < u.getData().size(); j++) {
				String act = u.getData().get(j).getName() + " | ";
				lista += act;
			}
			lista += "\n";
		}
		monitor.endRead();
		return lista;
	}


	public void deleteUser(List<Usuario> users,List<ObjectOutputStream> out, int indice) {
		this.conectados = users;
		this.out = out;
		if(indice < pos) {
			this.pos = this.pos - 1;
		}
	}

	public String listaFicheros(String name) {
		String lista = "";

		monitor.startRead();
		for(int i = 0; i < conectados.size(); i++) {
			if(conectados.get(i).getData().size() > 0 && !conectados.get(i).getId().equals(name)) {
				lista += conectados.get(i).getId() + ": ";
				Usuario u = conectados.get(i);

				for(int j = 0; j < u.getData().size(); j++) {
					String act = u.getData().get(j).getName() + " | ";
					lista += act;
				}
				lista += "\n";
			}
		}
		monitor.endRead();

		return lista;
	}
}
