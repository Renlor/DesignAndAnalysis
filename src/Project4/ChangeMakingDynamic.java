package Project4;

/**
 *
 * @author Renlar <liddev.com>
 */
public class ChangeMakingDynamic {
    public static void main(String args[]){
        int[] coins = {1, 3, 4, 10, 12};
        int change = 361;
        int[] results = makeChange(coins, change);
        System.out.print("\nCoins:\t");
        for(int i = 0; i < coins.length; i++){
            System.out.print(coins[i] + "\t|");
        }
        System.out.print("\nChange:\t");
        for(int i = 0; i < results.length; i++){
            System.out.print(results[i] + "\t|");
        }
    }
    public static int[] makeChange(int[] coins, int value){
        int[] minCoins = new int[value + 1];
        int[] firstCoin = new int[value + 1];
        for(int p = 1; p <= value; p++){
            int min = -1, coin = 0;
            for(int i = 0; i < coins.length; i++){
                if(coins[i] <= p){
                    if(1 + minCoins[p - coins[i]] < min || min == -1){
                        min = 1 + minCoins[p - coins[i]];
                        coin = i;
                    }
                }
            }
            minCoins[p] = min;
            firstCoin[p] = coin;
        }
    return getChange(firstCoin, value, coins);
    }

    private static int[] getChange(int[] firstCoin, int value, int[] coins) {
        int[] results = new int[coins.length];
        while(value > 0){
            results[firstCoin[value]]++;
            value -= coins[firstCoin[value]];
        }
        return results;
    }
    
}
