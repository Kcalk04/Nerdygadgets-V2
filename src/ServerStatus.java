public class ServerStatus {
    String cpuUsage;
    int diskUsage;
    String upTime;

    @Override
    public String toString() {
        return "ServerStatus{" +
                "cpuUsage='" + cpuUsage + '\'' +
                ", diskUsage=" + diskUsage +
                ", upTime='" + upTime + '\'' +
                '}';
    }
}
