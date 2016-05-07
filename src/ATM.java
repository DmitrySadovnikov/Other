import java.io.*;
import java.util.*;

/**
 * I spent 4 days on the task by using the greedy algorithm,
 * but I haven't been able to optimize the solution for large amounts of money
 */
public class ATM {
    static Map<Integer, Integer> mapBanknoteAndCount;
    static Map<Integer, Integer> mapBanknotesForIssuance;
    static long amountOfMoney;

    public static void main(String[] args) throws IOException {
        System.out.println("You can use currency denominated 1, 3, 5, 10, 25, 50, 100, 500, 1000, 5000" +
                "\n" + "Enter command or \"help\" to view all commands");
        new ATM().reset();
        new ATM().read();

    }

    /**
     * Read commands from the console
     */
    private void read() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        //BufferedReader reader = new BufferedReader(new FileReader(new File("test")));

        while (true) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            while (tokenizer.hasMoreTokens()) {
                String cmd = tokenizer.nextToken();

                switch (cmd) {
                    case "put":
                        try {
                            int d = Integer.parseInt(tokenizer.nextToken());
                            int count = Integer.parseInt(tokenizer.nextToken());
                            printCmd(cmd, d, count);
                            if (d > 0 && count > 0) {
                                put(d, count);
                            }
                        } catch (NumberFormatException e) {
                        }
                        break;
                    case "get":
                        try {
                            int amount = Integer.parseInt(tokenizer.nextToken());
                            printCmd(cmd, amount);
                            if (amount > 0) {
                                get(amount);
                            }
                        } catch (NumberFormatException e) {
                        }
                        break;
                    case "dump":
                        printCmd(cmd);
                        dump();
                        break;
                    case "state":
                        printCmd(cmd);
                        state();
                        break;
                    case "quit":
                        printCmd(cmd);
                        System.out.println("Thank you for using our ATM, goodbye");
                        return;
                }
            }
        }
    }

    /**
     * Duplicate the command and displays it to the console
     */
    private void printCmd(String cmd, int... i) {
        System.out.println("<" + cmd + " " + Arrays.toString(i).replaceAll("[\\,\\[\\]]", ""));
    }

    /**
     * Gets a specified amount of money from the ATM
     */
    private void get(int requestedAmount) {
        int remainder = withdraw(requestedAmount);
        int availableAmount = requestedAmount - remainder;
        printGet(mapBanknotesForIssuance, requestedAmount, availableAmount);
        amountOfMoney -= availableAmount;
    }

    /**
     * Takes the requested amount of money from the total amount
     */
    private int withdraw(int requestedAmount) {

        mapBanknotesForIssuance = new TreeMap<>(Comparator.<Integer>reverseOrder());
        for (Map.Entry<Integer, Integer> pair : mapBanknoteAndCount.entrySet()) {
            int key = pair.getKey();
            int value = pair.getValue();

            for (int i = 0; i < value; i++) {
                if (requestedAmount < key)
                    break;
                else {
                    requestedAmount -= key;
                    if (mapBanknotesForIssuance.containsKey(key)) {
                        mapBanknotesForIssuance.put(key, mapBanknotesForIssuance.get(key) + 1);
                    } else mapBanknotesForIssuance.put(key, 1);
                }
            }
        }

        for (Map.Entry<Integer, Integer> pair : mapBanknotesForIssuance.entrySet()) {
            int key = pair.getKey();
            int value = pair.getValue();
            mapBanknoteAndCount.put(key, mapBanknoteAndCount.get(key) - value);
        }
        return requestedAmount;
    }

    /**
     * Displays to the console the message about the successful withdrawal of money
     */
    private void printGet(Map<Integer, Integer> map, long theorySum, long realSum) {
        long remainder = theorySum - realSum;

        System.out.println(map.toString().replaceAll("[\\{\\}]", "") + ", всего " + realSum);
        if (remainder != 0) {
            System.out.println("без " + Math.abs(remainder));
        }
    }

    /**
     * Puts a specified amount of money to the ATM
     */
    private void put(int d, int count) {
        if (mapBanknoteAndCount.containsKey(d)) {
            mapBanknoteAndCount.put(d, mapBanknoteAndCount.get(d) + count);
            amountOfMoney += ((long) d * count);
            System.out.println("всего " + amountOfMoney);
        } else {
            System.out.println("Sorry, insert correct banknote");
        }
    }

    /**
     * Displays to the console the total amount of money
     */
    private void state() {
        System.out.println(amountOfMoney);
    }

    /**
     * Displays to the console the number of banknotes
     */
    private void dump() {
        System.out.println(mapBanknoteAndCount.toString().replaceAll("[\\{\\}]", ""));
    }

    /**
     * Resets all the money in the ATM
     */
    private void reset() {
        mapBanknoteAndCount = new TreeMap<>(Comparator.<Integer>reverseOrder());
        mapBanknoteAndCount.put(5000, 0);
        mapBanknoteAndCount.put(1000, 0);
        mapBanknoteAndCount.put(500, 0);
        mapBanknoteAndCount.put(100, 0);
        mapBanknoteAndCount.put(25, 0);
        mapBanknoteAndCount.put(10, 0);
        mapBanknoteAndCount.put(50, 0);
        mapBanknoteAndCount.put(5, 0);
        mapBanknoteAndCount.put(3, 0);
        mapBanknoteAndCount.put(1, 0);
        amountOfMoney = 0;
    }
}