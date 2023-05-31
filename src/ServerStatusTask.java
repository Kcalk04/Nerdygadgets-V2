import javax.swing.*;
import java.util.TimerTask;

class ServerStatusTask extends TimerTask {
    private JTable table;
    private MonitoringTable monitoringTable;

    public ServerStatusTask(JTable table, MonitoringTable monitoringTable) {
        this.table = table;
        this.monitoringTable = monitoringTable;
    }

    @Override
    public void run() {
        monitoringTable.getServerStatusi();
        SwingUtilities.invokeLater(() -> {
            table.repaint();
        });
    }
}
