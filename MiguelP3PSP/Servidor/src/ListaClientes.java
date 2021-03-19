

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListaClientes {
    private static final List<ClienteConectado> conectados = new ArrayList<>();

    private ListaClientes() {
    }

    public static void agregarCliente(ClienteConectado nuevoCliente) {
        conectados.add(nuevoCliente);
    }

    public static int getNumConectados() {
        return conectados.size();
    }

    public static void deleteClienteById(long id) {
        for (int i = 0; i < conectados.size(); i++) {
            ClienteConectado idUsuario = conectados.get(i);
            if (idUsuario.getId() == id) {
                conectados.remove(idUsuario);
            }
        }
    }

    public static String getNombres() {
        StringBuilder sb = new StringBuilder();
        for (ClienteConectado c : conectados) {
            sb.append(c.getNick());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static boolean enviarMensaje(long id, String mensaje) {
        try {
            for (ClienteConectado c : conectados) {
                if (c.getId() == id) {
                    c.getOut().writeUTF(mensaje);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getPositionById(long id) {
        for (int i = 0; i < conectados.size(); i++) {
            if (conectados.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static long getIdByNick(String nick) {
        if (nick == null) {
            throw new IllegalArgumentException("nick no puede ser null");
        }
        for (ClienteConectado c : conectados) {
            if (nick.equals(c.getNick())) {
                return c.getId();
            }
        }
        return -1;
    }
} 
