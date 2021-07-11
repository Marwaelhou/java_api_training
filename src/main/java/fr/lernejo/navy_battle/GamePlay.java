package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.prototypes.*;

public class GamePlay {
    private final GameMap localMap;
    private final GameMap remoteMap;
    private final Option<GameStatus> status = new Option<>(GameStatus.ONGOING);

    public GamePlay() {
        localMap = new GameMap(true);
        remoteMap = new GameMap(false);
    }

    public void wonGame() {
        status.set(GameStatus.WON);

        System.out.println("Hourray we won the game!!! Pierre is the best!!!");
        System.out.println("The play is over!!!!");
        System.out.println("Adversary map:");
        remoteMap.printMap();

        System.out.println("Our map:");
        localMap.printMap();
    }

    public Coordinates getNextPlaceToHit() {
        return remoteMap.getNextPlaceToHit();
    }

    public void setFireResult(Coordinates coordinates, FireResult result) {
        if (result == FireResult.MISS)
            remoteMap.setCell(coordinates, GameCell.MISSED_FIRE);
        else
            remoteMap.setCell(coordinates, GameCell.SUCCESSFUL_FIRE);
    }

    public boolean localMapShipLeft() {
        return localMap.hasShipLeft();
    }

    public FireResult hit(Coordinates coordinates) {
        return localMap.hit(coordinates);
    }

    public GameStatus getStatus() {
        if (!localMap.hasShipLeft())
            status.set(GameStatus.LOST);
        return status.get();
    }
}
