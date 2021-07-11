package fr.lernejo.navy_battle.scenarios;

import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.SecureRandom;
import java.util.Random;

import static java.lang.Thread.sleep;

public class BaseHttpTests {
    private final Random random = new SecureRandom();
    private final HttpClient client = HttpClient.newHttpClient();

    protected int getRandomPort(int offset) {
        int port;
        do {
            port = random.nextInt(200) + (offset * 200) + 6000;
        } while(!freePort(port));

        return port;
    }

    public void waitForPortToBeAvailable(int port) throws Exception {
        for (int i = 0; i < 100 && freePort(port); i++) {
            //noinspection BusyWait
            sleep(150);
        }

        if (freePort(port))
            throw new Exception("Port " + port + " did not make it on time!");
    }

    // https://stackoverflow.com/a/15340291
    private boolean freePort(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    protected String doGet(int port, String uri) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:" + port + "/" + uri))
            .GET()
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() < 200 || response.statusCode() > 299)
            throw new Exception("Request faild with status " + response.statusCode() + " !");

        return response.body();
    }

    public JSONObject doPost(int port, String uri, JSONObject obj) throws IOException, InterruptedException {
        String url = "http://localhost:" + port + "/" + uri;

        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(obj.toString()))
            .build();

        var response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        return new JSONObject(response.body());
    }
}
