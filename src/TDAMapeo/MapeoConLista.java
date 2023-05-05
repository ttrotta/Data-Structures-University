package TDAMapeo;

import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import Interfaces.*;
import TDALista.ListaDE; 


public class MapeoConLista<K,V> implements Map<K,V> {
	protected PositionList<Entrada<K,V>> S;
	
	public MapeoConLista() {
		S = new ListaDE<Entrada<K,V>>();
	}
	
	public int size() {
		return S.size();
	}

	public boolean isEmpty() {
		return S.isEmpty();
	}

	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		for(Position<Entrada<K,V>> p : S.positions()) {
			if(p.element().getKey().equals(key))
				return p.element().getValue();
		}
		return null;
	}

	public V put(K key, V value) throws InvalidKeyException {
		checkKey(key);
		for(Position<Entrada<K,V>> p : S.positions()) {
			if(p.element().getKey().equals(key)) {
				V aux = p.element().getValue();
				p.element().setValue(value);
				return aux;
			}
		}
		S.addLast(new Entrada<K,V>(key,value));
		return null;
	}

	public V remove(K key) throws InvalidKeyException {
		checkKey(key);
		for(Position<Entrada<K,V>> p : S.positions()) {
			if(p.element().getKey().equals(key)) {
				V value = p.element().getValue();
				try {
					S.remove(p);
				} catch (InvalidPositionException e) {
					e.printStackTrace();
				}
				return value;
			}
		}
		return null;
	}

	public Iterable<K> keys() {
		PositionList<K> keys = new ListaDE<K>();
        for(Entrada<K,V> e : S) {
            keys.addLast(e.getKey());
        }
        return keys;
	}

	public Iterable<V> values() {
		PositionList<V> values = new ListaDE<V>();
        for(Entrada<K,V> e : S) {
            values.addLast(e.getValue());
        }
        return values;
	}

	public Iterable<Entry<K,V>> entries() {
        PositionList<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();
        for(Entrada<K,V> e : S) {
            entries.addLast(e);
        }
        return entries;
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es nula.");
	}
}
