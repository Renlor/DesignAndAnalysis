package Project4;

/**
 *
 * @author Renlar <liddev.com>
 */
public class EditDistance {

    public static void main(String args[]) {
        String s1 = "sandy";
        String s2 = "sand";
        int[] a = Distance(s1, s2);
        System.out.println("String1:" + s1 + " String2:" + s2 + " insertions: " + a[0] + " deletions: " + a[1] + " replacements: " + a[2]);
    }

    public static int[] Distance(String s1, String s2) {
        int insertions = 0, deletions = 0, replacements = 0;
        char[] set1 = s1.toCharArray();
        char[] set2 = s2.toCharArray();
        int[][] result = new int[set1.length + 1][set2.length + 1];
        for (int j = 0; j <= set1.length; j++) {
            result[j][0] = j;
        }
        for (int j = 0; j <= set2.length; j++) {
            result[0][j] = j;
        }

        for (int i = 1; i <= set1.length; i++) {
            for (int j = 1; j <= set2.length; j++) {
                if (set1[i - 1] == set2[j - 1]) {
                    result[i][j] = result[i - 1][j - 1];
                } else {
                    int n = Math.min(result[i - 1][j - 1], Math.min(result[i][j - 1], result[i - 1][j]));
                    result[i][j] = n + 1;
                }
            }
        }
        int i = set1.length;
        int j = set2.length;
        while (i > 0 || j > 0) {
            if (result[i - 1][j - 1] <= result[i][j - 1]) {
                if (result[i - 1][j - 1] <= result[i - 1][j]) {
                    if (result[i][j] > result[i - 1][j - 1]) {
                        replacements++;
                    }
                    i--;
                    j--;
                } else {
                    if (result[i][j] > result[i - 1][j]) {
                        deletions++;
                    }
                    i--;
                }
            } else {
                if (result[i][j - 1] < result[i - 1][j]) {
                    if (result[i][j] > result[i][j - 1]) {
                        insertions++;
                    }
                    j--;
                } else {
                    if (result[i][j] > result[i - 1][j]) {
                        deletions++;
                    }
                    i--;
                }
            }
        }

        System.out.println();
        for (i = 0; i < result.length; i++) {
            for (j = 0; j < result[i].length; j++) {
                System.out.print(result[i][j] + " | ");
            }
            System.out.println();
        }
        return new int[]{insertions, deletions, replacements};
    }
}
