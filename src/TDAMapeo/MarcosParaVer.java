package TDAMapeo;

import Exceptions.*;
import Interfaces.Entry;
import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.ListaDE;

public class MarcosParaVer<K,V> implements Map<K,V>{

	protected PositionList<Entrada<K, V>> S;
	
	public MarcosParaVer() {
		
		S = new ListaDE<Entrada<K,V>>();
	
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return S.size();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return S.isEmpty();
	}

	@Override
	public V get(K key) throws InvalidKeyException {
		checkKey(key);
		for(Position<Entrada<K, V>> p: S.positions()) {
			if(p.element().getKey().equals(key)) {
				return p.element().getValue();
			}
		}
		return null;
	}

	@Override
	public V remove(K key) throws InvalidKeyException {
		// TODO Auto-generated method stub
		checkKey(key);
		for(Position<Entrada<K, V>> p: S.positions()) {
			if(p.element().getKey().equals(key)) {
				V aux = p.element().getValue();
				try {
					S.remove(p);
				} catch (InvalidPositionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return aux;
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) throws InvalidKeyException {
		// TODO Auto-generated method stub
		checkKey(key);
		for(Entrada<K, V> p : S) {
			if(p.getKey().equals(key)) {
				V aux = p.getValue();
				p.setValue(value);
				return aux;
			}
		}
		S.addLast(new Entrada<K, V>(key, value));
		return null;
	}

	@Override
	public Iterable<K> keys() {
		// TODO Auto-generated method stub
		PositionList<K> toReturn = new ListaDE<K>();
        
        for(Entrada<K, V> p : S) {
    		toReturn.addLast(p.getKey());
		}
        return toReturn;
	}

	@Override
	public Iterable<V> values() {
		// TODO Auto-generated method stub
		PositionList<V> toReturn = new ListaDE<V>();
        
		for(Entrada<K, V> p: S) {
			toReturn.addLast(p.getValue());
		}
		return toReturn;
	}

	@Override
	public Iterable<Entry<K, V>> entries() {
		// TODO Auto-generated method stub
		PositionList<Entry<K, V>> toReturn = new ListaDE<Entry<K,V>>();
		for(Entrada<K, V> p : S) {
			toReturn.addLast(p);
		}
        return toReturn;
	}
	private void checkKey(K key) throws InvalidKeyException{
		try {
			if(key == null) {
				throw new InvalidKeyException("key invalida.");
			}
		}catch (ClassCastException e) {
			e.printStackTrace();
		}
	}
}