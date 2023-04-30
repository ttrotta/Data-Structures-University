package TDAPila;

import Exceptions.EmptyStackException;
import Interfaces.Stack;

/**
 * Clase PilaArreglo
 * @author Thiago Trotta
 *
 */

public class PilaArreglo<E> implements Stack<E>{
	
	protected E[] pila;
	protected int tope;
	
	@SuppressWarnings("unchecked")
	public PilaArreglo(int max) {
		pila = (E[]) new Object[max];
		tope = 0;
	}
	
	public int size() {
		return tope; 
	}
	
	public boolean isEmpty() {
		return tope == 0;
	}
	
	public E top() throws EmptyStackException {
		if(isEmpty()) 
			throw new EmptyStackException("La pila esta vacia.");
		return pila[tope-1];
	}

	public void push(E element) {
		if(tope == pila.length)
			resize();
		pila[tope] = element;
		tope++;
	}

	public E pop() throws EmptyStackException { 
		if(isEmpty()) 
			throw new EmptyStackException("La pila esta vacia.");
		E elemento = pila[tope-1];
		pila[tope-1] = null;
		tope--;
		return elemento;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	private void resize() {
		Object [] auxObject = new Object[tope*2];
		for(int i=0; i < pila.length; i++) {
			auxObject[i] = pila[i];
		}
		pila = (E[]) auxObject;
	}
	
	public void invertir() {
		int i = 0; 
		int j = tope; 
		while (i < j) { // n/2 ciclos O(log(n))
			swap(i++,j--);
		}
	}
	
	public void invertirPorParametro(PilaArreglo<E> p){
		int cant = p.size();
		PilaArreglo<E> temp1 = new PilaArreglo<E>(p.size());
		PilaArreglo<E> temp2 = new PilaArreglo<E>(p.size());
		try {
			for(int i=0; i < cant; i++) {
				temp1.push(p.pop());
			}
			for(int j=0; j < cant; j++) {
				temp2.push(temp1.pop());
			}
			for(int k=0; k < cant; k++) {
				p.push(temp2.pop());
			}
		} catch(EmptyStackException e) {
			e.printStackTrace();
		}
	}
	
	private void swap(int i, int j) {
		E temp = pila[j];
		pila[j] = pila[i];
		pila[i] = temp;
	}
}
