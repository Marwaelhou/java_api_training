package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.prototypes.FireResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GamePlayTest {
    @Test
    public void testGame() {
        var game = new GamePlay();

        for (int i = 0; game.localMapShipLeft() && i < 100; i++) {
            var coordinates = game.getNextPlaceToHit();
            game.setFireResult(coordinates, game.hit(coordinates));
        }

        assertFalse(game.localMapShipLeft());
    }

    @Test
    public void testEmptyMap() {
        var game = new GamePlay();

        assertThrows(Exception.class, () -> {
            for (int i = 0; i < 200; i++) {
                var coordinates = game.getNextPlaceToHit();
                game.setFireResult(coordinates, FireResult.MISS);
            }
        });
    }
}
