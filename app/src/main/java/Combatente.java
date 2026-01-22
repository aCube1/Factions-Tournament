package FactionsTournament.app.src.main.java;

public class Combatente {
    private String nome;
    private float xp; //pontos de experiência
    private float pv; //pontos de vida
    private float atk_fisico;
    private float velocidade;

    public Combatente(String nome, float xp, float pv, float atk_fisico, float velocidade){
        this.nome = nome;
        this.xp = xp;
        this.pv = pv;
        this.atk_fisico = atk_fisico;
        this.velocidade = velocidade;
    }
    public float atacar(){
        pv = pv - atk_fisico;
        return pv;
    }
}
/*O padrao de PV será o mago, o Guardiao terá 1.5 PV do mago e o arqueiro 0,75 PV do mago
Exemplo: Mago:100PV ; Guardiao:150PV ; Caçador:75PV */