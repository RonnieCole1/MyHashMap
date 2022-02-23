/*
 * This is the driver class.
 */
package myhashmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class MyHashMapDriver {

    public static void main(String[] args) throws FileNotFoundException {
        //debuggingFunctions();
        completeAnalysis();
        //readWriteData();

    }

    public static void debuggingFunctions() {
        MyHashTable<Integer> myTable = new MyHashTable<Integer>(7);
        ArrayList<Integer> addedKeys = new ArrayList<Integer>();
        System.out.println("TESTING ADDING ELEMENTS");
        for (int i = 0; i < 5; i++) {
            int key_value = (int) (Math.random() * 1000000);
            if (myTable.insert(key_value, key_value)) {
                addedKeys.add(key_value);
            } else {
                i--; //Don't count the insert if key already in table
            }
        }
        System.out.println(myTable);

        System.out.println("\nTESTING FINDING ELEMENTS");
        for (int i = 0; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " found: " + myTable.find(key, key));
        }
        System.out.println(myTable);

        System.out.println("\nTESTING REMOVING ELEMENTS");
        for (int i = 0; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " removed: " + myTable.remove(key));
        }
        System.out.println("Expect to see Tombstones for removed values");
        System.out.println(myTable);
        System.out.println("\nTESTING FINDING REMOVED ELEMENTS - FALSE");
        System.out.println("\nTESTING FINDING REMOVED ELEMENTS");
        for (int i = 0; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " found: " + myTable.find(key, key));
        }
        System.out.println(myTable);

        System.out.println("\nTESTING FINDING VALID ELEMENTS AFTER REMOVAL");
        for (int i = 1; i < addedKeys.size(); i += 2) {
            int key = addedKeys.get(i);
            System.out.println("The value " + key + " found: " + myTable.find(key, key));
        }
        System.out.println(myTable);

    }

    public static void completeAnalysis() {
        MyHashTable<Integer> myTable = new MyHashTable<Integer>(1009);
        double[] collisionArray = new double[10];
        int j = 0;
        for (int i = 0; i < 1009; i++) {
            int key_value = (int) (Math.random() * 1000000);
            if(i == 100 || i == 201 || i == 302 || i == 403 || i == 504 || i ==605 || i == 706 || i == 807 || i == 908 || i == 1007){
                int[] avCollisionArray = new int[20];
                double n = 0;
                for (int index = 0; index < avCollisionArray.length; index++) {
                    if (myTable.insert(key_value, key_value)) {
                        avCollisionArray[index] = myTable.collisionsForThisInsert;
                        myTable.remove(key_value); 
                    } else {
                        index--;
                    }
                }
                for (int index = 0; index < avCollisionArray.length; index++) {
                    n = n + avCollisionArray[index];
                }
                n = n/20;
                collisionArray[j] = n;
                j++;
            }
            else if (! myTable.insert(key_value, key_value)) {
                i--; //Don't count the insert if key already in table
            }
            if ( j == 10) {
                break;
            }
        }
        for (int index = 0; index < collisionArray.length; index++) {
            System.out.println(collisionArray[index]);
        }
    }
    
    /**
     * This part is unnecessary for all levels other than A. It is a brief
     * example of how to read and write from a file.
     * @throws FileNotFoundException 
     */
    public static void readWriteData() throws FileNotFoundException{
        PrintWriter outFile = new PrintWriter("output.txt");
        //outFile == System.out
        System.out.println("Hello World");
        outFile.println("Hello World");
        outFile.close();
        
        
        File file = new File("output.txt");
        Scanner input = new Scanner(file);
        
        int value = input.nextInt();
        
    }

}
