package Project2;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author justin
 */
public class KSPAnt implements Runnable {

    static Map map;
    private static double[] weightInverse;
    private static double[] PH;
    private static double[] ProbMap;
    private static double pherimone;
    private static double evaporation;
    private static ArrayList<KSPWorker> workers = new ArrayList<>();
    private static ArrayList<Thread> threads = new ArrayList<>();
    private static long runTime;
    private static int cores;
    private static int maxWeight;

    public KSPAnt(Map map, int RunTimeSeconds, int workers, double pherimone, double evaporation, int maxWeight) {
        this.map = map;
        this.cores = workers;
        this.pherimone = pherimone;
        this.evaporation = evaporation;
        this.runTime = RunTimeSeconds;
        this.maxWeight = maxWeight;
    }

    @Override
    public void run() {
        populateConnectionArrays();
        for (int i = 0; i < cores; i++) {
            KSPWorker w = new KSPWorker();
            workers.add(w);
            Thread s = new Thread(w);
            s.start();
            threads.add(s);
        }
        long startTime = System.currentTimeMillis() / 1000;
        do {
            try {
                ArrayList<Node> finalSet = getFinalSet();
                System.out.println("Weight: " + map.getWeight(finalSet) + "Value: " + map.getValue(finalSet));
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
        ArrayList<Node> finalSet = getFinalSet();
        map.setBest(finalSet);
        map.setBestValue(map.getValue(finalSet));
        map.writeOut();
        //map.print(finalSet);
    }

    public static void populateConnectionArrays() {
        weightInverse = new double[map.size()];
        PH = new double[map.size()];
        for (int i = 0; i < map.size(); i++) {
            weightInverse[i] = 1.0 / (double) map.getNode(i).getX();
            PH[i] = pherimone;
        }
    }

    public static Map getMap() {
        return map;
    }

    public static int getMaxWeight() {
        return maxWeight;
    }

    public static ArrayList<Node> getFinalSet() {
        ArrayList<Node> fullSet = map.getNodes();
        ArrayList<Node> set = new ArrayList<>();
        int totalWeight = 0;
        for (int i = 0; i < map.size(); i++) {
            int next = -1;
            double ph = -1;
            for (int j = 0; j < fullSet.size(); j++) {
                boolean visited = false;
                for (Node p : set) {
                    if (map.getNumber(p) == map.getNumber(fullSet.get(j))) {
                        visited = true;
                    }
                }
                if (!visited && getPH(map.getNumber(map.getNode(j))) > ph) {
                    next = j;
                    ph = getPH(map.getNumber(map.getNode(j)));
                }
            }
            if (next >= 0) {
                if ((fullSet.get(next).getX() + totalWeight) > maxWeight) {
                    fullSet.remove(next);
                } else {
                    set.add(fullSet.get(next));
                    totalWeight += fullSet.get(next).getX();
                    fullSet.remove(next);
                }
            }
        }
        if (map.getBestValue() > map.getValue(set)) {
            return map.getBest();
        }
        return set;
    }

    public static void update() {
        updatePH();
        updateProbabilityMap();
    }

    private static void updatePH() {
        for (int i = 0; i < map.size(); i++) {
            PH[i] *= evaporation;
        }
    }

    public static int numberOfCities() {
        return map.size();
    }

    public static double getPH(int item) {
        return PH[item];
    }

    public static double getWeightInverse(int item) {
        return weightInverse[item];
    }

    public static double getProbability(int item) {
        return ProbMap[item];
    }

    public static void updateProbabilityMap() {
        ProbMap = new double[map.size()];

        for (int i = 0; i < map.size(); i++) {

            double vertexUpperProb = getWeightInverse(i) * getPH(i);
            ProbMap[i] = vertexUpperProb;
        }
    }

    public static void incrementPH(int city1) {
        PH[city1]++;
        ProbMap[city1] = (getWeightInverse(city1) * getPH(city1));
    }
}
