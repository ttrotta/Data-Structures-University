package TDAColaCP;

import java.util.Comparator;

import Exceptions.*;
import Interfaces.Entry;
import Interfaces.PriorityQueue;

/**
 * Clase Heap que implementa una Cola con Prioridad.
 * @author Thiago Trotta
 * @author Cipriano Martin
 * @param <K> Parámetro formal de tipo de la clave.
 * @param <V> Parámetro formal de tipo del valor.
 */
public class Heap<K,V> implements PriorityQueue<K,V> {
	protected Entrada<K,V> [] elems;
	protected Comparator<K> comp;
	protected int size;
	
	/**
	 * Crea una nueva instancia de la clase Heap.
	 * @param maxElems Tamaño tope del arreglo.
	 * @param comp Comparador. 
	 */
	@SuppressWarnings("unchecked")
	public Heap(int maxElems, Comparator<K> comp) {
		elems = (Entrada<K,V> []) new Entrada[maxElems];
		this.comp = comp;
		size = 0;
	}
	
	/**
	 * Crea una nueva instancia de la clase Heap
	 * donde el arreglo será de tamaño 1500.
	 * @param comp Comparador.
	 */
	public Heap(Comparator<K> comp){
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
			throw new EmptyPriorityQueueException("La cola está vacía.");
		return elems[1];
	}

	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		if(size+1 >= elems.length)
			reSize();
		checkKey(key);
		Entrada<K,V> entrie = new Entrada<K,V>(key,value); 
		elems[++size] = entrie;
		int i = size;
		boolean seguir = true;
		while(i>1 && seguir) {
			Entrada<K,V> elemActual = elems[i]; 
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
		int m = 1;
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
					if(tieneHijoDerecho) {
						if(comp.compare(elems[hi].getKey(), elems[hd].getKey()) < 0)
							m = hi;
						else
							m = hd;
					}
					else {
						m = hi;
					}
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
		return entrie;
	} 
	
	/**
	 * Chequea si la clave es válida.
	 * @param key Clave de la entrada a chequear.
	 * @throws InvalidKeyException si la clave es inválida.
	 */
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La llave es inválida.");
	}
	
	/**
	 * Copia las entradas actuales del arreglo en uno nuevo, con el 
	 * doble del tamaño actual.
	 */
	private void reSize() {
		@SuppressWarnings("unchecked")
		Entrada<K,V> [] newArray = (Entrada<K,V> []) new Entrada[elems.length*2];
		int i = 0; 
		for(Entrada<K,V> e : elems) {
			newArray[i++] = e; 
		}
		elems = newArray;
	}
	
	/**
	 * Intercambia las entradas de dos posiciones. 
	 * @param pos1 Primera posición.
	 * @param pos2 Segunda posición.
	 */
	private void swap(int pos1, int pos2) {
		Entrada<K,V> aux = elems[pos1];
		elems[pos1] = elems[pos2];
		elems[pos2] = aux;
	}
	
	// ------------- Clase Anidada Entrada -----------------
	/**
	 * Clase Entrada anidada en la Clase Heap
	 * @author Thiago Trotta
	 * @author Cipriano Martin
	 * @param <K> Parámetro formal de tipo de la clave.
	 * @param <V> Parámetro formal de tipo del valor.
	 */
	@SuppressWarnings("hiding")
	private class Entrada<K,V> implements Entry<K,V> {
		private K key;
		private V value;
		
		/**
		 * Crea una nueva instancia de la clase Entrada.
		 * @param key La clave de la entrada.
		 * @param value El valor de la entrada.
		 */
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
	// -------- Fin de la clase Anidada Entrada ------------
}
