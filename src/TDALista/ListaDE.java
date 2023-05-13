package TDALista;

import java.util.Iterator;

import Exceptions.*;
import Interfaces.*;

public class ListaDE<E> implements PositionList<E> {	
	protected Nodo<E> header;
	protected Nodo<E> trailer;
	protected int size;
	
	public ListaDE() {
		header = new Nodo<>(null,null,null);
		trailer = new Nodo<>(null,header,null);
		header.setNext(trailer);
		size = 0;
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
		Nodo<E> nuevo = new Nodo<E>(element,header,null);
		nuevo.setNext(header.getNext());
		header.getNext().setPrev(nuevo);
		header.setNext(nuevo);
		size++;
	}

	public void addLast(E element) {
		Nodo<E> nuevo;
		if(isEmpty())
			addFirst(element);
		else {
			nuevo = new Nodo<E>(element,null,trailer);
			nuevo.setPrev(trailer.getPrev());
			trailer.getPrev().setNext(nuevo);
			trailer.setPrev(nuevo);
			size++;
		}
	}

	public void addAfter(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
		Nodo<E> nuevo = new Nodo<E>(element,pos,null);
		nuevo.setNext(pos.getNext());
		nuevo.getNext().setPrev(nuevo);
		pos.setNext(nuevo);
		size++;
	}

	public void addBefore(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
		Nodo<E> nuevo = new Nodo<E>(element,null,pos);
		nuevo.setPrev(pos.getPrev());
		pos.getPrev().setNext(nuevo);
		pos.setPrev(nuevo);
		size++;
	}
	     
	@Override
	public E remove(Position<E> p) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
        E toReturn;
        pos.getPrev().setNext(pos.getNext());
        pos.getNext().setPrev(pos.getPrev());
        toReturn = pos.element();
        pos.setElement(null);
        pos.setPrev(null);
        pos.setNext(null);
        size--;
        return toReturn;
	}

	public E set(Position<E> p, E element) throws InvalidPositionException {
		Nodo<E> pos = checkPosition(p);
		E toReturn = pos.element();
		pos.setElement(element);
		return toReturn;
	}

	public Iterator<E> iterator() {
		return new ElementIterator<E>(this);
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> p = new ListaDE<Position<E>>();
		if(!isEmpty()) {
			Position<E> pos = null;
			try {
				pos = first();
			} catch (EmptyListException e) { e.printStackTrace(); }
			try {
				while(pos != last()) {
					p.addLast(pos);
					pos = next(pos);
				}
			} catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {				
				e.printStackTrace();
			}	
			p.addLast(pos);
			} 
		return p;
		
        /* PositionList<Position<E>> toReturn = new ListaDE<Position<E>>();
        Nodo<E> nodo = header.getNext();
        while(nodo != trailer) {
            toReturn.addLast(nodo);
            nodo = nodo.getNext();
        }
        return toReturn; */
    }
	 
	private Nodo<E> checkPosition(Position<E> p) throws InvalidPositionException {
		try {
			if( p == null )
                throw new InvalidPositionException("La posición es nula.");
            if( p == header )
                throw new InvalidPositionException("La posición es inválida.");
            if( p == trailer )
                throw new InvalidPositionException("La posición es inválida.");
            if( p.element() == null )
                throw new InvalidPositionException("La posición fue eliminada previamente.");
            Nodo<E> nodo = (Nodo<E>) p;
            if ((nodo.getPrev() == null))
                throw new InvalidPositionException("La posición no tiene anterior.");
            else if ((nodo.getNext() == null)) 
            	throw new InvalidPositionException("La posición no tiene siguiente.");
            return nodo;
		} catch(ClassCastException e) {
			throw new InvalidPositionException("La posición no es de tipo Nodo E.");
		}
	}
	
	//--------------- Clase Nodo Anidada ---------------
	private static class Nodo<E> implements Position<E> {
		private E element;
		private Nodo<E> prev;
		private Nodo<E> next;
		
		public Nodo(E e, Nodo<E> p, Nodo<E> n) {
			element = e;
			prev = p;
			next= n;
		}
		
		@SuppressWarnings("unused")
		public Nodo(E e) {
			this(e,null,null);
		}
		
		public E element() { return element; }
		
		public Nodo<E> getPrev(){ return prev; }
		
		public Nodo<E> getNext(){ return next; }
		
		public void setElement(E e) { element = e; }
		
		public void setPrev(Nodo<E> p) { prev = p; }
		
		public void setNext(Nodo<E> n) { next = n; }
	}
	//--------- Final de la Clase Nodo Anidada ---------
	
}
