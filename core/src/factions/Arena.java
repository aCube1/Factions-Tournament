import java.util.*;

public class Arena {

    private int[] times;
    private List<Character> personagens;

    public Arena(int[] times) {
        this.times = new int[times.length];
        this.personagens = new ArrayList<>();
    }

    public List<Character> adicionarPersonagem() {
        
        personagens.add(new Character()); // Método para adicionar uma personagem à arena
        return personagens;
        }

    public void removerEntidade() {
        for (Character personagem : personagens) {
            personagens.removeIf(personagem -> personagem.getPV() == 0); // Método para remover uma entidade da arena
            }
        }

    public int[] getTimes() {
        // Método para obter os times na arena

        return times;
    }

    public void iniciarCombate() {
        // Método para iniciar o combate entre as entidades na arena

    }

    public boolean condicaoVitoria() {
        
        if(times[x] == 0) {
            System.out.println("Vitória do time " + times[x] + "!");
            return true;
        } else if(times[y] == 0) {
            System.out.println("Vitória do time " + times[y] + "!"); // Método para verificar a condição de vitória
            return true;
        } else {
            return false;
        }
    }

    /* Precisa de outros métodos para atribuir entidades, 
     gerenciar turnos, etc. */

}