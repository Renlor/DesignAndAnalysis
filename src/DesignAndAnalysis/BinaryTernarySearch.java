package DesignAndAnalysis;

/**
 *
 * @author justin
 */
public class BinaryTernarySearch {
    public static int count = 0;

    public static void testBinarySearch(int start, int end, double randomFactor, int loops, double skip, int testSets) {
        if (start < 0 || end < 0) {
            throw new NegativeArraySizeException("The start and end array sizes can not be negative: " + start + ", " + end);
        }
        System.out.println("Binary Search Test");
        for (int i = start; i <= end;) {
            for(int k = 1; k <= testSets; k++){
            int randSize = (int) (i * randomFactor);
            long[] testArray = generateRandomIntegerArray(i, 0, randSize);
            long startTime, endTime, sumTime = 0;
            for (int j = 1; j <= loops; j++) {
                int searchValue = (int) (Math.random() * randSize);
                startTime = System.nanoTime();
                binarySearch(testArray, searchValue);
                endTime = System.nanoTime();
                sumTime += endTime - startTime;
            }
            System.out.println(i +" " + (double)count/loops + " " + sumTime/loops);
            count = 0;
        }
            if(i < 2){ i++; }else{ i=(int)(skip*i);}
        }
    }

    public static void testTernarySearch(int start, int end, double randomFactor, int loops, double skip, int testSets) {
        if (start < 0 || end < 0) {
            throw new NegativeArraySizeException("The start and end array sizes can not be negative: " + start + ", " + end);
        }
        System.out.println("\nTernary Search Test");
        for (int i = start; i <= end;) {
            for(int k = 1; k <= testSets; k++){
            int randSize = (int) (i * randomFactor);
            long[] testArray = generateRandomIntegerArray(i, 0, randSize);
            long startTime, endTime, sumTime = 0;
            for (int j = 1; j <= loops; j++) {
                int searchValue = (int) (Math.random() * randSize);
                startTime = System.nanoTime();
                ternarySearch(testArray, searchValue);
                endTime = System.nanoTime();
                sumTime += endTime - startTime;
            }
            System.out.println(i +" " + (double)count/loops + " " + sumTime/loops);
            count = 0;
        }
            if(i < 2){ i++; }else{ i=(int)(skip*i);}
        }
    }

    public static int binarySearch(long[] array, long value) {
        int p1 = 0;
        int p2 = array.length - 1;
        int div;
        while (p1 < p2) {
            count+= 1;
            div = p1 + (p2 - p1) / 2;
            if (value > array[div]) {
                p1 = div + 1;
            } else if (value < array[div]) {
                p2 = div - 1;
            } else {
                return div;
            }
        }
        return -1;
    }

    public static int ternarySearch(long[] array, long value) {
        int p1 = 0;
        int p2 = array.length - 1;
        int div1, div2;
        while (p1 < p2) {
            count+= 1;
            div1 = p1 + (p2 - p1) / 3;
            div2 = p1 + 2 * (p2 - p1) / 3;
            if (value > array[div1] && value < array[div2]) {
                p1 = div1 + 1;
                p2 = div2 - 1;
            } else if (value < array[div1]) {
                p2 = div1 - 1;
            } else if (value > array[div2]) {
                p1 = div2 + 1;
            } else {
                return div1;
            }
        }
        return -1;
    }

    public static long[] generateRandomIntegerArray(int length, int min, int max) {
        long[] array = new long[length];
        for (int i = 0; i < length; i++) {
            array[i] = min + (int) (Math.random() * (max - min + 1));
        }
        quickSort(array, 0, array.length - 1);
        return array;
    }

    private static void quickSort(long[] array, int left, int right) {
        if(array.length < 2){
           return; 
        }
        int part = partition(array, left, right);
        if(left < part - 1){
            quickSort(array, left, part - 1);
        }
        if(right > part){
            quickSort(array, part, right);
        }
    }

    private static int partition(long[] array, int left, int right) {
        long pivotValue = (array[left] + array[right] + array[(left + right) / 2]) / 3;
        int i = left, j = right;
        while (i <= j) {
            while (array[i] < pivotValue) {
                i++;
            }
            while (array[j] > pivotValue) {
                j--;
            }
            if (i <= j) {
                long temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                i++;
                j--;
            }
        }
        return i;
    }
}