package TDADiccionario;

import Exceptions.InvalidEntryException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import Interfaces.Dictionary;
import Interfaces.Entry;
import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.ListaDE;

public class DiccionarioConLista<K,V> implements Dictionary<K,V> {
	protected PositionList<Entry<K,V>> D;
	
	public DiccionarioConLista() {
		D = new ListaDE<Entry<K,V>>();
	}
	
	public int size() {
		return D.size();
	}

	public boolean isEmpty() {
		return D.isEmpty();
	}
	
	
    public Entry<K,V> find(K key) throws InvalidKeyException {
    	Entry<K,V> toReturn = null;
    	checkKey(key);
    	for(Entry<K,V> e : D) {
       		if(key.equals(e.getKey())){
            	toReturn = e;
            	break;
        	}
    	}
    	return toReturn;
	}

	public Iterable<Entry<K,V>> findAll(K key) throws InvalidKeyException {
		PositionList<Entry<K,V>> l = new ListaDE<Entry<K,V>>();
		checkKey(key);
		for(Position<Entry<K,V>> p : D.positions()) {
			if(p.element().getKey().equals(key)) {
				l.addLast(p.element());
			}
		}
		return l;
	}
	
	public Entry<K,V> insert(K key, V value) throws InvalidKeyException {
		checkKey(key);
		Entry<K,V> e = new Entrada<K,V>(key,value);
		D.addLast(e);
		return e;
	}

	public Entry<K,V> remove(Entry<K,V> e) throws InvalidEntryException {
		checkEntry(e);
		for(Position<Entry<K,V>> p : D.positions()) {
			if(p.element() == e) {
				try {
					D.remove(p);
					return e;
				} catch (InvalidPositionException e1) {
					e1.printStackTrace();
				}
			}
		}
		throw new InvalidEntryException("La entrada no pertenece al diccionario.");
	}
	
	public Iterable<Entry<K,V>> entries() {
		return D;
	}
	
	private void checkKey(K key) throws InvalidKeyException {
		if(key == null)
			throw new InvalidKeyException("La clave es nula.");
	}
	
    private void checkEntry(Entry<K,V> e) throws InvalidEntryException{
        if(e == null){
            throw new InvalidEntryException("La entrada nula.");
        }
    }
	
}
