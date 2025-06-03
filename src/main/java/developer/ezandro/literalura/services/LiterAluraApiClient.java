package developer.ezandro.literalura.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class LiterAluraApiClient {
    private static final String BASE_URL = "https://gutendex.com/books?search=";
    private final HttpClient client;

    public LiterAluraApiClient(HttpClient client) {
        this.client = client;
    }

    public String getData(String title) throws IOException {
        String formattedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
        try (HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NORMAL)
                .build()) {

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL+ formattedTitle))
                    .GET()
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() >= 400) {
                    throw new IOException("HTTP error " + response.statusCode() + " for " + title);
                }

                return response.body();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IOException("Request to " + title + " was interrupted", e);
            }
        }
    }
}