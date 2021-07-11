package fr.lernejo.navy_battle.scenarios;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PingTest extends BaseHttpTests {
    @Test
    public void testPing() throws Exception {
        int port = getRandomPort(0);
        var test = new ScenariosTest(port);
        try {
            waitForPortToBeAvailable(port);
            assertEquals("OK", doGet(port, "ping"));
        } finally {
            test.stop();
        }

    }
}
