

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;

public class ClienteConectado extends Thread {
	
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;
    private long id;
    private String nick;
    private long idConver;

    public ClienteConectado(Socket socket) throws IOException {
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        this.nick = in.readUTF();
        this.id = new Random().nextLong();
        System.out.println("Cliente conectado: " + nick + " (" + socket.getInetAddress() + ")");
    }

    @Override
    public void run() {
        try {
            out.writeUTF("Estás conectado con el nick " + nick);
            out.writeUTF(ConstantesServidor.CMD_WELCOME);
            
        } catch (IOException e) {
        	System.out.println("Error de conexión en el cliente conectado.");
        }
        while (!socket.isClosed()) {
            try {
                procesarCmd(in.readUTF());
                
            } catch (SocketException e) {
            	
                try {
                    ListaClientes.deleteClienteById(id);
                    socket.close();
                    System.out.println("Cliente desconectado: " + nick + " (" + socket.getInetAddress() + ")");
                    
                } catch (IOException ex) {
                	System.out.println("Error de conexión en el cliente conectado.");
                }
                
            } catch (IOException e) {
                try {
                    ListaClientes.deleteClienteById(id);
                    socket.close();
                    System.out.println("Cliente desconectado: " + nick + " (" + socket.getInetAddress() + ")");
                    
                } catch (IOException ex) {
                	System.out.println("Error de conexión en el cliente conectado.");
                }
            }
        }
    }

    public void enviarRespuesta(String respuesta) {
        if (respuesta != null) {
            ListaClientes.enviarMensaje(idConver, "> " + nick + ": " + respuesta);
        }
    }

    public void procesarCmd(String cmd) throws IOException {
        if (cmd == null || cmd.length() == 0) {
            out.writeUTF(String.format(ConstantesServidor.CMD_ERROR, cmd));
            return;
        }
        if (idConver != 0 && !cmd.startsWith("#")) {
            enviarRespuesta(cmd);
            
        } else {
            String[] input = cmd.split(" ");
            String comando = input[0];
            
            switch (comando) {
            
                case "#ayuda":
                    out.writeUTF(ConstantesServidor.CMD_AYUDA);
                    break;
                    
                case "#listar":
                    int conectados = ListaClientes.getNumConectados();
                    
                    if (conectados == 0) {
                        out.writeUTF("No hay ningún usuario conectado");
                        
                    } else {
                        if (conectados == 1) {
                            out.writeUTF("Hay 1 usuario conectado:");
                            
                        } else {
                            out.writeUTF("En este momento están conectados " + conectados + " usuarios:");
                        }
                    }
                    out.writeUTF(ListaClientes.getNombres());
                    break;
                    
                case "#charlar":
                	
                    if (input.length != 2) {
                        out.writeUTF(ConstantesServidor.CMD_CHARLAR_ERR);
                        break;
                    }
                    String nickInterlocutor = input[1];
                    long idConver = ListaClientes.getIdByNick(nickInterlocutor);
                    
                    if (idConver != -1) {
                        out.writeUTF(String.format(ConstantesServidor.CMD_CHARLAR_OK, nickInterlocutor));
                        this.idConver = idConver;
                        
                    } else {
                        out.writeUTF(String.format(ConstantesServidor.CMD_DISCON_ERR, nickInterlocutor));
                    }
                    break;
                    
                case "#salir":
                	
                    ListaClientes.deleteClienteById(id);
                    out.writeUTF(ConstantesServidor.BYE_MSG);
                    this.idConver = 0;
                    break;
                    
                default:
                    out.writeUTF(String.format(ConstantesServidor.CMD_ERROR, cmd));
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getIdConver() {
        return idConver;
    }

    public void setIdConver(long idConver) {
        this.idConver = idConver;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
