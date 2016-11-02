package programaciondmi.per.comunicacion;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

import programaciondmi.per.modelo.Instrumento;

public class ControladorCliente extends Observable implements Runnable {
	
	private int id;
	private Instrumento instrumento;
	private Socket socketCliente;
	private boolean conectado;
	
	public ControladorCliente(Socket socketCliente, int id, Instrumento instrumento){
		this.socketCliente = socketCliente;
		this.id =  id;
		this.instrumento = instrumento;
		conectado = true;
	}

	public void run() {
		System.out.println("[ControladorCliente "+id+"] Enviando instrumento asignado");
		enviar("Instrumento");
		
		System.out.println("[ControladorCliente "+id+"] Esperando mensajes");
		while (conectado) {
			recibir();
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
			System.out.println("recibido: " + val +" del cliente "+id);
		} catch (SocketException e) {			
			System.err.println("Se perdió la conexión con el cliente");
			try {
				entrada.close();
				socketCliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			socketCliente = null;
			conectado = false;
			setChanged();
			notifyObservers("cliente desconectado");
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}

	public void enviar(String mensaje) {
		DataOutputStream salida = null;
		try {
			salida = new DataOutputStream(socketCliente.getOutputStream());
			salida.writeUTF(mensaje);
			System.out.println("Se envió el mensaje: " + mensaje);
		} catch (SocketException e) {			
			System.err.println("Se perdió la conexión con el cliente");
			try {
				salida.close();
				socketCliente.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}			
			socketCliente = null;
			conectado = false;
			setChanged();
			notifyObservers("cliente desconectado");
		} catch (IOException e) {			
			e.printStackTrace();
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
