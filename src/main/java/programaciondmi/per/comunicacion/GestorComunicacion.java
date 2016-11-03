package programaciondmi.per.comunicacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import programaciondmi.per.modelo.Instrumento;
import programaciondmi.per.modelo.NotaMusical;
import programaciondmi.per.modelo.exceptions.TipoInstrumentoException;
import programaciondmi.per.util.Permutaciones;

/**
 * Esta clase se encarga de lenvantar un ServerSocket para escuchar solicitudes de los clientes
 * @author damanzano
 *
 */
public class GestorComunicacion implements Observer, Runnable {
	
	/**
	 * Puerto utilizado en caso de que se pasa un parámetro inválido
	 */
	private final int PUERTO_PREDETERMINADO = 5000;
	
	/**
	 * Socket a través del cual se aceptan las solicitudes
	 */
	private ServerSocket socketServidor;
	
	/**
	 * Indica si es servidor se encuentra activo o no
	 */
	private boolean conectado;
	
	/**
	 * Listado de agentes atendiendo a clientes conectados
	 */
	private ArrayList<ControladorCliente> clientes;
	
	/**
	 * Secuencia en la que se asignaran los instrmuentos a los clientes
	 */
	private LinkedList<Instrumento> secuenciaInstrmentos;
	
	/**
	 * Puerto usado en la comunicación
	 */
	private int puerto;

	/**
	 * Construnctor
	 * @param puerto Debe ser un entero positivo mayor a 1024  y menor que limite de puertos
	 */
	public GestorComunicacion(int puerto) {
		// Definir un puerto valido
		this.puerto = (puerto > 1024) ? puerto : PUERTO_PREDETERMINADO;

		// Iniciar la lista de clientes
		clientes = new ArrayList<ControladorCliente>();

		// Definir la secuencia en la que se signaran los instrumentos
		secuenciaInstrmentos = crearPermutacionInstrmentos();

		// Iniciar el socket
		try {
			socketServidor = new ServerSocket(this.puerto);
			conectado = true;
			System.out.println("[Servidor]: Atendiendo en " + InetAddress.getLocalHost().getHostAddress().toString()
					+ ":" + this.puerto);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Hilo de ejecución en background
	 */
	public void run() {
		while (conectado) {
			try {

				// Esperar a que un cliente se conecte
				Socket socketCliente = socketServidor.accept();
				
				// Solo se reciben 8 clientes, uno por cada instrumento.
				if (clientes.size() < 8) {

					// Crear un controlador para cada nuevo cliente conectado y
					// asignarle su instrumento
					Instrumento instrumento = secuenciaInstrmentos.removeFirst();
					ControladorCliente cc = new ControladorCliente(socketCliente, clientes.size(), instrumento);

					// Agregar el gestor como observador
					cc.addObserver(this);

					// Comenzar el hilo de ejecución del contrlador
					new Thread(cc).start();

					// Agregar a la colección de clientes
					clientes.add(cc);
					System.out.println("[Servidor] Tenemos: " + clientes.size() + " clientes");
					if (clientes.size() == 8) {
						System.out.println("[Servidor] No se aceptan más clientes");
					}
				} else {
					System.out.println("[Servidor] No se aceptan más clientes");
					
					// Responder al cliente que no es posible atenderlo
					rechazarCliente(socketCliente);
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
	 * Genera una lista de instrumentos en una secuencia aleatoria
	 * @return LinkedList<Instrumento>
	 */
	private LinkedList<Instrumento> crearPermutacionInstrmentos() {
		// Define la lista a retornar.
		LinkedList<Instrumento> instrumentos = new LinkedList<Instrumento>();

		// Define los tipos de instrmentos
		int[] tipos = { 0, 1, 2, 3, 4, 5, 6, 7 };

		// Obtiene todas las posibles permutaciones
		ArrayList<ArrayList<Integer>> permutaciones = Permutaciones.permute(tipos);

		// Selecciona una permutacion alaazar
		int random = (int) (Math.random() * permutaciones.size() - 1);

		// Llenar la lista de instrmentos deacuerdo a la secuencia seleccionada
		for (Iterator<Integer> iterator = permutaciones.get(random).iterator(); iterator.hasNext();) {
			Integer codigoInst = (Integer) iterator.next();
			try{
				instrumentos.addLast(new Instrumento(codigoInst));
			}catch(TipoInstrumentoException e){
				e.printStackTrace();
			}
			
		}

		return instrumentos;

	}

	/**
	 * Define que se debe hacer cuando se recibe una notificación de algún
	 * observador
	 */
	public void update(Observable o, Object arg) {
		ControladorCliente controladorCliente = (ControladorCliente) o;

		// Si llega una nota musical, reenviar
		if (arg instanceof NotaMusical) {
			NotaMusical nota = (NotaMusical) arg;
			reenviarNota(nota, controladorCliente);
		}

		// Si notifican una desconexión
		if (arg instanceof String) {
			String mensaje = (String) arg;
			if (mensaje.equalsIgnoreCase("cliente desconectado")) {
				// Dejar disponible el instrumento para otro cliente
				secuenciaInstrmentos.addLast(controladorCliente.getInstrumento());

				// remover al cliente
				clientes.remove(controladorCliente);

				System.out.println("[Servidor] Tenemos: " + clientes.size() + " clientes");
			}
		}

	}

	/**
	 * Reenvia una NotaMusical a todos los clientes conectados excepto el
	 * remitente
	 * 
	 * @param nota
	 * @param remitente
	 */
	private void reenviarNota(NotaMusical nota, ControladorCliente remitente) {
		int reenvios = 0;
		for (Iterator<ControladorCliente> iterator = clientes.iterator(); iterator.hasNext();) {
			ControladorCliente controladorCliente = (ControladorCliente) iterator.next();
			if (!controladorCliente.equals(remitente)) {
				controladorCliente.enviarObjeto(nota);
				reenvios++;
			}

		}
		System.out.println("[Servidor] Se reenvió la nota a : " + reenvios + " clientes");
	}
	
	/**
	 * Envía un mesaje a un cliente diciéndole que no es posible atenderlo
	 * @param socketCliente
	 */
	private void rechazarCliente(Socket socketCliente){
		try (ObjectOutputStream salida = new ObjectOutputStream(socketCliente.getOutputStream())){
			String mensaje = "No se aceptan más clientes en el momento";
			salida.writeObject(mensaje);
			System.out.println("[Servidor] Se envió el mensaje: " + mensaje);
			socketCliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
