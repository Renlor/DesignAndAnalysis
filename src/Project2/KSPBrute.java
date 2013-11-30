package Project2;

import java.util.ArrayList;

/**
 *
 * @author justin
 */
public class KSPBrute implements Runnable {
    private int timeout;
    private long startTime, endTime;
    private boolean stop = false;
    private int valueDeviation;
    private int weightDeviation;
    private ArrayList<Node> bestSet;

    public KSPBrute(int timeoutInMinutes, int valueDeviation, int weightDeviation){
        timeout = timeoutInMinutes * 60 * 1000;
        this.valueDeviation = valueDeviation;
        this.weightDeviation = weightDeviation;
    }

    @Override
    public void run() {
        int i = 1;
        while(!stop){
            i++;
            Map map = Map.generateMap(Map.ksp, "nsp" + i, i, weightDeviation, valueDeviation,  0);
            int maxWeight = (int) (Math.random() * (weightDeviation ) + weightDeviation);
            map.print(map.getNodes());
            System.out.println("Max weight: " + maxWeight);
            startTime = System.currentTimeMillis();
            bestSet = findBestPackageSet(map, maxWeight);
            endTime = System.currentTimeMillis();
            map.setBest(bestSet);
            map.setBestValue(map.getValue(bestSet));
            map.setRuntimeMillis(endTime-startTime);
            map.writeOut();
            map.print(bestSet);
        }
    }

    public ArrayList<Node> findBestPackageSet(Map map, int maxWeight) {
        long binaryCounter = 0b0;
        long bitMask = getBitMask(map.size());
        ArrayList<Node> best = new ArrayList<>();
        while ((binaryCounter & bitMask) != bitMask) {
            
            if(System.currentTimeMillis() - startTime > timeout){
                stop = true;
                return null;
            }
            
            ArrayList<Node> current = new ArrayList<>();
            binaryCounter++;
            long temp = binaryCounter;
            for (int i = 0; i < map.size(); i++) {
                if ((temp & 0b1) == 0b1) {
                    current.add(map.getNode(i));
                }
                temp = temp >>> 1;
            }
            if (map.getValue(current) > map.getValue(best) && map.getWeight(current) < maxWeight) {
                best = (ArrayList<Node>) current.clone();
            }
        }
        return best;
    }

    public static long getBitMask(int a) {
        long bitMask = 0b0;
        for (int i = 0; i < a; i++) {
            bitMask = bitMask << 1;
            bitMask++;
        }
        return bitMask;
    }
    
}
