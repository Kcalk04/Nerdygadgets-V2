import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;



class ServerInfrastructure {

    private ArrayList<Component> huidigeComponenten;

    private static int aantalKeer;
    private static double totaalPercentage;
    private double maximumCost;
    private double minCost = maximumCost;
    private ArrayList<Component> optimalInfrastructure;

    ArrayList<Component> firewalls;
    ArrayList<Component> webServers;
    ArrayList<Component> dbServers;

    public ServerInfrastructure(double beschikbaarheid, double kosten) {
        this.totaalPercentage = beschikbaarheid / 100;
        this.maximumCost = kosten;
        this.minCost = maximumCost;
        this.optimalInfrastructure = null;

        huidigeComponenten = SimulatieFrame.catalogusPanel.catalogusComponenten;

        firewalls = new ArrayList<>();
        webServers = new ArrayList<>();
        dbServers = new ArrayList<>();

        for (Component component : huidigeComponenten) {
            if (component.getType() == ComponentType.PFSENSE) {
                firewalls.add(component);
            } else if (component.getType() == ComponentType.WEBSERVER) {
                webServers.add(component);
            } else if (component.getType() == ComponentType.DATABASESERVER) {
                dbServers.add(component);
            }
        }

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

    public ArrayList<Component> findCheapestInfrastructure(int firewallsUsed, int webServersUsed, int dbServersUsed, double totalCost) {
        return findCheapestInfrastructure(firewallsUsed, webServersUsed, dbServersUsed, totalCost, new ArrayList<>(), firewalls, webServers, dbServers, 0, 0);
    }

    private ArrayList<Component> findCheapestInfrastructure(int firewallsUsed, int webServersUsed, int dbServersUsed, double totalCost, ArrayList<Component> selectedServers,
                                                            ArrayList<Component> firewalls, ArrayList<Component> webServers, ArrayList<Component> dbServers, int webServerIndex, int dbServerIndex) {
        aantalKeer++;

        if (selectedServers.size() > 0) {
            double beschikbaarheid = berekenBeschikbaarheid(selectedServers);

            int beschikbaarheid2 = (int) (beschikbaarheid * 1000);
            int totaalPercentage2 = (int) (totaalPercentage * 1000);

            if (totalCost > minCost || beschikbaarheid2 > totaalPercentage2) {
                System.out.println(selectedServers.size());
                ArrayList<Component> nieuweServers = new ArrayList<>();
                for (Component component : selectedServers) {
                    nieuweServers.add(component);
                }
                return nieuweServers;
            }
        }

        // Try adding a firewall
//        for (Component component : firewalls) {
//            selectedServers.add(component);
//            findCheapestInfrastructure(firewallsUsed + 1, webServersUsed, dbServersUsed,
//                    totalCost + component.getKosten(), selectedServers, firewalls, webServers, dbServers,
//                    webServerIndex, dbServerIndex);
//            selectedServers.remove(selectedServers.size() - 1); // Backtrack by removing the last added firewall
//        }

        // Try adding a web server
        for (int i = webServerIndex; i < webServers.size(); i++) {
            Component webServer = webServers.get(i);
            selectedServers.add(webServer);
            findCheapestInfrastructure(firewallsUsed, webServersUsed + 1, dbServersUsed,
                    totalCost + webServer.getKosten(), selectedServers, firewalls, webServers, dbServers,
                    i + 1, dbServerIndex); // Increment the index here
            selectedServers.remove(selectedServers.size() - 1); // Backtrack by removing the last added web server
            return selectedServers;
        }

        // Try adding a database server
        for (int i = dbServerIndex; i < dbServers.size(); i++) {
            Component dbServer = dbServers.get(i);
            selectedServers.add(dbServer);
            findCheapestInfrastructure(firewallsUsed, webServersUsed, dbServersUsed + 1,
                    totalCost + dbServer.getKosten(), selectedServers, firewalls, webServers, dbServers,
                    webServerIndex, i + 1); // Increment the index here
            selectedServers.remove(selectedServers.size() - 1); // Backtrack by removing the last added database server
            return selectedServers;
        }


        // Check for valid solution
        if (firewallsUsed > 0 && webServersUsed > 0 && dbServersUsed > 0
                && berekenBeschikbaarheid(selectedServers) >= totaalPercentage
                && totalCost <= maximumCost) {
            if (totalCost < minCost) {
                minCost = totalCost;
                optimalInfrastructure = new ArrayList<>(selectedServers);
            }
        }

        return null;
    }

    public static double berekenBeschikbaarheid(ArrayList<Component> selectedServers) {

        double[] lijst = new double[selectedServers.size()];

        for(int i = 0; i < lijst.length; i++) {
            double beschikbaarheid = (1 - (selectedServers.get(i).getBeschikbaarheid() / 100));
            lijst[i] = beschikbaarheid;
        }

        double beschikbaarheid = 1;

        for (double ls : lijst) {
            beschikbaarheid *= ls;
        }

        beschikbaarheid = 1 - beschikbaarheid;

        return beschikbaarheid;
    }
}