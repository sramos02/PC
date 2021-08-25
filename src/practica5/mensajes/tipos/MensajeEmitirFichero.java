package practica5.mensajes.tipos;

import java.io.Serializable;

import practica5.controller.Fichero;

@SuppressWarnings("serial")
public class MensajeEmitirFichero extends Mensaje implements Serializable {

	private String origen;
	private String destino;
	
	private Fichero fichero;
	private String nombre_cliente;
	
	public MensajeEmitirFichero(String user, Fichero fichero, String origen, String destino) {
		this.nombre_cliente = user;
		this.fichero = fichero;
		this.origen = origen;
		this.destino = destino;
	}
	
	@Override
	public int getTipo() {
		return 3;
	}

	@Override
	public String getOrigen() {
		return origen;
	}

	@Override
	public String getDestino() {
		return destino;
	}
	
	public Fichero getNombre_Fichero() {
		return fichero;
	}
	
	public String getNombre_Cliente() {
		return nombre_cliente;
	}

}