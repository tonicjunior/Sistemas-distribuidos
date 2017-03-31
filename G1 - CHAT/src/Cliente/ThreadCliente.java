/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Toni
 */
public class ThreadCliente {
    Socket socket=null;
    ObjectOutputStream output;
    
    public Socket connect() {
        try {
            socket = new Socket("127.0.0.1", 7000);
            output = new ObjectOutputStream(socket.getOutputStream());
        } catch (UnknownHostException ex) {
            System.out.println(ex);
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return socket;
    }
    
    public void send(Mensagem mensagem) {
        try {
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
