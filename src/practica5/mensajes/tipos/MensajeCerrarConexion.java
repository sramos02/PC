package practica5.mensajes.tipos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MensajeCerrarConexion extends Mensaje implements Serializable {

	//Información sobre el mensaje
	private String origen;
	private String destino;
	
	
	public MensajeCerrarConexion(String origen, String destino) {
		this.origen = origen;
		this.destino = destino;
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
		return 2;
	}
}
