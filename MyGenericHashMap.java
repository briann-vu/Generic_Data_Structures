/*
 * Brian Vu
 * CSc 210
 * 
 * MyHashMap class represents a hash map object backed using an array list 
 * of linked lists of hash node objects. This data structure is generic
 * and maps any object type K as the keys to any type V object as the 
 * values. It uses a hash function to compute an index for the key 
 * value pair and from that stores them into buckets (linked lists in 
 * this case). 
 * 
 * Fields: 
 * public ArrayList<HashNode<K, V>> map : the array list that backs
 * the hash map
 * public Set<K> keySet : a set holding all active keys in the map
 * private static int NUMBUCKETS : a constant field to represent the
 * max amount of buckets to store keys/value pairs.	
 * private int size : an int to represent the size of the hash map
 * (how many key value pairs)
 * 
 * imports:
 * java.util.ArrayList for the array list that backs the hash map
 * java.util.HashSet for the node objects that store the key/value 
 * pairs for the linked list representing the buckets.
 * java.util.Set for the key set
 * 
 * Methods (descriptions of each method is on top of the 
 * method below):
 * 
 * MyHashMap()
 * MyHashMap(MyHashMap<K,V> copy)
 * clear()
 * containsKey(K key)
 * containsValue(V value)
 * get(K key)
 * isEmpty()
 * keySet()
 * put(K key, V value)
 * putHelper(K key, V value)
 * remove(K key) 
 * size() 
 * printTable() 
 * conflictHelper(HashNode<K,V> node) 
 * keysHelper(HashNode<K,V> node)
 * hash(K key)
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MyHashMap<K,V> {
	
	public ArrayList<HashNode<K, V>> map;
	public Set<K> keySet;
	private static int NUMBUCKETS = 8;
	private int size;

	/*
	 * Constructor initializes the size of the hash map to 0 and
	 * an array list filled with null's. It also initializes a new
	 * hash set for the key set.
	 */
	public MyHashMap() {
		this.map = new ArrayList<HashNode<K, V>>();
		for (int x = 0; x < NUMBUCKETS; x++) {
			this.map.add(null);
		}
		this.keySet = new HashSet<K>();
		this.size = 0;
	}
	
	/*
	 * Copy constructor takes in a hash map object to copy its array
	 * list, size and key set.
	 */
	public MyHashMap(MyHashMap<K,V> copy) {
		this.map = copy.map;
		this.size = copy.size;
		this.keySet = copy.keySet;
	}
	
	/*
	 * Clears the hash map by clearing the array list and filling it 
	 * with null's again. size is also reset to 0.
	 */
	public void clear() {
		this.map.clear();
		for (int x = 0; x < NUMBUCKETS; x++) {
			this.map.add(null);
		}
		this.size = 0;
		
	}
	
	/*
	 * Takes in a key type K and checks if the map contains it; if it
	 * does then true is returned, false otherwise.
	 */
	public boolean containsKey(K key) {
		for (HashNode<K, V> node : this.map) {
			while (node != null) {
				if (node.getKey().equals(key)) {
					return true;
				} else {
					node = node.getNext();
				}
			}
		}
		return false;
	}
	
	/*
	 * Takes in a value type V and checks if the map contains it; if it
	 * does then true is returned, false otherwise.
	 */
	public boolean containsValue(V value) {
		for (HashNode<K, V> node : this.map) {
			if (node == (null)) {
				continue;
			}
			while (node != null) {
				if (node.getValue().equals(value)) {
					return true;
				} else {
					node = node.getNext();
				}
			}
		}
		return false;
	}
	
	/*
	 * Takes in a key type K and checks if the map even contains it first,
	 * then returns the value that pairs with it. If it isn't in the map, 
	 * null is returned.
	 */
	public V get(K key) {
		if (containsKey(key) == false) {
			return null;
		} 
		for (HashNode<K, V> node : this.map) {
			while(node != null) {
				if (node.getKey().equals(key)) {
					return node.getValue();
				}
				node = node.getNext();
			}
		}
		return null;
	}
	
	/*
	 * Checks if the hash map is empty or not (via size), if so, true is 
	 * returned, otherwise false.
	 */
	public boolean isEmpty() {
		if (this.size == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Returns a set of all active keys in the hash map.
	 */
	public Set<K> keySet() {
		return this.keySet;
	}
	
	/*
	 * Takes in a key and value pair (types K, V) and creates a hash code
	 * for the key and checks to see if the key already exists in the map,
	 * if so, putHelper is called to over write the previous key and value
	 * with the new ones. If the new hash code already exists, the key and
	 * value pairs are chained to the previous key and value(pairs) in the 
	 * bucket (linked list) corresponding to the hash code index. The value
	 * being added is also returned and the size is increased by 1.
	 */
	public V put(K key, V value) {
		HashNode<K, V> toPut 
		= new HashNode<K, V>(key, value);
		int hash = hash(key);
		
		if (map.get(hash) != null) {
			if (containsKey(key) == true) {
				putHelper(key, value);
			} else {
				toPut.setNext(map.get(hash));
				this.map.set(hash, toPut);
				keySet.add(key);
				this.size++;
			}
		} else {
			this.map.add(hash, toPut);
			keySet.add(key);
			this.size++;
		}
		return value;
	}
	
	/*
	 * Takes in a new key and value pair (types K, V) to be added into the 
	 * hash map and over writes the existing pair with the same key.
	 */
	private void putHelper(K key, V value) { //override
		for (HashNode<K, V> node : this.map) {
			while (node != null) {
				if (node.getKey().equals(key)) {
					node.setValue(value);
				}
				node = node.getNext();
			}
		}
	}
	
	/*
	 * Takes in a type K key and removes it and its corresponding value.
	 * The key is removed from the key set, the size is reduced by 1 and
	 * the node holding the key/value pair in its bucket is set to null.
	 * If the key doesn't even exist, null is returned, otherwise the value
	 * from the key is returned.
	 */
	public V remove(K key) {
		for(HashNode<K,V> node : this.map) {
			while (node != null) {
				if (node.getKey().equals(key)) {
					if(node.getNext() != null) {
						if (node.getNext().getNext() != null) {
							node.setNext(node.getNext().getNext());
						}
					} else {
						node.setKey(null);
					}
					this.size--;
					this.keySet.remove(key);
					return node.getValue();
				}
				node = node.getNext();
			}
		}
		return null;
	}
	
	/*
	 * returns the size of the hash map.
	 */
	public int size() {
		return this.size;
		
	}
	/*
	 * Prints a table of all of the buckets of the hash map. For each bucket
	 * it displays the index, amount of collisions and the keys in that bucket.
	 * At the end there is also the total amount of collisions that is 
	 * displayed. Two helper functions: conflictHelper and keysHelper are used
	 * to retrieve the number of collisions and set of keys.
	 */
	public void printTable() {
		int conflictCount = 0;
		for (int i = 0; i < NUMBUCKETS; i++) {
			int conflictAmount = conflictHelper(this.map.get(i));
			conflictCount += conflictAmount;
			String bucket = "Index " + i + ": (" + conflictAmount + " conflicts), " 
			+ keysHelper(this.map.get(i));
			System.out.println(bucket);
		}
		System.out.println("Total # of conflicts: " + conflictCount);
	}
	
	/*
	 * Takes in a Hash Node object of types K, V and Counts and returns the 
	 * amount (int) of collisions in each bucket. 
	 */
	private int conflictHelper(HashNode<K,V> node) {
		if (keysHelper(node).size() == 0) {
			return 0;
		} else {
			return keysHelper(node).size() - 1;
		}
	}
	
	/*
	 * Takes in a Hash Node object of types K, V to get and return a set of keys 
	 * from each bucket
	 */
	private Set<K> keysHelper(HashNode<K,V> node) {
		Set<K> keys = new HashSet<K>();
		while(node != null) {
			if (node.getKey() == null) {
				node = node.getNext();
				continue;
			} else {
			keys.add(node.getKey());
			node = node.getNext();
			}
			
		}
		return keys;
	}

	/*
	 * Hash function generates and returns a hash code for a key (type K) that
	 * is taken in.
	 */
	private int hash(K key) {
		int hashCode = key.hashCode();
		int index = hashCode % NUMBUCKETS ;
		return Math.abs(index);
		}

}
