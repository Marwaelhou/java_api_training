package fr.lernejo.navy_battle.prototypes;

public enum GameCell {
    EMPTY("."),
    MISSED_FIRE("-"),
    SUCCESSFUL_FIRE("X"),
    BOAT("B");

    private final String letter;

    GameCell(String letter) {
        this.letter = letter;
    }

    public String getLetter() {
        return letter;
    }
}
