import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptimalisatieDialog extends JDialog implements ActionListener {
    private JTextField sizeTextField;
    public JButton Bereken;
    private static double beschikbaarheid;

    public OptimalisatieDialog(JFrame parent) {
        super(parent, "Nieuw component toevoegen", true);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel sizeLabel = new JLabel("Beschikbaarheid:");
        sizeTextField = new JTextField(12);

        Bereken = new JButton("Bereken!");
        Bereken.addActionListener(this);


        add(sizeLabel);
        add(sizeTextField);
        add(new JLabel());
        add(Bereken);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Bereken) {
            optimalisatieTabel();
            beschikbaarheid = Double.parseDouble(sizeTextField.getText());
        } else if(e.getSource() != Bereken) {
            System.out.println("nahhhhhhhhhhhhh");
        }
    }
    public void optimalisatieTabel() {
        // Create the dialog
        JFrame frame = new JFrame();
        TabelOptimalisatie Table = new TabelOptimalisatie(frame);
        // Wait for the dialog to close
        Table.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    public static double getGewildeBeschikbaarheid(){
        return beschikbaarheid;
    }

    }