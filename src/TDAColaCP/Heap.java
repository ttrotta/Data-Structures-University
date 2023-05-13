package TDAColaCP;

import Exceptions.*;
import Interfaces.Entry;
import Interfaces.PriorityQueue;

public class Heap<K,V> implements PriorityQueue<K,V> {
	protected Entrada<K,V> [] elems;
	protected Comparador<K> comp;
	protected int size;
	
	@SuppressWarnings("unchecked")
	public Heap(int maxElems, Comparador<K> comp) {
		elems = (Entrada<K,V> []) new Entrada[maxElems];
		this.comp = comp;
		size = 0;
	}
	
	public Heap(Comparador<K> comp){
        this(1500, comp);
    }
	
	public int size() {
		return size;
	}


	public boolean isEmpty() {
		return size == 0;
	}

	public Entry<K,V> min() throws EmptyPriorityQueueException {
		if(isEmpty())
			throw new EmptyPriorityQueueException("El árbol está vacío.");
		return elems[1];
	}

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if(size+1 >= elems.length)
			reSize();
		checkKey(key);
		Entrada<K,V> entrie = new Entrada<K,V>(key,value); // Preguntar si pongo Entry
		elems[++size] = entrie;
		int i = size;
		boolean seguir = true;
		
		while(i>1 && seguir) {
			Entrada<K,V> elemActual = elems[i]; //i es el ultimo elem agregado
			Entrada<K,V> elemPadre = elems[i/2];
			if(comp.compare(elemActual.getKey(),elemPadre.getKey()) < 0) {
				swap(i,i/2);
				i /= 2;
			}
			else
				seguir = false;
		}
		return entrie;
	}
	
	public Entry<K,V> removeMin() throws EmptyPriorityQueueException {
		Entry<K,V> entrie = min();
		if(size == 1) {
			elems[1] = null;
			size = 0;
		}
		else {
			elems[1] = elems[size];
			elems[size] = null;
			size--;
			int i = 1;
			boolean seguir = true;
			while(seguir) {
				int hi = i*2;
				int hd = i*2 + 1;
				boolean tieneHijoIzquierdo = hi <= size();
				boolean tieneHijoDerecho = hd <= size();
				if(!tieneHijoIzquierdo)
					seguir = false;
				else {
					int m;
					if(tieneHijoDerecho) {
						if(comp.compare(elems[hi].getKey(), elems[hd].getKey()) < 0)
							m = hi;
						else
							m = hd;
					}
					else {
						m = hi;
					}
					if(comp.compare(elems[i].getKey(), elems[m].getKey()) > 0) {
						swap(i,m);
						i = m;
					}
					else {
						seguir = false;
					}
				}
			}
		}
		return entrie;
	} 
	
	/**
	 * 
	 * @param key
	 * @throws InvalidKeyException
	 */
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La llave es incorrecta.");
	}
	
	/**
	 * 
	 */
	private void reSize() {
		@SuppressWarnings("unchecked")
		Entrada<K,V> [] newArray = (Entrada<K,V> []) new Entrada[elems.length*2];
		int i = 0; 
		for(Entrada<K,V> e : elems) {
			newArray[i++] = e; // uso i++ y no ++i puesto que ya doy por hecho que elems tenia nulo en la posición 0
		}
		elems = newArray;
	}
	
	/**
	 * 
	 * @param pos1
	 * @param pos2
	 */
	private void swap(int pos1, int pos2) {
		Entrada<K,V> aux = elems[pos1];
		elems[pos1] = elems[pos2];
		elems[pos2] = aux;
	}
	
	// ------------- Clase Privada Entrada -----------------
	@SuppressWarnings("hiding")
	private class Entrada<K,V> implements Entry<K,V> {
		private K key;
		private V value;
		
		public Entrada(K key, V value) {
			this.key = key;
			this.value = value;
		}
		
		public K getKey() {
			return key;
		}
		
		public V getValue() {
			return value;
		}
		
		public String toString() {
			return "("+key+","+value+")";
		}
	}
}
