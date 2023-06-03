import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.Map;

public class MonitoringTable extends AbstractTableModel {
    String[] columnNames = {"IconType", "Name", "Status", "Uptime", "Cpu usage", "Disk usage"};
    Object[][] rowData = {
            {"databaseserver", "DB-1", "Unknown", "", "", "", "http://192.168.1.108/Indexer.php"},
            {"databaseserver", "DB-2", "Unknown", "", "", "", "http://192.168.1.109/Indexer.php"},
            {"webserver", "HTTP-1", "Unknown", "", "", "", "http://192.168.1.104/Indexer.php"},
            {"webserver", "HTTP-2", "Unknown", "", "", "", "http://192.168.1.105/Indexer.php"},
            {"pfsense", "pfSense-LB", "Unknown", "", "", "", "https://192.168.1.1/nerdygadgets-V2-status/Indexer.php"}
    };

    private static final Map<String, ImageIcon> IMAGES = new HashMap<>();
    static {
        IMAGES.put("databaseserver", new ImageIcon( MonitoringTable.class.getResource("img/databaseserver-32x32.jpg")));
        IMAGES.put("webserver", new ImageIcon(MonitoringTable.class.getResource("img/webserver-32x34.jpg")));
        IMAGES.put("pfsense", new ImageIcon(MonitoringTable.class.getResource("img/pfsense-32x32.jpg")));
    }

    public void getServerStatusi() {
        //synchronized betekend dat beide threads of control aan deze rowData mogen werken
        synchronized (rowData) {
            ServerStatusService serverStatusService = new ServerStatusService();
            for (int i = 0; i < rowData.length; i++) {
                System.out.println(TimeService.timeStamp() + " getting status of: " + rowData[i][1]);
                ServerStatus serverStatus = serverStatusService.getStatus((String) rowData[i][6]);
                if (serverStatus == null) {
                    rowData[i][2] = "Unavailable";
                    rowData[i][3] = "-";
                    rowData[i][4] = "-";
                    rowData[i][5] = "-";
                    continue;
                }
                rowData[i][2] = "Active";
                rowData[i][3] = serverStatus.getUpTime();
                rowData[i][4] = serverStatus.getCpuUsage();
                rowData[i][5] = "" + serverStatus.getDiskUsage() + "%";
            }
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
        synchronized (rowData) {
            if (columnIndex == 0) {
                return IMAGES.get(rowData[rowIndex][columnIndex]);
            }
            return rowData[rowIndex][columnIndex];
        }
    }
}
