package programaciondmi.per.main;

import programaciondmi.per.comunicacion.GestorComunicacion;

/**
 * Esta clase ejecuta un servidor sin interfaz gráfica
 * @author damanzano
 *
 */
public class ServidorNoUI {
	/**
	 * Gestor a través del cual se recibiran las solicitudes de los clientes
	 */
	private static GestorComunicacion gc;
	
	/**
	 * Punto de partia de la aplicación
	 * @param args
	 */
	public static void main(String[] args) {
		gc = new GestorComunicacion(0);
		new Thread(gc).start();
	}

}
