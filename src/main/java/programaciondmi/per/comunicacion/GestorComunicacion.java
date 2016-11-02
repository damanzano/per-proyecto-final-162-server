package programaciondmi.per.comunicacion;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import programaciondmi.per.modelo.Instrumento;
import programaciondmi.per.modelo.NotaMusical;
import programaciondmi.per.util.Permutaciones;

public class GestorComunicacion implements Observer, Runnable {
	private final int PUERTO_PREDETERMINADO = 5000;
	private ServerSocket socketServidor;
	private boolean conectado;
	private ArrayList<ControladorCliente> clientes;
	private LinkedList<Instrumento> secuenciaInstrmentos;
	private int puerto;

	public GestorComunicacion(int puerto) {
		// Definir un puerto valido
		this.puerto = (puerto > 1024)? puerto: PUERTO_PREDETERMINADO;
		
		// Iniciar la lista de clientes
		clientes = new ArrayList<ControladorCliente>();
		
		// Definir la secuencia en la que se signaran los instrumentos
		secuenciaInstrmentos = crearPermutacionInstrmentos();
		
		// Iniciar el socket
		try{
			socketServidor = new ServerSocket(this.puerto);
			conectado = true;
			System.out.println("[Servidor]: Atendiendo en "+InetAddress.getLocalHost().getHostAddress().toString()+":"+this.puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (conectado) {
			try {
				// Solo se reciben 8 clientes, uno por cada instrumento.
				if(clientes.size()<=8){
					// Esperar a que un cliente se conecte
					Socket socketCliente = socketServidor.accept();
					
					// Crear un controlador para cada nuevo cliente conectado y asignarle su instrumento
					Instrumento instrumento =secuenciaInstrmentos.removeFirst();
					ControladorCliente cc = new ControladorCliente(socketCliente, clientes.size(), instrumento);
					
					// Agregar el gestor como observador
					cc.addObserver(this);
					
					// Comenzar el hilo de ejecución del contrlador
					new Thread(cc).start();
					
					// Agregar a la colección de clientes
					clientes.add(cc);	
					System.out.println("[Servidor] Tenemos: " + clientes.size() + " clientes");
					if(clientes.size()==8){
						System.out.println("[Servidor] No se reciben más clientes");
					}
				}
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}
	
	/**
	 * Define que se debe hacer cuando se recibe una notificación de algún observador
	 */
	public void update(Observable o, Object arg) {
		ControladorCliente controladorCliente = (ControladorCliente) o;
		
		// Si llega una nota musical, reenviar
		if(arg instanceof NotaMusical){
			NotaMusical nota = (NotaMusical) arg;
			reenviarNota(nota, controladorCliente);
		}
		
		// Si notifican una desconexión
		if(arg instanceof String){
			String mensaje = (String) arg;
			if(mensaje.equalsIgnoreCase("desconexion")){
				// Dejar disponible el instrumento para otro cliente
				secuenciaInstrmentos.addLast(controladorCliente.getInstrumento());
				
				// remover al cliente
				clientes.remove(controladorCliente);
				
				System.out.println("[Servidor] Tenemos: " + clientes.size() + " clientes");
			}
		}

	}
	
	/**
	 * Reenvia una NotaMusical a todos los clientes conectados excepto el remitente
	 * @param nota
	 * @param remitente
	 */
	private void reenviarNota(NotaMusical nota, ControladorCliente remitente){
		for (Iterator iterator = clientes.iterator(); iterator.hasNext();) {
			ControladorCliente controladorCliente = (ControladorCliente) iterator.next();
			controladorCliente.enviar("NotaMusical");
		}
	}
	
	/**
	 * Genera una lista de instrumentos en una secuencia aleatoria
	 * @return
	 */
	private LinkedList<Instrumento> crearPermutacionInstrmentos(){
		// Define la lista a retornar.
		LinkedList<Instrumento> instrumentos = new LinkedList<Instrumento>();
		
		// Define los tipos de instrmentos
		int[] tipos = {0,1,2,3,4,5,6,7};
		
		// Obtiene todas las posibles permutaciones
		ArrayList<ArrayList<Integer>> permutaciones = Permutaciones.permute(tipos);
		
		// Selecciona una permutacion alaazar
		int random = (int) (Math.random() * permutaciones.size()-1);
		
		// Llenar la lista de instrmentos deacuerdo a la secuencia seleccionada
		for (Iterator iterator = permutaciones.get(random).iterator(); iterator.hasNext();) {
			Integer codigoInst = (Integer) iterator.next();
			instrumentos.addLast(new Instrumento(codigoInst));
		}
		
		return instrumentos;
		
	}

}