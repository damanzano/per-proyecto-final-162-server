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

public class ControladorCliente extends Observable implements Runnable {

	private int id;
	private Instrumento instrumento;
	private Socket socketCliente;
	private boolean conectado;

	public ControladorCliente(Socket socketCliente, int id, Instrumento instrumento) {
		this.socketCliente = socketCliente;
		this.id = id;
		this.instrumento = instrumento;
		conectado = true;
	}

	public void run() {
		System.out.println("[ControladorCliente " + id + "] Enviando instrumento asignado");
		// enviar("Instrumento");
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

	private void recibir() {
		DataInputStream entrada = null;
		try {
			entrada = new DataInputStream(socketCliente.getInputStream());
			int val = entrada.readInt();
			System.out.println("[ControladorCliente " + id + "] recibido: " + val + " del cliente");
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
		}
	}

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
