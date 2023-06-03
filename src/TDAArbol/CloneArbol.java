package TDAArbol;

import Interfaces.Position;
import Interfaces.Tree;
import Exceptions.*;

public class CloneArbol<E> extends Arbol<E> {
	public CloneArbol() {
		super();
	}
	
	public Tree<E> clone() {
		Tree<E> toReturn = new Arbol<E>();
		if(!isEmpty()) {
			try {
				toReturn.createRoot(root.element());
				auxClone(root,toReturn.root(), toReturn);
			} catch (InvalidOperationException | InvalidPositionException | EmptyTreeException e) {
				e.printStackTrace();
			}
		}
		return toReturn;
	}
	
	private void auxClone(TNodo<E> pos,Position<E> padreClone, Tree<E> treeClone) throws InvalidPositionException{
		TNodo<E> posAux;
		for(TNodo<E> nodo : pos.getHijos()) { // Llamo a los hijos de pos
			try {				
				posAux = (TNodo<E>) treeClone.addLastChild(padreClone, nodo.element());
				auxClone(nodo, posAux, treeClone);
			} catch (InvalidPositionException e) { e.printStackTrace(); }
		}
	}
	
	public static void main(String args []) {
		CloneArbol<Integer> arbol = new CloneArbol<Integer>();
		Tree<Integer> arbolClonado = null;

		try {
			arbol.createRoot(1);
			Position<Integer> raiz = arbol.root();
			
			Position<Integer> p2 = arbol.addLastChild(raiz, 2);
			Position<Integer> p3 = arbol.addLastChild(raiz, 3);
			arbol.addLastChild(raiz, 4);
			
			arbol.addLastChild(p2, 5);
			arbol.addLastChild(p2, 6);
			
			Position<Integer> p7 = arbol.addLastChild(p3, 7);
			
			arbol.addLastChild(p7, 8);
			arbol.addLastChild(p7, 9);
			
			for(Position<Integer> elem : arbol.positions()) {
				System.out.print(elem.element() + " - ");
			}
			System.out.println();
			arbolClonado = arbol.clone();
			for(Position<Integer> elem : arbolClonado.positions()) {
				System.out.print(elem.element() + " - ");
			}
		} catch (InvalidOperationException | EmptyTreeException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
}