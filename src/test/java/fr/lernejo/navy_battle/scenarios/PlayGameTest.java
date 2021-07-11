package fr.lernejo.navy_battle.scenarios;

import fr.lernejo.navy_battle.prototypes.ServerInfo;
import org.junit.jupiter.api.Test;

public class PlayGameTest extends BaseHttpTests {

    /*@Test
    public void playAgainHimself() throws Exception {
        int port = getRandomPort(1);
        try (var serverOne = new ScenariosTest(port)) {
            waitForPortToBeAvailable(port);

            var srv = new ServerInfo(
                "0xDEADBEEF",
                "http://localhost:" + (port), "Tests are the best!"
            );
            var res = doPost(port, "api/game/start", srv.toJSON());

            ServerInfo.fromJSON(res);

        }
    }*/

    @Test
    public void playStandalonePart() throws Exception {
        int standalonePort = getRandomPort(2);
        int localPort = standalonePort + 1;
        try (var serverOne = new ScenariosTest(standalonePort)) {
            waitForPortToBeAvailable(standalonePort);

            try (var clientOne = new HttpServerTest()) {
                clientOne.startServer(localPort, "http://localhost:" + standalonePort);

                clientOne.waitForEndOfGame();
            }
        }
    }

    @Test
    public void playSecondPart() throws Exception {
        int standalonePort = getRandomPort(3);
        int clientPort = standalonePort + 1;
        try (var clientOne = new HttpServerTest()) {
            clientOne.startServer(clientPort, null);
            waitForPortToBeAvailable(clientPort);

            try (var serverOne = new ScenariosTest(standalonePort, "http://localhost:" + clientPort)) {

                clientOne.waitForEndOfGame();
            }
        }
    }
}
