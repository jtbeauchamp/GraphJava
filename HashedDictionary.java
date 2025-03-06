//
//      Name:           Beauchamp, Joshua
//      Project:        5
//      Due:            12/08/23
//      Course:         cs-2400-02-f23
//
//      Description:
//                  The HashedDictionary class contains the implementations of the Dictionary interface.
//					It has an inner class for the entries that are to be assigned indexes within the 
//					hashed dictionary itself. It also contains a private heler classes to assist with
//					the implementations, and iterator class to create iterator object to traverse
//					through the keys and values of the dictionary
//

import java.util.Iterator;
import java.util.NoSuchElementException;
public class HashedDictionary <K, V> implements DictionaryInterface<K, V> {
	private int collisionCount =0;
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 1361; 
	
	private Entry<K, V>[] hashTable;	//Creates the table of keys and their corresponding values
	private int tableSize;
	private int locationsUsed;
	private boolean integrityOK = true;
	protected final Entry <K, V> AVAILABLE = new Entry<>(null, null);	//Creates an available location in the dictionary
	
	//Default dictionary constructor
	public HashedDictionary() {
		this(DEFAULT_CAPACITY);
	}
	
	//Paramtereized constructor for the dictionary
	public HashedDictionary(int tableSize) {
		int primeSize = getNextPrime(tableSize);	//gets the next best prime number of the given size
		
		hashTable = new Entry[primeSize];	//Creates the dictionary with the new size
		numberOfEntries =0;
		locationsUsed=0;
	}

	//Class of entries consisted of a "Key" and "Value" within the dictionary
	protected final class Entry<K, V> {
		private K key;
		private V value;
		private boolean inTable;
		
		public Entry(K searchKey, V dataValue) {
			key = searchKey;
			value = dataValue;
			inTable = true;
		}
		
		public K getKey() {
			return key;
		}
		
		public void setKey(K key) {
			this.key = key;
		}
		public V getValue() {
			return value;
		}
		
		public void setValue(V newValue) {
			value = newValue;
		}
	}

	//linearProbe method
	private int linearProbe(int index, K key) {
        boolean found = false;
        int availableIndex = -1;

        while (!found && (hashTable[index] != null)) {
            if (hashTable[index] != AVAILABLE) {
                if (key.equals(hashTable[index].getKey())) {
                    found = true; //Key found
                } else { //follow probe sequence
                    index = (index + 1) % hashTable.length;
                    if (hashTable[index] != null && key != null && !key.equals(hashTable[index].getKey())) {
                    }
                }
            } else {
                if (availableIndex == -1) {
                    availableIndex = index;
                }

                index = (index + 1) % hashTable.length;
            }
        }

        if (found || (availableIndex == -1))
            return index; //Index of either key or null
        else
            return availableIndex; //index of an available location
    }


	    

	
	//used to give the location of the Index
	private int locateIndex(K key) {
		int index = getHashIndex(key);
		
		while (index < tableSize && hashTable[index] != null && !key.equals(hashTable[index].getKey())) {
			index = (index + 1) % tableSize;
		}
		return index;
	}
	
	//this method sets the value
	private void setValue(int index, V value) {
		checkIntegrity();
        if (index >= 0 && index < tableSize && hashTable[index] != null) {
            hashTable[index].setValue(value);
        } else {
            throw new IllegalArgumentException("Invalid index or entry is null.");
        }
	}
	
	//checks to see if there is available space in the hashtable
	private boolean isAvailable(int index) {
	    return (hashTable[index] == null) || (hashTable[index] == AVAILABLE);
	}

	
	//method used to rehash if the hashindex was already in use
	private void rehash() {
	    Entry<K, V>[] oldTable = hashTable;
	    int oldSize = tableSize;

	    int newSize = getNextPrime(2 * oldSize);
	    //checkSize(newSize);

	    @SuppressWarnings("unchecked")
	    Entry<K, V>[] newTable = (Entry<K, V>[]) new Entry[newSize];
	    hashTable = newTable;
	    numberOfEntries = 0;
	    tableSize = newSize;

	    for (int i = 0; i < oldSize; i++) {
	        if ((oldTable[i] != null) && (oldTable[i] != AVAILABLE)) {
	            add(oldTable[i].getKey(), oldTable[i].getValue());
	        }
	    }
	}

	//Irrelevant in this project
	private int getCollisionCount() {
		int collisionCount = 0;

		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] != null && hashTable[i] != AVAILABLE) {
				int index = getHashIndex(hashTable[i].getKey());
				if (index != i) {
					collisionCount++;
				} else {
					int newIndex = linearProbe(index, hashTable[i].getKey());
					if (newIndex != index) {
						collisionCount++;
					}
				}
			}
		}

		return collisionCount;
	} 
	    
	//Checks the integrity of the hashed dictionary
	private void checkIntegrity() {
		if (!integrityOK) {
			throw new SecurityException("HashedDictionary object is corrupt.");
		}
	}
	
	/**Obtains the HashIndex from a given key
	 * @param key The key object that will be used to obtain a hash index
	 * @return the hash index to be used in the dictionary */
	private int getHashIndex(K key) {
		int index = key.hashCode() % hashTable.length;
		
		if(index < 0) {
			index+=hashTable.length;
		}
		
		return index;
		}
	    
	/**Obtains the next best prime after the given number\
	 * @param num The number being used to find the next prime after it
	 * @return The next best prime number */
	private int getNextPrime(int num) {
		if (num <= 1) return 2;

		int nextPrime = num;
		boolean found = false;

		while (!found) {
			nextPrime++;

			if (isPrime(nextPrime)) {
				found = true;
			}
		}

		return nextPrime;
	}
	    
	/**Checks if the given number is a prime number
	 * @param num The number being used to check if it is a prime
	 * @return True if the number is a prime, else false */
	private boolean isPrime(int num) {
		if (num <= 1) return false;
		if (num <= 3) return true;
		if (num % 2 == 0 || num % 3 == 0) return false;

		for (int i = 5; i * i <= num; i += 6) {
			if (num % i == 0 || num % (i + 2) == 0) return false;
		}

		return true;
	}

	/**Adds a new entry into the dictionary. Should the key already exist
     * in the dictionary, the corresponding value is replaced with the new entry
     * @param key An object search key of the new entry
     * @param value An object associated with the search key
     * @return The corresponding value being replaced. If there is no value, then returns null */
	 @Override
	public V add(K key, V value) {
		checkIntegrity();
		if (key == null || value == null) {
			throw new IllegalArgumentException("Cannot add null to a dictionary.");
		} else {
			V oldValue;

			int index = getHashIndex(key);
			assert (index >= 0) && (index < hashTable.length);

			if ((hashTable[index] == null) || (hashTable[index] == AVAILABLE)) {
				hashTable[index] = new Entry(key, value);
				numberOfEntries++;
				oldValue = null;
			} else {
				index = linearProbe(index, key); 
				hashTable[index] = new Entry(key, value);
				numberOfEntries++;

				oldValue = null;
			}
			return oldValue;
		}
	}
	
	/**Retrieves an object value associated with the given object search key
     * @param key An object search key that is the key of the object value to be returned
     * @return The object value that is associated with the object key. If there is no entry,
     * then returns null */
	@Override
	public V getValue(K key) {
		checkIntegrity();
		V result = null;
		int index = getHashIndex(key);

		if((hashTable[index] != null) && (hashTable[index] != AVAILABLE))
			result = hashTable[index].getValue();
		return result;
	}
	
	/**Creates an interator object to traverse the search keys in the dictionary
     * @returns An iterator that provides sequential access to the search keys in the dictionary */
	@Override
	public Iterator<K> getKeyIterator() {
		return new KeyIterator();
	}

	//Class for an object that will iterate through the keys
	private class KeyIterator implements Iterator<K> {
	    private int currentIndex = 0;

		//Finds the next index that is occupied to start iterate
	    public KeyIterator() {
	        findNextIndex();
	    }

		/**Checks if the iterator has an entry in the next index
		 * @return True if there is a next entry, else false */
	    @Override
	    public boolean hasNext() {
	        return currentIndex < hashTable.length;
	    }

		/**Retrieves the entry that is next in the iteration
		 * @return The key that is the next entry in the iteration cycle */
	    @Override
	    public K next() {
	        if (!hasNext()) {
	            throw new NoSuchElementException();
	        }

	        K key = hashTable[currentIndex].getKey();
	        findNextIndex();
	        return key;
	    }

		//Finds the next index that has an entry
	    private void findNextIndex() {
	        currentIndex++;
	        while (currentIndex < hashTable.length && (hashTable[currentIndex] == null || hashTable[currentIndex] == AVAILABLE)) {
	            currentIndex++;
	        }
	    }
	}

	//Class for an object that will iterate through the values
	private class ValueIterator implements Iterator<V> {
	    private int currentIndex = 0;

		//Creates an iterator object to find the next entry in the dictionary
	    public ValueIterator() {
	        findNextIndex();
	    }

		/**Checks if the iterator has an entry in the next index
		 * @return True if there is a next entry, else false */
	    @Override
	    public boolean hasNext() {
	        return currentIndex < hashTable.length;
	    }

		/**Retrieves the entry that is next in the iteration
		 * @return The key that is the next entry in the iteration cycle */
	    @Override
	    public V next() {
	        if (!hasNext()) {
	            throw new NoSuchElementException();
	        }

	        V value = hashTable[currentIndex].getValue();
	        findNextIndex();
	        return value;
	    }

		//Finds the next index that has an entry
	    private void findNextIndex() {
	        currentIndex++;
	        while (currentIndex < hashTable.length && (hashTable[currentIndex] == null || hashTable[currentIndex] == AVAILABLE)) {
	            currentIndex++;
	        }
	    }
	}

	/**Creates an iterator object to traverse the values in the dictionary
     * @return An iterator that provides sequential access to the values in the dictionary */
	@Override
	public Iterator<V> getValueIterator() {
		return new ValueIterator();
	}

	
	/**Determines whether a specific entry is in the dictionary
     * @param key An object search key of the desired entry
     * @return True if the key is associated with an entry in the dictionary */
	@Override
	public boolean contains(K key) {
		checkIntegrity();
		return getValue(key) != null;
	}
		
	/**Determines if the dictionary is empty
     * @return True if the dictionary is empty */
	@Override
	public boolean isEmpty() {
		//Returning "not implemented"
		throw new UnsupportedOperationException("Not implemented");
	}
		
	/**Gets the size of the dictionary
     * @return Number of entrys, or key-value pairs, currently in the dictionary */
	@Override
	public int getSize() {
		//Returning not implemented
		throw new UnsupportedOperationException("Not implemented");
	}

	/**Removes all entries from the dictionary */
	@Override
	public void clear() {
		throw new UnsupportedOperationException("Not implemented");
	}
	    
	/**Removes a specific entry from the dictionary using an object search key
	 * @param key An object search key that is the key of the entry to be removed
	 * @return The object value that was associated with the object search key. If there
	 * is no value, then returns null */
	@Override
	public V remove(K key) {
		// Return "not implemented" for remove
		throw new UnsupportedOperationException("Not implemented");
	}
}