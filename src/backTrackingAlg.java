import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BackTrackingAlg {

    public int[][] getAantal() {
        List<Component> catComponenten = SimulatieFrame.catalogusPanel.catalogusComponenten;

        catComponenten.add(SimulatieFrame.catalogusPanel.catalogusComponenten.get(0));

        int[][] aantallen = new int[catComponenten.size()][1];

        for (int i = 0; i < catComponenten.size(); i++) {
            if (catComponenten.contains(catComponenten.get(i))) {
                int positie = catComponenten.indexOf(catComponenten.get(i));
                aantallen[positie][0]++;
            }
        }

        return aantallen;
    }
}

//        private List<Component> componentDB;
//        private List<Component> componentWEB;
//        private List<Component> componentPFS;
//        public double beschikbaarheidEis = OptimalisatieDialog.getGewildeBeschikbaarheid();
//        public double beschikbaarheidComp;
//        public double beschikbaarheidDB;
//        public double beschikbaarheidWEB;
//        public Component[] optimaalDB;
//        public Component[] optimaalWEB;

//        private int configuratieLoop;
//        private boolean forceStop = false;

//        public BackTrackingAlg() {
////                for (Component component : SimulatieFrame.catalogusPanel.catalogusComponenten) {
////                        if (component.getType().equals(ComponentType.DATABASESERVER)) {
////                                componentDB.add(component);
////                        } else if (component.getType().equals(ComponentType.WEBSERVER)) {
////                                componentWEB.add(component);
////                        } else {
////                                componentPFS.add(component);
////                        }
////                        System.out.println(component);
//        }
//
//
//        public void berekenBeschikbaarheidPER() {
//                double beschikbaarheidPER = beschikbaarheidEis / 100;
//                for (Component component : componenten) {
//                        beschikbaarheidPER = beschikbaarheidPER / (component.getBeschikbaarheid() / 100);
//                }
//
//                if (optimaalDB != null) {
//                        beschikbaarheidPER = beschikbaarheidPER / (getBeschikbaarheid(optimaalDB) / 100);
//                } else if (optimaalWEB != null) {
//                        beschikbaarheidPER = beschikbaarheidPER / (getBeschikbaarheid(optimaalWEB) / 100);
//                } else {
//                        beschikbaarheidPER = Math.sqrt(beschikbaarheidPER) / 100;
//                }
//                beschikbaarheidComp = beschikbaarheidPER;
//        }
//
//        public double berekenBeschikbaarheidDB() {
//                double beschikbaarheidDatabase = 1;
//                for (Component component : componenten) {
//                        if (component.getType() == ComponentType.DATABASESERVER) {
//                                beschikbaarheidDatabase = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
//                        }
//                }
//                return beschikbaarheidDatabase;
//        }
//
//        public double berekenBeschikbaarheidWEB() {
//                double beschikbaarheidWeb = 1;
//                for (Component component : componenten) {
//                        if (component.getType() == ComponentType.DATABASESERVER) {
//                                beschikbaarheidWeb = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
//                        }
//                }
//                return beschikbaarheidWeb;
//        }
//
//        public static double getBeschikbaarheid(Component[] components) {
//                if (components == null) {
//                        return 0;
//                }
//
//                double beschikbaarheid = 1;
//
//                for (Component component : components) {
//                        beschikbaarheid *= (1 - (component.getBeschikbaarheid() / 100));
//                }
//
//                return (1 - beschikbaarheid) * 100;
//        }
//
//        public static double getKosten(Component[] components) {
//                if (components == null) {
//                        return 0;
//                }
//
//                double kosten = 0;
//
//                for (Component component : components) {
//                        kosten += (component.getKosten());
//                }
//
//                return kosten;
//        }
//
//
////        public double berekenKosten(Component component) {
////                double kostenPfsense = 1;
////                double kostenDatabase = 1;
////                double kostenWeb = 1;
////                if (component.getType() == ComponentType.PFSENSE) {
////                        kostenPfsense = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
////                }
////                if (component.getType() == ComponentType.DATABASESERVER) {
////                        kostenDatabase = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
////                }
////                if (component.getType() == ComponentType.WEBSERVER) {
////                        kostenWeb = 1 - Math.pow(1 - (component.getBeschikbaarheid() / 100), componenten.size());
////                }
////
////                // Berekenen totale kosten
////                double totaleKosten = kostenPfsense + kostenDatabase + kostenWeb;
////                System.out.println(totaleKosten);
////                return totaleKosten;
////        }
//        public double berekenTotaleBeschikbaarheid(){
//                if (optimaalDB==null || optimaalWEB==null || forceStop) {
//                        return -1;
//                }
//                double availability=1;
//
//                for (Component component : componentPFS) {
//                        availability *= (component.getBeschikbaarheid()*0.01);
//                }
//
//                return (availability*(getBeschikbaarheid(optimaalWEB)*0.01)
//                        *(getBeschikbaarheid(optimaalDB)*0.01))*100;
//        }
//        public double berekenTotaleKosten(){
//                if (optimaalDB==null || optimaalWEB==null || forceStop) {
//                        return -1;
//                }
//                int kosten=0;
//
//                for (Component component : componentPFS) {
//                        kosten += component.getKosten();
//                }
//
//                return kosten +getKosten(optimaalDB) +getKosten(optimaalWEB);
//        }
//        private void solve(List<Component> currentComponentList, Component[] componentList) {
//                try {
//                        configuratieLoop++;
//
//                        if (currentComponentList == null || componentList == null) {
//                                return;
//                        }
//                        if (validConfiguration(currentComponentList.toArray(Component[]::new))) {
//                                return;
//                        }
//
//                        for (Component component : componentList) {
//                                if (forceStop) {
//                                        return;
//                                }
//
//                                List<Component> _list = new ArrayList<>(currentComponentList);
//                                _list.add(component);
//
//                                solve(_list, componentList);
//                        }
//                } catch (StackOverflowError | OutOfMemoryError e) {
//                        stop();
//
//                }
//        }
//
//        private boolean validConfiguration(Component[] components) {
//                if (components.length <= 0) {
//                        return false;
//                } else {
//                        if (getBeschikbaarheid(components) >= configuratieLoop) {
//                                if (components[0].getType().equals(ComponentType.DATABASESERVER)) {
//                                        if (getKosten(components) == getKosten(optimaalDB)) {
//                                                if (components.length < optimaalDB.length) {
//                                                        optimaalDB = components;
//                                                }
//                                        } else if (getKosten(components) < getKosten(optimaalDB)) {
//                                                optimaalDB = components;
//                                        }
//                                } else if (components[0].getType().equals(ComponentType.WEBSERVER)) {
//                                        if (getKosten(components) == getKosten(optimaalWEB)) {
//                                                if (components.length < optimaalWEB.length) {
//                                                        optimaalWEB = components;
//                                                }
//                                        } else if (getKosten(components) < getKosten(optimaalWEB)) {
//                                                optimaalWEB = components;
//                                        }
//                                }
//                                return true;
//                        } else {
//                                return false;
//                        }
//                }
//        }
//
//        public boolean stop() {
//                if (!forceStop) {
//                        forceStop = true;
//                        return true;
//                }
//                return false;
//        }
//        public boolean isForceStop() {
//                return forceStop;
//        }
//        public boolean start() {
//                if (optimaalDB==null && !forceStop) {
//                        solve(new ArrayList<>(), componentDB.toArray(Component[]::new));
//                }
//                if (optimaalWEB==null && !forceStop) {
//                        solve(new ArrayList<>(), componentWEB.toArray(Component[]::new));
//                }
//
//                if (forceStop) {
//                        return false;
//                } else {
//                        return ((optimaalDB != null) && (optimaalWEB != null));
//                }
//        }
//        public List<Component> getComponenten() {
//                List<Component> componentenLijst = new ArrayList<>();
//
//                componentenLijst.addAll(Arrays.asList(optimaalDB));
//                componentenLijst.addAll(Arrays.asList(optimaalWEB));
//                componentenLijst.addAll(componentPFS);
//
//                return componentenLijst;
//        }
//        public void printSolution() {
//                if (forceStop || optimaalDB==null || optimaalWEB==null) {
//                        return;
//                }
//                System.out.println(getSolution());
//        }
//        public String getSolution() {
//                if (forceStop || optimaalWEB==null || optimaalDB==null) {
//                        return null;
//                }
//
//                StringBuilder solution = new StringBuilder();
//
//                StringBuilder databaseServers = new StringBuilder();
//                for (Component component : optimaalDB) {
//                        databaseServers.append(component.getNaam());
//                        databaseServers.append(", ");
//                }
//                if (databaseServers.length()>0) {
//                        solution.append("Database servers(");
//                        solution.append(optimaalDB.length);
//                        solution.append("): [");
//
//                        solution.append(databaseServers.substring(0, databaseServers.length() - 2));
//                        solution.append("]\n");
//                } else {
//                        solution.append("Database servers(0): []\n");
//                }
//
//                StringBuilder webServers = new StringBuilder();
//                for (Component component : optimaalWEB) {
//                        webServers.append(component.getNaam());
//                        webServers.append(", ");
//                }
//                if (webServers.length()>0) {
//                        solution.append("Web servers(");
//                        solution.append(optimaalWEB.length);
//                        solution.append("): [");
//
//                        solution.append(webServers.substring(0, webServers.length() - 2));
//                        solution.append("]\n");
//                } else {
//                        solution.append("Web servers(0): []\n");
//                }
//
//                StringBuilder otherComponents = new StringBuilder();
//                for (Component component : this.componentPFS) {
//                        otherComponents.append(component.getNaam());
//                        otherComponents.append(", ");
//                }
//                if (otherComponents.length()>0) {
//                        solution.append("Andere components(");
//                        solution.append(this.componentPFS.size());
//                        solution.append("): [");
//
//                        solution.append(otherComponents.substring(0, otherComponents.length() - 2));
//                        solution.append("]\n");
//                } else {
//                        solution.append("Andere components(0): []\n");
//                }
//
//                solution.append("\n");
//
//                solution.append("Beschikbaarheid: ");
//                solution.append(berekenTotaleBeschikbaarheid());
//                solution.append("%\n");
//
//                solution.append("Kosten: €");
//                solution.append(berekenTotaleKosten());
//                solution.append(",-\n");
//
//                solution.append("Configuraties getest: ");
//                solution.append(configuratieLoop);
//
//                return solution.toString();
//        }}