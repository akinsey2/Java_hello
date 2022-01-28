/* *****************************************************************************
 *  Name:              Adam Kinsey
 *  Coursera User ID:  akinsey2@gmail.com
 *  Last modified:     December 27, 2021
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int arrSize, numElements;
    private Item[] randQueue;

    // ----------------------------------------
    // Constructor for an empty randomized queue
    public RandomizedQueue() {
        randQueue = (Item[]) new Object[1];
        randQueue[0] = null;
        arrSize = 1;
        numElements = 0;
    }

    // ----------------------------------------
    // is the randomized queue empty?
    public boolean isEmpty() {
        return numElements == 0;
    }

    // ----------------------------------------
    // return the number of items on the randomized queue
    public int size() {
        return numElements;
    }

    // ----------------------------------------
    // add the item
    public void enqueue(Item item) {

        if (item == null) {
            String errMsg = "Cannot enqueue NULL item.";
            throw new IllegalArgumentException(errMsg);
        }
        // If first item
        else if (numElements == 0) {
            randQueue[0] = item;
            numElements++;
        }
        // If there is room
        else if (numElements < arrSize) {
            randQueue[numElements] = item;
            numElements++;
        }
        // If there is no room, resize array larger
        else {
            Item[] oldRandQueue = randQueue;
            randQueue = (Item[]) new Object[arrSize * 2];
            System.arraycopy(oldRandQueue, 0, randQueue, 0, oldRandQueue.length);
            arrSize = arrSize * 2;
            randQueue[numElements] = item;
            numElements++;
        }
    }

    // ----------------------------------------
    // remove and return a random item
    public Item dequeue() {

        if (numElements == 1) {
            Item toReturn = randQueue[0];
            randQueue[0] = null;
            numElements--;
            return toReturn;
        }

        else if (numElements > 1) {
            int randIndex = StdRandom.uniform(numElements);
            Item toReturn = randQueue[randIndex];
            randQueue[randIndex] =
                    randQueue[numElements
                            - 1];   // Replace removed {item} with {last item in array}
            randQueue[numElements - 1] = null;
            numElements--;

            // if the array now needs to be resized smaller
            if (numElements <= arrSize / 4) {

                Item[] oldRandQueue = randQueue;
                randQueue = (Item[]) new Object[arrSize / 2];
                System.arraycopy(oldRandQueue, 0, randQueue, 0, numElements);
                arrSize = arrSize / 2;
            }

            return toReturn;
        }
        else {
            String errMsg = "Queue is Empty. Cannot return dequeue().";
            throw new java.util.NoSuchElementException(errMsg);
        }
    }

    // ----------------------------------------
    // return a random item (but do not remove it)
    public Item sample() {

        if (!this.isEmpty()) {
            int randIndex = StdRandom.uniform(numElements);
            return randQueue[randIndex];
        }
        else {
            String errMsg = "Queue is Empty. Cannot return sample().";
            throw new java.util.NoSuchElementException(errMsg);
        }
    }

    // ----------------------------------------
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private int[] randIndexes;
        private int iterIndex;


        // Constructor
        private RandomizedQueueIterator() {
            randIndexes = StdRandom.permutation(numElements);
            iterIndex = 0;
        }

        public void remove() {
            String errStr = "\"remove()\" is not supported for the RandomizedDequeIterator class.";
            throw new UnsupportedOperationException(errStr);
        }

        public boolean hasNext() {

            // !! Assuming that the queue still has the same length.
            return iterIndex < numElements;

            // // If the Queue still has the same length
            // if (numElements == randIndexes.length) {
            //     return (iterIndex < randIndexes.length);
            // }
            // // If the Queue is now shorter
            // else if (numElements < randIndexes.length) {
            //     return (iterIndex < numElements);
            // }
            // // If the Queue is now longer
            // else {
            //     return (iterIndex < numElements);
            // }
        }

        public Item next() {

            // !! Assuming the Queue still has the same length.
            if (iterIndex < randIndexes.length && randQueue[randIndexes[iterIndex]] != null) {

                Item toReturn = randQueue[randIndexes[iterIndex]];
                iterIndex++;
                return toReturn;
            }
            else {
                String errStr
                        = "There are no more items to return from RandomizedQueue instance.";
                throw new java.util.NoSuchElementException(errStr);
            }
        }
    }

    // ----------------------------------------
    // ----------------------------------------
    // ----------------------------------------
    // unit testing (required)
    public static void main(String[] args) {

        int numTestElements = 100;
        if (args.length == 1) {
            numTestElements = Integer.parseInt(args[0]);
        }

        int testInd = 0;
        String msg;
        boolean allPassed = true;

        // Create randQueues to test.
        RandomizedQueue<Integer> randQueueInt = new RandomizedQueue<>();
        RandomizedQueue<String> randQueueStr = new RandomizedQueue<>();

        // ----------------------------------------
        // Test isEmpty()

        // Int
        testInd++;
        msg = String.format("\nTest %d: Test Int.isEmpty() : ", testInd);
        StdOut.print(msg);

        if (randQueueInt.isEmpty()) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // Str
        testInd++;
        msg = String.format("\nTest %d: Test Str.isEmpty() : ", testInd);
        StdOut.print(msg);

        if (randQueueStr.isEmpty()) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Error upon enqueue(null)

        // Int
        testInd++;
        msg = String.format("\nTest %d: Verify Exception for Int.enqueue(null) : ", testInd);
        StdOut.print(msg);
        boolean err1 = false;
        try {
            randQueueInt.enqueue(null);
        }
        catch (IllegalArgumentException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // Str
        testInd++;
        msg = String.format("\nTest %d: Verify Exception for Str.enqueue(null) : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            randQueueStr.enqueue(null);
        }
        catch (IllegalArgumentException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Error upon sample() with empty Queue
        testInd++;
        msg = String.format("\nTest %d: Verify Exception for sample() of EMPTY Queue : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            randQueueInt.sample();
        }
        catch (java.util.NoSuchElementException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Error upon dequeue() with empty Queue
        testInd++;
        msg = String.format("\nTest %d: Verify Exception for dequeue() of EMPTY Queue : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            randQueueInt.dequeue();
        }
        catch (java.util.NoSuchElementException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Create test data

        int[] testIntArr = new int[numTestElements];
        for (int i = 0; i < numTestElements; i++) {
            testIntArr[i] = i;
        }

        String[] testStrArr = new String[numTestElements];
        char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', ' ' };
        for (int i = 0; i < numTestElements; i++) {
            StdRandom.shuffle(chars);
            testStrArr[i] = new String(chars);
        }

        // ----------------------------------------
        // Test size()
        testInd++;
        msg = String.format("\nTest %d: size() == 0 : ", testInd);
        StdOut.print(msg);

        // Test zero size
        if (randQueueInt.size() == 0 && randQueueStr.size() == 0) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // Load randQueues
        for (int i = 0; i < numTestElements; i++) {
            randQueueInt.enqueue(testIntArr[i]);
            randQueueStr.enqueue(testStrArr[i]);
        }

        testInd++;
        msg = String.format("\nTest %d: size() == %d : ", testInd, numTestElements);
        StdOut.print(msg);
        // Test numTestElements size
        if (randQueueInt.size() == numTestElements && randQueueStr.size() == numTestElements) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Test sample()

        // Int
        testInd++;
        msg = String.format("\nTest %d: sample(), %d times : ", testInd, numTestElements);
        StdOut.print(msg);
        boolean allFound = true;
        for (int i = 0; i < numTestElements; i++) {
            boolean foundInt = false;
            int sample = randQueueInt.sample();
            for (int j = 0; j < testIntArr.length; j++) {
                if (testIntArr[j] == sample) {
                    foundInt = true;
                    break;
                }
            }
            if (!foundInt) {
                allFound = false;
                break;
            }
        }
        if (allFound) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Test dequeue()

        testInd++;
        msg = String.format("\n\nTest %d: Test dequeue :\n", testInd);
        StdOut.print(msg);
        msg = String.format("randQueueInt.size() = %d", randQueueInt.size());
        StdOut.println(msg);
        msg = String.format("randQueueStr.size() = %d", randQueueStr.size());
        StdOut.println(msg);
        StdOut.print("\ntestIntArr   = [");
        for (int i : testIntArr) {
            msg = String.format(" %2d,", i);
            StdOut.print(msg);
        }
        StdOut.print(" ]\ndequeuedInts = [");

        // Dequeue all elements and print
        int[] dequeuedInts = new int[numTestElements];
        String[] dequeuedStrs = new String[numTestElements];

        for (int i = 0; i < numTestElements; i++) {
            dequeuedInts[i] = randQueueInt.dequeue();
            dequeuedStrs[i] = randQueueStr.dequeue();
            msg = String.format(" %2d,", dequeuedInts[i]);
            StdOut.print(msg);
        }
        StdOut.print(" ]\n");
        msg = String.format("randQueueInt.size() = %d", randQueueInt.size());
        StdOut.println(msg);

        // Test Equality of testIntArr and dequeued values
        boolean intsEqual = true;
        for (int i : testIntArr) {

            boolean foundInt = false;
            for (int j = 0; j < dequeuedInts.length; j++) {
                if (i == dequeuedInts[j]) {
                    foundInt = true;
                    break;
                }
            }
            if (!foundInt) {
                intsEqual = false;
                break;
            }
        }
        if (intsEqual) {
            StdOut.println("Int dequeue: SUCCESS");
        }
        else {
            StdOut.println("Int dequeue: FAILED");
            allPassed = false;
        }

        // Print test and dequeued Strings
        StdOut.print("\ntestStrArr = [");
        for (String s : testStrArr) {
            StdOut.print(s + ",");
        }
        StdOut.print(" ]\ndequeuedStrs = [");
        for (String s : dequeuedStrs) {
            StdOut.print(s + ",");
        }
        msg = String.format("randQueueStr.size() = %d", randQueueStr.size());
        StdOut.println("]\n" + msg);

        boolean strsEqual = true;
        for (String s : testStrArr) {

            boolean foundStr = false;
            for (int j = 0; j < dequeuedStrs.length; j++) {
                if (s.equals(dequeuedStrs[j])) {
                    foundStr = true;
                    break;
                }
            }
            if (!foundStr) {
                strsEqual = false;
                break;
            }
        }
        if (strsEqual) {
            StdOut.println("Str dequeue: SUCCESS");
        }
        else {
            StdOut.println("Str dequeue: FAILED");
            allPassed = false;
        }

        // Test dequeue() after randQueue is empty.

        // Int
        testInd++;
        msg = String.format("\nTest %d: Test Int dequeue underflow error : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            randQueueInt.dequeue();
        }
        catch (java.util.NoSuchElementException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // Str
        testInd++;
        msg = String.format("\nTest %d: Str dequeue underflow error : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            randQueueStr.dequeue();
        }
        catch (java.util.NoSuchElementException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Test Iterator

        // Int

        // Load randQueues
        for (int i = 0; i < numTestElements; i++) {
            randQueueInt.enqueue(testIntArr[i]);
            randQueueStr.enqueue(testStrArr[i]);
        }

        Iterator<Integer> intIterator1 = randQueueInt.iterator();
        Iterator<Integer> intIterator2 = randQueueInt.iterator();

        int[] intIterResult1 = new int[numTestElements];
        int i = 0;
        while (intIterator1.hasNext()) {
            intIterResult1[i] = intIterator1.next();
            i++;
        }

        int[] intIterResult2 = new int[numTestElements];
        i = 0;
        while (intIterator2.hasNext()) {
            intIterResult2[i] = intIterator2.next();
            i++;
        }

        testInd++;
        msg = String
                .format("\nTest %d: Int Iterators Return Size = %d : ", testInd, numTestElements);
        StdOut.print(msg);
        if (intIterResult1.length == numTestElements && intIterResult2.length == numTestElements) {
            StdOut.print("SUCCESS");
        }
        else {
            StdOut.print("FAILURE");
            allPassed = false;
        }

        // Test "Set" Equality
        testInd++;
        msg = String.format("\n\nTest %d: Int Iterators Return Matched Sets : \n", testInd);
        StdOut.print(msg);
        // Test contents equality
        int[] numCount1 = new int[intIterResult1.length];
        int[] numCount2 = new int[intIterResult2.length];

        for (i = 0; i < numTestElements; i++) {
            numCount1[intIterResult1[i]]++;
            numCount2[intIterResult2[i]]++;
        }
        boolean match = true;
        for (i = 0; i < numCount1.length; i++) {
            StdOut.print("[" + i + ": " + numCount1[i] + "," + numCount2[i] + "]");
            if (numCount1[i] != numCount2[i]) {
                match = false;
                break;
            }
        }
        if (match) {
            StdOut.print("\nSUCCESS : the contents match");
        }
        else {
            StdOut.print("\nFAILURE : the contents DO NOT match.");
            allPassed = false;
        }

        // Test Perfect Equality
        testInd++;
        msg = String.format("\n\nTest %d: Int Iterators Return NOT identical :\n", testInd);
        StdOut.print(msg);
        match = true;
        for (i = 0; i < intIterResult1.length; i++) {
            if (intIterResult1[i] != intIterResult2[i]) {
                StdOut.print("[" + i + ": " + intIterResult1[i] + "," + intIterResult2[i] + "]");
                match = false;
                break;
            }
        }
        if (!match) {
            StdOut.print("\nSUCCESS : the contents are not identical (randomized)\n");
        }
        else {
            StdOut.print("\nFAILURE : the contents are identical. (NOT randomized)\n");
            allPassed = false;
        }

        // ----------------------------------------
        // Error upon {iterator}.next() with no more items
        testInd++;
        msg = String.format("\nTest %d: Exception if iterator.next() overflows : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            intIterator1.next();
        }
        catch (java.util.NoSuchElementException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
            allPassed = false;
        }

        // ----------------------------------------
        // Error upon calling {iterator}.remove()
        testInd++;
        msg = String.format("\nTest %d: Exception if iterator.remove() is called : ", testInd);
        StdOut.print(msg);
        err1 = false;
        try {
            intIterator1.remove();
        }
        catch (UnsupportedOperationException e) {
            err1 = true;
            StdOut.print("SUCCESS");
        }
        if (!err1) {
            StdOut.print("FAILED");
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

