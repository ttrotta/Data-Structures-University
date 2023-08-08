package TDAMapeo;

import Exceptions.InvalidKeyException;
import Interfaces.Entry;
import Interfaces.PositionList;
import TDALista.ListaDoblementeEnlazada;

public class MapOpenAdressing<K,V> implements Map<K,V> {
	protected int size;
	protected int capacidad;
	protected Entry<K,V> disponible;
	protected Entry<K,V> [] buckets;

	@SuppressWarnings("unchecked")
	public MapOpenAdressing() {
		capacidad = 13;
		disponible = new Entrada<K,V>(null,null);
		buckets = (Entry<K,V> []) new Entry[capacidad];
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int balde = hashThisKey(key); int i = 0;
		boolean found = false; boolean foundNull = false;
		while(i < capacidad && !found && !foundNull) {
			if(buckets[balde] == null || buckets[balde] == disponible) 
				foundNull = true;
			else {
				if(buckets[balde].getKey().equals(key)) {
					found = true;
					toReturn = buckets[balde].getValue();
				}
			}
		balde = (balde+1) % capacidad;
		i++;
		}
		return toReturn;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int balde = hashThisKey(key); int i = 0;
		boolean found = false;
		while(i < capacidad && !found) {
			if(buckets[balde] == null || buckets[balde] == disponible) {
				buckets[balde] = new Entrada<K,V>(key,value);
				found = true;
				size++;
			}
			else
				if(buckets[balde].getKey().equals(key)) {
					toReturn = buckets[balde].getValue();
					((Entrada<K,V>)buckets[balde]).setValue(value);
					found = true;
				}
			balde = (balde+1) % capacidad;
			i++;
		}
		if(size/capacidad > 0.5) {
			reHash();
		}
		return toReturn;
	}
	
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		V toReturn = null;
		int balde = hashThisKey(key);
		int i = 0;
		boolean found = false; boolean foundNull = false;
		while(i < capacidad && !found && !foundNull) {
			if(buckets[balde] == null )
				foundNull = true;
			else
				if(buckets[balde] != disponible && buckets[balde].getKey().equals(key)) {
					found = true;
					toReturn = buckets[balde].getValue();
					buckets[balde] = disponible;
					size--;
				}
			balde = (balde+1)%capacidad;
			i++;
		}
		return toReturn;
	}

	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDoblementeEnlazada<K>();
		for(int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible)
				keys.addLast(buckets[i].getKey());
		}
		return keys;
	}

	public Iterable<V> values() {
		PositionList<V> values = new ListaDoblementeEnlazada<V>();
		for(int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible)
				values.addLast(buckets[i].getValue());
		}
		return values;
	}
	
	public Iterable<Entry<K, V>> entries() {
		PositionList<Entry<K,V>> entries = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(int i = 0; i < buckets.length; i++) {
			if(buckets[i] != null && buckets[i] != disponible)
				entries.addLast(buckets[i]);
		}
		return entries;
	}
	
	@SuppressWarnings("unchecked")
	private void reHash() {
		Iterable<Entry<K,V>> entries = this.entries();
		capacidad = nextPrime(capacidad*2);
		buckets = (Entrada<K,V> []) new Entrada[capacidad];
		size = 0;
		for(Entry<K,V> e : entries) {
			try {
				put(e.getKey(),e.getValue());
			} catch (InvalidKeyException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private int nextPrime(int num) {
		int toReturn = 0;
		boolean isPrime = false;
		while(!isPrime) {

			isPrime = true;
			for (int j = 2; (j<=Math.sqrt(num)) && (isPrime); j++) {
				if((num % j) == 0) {
					isPrime=false;
					num++;
				}
			}
			if(isPrime)
				toReturn= num;
		}
		return toReturn;
	}
	
	private int hashThisKey(K key) {
		return Math.abs(key.hashCode()) % capacidad; 
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es inválida.");
	}
}
