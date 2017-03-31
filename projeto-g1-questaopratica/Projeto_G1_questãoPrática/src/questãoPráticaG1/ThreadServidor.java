/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questãoPráticaG1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 113851510
 */

class ThreadServidor extends Thread implements Serializable {

    Socket clientSocket;
    ArrayList<Pessoa> pessoas;
    ObjectInputStream receiver;
    ObjectOutputStream sender;
    PrintStream senderText;
    Metodos metodos;

    ThreadServidor(Socket clientSocket, ArrayList pessoas, Metodos metodos) {
        this.clientSocket = clientSocket;
        this.pessoas = pessoas;
        this.metodos = metodos;
    }

    @Override
    public void run() {
        try {
            senderText = new PrintStream(clientSocket.getOutputStream(), true);
            sender = new ObjectOutputStream(clientSocket.getOutputStream());
            receiver = new ObjectInputStream(clientSocket.getInputStream());
            Pessoa pessoa;
            do {
                pessoa = (Pessoa) receiver.readObject();
                switch (pessoa.getMetodo()) {
                    case "inserir":
                        metodos.inserir(pessoa);
                        sender.writeObject(metodos.pessoasmaneiras());                       
                        sender.reset();
                        break;
                        
                       case "hora":  
                         sender.writeObject(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime()));
                        break;
                        
                         case "imprimir":  
                         sender.writeObject(metodos.pessoasmaneiras());
                         sender.reset();
                        break;
               
                 
                }
            } while (!pessoa.getMetodo().equals("sair"));
            senderText.close();
            sender.close();
            receiver.close();
            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro: " + e);
            System.exit(0);
        }
    }
}