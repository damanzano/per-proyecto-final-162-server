package programaciondmi.per.main;

import processing.core.PApplet;
import programaciondmi.per.comunicacion.GestorComunicacion;

/**
 * Esta clase ejecuta un servidor con interfaz gráfica mostrando
 * las notas recibidas por cada uno de los clientes conectados
 * @author damanzano
 *
 */
public class ServidorUI extends PApplet {
	/**
	 * Gestor a través del cual se recibiran las solicitudes de los clientes
	 */
	private GestorComunicacion gc;
	
	public static void main(String[] args) {
		PApplet.main("programaciondmi.per.main.ServidorUI");
	}
	
	/**
	 * Çonfigura la patalla
	 */
	public void settings(){
		fullScreen();
		smooth();
	}
	
	/**
	 * Define los valores iniciales
	 */
	public void setup(){
		// Crea el gestor de comunicación en un hilo de ejecucón aparte
		gc = new GestorComunicacion(0);
		new Thread(gc).start();
	}
	
	public void draw(){}

}
