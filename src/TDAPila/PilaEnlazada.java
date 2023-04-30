package TDAPila;

import Exceptions.EmptyStackException;
import Interfaces.Stack;
import TDANodo.Nodo;

public class PilaEnlazada<E> implements Stack<E> {
	protected Nodo<E> head;
	protected int size;
	
	public PilaEnlazada() {
		head = null;
		size = 0;
	}
	
	public void push(E element) {
		head = new Nodo<E>(element,head);
		size++;
	}

	public E pop() throws EmptyStackException{
		E temp = null;
		if (isEmpty()) 
			throw new EmptyStackException("La pila esta vacia");
		temp = head.getElement();
		head = head.getNext();
		size--;
		return temp;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public E top() throws EmptyStackException{
		if (size == 0)
			throw new EmptyStackException("La pila no tiene nodos");
		return head.getElement();
	}

	public int size() {
		return size;
	}
	
}
