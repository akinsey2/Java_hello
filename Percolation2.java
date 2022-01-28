/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 20, 2021
 **************************************************************************** */

// Notes:
// - CLOSED if site contains {-2,-2}
// - Virtual TOP site is {-1,-1}
// - Top row has a tree depth of zero

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Percolation2 {


    int[][][] system;  // Main array used to store
    int N;
    int num_open;

    //--------------------------------------------------------------------------
    // creates n-by-n grid, with all sites initially blocked
    public Percolation2(int N) {

        if (N < 1) {
            String err_msg = "Improper Argument: The system must contain at least 1 site.";
            throw new IllegalArgumentException(err_msg);
        }

        this.N = N;     // Store N of current instance
        this.system = new int[N][N][2];    // Allocate "system" instance var

        // Initialize all sites as BLOCKED.
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                this.system[row][col][0] = -2;
                this.system[row][col][1] = -2;
            }
        }
        this.num_open = 0;
    }

    //--------------------------------------------------------------------------
    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        index_check(row, col);

        int row_idx = row - 1;
        int col_idx = col - 1;
        int val_row = this.system[row_idx][col_idx][0];
        int val_col = this.system[row_idx][col_idx][1];

        // If the site is already open, do nothing
        if (val_row > -2 && val_col > -2) {
            return;
        }

        // If in top row, set root to virtual top site == {-1,-1}
        else if (row == 1) {
            this.system[row_idx][col_idx][0] = -1;
            this.system[row_idx][col_idx][1] = -1;
            this.num_open++;
            return;
        }

        // Check if the surrounding boxes are all CLOSED
        boolean[] surround_closed = check_surround_closed(row, col);
        boolean all_closed = (surround_closed[0] && surround_closed[1] && surround_closed[2]
                && surround_closed[3]);

        if (all_closed) {   // Set "root" to self.
            this.system[row_idx][col_idx][0] = row_idx;
            this.system[row_idx][col_idx][1] = col_idx;
            this.num_open++;
            return;
        }

        // Check surrounding boxes one-by-one

        // Box ABOVE is open - connect via union()
        if (this.system[row_idx - 1][col_idx][0] > -2 &&
                this.system[row_idx - 1][col_idx][1] > -2) {
            union(row_idx, col_idx, row_idx - 1, col_idx);
        }

        // Box right is open - connect via union()
        if (col < this.N && this.system[row_idx][col_idx + 1][0] > -2 &&
                this.system[row_idx][col_idx + 1][1] > -2) {

            union(row_idx, col_idx, row_idx - 1, col_idx);
        }


    }

    //--------------------------------------------------------------------------
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        index_check(row, col);

        int val_row = this.system[row - 1][col - 1][0];
        int val_col = this.system[row - 1][col - 1][1];

        return !(val_row == -2 && val_col == -2);

    }

    //--------------------------------------------------------------------------
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        index_check(row, col);

        int[] root = new int[2];
        root = find_root(row, col, 0);

        return (root[0] == -1 && root[1] == -1);
    }

    //--------------------------------------------------------------------------
    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.num_open;
    }

    // does the system percolate?
    public boolean percolates() {
        return false;
    }

    //--------------------------------------------------------------------------
    private void index_check(int row, int col) {
        if (row < 1 || col < 1 || row > this.N || col > this.N) {
            String err_msg = "Improper Argument: Site indeces must be between 1 and N.";
            throw new IllegalArgumentException(err_msg);
        }
    }

    //--------------------------------------------------------------------------

    private int[] find_root(int row_idx, int col_idx, int count) {

        //int cur_row = row, cur_col = col, count = 0;
        // while (val_row != -1 && val_col != -1 &&) {     }
        int val_row = this.system[row_idx][col_idx][0];
        int val_col = this.system[row_idx][col_idx][1];

        // If connected to virtual top
        if (val_row == -1 && val_col == -1) {
            int[] root_info = { -1, -1, count };
            return root_info;
        }
        // If top of local tree
        else if (row_idx == val_row && col_idx == val_col) {
            int[] root_info = { row_idx, col_idx, count };
            return root_info;
        }
        // Else traverse up tree to root
        else {
            return find_root(val_row, val_col, count + 1);
        }
    }

    //--------------------------------------------------------------------------
    private void union(int row1, int col1, int row2, int col2) {

    }

    //--------------------------------------------------------------------------
    private boolean[] check_surround_closed(int row, int col) {

        int row_idx = row - 1;
        int col_idx = col - 1;

        // Check top
        boolean top_closed = true;
        if (row > 1) {  // If not on top row
            top_closed = (this.system[row_idx - 1][col_idx][0] == -2 &&
                    this.system[row_idx - 1][col_idx][1] == -2);
        }

        // Check right
        boolean right_closed = true;
        if (col < this.N) { // If not on right column
            right_closed = (this.system[row_idx][col_idx + 1][0] == -2 &&
                    this.system[row_idx][col_idx + 1][1] == -2);
        }

        // Check bottom
        boolean bottom_closed = true;
        if (row < this.N) {
            bottom_closed = (this.system[row_idx + 1][col_idx][0] == -2 &&
                    this.system[row_idx + 1][col_idx][1] == -2);
        }

        // Check left
        boolean left_closed = true;
        if (col > 0) {
            left_closed = (this.system[row_idx][col_idx - 1][0] == -2 &&
                    this.system[row_idx][col_idx - 1][1] == -2);
        }

        boolean[] closed = { top_closed, right_closed, bottom_closed, right_closed };
        return closed;
    }

    //--------------------------------------------------------------------------
    public void print_table() {

        // Print Header Lines
        StdOut.println("\nCurrent Table:  ( X -> Closed, O -> Open)\n");
        String str1 = "  ";
        for (int i = 1; i <= this.N; i++) {
            str1 += String.format(" %2d", i);
        }
        StdOut.println(str1);

        // Generate and print table
        for (int i = 0; i < this.N; i++) {

            str1 = String.format("%2d ", i + 1);
            for (int j = 0; j < this.N; j++) {
                if (this.system[i][j][0] == -2 && this.system[i][j][1] == -2) {
                    str1 += " X ";
                }
                else {
                    str1 += " O ";
                }
            }
            StdOut.println(str1);
        }
    }


    //--------------------------------------------------------------------------
    // test client (optional)
    public static void main(String[] args) {

        StdOut.print("\nEnter N: ");
        int N = StdIn.readInt();

        Percolation2 test1 = new Percolation2(N);
        test1.print_table();

    }
}
