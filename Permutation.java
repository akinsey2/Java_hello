/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 27, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Enter 1 C.L. argument");
        }

        int numReturn = Integer.parseInt(args[0]);

        if (StdIn.isEmpty()) {
            StdOut.println("No input was provided.");
            return;
        }

        RandomizedQueue<String> randQueue = new RandomizedQueue<>();
        String word;
        while (!StdIn.isEmpty()) {
            word = StdIn.readString();
            randQueue.enqueue(word);
        }

        for (int i = 0; i < numReturn; i++) {
            StdOut.println(randQueue.dequeue());
        }
    }
}
