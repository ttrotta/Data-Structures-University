package TDALista;

import java.util.Iterator;

import Exceptions.*;
import Interfaces.*;

public class ListaDE<E> implements PositionList<E> {
	//--------------- Clase Nodo Anidada ---------------
	private static class Nodo<E> implements Position<E> {
		private E element;
		private Nodo<E> prev;
		private Nodo<E> next;
		
		public Nodo(E e, Nodo<E> p, Nodo<E> n) {
			element = e;
			p = prev;
			n = next;
		}
		
		public E element() { return element; }
		
		public Nodo<E> getPrev(){ return prev; }
		
		public Nodo<E> getNext(){ return next; }
		
		@SuppressWarnings("unused")
		public void setPrev(Nodo<E> p) { prev = p; }
		
		public void setNext(Nodo<E> n) { next = n; }
	}
	//--------- Final de la Clase Nodo Anidada ---------
	
	protected Nodo<E> header;
	protected Nodo<E> trailer;
	protected int size;
	
	public ListaDE() {
		header = new Nodo<>(null,null,null);
		trailer = new Nodo<>(null,header,null);
		header.setNext(trailer);
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public Position<E> first() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista está vacía.");
		return header.getNext();
	}

	public Position<E> last() throws EmptyListException {
		if(isEmpty())
			throw new EmptyListException("La lista está vacía.");
		return trailer.getPrev();
	}

	public Position<E> next(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> nodo = checkPosition(p); 
		if(nodo.getNext() == trailer)
			throw new BoundaryViolationException("La posición corresponde al último elemento de la lista. ");
		return nodo.getNext();
	}

	public Position<E> prev(Position<E> p) throws InvalidPositionException, BoundaryViolationException {
		Nodo<E> nodo = checkPosition(p);
		if(nodo.getPrev() == header)
			throw new BoundaryViolationException("La posición corresponde al último elemento de la lista. ");
		return nodo.getPrev();
	}

	public void addFirst(E element) {
		Nodo<E> nuevo = new Nodo<E>(null,null,null);
		
	}

	@Override
	public void addLast(E element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E set(Position<E> p, E element) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Position<E>> positions() {
		// TODO Auto-generated method stub
		return null;
	}
	 
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if(p == null)
				throw new InvalidPositionException("La posición es nula.");
			if(p.element() == null)
				throw new InvalidPositionException("La posición fue eliminada previamente.");
			return (Nodo<E>) p;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("Posicion inválida, no es de tipo Nodo E.");
		}
	}
	
}
