import java.util.*;

public class Arena {

    private int[] times;
    private List<Character> personagens;

    public Arena(int[] times) {
        this.times = new int[times.length];
        this.personagens = new ArrayList<>();
    }

    public List<Character> adicionarPersonagem() {
        // Método para adicionar uma personagem à arena
        personagens.add(new Character());
        return personagens;
        }

    public void removerEntidade() {
        // Método para remover uma entidade da arena
        for (Combatente personagem : getCombatentes()) {
            if (personagem.estaVivo() == false) {
                combatentes.remove(personagem);
                // Condição para remover o personagem da arena...
            } else {
                return void;
            }
        }
    }

    public int[] getTimes() {
        // Método para obter os times na arena

        return times;
    }

    public void iniciarCombate() {
        // Método para iniciar o combate entre as entidades na arena

    }e

    public boolean condicaoVitoria() {
        // Método para verificar a condição de vitória
        if(times[x] == 0) {
            System.out.println("Vitória do time " + times[x] + "!");
            return true;
        } else if(times[y] == 0) {
            System.out.println("Vitória do time " + times[y] + "!");
            return true;
        } else {
            return false;
        }
    }

    /* Precisa de outros métodos para atribuir entidades, 
     gerenciar turnos, etc. */

}