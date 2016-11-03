package programaciondmi.per.modelo;

import java.io.Serializable;

import programaciondmi.per.modelo.exceptions.MalaNotaMusicalException;

public class NotaMusical implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// TIPOS DE NOTAS
	public static final int DO = 0;
	public static final int RE = 1;
	public static final int MI = 2;
	public static final int FA = 3;
	public static final int SOL = 4;
	public static final int LA = 5;
	public static final int SI = 6;

	// DURACIONES
	public static final double NEGRA = 1.0f;
	public static final double BLANCA = 2.0f;
	public static final double REDONDA = 4.0f;
	public static final double CORCHEA = 1 / 2;
	public static final double SEMICORCHEA = 1 / 4;
	public static final double FUSA = 1 / 8;
	public static final double SEMIFUSA = 1 / 16;

	/**
	 * El instrumento que genera la nota
	 */
	private Instrumento instrumento;

	/**
	 * La nota musical en si misma
	 */
	private int nota;

	/**
	 * La duración relativa de la nota (debe ser un valor entre 0 y 4)
	 */
	private double duracion;

	/**
	 * Construye una nueva NotaMusical con los parametros entregados
	 * 
	 * @param instrumento
	 *            El instrumento que genera la nota.
	 * @param nota
	 *            La nota musical en si misma. Ver constantes estáticas para la
	 *            nota.
	 * @param duracion
	 *            La duración relativa de la nota (debe ser un valor entre 0 y
	 *            4), se suguiere utilizar una de las constantes estáticas para
	 *            la duración.
	 * @throws MalaNotaMusicalException
	 */
	public NotaMusical(Instrumento instrumento, int nota, double duracion) throws MalaNotaMusicalException {
		this.instrumento = instrumento;
		this.duracion = duracion;
		if (nota == DO || nota == RE || nota == MI || nota == FA || nota == SOL || nota == LA || nota == SI) {
			this.nota = nota;
		} else {
			throw new MalaNotaMusicalException(
					"Tipo de nota invalido, use una de las constantes definidas en la clase NotaMusical: DO, RE, MI FA, SOL, LA, SI");
		}

	}
	
	/**
	 * 
	 * @return
	 */
	public Instrumento getInstrumento() {
		return instrumento;
	}
	
	/**
	 * 
	 * @param instrumento
	 */
	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getNota() {
		return nota;
	}
	
	/**
	 * 
	 * @param nota
	 * @throws MalaNotaMusicalException
	 */
	public void setNota(int nota) throws MalaNotaMusicalException {
		if (nota == DO || nota == RE || nota == MI || nota == FA || nota == SOL || nota == LA || nota == SI) {
			this.nota = nota;
		} else {
			throw new MalaNotaMusicalException(
					"Tipo de nota invalido, use una de las constantes definidas en la clase NotaMusical: DO, RE, MI FA, SOL, LA, SI");
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public double getDuracion() {
		return duracion;
	}
	
	/**
	 * 
	 * @param duracion
	 */
	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "NotaMusical [instrumento=" + instrumento + ", nota=" + nota + ", duracion=" + duracion + "]";
	}

}
