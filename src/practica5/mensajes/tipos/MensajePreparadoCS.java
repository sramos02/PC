package practica5.mensajes.tipos;

import java.io.Serializable;

import practica5.controller.CONSTANTES;
import practica5.controller.Fichero;

@SuppressWarnings("serial")
public class MensajePreparadoCS extends Mensaje implements Serializable {

	private String origen;
	private String destino;
	private String cliente;
	
	private String ip;
	private int puerto;
	private Fichero file;
	
	public MensajePreparadoCS(String origen, String destino, String cliente, String ip, int puerto,Fichero file) {
		this.origen = origen;
		this.destino = destino;
		this.cliente = cliente;
		this.ip = ip;
		this.puerto = CONSTANTES.PUERTO; //DUDA
		this.file = file;
	}
	
	@Override
	public int getTipo() {
		return 4;
	}

	@Override
	public String getOrigen() {
		return origen;
	}

	@Override
	public String getDestino() {
		return destino;
	}

	public String getCliente() {
		return cliente;
	}

	public String getIp() {
		return ip;
	}

	public int getPuerto() {
		return puerto;
	}
	
	public Fichero getFichero() {
		return file;
	}
}