package TDANodo;

public class Nodo<E> {
	
	// Atributos de instancia
	protected E element;
	protected Nodo<E> next;
	
	// Constructores
	public Nodo(E item, Nodo<E> nxt) {
		element = item;
		next = nxt;
	}
	public Nodo(E item) { this(item, null); }
	
	// Setters 
	public void setElement(E element) {
		this.element = element;
	}
	public void setNext(Nodo<E> next) {
		this.next = next;
	}
	
	// Getters
	public E getElement() {
		return element;
	}
	public Nodo<E> getNext(){
		return next;
	}
}
