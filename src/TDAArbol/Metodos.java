package TDAArbol;

import Exceptions.*;
import Interfaces.*;
import TDALista.*;
import TDACola.*;
import TDAPila.*;

public class Metodos {
	// Recorrido post Orden
	public static <E> Iterable<E> recPostOrden(Tree<E> a) {
		PositionList<E> list = new ListaDE<>();
		try {
			postOrden(a,a.root(),list);
		} catch (EmptyTreeException e) { System.out.println(e.getMessage());}
		return list;
	}
	
	public static <E> void postOrden(Tree<E> a, Position<E> position, PositionList<E> list) {
		TNodo<E> nodo = (TNodo<E>) position;
		for(TNodo<E> nodoHijo : nodo.getHijos()) {
			postOrden(a,nodoHijo,list);
		}
		list.addLast(nodo.element());
	}
	
	// Ejercicio 5 a)
	public static <E> Iterable<E> recPorNivel(Tree<E> T) { 
	    PositionList<E> list = new ListaDE<>(); 
	    Queue<TNodo<E>> cola = new ColaEnlazada<TNodo<E>>();
	    try {

	        TNodo<E> padre = checkPosition(T.root());
	        cola.enqueue(padre);
	        cola.enqueue(null);

	        while (!cola.isEmpty()) {
	            TNodo<E> v = cola.dequeue();
	            if (v != null) {
	                System.out.print(v.element() + " ");
	                list.addLast(v.element());
	                for (Position<TNodo<E>> w : v.getHijos().positions()) {
	                    cola.enqueue(w.element());
	                }
	            } else {
	                System.out.println();
	                if (!cola.isEmpty())
	                    cola.enqueue(null);
	            }
	        }
	    } catch (EmptyTreeException | EmptyQueueException | InvalidPositionException | ClassCastException e) {
	        e.printStackTrace();
	    }

	    return list;
	}
	
	// Ejercicio 5 b) - Terminar
	public static <E> Iterable<E> recPorNivelInverso(Tree<E> T) {
		PositionList<E> list = new ListaDE<>(); 
	    Queue<TNodo<E>> cola = new ColaEnlazada<TNodo<E>>();
	    try {

	        TNodo<E> padre = checkPosition(T.root());
	        cola.enqueue(padre);
	        cola.enqueue(null);

	        while (!cola.isEmpty()) {
	            TNodo<E> v = cola.dequeue();
	            if (v != null) {
	                System.out.print(v.element() + " ");
	                list.addFirst(v.element());
	                for (Position<TNodo<E>> w : v.getHijos().positions()) {
	                    cola.enqueue(w.element());
	                }
	            } else {
	                System.out.println();
	                if (!cola.isEmpty())
	                    cola.enqueue(null);
	            }
	        }
	    } catch (EmptyTreeException | EmptyQueueException | InvalidPositionException | ClassCastException e) {
	        e.printStackTrace();
	    }

	    return list;
	}

	
	// Ejercicio 6 a)
	
	// Ejericico 6 c)
	/* Escriba un método tal que, dado un árbol A, elimine todas las hojas de A (deben quedar 
	   solo los nodos interiores del árbol original).
	*/
	
	public static <E> void eliminarHojas(Tree<E> A) {
		
	}
	
	// Ejercicio 9
	// Dadas dos posiciones P1 y P2 y un árbol T, retornar el ancestro común más cercano entre P1 y P2. 
	public static <E> Position<E> ancestroComun(Position<E> p1, Position<E> p2, Tree<E> T) {
		Stack<Position<E>> pila1 = new PilaEnlazada<Position<E>>();
		Stack<Position<E>> pila2 = new PilaEnlazada<Position<E>>();
		Position<E> ancestroComun = null;
		
		try {
			// Apilamos la primera posición hasta la raíz.
			while(p1 != T.root()) {
				pila1.push(p1);
				p1 = T.parent(p1);
			}
			pila1.push(T.root());
			// Apilamos la segunda posición hasta la raíz.
			while(p2 != T.root()) {
				pila2.push(p2);
				p2 = T.parent(p2);
			}
			pila2.push(T.root());
			
			Position<E> tope1 = pila1.pop();
			Position<E> tope2 = pila2.pop();
			while(tope1 == tope2) { // Si o si entran xq el tope de ambas es la raiz
				ancestroComun = tope1;
				if(!pila1.isEmpty())
					tope1 = pila1.pop();
				if(!pila2.isEmpty())
					tope2 = pila2.pop();
			}
		} catch (InvalidPositionException | BoundaryViolationException | EmptyTreeException | EmptyStackException e) { e.printStackTrace(); }
		
		return ancestroComun;
	}
	
	
	public static <E> PositionList<E> altura(E r, Arbol<E> T) {
		PositionList<E> camino = new ListaDE<E>();
		TNodo<E> hojaN = null;
		try {
			hojaN = preB((TNodo<E>) T.root(), r, T);
			if (hojaN == null)
				System.out.println("El nodo no se encuentra en el árbol.");
			else
				preR(hojaN,T,camino);
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
		return camino;
	}
	
	private static <E> TNodo<E> preB(TNodo<E> n, E r, Arbol<E> t){
		TNodo<E> ret = null;
		if (n.element().equals(r)) {
			ret = n;
		}
		else {
			for (TNodo<E> hijo : n.getHijos()) {
				if (ret == null)
					ret = preB(hijo,r,t);
				}
		}
		return ret;
	}
	
	private static <E> int preR(TNodo<E> R, Arbol<E> t, PositionList<E> l){
		int mayor = 0;
		int aux = 1;
		try {
			for (TNodo<E> hijos : R.getHijos()) {
				aux += preR(hijos,t,l);
				if (aux > mayor) {
					for (int i = 0; i < mayor; i++) {
						l.remove(l.first());
					}
					mayor = aux;
				}
				else {
					for (int i = 0; i < aux; i++) {
						l.remove(l.last());
					}
				}
				aux = 1;
			}
			l.addLast(R.element());
		} catch (InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
		return mayor;
	}
	
	private static <E> TNodo<E> checkPosition(Position<E> v) throws InvalidPositionException {
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
	
	public static void main(String args []) {
		Tree<Integer> arbol = new Arbol<Integer>();
		try {
			arbol.createRoot(1);
			Position<Integer> raiz = arbol.root();
			
			Position<Integer> p2 = arbol.addLastChild(raiz, 2);
			Position<Integer> p3 = arbol.addLastChild(raiz, 3);
			arbol.addLastChild(raiz, 4);
			
			arbol.addLastChild(p2, 5);
			arbol.addLastChild(p2, 6);
			
			Position<Integer> p7 = arbol.addLastChild(p3, 7);
			
			Position<Integer> p8 = arbol.addLastChild(p7, 8);
			arbol.addLastChild(p7, 9);
			
			arbol.addLastChild(p8, 10);
			
			System.out.println();
			System.out.println();
			
			for(Position<Integer> elem : arbol.positions()) {
				System.out.print(elem.element() + " - ");
			}
			
			System.out.println();
			
			for(Integer elem : recPostOrden(arbol)) {
				System.out.print(elem + " - ");
			}
			System.out.println();
			System.out.println();
			
			recPorNivel(arbol);
			
			System.out.println();
			System.out.println();
			
		} catch (InvalidOperationException | EmptyTreeException | InvalidPositionException e) {
			e.printStackTrace();
		}
		
		Tree<Character> arbolDos = new Arbol<Character>();
		try {
			arbolDos.createRoot('A');
			Position<Character> raizS = arbolDos.root();
			
			Position<Character> pb = arbolDos.addLastChild(raizS, 'B');
			Position<Character> pc = arbolDos.addLastChild(raizS, 'C');
			Position<Character> pd = arbolDos.addLastChild(raizS, 'D');
			
			Position<Character> ph = arbolDos.addLastChild(pb, 'H');
			Position<Character> pf = arbolDos.addLastChild(pb, 'F');
			
			Position<Character> pk = arbolDos.addLastChild(ph, 'K');
			Position<Character> pg = arbolDos.addLastChild(ph, 'G');
			
			Position<Character> pe = arbolDos.addLastChild(pc, 'E');
			
			Position<Character> pl = arbolDos.addLastChild(pd, 'L');
			Position<Character> pz = arbolDos.addLastChild(pd, 'Z');
			
			Position<Character> pm = arbolDos.addLastChild(pl, 'M');
			arbolDos.addLastChild(pm, 'P');
			
			for(Position<Character> elem : arbolDos.positions()) {
				System.out.print(elem.element() + " - ");
			}
			
			System.out.println();
			
			for(Character elem : recPostOrden(arbolDos)) {
				System.out.print(elem + " - ");
			}
			System.out.println();
			System.out.println();
			
			recPorNivel(arbolDos);
			
			System.out.println();
			System.out.println();
			
			Position<Character> ancestro1 = ancestroComun(pk,pf,arbolDos);
			System.out.println("El ancestro en común entre k y f debería ser b: "+ ((TNodo<Character>) ancestro1).element());
			
			Position<Character> ancestro2 = ancestroComun(pe,pl,arbolDos);
			System.out.println("El ancestro en común entre e y l debería ser a: "+ ((TNodo<Character>) ancestro2).element());
			
			Position<Character> ancestro3 = ancestroComun(pl,pz,arbolDos);
			System.out.println("El ancestro en común entre l y z debería ser d: "+ ((TNodo<Character>) ancestro3).element());
			
			Position<Character> ancestro4 = ancestroComun(pg,ph,arbolDos);
			System.out.println("El ancestro en común entre g y h debería ser h: "+ ((TNodo<Character>) ancestro4).element());
			
			System.out.println();
			System.out.println();
			
			((Arbol<Character>)arbolDos).invertirHijos('A');
			
			recPorNivel(arbolDos);
			
			System.out.println();
			System.out.println();

		} catch (InvalidOperationException | EmptyTreeException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}
}
