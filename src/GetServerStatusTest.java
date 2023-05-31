import static org.junit.jupiter.api.Assertions.*;

class GetServerStatusTest {

    @org.junit.jupiter.api.Test
    void createServerStatus() {
        var statusGetter = new ServerStatusService();
        var status = statusGetter.createServerStatus("CPU Usage: 19,4%\n" +
                "Uptime: up 19 hours, 43 minutes\n" +
                "disk usage:  37%\n" +
                "<script>");
        assertEquals("19,4%", status.cpuUsage);
        assertEquals(37, status.diskUsage);
        assertEquals("Uptime: up 19 hours, 43 minutes", status.upTime);
    }
    @org.junit.jupiter.api.Test
    void createServerStatus_otherData() {
        var statusGetter = new ServerStatusService();
        var status = statusGetter.createServerStatus("CPU Usage: 23.8%\n" +
                "Uptime: up 50 hours, 43 minutes\n" +
                "disk usage:  99%\n" +
                "<script>");
        assertEquals("23.8%", status.cpuUsage);
        assertEquals(99, status.diskUsage);
        assertEquals("Uptime: up 50 hours, 43 minutes", status.upTime);
    }
}