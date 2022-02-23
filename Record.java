/**
 Each record holds an integer key and a value of any type
 (hence the need for a template).  The value type must be
 printable using toString or this code will fail.

 In this implementation, the value is stored directly in
 the record.  If the value is large, it would be more
 efficient to have the records hold references to the values.
 In this case, care would need to be taken to ensure
 that deep copies are made upon assignment, and that
 any values allocated are properly deleted in the Record
 destructor.
 
 */
 
package myhashmap;

// There are three types of records in a closed hash:
// Normal records, empty records, and tombstones
enum RecordType { normalRecord, emptyRecord, tombstone };


public class Record<T> {

    private int key;
    private T value;
    private RecordType type;

    public Record() {
        key = 0;
        type = RecordType.emptyRecord;
    }

    public Record(int newkey, T newValue) {
        this.key = newkey;
        this.value = newValue;
        type = RecordType.normalRecord;
    }

    // Convert a record to a tombstone
    public void deleteRecord() {
        type = RecordType.tombstone;
    }

    // Get the integer key of a record
    public int getKey() {
        return key;
    }

    // Get the value of a record
    public T getValue() {
        return value;
    }

    // Check if a record is empty
    public boolean isEmpty() {
        return (type == RecordType.emptyRecord);
    }

    // Check if a record is a normal record
    public boolean isNormal() {
        return (type == RecordType.normalRecord);
    }

    // Check if a record is a tombstone
    public boolean isTombstone() {
        return (type == RecordType.tombstone);
    }

    // Overload the toString operator for printing records
    @Override
    public String toString() {
        if (isTombstone()) {
            return "<<Tombstone>>";
        } else if (isEmpty()) {
            return "<<Empty>>";
        } else {
            return "Key: " + key + ", Value: " + value;
        }
    }

}
