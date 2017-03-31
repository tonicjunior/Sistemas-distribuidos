package Servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import Cliente.Mensagem;
import Cliente.Mensagem.Metodos;
import java.util.ArrayList;

public class ThreadServidor extends Thread {

    ServerSocket serverSocket;    
    Socket cSocket;    
    Map<String, ObjectOutputStream> mapOnlines = new HashMap<String, ObjectOutputStream>();


    ThreadServidor(Socket cSocket,Map<String, ObjectOutputStream> mapOnlines) {
        this.cSocket = cSocket;
        this.mapOnlines = mapOnlines;
    }
 

        public void run() { 
                    
            Mensagem mensagem = null;
            try {
             ObjectOutputStream output;
             ObjectInputStream input;
             output = new ObjectOutputStream(cSocket.getOutputStream());
             input = new ObjectInputStream (cSocket.getInputStream());
             
                while ((mensagem = (Mensagem) input.readObject()) != null) {
                    Metodos metodo = mensagem.getMetodo();
                    if (metodo.equals(Metodos.CONECTADO)) {
                        boolean isConnect = conectado(mensagem, output);
                        if (isConnect) {
                            mapOnlines.put(mensagem.getNick(), output);
                            Onlines();
                        }
                    } else if (metodo.equals(Metodos.DESCONECTADO)) {
                        desconectar(mensagem, output);
                        Onlines();
                        return;
                    } else if (metodo.equals(Metodos.ENVIA_PRIVADO)) {
                        enviarUSER(mensagem);
                    } else if (metodo.equals(Metodos.ENVIA_TODOS)) {
                        enviarTodos(mensagem);
                    }
                }
               }catch (IOException ex) {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    
    

    private boolean conectado(Mensagem mensagem, ObjectOutputStream output) {
        if (mapOnlines.size() == 0) {
            mensagem.setStatus(true);
            enviar(mensagem, output);
            return true;
        }

        if (mapOnlines.containsKey(mensagem.getNick())) {
            mensagem.setStatus(false);
            enviar(mensagem, output);
            return false;
        } else {
            mensagem.setStatus(true);
            enviar(mensagem, output);
            return true;
        }
    }

    private void desconectar(Mensagem mensagem, ObjectOutputStream output) {
        mapOnlines.remove(mensagem.getNick());
        System.out.println("Usuario" + mensagem.getNick()+ " saiu da sala!");
    }

    private void enviar(Mensagem mensagem, ObjectOutputStream output) {
        try {
            output.writeObject(mensagem);
        } catch (IOException ex) {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviarUSER(Mensagem mensagem) {
        for (Map.Entry<String, ObjectOutputStream> pessoa : mapOnlines.entrySet()) {
            if (pessoa.getKey().equals(mensagem.getNickReservado())) {
                try {
                    pessoa.getValue().writeObject(mensagem);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void enviarTodos(Mensagem mensagem) {
        for (Map.Entry<String, ObjectOutputStream> pessoa : mapOnlines.entrySet()) {
            if (!pessoa.getKey().equals(mensagem.getNick())) {              
                try {
                    pessoa.getValue().writeObject(mensagem);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void Onlines() {
        Set<String> setNames = new HashSet<String>();
        for (Map.Entry<String, ObjectOutputStream> pessoa : mapOnlines.entrySet()) {
            setNames.add(pessoa.getKey());
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setMetodo(Metodos.TODOS_USUARIOS);
        mensagem.setOnlines(setNames);

        for (Map.Entry<String, ObjectOutputStream> pessoa : mapOnlines.entrySet()) {
            mensagem.setNick(pessoa.getKey());
            try {
                pessoa.getValue().writeObject(mensagem);
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}