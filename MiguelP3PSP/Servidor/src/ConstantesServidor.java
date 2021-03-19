

public class ConstantesServidor {

    private ConstantesServidor() {
    }

    public static final int PUERTO_ESCUCHA = 2222;

    public static final String CMD_WELCOME = "Introduzca '#ayuda' para consultar los comandos que hay disponibles";

    public static final String CMD_AYUDA = "#listar: lista todos los usuarios conectados." +
                                           "\n#charlar <usuario>: comienza la comunicaci�n con el usuario <usuario>." +
                                           "\n#salir: se desconecta el chat";

    public static final String CMD_CHARLAR_ERR = "[ERROR] No se ha podido establecer la conversaci�n. " +
                                                 "Por favor, introduzca '#charlar <usuario>'";

    public static final String CMD_CHARLAR_OK = "Ahora est�s conectado con %s. Escribe para hablarle";

    public static final String CMD_ERROR = "[ERROR] '%s' no se reconoce como comando. Si quieres iniciar " +
                                           "una conversaci�n o responder a un usuario utiliza el comando #charlar <usuario>";

    public static final String BYE_MSG = "Conversaci�n terminada, adios";

    public static final String CMD_DISCON_ERR = "[ERROR] El usuario %s no se encuentra conectado. Utiliza el comando '#listar' " +
                                                "para ver los usuarios conectados";

    public static final String CMD_DISCON2_ERR = "[ERROR] El usuario se ha desconectado o no ha establecido conexi�n correctamente.";
}
