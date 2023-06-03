import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Properties;

public class ServerStatusService {
    private static SSLContext sslContext;
    static {
        try {
            final Properties props = System.getProperties();
            props.setProperty("jdk.internal.httpclient.disableHostnameVerification", Boolean.TRUE.toString());
            sslContext = setupSloppySSLConext();
        } catch (Exception e) {
            System.err.println(TimeService.timeStamp() + " Exception: " + e.getMessage());
            e.printStackTrace();
            sslContext = null;
        }
    }

    static SSLContext setupSloppySSLConext() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        return sslContext;
    }

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

    private String getStatusString(String url) {
        HttpClient client = HttpClient.newBuilder()
                .sslContext(sslContext)
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

    private static TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
    };
}
