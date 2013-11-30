package Project3;

import java.util.ArrayList;
import static java.util.Collections.swap;
import java.util.List;

/**
 *
 * @author Renlar <liddev.com>
 */
public class InversionDivideAndConquer {

    private static long inversions = 0;
    private static long timeout = 600;

    public static void main(String args[]) {
        int size = 10;
        long time = 0;
        long start, end;
        //while (time/1000000000 < timeout) {
        int[] set = {0, 53, 1, 71, 8, 2, 44, 71, 94, 21};
        //List<Integer> list = randomList(size, 100);
        List<Integer> list = new ArrayList<>();
        for (int i : set) {
            list.add(i);
        }
        for (Integer i : list) {
            System.out.print(i + ", ");
        }
        System.out.println();
        start = System.nanoTime();
        mergesortCountInversions(list);
        end = System.nanoTime();
        time = end - start;
        System.out.println(size + ", " + inversions + ", " + time);
        inversions = 0;
        if (size < 2) {
            size++;
        } else {
            size += size / 2;
        }
        // }
    }

    public static List<Integer> randomList(int size, int deviation) {
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add((int) (Math.random() * deviation));
        }
        return list;
    }

    public static List<Integer> mergesortCountInversions(List<Integer> list) {
        if (list.size() < 7) {
            return countInversionSort(list);
        } else {
            List<Integer> list1 = mergesortCountInversions(list.subList(0, list.size() / 2));
            List<Integer> list2 = mergesortCountInversions(list.subList(list.size() / 2, list.size()));
            return countInversionMerge(list1, list2);
        }
    }

    private static List<Integer> countInversionSort(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    inversions++;
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(j) < list.get(i)) {
                    swap(list, i, j);
                }
            }
        }
        return list;
    }

    private static List<Integer> countInversionMerge(List<Integer> list1, List<Integer> list2) {
        int i = 0, j = 0;
        while (i < list1.size()) {
            if (j < list2.size()) {
                if (list1.get(i) <= list2.get(j)) {
                    inversions += j;
                    i++;
                } else {
                    j++;
                }
            } else {
                inversions += j;
                i++;
            }
        }
        return merge(list1, list2);
    }

    private static List<Integer> merge(List<Integer> list1, List<Integer> list2) {
        List<Integer> list = new ArrayList<>(list1.size() + list2.size());
        int i = 0, j = 0;
        boolean done = false;
        while (!done) {
            if (j < list2.size() && i < list1.size()) {
                if (list1.get(i) >= list2.get(j)) {
                    list.add(list2.get(j));
                    j++;
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
}
