import javax.swing.SwingWorker;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class ServerStatusWorker extends SwingWorker<ServerStatus, Void> {
    private final String url;
    private final MonitoringTable model;
    private final int row;

    public ServerStatusWorker(String url, MonitoringTable model, int row) {
        this.url = url;
        this.model = model;
        this.row = row;
    }

    @Override
    protected ServerStatus doInBackground() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode != 200) {
            System.out.println("HTTP request failed with status code: " + statusCode);
            return null;
        }
        ServerStatusService service = new ServerStatusService();
        String statusString = response.body();
        ServerStatus serverStatus = service.createServerStatus(statusString);
        return serverStatus;
    }

    @Override
    protected void done() {
        try {
            ServerStatus serverStatus = get();
            if (serverStatus != null) {
                model.setValueAt(serverStatus.getUpTime(), row, 3);
                model.setValueAt(serverStatus.getCpuUsage(), row, 4);
                model.setValueAt(serverStatus.getDiskUsage(), row, 5);
            } else {
                model.setValueAt("error", row, 2);
            }
        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }
    }
}