import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptimalisatieDialog extends JDialog implements ActionListener {
    private JTextField jtfBeschikbaarheidInput;
    public JButton Bereken;
    private static double inputBeschikbaarheid;

    public OptimalisatieDialog(JFrame parent) {
        super(parent, "Nieuw component toevoegen", true);
        setSize(300, 100);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        JLabel sizeLabel = new JLabel("Beschikbaarheid:");
        jtfBeschikbaarheidInput = new JTextField(12);

        Bereken = new JButton("Bereken!");
        Bereken.addActionListener(this);


        add(sizeLabel);
        add(jtfBeschikbaarheidInput);
        add(new JLabel());
        add(Bereken);
        setVisible(true);
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Bereken) {
            inputBeschikbaarheid = Double.parseDouble(jtfBeschikbaarheidInput.getText());
            if (inputBeschikbaarheid >= 100 || inputBeschikbaarheid <= 0) {
                System.out.println("Ongeldige input, moet tussen 1 - 100");
                return;
            }
            optimalisatieTabel();
            dispose();
        }
    }
    public void optimalisatieTabel() {
        // Create the dialog
        JFrame frame = new JFrame();
        TabelOptimalisatie table = new TabelOptimalisatie(frame, getInputBeschikbaarheid());
        // Wait for the dialog to close
        table.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    public static double getInputBeschikbaarheid(){
        return inputBeschikbaarheid;
    }
}