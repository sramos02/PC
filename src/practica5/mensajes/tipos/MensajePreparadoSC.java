package practica5.mensajes.tipos;

import java.io.Serializable;

import practica5.controller.Fichero;

@SuppressWarnings("serial")
public class MensajePreparadoSC extends Mensaje implements Serializable {

	private String origen;
	private String destino;
	
	private String ip;
	private int puerto;
	private Fichero file;
	
	public MensajePreparadoSC(String origen, String destino, String ip, int puerto,Fichero file) {
		this.ip = ip;
		this.puerto = puerto;
		this.origen = origen;
		this.destino = destino;
		this.file = file;
	}
	
	@Override
	public int getTipo() {
		return 5;
	}

	@Override
	public String getOrigen() {
		return origen;
	}

	@Override
	public String getDestino() {
		return destino;
	}

	public String getIp() {
		return ip;
	}

	public int getPuerto() {
		return puerto;
	}
	public Fichero getFile() {
		return file;
	}
}
