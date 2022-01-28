/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 21, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] openPerc;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n < 2 || trials < 2) {
            String errMsg
                    = "Improper Argument: The system must contain at least n>2 and 2+ trial.";
            throw new IllegalArgumentException(errMsg);
        }
        openPerc = new double[trials];
        Percolation p;
        int[] randNodes;

        // Run trials
        for (int trial = 0; trial < trials; trial++) {

            p = new Percolation(n);
            randNodes = StdRandom.permutation(n * n);

            int nodeIdx = 0;
            int[] currRowCol;

            // DEBUG only
            // String str1;

            // Open nodes one at a time (randomly) until percolation
            while (!p.percolates()) {
                currRowCol = _1Dto2D(randNodes[nodeIdx] + 1, n);

                // DEBUG only
                // str1 = String.format("Opening [%d, %d] Index %d", currRowCol[0], currRowCol[1],
                //                      randNodes[nodeIdx] + 1);
                // StdOut.println(str1);

                p.open(currRowCol[0], currRowCol[1]);
                nodeIdx++;
            }

            // Record percent of nodes open upon percolation for trial
            openPerc[trial] = (double) p.numberOfOpenSites() / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openPerc);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openPerc);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double delta = 1.96 * stddev() / Math.sqrt(openPerc.length);
        return mean() - delta;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double delta = 1.96 * stddev() / Math.sqrt(openPerc.length);
        return mean() + delta;
    }

    private int _2Dto1D(int row, int col, int n) {
        if (row == 0 || col == 0) {
            return 0;
        }
        else {
            int _1D_idx = n * (row - 1) + col;
            return _1D_idx;
        }
    }

    private int[] _1Dto2D(int idx, int n) {
        if (idx == 0) {
            int[] root = { 0, 0 };
            return root;
        }
        else {
            int[] idxs = { ((idx - 1) / n) + 1, ((idx - 1) % n) + 1 };
            return idxs;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        if (args.length == 2) {
            PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                          Integer.parseInt(args[1]));
            String str1 = String.format("mean     = %f", stats.mean());
            StdOut.println(str1);
            str1 = String.format("stddev   = %f", stats.stddev());
            StdOut.println(str1);
            str1 = String
                    .format("95%% C.I. = [%f, %f]", stats.confidenceLo(), stats.confidenceHi());
            StdOut.println(str1);
        }
        else {
            StdOut.println("Improper Input. Please provide 2 arguments for 'n' and 'trials'");
        }
    }
}
