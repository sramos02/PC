package practica5.mensajes.tipos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MensajeConfirmacion extends Mensaje implements Serializable {

	private String origen;
	private String destino;
	private String msg;

	public MensajeConfirmacion(String msg, String origen, String destino) {
		this.origen = origen;
		this.destino = destino;
		this.msg = msg;
	}

	//Para cerrar conexión
	public MensajeConfirmacion(String msg, String origen) {
		this.origen = origen;
		this.destino = "";
		this.msg = msg;
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
		return -1; //No tiene tipo
	}
	
	public String getMsg() {
		return this.msg;
	}

}