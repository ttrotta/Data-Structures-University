package TDAMapeo;

import Exceptions.InvalidKeyException;
import Interfaces.Entry;
import TDALista.ListaDE;

// También conocido como Map Separating Chaining en GT
public class MapConHashAbierto<K,V> implements Map<K,V> {
	protected Map<K,V> [] buckets;
	protected int size;
	protected int N;
	
	@SuppressWarnings("unchecked")
	public MapConHashAbierto(int N) {
		size = 11;
		buckets = (Map<K,V> []) new MapeoConLista[N];
		for(int i=0; i < N; i++) {
			buckets[i] = new MapeoConLista<K,V>();
		}
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		int p = hashThisKey(key);
		return buckets[p].get(key);
	}
	
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		int p = hashThisKey(key);
		V toReturn = buckets[p].put(key,value);
		if(toReturn == null)
			size++;
		return toReturn;
	}
	
	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		int p = hashThisKey(key);
		V toReturn = buckets[p].remove(key);
		if(toReturn == null) 
			size++;
		return toReturn;
	}
	
	public Iterable<K> keys() {
		ListaDE<K> keys = new ListaDE<K>();
		for (Map<K,V> m : buckets) {
            for (K key : m.keys()) {
                keys.addLast(key);
            }
        }
        return keys;
	}
	
	public Iterable<V> values() {
		ListaDE<V> values = new ListaDE<V>();
		for (Map<K,V> m : buckets) {
            for (V value : m.values()) {
                values.addLast(value);
            }
        }
        return values;
	}
	
	public Iterable<Entry<K, V>> entries() {
        ListaDE<Entry<K, V>> entries = new ListaDE<Entry<K, V>>();
        for (Map<K,V> m : buckets) {
            for (Entry<K,V> e : m.entries()) {
                entries.addLast(e);
            }
        }
        return entries;
    }
	
	private int hashThisKey(K key) {
	    return Math.abs(key.hashCode() % N);
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es nula.");
	}
}
