package programaciondmi.per.modelo;

import java.io.Serializable;

public class NotaMusical implements Serializable {
	private Instrumento instrumento;
	private int nota;
	private double duracion;
	
	
	public NotaMusical(Instrumento instrumento, int nota, double duracion) {
		super();
		this.instrumento = instrumento;
		this.nota = nota;
		this.duracion = duracion;
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


	public void setNota(int nota) {
		this.nota = nota;
	}


	public double getDuracion() {
		return duracion;
	}


	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}
	
	
}
