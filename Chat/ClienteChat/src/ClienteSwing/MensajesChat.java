package ClienteSwing;
import java.util.Observable;

//Patron observer.
//migranitodejava.blogspot.com/2011/06/observer.html
//https://es.wikipedia.org/wiki/Observer_(patr%C3%B3n_de_dise%C3%B1o)
/**
 * Esta clase extiende de Observable, representa a un mensaje del chat
 * y de cambiar asienta su cambio y le informa a todos los observadores
 * que cambio.
 */
public class MensajesChat extends Observable
{

    private String mensaje;
    
    public MensajesChat(){}
    
    public String getMensaje()
    {
        return mensaje;
    }
    
    /** Este metodo es el que "setea" el nuevo valor del mensaje y notifica a los observadores que cambio.*/ 
    public void setMensaje(String mensaje)
    {
        this.mensaje = mensaje;
        // Indica que el mensaje ha cambiado
        this.setChanged();
        // Notifica a los observadores que el mensaje ha cambiado y se lo pasa
        // (Internamente notifyObservers llama al metodo update del observador)
        this.notifyObservers(this.getMensaje());
    }
}
