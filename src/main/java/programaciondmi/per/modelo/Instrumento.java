package programaciondmi.per.modelo;

import java.io.Serializable;

public class Instrumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int tipo;
	private String descripcion;

	public Instrumento(int tipo) {
		super();
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Instrumento [tipo=" + tipo + ", descripcion=" + descripcion + "]";
	}

}
