package ClienteSwing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConexionServidor
{
    private Socket socket; 
    private String usuario;
    private DataOutputStream salidaDatos;
    
    public ConexionServidor(Socket socket, String usuario) 
    {
        this.socket = socket;
        this.usuario = usuario;
        try 
        {
            this.salidaDatos = new DataOutputStream(this.socket.getOutputStream());
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al crear el stream de salida : " + ex.getMessage());
        } 
        catch (NullPointerException ex) 
        {
            System.out.println("El socket no se creo correctamente. ");
        }
    }
    
    /** Este metodo escribe en el buffer de salida el mensaje introducido por el usuario en el input.*/
    public void enviarMensaje(String mensaje) 
    {
    	//Intento escribir en el buffer de salida.
        try 
        {
            salidaDatos.writeUTF(usuario + ": " + mensaje);
            //Al realizar salidaDatos.writeUTF estaria "llamando" al entradaDatos.readUTF(); del servidor.
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al intentar enviar un mensaje: " + ex.getMessage());
        }
    }
}
