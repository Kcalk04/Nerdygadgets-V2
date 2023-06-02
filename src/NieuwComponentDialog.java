import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;

public class NieuwComponentDialog extends JDialog {
    private JTextField JtNaam;
    private JTextField JtPrijs;
    private JTextField JtBeschikbaarheid;
    private JRadioButton typePfsense;
    private JRadioButton typeDatabase;
    private JRadioButton typeWeb;

    public NieuwComponentDialog(JFrame parent) {
        super(parent, "Nieuw component toevoegen", true);
        setSize(new Dimension(300,400));

        // Tekstvelden aanmaken
        JtNaam = new JTextField(10);
        JtPrijs = new JTextField(10);
        JtBeschikbaarheid = new JTextField(10);

        // Radiobuttons aanmaken en toevoegen aan een groep zodat er maar één aangeklikt kan worden tegelijkertijd
        typePfsense = new JRadioButton("Pfsense");
        typeDatabase = new JRadioButton("Databaseserver");
        typeWeb = new JRadioButton("Webserver");

        ButtonGroup group = new ButtonGroup();
        group.add(typePfsense);
        group.add(typeDatabase);
        group.add(typeWeb);

        // OK knop aanmaken en actionlistener toevoegen om ingevulde data op te slaan in variables
        JButton okButton = new JButton("OK");

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String typeComponent = "";

                if(typePfsense.isSelected()) {
                    typeComponent = "pfsense";
                }
                if(typeDatabase.isSelected()) {
                    typeComponent = "databaseserver";
                }
                if(typeWeb.isSelected()) {
                    typeComponent = "webserver";
                }

                String naam = JtNaam.getText();;
                double prijs = Double.parseDouble(JtPrijs.getText());
                double beschikbaarheid = Double.parseDouble(JtBeschikbaarheid.getText());
                String type = typeComponent;

                Database.insertData(naam, prijs, beschikbaarheid, type);
                Database.haalComponentenOp(SimulatieFrame.catalogusPanel.catalogusComponenten);
                SimulatieFrame.visualisatiePanel.repaint();

                int input = JOptionPane.showOptionDialog(null, "Component toegevoegd!", "Succes!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[] {"Ok"}, "Ok");
                if(input == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        // Keylistener om alleen getallen toe te laten voor de prijs input
        JtPrijs.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
                    e.consume();  // Ignore non-digit characters
                }
            }
        });

        // Keylistener om alleen getallen en "," en "." toe te laten voor de prijs input
        JtBeschikbaarheid.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != ',' && c != '.') {
                    e.consume();  // Ignore non-digit, non-comma, and non-period characters
                }
            }
        });

        // Create the layout
        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Naam component: "));
        panel.add(JtNaam);
        panel.add(new JLabel("Kosten component: "));
        panel.add(JtPrijs);
        panel.add(new JLabel("Beschikbaarheid component: "));
        panel.add(JtBeschikbaarheid);
        panel.add(typePfsense);
        panel.add(typeDatabase);
        panel.add(typeWeb);
        panel.add(new JLabel(" "));

        // Add the components to the dialog
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().add(okButton, BorderLayout.PAGE_END);

        // Set dialog properties
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        setVisible(true);
    }
}