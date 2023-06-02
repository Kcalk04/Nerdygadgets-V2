import java.util.ArrayList;


class Algoritme {
    private ArrayList<Component> CatalogusComponenten = new ArrayList<>();
    private double totaalBeschikbaarheidsPercentage;
    private double maximaleKosten;
    private double minimaleKosten = maximaleKosten;
    public ArrayList<Component> optimaalOntwerp;


    public ArrayList<Component> getOptimaalOntwerp() {
        return optimaalOntwerp;
    }

    public Algoritme(double inputBeschikbaarheid, double kosten){

        inputBeschikbaarheid = OptimalisatieDialog.getInputBeschikbaarheid()/100;
        this.maximaleKosten = kosten;
        this.totaalBeschikbaarheidsPercentage = inputBeschikbaarheid;
        minimaleKosten = maximaleKosten;

        ArrayList<Component> fireWallArrayList = new ArrayList<>();
        ArrayList<Component> webserverArrayList = new ArrayList<>();
        ArrayList<Component> databaseserverArrayList = new ArrayList<>();

        // Het initialiseren van de verschillende componenten
        Component pfsense = new Component("pfSense", 4000, 99.998, ComponentType.PFSENSE);
        Component mySQL1 = new Component("HAL9001DB", 5100, 90, ComponentType.DATABASESERVER);
        Component mySQL2 = new Component("HAL9002DB", 7700, 95, ComponentType.DATABASESERVER);
        Component mySQL3 = new Component("HAL9003DB", 12200, 98, ComponentType.DATABASESERVER);
        Component apacheServer1 = new Component("HAL9001W", 2200, 80, ComponentType.WEBSERVER);
        Component apacheServer2 = new Component("HAL9002W", 3200, 90, ComponentType.WEBSERVER);
        Component apacheServer3 = new Component("HAL9003W", 5100, 95, ComponentType.WEBSERVER);


        // Het toevoegen van de componenten aan de catelogus
        CatalogusComponenten.add(pfsense);
        CatalogusComponenten.add(apacheServer1);
        CatalogusComponenten.add(apacheServer2);
        CatalogusComponenten.add(apacheServer3);
        CatalogusComponenten.add(mySQL1);
        CatalogusComponenten.add(mySQL2);
        CatalogusComponenten.add(mySQL3);

//        Database.haalComponentenOp(SimulatieFrame.catalogusPanel.catalogusComponenten);

        for (Component component : CatalogusComponenten) {
            if(component.getType() == ComponentType.PFSENSE){
                fireWallArrayList.add(component);
            } else if (component.getType() == ComponentType.WEBSERVER) {
                webserverArrayList.add(component);
            } else if (component.getType() == ComponentType.DATABASESERVER) {
                databaseserverArrayList.add(component);
            }
        }

        vindOptimaalOntwerp(0, 0, 0, 0, new ArrayList<>(), fireWallArrayList, webserverArrayList, databaseserverArrayList, 0 ,0);

        if (optimaalOntwerp == null) {
            System.out.println("Geen optimaal ontwerp gevonden bij de gegeven beschikbaarheid, of er is een fout opgetreden!");
        }
    }

    public void vindOptimaalOntwerp(int aantalFirewalls, int aantalWebservers, int aantalDatabaseservers, double totalCost, ArrayList<Component> geselecteerdeServers, ArrayList<Component> pfsense, ArrayList<Component> webserver, ArrayList<Component> databaseserver, int webServerIndex, int dbServerIndex) {

        if (totalCost > minimaleKosten){
            return;
        }
        if (aantalFirewalls >= 1 && geselecteerdeServers.size() >= 12) {
            return;
        }

        // Try adding a firewall
        if (aantalFirewalls < 1) {
            for (Component firewall : pfsense) {
                geselecteerdeServers.add(firewall);
                vindOptimaalOntwerp(aantalFirewalls + 1, aantalWebservers, aantalDatabaseservers, totalCost + firewall.getKosten(), geselecteerdeServers, pfsense, webserver, databaseserver, webServerIndex, dbServerIndex);
                geselecteerdeServers.remove(geselecteerdeServers.size() - 1); // Backtrack by removing the last added firewall
            }
        }

        // Try adding a web server
        if (geselecteerdeServers.size() < 12) {
            for (int i = webServerIndex; i < webserver.size(); i++) {
                Component webServer = webserver.get(i);
                geselecteerdeServers.add(webServer);
                vindOptimaalOntwerp(aantalFirewalls, aantalWebservers + 1, aantalDatabaseservers, totalCost + webServer.getKosten(), geselecteerdeServers, pfsense, webserver, databaseserver, i, dbServerIndex);
                geselecteerdeServers.remove(geselecteerdeServers.size() - 1); // Backtrack by removing the last added web server
            }
        }

        // Try adding a database server
        if (geselecteerdeServers.size() < 12) {
            for (int i = dbServerIndex; i < databaseserver.size(); i++) {
                Component dbServer = databaseserver.get(i);
                geselecteerdeServers.add(dbServer);
                vindOptimaalOntwerp(aantalFirewalls, aantalWebservers, aantalDatabaseservers + 1, totalCost + dbServer.getKosten(), geselecteerdeServers, pfsense, webserver, databaseserver, webServerIndex, i);
                geselecteerdeServers.remove(geselecteerdeServers.size() - 1); // Backtrack by removing the last added database server
            }
        }

        // Check for valid solution
        if (aantalFirewalls > 0 && aantalWebservers > 0 && aantalDatabaseservers > 0
                && berekenBeschikbaarheid(geselecteerdeServers) >= totaalBeschikbaarheidsPercentage
                && totalCost <= maximaleKosten) {
            if (totalCost < minimaleKosten) {
                minimaleKosten = totalCost;
                optimaalOntwerp = new ArrayList<>(geselecteerdeServers);
            }
        }
    }

    public double berekenBeschikbaarheid(ArrayList<Component> selectedServers) {
        // Zet variabalen op 0 en 1
        double totaalPercentage = 0;
        double beschikbaarheidPfsense = 1;
        double beschikbaarheidWeb = 1;
        double beschikbaarheidDatabase = 1;

        // Loop door de componenten heen en bereken voor elk ComponentType apart de beschikbaarheid
        for (Component component : selectedServers) {
            if (component.getType() == ComponentType.PFSENSE) {
                beschikbaarheidPfsense *= (1 - (component.getBeschikbaarheid() / 100));
            }
            if (component.getType() == ComponentType.WEBSERVER) {
                beschikbaarheidWeb *= (1 - (component.getBeschikbaarheid() / 100));
            }
            if (component.getType() == ComponentType.DATABASESERVER) {
                beschikbaarheidDatabase *= (1 - (component.getBeschikbaarheid() / 100));
            }
        }
        // Herschrijf de beschikbaarheid om het op te kunnen tellen
        beschikbaarheidPfsense = 1 - beschikbaarheidPfsense;
        beschikbaarheidWeb = 1 - beschikbaarheidWeb;
        beschikbaarheidDatabase = 1 - beschikbaarheidDatabase;

        // Berekenen totaalpercentage
        totaalPercentage = (beschikbaarheidPfsense * beschikbaarheidWeb * beschikbaarheidDatabase);
        return totaalPercentage;
    }

    public double getTotaalKosten() {
        return minimaleKosten;
    }
}