package TDACola;

import Exceptions.EmptyQueueException;
import Interfaces.Queue;

public class ColaConArregloCircular<E> implements Queue<E> {
	private E[] cola;
	protected int frente;
	protected int rabo;
	
	@SuppressWarnings("unchecked")
	public ColaConArregloCircular(int max) {
		frente = 0;
		rabo = 0;
		cola = (E[]) new Object[max];
	}
	
	public boolean isEmpty() {
		return frente == rabo;
	}
	
	public E front() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola est\\u00e1 vac\\u00e9a.");
		return cola[frente];
	}
	
	public int size() {
		int N = cola.length;
		return (N-frente+rabo) % N; 
	}
	
	public void enqueue(E element) { 
		int N = cola.length;
		if(size() == N - 1) { 
			resize();
		}	
		cola[rabo] = element;
		rabo = (rabo+1) % cola.length;
	}
	
	public E dequeue() throws EmptyQueueException {
		if(isEmpty())
			throw new EmptyQueueException("La cola está vacía.");
		E temp = cola[frente];
		cola[frente] = null;
		frente = (frente+1) % cola.length;
		return temp;			
	}
	
	private void resize() {
	    int tamaño = this.size(); 
	    @SuppressWarnings("unchecked")
		E []aux = (E[])new Object[tamaño*2];
	        for (int i = 0; i < tamaño; i++) {
	            aux[i] = cola[frente];
	            frente= (frente+1)% cola.length;
	        }
	        cola = aux;
	        rabo= tamaño;
	        frente=0;
	    }
}
