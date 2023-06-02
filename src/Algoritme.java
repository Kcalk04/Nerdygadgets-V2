import java.util.ArrayList;


class Algoritme {
    public static void main(String[] args) {

        new Algoritme(99.99, 100000);
        System.out.println("aantalKeer: " +aantalKeer);
    }


    private ArrayList<Component> CatalogusComponenten = new ArrayList<>();

    private static int aantalKeer;
    private static final int MAX_FIREWALLS = 2;
    private static final int MAX_WEB_SERVERS = 5;
    private static final int MAX_DB_SERVERS = 5;


    private static double totalAvailabilityPercentage;
    private static double maximumCost;

    private static double minCost = maximumCost;
    private static ArrayList<Component> optimalInfrastructure;

    public static ArrayList<Component> getOptimalInfrastructure() {
        return optimalInfrastructure;
    }


    public Algoritme(double beschikbaarheid, double kosten){
        this.totalAvailabilityPercentage = beschikbaarheid/100;
        this.maximumCost = kosten;
        minCost = maximumCost;

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


        ArrayList<Component> firewalls = new ArrayList<>();
        ArrayList<Component> webServers = new ArrayList<>();
        ArrayList<Component> dbServers = new ArrayList<>();

        for (Component component : CatalogusComponenten) {
            if(component.getType() == ComponentType.PFSENSE){
                firewalls.add(component);
            } else if (component.getType() == ComponentType.WEBSERVER) {
                webServers.add(component);
            } else if (component.getType() == ComponentType.DATABASESERVER) {
                dbServers.add(component);
            }

        }
        findCheapestInfrastructure(0, 0, 0, 0, new ArrayList<>(), firewalls, webServers, dbServers, 0 ,0);

        if (optimalInfrastructure == null) {
            System.out.println("No valid infrastructure found!");
        } else {
            System.out.println("Cheapest cost: " + minCost);
            System.out.println("Optimal infrastructure:");
            for (Component component : optimalInfrastructure) {
                System.out.println(component.getNaam() + " Server cost: " + component.getKosten() + ", availability: " + component.getBeschikbaarheid());
            }
        }

    }
    private static void findCheapestInfrastructure(int firewallsUsed, int webServersUsed, int dbServersUsed,
                                                   double totalCost, ArrayList<Component> selectedServers,
                                                   ArrayList<Component> firewalls, ArrayList<Component> webServers, ArrayList<Component> dbServers,
                                                   int webServerIndex, int dbServerIndex) {
        aantalKeer++;

        if (firewallsUsed >= MAX_FIREWALLS && webServersUsed >= MAX_WEB_SERVERS && dbServersUsed >= MAX_DB_SERVERS) {
            return; // Terminate the loop when every combination is found
        }
        if(totalCost > minCost){
            return;
        }
        // Try adding a firewall
        if (firewallsUsed < MAX_FIREWALLS) {
            for (Component firewall : firewalls) {
                selectedServers.add(firewall);
                findCheapestInfrastructure(firewallsUsed + 1, webServersUsed, dbServersUsed,
                        totalCost + firewall.getKosten(), selectedServers, firewalls, webServers, dbServers,
                        webServerIndex, dbServerIndex);
                selectedServers.remove(selectedServers.size() - 1); // Backtrack by removing the last added firewall
            }
        }

        // Try adding a web server
        if (webServersUsed < MAX_WEB_SERVERS) {
            for (int i = webServerIndex; i < webServers.size(); i++) {
                Component webServer = webServers.get(i);
                selectedServers.add(webServer);
                findCheapestInfrastructure(firewallsUsed, webServersUsed + 1, dbServersUsed,
                        totalCost + webServer.getKosten(), selectedServers, firewalls, webServers, dbServers,
                        i, dbServerIndex);
                selectedServers.remove(selectedServers.size() - 1); // Backtrack by removing the last added web server
            }
        }

        // Try adding a database server
        if (dbServersUsed < MAX_DB_SERVERS) {
            for (int i = dbServerIndex; i < dbServers.size(); i++) {
                Component dbServer = dbServers.get(i);
                selectedServers.add(dbServer);
                findCheapestInfrastructure(firewallsUsed, webServersUsed, dbServersUsed + 1,
                        totalCost + dbServer.getKosten(), selectedServers, firewalls, webServers, dbServers,
                        webServerIndex, i);
                selectedServers.remove(selectedServers.size() - 1); // Backtrack by removing the last added database server
            }
        }

        // Check for valid solution
        if (firewallsUsed > 0 && webServersUsed > 0 && dbServersUsed > 0
                && calculateTotalAvailability(selectedServers) >= totalAvailabilityPercentage
                && totalCost <= maximumCost) {
            if (totalCost < minCost) {
                minCost = totalCost;
                optimalInfrastructure = new ArrayList<>(selectedServers);
            }
        }
    }

    public static double calculateTotalAvailability(ArrayList<Component> selectedServers){
        double fwp = 1;
        double wsp = 1;
        double dsp = 1;



        for (Component component : selectedServers) {
            double percent = component.getBeschikbaarheid()/100;
            if(component.getType() == ComponentType.WEBSERVER){
                if(wsp == 1){
                    wsp = (1-percent);
                }else{
                    wsp *=(1-percent);
                }
            }
            if(component.getType() == ComponentType.DATABASESERVER){
                if(dsp == 1){
                    dsp = (1-percent);
                }else{
                    dsp *=(1-percent);
                }
            }
            if(component.getType() == ComponentType.PFSENSE){
                if(fwp == 1){
                    fwp = (1-percent);
                }else{
                    fwp *=(1-percent);
                }
            }
        }
        fwp = 1-fwp;
        wsp = 1-wsp;
        dsp = 1-dsp;
        if(fwp != 1 || dsp != 1 || wsp != 1){
            return (fwp * dsp * wsp);
        }else{
            return 0;
        }
    }

    public static double getMinCost() {
        return minCost;
    }

    public static double getTotalAvailabilityPercentage() {
        return totalAvailabilityPercentage;
    }

    public ArrayList<Component> getCatalogusComponenten() {
        return CatalogusComponenten;
    }
}