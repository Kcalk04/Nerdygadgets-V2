import static org.junit.jupiter.api.Assertions.*;

class GetServerStatusTest {
    @org.junit.jupiter.api.Test
    void createServerStatus() throws Exception {
        var statusGetter = new ServerStatusService();
        var status = statusGetter.createServerStatus("CPU Usage: 19,4%\n" +
                "Uptime: up 19 hours, 43 minutes\n" +
                "disk usage:  37%\n" +
                "<script>");
        assertEquals("19,4%", status.getCpuUsage());
        assertEquals(37, status.getDiskUsage());
        assertEquals("up 19 hours, 43 minutes", status.getUpTime());
    }
    @org.junit.jupiter.api.Test
    void createServerStatus_otherData() throws Exception {
        var statusGetter = new ServerStatusService();
        var status = statusGetter.createServerStatus("CPU Usage: 23.8%\n" +
                "Uptime: up 50 hours, 43 minutes\n" +
                "disk usage:  99%\n" +
                "<script>");
        assertEquals("23.8%", status.getCpuUsage());
        assertEquals(99, status.getDiskUsage());
        assertEquals("up 50 hours, 43 minutes", status.getUpTime());
    }
}