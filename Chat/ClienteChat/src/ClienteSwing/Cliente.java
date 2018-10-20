package ClienteSwing;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;

@SuppressWarnings("serial")
public class Cliente extends JFrame 
{
    
    private JTextArea mensajesChat;
    private Socket socket;
    
    private int puerto = Configuraciones.PUERTO;
    private String host = Configuraciones.HOST;
    private String usuario = Configuraciones.USUARIO;
    
    public Cliente()
    {
    	//Nombre de la ventana.
        super("Cliente Chat");
        
        // Elementos de la ventana
        mensajesChat = new JTextArea();
        mensajesChat.setEnabled(false); // El area de mensajes del chat no se debe de poder editar
        mensajesChat.setLineWrap(true); // Las lineas se parten al llegar al ancho del textArea
        mensajesChat.setWrapStyleWord(true); // Las lineas se parten entre palabras (por los espacios blancos)
        JScrollPane scrollMensajesChat = new JScrollPane(mensajesChat);
        JTextField tfMensaje = new JTextField("");
        JButton btEnviar = new JButton("Enviar");
        
        
        // Colocacion de los componentes en la ventana
        Container c = this.getContentPane();
        c.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.insets = new Insets(20, 20, 20, 20);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        c.add(scrollMensajesChat, gbc);
        // Restaura valores por defecto
        gbc.gridwidth = 1;        
        gbc.weighty = 0;
        
        gbc.fill = GridBagConstraints.HORIZONTAL;        
        gbc.insets = new Insets(0, 20, 20, 20);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        c.add(tfMensaje, gbc);
        // Restaura valores por defecto
        gbc.weightx = 0;
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        c.add(btEnviar, gbc);
        
        this.setBounds(400, 100, 400, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);     
        
        System.out.println("Conexion al host " + host + " en el puerto " + puerto + " con el nombre de usuario: " + usuario + ".");
        
        // Se crea el socket para conectar con el Servidor del Chat
        try 
        {
            socket = new Socket(host, puerto);
        } 
        catch (UnknownHostException ex) 
        {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } 
        catch (IOException ex) 
        {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
        
        ConexionServidor con = new ConexionServidor(socket, usuario);
        
        // Al realizar click en el boton enviar.
        btEnviar.addActionListener(new ActionListener() 
        {
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if(tfMensaje.getText()=="")
        			return;
				con.enviarMensaje(tfMensaje.getText()); // Realizo el envio del mensaje.
				tfMensaje.setText(""); // Luego de enviar el mensaje limpio el input del mensaje.
			}
		});
        
        // Al realizar enter en el input.
        tfMensaje.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e)
        	{
        		if(tfMensaje.getText()=="")
        			return;
        		con.enviarMensaje(tfMensaje.getText()); // Realizo el envio del mensaje.
				tfMensaje.setText(""); // Luego de enviar el mensaje limpio el input del mensaje.
        	}
        });
    }
    
    public void recibirMensajesServidor()
    {
        // Obtiene el flujo de entrada del socket
        DataInputStream entradaDatos = null;
        String mensaje;
        try 
        {
            entradaDatos = new DataInputStream(socket.getInputStream());
        } 
        catch (IOException ex) 
        {
            System.out.println("Error al crear el stream de entrada: " + ex.getMessage());
        } 
        catch (NullPointerException ex) 
        {
            System.out.println("El socket no se creo correctamente. ");
        }
        
        // Bucle infinito que recibe mensajes del servidor
        boolean conectado = true;
        while (conectado) 
        {
            try 
            {
                mensaje = entradaDatos.readUTF();
                mensajesChat.append(mensaje + System.lineSeparator());
            } 
            catch (IOException ex) 
            {
                System.out.println("Error al leer del stream de entrada: " + ex.getMessage());
                conectado = false;
            } 
            catch (NullPointerException ex) 
            {
                System.out.println("El socket no se creo correctamente. ");
                conectado = false;
            }
        }
    }
    
    public static void main(String[] args) 
    {     
    	//Creo un nuevo Cliente que contiene el form e inicializa la conexion.
        Cliente c = new Cliente();
        //Ejecucion del metodo recibirMensajesServidor que es un while(true) a la espera de mensajes del servidor.
        c.recibirMensajesServidor();
    }

}