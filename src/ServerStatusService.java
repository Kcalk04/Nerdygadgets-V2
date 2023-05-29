import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ServerStatusService {
    public ServerStatus getStatus(){
        var statusString = getStatusString();
        return createServerStatus(statusString);
    }

    ServerStatus createServerStatus(String statusString) {

        ServerStatus serverStatus = new ServerStatus();
        String[] substrings = statusString.replaceAll(" +", " ").split("\n");
        String[] cpuSubString = substrings[0].split(" ");
        String[] diskSubString = substrings[2].split(" ");
//        for (String substring : substrings) {
//            System.out.println(substring);
//        }
        serverStatus.cpuUsage = cpuSubString[2];
        serverStatus.diskUsage = Integer.parseInt(diskSubString[2].replaceAll("%", ""));
        serverStatus.upTime = substrings[1];
        return serverStatus;
    }

    private static String getStatusString() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://localhost/projecten/NerdygadgetsV2/vendor/Indexer.php"))
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("Response code: " + response.statusCode());
            System.out.println("Page content: \n" + response.body());
            return response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
