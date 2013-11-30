package Project3;

import java.util.ArrayList;
import static java.util.Collections.swap;
import java.util.List;

/**
 *
 * @author Renlar <liddev.com>
 */
public class SPQuickSelect {

    private static long timeout = 600;

    public static void main(String args[]) {
        int size = 10;
        long time = 0;
        long start, end;
        while (time/1000000000 < timeout) {
            ArrayList<Integer> list = randomList(size, 100);
            start = System.nanoTime();
            select(list, (int)((size) * Math.random()));
            end = System.nanoTime();
            time = end - start;
            System.out.println(size + ", " + time);
            size += size / 2;
        }
    }

    public static ArrayList<Integer> randomList(int size, int deviation) {
        ArrayList<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((int) (Math.random() * deviation));
        }
        return list;
    }

    public static int select(List<Integer> list, int k) {
        int pivot;
        boolean notFound = true;
        while (notFound) {
            pivot = partition(list);
            if (pivot <= k) {
                list = list.subList(pivot, list.size());
                k -= pivot;
            } else {
                list = list.subList(0, pivot);
            }
            if (list.size() == 1) {
                notFound = false;
            }
        }
        return list.get(0);
    }

    public static int partition(List<Integer> list) {
        int pivot = list.get(list.size() / 2); //(list.get(list.size() / 2) + list.get(0) + list.get(list.size() - 1)) / 3;
        int i = 0;
        int j = list.size() - 1;
        while (i < j) {
            while (list.get(j) > pivot && i < j) {
                j--;
            }
            while (list.get(i) <= pivot && i < j) {
                i++;
            }
            swap(list, i, j);
        }
        return i;
    }
}
