/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 15, 2021
 **************************************************************************** */

public class HelloGoodbye {
    public static void main(String[] args) {

        if (args.length == 2) {
            System.out.println("Hello " + args[0] + " and " + args[1] + ".");
            System.out.println("Goodbye " + args[1] + " and " + args[0] + ".");
        }
        else {
            System.out.println("Incorrect number of inputs. Please supply 2 arguments.");
        }
    }
}
