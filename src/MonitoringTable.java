import javax.swing.table.AbstractTableModel;

public class MonitoringTable extends AbstractTableModel {
    String[] columnNames = {"IconType", "Name", "Status", "Uptime", "Cpu usage", "Disk usage"};
    Object[][] rowData = {
            {"Test", "Test", "Unknown", "", "", ""},
            {"abc", "abc", "Unknown", "", "", ""},
            {"Raja", "Raja", "Unknown", "", "", ""},
            {"Raja", "Raja", "Unknown", "", "", ""},
            {"Raja", "Raja", "Unknown", "", "", ""}
    };

    public void getServerStatusi() {
        ServerStatusService serverStatusService = new ServerStatusService();
        for (int i = 0; i < rowData.length; i++) {
            ServerStatus serverStatus = serverStatusService.getStatus();
            if (serverStatus == null) {
                rowData[i][2] = "Unavailable";
                rowData[i][3] = "-";
                rowData[i][4] = "-";
                rowData[i][5] = "-";
                continue;
            }
            System.out.println(serverStatus);
            rowData[i][2] = "Active";
            rowData[i][3] = serverStatus.upTime;
            rowData[i][4] = serverStatus.cpuUsage;
            rowData[i][5] = "" + serverStatus.diskUsage + "%";
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getRowCount() {
        return rowData.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }
}
