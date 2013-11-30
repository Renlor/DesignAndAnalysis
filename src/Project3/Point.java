package Project3;

/**
 *
 * @author Renlar <liddev.com>
 */
public class Point {

    private double x, y, z;
    private Point next = null, previous = null;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public double getZ(){
        return z;
    }
    
    public Point getNext(){
        return next;
    }
    
    public Point getPrevious(){
        return previous;
    }
    
    public void setNext(Point next){
        this.next = next;
    }
    
    public void setPrevious(Point previous){
        this.previous = previous;
    }
}
