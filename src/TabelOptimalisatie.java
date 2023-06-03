import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TabelOptimalisatie extends JDialog implements ActionListener {
    public JTable optimalisatieTabel;
    private Algoritme algoritme;
    private JButton jbImporteren;
    private JButton jbAnnuleren;

    public TabelOptimalisatie(JFrame parent, double beschikbaarheid) {
        super(parent, "Overzicht optimaal ontwerp", true);
        optimalisatieTabel = new JTable();

        algoritme = new Algoritme(beschikbaarheid, 1000000);
        ArrayList<Component> optimaalOntwerp = algoritme.optimaalOntwerp;

        // Create table column names
        String[] columnNames = {"Machine", "Beschikbaarheid", "Prijs", "Aantal"};

        // Maak een int gebaseerd op het aantal in de catalogusComponenten arraylist
        int numComponents = SimulatieFrame.catalogusPanel.catalogusComponenten.size();
        Object[][] data = new Object[numComponents + 2][4]; // 4 kolommen

        Map<String, Integer> componentCounts = new HashMap<>();

        for (Component component : optimaalOntwerp) {
            String componentName = component.getNaam();
            componentCounts.put(componentName, componentCounts.getOrDefault(componentName, 0) + 1);
        }

        for (int i = 0; i < numComponents; i++) {
            Component component = SimulatieFrame.catalogusPanel.catalogusComponenten.get(i);
            String componentName = component.getNaam();
            data[i][0] = componentName;
            data[i][1] = component.getKosten();
            data[i][2] = component.getBeschikbaarheid();
            data[i][3] = componentCounts.getOrDefault(componentName, 0);
        }

        // Lege rij aanmaken voor layout
        data[numComponents][0] = "";
        data[numComponents][1] = "";
        data[numComponents][2] = "";
        data[numComponents][3] = "";

        // Rij aanmaken voor de totalen
        data[numComponents + 1][0] = "Totalen";
        data[numComponents + 1][1] = "€" + algoritme.getTotaalKosten();
        data[numComponents + 1][2] = algoritme.berekenBeschikbaarheid(optimaalOntwerp) * 100 + "%";
        data[numComponents + 1][3] = optimaalOntwerp.size();


        TableModel model = new DefaultTableModel(data, new String[]{"Naam machine", "Prijs", "beschikbaarheid", "Aantal"});

        // Create the JTable with the DefaultTableModel
        JTable table = new JTable(model);

        // Create a JScrollPane to contain the table
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a JPanel and add the JScrollPane to it
        JPanel panel = new JPanel();
        panel.add(scrollPane);

        // Create buttons
        jbImporteren = new JButton("Importeren");
        jbAnnuleren = new JButton("Annuleren");

        // Add action listeners to the buttons
        jbImporteren.addActionListener(this);
        jbAnnuleren.addActionListener(this);

        // Create a JPanel for the buttons and add them to it
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(jbImporteren);
        buttonPanel.add(jbAnnuleren);

        // Add the panel and button panel to the frame
        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        setSize(new Dimension(600, 400));

        // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbImporteren) {
            ArrayList optimaalOntwerp = algoritme.optimaalOntwerp;
            SimulatieFrame.visualisatiePanel.transferDataFromOptimaalOntwerp(optimaalOntwerp);
            SimulatieFrame.visualisatiePanel.tekenVisualisatiePanel();

            SimulatieFrame.visualisatiePanel.berekenKosten();
            SimulatieFrame.visualisatiePanel.berekenBeschikbaarheid();
            SimulatieFrame.visualisatiePanel.berekenAantal();
            SimulatieFrame.overviewPanel.tekenOverviewPanel();
            dispose();
        } else if (e.getSource() == jbAnnuleren) {
            dispose();
        }
    }

}