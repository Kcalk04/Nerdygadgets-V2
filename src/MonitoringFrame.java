import jdk.jfr.Percentage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MonitoringFrame extends JFrame implements ActionListener {
    private static LowLevelMonitoringPanel lowLevelMonitoringPanel;

    public MonitoringFrame(JFrame owner, boolean modal) {
        setTitle("Monitoring");
        setSize(new Dimension(900,720));

        lowLevelMonitoringPanel = new LowLevelMonitoringPanel(this);
        add(lowLevelMonitoringPanel);

        // Zorgt ervoor dat het frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        GridBagConstraints layout = new GridBagConstraints();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
