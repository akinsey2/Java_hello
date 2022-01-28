/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 16, 2021
 **************************************************************************** */

// Notes:
// - CLOSED if site contains {-2,-2}
// - Virtual TOP site is {-1,-1}
// - Top row has a tree depth of zero

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int n;
    private boolean[] open;
    private int numOpen;
    private WeightedQuickUnionUF network;

    //--------------------------------------------------------------------------
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        if (n < 1) {
            String errMsg = "Improper Argument: The network must contain at least 1 site.";
            throw new IllegalArgumentException(errMsg);
        }
        this.n = n;     // Store N of current instance
        this.numOpen = 0;

        this.network = new WeightedQuickUnionUF(n * n + 2);
        this.open = new boolean[n * n + 2];

        // Initialize "open" indicator array
        for (int i = 0; i < (n * n + 1); i++) {
            this.open[i] = false;
        }
    }

    //--------------------------------------------------------------------------
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        this.indexCheck(row, col);
        int netIdx = _2Dto1D(row, col);

        // If node is already open, do nothing
        if (open[netIdx]) {
            return;
        }

        // Else, connect to rest of nets
        open[netIdx] = true;
        numOpen++;

        // If box in top row, union to virtual top
        if (row == 1) {
            network.union(0, netIdx);
        }

        // If box open above, union to it
        if (row > 1 && isOpen(row - 1, col)) {
            int aboveIdx = _2Dto1D(row - 1, col);
            network.union(netIdx, aboveIdx);
        }

        // If box open right, union to it
        if (col < n && isOpen(row, col + 1)) {
            int rightIdx = _2Dto1D(row, col + 1);
            network.union(netIdx, rightIdx);
        }

        // If box open left, union to it
        if (col > 1 && isOpen(row, col - 1)) {
            int leftIdx = _2Dto1D(row, col - 1);
            network.union(netIdx, leftIdx);
        }

        // If box open below, union to it
        if (row < n && isOpen(row + 1, col)) {
            int belowIdx = _2Dto1D(row + 1, col);
            network.union(netIdx, belowIdx);
        }

        // If box in bottom row, union to virtual bottom
        if (row == n) {
            int virtBotIdx = n * n + 1;
            network.union(netIdx, virtBotIdx);
        }
    }


    //--------------------------------------------------------------------------
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        indexCheck(row, col);
        int netIdx = _2Dto1D(row, col);
        return open[netIdx];
    }

    //--------------------------------------------------------------------------
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        indexCheck(row, col);
        int netIdx = _2Dto1D(row, col);
        return (network.find(netIdx) == network.find(0));
    }

    //--------------------------------------------------------------------------
    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the network percolate?
    public boolean percolates() {
        boolean topToBottom = network.find(0) == n * n + 1;
        boolean bottomToTop = network.find(n * n + 1) == 0;
        boolean endsToMiddle = network.find(0) == network.find(n * n + 1);
        return (topToBottom || bottomToTop || endsToMiddle);
    }

    //--------------------------------------------------------------------------
    private void indexCheck(int row, int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            String errMsg = "Improper Argument: Site indeces must be between 1 and n.";
            throw new IllegalArgumentException(errMsg);
        }
    }

    //--------------------------------------------------------------------------
    private int _2Dto1D(int row, int col) {
        if (row == 0 || col == 0) {
            return 0;
        }
        else {
            int _1D_idx = this.n * (row - 1) + col;
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

    private void printTable() {

        String str1 = "     ";
        for (int i = 0; i < this.n; i++) {
            str1 += String.format("%2d ", i + 1);
        }
        StdOut.println(str1);

        // Generate and print table
        int idx = 1;
        for (int i = 0; i < this.n; i++) {

            str1 = String.format("%3d  ", i + 1);
            for (int j = 0; j < this.n; j++) {
                if (this.open[idx]) {
                    str1 += " O ";
                }
                else {
                    str1 += "   ";
                }
                idx++;
            }
            StdOut.println(str1);
        }
    }

    //--------------------------------------------------------------------------
    // test client (optional)
    public static void main(String[] args) {

        /* StdOut.print("\nEnter N: ");
        int n = StdIn.readInt();

        Percolation test1 = new Percolation(n);

        String str1;
        int randRow = 0, randCol = 0;
        while (!test1.percolates()) {
            StdOut.println("\n--------------------");
            str1 = String.format("Num Open: %3d  |  Num Sets: %2d",
                                 test1.numberOfOpenSites(), test1.network.count());
            StdOut.println(str1);
            StdOut.println("Percolates: " + test1.percolates());

            StdOut.println("\nCurrent Table:\n");
            test1.printTable();

            str1 = String.format("\nInserted [%2d,%2d]  Index: %3d", randRow, randCol,
                                 test1._2Dto1D(randRow, randCol));
            StdOut.println(str1);
            int root = test1.network.find(test1._2Dto1D(randRow, randCol));
            int[] idxs = test1._1Dto2D(root, n);
            str1 = String.format("Root: [%2d, %2d]  Index: %3d", idxs[0], idxs[1], root);
            StdOut.println(str1);

            // Wait for input
            StdOut.print("\nEnter to continue. 'n' to quit: ");
            char c = StdIn.readChar();
            if (c == 'n') {
                break;
            }
            randRow = StdRandom.uniform(n) + 1;
            randCol = StdRandom.uniform(n) + 1;
            test1.open(randRow, randCol);
        }

        StdOut.println("\n--------------------");
        str1 = String.format("Num Open: %3d  |  Num Sets: %2d",
                             test1.numberOfOpenSites(), test1.network.count());
        StdOut.println(str1);
        StdOut.println("Percolates: " + test1.percolates());

        StdOut.println("\nCurrent Table:\n");
        test1.printTable();

        str1 = String.format("\nInserted [%2d,%2d]  Index: %3d", randRow, randCol,
                             test1._2Dto1D(randRow, randCol));
        StdOut.println(str1);
        int root = test1.network.find(test1._2Dto1D(randRow, randCol));
        int[] idxs = test1._1Dto2D(root, n);
        str1 = String.format("Root: [%2d, %2d]  Index: %3d", idxs[0], idxs[1], root);
        StdOut.println(str1);
        StdOut.println("\n   IT PERCOLATES!\n"); */
    }
}
