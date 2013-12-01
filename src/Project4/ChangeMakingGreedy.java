package Project4;

/**
 *
 * @author Renlar <liddev.com>
 */
public class ChangeMakingGreedy {

    public static void main(String args[]){
        int[] coins = {1, 5, 10, 25, 50, 100};
        int value = 123;
        int[] result = makeChange(coins, value);
        System.out.print("Coins:\t");
        for(int i = result.length - 1; i >= 0; i--){
            System.out.print(coins[i] + "\t| ");
        }
        System.out.print("\nChange:\t");
        for(int i = result.length - 1; i >= 0; i--){
            System.out.print(result[i] + "\t| ");
        }
    }
    
    /**
     * @param coins the list of coins in ascending order.
     * @param value the value to make change for.
     * @return the number of each coin in ascending order.
     */
    public static int[] makeChange(int[] coins, int value) {
        int[] result = new int[coins.length];
        for (int i = coins.length - 1; i >= 0; i--) {
            result[i] = value / coins[i];
            value = value %coins[i];
        }
        return result;
    }
}
