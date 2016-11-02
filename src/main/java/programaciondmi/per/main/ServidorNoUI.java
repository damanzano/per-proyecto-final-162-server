package programaciondmi.per.main;

import programaciondmi.per.comunicacion.GestorComunicacion;

public class ServidorNoUI {

	static GestorComunicacion gc;

	public static void main(String[] args) {
		gc = new GestorComunicacion(0);
		new Thread(gc).start();
	}

}
