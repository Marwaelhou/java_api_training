package fr.lernejo.navy_battle.prototypes;

import java.util.Arrays;
import java.util.stream.Collectors;

public class WriteableGameMap extends BaseGameMap {
    public WriteableGameMap(boolean fill) {
        super(fill);

        if (fill)
            printMap();
    }

    public void printMap() {
        System.out.println(" .... ");
        for (GameCell[] row : getMap()) {
            System.out.println(Arrays.stream(row).map(GameCell::getLetter).collect(Collectors.joining(" ")));
        }
        System.out.println(" .... ");
    }

    public boolean hasShipLeft() {
        for (var row : getMap()) {
            if (Arrays.stream(row).anyMatch(s -> s == GameCell.BOAT))
                return true;
        }
        return false;
    }

    public void setCell(Coordinates coordinates, GameCell newStatus) {
        getMap()[coordinates.getX()][coordinates.getY()] = newStatus;
    }
}
