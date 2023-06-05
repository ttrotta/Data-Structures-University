package MapeoConABB;

import java.util.Comparator;

import Exceptions.*;
import TDALista.*;
import Interfaces.PositionList;

public class MapeoConABB<K extends Comparable<K>,V> implements MapABB<K,V> {
	protected int size;
	protected Comparator<K> comp;
	protected NodoABB<EntryABB<K,V>> root;
	
	public MapeoConABB(Comparator<K> c) {
		comp = c;
		size = 0;
		root = new NodoABB<EntryABB<K,V>>(null,null);
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
		NodoABB<EntryABB<K,V>> n = buscar(key);
		if(n.element() != null)
			toReturn = n.element().getValue();
		return toReturn;
	}
	
	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		V old = null;
		NodoABB<EntryABB<K,V>> n = buscar(key);
		EntradaABB<K,V> en = new EntradaABB<K,V>(key,value);
		if(n.element() != null) {
			old = n.element().getValue();
			n.setElement(en);
		}
		else {
			n.setElement(en);
			n.setLeft(new NodoABB<EntryABB<K,V>>(null,n));
			n.setRight(new NodoABB<EntryABB<K,V>>(null,n));
			size++;
		}
		return old;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		NodoABB<EntryABB<K,V>> n = buscar(key);
		V removed = null;
		if(n.element() != null) {
			removed = n.element().getValue();
			eliminar(n);
			size--;
		}
		return removed;
	}

	public Iterable<K> keys() {
		PositionList<K> list = new ListaDoblementeEnlazada<K>();
		if(!isEmpty())
			inOrdenK(root,list);
		return list;
	}

	public Iterable<V> values() {
		PositionList<V> list = new ListaDoblementeEnlazada<V>();
		if(!isEmpty())
			inOrdenV(root,list);
		return list;
	}

	public Iterable<EntryABB<K,V>> entries() {
		PositionList<EntryABB<K,V>> list = new ListaDoblementeEnlazada<EntryABB<K,V>>();
		if(!isEmpty())
			inOrdenE(root,list);
		return list;
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("The key is invalid.");
	}
	
	private NodoABB<EntryABB<K,V>> buscar(K key){
		return buscarAux(key, root);
	}
	
	private NodoABB<EntryABB<K,V>> buscarAux(K key, NodoABB<EntryABB<K,V>> n) {
		if(n.element() == null)
			return n; // Dummy, key not found.
		else {
			int c = comp.compare(key, n.element().getKey());
			if(c == 0)
				return n; // n was found!
			else
				if(c < 0)
					return buscarAux(key,n.getLeft());
				else
					return buscarAux(key,n.getRight());
		}
	}
	
	private void eliminar(NodoABB<EntryABB<K,V>> n) {
		if(n == root) 
			eliminarRaiz();
		else {
			if(isExternal(n)) {
				n.setElement(null);
				n.setLeft(null);
				n.setRight(null);
			}
			else {
				if(hasOnlyLeftChild(n)) {
					if(n.getParent().getLeft() == n)
						n.getParent().setLeft(n.getLeft());
					else
						n.getParent().setRight(n.getLeft());
					n.getLeft().setParent(n.getParent());
				}
				else {
					if(hasOnlyRightChild(n)) {
						if(n.getParent().getLeft() == n)
							n.getParent().setLeft(n.getRight());
						else
							n.getParent().setRight(n.getRight());
						n.getRight().setParent(n.getParent());
					} 
					else {
						NodoABB<EntryABB<K,V>> min = findMinimum(n.getRight());
						n.setElement(min.element());
						eliminar(min);
					}
				}
			}
		}
	}
	
	private void eliminarRaiz() {
		if(hasOnlyLeftChild(root)) {
			root = root.getLeft();
			root.setParent(null);
		}
		else {
			if(hasOnlyRightChild(root)) {
				root = root.getRight();
				root.setParent(null);
			}
			else { 
				NodoABB<EntryABB<K,V>> min = findMinimum(root.getRight());
				root.setElement(min.element());
				eliminar(min);
			}
		}
	}

	private NodoABB<EntryABB<K,V>> findMinimum(NodoABB<EntryABB<K,V>> n){
		NodoABB<EntryABB<K,V>> min = n;
		while (!isExternal(min) || !hasOnlyRightChild(min)) {
			min = n.getLeft();
		}
		return min;
	}
	
	private boolean isExternal(NodoABB<EntryABB<K,V>> p) {
		return p.getLeft().element() == null && p.getRight().element() == null;
	}
	
	private boolean hasOnlyLeftChild(NodoABB<EntryABB<K,V>> p) {
		return p.getLeft().element() != null && p.getRight().element() == null;
	}
	
	private boolean hasOnlyRightChild(NodoABB<EntryABB<K,V>> p) {
		return p.getLeft().element() == null && p.getRight().element() != null;
	}
	
	private void inOrdenK(NodoABB<EntryABB<K,V>> e, PositionList<K> list) {
		if(e.getLeft().element() != null) {
			inOrdenK(e.getLeft(),list);
		}
		list.addLast(e.element().getKey());
		if(e.getRight().element() != null) {
			inOrdenK(e.getRight(),list); 
		}
	}
	
	private void inOrdenV(NodoABB<EntryABB<K,V>> e, PositionList<V> list) {
		if(e.getLeft().element() != null) {
			inOrdenV(e.getLeft(),list);
		}
		list.addLast(e.element().getValue());
		if(e.getRight().element() != null) {
			inOrdenV(e.getRight(),list); 
		}
	}
	
	private void inOrdenE(NodoABB<EntryABB<K,V>> e, PositionList<EntryABB<K,V>> list) {
		if(e.getLeft().element() != null) {
			inOrdenE(e.getLeft(),list);
		}
		list.addLast(e.element());
		if(e.getRight().element() != null) {
			inOrdenE(e.getRight(),list); 
		}
	}
}
