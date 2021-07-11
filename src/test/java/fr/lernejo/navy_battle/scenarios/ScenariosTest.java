package fr.lernejo.navy_battle.scenarios;

import fr.lernejo.navy_battle.Launcher;

public class ScenariosTest implements AutoCloseable{
    private final Thread thread;

    public ScenariosTest(int port) {
        this(new String[]{String.valueOf(port)});
    }

    public ScenariosTest(int port, String remoteURL) {
        this(new String[]{String.valueOf(port), remoteURL});
    }

    public ScenariosTest(String[] args) {
        thread = new Thread(() -> {
            Launcher.main(args);
        });
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }

    @Override
    public void close() {
        stop();
    }
}
