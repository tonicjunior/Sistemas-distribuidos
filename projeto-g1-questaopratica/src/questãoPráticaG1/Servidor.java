/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package questãoPráticaG1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Toni
 */
public class Servidor {
    

    public static ArrayList<Pessoa> pessoas = new ArrayList<>();
    public static Metodos metodos = new Metodos(pessoas);

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        Socket clientSocket;
        try {
            serverSocket = new ServerSocket(7000);
        } catch (IOException e) {
            System.err.println("Servidor nao pode ouvir a porta: 7000");
            System.exit(0);
        }
        while (true) {
            try {
                System.out.println("\n\nEsperando cliente...");
                clientSocket = serverSocket.accept();

                new ThreadServidor(clientSocket, pessoas, metodos).start();
            } catch (IOException e) {
                System.err.println("Erro Servidor :" + e);
                System.exit(0);
            }
        }
    }
}


class Metodos {

    ArrayList<Pessoa> pessoas;
  
    Metodos(ArrayList<Pessoa> pessoas) {
        this.pessoas = pessoas;
      
    }

    public synchronized void inserir(Pessoa pessoa) {
        pessoas.add(pessoa);
    } 
      
    public ArrayList<Pessoa> pessoasmaneiras() {
        return pessoas;
    }

}
