package Project3;

import java.awt.Color;
import java.util.ArrayList;
import static java.util.Collections.swap;
import java.util.List;

/**
 *
 * @author Renlar <liddev.com>
 */
public class ConvexHull {

    private static List<Point> set;
    private static long timeout = 600;
    private static int xMax = 100;
    private static int yMax = 100;
    private static boolean drawEnabled = true;

    public static void main(String[] args) {
        long time = 0, start, stop;
        int size = 50;
        while (time / 1000000000 < timeout) {
            ConvexHull c = new ConvexHull(xMax, yMax, 0, size);
            if (drawEnabled) {
                c.draw(-2, xMax + 2, -2, yMax + 2);
                StdDraw.setPenColor(Color.red);
                for (int i = 0; i < set.size(); i++) {
                    StdDraw.filledCircle(set.get(i).getX(), set.get(i).getY(), .5);
                }
            }
            start = System.nanoTime();
            Point hull = c.divideAndConquer(set);
            stop = System.nanoTime();
            time = stop - start;
            if (hull == null) {
                System.out.println("null");
            } else {
                System.out.println(size + ", " + time);
                if (drawEnabled) {
                    time = timeout * 1000000000 + 1;
                    Point a = hull.getNext();
                    Point b = hull;
                    while (a != hull) {
                        StdDraw.setPenColor(Color.black);
                        StdDraw.line(a.getX(), a.getY(), b.getX(), b.getY());
                        StdDraw.setPenColor(Color.green);
                        StdDraw.filledCircle(b.getX(), b.getY(), .5);
                        a = a.getNext();
                        b = b.getNext();
                    }
                    StdDraw.setPenColor(Color.black);
                    StdDraw.line(a.getX(), a.getY(), b.getX(), b.getY());
                    StdDraw.setPenColor(Color.green);
                    StdDraw.filledCircle(b.getX(), b.getY(), .5);
                }
            }

            size += size / 2;
        }
        StdDraw.show(1000);
    }

    public ConvexHull(double devx, double devy, double devz, int number) {
        set = new ArrayList<>();
        generatePoints(devx, devy, devz, number);
    }

    public void generatePoints(double devx, double devy, double devz, int number) {
        for (int i = 0; i < number; i++) {
            set.add(new Point((double) (Math.random() * devx), (double) (Math.random() * devy), (double) (Math.random() * devz)));
        }
        set = mergesort(set);
    }

    public Point divideAndConquer(List<Point> set) {
        if (set.size() < 4) {
            return buildHull(set);
        }
        Point hull1 = divideAndConquer(set.subList(0, set.size() / 2));
        Point hull2 = divideAndConquer(set.subList(set.size() / 2, set.size()));
        return combine(hull1, hull2);
    }

    public Point buildHull(List<Point> set) {
        if (set.size() == 1) {
            Point a = set.get(0);
            a.setNext(a);
            a.setPrevious(a);
            return a;
        } else if (set.size() == 2) {
            Point a = set.get(0);
            Point b = set.get(1);
            a.setNext(b);
            b.setNext(a);
            a.setPrevious(b);
            b.setPrevious(a);
            return a;
        } else if (set.size() == 3) {
            Point a = set.get(0);
            Point b = set.get(1);
            Point c = set.get(2);
            if (convex(a, b, c)) {
                a.setNext(b);
                b.setNext(c);
                c.setNext(a);
                a.setPrevious(c);
                b.setPrevious(a);
                c.setPrevious(b);
            } else {
                a.setNext(c);
                b.setNext(a);
                c.setNext(b);
                a.setPrevious(b);
                b.setPrevious(c);
                c.setPrevious(a);
            }
            return a;
        }
        return null;
    }

    public Point combine(Point hulla, Point hullb) {
        Point al = getRightPoint(hulla);
        Point bl = hullb;
        while (!convex(al.getPrevious(), al, bl) || !convex(al, bl, bl.getNext())) {
            while (!convex(al.getPrevious(), al, bl)) {
                al = al.getPrevious();
            }
            while (!convex(al, bl, bl.getNext())) {
                bl = bl.getNext();
            }
        }

        Point au = getRightPoint(hulla);
        Point bu = hullb;
        while (!convex(bu, au, au.getNext()) || !convex(bu.getPrevious(), bu, au)) {
            while (!convex(bu, au, au.getNext())) {
                au = au.getNext();
            }
            while (!convex(bu.getPrevious(), bu, au)) {
                bu = bu.getPrevious();
            }
        }
        Point hull = hulla;
        au.setPrevious(bu);
        bu.setNext(au);
        al.setNext(bl);
        bl.setPrevious(al);
        return hull;
    }

    /**
     * return true if the points a, b, c, in counterclockwise order, are convex
     *
     * @param a first point in counterclockwise order
     * @param b next point in counterclockwise order
     * @param c last point in counterclockwise order
     */
    private boolean convex(Point a, Point b, Point c) {
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (c.getX() - a.getX()) * (b.getY() - a.getY()) <= 0.0;
    }

    public double distance(Point a, Point b) {
        return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
    }

    private Point getRightPoint(Point hull) {
        Point a = hull.getNext();
        Point right = hull.getNext();
        while (hull != a) {
            if (a.getX() > right.getX()) {
                right = a;
            }
            a = a.getNext();
        }
        return right;
    }

    public static List<Point> mergesort(List<Point> list) {
        if (list.size() < 4) {
            return sort(list);
        } else {
            List<Point> list1 = mergesort(list.subList(0, list.size() / 2));
            List<Point> list2 = mergesort(list.subList(list.size() / 2, list.size()));
            return merge(list1, list2);
        }
    }

    private static List<Point> sort(List<Point> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(j).getX() < list.get(i).getX()) {
                    swap(list, i, j);
                }
            }
        }
        return list;
    }

    private static List<Point> merge(List<Point> list1, List<Point> list2) {
        List<Point> list = new ArrayList<>(list1.size() + list2.size());
        int i = 0, j = 0;
        boolean done = false;
        while (!done) {
            if (j < list2.size() && i < list1.size()) {
                if (list1.get(i).getX() > list2.get(j).getX()) {
                    list.add(list2.get(j));
                    j++;
                } else if (list1.get(i).getX() == list2.get(j).getX()) {
                    if (list1.get(i).getY() > list2.get(j).getY()) {
                        list.add(list2.get(j));
                        j++;
                    } else {
                        list.add(list1.get(i));
                        i++;
                    }
                } else {
                    list.add(list1.get(i));
                    i++;
                }
            } else if (i < list1.size()) {
                list.add(list1.get(i));
                i++;
            } else if (j < list2.size()) {
                list.add(list2.get(j));
                j++;
            } else {
                done = true;
            }
        }
        return list;
    }

    // display the maze in turtle graphics
    public void draw(int xMin, int xMax, int yMin, int yMax) {
        StdDraw.setXscale(yMin, yMax);
        StdDraw.setYscale(xMin, xMax);
    }
}
