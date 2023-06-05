package TDAArbolBinario;

import Interfaces.BTPosition;

public class BTNodo<E> implements BTPosition<E> {
	private E element;
	private BTPosition<E> left, right, parent;
	
	public BTNodo(E element, BTPosition<E> left, BTPosition<E> right, BTPosition<E> parent) {
		this.element = element;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	public E element() {
		return element;
	}

	public BTPosition<E> getParent() {
		return parent;
	}
	
	public BTPosition<E> getLeft() {
		return left;
	}

	public BTPosition<E> getRight() {
		return right;
	}

	public void setElement(E e) {
		element = e;
	}
	
	public void setPadre(BTPosition<E> p) {
		parent = p;
	}

	public void setLeft(BTPosition<E> l) {
		left = l;
		
	}
	public void setRight(BTPosition<E> r) {
		right = r;
	}
	
	public String toString() {
        return String.valueOf(element);
	}
}
