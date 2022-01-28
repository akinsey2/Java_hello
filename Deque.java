/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 27, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Node prev;
        private Item item;
        private Node next;

        // Constructor 1:If called without args, create null node
        private Node() {
            prev = null;
            item = null;
            next = null;
        }

        // Constructor 2: If called with arg, initialize reference to item
        private Node(Item i) {
            item = i;
        }
    }

    // ----------------------------------------
    // construct an empty deque
    public Deque() {
        first = new Node();
        last = first;
        // last = new Node();
        // first.next = last;
        // last.prev = first;
        size = 0;
    }

    // ----------------------------------------
    // is the deque empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // ----------------------------------------
    // return the number of items on the deque
    public int size() {
        return size;
    }

    // ----------------------------------------
    // add the item to the front
    public void addFirst(Item item) {

        // If the new data to be reference is empty
        if (item == null) {
            String errStr = "All deque items must be non-null.";
            throw new IllegalArgumentException(errStr);
        }
        // If the Deque has no first element
        else if (first.item == null) {
            first.item = item;
            size++;
        }
        // If the Deque already has a first element
        else {

            Node nowSecond = first;
            first = new Node(item);
            first.next = nowSecond;
            nowSecond.prev = first;
            size++;

            /* // Other Implementation
            Node nowSecond = new Node(first.item);
            nowSecond.next = first.next;
            nowSecond.prev = first;
            first.item = item;
            first.next = nowSecond;
            size++;
            */
        }
    }

    // ----------------------------------------
    // add the item to the back
    public void addLast(Item item) {

        if (item == null) {
            String errStr = "All deque items must be non-null.";
            throw new IllegalArgumentException(errStr);
        }
        // If the Deque has no last element
        else if (last.item == null) {
            last.item = item;
            size++;
        }
        // If the Deque already has a last element
        else {
            Node nowSecondToLast = last;
            last = new Node(item);
            nowSecondToLast.next = last;
            last.prev = nowSecondToLast;
            size++;

            /* // Other implementation
            Node nowSecondToLast = new Node(last.item);
            nowSecondToLast.prev = last.prev;
            nowSecondToLast.next = last;
            last.item = item;
            last.prev = nowSecondToLast;
            size++;
             */
        }

    }

    // ----------------------------------------
    // remove and return the item from the front
    public Item removeFirst() {

        // If Deque is empty
        if (size < 1) {
            String errStr = "Cannot remove from an empty deque.";
            throw new java.util.NoSuchElementException(errStr);
        }
        else if (size == 1) {
            Item toReturn = first.item;
            first.item = null;
            size--;
            return toReturn;
        }
        else {
            Item toReturn = first.item;
            first.item = null;
            first = first.next; // Make 2nd element new "first"
            first.prev = null;
            size--;
            return toReturn;
        }

        // // If there is no first item
        // if (frontSize < 1) {
        //     // And only the last item is present
        //     if (backSize == 1) {
        //         Item toReturn = first.next.item;
        //         first.next.item = null;
        //         backSize--;
        //         return toReturn;
        //     }
        //     // If there are multiple last items
        //     else {
        //
        //         Item toReturn = first.next.item;
        //         first.next.next.prev = first;   // Reconnect 2nd non-zero item's .prev to "first"
        //         /* // Optional "zero out" of middle element
        //         first.next.item = null;  */
        //         first.next = first.next.next;   // Reconnect first to 2nd non-zero item
        //         backSize--;
        //         return toReturn;        // Hope garbage collection does its job
        //     }
        // }
        //
        // // There is at least one first item
        // else {
        //     //
        //     if (frontSize == 1) {
        //         Item toReturn = first.item;
        //         first.item = null;
        //         frontSize--;
        //         return toReturn;
        //     }
        //     // There are multiple front items
        //     else {
        //         Item toReturn = first.item;
        //         first.item = null;
        //         first = first.next; // Make 2nd element new "first"
        //         first.prev = null;
        //         frontSize--;
        //         return toReturn;
        //     }
        // }
    }

    // ----------------------------------------
    // remove and return the item from the back
    public Item removeLast() {

        // If Deque is empty
        if (size == 0) {
            String errStr = "Cannot remove from an empty deque.";
            throw new java.util.NoSuchElementException(errStr);
        }
        else if (size == 1) {
            Item toReturn = last.item;
            last.item = null;
            size--;
            return toReturn;
        }
        else {
            Item toReturn = last.item;
            last.item = null;
            last = last.prev; // Make 2nd element new "first"
            last.next = null;
            size--;
            return toReturn;
        }

        // // If there is no last item
        // if (backSize < 1) {
        //     // If there is only one first item
        //     if (frontSize == 1) {
        //         Item toReturn = first.item;
        //         first.item = null;
        //         frontSize--;
        //         return toReturn;
        //     }
        //     // There are multiple first items
        //     else {
        //         Item toReturn = last.prev.item;
        //         last.prev.prev.next = last;     // Reconnect 2nd to last to last
        //         /* // Optional "zero out" of middle element
        //         last.prev.item = null; */
        //         last.prev = last.prev.prev;     // Reconnect last to 2nd to last
        //         frontSize--;
        //         return toReturn;
        //     }
        // }
        //
        // // If there is at least one last item
        // else {
        //     // Only one last item
        //     if (backSize == 1) {
        //         Item toReturn = last.item;
        //         last.item = null;
        //         backSize--;
        //         return toReturn;
        //     }
        //     // There are multiple last items
        //     else {
        //         Item toReturn = last.item;
        //         last.item = null;
        //         last = last.prev; // Make 2nd to last element new "last"
        //         last.next = null;
        //         backSize--;
        //         return toReturn;
        //     }
        // }
    }

    // ----------------------------------------
    // Definition of iterator
    private class DequeIterator implements Iterator<Item> {

        private Node current;

        private DequeIterator() {
            if (size > 0) {
                current = first;
            }
            else {
                current = null;
            }
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            String errStr = "\"remove()\" is not supported for the Deque class.";
            throw new UnsupportedOperationException(errStr);
        }

        public Item next() {
            if (current == null) {
                String errStr = "There is no next element for the Deque iterator to return.";
                throw new java.util.NoSuchElementException(errStr);
            }

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // ----------------------------------------
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // ----------------------------------------
    // ---------- Testing Methods -------------
    // ----------------------------------------

    private Deque<Integer> loadIntegers(Deque<Integer> dInt, int[] ints, int loadType) {

        switch (loadType) {
            case 1: // Load from front
                for (int i = ints.length - 1; i > -1; i--) {
                    dInt.addFirst(ints[i]);
                }
                break;

            case 2: // Load from back
                for (int i = 0; i < ints.length; i++) {
                    dInt.addLast(ints[i]);
                }
                break;

            case 3: // Load 50% from back, then from front
                int i;
                for (i = ints.length / 2; i < ints.length; i++) {
                    dInt.addLast(ints[i]);
                }
                for (i = (ints.length / 2 - 1); i > -1; i--) {
                    dInt.addFirst(ints[i]);
                }
                break;

            default:
                String errMsg = "Unknown loadIntegers loadType, not 1-3";
                throw new IllegalArgumentException(errMsg);
        }

        return dInt;
    }

    private Deque<String> loadStrings(Deque<String> dStr, String[] strings, int loadType) {

        switch (loadType) {
            case 1: // Load from front
                for (int i = strings.length - 1; i > -1; i--) {
                    dStr.addFirst(strings[i]);
                }
                break;

            case 2: // Load from back
                for (int i = 0; i < strings.length; i++) {
                    dStr.addLast(strings[i]);
                }
                break;

            case 3: // Load 50% from back, then from front
                int i;
                for (i = strings.length / 2; i < strings.length; i++) {
                    dStr.addLast(strings[i]);
                }
                for (i = (strings.length / 2 - 1); i > -1; i--) {
                    dStr.addFirst(strings[i]);
                }
                break;

            default:
                String errMsg = "Unknown loadStrings loadType, not 1-3";
                throw new IllegalArgumentException(errMsg);
        }

        return dStr;
    }


    // ----------------------------------------
    // ----------------------------------------
    // ----------------------------------------
    // unit testing (required)

    public static void main(String[] args) {

        int numElements = 100;
        if (args.length == 1) {
            numElements = Integer.parseInt(args[0]);
        }

        // Create Deques for testing
        Deque<Integer> dInt = new Deque<Integer>();
        Deque<String> dStr = new Deque<String>();
        boolean allPassed = true;
        int testInd = 0;

        // ----------------------------------------
        // isEmpty checks

        testInd++;
        String msg = String.format("\nTest %d: isEmpty from empty Deque<Int> :", testInd);
        StdOut.print(msg);
        if (dInt.isEmpty()) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        testInd++;
        msg = String.format("\nTest %d: isEmpty from empty Deque<String> :", testInd);
        StdOut.print(msg);
        if (dStr.isEmpty()) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        testInd++;

        dInt.addFirst(1);
        msg = String.format("\nTest %d: isEmpty from NON-empty Deque<Int> :", testInd);
        StdOut.print(msg);
        if (!dInt.isEmpty()) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }
        dInt.removeFirst();

        testInd++;

        String s = "a";
        dStr.addFirst(s);
        msg = String.format("\nTest %d: isEmpty from NON-empty Deque<String> :", testInd);
        StdOut.print(msg);
        if (!dStr.isEmpty()) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }
        dStr.removeFirst();

        // ----------------------------------------
        // Underflow : removing from Empty Deque

        // From Front
        Deque[] deques = { dInt, dStr };
        boolean err1 = false;

        // For each Deque, test underflow from from and back
        for (Deque d : deques) {

            testInd++;
            try {
                msg = String.format("\nTest %d: Remove First from Empty Deque :", testInd);
                StdOut.print(msg);
                err1 = false;
                d.removeFirst();
            }
            catch (java.util.NoSuchElementException e) {
                err1 = true;
                StdOut.print("SUCCESS");
            }
            finally {
                if (!err1) {
                    StdOut.print("FAILED");
                    allPassed = false;
                }
            }

            testInd++;

            // From Back
            try {
                msg = String.format("\nTest %d: Remove Last from Empty Deque :", testInd);
                StdOut.print(msg);
                err1 = false;
                d.removeLast();
            }
            catch (java.util.NoSuchElementException e) {
                err1 = true;
                StdOut.print("SUCCESS");
            }
            finally {
                if (!err1) {
                    StdOut.print("FAILED");
                    allPassed = false;
                }
            }
        }

        // ----------------------------------------
        // Insert null

        for (Deque d : deques) {

            testInd++;
            try {
                msg = String.format("\nTest %d: Insert Null at Front of Deque :", testInd);
                StdOut.print(msg);
                err1 = false;
                d.addFirst(null);
            }
            catch (IllegalArgumentException e) {
                err1 = true;
                StdOut.print("SUCCESS");
            }
            finally {
                if (!err1) {
                    StdOut.print("FAILED");
                    allPassed = false;
                }
            }

            testInd++;

            // From Back
            try {
                msg = String.format("\nTest %d: Insert Null at Back of Deque :", testInd);
                StdOut.print(msg);
                err1 = false;
                d.addLast(null);
            }
            catch (IllegalArgumentException e) {
                err1 = true;
                StdOut.print("SUCCESS");
            }
            finally {
                if (!err1) {
                    StdOut.print("FAILED");
                    allPassed = false;
                }
            }
        }

        // ----------------------------------------
        // Create test array of ints
        int[] testInts = new int[numElements];
        for (int i = 0; i < numElements; i++) {
            testInts[i] = i;
        }

        // Create test array of Strings
        char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', ' ' };

        String[] testStrArr = new String[numElements];

        for (int i = 0; i < numElements; i++) {
            StdRandom.shuffle(chars);
            testStrArr[i] = new String(chars);
        }


        // ----------------------------------------
        // Test size()

        testInd++;
        msg = String.format("\nTest %d: size() == 0 : ", testInd);
        StdOut.print(msg);
        if (dInt.size() == 0) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        dInt = dInt.loadIntegers(dInt, testInts, 1);

        testInd++;
        msg = String.format("\nTest %d: size() == %d : ", testInd, numElements);
        StdOut.print(msg);
        if (dInt.size() == numElements) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Front load - Front unload

        // Integers
        testInd++;
        msg = String.format("\n\nTest %d: Ints Front Load, Front Unload:\n", testInd);
        StdOut.print(msg);
        boolean success = true;
        int current;

        for (int i = 0; i < testInts.length; i++) {
            current = dInt.removeFirst();
            StdOut.print("[" + testInts[i] + "," + current + "]");
            if (testInts[i] != current) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // Strings
        testInd++;
        msg = String.format("\n\nTest %d: Strings Front Load, Front Unload:\n", testInd);
        StdOut.print(msg);
        success = true;
        String currentStr;

        dStr = dStr.loadStrings(dStr, testStrArr, 1);
        for (int i = 0; i < testStrArr.length; i++) {
            currentStr = dStr.removeFirst();
            StdOut.print("[" + testStrArr[i] + "," + currentStr + "]");
            if (testStrArr[i] != currentStr) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Front load - Rear unload

        // Integers
        testInd++;
        msg = String.format("\n\nTest %d: Ints Front Load, Rear Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dInt = dInt.loadIntegers(dInt, testInts, 1);
        // removeLast will reverse order, so increment from end backwards
        for (int i = testInts.length - 1; i > -1; i--) {
            current = dInt.removeLast();
            StdOut.print("[" + testInts[i] + "," + current + "]");
            if (testInts[i] != current) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // Strings
        testInd++;
        msg = String.format("\n\nTest %d: Strings Front Load, Rear Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dStr = dStr.loadStrings(dStr, testStrArr, 1);
        // removeLast will reverse order, so increment from end backwards
        for (int i = testStrArr.length - 1; i > -1; i--) {
            currentStr = dStr.removeLast();
            StdOut.print("[" + testStrArr[i] + "," + currentStr + "]");
            if (testStrArr[i] != currentStr) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Rear load - Front Unload

        // Integers
        testInd++;
        msg = String.format("\n\nTest %d: Ints Rear Load, Front Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dInt = dInt.loadIntegers(dInt, testInts, 2);
        for (int i = 0; i < testInts.length; i++) {
            current = dInt.removeFirst();
            StdOut.print("[" + testInts[i] + "," + current + "]");
            if (testInts[i] != current) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // Strings
        testInd++;
        msg = String.format("\n\nTest %d: Strings Rear Load, Front Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dStr = dStr.loadStrings(dStr, testStrArr, 2);
        for (int i = 0; i < testStrArr.length; i++) {
            currentStr = dStr.removeFirst();
            StdOut.print("[" + testStrArr[i] + "," + currentStr + "]");
            if (testStrArr[i] != currentStr) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Rear load - Rear Unload

        // Integers
        testInd++;
        msg = String.format("\n\nTest %d: Ints Rear Load, Rear Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dInt = dInt.loadIntegers(dInt, testInts, 2);
        // removeLast will reverse order, so increment from end backwards
        for (int i = testInts.length - 1; i > -1; i--) {
            current = dInt.removeLast();
            StdOut.print("[" + testInts[i] + "," + current + "]");
            if (testInts[i] != current) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // Strings
        testInd++;
        msg = String.format("\n\nTest %d: Strings Rear Load, Rear Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dStr = dStr.loadStrings(dStr, testStrArr, 2);
        // removeLast will reverse order, so increment from end backwards
        for (int i = testStrArr.length - 1; i > -1; i--) {
            currentStr = dStr.removeLast();
            StdOut.print("[" + testStrArr[i] + "," + currentStr + "]");
            if (testStrArr[i] != currentStr) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Front and rear load
        dInt = new Deque<Integer>();

        // with Front unload
        testInd++;
        // int[] seq = { 5, 4, 3, 2, 1, 6, 7, 8, 9, 10 };
        msg = String
                .format("\n\nTest %d: Front and Rear Load, Front unload\n", testInd);
        StdOut.print(msg);
        success = true;
        dInt = dInt.loadIntegers(dInt, testInts, 3);
        // for (int i = 0; i < 5; i++) {
        //     d.addFirst(seq[i]);
        //     StdOut.print(" [" + d.size() + "] " + seq[i]);
        // }
        // StdOut.print("\nAdded in Last:");
        // for (int i = 5; i < 10; i++) {
        //     d.addLast(seq[i]);
        //     StdOut.print(" [" + d.size() + "] " + seq[i]);
        // }
        for (int i = 0; i < testInts.length; i++) {
            current = dInt.removeFirst();
            StdOut.print("[" + testInts[i] + ',' + current + ']');
            if (testInts[i] != current) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // with Rear Unload
        testInd++;
        dInt = new Deque<Integer>();
        msg = String
                .format("\n\nTest %d:Front and Rear Load, Rear unload\n", testInd);
        StdOut.print(msg);
        success = true;

        dInt = dInt.loadIntegers(dInt, testInts, 3);

        for (int i = testInts.length - 1; i > -1; i--) {
            current = dInt.removeLast();
            StdOut.print("[" + testInts[i] + ',' + current + ']');
            if (testInts[i] != current) {
                success = false;
                break;
            }
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Iterator Test
        // ----------------------------------------
        // Front load - Front unload

        // Integers
        testInd++;
        msg = String
                .format("\n\nTest %d: Ints Front Load, Iterator (Front) Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dInt = dInt.loadIntegers(dInt, testInts, 1);

        Iterator<Integer> dIntIterator = dInt.iterator();
        int i = 0;

        while (dIntIterator.hasNext()) {
            current = dIntIterator.next();
            StdOut.print("[" + testInts[i] + "," + current + "]");
            if (testInts[i] != current) {
                success = false;
                break;
            }
            i++;
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        // Strings
        testInd++;
        msg = String.format("\n\nTest %d: Strings Front Load, Iterator (Front) Unload:\n", testInd);
        StdOut.print(msg);
        success = true;

        dStr = dStr.loadStrings(dStr, testStrArr, 1);

        Iterator<String> dStrIterator = dStr.iterator();
        i = 0;
        while (dStrIterator.hasNext()) {
            currentStr = dStrIterator.next();
            StdOut.print("[" + testStrArr[i] + "," + currentStr + "]");
            if (testStrArr[i] != currentStr) {
                success = false;
            }
            i++;
        }
        if (success) {
            StdOut.print("\nSUCCESS");
        }
        else {
            StdOut.print("\nFAILED");
            allPassed = false;
        }

        if (allPassed) {
            StdOut.println("\n\n    ALL TESTS PASSED!!");
        }
        else {
            StdOut.println("\n\n    One or more tests FAILED!");
        }
    }
}
