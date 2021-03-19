
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;


public class ServidorChat {

    public static void main(String[] args) {
        new ServidorChat();
    }

    public ServidorChat() {
    	
    	ServerSocket serverSocket;
    	
        try {
            System.out.println("Servidor iniciado");
            serverSocket = new ServerSocket(ConstantesServidor.PUERTO_ESCUCHA);
            
            while (true) {
                Socket socket = serverSocket.accept();
                ClienteConectado cliente = new ClienteConectado(socket);
                cliente.start();
                ListaClientes.agregarCliente(cliente);
            }
            
        } catch(SocketException e){
        	System.out.println("[Error] conexión con el socket caida.");
        	           
        } catch (IOException e) {
        	System.out.println(ConstantesServidor.CMD_DISCON_ERR);            
        }
    }
}
