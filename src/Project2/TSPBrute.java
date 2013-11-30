package Project2;

import java.util.ArrayList;

/**
 *
 * @author justin
 */
public class TSPBrute implements Runnable {

    public final long timeout;
    private int deviationx;
    private int deviationy;
    private int deviationz;
    private Map map;
    private ArrayList<Node> shortestPath;
    private long shortestPathLength;
    private ArrayList<Node> currentPath;
    private long pathLength;
    private boolean stop = false;
    private long startTime, endTime;

    public TSPBrute(int deviationx, int deviationy, int deviationz, int timeoutInMinutes) {
        this.deviationx = deviationx;
        this.deviationy = deviationy;
        this.deviationz = deviationz;
        this.timeout = timeoutInMinutes * 60 * 1000;
    }

    @Override
    public void run() {
        int i = 1;
        while (stop == false) {
            i++;
            map = Map.generateMap(Map.tsp2d, "tsp" + i, i, deviationx, deviationy, deviationz);
            startTime = System.currentTimeMillis();
            findShortestPath();
            endTime = System.currentTimeMillis();
            map.setBest(shortestPath);
            map.setBestValue(map.getPathLength(shortestPath));
            map.setRuntimeMillis(endTime-startTime);
            map.writeOut();
            this.printPath(map, shortestPath, shortestPathLength);
        }
    }

    private ArrayList<Node> findShortestPath() {
        shortestPath = map.getNodes();
        currentPath = map.getNodes();
        shortestPathLength = map.getPathLength(shortestPath);
        if(map.size() < 4){
            return shortestPath;
        }
        int head, tail;
        do {
            if(System.currentTimeMillis() - startTime > timeout){
                stop = true;
                return null;
            }
            head = getHead(currentPath);
            tail = getTail(head, currentPath);
            swap(head, tail, currentPath);
            reverse(head + 1, currentPath);
            pathLength = map.getPathLength(currentPath);
            if (pathLength < shortestPathLength) {
                shortestPathLength = pathLength;
                shortestPath = (ArrayList<Node>) currentPath.clone();
            }
        }while (head > 0);
        return shortestPath;
    }
    
    public int getTail(int head, ArrayList<Node> path){
        int tail = 0;
        for (int i = head + 1; i < path.size(); i++) {
            if (map.getNumber(path.get(head)) < map.getNumber(path.get(i))) {
                tail = i;
            }
        }
        return tail;
        
    } 

    public int getHead(ArrayList<Node> path) {
        int head = 0;
        for (int i = 1; i < path.size() - 1; i++) {
            if (map.getNumber(path.get(i)) < map.getNumber(path.get(i+1))) {
                head = i;
            }
        }
        return head;
    }

    private void reverse(int startPosition, ArrayList<Node> path) {
        if(startPosition > path.size() - 2){
            return;
        }
        int endPostion = path.size() - 1;
        while (startPosition < endPostion) {
            swap(startPosition, endPostion, path);
            startPosition++;
            endPostion--;
        }
    }
    
    
    

    private static void swap(int i, int j, ArrayList<Node> path) {
        Node nodei = path.get(i);
        Node nodej = path.get(j);
        path.remove(i);
        path.add(i, nodej);
        path.remove(j);
        path.add(j, nodei);
    }

    private static void swap(Node nodei, Node nodej, ArrayList<Node> path) {
        int indexi = path.indexOf(nodei);
        int indexj = path.indexOf(nodej);
        path.remove(indexj);
        path.add(indexj, nodei);
        path.remove(indexi);
        path.add(indexi, nodej);
    }

    public void printPath(Map cities, ArrayList<Node> path, long pathLength) {
        for (int i = 0; i < path.size(); i++) {
            System.out.print("City" + map.getNumber(path.get(i)) + ": ");
            path.get(i).print();
        }
        System.out.println("Path Length Square Sum: " + pathLength);
    }
}
