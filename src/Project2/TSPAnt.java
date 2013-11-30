package Project2;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author justin
 */
public class TSPAnt implements Runnable{

    static Map map;
    private static double[][] visibility;
    private static double[][] PH;
    private static double[][] ProbMap;
    private static double pherimone;
    private static double evaporation;
    private static ArrayList<TSPWorker> workers = new ArrayList<>();
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static long runTime;
    private static int cores;
    
    public TSPAnt(Map map, int RunTimeSeconds, int workers, double pherimone, double evaporation){
        this.map = map;
        this.cores = workers;
        this.pherimone = pherimone;
        this.evaporation = evaporation;
        this.runTime = RunTimeSeconds;
    }

    @Override
    public void run() {
        populateConnectionArrays();
        for (int i = 0; i < cores; i++) {
            TSPWorker w = new TSPWorker();
            workers.add(w);
            Thread s = new Thread(w);
            s.start();
            threads.add(s);
        }
        long startTime = System.currentTimeMillis() / 1000;
        do {
            try {
                ArrayList<Node> finalPath = getFinalPath();
                System.out.println("Path length: " + map.getPathLength(finalPath));
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(TSPAnt.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (((System.currentTimeMillis() / 1000) - startTime) < runTime);
        for (int i = 0; i < workers.size(); i++) {
            workers.get(i).stop();
        }
        //kill the threads
        for (int i = 0; i < threads.size(); i++) {
            if (threads.get(i).isAlive()) {
                try {
                    threads.get(i).join(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TSPAnt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        ArrayList<Node> finalPath = getFinalPath();
        map.setBest(finalPath);
        map.setBestValue(map.getPathLength(finalPath));
        map.writeOut();
        //map.print(finalPath);
    }

    public static void populateConnectionArrays() {
        visibility = new double[map.size()][map.size()];
        PH = new double[map.size()][map.size()];
        for (int i = 0; i < map.size(); i++) {
            for (int j = i + 1; j < map.size(); j++) {
                visibility[i][j] = 1.0 / map.getNode(i).getConnection(map.getNode(j));
            }
        }
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                PH[i][j] = pherimone;
            }
        }
    }

    public static Map getMap() {
        return map;
    }

    public static long squareDistance(int city1, int city2, Map cities) {
        int x1 = cities.getNode(city1).getX(), x2 = cities.getNode(city2).getX();
        int y1 = cities.getNode(city1).getY(), y2 = cities.getNode(city2).getY();
        long distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
        return distance;
    }

    public static ArrayList<Node> getFinalPath() {
        ArrayList<Node> path = new ArrayList<>();
        path.add(map.getNode(0));
        for (int i = 1; i < map.size(); i++) {
            int next = 0;
            double ph = 0.0;
            for (int j = 0; j < map.size(); j++) {
                boolean visited = false;
                for (Node p : path) {
                    if (map.getNumber(p) == j) {
                        visited = true;
                    }
                }
                if (!visited && getPH(map.getNumber(path.get(path.size() - 1)), j) > ph) {
                    next = j;
                    ph = getPH(map.getNumber(path.get(path.size() - 1)), j);
                }
            }
            path.add(map.getNode(next));
        }
        return path;
    }

    public static void update() {
        updatePH();
        updateProbabilityMap();
    }

    private static void updatePH() {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.size(); j++) {
                PH[i][j] *= evaporation;
            }
        }
    }

    public static int numberOfCities() {
        return map.size();
    }

    public static double getPH(int city1, int city2) {
        if (city1 > city2) {
            return PH[city2][city1];
        }
        return PH[city1][city2];
    }

    public static double getVisibility(int city1, int city2) {
        if (city1 == city2) {
            return 1;
        } else if (city1 > city2) {
            return visibility[city2][city1];
        }
        return visibility[city1][city2];
    }

    public static double getProbability(int city1, int city2) {
        return ProbMap[city1][city2];
    }

    public static void updateProbabilityMap() {
        ProbMap = new double[map.size()][map.size()];

        for (int i = 0; i < map.size(); i++) {

            for (int j = 0; j < map.size(); j++) {
                if (i == j) {
                    ProbMap[i][j] = 0;  //probability of traveling to the city we are already at.
                } else {
                    double vertexUpperProb = getVisibility(i, j) * getPH(i, j);
                    ProbMap[i][j] = vertexUpperProb;
                }
            }
        }
    }

    public static void incrementPH(int city1, int city2) {
        int c1 = city2;
        int c2 = city1;
        if (city1 > city2) {
            c1 = city2;
            c2 = city1;
        }
        PH[c1][c2]++;
        PH[c2][c1]++;
        ProbMap[c1][c2] = (getVisibility(city1, city2) * getPH(city1, city2));
    }
}
