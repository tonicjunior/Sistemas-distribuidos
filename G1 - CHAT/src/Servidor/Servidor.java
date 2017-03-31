package Servidor;
import Servidor.ThreadServidor;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Toni
 */

public class Servidor {
   
    static Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();
    
    
 public static void main(String[] args) throws IOException {


       ServerSocket sSocket = null;
        Socket cSocket;
        try {
            sSocket = new ServerSocket(7000);
        } catch (IOException e) {
            System.err.println("Servidor nao pode ouvir a porta: 7000 ");
            System.exit(0);
        }
        
        while(true){
            try{
	        System.out.println("\n\nEsperando cliente:"+
                                    "Endereço IP do servidor: " + sSocket.getInetAddress());
                cSocket = sSocket.accept();
                     ThreadServidor t = new ThreadServidor(cSocket, mapOnlines);                      
                      t.start();
                   
              
            }catch(IOException e){
                System.err.println("Erro Servidor :"+e);
                System.exit(0);	  	
            }
        } 
     
               
    }
}
