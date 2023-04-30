package TDACola;

import Interfaces.Queue;

import TDAPila.PilaArreglo;
import Exceptions.*;

public class ColaConPila <E> implements Queue<E>{
	protected PilaArreglo<E> pila;
	protected int front;
	
	public ColaConPila() {
		pila = new PilaArreglo<E>(20);
		front = 0;
	}
	
	public void enqueue(E e) {
		pila.push(e);
		front++;
	}

	public E dequeue() throws EmptyQueueException {
		E temp = null;
		if (isEmpty())
			throw new EmptyQueueException("La pila esta vacia");
		try {
			pila.invertir();
			temp = pila.pop();
			front--;
			pila.invertir();
		} catch (EmptyStackException e) {
			System.out.println(e.getMessage());
		}
		return temp;
	}
	
	public E front() throws EmptyQueueException {
		E temp = null;
		try {
			temp =  pila.top();
		} catch (EmptyStackException e) {
			System.out.println(e.getMessage());
		}
		return temp;
	}

	public boolean isEmpty() {
		return pila.isEmpty();
	}

	
	public int size() {
		return pila.size();
	}
	
	public String toString() {
		return pila.toString();
	}

}