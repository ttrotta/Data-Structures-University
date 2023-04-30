package TDALista;

import Interfaces.*;
import Exceptions.*;
import TDAPila.PilaEnlazada;

public class Metodos {
	
	// Ejercicio 3 a)
	public static ListaSE<Integer> intercalar(ListaSE<Integer> l1, ListaSE<Integer> l2) { 
		ListaSE<Integer> U = new ListaSE<Integer>();
		try {
			while(!l1.isEmpty() && !l2.isEmpty()) {
				Position<Integer> tmp1 = l1.first();
				Position<Integer> tmp2 = l2.first();
				
				if(tmp1.element() < tmp2.element()) {
					U.addLast(tmp1.element());
					l1.remove(tmp1);
				}	
				else if (tmp1.element() > tmp2.element()){
					U.addLast(tmp2.element());
					l2.remove(tmp2);
				}
				else {
					U.addLast(tmp1.element());
					l1.remove(tmp1);
					l2.remove(tmp2);
				}
			}
			while(!l1.isEmpty()) {
				Position<Integer> aux1 = l1.first();
				U.addLast(aux1.element());
				l1.remove(aux1);
			}
			while(!l2.isEmpty()) {
				Position<Integer> aux2 = l2.first();
				U.addLast(aux2.element());
				l2.remove(aux2);
			}
		} catch(InvalidPositionException | EmptyListException e) {
			e.printStackTrace();			
		}
		return U;
	}
	
	// Ejercicio 5 a)
	public static <E> void invertir(ListaSE<E> L) {
		ListaSE<E> lista = new ListaSE<E>();
		try {
			while(!L.isEmpty()) {
				lista.addFirst(L.remove(L.first()));
			}
			while(!lista.isEmpty()) {
				L.addLast(lista.remove(lista.first()));
			}
		} catch(InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
	}
	
	public static <E> void invertirConPila(ListaSE<E> L){
		Stack<E> pila = new PilaEnlazada<E>();
		Position<E> pos;
		
		try{
			while(!L.isEmpty()) {
				pos = L.first();
				pila.push(pos.element());
				L.remove(pos);
			}
			while(!pila.isEmpty()) {
				L.addLast(pila.pop());
			}
		} catch(EmptyListException | InvalidPositionException | EmptyStackException e) {
			e.printStackTrace();
		}
	}
	
	// Ejercicio 5 b)
	public <E> void invertirB(ListaSE<E> L) throws EmptyListException {
		if(L.isEmpty()) {
			invertirRec(L,L.first(),L.last());
		}
	}
	
	private <E> void invertirRec(ListaSE<E> lista, Position<E> p1, Position<E> p2) {
		swap(lista, p1, p2);
		
		try {
			if(p1 != p2 && lista.next(p1) != p2) {
				invertirRec(lista,lista.next(p1),lista.prev(p2));
			}
		} catch(BoundaryViolationException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	private <E> void swap(ListaSE<E> lista, Position<E> p1, Position<E> p2) {
		E aux = p1.element();
		try {
			lista.set(p1,p2.element());
			lista.set(p2,aux);
		} catch(InvalidPositionException e) {
			e.printStackTrace();
		}
	}
	
	//Ejercicio 8
	public <E> boolean meetsCondition(PositionList<E> l1, PositionList<E> l2) {
		boolean cumple = l1 != null && l2 != null && l1.size() / 2 == l2.size();
		try {
			Position<E> puntero1 = l1.first();
			Position<E> puntero2 = l2.first();
			Position<E> last1 = l1.last();
			Position<E> last2 = l2.last(); 
			
			// Comparo que sea igual a la lista 2
			while(cumple && puntero2 != last2) {
				if(puntero1.element() != puntero2.element()) 
					cumple = false;
				puntero1 = l1.next(puntero1);
				puntero2 = l2.next(puntero2);
			}
			if(puntero1.element() == puntero2.element()) 
				puntero1 = l1.next(puntero1);
			else
				cumple = false;
			// Comparo que sea igual a la lista 2 invertida
			while(cumple && puntero1 != last1) {
				if(puntero1.element() != puntero2.element()) 
					cumple = false;
				puntero1 = l1.next(puntero1);
				puntero2 = l2.prev(puntero2);
			}
			cumple = puntero2 == l2.first() && l1.next(puntero1) == null;
			} catch(EmptyListException | InvalidPositionException | BoundaryViolationException e) {
				e.printStackTrace();
			}
		return cumple;
	}
	
	
	// Ejercicio mostrado en clases, elimina de una lista metodos impares.
	@SuppressWarnings("hiding")
	public static <Integer> PositionList<Integer> eliminarImpares(PositionList<Integer> L) {
		PositionList<Integer> toRet = new ListaSE<Integer>();
		if(!L.isEmpty()) {
			Position<Integer> p;
			try {
				p = L.first();
				Position<Integer> toElim = null;
				while(p != null) {
					if((((int)p.element()) % 2) != 0)
						toElim = p;
					if(p != L.last()) {
						p = L.next(p);
					}
					else {
						p = null;
					}
					if(toElim != null) {
						toRet.addLast(toElim.element());
						L.remove(toElim);
						toElim = null;
					}
				}
			} catch(InvalidPositionException | BoundaryViolationException | EmptyListException e) {
				e.printStackTrace();			}
		}
		return L;
	}
	
	public static void main(String[] args) {
		ListaSE<Integer> l1 = new ListaSE<Integer>();
		ListaSE<Integer> l2 = new ListaSE<Integer>();
		
		l1.addLast(1);
		l1.addLast(4);
		l1.addLast(7);
		l1.addLast(9);
		l1.addLast(10);
		
		l2.addLast(2);
		l2.addLast(4);
		l2.addLast(6);
		l2.addLast(9);
		l2.addLast(11);
		
		@SuppressWarnings("unused")
		ListaSE<Integer> U1 = intercalar(l1,l2);
	}
	
}
