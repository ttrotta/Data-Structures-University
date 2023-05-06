package TDADiccionario;

import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import Interfaces.Dictionary;
import Interfaces.Entry;
import Interfaces.PositionList;
import TDALista.ListaDE;

public class DiccionarioConHashAbierto<K,V> implements Dictionary<K,V> {
	protected static final float factorDeCarga = 0.5f;
	protected PositionList<Entry<K,V>> [] buckets; 
	protected int n; // cantidad de entradas
	protected int N; // tamaño del arreglo (num primo)

	@SuppressWarnings("unchecked")
	public DiccionarioConHashAbierto() {
		N = 11;
		n = 0;
		buckets = (Dictionary<K,V>[]) new PositionList[N];
		for(int i=0; i < N; i++) {
			buckets[i] = new ListaDE<Entry<K,V>>();
		}
	}
	
	public int size() {
		return n;
	}
	
	public boolean isEmpty() {
		return n == 0;
	}
	
	public Entry<K,V> find(K key) throws InvalidKeyException {
		checkKey(key);
		int p = hashThisKey(key);
		return buckets[p].find(key);
	}
	
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		int p = hashThisKey(key);
		return buckets[p].findAll(key);
	}
	
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		if((float)(n/N) >= factorDeCarga) {
			reHash();
		}
		int p = hashThisKey(key);	n++;
		return buckets[p].insert(key, value);
	}
	
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		checkEntry(e);
		int p = hashThisKey(e.getKey()); n--;
		return buckets[p].remove(e);
	}
	
	public Iterable<Entry<K,V>> entries() {
		PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();
		for(Dictionary<K,V> d : buckets) {
			for(Entry<K,V> e : d.entries()) {
				entries.addLast(e);
			}
		}
		return entries;
	}
	
	@SuppressWarnings("unchecked")
	private void reHash() throws InvalidKeyException {
		Iterable<Entry<K,V>> entries = this.entries();
		N = nextPrime(N*2);
		buckets = (Dictionary<K,V> []) new DiccionarioConLista[N];
		for(int i=0; i < N; i++) {
			buckets[i] = new DiccionarioConLista<K,V>();
		}
		n = 0;
		for(Entry<K,V> e : entries) {
			this.insert(e.getKey(), e.getValue());
		}
	}
	
	private int hashThisKey(K key) {
	    return Math.abs(key.hashCode() % N);
	}
	
	private int nextPrime(int N) {
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
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es nula.");
	} 
	
	private void checkEntry(Entry<K,V> e) throws InvalidEntryException {
		if(e == null) 
			throw new InvalidEntryException("La entrada es nula.");
	}
}
