

import java.io.DataInputStream;
import java.io.IOException;

public class ServerReceiver extends Thread {
    private final DataInputStream in;

    private boolean activo;

    public ServerReceiver(DataInputStream in) {
        this.in = in;
        activo = true;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                System.out.println(in.readUTF());
            } catch (IOException e) {
                parar();
                System.out.println("El servidor se ha desconectado");
            }
        }
    }

    public void parar() {
        activo = false;
    }
}
