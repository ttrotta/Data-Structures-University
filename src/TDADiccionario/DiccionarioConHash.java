package TDADiccionario;

import java.util.Iterator;

import Exceptions.*;
import Interfaces.Dictionary;
import Interfaces.Entry;
import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.ListaDE;

public class DiccionarioConHash<K,V> implements Dictionary<K,V> {
	protected static final float factorDeCarga = 0.5f;
	protected PositionList<Entry<K,V>> [] buckets; 
	protected int n; // cantidad de entradas
	protected int N; // tamaño del arreglo (num primo)

	@SuppressWarnings("unchecked")
	public DiccionarioConHash() {
		N = 13;
		buckets = (PositionList<Entry<K,V>>[]) new ListaDE[N];
		for(int i=0; i < N; i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
		}
		n = 0;
	}
	
	public int size() {
		return n;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}

	public Entry<K,V> find(K key) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> toReturn = null; 
		int p = hashThisKey(key);
		Iterator<Entry<K,V>> it = buckets[p].iterator();
		while(it.hasNext() && toReturn == null) {
			Entry<K,V> e = it.next();
			if(e.getKey().equals(key)) 
				toReturn  = e;
		}
		return toReturn;
	}
	
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> l = new ListaDE<Entry<K,V>>();
		int p = hashThisKey(key);
		for(Entry<K,V> e : buckets[p]) {
			if(e.getKey().equals(key))
				l.addLast(e);
		}
		return l;
	}
	
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> toReturn = new Entrada<K,V>(key, value); 
		int p = hashThisKey(key);
		buckets[p].addLast(toReturn);
		n++;
 		if((float)(n/N) >= factorDeCarga) {
			reHash();
		}
		return toReturn;
	}
	
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		checkEntry(e);
		Entry<K,V> toReturn = null;
		Position<Entry<K,V>> cursor = null, last = null;
		try {
			PositionList<Entry<K,V>> bucket = buckets[hashThisKey(e.getKey())];
			if(!bucket.isEmpty()) {
				cursor = bucket.first();
				last = bucket.last();
			}
			while(cursor != null && toReturn == null) {
				if(e.equals(cursor.element())) {
					toReturn = bucket.remove(cursor);
					n--;
				}
				else
					cursor = cursor == last ? null : bucket.next(cursor);
			}
		} catch(EmptyListException | InvalidPositionException | BoundaryViolationException e1) {
			e1.printStackTrace();
		}
		if(toReturn == null)
			throw new InvalidEntryException("La entrada no se encuentra en el diccionario.");
		return toReturn;
	}
	
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();
		for(PositionList<Entry<K,V>> list : buckets) {
			for(Entry<K,V> e : list)
				entries.addLast(e);
		}
		return entries;
	}
	
	/**
	 * 
	 * @throws InvalidKeyException
	 */
	@SuppressWarnings("unchecked")
	private void reHash() throws InvalidKeyException {
		Iterable<Entry<K,V>> entries = this.entries();
		N = nextPrimo(N*2);
		buckets = (PositionList<Entry<K,V>> []) new ListaDE[N];
		for(int i=0; i < N; i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
		}
		n = 0;
		for(Entry<K,V> e : entries) {
			this.insert(e.getKey(), e.getValue());
		}
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	private int hashThisKey(K key) {
	    return Math.abs(key.hashCode() % N);
	}
	
	/**
	 * 
	 * @param N
	 * @return
	 */
	private int nextPrimo(int N) {
		int toReturn = 0;
		boolean isPrime = false;
		while(!isPrime) {
			isPrime = true;
			for(int j = 2; j <= Math.sqrt(N) && isPrime; j++) {
				if((N % j) == 0) {
					isPrime = false;
					N++;
				}
			}
			if(isPrime) {
				toReturn = N;
			}
		}
		return toReturn;
	}
	
	
	/**
	 * 
	 * @param key
	 * @throws InvalidKeyException
	 */
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es nula.");
	} 
	
	/**
	 * 
	 * @param e
	 * @throws InvalidEntryException
	 */
	private void checkEntry(Entry<K,V> e) throws InvalidEntryException {
		if(e == null) 
			throw new InvalidEntryException("La entrada es nula.");
	}
}
