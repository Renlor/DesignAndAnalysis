package Project2;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author justin
 */
public class Node {

    private int x = 0;
    private int y = 0;
    private int z = 0;
    private HashMap<Node, Double> connections;
    
    public Node(){
    }
    
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
    
    public void setConnections(HashMap<Node, Double> connections){
        this.connections = connections;
    }

    public double distance(Node node2) {
        int x1 = this.getX(), x2 = node2.getX();
        int y1 = this.getY(), y2 = node2.getY();
        int z1 = this.getZ(), z2 = node2.getZ();
        long distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2);
        return Math.sqrt(distance);
    }

    public double getConnection(Node node) {
        return connections.get(node);
    }
    
    public void addConnection(Node node, double distance){
        connections.put(node, distance);
    }
    
    public void addConnection(Node node){
        connections.put(node, getDistance(node));
    }

    public void populateConnections(ArrayList<Node> nodes) {
        connections = new HashMap<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            connections.put(node, this.distance(node));
        }
    }
    
    public double getDistance(Node node){
        return this.distance(node);
    }
    
    public void print(){
        System.out.println("x: " + x + " y: " + y);
    }
    
    public HashMap<Node, Double> getConnections(){
        return (HashMap<Node, Double>) connections;
    }
}
