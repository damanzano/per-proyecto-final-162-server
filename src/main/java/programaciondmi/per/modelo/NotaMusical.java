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

	private Instrumento instrumento;
	private int nota;
	private double duracion;

	/**
	 * 
	 * @param instrumento
	 * @param nota
	 * @param duracion
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

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public int getNota() {
		return nota;
	}

	public void setNota(int nota) throws MalaNotaMusicalException{
		if (nota == DO || nota == RE || nota == MI || nota == FA || nota == SOL || nota == LA || nota == SI) {
			this.nota = nota;
		} else {
			throw new MalaNotaMusicalException(
					"Tipo de nota invalido, use una de las constantes definidas en la clase NotaMusical: DO, RE, MI FA, SOL, LA, SI");
		}
	}

	public double getDuracion() {
		return duracion;
	}

	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}

	@Override
	public String toString() {
		return "NotaMusical [instrumento=" + instrumento + ", nota=" + nota + ", duracion=" + duracion + "]";
	}

}
