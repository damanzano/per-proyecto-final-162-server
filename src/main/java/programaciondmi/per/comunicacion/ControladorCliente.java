package programaciondmi.per.comunicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

import programaciondmi.per.modelo.Instrumento;
import programaciondmi.per.modelo.NotaMusical;

/**
 * Esta clase se encarga de controlar todos los aspectos de comunicación
 * con un cliente que haya sido aceptado por el servidor
 * @author damanzano
 *
 */
public class ControladorCliente extends Observable implements Runnable {
	
	/**
	 * Identificador del controlador
	 */
	private int id;
	
	/**
	 * Instrumento asignado al cliente que es atendido por este controlador
	 */
	private Instrumento instrumento;
	
	/**
	 * Socket a través del cual se realiza la comunicación con el cliente
	 */
	private Socket socketCliente;
	
	/**
	 * Indica si el controlador está o no conectado con el cliente
	 */
	private boolean conectado;
	
	/**
	 * Crea un nuevo ControladorCliente con los parámetros especificados.
	 * @param socketCliente Socket a través del cual se realiza la comunicación con el cliente
	 * @param id Identificador del controlador
	 * @param instrumento Instrumento asignado al cliente que es atendido por este controlador
	 */
	public ControladorCliente(Socket socketCliente, int id, Instrumento instrumento) {
		this.socketCliente = socketCliente;
		this.id = id;
		this.instrumento = instrumento;
		conectado = true;
	}
	
	/**
	 * Hilo de ejecución en background
	 */
	public void run() {
		System.out.println("[ControladorCliente " + id + "] Enviando instrumento asignado");
		enviarObjeto(instrumento);

		System.out.println("[ControladorCliente " + id + "] Esperando mensajes");
		while (conectado) {
			// recibir();
			recibirObjeto();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Método que se encarga de recibir objetos enviador por el cliente y procesarlos
	 */
	private void recibirObjeto() {
		ObjectInputStream entrada = null;
		try {
			entrada = new ObjectInputStream(socketCliente.getInputStream());
			Object mensaje = entrada.readObject();
			System.out.println("[ControladorCliente " + id + "] Se recibio: " + mensaje + " del cliente " + id);

			setChanged();
			notifyObservers(mensaje);

		} catch (IOException e) {
			System.err.println("[ControladorCliente " + id + "] Se perdió la conexión con el cliente");
			try {
				if (entrada != null) {
					entrada.close();
				}
				socketCliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			socketCliente = null;
			conectado = false;
			setChanged();
			notifyObservers("cliente desconectado");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para enviar un mensaje en formato string a l cliente
	 * @param mensaje
	 */
	public void enviar(String mensaje) {
		DataOutputStream salida = null;
		try {
			salida = new DataOutputStream(socketCliente.getOutputStream());
			salida.writeUTF(mensaje);
			System.out.println("[ControladorCliente " + id + "] Se envió el mensaje: " + mensaje);
		} catch (IOException e) {
			System.err.println("[ControladorCliente " + id + "] Se perdió la conexión con el cliente");
			try {
				if (salida != null) {
					salida.close();
				}
				
				socketCliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			socketCliente = null;
			conectado = false;
			setChanged();
			notifyObservers("cliente desconectado");
		}
	}
	
	/**
	 * Método que se encarga de enviar un objeto al cliente.
	 * el servidor puede enviar dos tipos de mensajes Instrumento o NotaMusical
	 * @param mensaje
	 */
	public void enviarObjeto(Object o) {
		ObjectOutputStream salida = null;

		try {
			salida = new ObjectOutputStream(socketCliente.getOutputStream());
			salida.writeObject(o);
			System.out.println("[ControladorCliente " + id + "] Se envió el mensaje: " + o);
		} catch (IOException e) {
			System.err.println("[ControladorCliente " + id + "] Se perdió la conexión con el cliente");
			try {
				if (salida != null) {
					salida.close();
				}
				socketCliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			socketCliente = null;
			conectado = false;
			setChanged();
			notifyObservers("cliente desconectado");
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Instrumento getInstrumento() {
		return instrumento;
	}

	public void setInstrumento(Instrumento instrumento) {
		this.instrumento = instrumento;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}

}
