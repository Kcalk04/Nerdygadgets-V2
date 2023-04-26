import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame implements ActionListener {

    public JButton jbSimulatie;
    public JButton jbMonitoring;

    public StartFrame() {
        setTitle("ICTM2n - Groep 1");
        setSize(new Dimension(600,400));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,2,20,0));

        jbSimulatie = new JButton("Simulatie");
        jbSimulatie.addActionListener(this);
        add(jbSimulatie);

        jbMonitoring = new JButton("Monitoring");
        jbMonitoring.addActionListener(this);
        add(jbMonitoring);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jbSimulatie) {

        } else if(e.getSource() == jbMonitoring) {

        }
    }
}
