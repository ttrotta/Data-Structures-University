package Interfaces;

public interface BTPosition<E> extends Position<E> {
	/**
	 * 
	 * @return
	 */
	public BTPosition<E> getPadre();
	
	/**
	 * 
	 * @return
	 */
	public BTPosition<E> getLeft();
	
	/**
	 * 
	 * @return
	 */
	public BTPosition<E> getRight();
	
	/**
	 * 
	 */
	public void setElement(E e);
	
	/**
	 * 
	 */
	public void setPadre(BTPosition<E> p);
	
	/**
	 * 
	 */
	public void setLeft(BTPosition<E> l);
	
	/**
	 * 
	 */
	public void setRight(BTPosition<E> r);
	
}
