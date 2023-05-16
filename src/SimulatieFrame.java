import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;

public class SimulatieFrame extends JDialog implements ActionListener, KeyListener {
    public ArrayList<Component> componenten;
    public static SimulatieFrame simulatieFrame;
    public static CatalogusPanel catalogusPanel;
    public static VisualisatiePanel visualisatiePanel;
    public static OverviewPanel overviewPanel;
    public boolean shiftKeyPressed;
    public boolean nKeyPressed;
    public boolean sKeyPressed;
    public boolean oKeyPressed;
    public boolean ctrlKeyPressed;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem jmiNieuw;
    private JMenuItem jmiOpslaan;
    private JMenuItem jmiDupliceer;
    private JMenuItem jmiLaden;
    private String saveFilePath;

    public SimulatieFrame(JFrame owner, boolean modal) {
        super(owner, modal);

        setTitle("Simulatie Modus");
        setSize(new Dimension(900,720));
        setLayout(new GridBagLayout());

        saveFilePath = "";

        // Zorgt ervoor dat de frame niet in fullscreen kan en niet uit te groten is
        setResizable(false);

        componenten = new ArrayList<>();
        GridBagConstraints layout = new GridBagConstraints();

        simulatieFrame = this;

        // Zorgt ervoor dat het scherm gevuld word met de Panels (strecht)
        layout.fill = GridBagConstraints.BOTH;
        layout.gridy = 0;
        layout.weighty = 1;

        // De gridx is de x-positie in het grid (het totale grid bestaat uit (x: 6, y: 0)
        // grid (x: 0, y: 0)
        layout.gridx = 0;
        layout.gridy = 1;
        layout.weightx = 1;
        catalogusPanel = new CatalogusPanel(componenten);
        add(catalogusPanel, layout);

        // grid (x: 2, y: 0)
        layout.gridx = 1;
        layout.weightx = 2;
        visualisatiePanel = new VisualisatiePanel(componenten);
        add(visualisatiePanel, layout);

        // grid (x: 5, y: 0)
        layout.gridx = 3;
        layout.weightx = 2;
        overviewPanel = new OverviewPanel(componenten);
        add(overviewPanel, layout);

        menu = new JMenu("File");

        menuBar = new JMenuBar();
        menuBar.setFocusable(false);

        jmiNieuw = new JMenuItem("Nieuw ontwerp");
        jmiOpslaan = new JMenuItem("Opslaan");
        jmiDupliceer = new JMenuItem("Opslaan als");
        jmiLaden = new JMenuItem("Laden");

        jmiNieuw.addActionListener(this);
        jmiOpslaan.addActionListener(this);
        jmiDupliceer.addActionListener(this);
        jmiLaden.addActionListener(this);

        menu.add(jmiNieuw);
        menu.add(jmiOpslaan);
        menu.add(jmiDupliceer);
        menu.add(jmiLaden);
        menuBar.add(menu);

        setJMenuBar(menuBar);

        addKeyListener(this);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == jmiNieuw) {
            nieuwOntwerp();
        }
        else if(e.getSource() == jmiOpslaan) {
            ontwerpOpslaan(false);
        } else if(e.getSource() == jmiDupliceer) {
            ontwerpOpslaan(true);
        }
        else if(e.getSource() == jmiLaden) {
            try {
                ontwerpLaden();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (ParseException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void nieuwOntwerp() {
        int input = JOptionPane.showConfirmDialog(null, "Weet je zeker dat je een nieuw ontwerp wilt aanmaken?", "Waarschuwing", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if(input == JOptionPane.YES_OPTION) {
            saveFilePath = "";
            SimulatieFrame.visualisatiePanel.removeAll();
            SimulatieFrame.visualisatiePanel.repaint();
            SimulatieFrame.visualisatiePanel.totaleKosten = 0.0;
            SimulatieFrame.visualisatiePanel.totaleBeschikbaarheid = 1;
            componenten.clear();
        }
    }

    public void ontwerpOpslaan(boolean dupliceren) {
        if (Desktop.isDesktopSupported()) {
            if(saveFilePath.isEmpty() || dupliceren) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.sim", "sim"));
                fileChooser.setDialogTitle("Ontwerp opslaan");
                int userSelection = fileChooser.showSaveDialog(this);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    opslaan(fileToSave.getAbsolutePath());
                }
            } else {
                opslaan(saveFilePath);
            }
        }
    }

    public void opslaan(String path) {
        try {
            FileWriter file = new FileWriter(path + ".sim");
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

            saveFilePath = path;
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void ontwerpLaden() throws IOException, ParseException {
        if (Desktop.isDesktopSupported()) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("*.sim", "sim"));
            fileChooser.setDialogTitle("Ontwerp laden");
            int userSelection = fileChooser.showOpenDialog(this);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                saveFilePath = fileToSave.getAbsolutePath();
                saveFilePath = saveFilePath.replace(".sim", "");
                System.out.println(saveFilePath);

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

                    if(visualisatiePanel.jmiVerwijderen != null) {
                        visualisatiePanel.remove(visualisatiePanel.jmiVerwijderen);
                    }

                    Component nieuwComponent = new Component(naam, kosten, beschikbaarheid, componentType);
                    visualisatiePanel.voegComponentToe(nieuwComponent);
                }
                file.close();

                System.out.println("Successfully read the file.");
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void updateKeyState(KeyEvent e, boolean state) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL || e.getKeyCode() == KeyEvent.VK_META) {
            ctrlKeyPressed = state;
        }
        if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
            shiftKeyPressed = state;
        }
        if(e.getKeyCode() == KeyEvent.VK_N) {
            nKeyPressed = state;
        }
        if(e.getKeyCode() == KeyEvent.VK_S) {
            sKeyPressed = state;
        }
        if(e.getKeyCode() == KeyEvent.VK_O) {
            oKeyPressed = state;
        }
    }

    public void releasePressedKeys() {
        ctrlKeyPressed = false;
        shiftKeyPressed = false;
        nKeyPressed = false;
        sKeyPressed = false;
        oKeyPressed = false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        updateKeyState(e, true);

        if(ctrlKeyPressed && nKeyPressed) {
            releasePressedKeys();
            nieuwOntwerp();
        }
        if(ctrlKeyPressed && !shiftKeyPressed && sKeyPressed) {
            releasePressedKeys();
            ontwerpOpslaan(false);
        } else if(ctrlKeyPressed && shiftKeyPressed && sKeyPressed) {
            releasePressedKeys();
            updateKeyState(e, true);
            ontwerpOpslaan(true);
        }
        else if(ctrlKeyPressed && oKeyPressed) {
            try {
                releasePressedKeys();
                ontwerpLaden();
            } catch (IOException | ParseException ex) {
                releasePressedKeys();
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        updateKeyState(e, false);
    }
}
