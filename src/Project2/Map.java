package Project2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author justin
 */
public class Map implements Cloneable {

    private String type;
    public static final String tsp3d = "TSP3D";
    public static final String tsp2d = "TSP2D";
    public static final String ksp = "KSP";
    private String n1;
    private String n2;
    private String n3;
    private String name;
    private ArrayList<Node> nodes;
    private ArrayList<Node> bestSet;
    private long bestValue;
    private long runtimeMillis;

    public Map(String type, String name) {
        if (type.equalsIgnoreCase(tsp2d)) {
            type = tsp2d;
            this.n1 = "x: ";
            this.n2 = "y: ";
        } else if (type.equalsIgnoreCase(tsp3d)) {
            type = tsp3d;
            this.n1 = "x: ";
            this.n2 = "y: ";
            this.n3 = "z: ";
        } else if (type.equalsIgnoreCase(ksp)) {
            type = ksp;
            this.n1 = "w: ";
            this.n2 = "v: ";
        } else {
            Logger.getLogger("global").log(Level.SEVERE, null, "Unknown map type: " + type);
        }
        this.type = type;
        nodes = new ArrayList<>();
        bestSet = new ArrayList<>();
        this.name = name;
    }

    public Map(String type, String name, ArrayList<Node> nodes) {
        this(type, name);
        this.nodes = nodes;
    }

    /**
     * @param deviationx maximum distance from 0 for x Position or weight
     * @param deviationy maximum distance from 0 for y Position or value
     * @param deviationz maximum distance from 0 for z Position set to 0
     * otherwise
     */
    public static Map generateMap(String type, String name, int numberOfNodes, int deviationx, int deviationy, int deviationz) {
        Map map = new Map(type, name);
        if (type.equals(tsp2d) || type.equals(tsp3d)) {
            generateTSPNodes(map, numberOfNodes, deviationx, deviationy, deviationz);
            for (Node n : map.nodes) {
                n.populateConnections(map.nodes);
            }
        } else if (type.equals(ksp)) {
            generateNSPNodes(map, numberOfNodes, deviationx, deviationy);
        }
        return map;
    }

    private static void generateTSPNodes(Map map, int numberOfNodes, int deviationx, int deviationy, int deviationz) {
        for (int i = 0; i < numberOfNodes; i++) {
            map.addNode(new Node((int) (Math.random() * (deviationx + 1)), (int) (Math.random() * (deviationy + 1)), (int) (Math.random() * (deviationz + 1))));
        }
    }

    private static void generateNSPNodes(Map map, int numberOfNodes, int deviationw, int deviationv) {
        for (int i = 0; i < numberOfNodes; i++) {
            map.addNode(new Node((int) (Math.random() * (deviationw) + 1), (int) (Math.random() * (deviationv) + 1)));
        }
    }

    public String getN1() {
        return n1;
    }

    public String getN2() {
        return n2;
    }

    public String getN3() {
        return n3;
    }

    public void setN1(String n1) {
        this.n1 = n1;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public void setN3(String n3) {
        this.n3 = n3;
    }

    public String getType() {
        return type;
    }

    public Node getNode(Integer node) {
        return nodes.get(node);
    }

    public ArrayList<Node> getNodes() {
        return (ArrayList<Node>) nodes.clone();
    }

    synchronized public void addNode(Node node) {
        nodes.add(node);
    }

    public String getName() {
        return name;
    }

    public int size() {
        return nodes.size();
    }

    public int getNumber(Node node) {
        return nodes.indexOf(node);
    }

    public long getPathLength(ArrayList<Node> path) {
        long length = 0;
        for (int i = 0; i < this.nodes.size() - 1; i++) {
            length += path.get(i).getConnection(path.get(i + 1));
        }
        length += path.get(path.size() - 1).getConnection(path.get(0));
        return length;
    }

    public int getWeight(ArrayList<Node> current) {
        int weight = 0;
        for (int i = 0; i < current.size(); i++) {
            weight += current.get(i).getX();
        }
        return weight;
    }

    public int getValue(ArrayList<Node> current) {
        int value = 0;
        for (int i = 0; i < current.size(); i++) {
            value += current.get(i).getY();
        }
        return value;
    }

    public ArrayList<Node> getBest() {
        return (ArrayList<Node>) bestSet.clone();
    }

    public void setBest(ArrayList<Node> set) {
        bestSet = (ArrayList<Node>) set.clone();
    }

    public void setBestValue(long value) {
        bestValue = value;
    }

    public long getBestValue() {
        return bestValue;
    }

    public void print(ArrayList p) {
        if ((type == null ? tsp2d == null : type.equals(tsp2d)) || (type == null ? ksp == null : type.equals(ksp))) {
            print2D(p);
        } else if (type == null ? tsp3d == null : type.equals(tsp3d)) {
            print3D(p);
        }
    }

    private void print2D(ArrayList p) {
        if (p.isEmpty()) {
            //Logger.getLogger("global").log(Level.SEVERE, null, "Nothing to print");
        } else if (p.get(0) instanceof Node) {
            ArrayList<Node> path = (ArrayList<Node>) p;
            for (Node node : path) {
                System.out.println("Node " + nodes.indexOf(node) + ": " + n1
                        + " " + node.getX() + " " + n2 + " " + node.getY());
            }
            if (type.equals(ksp)) {
                int weight = 0;
                int value = 0;
                for (Node node : path) {
                    weight += node.getX();
                    value += node.getY();
                }
                System.out.println("Total weight: " + weight + " Total value: " + value);
            }
            if (type.equals(tsp2d)) {
                System.out.println("Path Length Square Sum: " + getPathLength(p));
            }

        } else {
            Logger.getLogger("global").log(Level.SEVERE, null, "Unable to print path of type: " + p.getClass());
        }
    }

    private void print3D(ArrayList p) {
        if (p.get(0) instanceof Node) {
            ArrayList<Node> path = (ArrayList<Node>) p;
            for (Node node : path) {
                System.out.println("Node " + nodes.indexOf(node) + ": " + n1
                        + " " + node.getX() + " " + n2 + " " + node.getY() + " " + n3 + " " + node.getZ());
            }
        } else {
            Logger.getLogger("global").log(Level.SEVERE, null, "Unable to print path of type: " + p.getClass());
        }
    }

    public void writeOut() {
        String output;
        DumperOptions options = new DumperOptions();
        options.setAllowReadOnlyProperties(true);
        options.setCanonical(true);
        Yaml yaml = new Yaml(options);
        output = yaml.dump(this);
        try {
            FileWriter writer = new FileWriter(this.name + ".yml");
            writer.write(output);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Map readIn(String file) {

        Map map = null;
        try {
            InputStream input = new FileInputStream(new File(file));
            Yaml yaml = new Yaml();
            map = (Map) yaml.load(input);
            input.close();
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        return map;
    }

    public void setRuntimeMillis(long runtime) {
        runtimeMillis = runtime;
    }

    public long getRuntimeMillis() {
        return runtimeMillis;
    }
}