package fr.lernejo.navy_battle.scenarios;

import fr.lernejo.navy_battle.RequestHandler;
import fr.lernejo.navy_battle.Server;
import fr.lernejo.navy_battle.prototypes.GameStatus;

import java.io.IOException;

public class HttpServerTest extends Server implements AutoCloseable {

    final Object lock = new Object();

    @Override
    public void fire() throws IOException, InterruptedException {
        super.fire();
        checkForEnd();
    }

    @Override
    public void handleFire(RequestHandler handler) throws IOException {
        super.handleFire(handler);
        checkForEnd();
    }

    private boolean gameEnded() {
        return gamePlay.get().getStatus() != GameStatus.ONGOING;
    }

    private void checkForEnd() {
        if (gameEnded())
            stopServer();
    }

    @Override
    public void stopServer() {
        super.stopServer();

        synchronized (lock) {
            lock.notify();
        }
    }

    @Override
    public void close() throws Exception {
        super.stopServer();
    }

    public void waitForEndOfGame() throws InterruptedException, Exception {
        synchronized (lock) {
            lock.wait(5000);

            if (!gameEnded())
                throw new RuntimeException("The game did not complete in allowed time!");
        }
    }
}
