package practica5.mensajes.tipos;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5499918901424132888L;
	public abstract int getTipo();
	public abstract String getOrigen();
	public abstract String getDestino();
}
