package Servidor;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;


public class ConexionCliente extends Thread implements Observer
{
    
    private Socket socket; 
    private MensajesChat mensajes;
    //Buffer de entrada del Servidor, que a su vez seria el input de salida del Cliente.
    private DataInputStream entradaDatos;
    //Inp
    private DataOutputStream salidaDatos;
    
    /** Es el constructor de la clase ConexionCliente, recibe 
     * 
     * @param socket Un socket ya creado por una conexion recibida por ServerSocket.accept();
     * @param mensajes -
     */
    public ConexionCliente (Socket socket, MensajesChat mensajes)
    {
        this.socket = socket;
        this.mensajes = mensajes;
        
        /**Intento instanciar la entradaDatos y salidaDatos en base a la informacion
         * de los mismos obtenida por el socket.*/
        try 
        {
            entradaDatos = new DataInputStream(socket.getInputStream());
            salidaDatos = new DataOutputStream(socket.getOutputStream());
        } 
        catch (IOException ex) 
        {
        	String mensajeError = "Error al crear los stream de entrada y salida : " + ex.getMessage();
        	System.out.println(mensajeError);
        	Log.imprimirError(mensajeError);
        }
    }
    

    @Override
    public void run()
    {
        String mensajeRecibido;
        boolean conectado = true;
        // Esta instancia de ConexionCliente que corre sobre un thread se anade a la lista de observadores del mensaje.
        mensajes.addObserver(this);
        
        while (conectado) 
        {
            try 
            {
                // Lee un mensaje enviado por el cliente
                mensajeRecibido = entradaDatos.readUTF();
                // Pone el mensaje recibido en mensajes para que se notifique 
                // a sus observadores(demas clientes incluido el mismo cliente) que hay un nuevo mensaje.
                mensajes.setMensaje(mensajeRecibido);
                Log.imprimirLogChat(mensajeRecibido);
            } 
            catch (IOException ex) 
            {
            	String mensaje = "Cliente con la IP " + socket.getInetAddress().getHostName() + " desconectado.";
            	System.out.println(mensaje);
            	Log.imprimirInfo(mensaje);
                conectado = false; 
                // Si se ha producido un error al recibir datos del cliente se cierra la conexion con el.
                try 
                {
                    entradaDatos.close();
                    salidaDatos.close();
                } 
                catch (IOException ex2) 
                {
                	String mensajeError2 = "Error al cerrar los stream de entrada y salida :" + ex2.getMessage();
                	System.out.println(mensajeError2);
                	Log.imprimirError(mensajeError2);
                }
            }
        }   
    }
    
    /**
     * Cada vez que MensajesChat cambia de valor es un String,
     * se ejecuta el metodo Update.
     */
    @Override
    public void update(Observable o, Object arg) 
    {
        try 
        {
            // Envia el mensaje al cliente
            salidaDatos.writeUTF(arg.toString());
        } 
        catch (IOException ex) 
        {
        	String mensajeError = "Error al enviar mensaje al cliente (" + ex.getMessage() + ").";
        	System.out.println(mensajeError);
        	Log.imprimirError(mensajeError);
        }
    }
} 