import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;


public class UrlContentCollector {


    public static void UrlContentCollector(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://example.com/health/index.html"))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Page content: \n" + response.body());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}


