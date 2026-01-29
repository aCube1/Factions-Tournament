package factions;

public class Rules {
    public String getRules(){
        return
        "Regras do Torneio Factions:" + 
        "\n1. Cada equipe tem no máximo 10 personagens." + 
        "\n2. O jogo possui três tipos de personagens com características e habilidades distintas." +
        "\n3. Ataques reduzem os PVs dos personagens adversários." +
        "\n4. Um personagem é eliminado quando seus PVs chegam a 0." +
        "\n5. A equipe que eliminar todos os personagens adversários vence o torneio.";
    }
    public String getDescriptionCharacters(){
        return
        "\nDescrição dos Personagens:" +
        "\n1. O Guardião possui o maior PVs entre todos e sua habilidade é o vigor, que permite não ter seu PV reduzido, caso seu ele seja maior que o ataque do adversário." +
        " \n \tPV: 125. Vigor: 80. Ataque Corpo a Corpo: 30." +

        "\n2. O Mago é especialista em ataques mágicos, porém eles acabam com sua mana. Caso ela se esgote, ele não poderá mais atacar de longe e deverá atacar corpo a corpo." +
        " \n \tPV: 100. Mana: 50. Ataque Mágico: 20. Ataque Corpo a Corpo: 10." +

        "\n3. O Caçador muito perigoso a longas distâncias, especialmente graças à habilidade de precisão, capaz de causar dano crítico, que pode tirar o dobro de PVs do adversário." +
        " \n \tPV: 75. Ataque à Distância: 75.";

    }
}
