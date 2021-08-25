package practica5.mensajes.tipos;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import practica5.controller.Usuario;

@SuppressWarnings("serial")
public class MensajeConexion extends Mensaje implements Serializable {

	private String origen;
	private String destino;
	private Usuario user;
	//private ObjectOutputStream fout;
	
	public MensajeConexion(String origen, String destino,Usuario user) {
		this.origen = origen;
		this.destino = destino;
		this.user = user;
		//this.fout = fout;
	}
	
	@Override
	public String getOrigen() {
		return this.origen;
	}

	@Override
	public String getDestino() {
		return this.destino;
	}

	@Override
	public int getTipo() {
		return -1; 
	}

	public Usuario getUsuario() {
		return this.user;
	}

//	public ObjectOutputStream fout() {
//		return this.fout;
//	}
//
//	public ObjectOutputStream getOutputStream() {
//		return this.fout;
//	}

}
