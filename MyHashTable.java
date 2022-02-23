package myhashmap;


public class MyHashTable<T> {

    private final int HASH_TABLE_SIZE; //Capacity of the table
    private Record<T>[] hashMap;
    private int currentSize; //Number of values in the table
    public int collisionsForThisInsert; //This variable is strictly for analysis purposes
    private int[] rngArray; //Array of random numbers
    
    public MyHashTable(int size) {
        this.HASH_TABLE_SIZE = size;
        hashMap = new Record[HASH_TABLE_SIZE];
        currentSize = 0;
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            Record<T> r = new Record<T>();
            hashMap[i] = r;
        }
        rngArray = new int[HASH_TABLE_SIZE];
        for (int i = 0; i < HASH_TABLE_SIZE; i++) {
            int r = (int)(Math.random() * 1000);
            rngArray[i] = r;
        }
    }
   
    /*Finds an element with a certain key and stores it in the value passed*/
    public boolean find(int key, T value) {
        int homeIndex = hashFunction(key);
        int keyPrime = homeIndex;
        int collisions = 0;
        while ( collisions < HASH_TABLE_SIZE ) {
            if ( hashMap[keyPrime].isTombstone()) {
                keyPrime = probeFunction(keyPrime, collisions);
                collisions++;
            } else if ( hashMap[keyPrime].isEmpty()) {
                break;
            } else if ( hashMap[keyPrime].getKey() == key) {
                value = hashMap[keyPrime].getValue();
                return true;
            } else {
                keyPrime = probeFunction(keyPrime, collisions);
                collisions++;  
            }
        }
        return false;
    }

    /*Inserts the key/value into the hashtable*/
    public boolean insert(int key, T value) {
        int homeIndex = hashFunction(key);
        int keyPrime = homeIndex;
        collisionsForThisInsert = 0;
        Record insertRecord = new Record(key, value);
        int collisions = 0;
        if (currentSize == HASH_TABLE_SIZE ) {
            System.out.println("Array is full");
            return false;       
        }
        if (find(key,value)) {
            return false;       
        }
        while ( hashMap[keyPrime].isNormal()) {
            keyPrime = probeFunction(keyPrime, collisions);
            collisionsForThisInsert++;
        }
        hashMap[keyPrime] = insertRecord;
        currentSize++;
        return true;
    }

    /*Kills a table key and returns the associated value*/
    public T remove(int key) {
        int collisions = 0;
        int homeIndex = hashFunction(key);
        int keyPrime = homeIndex;
        while ( collisions < HASH_TABLE_SIZE ) {
            if ( hashMap[keyPrime].getKey() == key && hashMap[keyPrime].isNormal() ) {
                T val = hashMap[keyPrime].getValue();
                hashMap[keyPrime].deleteRecord();
                currentSize--;
                return val; 
            } else if  ( hashMap[keyPrime].isTombstone() || hashMap[keyPrime].isNormal() ) {
                keyPrime = probeFunction(keyPrime, collisions);
                collisions++;
            } else if ( hashMap[keyPrime].isEmpty() ) {
                break;
            } 
        }
        return null;
    }

    /*Returns the load factor for the hash*/
    public double alpha() {
        return this.currentSize / this.HASH_TABLE_SIZE;
    }

    /*Hash function for finding the home position*/
    private int hashFunction(int key) {
        return key%HASH_TABLE_SIZE;
    }

    /*The result of probing is returned with the new slot's position*/
    private int probeFunction(int homeIndex, int collisions) {
        ++homeIndex;
        return homeIndex%HASH_TABLE_SIZE;
    }
    
//    /*Hash function for finding the home position*/
//    private int hashFunction(int key) {
//        int k = 0;
//        int i = 0;
//        int n = 0;
//        int keyPrime = 0;
//        while ( key != 0 ) {
//            k += i*128;
//            n = key%( (int) Math.pow( 10, i+1) );
//            key = key - n;
//            keyPrime = keyPrime + n*k;
//            i++;
//        }
//        keyPrime = Math.abs(keyPrime);
//        return keyPrime%HASH_TABLE_SIZE;
//    }
//    
//    /*The result of probing is returned with the new slot's position*/
//    private int probeFunction(int homeIndex, int collisions) {
//        homeIndex = homeIndex + rngArray[homeIndex];
//        return homeIndex%HASH_TABLE_SIZE;
//    }

    public String toString() {
        String table = "";
        for (int i = 0; i < this.HASH_TABLE_SIZE; i++) {
            table += i + ". " + hashMap[i].toString() + "\n";
        }
        return table;
    }

}
