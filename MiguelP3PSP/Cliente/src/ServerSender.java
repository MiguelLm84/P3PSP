

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerSender extends Thread {
    private final DataOutputStream out;
    private final BufferedReader teclado;

    private boolean activo;

    public ServerSender(DataOutputStream out) {
        this.out = out;
        teclado = new BufferedReader(new InputStreamReader(System.in));
        activo = true;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                out.writeUTF(teclado.readLine());
                
            } catch (IOException e) {
                parar();
                System.out.println("Error, conexión interrumpida en el Thread de ServerSender.");
                
            }  catch (NullPointerException h) {
                System.out.println("La conexión se ha cerrado inesperadamente.");
            }
        }
    }

    public void parar() {
        activo = false;
    }
}
