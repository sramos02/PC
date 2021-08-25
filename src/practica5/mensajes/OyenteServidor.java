package practica5.mensajes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import practica5.conexion.Emisor;
import practica5.conexion.MyLock;
import practica5.conexion.Receptor;
import practica5.controller.CONSTANTES;
import practica5.controller.Usuario;
import practica5.mensajes.tipos.Mensaje;
import practica5.mensajes.tipos.MensajeConexion;
import practica5.mensajes.tipos.MensajeConfirmacion;
import practica5.mensajes.tipos.MensajeEmitirFichero;
import practica5.mensajes.tipos.MensajePreparadoCS;
import practica5.mensajes.tipos.MensajePreparadoSC;

public class OyenteServidor extends Thread{

	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	private Usuario users;
	private MyLock miLock;

	public OyenteServidor(Usuario user, ObjectInputStream fin, ObjectOutputStream fout,MyLock lock) {
		this.users = user;
		this.fin = fin;
		this.fout = fout;
		miLock = lock;
	}

	public void run() {

		try {
			//System.out.println("Mensaje de conexion enviado");
			fout.writeObject(new MensajeConexion(users.getId(), "server", users));
			fout.flush();
			while(true) {
				Mensaje msg = (Mensaje) fin.readObject();
				miLock.takeLock(2);
				switch(msg.getTipo()) {
				case -1:
					MensajeConfirmacion msgCon = (MensajeConfirmacion) msg;
					
					System.out.println(msgCon.getMsg());
					
					break;
				case 2:
					fin.close();
					fout.close();
					
					System.out.println("Conexión cerrada, puede cerrar la consola");
					
					return;
				case 3:
					MensajeEmitirFichero msgEmi = (MensajeEmitirFichero) msg;
					fout.writeObject(new MensajePreparadoCS(users.getId(), "server", msgEmi.getOrigen(), "ip_servidor", CONSTANTES.PUERTO, msgEmi.getNombre_Fichero()));
					Emisor em = new Emisor(CONSTANTES.PUERTO,msgEmi.getNombre_Fichero(),msgEmi.getNombre_Cliente());
					em.start();
					break;
				case 5:
					MensajePreparadoSC msgSC = (MensajePreparadoSC) msg;
					Receptor rec = new Receptor(msgSC.getPuerto(), msgSC.getIp(), msgSC.getFile());
					rec.start();
					break;

				default: 
					
					System.out.println("Mensaje no tratado");
					
				}
				miLock.releaseLock(2);
				fout.flush();
			}
		}catch (ClassNotFoundException e) { e.printStackTrace();}
		catch (IOException e) { e.printStackTrace(); /*System.err.println("Excepcion en enviar mensaje");*/ }
	}

}