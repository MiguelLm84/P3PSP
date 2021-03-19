
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class ClienteChat {
	
    private final static int PORT = 2222;
    private static String HOST;
    private String nick;
    private Socket sc;
    private ServerSender ss;
    private ServerReceiver sr;
    private DataInputStream in;
    private DataOutputStream out;

    public static void main(String[] args) {
    	
    	if (args.length<2) {   		
			System.out.println("Para conectarse debe introducir: java ClienteChat <direccion_ip> <nickname>");
			
		} else {
			 HOST = args[0];
		        ClienteChat client = new ClienteChat(args[1]);
		        client.run();
		}       
    }

    public ClienteChat(String nick) {
        this.nick = nick;
    }

    public void run() {
        try {
            sc = new Socket(HOST, PORT);
            out = new DataOutputStream(sc.getOutputStream());
            out.writeUTF(nick);
            ss = new ServerSender(out);
            ss.start();
            sr = new ServerReceiver(new DataInputStream(sc.getInputStream()));
            sr.start();
            
        } catch (IOException e) {
        	System.out.println("conexión interrumpida.");
        }
//        try {
//            shutdown();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public void shutdown() throws IOException {
        if (ss != null) {
            ss.parar();
        }
        if (sr != null) {
            sr.parar();
        }
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        if (sc != null) {
            sc.close();
        }
    }
}
