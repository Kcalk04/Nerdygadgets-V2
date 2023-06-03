import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ServerStatusService {
    public ServerStatus getStatus(String url) {
        var statusString = getStatusString(url);
        if (statusString == null){
            return null;
        }
        return createServerStatus(statusString);
    }

    public ServerStatus createServerStatus(String statusString) {
        ServerStatus serverStatus = new ServerStatus();
        String[] substrings = statusString.replaceAll(" +", " ").split("\n");
        if (substrings.length < 3) {
            System.err.println(TimeService.timeStamp() + " Not enough data from status server");
            return null;
        }
        String[] cpuSubString = substrings[0].split(" ");
        serverStatus.setCpuUsage(cpuSubString[2]);
        String[] diskSubString = substrings[2].split(" ");
        serverStatus.setDiskUsage(Integer.parseInt(diskSubString[2].replaceAll("%", "")));
        var upTimeSubs = substrings[1].split(": ");
        serverStatus.setUpTime(upTimeSubs[1]);
        return serverStatus;
    }

    private static String getStatusString(String url) {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.statusCode();
            if (statusCode != 200) {
                System.err.println(TimeService.timeStamp() + " HTTP request failed with status code: " + statusCode);
                return null;
            }
            System.out.println(TimeService.timeStamp() + " Response code: " + statusCode);
            System.out.println(TimeService.timeStamp() + " Page content: \n" + response.body());
            return response.body();
        } catch (IOException | InterruptedException e) {
            System.err.println(TimeService.timeStamp() + " " + e.getMessage());
//            e.printStackTrace();
            return null;
        }
    }
}
