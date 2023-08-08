package TDADiccionario;

import Interfaces.Dictionary;
import Interfaces.Entry;
import Interfaces.PositionList;
import TDAMapeo.Entrada;
import Exceptions.*;
import TDALista.ListaDoblementeEnlazada;

public class DiccionarioOpenAdressing<K,V> implements Dictionary<K,V> {
	protected int size;
	protected int capacidad;
	protected Entry<K,V> disponible;
	protected Entry<K,V> [] buckets;
	
	@SuppressWarnings("unchecked")
	public DiccionarioOpenAdressing(){
		capacidad = 13;
		disponible = new Entrada<K,V>(null,null);
		buckets = (Entry<K,V> []) new Entry[capacidad];
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public Entry<K,V> find(K key) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> toReturn = null;
		int hash = hashThisKey(key);
		int i = 0;
		boolean found = false;
		while(i < capacidad && !found && buckets[hash] != null) {
			if(buckets[hash] != disponible && buckets[hash].getKey().equals(key)) {
				found = true;
				toReturn = buckets[hash];
			}
			hash = (hash+1) % capacidad;
			i++;
		}
		return toReturn;
	}
	
	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		checkKey(key);
		PositionList<Entry<K,V>> list = new ListaDoblementeEnlazada<Entry<K,V>>();
		int hash = hashThisKey(key); 
		int i = 0;
		while(i < capacidad && buckets[hash] != null) {
			if(buckets[hash] != disponible && buckets[hash].getKey().equals(key)) {
				list.addLast(buckets[hash]);
			}
			i++;
			hash = (hash+1)%capacidad;
		}
		return list;
	}
	
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> toReturn = new Entrada<K,V>(key,value);
		int hash = hashThisKey(key);
		int i = 0; 
		boolean found = false;
		while(i < capacidad && !found) {
			if(buckets[hash] == null || buckets[hash] == disponible) {
				found = true;
				buckets[hash] = toReturn;
				size++;
			}
			i++;
			hash = (hash+1) % capacidad;
		}
		if(size/capacidad > 0.5)
			reHash();
		return toReturn;
	}
	
	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		if(e == null || e == disponible) 
			throw new InvalidEntryException("La entrada es inválida.");
		Entry<K,V> toReturn = null;
		int hash = hashThisKey(e.getKey());
		int i = 0;
		boolean found = false;
		while(i < capacidad && !found && buckets != null) {
			if(buckets[hash] != disponible && buckets[hash].equals(e)) {
				found = true;
				toReturn = buckets[hash];
				buckets[hash] = disponible; // ó e 
				size--;
			}
			i++;
			hash = (hash+1) % capacidad;
		}
		if(!found) throw new InvalidEntryException("La entrada no está en el diccionario!");
		return toReturn;
	}

	public Iterable<Entry<K,V>> entries(){
		PositionList<Entry<K,V>> list = new ListaDoblementeEnlazada<Entry<K,V>>();
		for(Entry<K,V> e : buckets) {
			if(e != null && e != disponible)
				list.addLast(e);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private void reHash() {
		Iterable<Entry<K,V>> entries = entries();
		size = 0;
		capacidad = nextPrime(capacidad*2);
		buckets = (Entrada<K,V> []) new Entrada[capacidad];
		for(Entry<K,V> e : entries) {
			try {
				insert(e.getKey(),e.getValue());
			} catch (InvalidKeyException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private int hashThisKey(K key) {
		return Math.abs(key.hashCode()) % capacidad;
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es inválida");
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
}
