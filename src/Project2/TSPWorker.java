package Project2;

import java.util.ArrayList;

/**
 *
 * @author justin
 */
public class TSPWorker implements Runnable, Shutdown {

    int ants;
    boolean stop = false;
    static ArrayList<Node> cities;

    public void run() {
        TSPAnt.updateProbabilityMap();
        ants = TSPAnt.numberOfCities();
        stop = false;
        cities = (ArrayList<Node>) TSPAnt.getMap().getNodes().clone();
        while (!stop) {
            ArrayList<Node> bestPath = new ArrayList<>();
            long bestPathLength = -1;
            for (int i = 0; i < ants; i++) {
                ArrayList<Node> remainingCities = (ArrayList<Node>) cities.clone();
                ArrayList<Node> visitedCities = new ArrayList<>();
                Node nextCity = cities.get(0);

                visitedCities.add(nextCity);
                remainingCities.remove(nextCity);

                for (int j = 1; j < TSPAnt.numberOfCities(); j++) {
                    nextCity = getNextCity(visitedCities, remainingCities);
                    visitedCities.add(nextCity);
                    remainingCities.remove(nextCity);
                }
                long pathLength = TSPAnt.getMap().getPathLength(visitedCities);
                if (bestPathLength < 0 || pathLength < bestPathLength) {
                    bestPath = (ArrayList<Node>) visitedCities.clone();
                    bestPathLength = pathLength;
                }
            }
            for (int i = 1; i < bestPath.size(); i++) {
                TSPAnt.incrementPH(cities.indexOf(bestPath.get(i-1)), cities.indexOf(bestPath.get(i)));
            }
            TSPAnt.incrementPH(cities.indexOf(bestPath.get(bestPath.size() - 1)), cities.indexOf(bestPath.get(0)));

            //update PH and probability map
            TSPAnt.update();
        }
    }

    public static Node getNextCity(ArrayList<Node> visitedCities, ArrayList<Node> remainingCities) {
        Node currentCity = visitedCities.get(visitedCities.size() - 1);
        int next = 0;
        double selection = Math.random();
        double localSum = 0;
        for (Node c : remainingCities) {
            localSum += TSPAnt.getProbability(cities.indexOf(currentCity), cities.indexOf(c));
        }

        while (true) {
            double reduction = TSPAnt.getProbability(cities.indexOf(currentCity), cities.indexOf(remainingCities.get(next))) / localSum;
            selection -= reduction;

            if (selection <= 0) {
                break;
            }
            if (next < remainingCities.size() - 1) {
                next++;
            }
        }
        return remainingCities.get(next);
    }

    @Override
    public void stop() {
        stop = true;
    }
}
