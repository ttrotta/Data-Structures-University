package TDAArbol;

import java.util.Iterator;

import Exceptions.*;
import Interfaces.Position;
import Interfaces.PositionList;
import Interfaces.Tree;
import TDALista.*;
import TDAPila.*;
import Interfaces.Stack;

public class Arbol<E> implements Tree<E>{
	protected TNodo<E> root;
	protected int size;
	
	public Arbol() {
		root = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public Iterator<E> iterator() {
		PositionList<E> list = new ListaDE<E>();
		if(size > 0)
			recPreOrden(root, list);
		return list.iterator();
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list = new ListaDE<Position<E>>();
		if(size > 0)
			recPreOrdenPos(root,list);
		return list;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		E toReturn = nodo.element(); // Se llaman element() los de Nodo y Position (considerar cambiar) 
		nodo.setElement(e);
		return toReturn;
	}

	public Position<E> root() throws EmptyTreeException {
		if(isEmpty())
			 throw new EmptyTreeException("El árbol está vacío, por ende no tiene raíz.");
		return root;
	}
	
	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		TNodo<E> nodo = checkPosition(v);
		if(nodo == root)
			throw new BoundaryViolationException("La raíz no tiene padre.");
		return nodo.getPadre();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		PositionList<Position<E>> list = new ListaDE<Position<E>>(); //<- preguntar que en las diapos esta como <TNodo<E>>
		for(TNodo<E> n : nodo.getHijos()) {
			list.addLast(n);
		}
		return list;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return !nodo.getHijos().isEmpty();
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return nodo.getHijos().isEmpty();
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(v);
		return nodo == root;
	}

	public void createRoot(E e) throws InvalidOperationException {
		if(root != null)
			throw new InvalidOperationException("El árbol ya posee raíz.");
		root = new TNodo<E>(e);
		size = 1;
	}

	public Position<E> addFirstChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(p);
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> nuevo = new TNodo<E>(e,nodo);
		nodo.getHijos().addFirst(nuevo);
		size++;
		return nuevo;
		
	}

	public Position<E> addLastChild(Position<E> p, E e) throws InvalidPositionException {
		TNodo<E> nodo = checkPosition(p);
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> nuevo = new TNodo<E>(e,nodo);
		nodo.getHijos().addLast(nuevo);
		size++;
		return nuevo;
	}

	public Position<E> addBefore(Position<E> p, Position<E> rb, E e) throws InvalidPositionException {
		TNodo<E> padre = checkPosition(p);
		TNodo<E> hermanoD = checkPosition(rb);
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> nuevo = new TNodo<E>(e,padre);
		PositionList<TNodo<E>> hijos = padre.getHijos();
		Interfaces.Position<TNodo<E>> pp = null;
		boolean found = false;
		try {
			pp = hijos.first(); 
			while(pp != null && !found) {
				if(hermanoD == pp.element())
					found = true;
				else
					pp = (pp != hijos.last() ? hijos.next(pp) : null);
			}
		} catch(EmptyListException | BoundaryViolationException e1) {
			System.out.println(e1.getMessage());
		}
		if(!found)
			throw new InvalidPositionException("p no es padre de rb.");
		hijos.addBefore(pp, nuevo);
		size++;
		return nuevo;
	}

	public Position<E> addAfter(Position<E> p, Position<E> lb, E e) throws InvalidPositionException {
		TNodo<E> padre = checkPosition(p);
		TNodo<E> hermanoI = checkPosition(lb);
		TNodo<E> nuevo = new TNodo<E>(e,padre);
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		PositionList<TNodo<E>> hijos = padre.getHijos();
		Interfaces.Position<TNodo<E>> pp = null;
		boolean found = false;
		try {
			pp = hijos.first(); 
			while(pp != null && !found) {
				if(hermanoI == pp.element())
					found = true;
				else
					pp = (pp != hijos.last() ? hijos.next(pp) : null);
			}
		} catch(EmptyListException | BoundaryViolationException e1) {
			System.out.println(e1.getMessage());
		}
		if(!found)
			throw new InvalidPositionException("p no es padre de rb.");
		hijos.addAfter(pp, nuevo);
		size++;
		return nuevo;
	}

	/* public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		TNodo<E> nodo = checkPosition(p);
		if(!nodo.getHijos().isEmpty())
			throw new InvalidPositionException("p no es un nodo hoja.");
		TNodo<E> padre = nodo.getPadre();
		PositionList<TNodo<E>> hijosPadre = padre.getHijos();
		boolean found = false;
		Interfaces.Position<TNodo<E>> pp = null;
		Iterable<Interfaces.Position<TNodo<E>>> posiciones = hijosPadre.positions();
		Iterator<Interfaces.Position<TNodo<E>>> it = posiciones.iterator();
		while(it.hasNext() && !found) {
			pp = it.next();
			if(pp.element() == nodo)
				found = true;
		}
		if(!found)
			throw new InvalidPositionException("p no aparece en la lista de hijos de su padre: !no eliminé!");
		hijosPadre.remove(pp);
		nodo.setElement(null);
		size--;
		if(isEmpty())
			root = null;
	} */
	
	public void removeExternalNode(Position<E> p) throws InvalidPositionException {
		if(isEmpty()) 
			 throw new InvalidPositionException("El árbol está vacío.");
	     if(isInternal(p)) 
	         throw new InvalidPositionException("La posición no corresponde a un nodo interno.");
	     removeNode(p);
	}
	
	public void removeInternalNode(Position<E> p) throws InvalidPositionException {
		 if(isEmpty()) 
			 throw new InvalidPositionException("El árbol está vacío.");
	     if(isExternal(p)) 
	         throw new InvalidPositionException("La posición no corresponde a un nodo interno.");
	     removeNode(p);
	}

	public void removeNode(Position<E> p) throws InvalidPositionException {
		 if(size == 0) 
	            throw new InvalidPositionException("El árbol está vacío.");
	     TNodo<E> toRemove = checkPosition(p);
	     try {
	    	 if(toRemove == root) {
	    		 if(toRemove.getHijos().size() == 1) {
	    			 Position<TNodo<E>> nuevaRaiz = toRemove.getHijos().first();
	    			 root = nuevaRaiz.element();
	    			 root.setPadre(null);
	    			 size--;
	    		 }
	    		 else 
	    			  if(size == 1) {
	    				  root = null;
	    				  size--;
	    			  }
	    			  else
	    				  throw new InvalidPositionException("No se puede eliminar el nodo porque no se puede decidir que nodoHijo será la raíz."); 
	    	 }
	    	 else {
	    		 TNodo<E> padre = toRemove.getPadre();
	    		 PositionList<TNodo<E>> hijosDeR = toRemove.getHijos();
	    		 PositionList<TNodo<E>> hijosDelPadre = padre.getHijos();
	    		 Position<TNodo<E>> primero = hijosDelPadre.first();
	    		 while(primero.element() != toRemove) {
	    			 primero = hijosDelPadre.next(primero);
	    	 	 }
	    		 while(!hijosDeR.isEmpty()) {
	    			 Position<TNodo<E>> hijoAInsertar = hijosDeR.first();
	    			 hijosDelPadre.addBefore(primero, hijoAInsertar.element());
	    			 hijoAInsertar.element().setPadre(padre);
	    			 hijosDeR.remove(hijoAInsertar);
	    		 }
	    		 hijosDelPadre.remove(primero);
	    		 size--;
	    	 }
	    		 
	     } catch (EmptyListException | InvalidPositionException | BoundaryViolationException e) {
	    	 throw new InvalidPositionException("La posición no es válida.");
	     }
	}
	
	// Ejercicio 8
	public void invertirHijos(E r) {
		invertirHijosRec(r,root);
	}
	
	private void invertirHijosRec(E r, TNodo<E> v) {
		PositionList<TNodo<E>> listaAux = new ListaDoblementeEnlazada<TNodo<E>>();
		Stack<TNodo<E>> pilaAux = new PilaEnlazada<TNodo<E>>();
		try { checkPosition(v); } catch (InvalidPositionException e) { e.printStackTrace(); }
		try {
			if(isInternal(v)) {
				if(v.element() == r) {
					listaAux = v.getHijos();
					while(!listaAux.isEmpty()) {
						pilaAux.push(listaAux.remove(listaAux.first()));
					}
					while(!pilaAux.isEmpty()) {
						listaAux.addLast(pilaAux.pop());
					}
				}
			}
			for(TNodo<E> h : v.getHijos())
				invertirHijosRec(r,h);
		} catch (InvalidPositionException | EmptyListException | EmptyStackException e) {
			e.printStackTrace();
		}
	}
	
	private TNodo<E> checkPosition(Position<E> v) throws InvalidPositionException {
		 TNodo<E> nodo = null;
		 if(v == null)
			 throw new InvalidPositionException("La posición es nula.");
		 try {
			 nodo = (TNodo<E>) v;
		 } catch (ClassCastException e) {
			 throw new InvalidPositionException("No se lo puede castear a un objeto de tipo TNodo<E>, o nodo previamente eliminado.");
		 }
		 return nodo;
	}
	
	private void recPreOrden(TNodo<E> r, PositionList<E> l) { 
		l.addLast(r.element());
		for(TNodo<E> p : r.getHijos()) { 
			recPreOrden(p,l);
		}	
	}
	
	private void recPreOrdenPos(TNodo<E> r, PositionList<Position<E>> l) {
		l.addLast(r);
		for(TNodo<E> p : r.getHijos())
			recPreOrdenPos(p,l);
	}
}
