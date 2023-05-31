import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;

public class MonitoringTable extends AbstractTableModel {
    String[] columnNames = {"IconType", "Name", "Status", "Uptime", "Cpu usage", "Disk usage"};
    Object[][] rowData = {
            {"databaseserver", "DB-1", "Unknown", "", "", "", "http://localhost/projecten/NerdygadgetsV2/vendor/Indexer.php"},
            {"databaseserver", "DB-2", "Unknown", "", "", "", "http://localhost/projecten/NerdygadgetsV2/vendor/Indexer.php"},
            {"webserver", "HTTP-1", "Unknown", "", "", "", "http://192.168.178.20/projecten/NerdygadgetsV2/vendor/Indexer.php"},
            {"webserver", "HTTP-2", "Unknown", "", "", "", "http://localhost/projecten/NerdygadgetsV2/vendor/Indexer.php"},
            {"pfsense", "pfSense-LB", "Unknown", "", "", "", "http://localhost/projecten/NerdygadgetsV2/vendor/Indexer.php"}
    };

    private static final Map<String, ImageIcon> IMAGES = new HashMap<>();
    static {
        IMAGES.put("databaseserver", new ImageIcon( MonitoringTable.class.getResource("img/databaseserver-32x32.jpg")));
        IMAGES.put("webserver", new ImageIcon(MonitoringTable.class.getResource("img/webserver-32x34.jpg")));
        IMAGES.put("pfsense", new ImageIcon(MonitoringTable.class.getResource("img/pfsense-32x32.jpg")));
    }

    public void getServerStatusi() {
        ServerStatusService serverStatusService = new ServerStatusService();
        for (int i = 0; i < rowData.length; i++) {
            ServerStatus serverStatus = serverStatusService.getStatus((String) rowData[i][6]);
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
        if (columnIndex == 0) {
            return ImageIcon.class;
        }
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
        if (columnIndex == 0) {
            return IMAGES.get(rowData[rowIndex][columnIndex]);
        }
        return rowData[rowIndex][columnIndex];
    }
}
