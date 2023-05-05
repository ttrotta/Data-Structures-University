package Interfaces;

/**
 * Interface Entry
 * @author Thiago Trotta
 */

public interface Entry<K,V> {
	/**
	 * Consulta la clave de la entrada.
	 * @return la clave de la entrada.
	 */
	public K getKey();
	
	/**
	 * Consulta el valor de la entrada.
	 * @return el valor de la entrada.
	 */
	public V getValue();
}
