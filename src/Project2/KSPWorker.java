package Project2;

import java.util.ArrayList;

/**
 *
 * @author justin
 */
public class KSPWorker implements Runnable, Shutdown {

    int ants;
    boolean stop = false;
    static ArrayList<Node> items;

    @Override
    public void run() {
        KSPAnt.updateProbabilityMap();
        ants = KSPAnt.numberOfCities();
        stop = false;
        items = (ArrayList<Node>) KSPAnt.getMap().getNodes().clone();
        ArrayList<Node> globalBest = new ArrayList<>();
        int globalBestValue = -1;
        while (!stop) {
            //update PH and probability map
            KSPAnt.update();
            ArrayList<Node> localBest = new ArrayList<>();
            int localBestValue = -1;
            int totalWeight = 0;
            for (int i = 0; i < ants; i++) {
                ArrayList<Node> remainingNodes = (ArrayList<Node>) items.clone();
                ArrayList<Node> visitedNodes = new ArrayList<>();

                Node nextNode = remainingNodes.get(i);
                remainingNodes.remove(i);
                visitedNodes.add(nextNode);
                totalWeight += nextNode.getX();

                for (int j = 0; j < KSPAnt.numberOfCities(); j++) {
                    nextNode = getNextNode(remainingNodes);
                    if (totalWeight + nextNode.getX() > KSPAnt.getMaxWeight()) {
                        continue;
                    }
                    visitedNodes.add(nextNode);
                    remainingNodes.remove(nextNode);
                    totalWeight += nextNode.getX();

                }
                int setValue = KSPAnt.getMap().getValue(visitedNodes);

                if (setValue > localBestValue) {
                    localBest = (ArrayList<Node>) visitedNodes.clone();
                    localBestValue = setValue;
                }
                if (setValue > globalBestValue) {
                    globalBest = (ArrayList<Node>) visitedNodes.clone();
                    globalBestValue = setValue;
                }
            }

            for (int k = 0; k < localBest.size(); k++) {
                KSPAnt.incrementPH(items.indexOf(localBest.get(k)));
            }
        }
        KSPAnt.getMap().setBest(globalBest);
        KSPAnt.getMap().setBestValue(globalBestValue);
    }

    public static Node getNextNode(ArrayList<Node> remainingCities) {
        int next = 0;
        double selection = Math.random();
        double localSum = 0;
        for (Node c : remainingCities) {
            localSum += KSPAnt.getProbability(items.indexOf(c));
        }

        while (true) {
            double reduction = 1;
            for (Node c : remainingCities) {
                reduction = KSPAnt.getProbability(items.indexOf(c)) / localSum;
            }
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
