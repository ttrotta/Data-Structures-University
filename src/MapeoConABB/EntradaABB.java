package MapeoConABB;

public class EntradaABB<K extends Comparable<K>,V> implements EntryABB<K,V> {
	protected K key;
	protected V value;
	
	public EntradaABB(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setKey(K key) {
		this.key = key;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public String toString() {
		return "("+getKey()+','+getValue()+")";
	}
	
}

