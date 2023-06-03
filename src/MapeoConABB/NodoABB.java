package MapeoConABB;

public class NodoABB<E> {
	private E element;
	private NodoABB<E> parent;
	private NodoABB<E> left;
	private NodoABB<E> right;
	
	public NodoABB(E e, NodoABB<E> p) {
		element = e;
		parent = p;
		left = right = null;
	}
	
	// Getters	
	public E element() { return element; }
	public NodoABB<E> getParent() { return parent; }
	public NodoABB<E> getLeft() { return left; }
	public NodoABB<E> getRight() { return right; }
	
	// Setters
	public void setElement(E e) { element = e; }
	public void setParent(NodoABB<E> p) { parent = p; }
	public void setLeft(NodoABB<E> l) { left = l; }
	public void setRight(NodoABB<E> r) { right = r; }
 }
