package programaciondmi.per.modelo;

import java.io.Serializable;

import programaciondmi.per.modelo.exceptions.TipoInstrumentoException;

public class Instrumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// TIPOS DE INSTRUMENTO
	public static final int TIPO_VIENTO = 0;
	public static final int TIPO_PERCUSION = 1;
	public static final int TIPO_CUERDA = 2;
	public static final int TIPO_ELECTRONICO = 3;
	public static final int TIPO_ACUSTICO = 4;
	public static final int TIPO_URBANO = 5;
	public static final int TIPO_NATURAL = 6;
	public static final int TIPO_ASTRAL = 7;
	
	/**
	 * Identificador del tipo de instrumento musical.
	 */
	private int tipo;
	
	/**
	 * Descripción del instrumento
	 */
	private String descripcion;
	
	/**
	 * Construye un nuevo instrumento musical con los parámetros esntregados
	 * @param tipo Identificador del tipo de instrumento musical. Ver constantes estáticas de la clase
	 * @throws TipoInstrumentoException
	 */
	public Instrumento(int tipo) throws TipoInstrumentoException{
		if (tipo == TIPO_VIENTO || tipo == TIPO_PERCUSION || tipo == TIPO_CUERDA || tipo == TIPO_ELECTRONICO
				|| tipo == TIPO_ACUSTICO || tipo == TIPO_URBANO || tipo == TIPO_NATURAL || tipo == TIPO_ASTRAL) {
			this.tipo = tipo;
			switch (this.tipo) {
			case 0:
				this.descripcion = "VIENTO";
				break;
			case 1:
				this.descripcion = "PERCUSION";
				break;
			case 2:
				this.descripcion = "CUERDA";
				break;
			case 3:
				this.descripcion = "ELECTRONICO";
				break;
			case 4:
				this.descripcion = "ACUSTICO";
				break;
			case 5:
				this.descripcion = "URBANO";
				break;
			case 6:
				this.descripcion = "NATURAL";
				break;
			case 7:
				this.descripcion = "ASTRAL";
				break;
			default:
				break;
			}
		} else {
			throw new TipoInstrumentoException(
					"Tipo de intrumento invalido, use: TIPO_VIENTO, TIPO_PERCUSION, TIPO_CUERDA, TIPO_ELECTRONICO, "
					+ "TIPO_ACUSTICO, TIPO_URBANO,TIPO_NATURAL,TIPO_ASTRAL");
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public int getTipo() {
		return tipo;
	}
	
	/**
	 * 
	 * @param tipo
	 * @throws TipoInstrumentoException
	 */
	public void setTipo(int tipo) throws TipoInstrumentoException {
		if (tipo == TIPO_VIENTO || tipo == TIPO_PERCUSION || tipo == TIPO_CUERDA || tipo == TIPO_ELECTRONICO
				|| tipo == TIPO_ACUSTICO || tipo == TIPO_URBANO || tipo == TIPO_NATURAL || tipo == TIPO_ASTRAL) {
			this.tipo = tipo;
			switch (this.tipo) {
			case 0:
				this.descripcion = "VIENTO";
				break;
			case 1:
				this.descripcion = "PERCUSION";
				break;
			case 2:
				this.descripcion = "CUERDA";
				break;
			case 3:
				this.descripcion = "ELECTRONICO";
				break;
			case 4:
				this.descripcion = "ACUSTICO";
				break;
			case 5:
				this.descripcion = "URBANO";
				break;
			case 6:
				this.descripcion = "NATURAL";
				break;
			case 7:
				this.descripcion = "ASTRAL";
				break;
			default:
				break;
			}
		} else {
			throw new TipoInstrumentoException(
					"Tipo de intrumento invalido, use: TIPO_VIENTO, TIPO_PERCUSION, TIPO_CUERDA, TIPO_ELECTRONICO, "
					+ "TIPO_ACUSTICO, TIPO_URBANO,TIPO_NATURAL,TIPO_ASTRAL");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	@Override
	public String toString() {
		return "Instrumento [tipo=" + tipo + ", descripcion=" + descripcion + "]";
	}

}
