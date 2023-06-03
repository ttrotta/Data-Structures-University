package TDAArbolBinario;

import java.util.Iterator;

import Exceptions.*;
import Interfaces.BTPosition;
import Interfaces.BinaryTree;
import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.*;

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
			preOrden((BTNodo<E>)root, list);
		return list;
	}

	public E replace(Position<E> v, E e) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
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
		BTNodo<E> nodo = checkPosition(v);
		if(nodo == root)
			throw new BoundaryViolationException("La raíz no tiene padre.");
		return nodo.getPadre();
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
		BTNodo<E> nodo = checkPosition(v);
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
		BTNodo<E> nodo = checkPosition(v);
		if(nodo.getLeft() == null)
			throw new BoundaryViolationException("No existe el BTNodo izquierdo.");
		return nodo.getLeft();
	}

	public Position<E> right(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
		BTNodo<E> nodo = checkPosition(v);
		if(nodo.getRight() == null)
			throw new BoundaryViolationException("No existe el BTNodo derecho.");
		return nodo.getRight();
	}

	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
		return nodo.getLeft() != null;
	}

	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		BTNodo<E> nodo = checkPosition(v);
		return nodo.getRight() != null;
	}

	public Position<E> addLeft(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		BTNodo<E> nodo = checkPosition(v);
		if(hasLeft(v))
			throw new InvalidOperationException("Ya hay un hijo derecho en la posición.");
		BTNodo<E> toInsert = new BTNodo<E>(r, null, null, (BTNodo<E>) v);
		nodo.setLeft(toInsert);
		size++;
		return toInsert;
	}

	public Position<E> addRight(Position<E> v, E r) throws InvalidOperationException, InvalidPositionException {
		if(isEmpty())
			throw new InvalidPositionException("El árbol está vacío.");
		BTNodo<E> nodo = checkPosition(v);
		if(hasRight(v))
			throw new InvalidOperationException("Ya hay un hijo derecho en la posición.");
		BTNodo<E> toInsert = new BTNodo<E>(r, null, null, (BTNodo<E>) v);
		nodo.setRight(toInsert);
		size++;
		return toInsert;
	}

	public E remove(Position<E> v) throws InvalidOperationException, InvalidPositionException {
		BTNodo<E> toRemove = checkPosition(v);
		BTNodo<E> parentRemove = (BTNodo<E>) toRemove.getPadre();
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
		BTNodo<E> nodo = checkPosition(r);
		try {
            if(isInternal(r))
                throw new InvalidPositionException("No es posible enlazar un sub árbol en un nodo interno.");
            if(!T1.isEmpty()){
                BTNodo<E> rootLeft = (BTNodo<E>) T1.root();
                nodo.setLeft(rootLeft);
                size += T1.size();
            }
            if(!T2.isEmpty()) {
                BTNodo<E> rootRight = (BTNodo<E>) T2.root();
                nodo.setRight(rootRight);
                size += T2.size();
            }
        } catch (EmptyTreeException e) {
            throw new RuntimeException(e);
        }
	}
	
	private void preOrden(BTNodo<E> v, PositionList<Position<E>> list) {
		list.addLast(v);
		try {
			if(hasLeft(v))
				preOrden((BTNodo<E>)v.getLeft(),list);
			if(hasRight(v))
			    preOrden((BTNodo<E>)v.getRight(),list);
		} catch (InvalidPositionException e) {
			throw new RuntimeException(e);
		}
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
}
