
package Cliente;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Mensagem implements Serializable {
    
    private String nick;
    private String texto;
    private boolean status;
    private String nickReservado;
    private Set<String> Onlines = new HashSet<String>();
    private Metodos metodo;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    
    public String getNick() {
        return nick;
    }
    
    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getNickReservado() {
        return nickReservado;
    }

    public void setNickReservado(String nickReservado) {
        this.nickReservado = nickReservado;
    }

    public Set<String> getOnlines() {
        return Onlines;
    }

    public void setOnlines(Set<String> Onlines) {
        this.Onlines = Onlines;
    }

    public Metodos getMetodo() {
        return metodo;
    }

    public void setMetodo(Metodos metodo) {
        this.metodo = metodo;
    }
        
    public enum Metodos {
        CONECTADO, DESCONECTADO, ENVIA_PRIVADO, ENVIA_TODOS,TODOS_USUARIOS
    }
}
