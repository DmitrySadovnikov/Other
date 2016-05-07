import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.*;

public class Knapsack {


    public static void main(String[] args) throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader(new File("ttt")));
        int realtarget = 0;
        int[] items = new int[4];
        int[] result;
        try {
            for (int i = 0; i < items.length; i++) {

                System.out.println("Enter Weight" + (i + 1) + ":  ");
                items[i] = Integer.parseInt(reader.readLine());
            }

            for (int i = 0; i < items.length; i++)
                System.out.println("Value at " + (i + 1) + " is  :" + items[i]);

            System.out.print("Enter Target Value");
            realtarget = Integer.parseInt(reader.readLine());
        } catch (IOException io) {
            System.out.println(io);
        }

        System.out.println("Target Value is: " + realtarget);

        result = knapSack(items, realtarget, 0, 0);

        if (result == null) {

            int c = realtarget;

            while (result == null || c != 0) {
                result = knapSack(items, realtarget - 1, 0, 0);
                c--;
            }
        }


        for(int j=0; j < result.length; j++)
            System.out.println(result[j]);

    }

    // This function takes an array of items and a knapsack size. It attempts to
    // fill the knapsack exactly with the given items. It only considers items
    // in the array that are in slots first, first+1,... So when the function
    // is initially called it should be called with first equal to 0.
    // In addition the function also takes as input the size of the current
    // (partial) solution. This is initially 0.

    public static int[] knapSack(int[] items, int realtarget, int first, int solutionSize) {

        if (realtarget > 0) {
            // For each i, try placing the ith item in the knapsack
            for (int i = first; i < items.length; i++) {
                // Fit the smaller knapsack with items chosen from i+1, i+2,...
                int[] answer = knapSack(items, realtarget - items[i], i + 1, solutionSize + 1);

                if (answer != null) {
                    answer[solutionSize] = items[i];
                    return answer;
                }
            }

            return null;
        }
        // We have found a solution. So we create an array of the right size
        // and send it back for filling
        else if (realtarget == 0) {
            int[] temp = new int[solutionSize];
            return temp;
        } else // realtarget is negative, so no solution is possible
            return null;

    }

}