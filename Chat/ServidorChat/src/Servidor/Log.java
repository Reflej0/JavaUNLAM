package Servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log 
{
	private static final String DIRECTORIOCHAT = "./logs/chat.txt";
	private static final String DIRECTORIOERROR = "./logs/errores.txt";
	private static final String DIRECTORIOINFO = "./logs/info.txt";
	
	public static void imprimirLogChat(String mensaje)
	{
		try 
		{
			String fActual = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
			PrintWriter salida = new PrintWriter(new FileOutputStream(new File(DIRECTORIOCHAT), true));
			salida.println(fActual+" "+mensaje);
			salida.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Ocurrió un error con la escritura del log.");
		}
	}
	
	public static void imprimirError(String mensaje)
	{
		try 
		{
			String fActual = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
			PrintWriter salida = new PrintWriter(new FileOutputStream(new File(DIRECTORIOERROR), true));
			salida.println(fActual+" "+mensaje);
			salida.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Ocurrió un error con la escritura del log.");
		}
	}
	
	public static void imprimirInfo(String mensaje)
	{
		try 
		{
			String fActual = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss").format(Calendar.getInstance().getTime());
			PrintWriter salida = new PrintWriter(new FileOutputStream(new File(DIRECTORIOINFO), true));
			salida.println(fActual+" "+mensaje);
			salida.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("Ocurrió un error con la escritura del log.");
		}
	}
}
