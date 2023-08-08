package TDAGrafoDecoradoMetodos;

import java.util.*;

import Exceptions.*;
import Interfaces.*;

public class ElementIterator<E> implements Iterator<E> {
	protected PositionList<E> list; // Lista a iterar.
	protected Position<E> cursor; // Posición del elemento corriente.
	
	public ElementIterator(PositionList<E> l) {
		list = l; // Guardo la referencia a la lista a iterar.
		if(list.isEmpty()) 
			cursor = null; // Si la lista está vacía, la posición corriente es nula.
		else
			try {
				cursor = list.first(); // Sino la posición corriente es la primera de la lista.
			} catch (EmptyListException e) { 
				e.printStackTrace(); 
			}
	}
	
	public boolean hasNext() {
		return cursor != null; // Hay siguiente si el cursor no está más allá de la última posición.
	}

	public E next() throws NoSuchElementException {
		if (cursor == null) // Si el cursor es null, el cliente no testeó que hasNext fuera true.
			throw new NoSuchElementException("Error: No hay siguiente");
		E elem = cursor.element(); // Salvo el elemento corriente.
		try {
			// Avanzo a la siguiente posición
			cursor = (cursor == list.last()) ? null : list.next(cursor);
		} catch (EmptyListException e) {
			e.printStackTrace();
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		} catch (BoundaryViolationException e) {
			e.printStackTrace();
		}
		return elem; // Retorno el elemento salvado.
	}
}
