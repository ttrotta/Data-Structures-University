package TDAMapeo;

import Exceptions.InvalidKeyException;
import Interfaces.Entry;
import TDALista.ListaDE;

// También conocido como Map Open Addresing en GT 
public class MapConHashCerrado<K,V> implements Map<K,V> {
	protected static final float factorDeCarga = 0.5f;
	protected Entry<K,V> [] buckets;
    protected Entry<K,V> available;
    protected int size;
    protected int N;

    @SuppressWarnings("unchecked")
	public MapConHashCerrado() {
        N = 11;
        buckets = (Entrada<K,V> []) new Entrada[N];
        size = 0;
        available = new Entrada<K,V>(null, null);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(K key) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        int i = hashValue(key);
        int aux = 1;
        while((aux <= N) && (buckets[i] != null) && (toReturn == null)) {
            if((buckets[i] != available) && (buckets[i].getKey().equals(key)))
                toReturn = buckets[i].getValue();
            i = (i+1) % N;
            aux++;
        }
        return toReturn;
    }

    @Override
    public V put(K key, V value) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        boolean estado = true;
        int i = hashValue(key);
        int aux = 1;

        if((float)(size/N) >= factorDeCarga) {
            reHash();
        }

        while (aux <= N && estado){
            if(buckets[i] == null || buckets[i] == available){
                buckets[i] = new Entrada<K,V>(key, value);
                estado = false;
                size++;
            }
            else if(buckets[i].getKey().equals(key)) {
                toReturn = buckets[i].getValue();
                /* Arreglar este casteo */ ((Entrada<K, V>) buckets[i]).setValue(value);
                estado = false;
            }
            else {
                i = (i+1) % N;
            }
            aux++;
        }

        return toReturn;
    }

    @Override
    public V remove(K key) throws InvalidKeyException {
        checkKey(key);
        V toReturn = null;
        int i = hashValue(key);
        int aux = 1;

        while (aux <= N && buckets[i] != null && toReturn == null){
            if(buckets[i] != available && buckets[i].getKey().equals(key)) {
                toReturn = buckets[i].getValue();
                buckets[i] = available;
                size--;
            }
            else {
                i = (i+1) % N;
            }
            aux++;
        }
        return toReturn;
    }

    @Override
    public Iterable<K> keys() {
        ListaDE<K> keys = new ListaDE<K>();
        for(Entry<K,V> e: buckets){
            if((e != null) && (e != available))
                keys.addLast(e.getKey());
        }
        return keys;
    }

    @Override
    public Iterable<V> values() {
        ListaDE<V> values = new ListaDE<V>();
        for(Entry<K,V> e: buckets){
            if((e != null) && (e != available))
                values.addLast(e.getValue());
        }
        return values;
    }

    @Override
    public Iterable<Entry<K, V>> entries() {
        ListaDE<Entry<K,V>> entries = new ListaDE<Entry<K,V>>();
        for(Entry<K,V> e: buckets){
            if((e != null) && (e != available))
                entries.addLast(e);
        }
        return entries;
    }

    private void checkKey(K key) throws InvalidKeyException {
        if(key == null) {
            throw new InvalidKeyException("Clave nula.");
        }
    }

    @SuppressWarnings("unchecked")
	private void reHash() throws InvalidKeyException{
        Iterable<Entry<K,V>> entries = this.entries();
        N = nextPrime(N*2);
        buckets = (Entrada<K,V> []) new Entrada[N];
        size = 0;
        for(Entry<K,V> e : entries) {
            this.put(e.getKey(), e.getValue());
        }
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

    private int hashValue(K key){
        return Math.abs(key.hashCode() % N);
    }
}

