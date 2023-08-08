package TDAArbolBinario;

import java.util.Iterator;

import Exceptions.*;
import Interfaces.BTPosition;
import Interfaces.BinaryTree;
import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.*;
import java.util.Queue;
import java.util.LinkedList;

public class ArbolBinario<E> implements BinaryTree<E> {
	protected BTPosition<E> root;
	protected int size;
	
	public ArbolBinario() {
		root = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0; // raíz == null
	}
	
	public Iterator<E> iterator() {
		PositionList<E> list = new ListaDoblementeEnlazada<E>();
		for(Position<E> p : positions()) {
			list.addLast(p.element());
		}
		return list.iterator();
	}

	public Iterable<Position<E>> positions() {
		PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>();
		if(!isEmpty())
			preOrden(root, list);
		return list;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTPosition<E> nodo = checkPosition(v);
		E toReturn = nodo.element();
		nodo.setElement(e);
		return toReturn;
	}

	public Position<E> root() throws EmptyTreeException {
		if(isEmpty())
			throw new EmptyTreeException("El árbol está vacío.");
		return root;
	}

	public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> nodo = checkPosition(v);
		if(nodo == root)
			throw new BoundaryViolationException("La raíz no tiene padre.");
		return nodo.getParent();
	}

	public Iterable<Position<E>> children(Position<E> v) throws InvalidPositionException {
		checkPosition(v);
		PositionList<Position<E>> hijos = new ListaDoblementeEnlazada<Position<E>>();
		try {
			if(hasLeft(v))
				hijos.addLast(left(v));
			if(hasRight(v))
				hijos.addLast(right(v));
		} catch (InvalidPositionException | BoundaryViolationException e) {
				e.printStackTrace();
		}
		return hijos;
	}

	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		return hasLeft(v) || hasRight(v);
	}

	public boolean isExternal(Position<E> v) throws InvalidPositionException {
		return !isInternal(v);
	}

	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = checkPosition(v);
		return nodo == root;
	}

	public Position<E> createRoot(E e) throws InvalidOperationException {
		if(!isEmpty())
			throw new InvalidOperationException("Ya existe una raíz.");
		root = new BTNodo<E>(e,null,null,null);
		size = 1;
		return root;
	}

	public Position<E> left(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> nodo = checkPosition(v);
		if(nodo.getLeft() == null)
			throw new BoundaryViolationException("No existe el BTNodo izquierdo.");
		return nodo.getLeft();
	}

	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTPosition<E> nodo = checkPosition(v);
		if(nodo.getRight() == null)
			throw new BoundaryViolationException("No existe el BTNodo derecho.");
		return nodo.getRight();
	}

	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = checkPosition(v);
		return nodo.getLeft() != null;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTPosition<E> nodo = checkPosition(v);
		return nodo.getRight() != null;
	}

	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		BTPosition<E> nodo = checkPosition(v);
		if(hasLeft(v))
			throw new InvalidOperationException("Ya hay un hijo derecho en la posición.");
		BTPosition<E> toInsert = new BTNodo<E>(r, null, null, nodo);
		nodo.setLeft(toInsert);
		size++;
		return toInsert;
	}

	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		BTPosition<E> nodo = checkPosition(v);
		if(hasRight(v))
			throw new InvalidOperationException("Ya hay un hijo derecho en la posición.");
		BTPosition<E> toInsert = new BTNodo<E>(r, null, null, (BTNodo<E>) v);
		nodo.setRight(toInsert);
		size++;
		return toInsert;
	}

	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> toRemove = checkPosition(v);
		BTNodo<E> parentRemove = (BTNodo<E>) toRemove.getParent();
		BTNodo<E> left = (BTNodo<E>) toRemove.getLeft();
		BTNodo<E> right = (BTNodo<E>) toRemove.getRight();
		if(left != null && right != null) // si no tiene al menos un hijo izq o der (puede no tener ninguno)
			throw new InvalidOperationException("La posición tiene más de un hijo.");
		E toReturn = v.element();
		// Si queremos eliminar la raíz
		if(toRemove == root) {
			if(left != null) { // Si tiene un sólo hijo izquierdo.
				root = left;
				left.setPadre(null);
			}
			else
				if(right != null) { // Si tiene un sólo hijo derecho.
					root = right;
					right.setPadre(null);
				}
				else // Existe sólo el nodo raíz.
					root = null;
		}
		// Si queremos eliminar un nodo que no es la raíz.
		else {
			if(left != null) { // Si tiene un sólo hijo izquierdo.
				left.setPadre(parentRemove);
				if(parentRemove.getLeft() == toRemove) // Si el nodo a eliminar era hijo izquierdo de su padre.
					parentRemove.setLeft(left);
				else
					parentRemove.setRight(left); // Si el nodo a eliminar era hijo derecho de su padre.
			}
			else { // Si tiene un sólo hijo derecho.
				if(right != null) {
					right.setPadre(parentRemove);
					if(parentRemove.getLeft() == toRemove) // Si el nodo a eliminar era hijo izquierdo de su padre.
						parentRemove.setLeft(right);
					else 
						parentRemove.setRight(right); // Si el nodo a eliminar era hijo derecho de su padre.
				}
				else { // El nodo a eliminar es una hoja.
					if(parentRemove.getLeft() == toRemove)
						parentRemove.setLeft(null);
					else
						parentRemove.setRight(null);
				}
			}
		}
		size--;
		return toReturn;
	}

	public void attach(Position<E> r, BinaryTree<E> T1, BinaryTree<E> T2) throws InvalidPositionException {
		BTPosition<E> aux = checkPosition(r);
		if(isEmpty()) throw new InvalidPositionException("El árbol está vacío.");
		if(isInternal(r)) throw new InvalidPositionException("No es posible enlazar un sub-árbol en un nodo interno.");
        
		try {
			if(!T1.isEmpty()) {				
				BTPosition<E> rootT1 = checkPosition(T1.root());
				BTPosition<E> nodoT1 = new BTNodo<E>(rootT1.element(),null, null, aux);
				aux.setLeft(nodoT1);
				attachClonarHijos(nodoT1, rootT1);
			}
			if(!T2.isEmpty()) {				

				BTPosition<E> rootT2 = checkPosition(T2.root());
				BTPosition<E> nodoT2 = new BTNodo<E>(rootT2.element(),null,null,aux);
				aux.setRight(nodoT2);
				attachClonarHijos(nodoT2, rootT2);
			}


		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
		size++;
	}
	
	private void attachClonarHijos(BTPosition<E> r, BTPosition<E> tPos) {
		BTPosition<E> aux;
		if(tPos.getLeft() != null) {
			aux = new BTNodo<E>(tPos.element(),null,null,r);
			r.setLeft(aux);
			attachClonarHijos(aux,tPos.getLeft());
		}
		if(tPos.getRight() != null) {
			aux = new BTNodo<E>(tPos.element(),null,null,r);
			r.setRight(aux);
			attachClonarHijos(aux,tPos.getRight());
		}
	}
	
	// Ejercicio 1 e)
	public BinaryTree<E> clone() {
		BinaryTree<E> clone = new ArbolBinario<E>();
		try {
			if(!isEmpty()) {
				BTPosition<E> rootClone = (BTNodo<E>)clone.createRoot(root.element());
				cloneRec(clone,(BTNodo<E>)this.root, rootClone);
			}
		} catch (InvalidOperationException e) { throw new RuntimeException(e); }
		return clone;
	}
	
	private void cloneRec(BinaryTree<E> clone, BTPosition<E> root, BTPosition<E> rootClone) {
		BTNodo<E> posInsertClone;
		try {
			if(hasLeft(root)) {
				posInsertClone = (BTNodo<E>) clone.addLeft(rootClone,root.getLeft().element());
				cloneRec(clone, (BTNodo<E>) root.getLeft(), posInsertClone);
			}
			if(hasRight(root)) {
				posInsertClone = (BTNodo<E>) clone.addRight(rootClone, root.getRight().element());
				cloneRec(clone, (BTNodo<E>) root.getRight(), posInsertClone);
			}
		} catch (InvalidPositionException | InvalidOperationException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void preOrden(Position<E> v, PositionList<Position<E>> list) {
		list.addLast(v);
		try {
			if(hasLeft(v))
				preOrden(left(v),list);
			if(hasRight(v))
			    preOrden(right(v),list);
		} catch (InvalidPositionException | BoundaryViolationException e) {
			throw new RuntimeException(e);
		}
	}
	
	// Ejercicio 2 b)
	public BinaryTree<E> mirror() {
		BinaryTree<E> mirror = this.clone();
		try {
			BTPosition<E> rootMirror = (BTNodo<E>) mirror.root();
			mirrorRecursive(rootMirror);
		} catch (EmptyTreeException e) {
			throw new RuntimeException(e);
		}
		return mirror;
 	}
	
	private void mirrorRecursive(BTPosition<E> nodo) {
		invertirHijos(nodo);
		if(nodo.getLeft() != null)
			mirrorRecursive((BTNodo<E>)nodo.getLeft());
		if(nodo.getRight() != null)
			mirrorRecursive((BTNodo<E>)nodo.getRight());
	}
	
	private void invertirHijos(BTPosition<E> nodo) {
		BTPosition<E> aux  = (BTNodo<E>) nodo.getLeft();
		nodo.setLeft(nodo.getRight());
		nodo.setRight(aux);
	}
	
	private BTNodo<E> checkPosition(Position<E> v) throws InvalidPositionException {
		 BTNodo<E> nodo = null;
		 if(v == null)
			 throw new InvalidPositionException("La posición es nula.");
		 try {
			 nodo = (BTNodo<E>) v;
		 } catch (ClassCastException e) {
			 throw new InvalidPositionException("No se lo puede castear a un objeto de tipo TNodo<E>, o nodo previamente eliminado.");
		 }
		 return nodo;
	}
	
	public void recorrerPorNiveles(BTNodo<E> arbol) {
	    Queue<BTNodo<E>> cola = new LinkedList<>();
	    cola.clear();
	    cola.add(arbol);
	    while (!(cola.isEmpty())) {
	    	BTNodo<E> temp = cola.remove();
	        System.out.print(temp.element() + " ");
	        if (temp.getLeft() != null) {
	            cola.add((BTNodo<E>) temp.getLeft());
	        }
	        if (temp.getRight() != null) {
	            cola.add((BTNodo<E>) temp.getRight());
	        }
	    }
	}
	
	public void recorrerPorNiveles() {
		recorrerPorNivelesRec((BTNodo<E>) root,1);
		System.out.println();
	}

	private void recorrerPorNivelesRec(BTNodo<E> nodo, int nivel){
        if(nodo != null){
        	recorrerPorNivelesRec((BTNodo<E>) nodo.getLeft(),nivel+1);
            System.out.println(nodo.element() + "("+nivel+") - ");
            recorrerPorNivelesRec((BTNodo<E>) nodo.getRight(),nivel+1);
        }
    }
}

