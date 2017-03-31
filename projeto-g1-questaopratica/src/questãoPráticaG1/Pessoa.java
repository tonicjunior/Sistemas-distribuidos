package questãoPráticaG1;

import java.io.Serializable;


/**
 *
 * @author Toni
 */

public class Pessoa  implements Serializable{
    private String nome;
    private String telefone;
    private String metodo;
    
    public Pessoa() {
    }
        
    public Pessoa(String nome, String telefone,String me) {
        this.nome = nome;
        this.telefone = telefone;
        this.metodo = me;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }
       

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
      public String getMetodo() {
        return metodo;
    }
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    } 

    @Override
    public String toString() {
        return "\nNome: " + nome + ", Telefone: " + telefone;
    }
}
