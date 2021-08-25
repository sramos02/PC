package practica5.mensajes.tipos;

import java.io.Serializable;

import practica5.controller.Fichero;

@SuppressWarnings("serial")
public class MensajePedirFichero extends Mensaje implements Serializable {

	//Información sobre el mensaje
	private String origen;
	private String destino;
	
	private Fichero fichero;
	private String nombre_user;
	
	
	public MensajePedirFichero(String origen, String destino, Fichero nombreFich, String nombreUser){
		this.origen = origen;
		this.destino = destino;
		this.fichero = nombreFich;
		this.nombre_user = nombreUser;
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
		return 3;
	}

	public String getUser() {
		return nombre_user;
	}

	public Fichero getFich() {
		return fichero;
	}
}