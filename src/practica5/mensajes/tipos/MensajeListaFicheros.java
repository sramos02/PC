package practica5.mensajes.tipos;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MensajeListaFicheros extends Mensaje implements Serializable {

	private String origen;
	private String destino;
	
	public MensajeListaFicheros(String origen, String destino) {
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
		return 6; 
	}
}