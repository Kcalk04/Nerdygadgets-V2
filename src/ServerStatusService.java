import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerStatusService {
    public ServerStatus getStatus(String url) {
        var statusString = getStatusString(url);
        if (statusString == null){
            return null;
        }
        return createServerStatus(statusString);
    }

    ServerStatus createServerStatus(String statusString) {
        ServerStatus serverStatus = new ServerStatus();
        String[] substrings = statusString.replaceAll(" +", " ").split("\n");
        if (substrings.length < 3) {
            System.err.println("Not enough data from status server");
            return null;
        }
        String[] cpuSubString = substrings[0].split(" ");
        serverStatus.cpuUsage = cpuSubString[2];
        String[] diskSubString = substrings[2].split(" ");
        serverStatus.diskUsage = Integer.parseInt(diskSubString[2].replaceAll("%", ""));
        var upTimeSubs = substrings[1].split(": ");
        serverStatus.upTime = upTimeSubs[1];
        return serverStatus;
    }

    private static String getStatusString(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode != 200) {
                System.out.println("HTTP request failed with status code: " + statusCode);
                return null;
            }
            System.out.println("Response code: " + statusCode);
            System.out.println("Page content: \n" + response.body());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
