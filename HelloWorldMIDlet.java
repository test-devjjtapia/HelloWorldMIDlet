import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

/**
 * HelloWorldMIDlet - Aplicación MIDP de ejemplo que muestra un mensaje personalizado
 * con funcionalidad extendida que incluye múltiples comandos y mejor interacción.
 * 
 * @author [Tu Nombre]
 * @version 2.0
 */
public class HelloWorldMIDlet extends MIDlet {
    // Referencia a la pantalla principal
    private HelloScreen helloScreen;
    
    /**
     * Constructor de la aplicación MIDlet
     */
    public HelloWorldMIDlet() {
        // Inicialización de la aplicación
    }
    
    /**
     * Método llamado cuando la aplicación se inicia o reanuda
     */
    public void startApp() {
        Displayable current = Display.getDisplay(this).getCurrent();
        if (current == null) {
            // Crear y mostrar la pantalla principal si no hay una visible
            helloScreen = new HelloScreen(this, "Hola Mundo, esta es una prueba mejorada!");
            Display.getDisplay(this).setCurrent(helloScreen);
        }
    }
    
    /**
     * Método llamado cuando la aplicación se pausa
     */
    public void pauseApp() {
        // Liberar recursos temporales si es necesario
        System.out.println("Aplicación pausada");
    }
    
    /**
     * Método llamado cuando la aplicación se destruye
     * @param unconditional Indica si la destrucción es incondicional
     */
    public void destroyApp(boolean unconditional) {
        // Liberar todos los recursos
        helloScreen = null;
        System.out.println("Aplicación finalizada");
    }
    
    /**
     * Solicita la salida controlada de la aplicación
     */
    void exitRequested() {
        destroyApp(false);
        notifyDestroyed();
    }
    
    /**
     * Método para cambiar el mensaje en la pantalla
     * @param newMessage Nuevo mensaje a mostrar
     */
    public void updateMessage(String newMessage) {
        if (helloScreen != null) {
            helloScreen.setMessage(newMessage);
        }
    }
}

/**
 * HelloScreen - Pantalla principal que muestra un mensaje y maneja comandos
 */
class HelloScreen extends TextBox implements CommandListener {
    private final HelloWorldMIDlet midlet;
    private final Command exitCommand;
    private final Command infoCommand;
    private final Command changeMessageCommand;
    private final Alert infoAlert;
    
    /**
     * Constructor de la pantalla principal
     * @param midlet Referencia al MIDlet principal
     * @param message Mensaje a mostrar
     */
    HelloScreen(HelloWorldMIDlet midlet, String message) {
        super("HelloWorldMIDlet Mejorado", message, 256, TextField.ANY);
        this.midlet = midlet;
        
        // Crear comandos
        exitCommand = new Command("Salir", Command.EXIT, 1);
        infoCommand = new Command("Info", Command.SCREEN, 2);
        changeMessageCommand = new Command("Cambiar Mensaje", Command.SCREEN, 3);
        
        // Añadir comandos a la pantalla
        addCommand(exitCommand);
        addCommand(infoCommand);
        addCommand(changeMessageCommand);
        
        // Configurar listener de eventos
        setCommandListener(this);
        
        // Crear alerta de información
        infoAlert = new Alert("Información");
        infoAlert.setString("Esta es una aplicación MIDP de ejemplo.\nVersión 2.0");
        infoAlert.setTimeout(Alert.FOREVER);
    }
    
    /**
     * Maneja las acciones de los comandos
     * @param c Comando que triggered la acción
     * @param d Displayable donde ocurrió el evento
     */
    public void commandAction(Command c, Displayable d) {
        if (c == exitCommand) {
            midlet.exitRequested();
        } else if (c == infoCommand) {
            // Mostrar alerta de información
            Display.getDisplay(midlet).setCurrent(infoAlert, this);
        } else if (c == changeMessageCommand) {
            // Cambiar a un mensaje alternativo
            setString("¡Mensaje cambiado exitosamente!\n" + 
                     "Fecha: " + new java.util.Date().toString());
        }
    }
    
    /**
     * Establece un nuevo mensaje en la pantalla
     * @param message Nuevo mensaje a mostrar
     */
    public void setMessage(String message) {
        this.setString(message);
    }
    
    /**
     * Obtiene el mensaje actual de la pantalla
     * @return El mensaje actual
     */
    public String getMessage() {
        return this.getString();
    }
}