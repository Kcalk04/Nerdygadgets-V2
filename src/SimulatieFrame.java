import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class SimulatieFrame extends JDialog implements ActionListener {
    public ArrayList<Component> componenten;
    public static CatalogusPanel catalogusPanel;
    public static VisualisatiePanel visualisatiePanel;
    public static OverviewPanel overviewPanel;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem jmiOpslaan;
    private JMenuItem jmiLaden;

    public SimulatieFrame(JFrame owner, boolean modal) {
        super(owner, modal);

        setTitle("Simulatie Modus");
        setSize(new Dimension(900,720));
        setLayout(new GridBagLayout());

        // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        componenten = new ArrayList<>();
        GridBagConstraints layout = new GridBagConstraints();

        // Zorgt ervoor dat het scherm gevuld word met de Panels (strecht)
        layout.fill = GridBagConstraints.BOTH;
        layout.gridy = 0;
        layout.weighty = 1;

        // De gridx is de x-positie in het grid (het totale grid bestaat uit (x: 6, y: 0)
        // grid (x: 0, y: 0)
        layout.gridx = 0;
        layout.gridy = 1;
        layout.weightx = 2;
        catalogusPanel = new CatalogusPanel(componenten);
        add(catalogusPanel, layout);

        // grid (x: 2, y: 0)
        layout.gridx = 1;
        layout.weightx = 3;
        visualisatiePanel = new VisualisatiePanel(componenten);
        add(visualisatiePanel, layout);

        // grid (x: 5, y: 0)
        layout.gridx = 3;
        layout.weightx = 2;
        overviewPanel = new OverviewPanel(componenten);
        add(overviewPanel, layout);

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        jmiOpslaan = new JMenuItem("Opslaan");
        jmiLaden = new JMenuItem("Laden");

        jmiOpslaan.addActionListener(this);
        jmiLaden.addActionListener(this);

        menu.add(jmiOpslaan);
        menu.add(jmiLaden);
        menuBar.add(menu);

        setJMenuBar(menuBar);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jmiOpslaan) {
            ontwerpOpslaan();
        } else if(e.getSource() == jmiLaden) {
            try {
                ontwerpLaden();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void ontwerpOpslaan() {
        if (Desktop.isDesktopSupported()) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.sim", "sim"));
            fileChooser.setDialogTitle("Opslaan");
            int userSelection = fileChooser.showSaveDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                try {
                    FileWriter file = new FileWriter(fileToSave.getAbsoluteFile() + ".sim");

                    JSONArray jsonArray = new JSONArray();

                    for(Component component : componenten) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("naam", component.getNaam());
                        jsonObject.put("kosten", component.getKosten());
                        jsonObject.put("beschikbaarheid", component.getBeschikbaarheid());
                        jsonObject.put("type", component.getType().ordinal());
                        jsonArray.add(jsonObject);
                    }

                    file.write(jsonArray.toJSONString());
                    file.close();
                    System.out.println("Successfully wrote to the file.");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

    public void ontwerpLaden() throws IOException, ParseException {
        if (Desktop.isDesktopSupported()) {

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.sim", "sim"));
            fileChooser.setDialogTitle("Opslaan");
            int userSelection = fileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.println("Save as file: " + fileToSave.getAbsolutePath());

                FileReader file = new FileReader(fileToSave.getAbsoluteFile());
                JSONParser jsonParser = new JSONParser();
                JSONArray jsonArray = (JSONArray) jsonParser.parse(file);

                componenten.clear();
                for(Object object : jsonArray) {
                    JSONObject jsonObject1 = (JSONObject) object;

                    String naam = (String) jsonObject1.get("naam");
                    double kosten = (double) jsonObject1.get("kosten");
                    double beschikbaarheid = (double) jsonObject1.get("beschikbaarheid");
                    long type = (long) jsonObject1.get("type");
                    int type1 = Integer.parseInt(String.valueOf(type));

                    ComponentType componentType = ComponentType.values()[type1];

                    Component nieuwComponent = new Component(naam, kosten, beschikbaarheid, componentType);
                    visualisatiePanel.voegComponentToe(nieuwComponent);
                }
                file.close();

                System.out.println("Successfully read the file.");
            }
        }
    }
}
