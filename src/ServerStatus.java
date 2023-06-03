public class ServerStatus {
    private String cpuUsage;
    private int diskUsage;
    private String upTime;
    public String getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(String cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public int getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(int diskUsage) {
        this.diskUsage = diskUsage;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }
    @Override
    public String toString() {
        return "ServerStatus{" +
                "cpuUsage='" + cpuUsage + '\'' +
                ", diskUsage=" + diskUsage +
                ", upTime='" + upTime + '\'' +
                '}';
    }

}
