package TDAArbolBinario;

import Interfaces.BinaryTree;
import Interfaces.Position;
import Interfaces.PositionList;
import Interfaces.Stack;

import Exceptions.*;
import TDAPila.*;
import TDALista.*;

public class Metodos {
	/* Ejercicio 2 c)
	 Escriba un método tal que dados dos nodos N1 y N2 
	 de un AB A indique si existe un camino entre N1 y N2. */
	public static <E> boolean existeCamino(Position<E> N1, Position<E> N2, BinaryTree<E> A) {
		Stack<Position<E>> pila1 = new PilaEnlazada<Position<E>>();
		Stack<Position<E>> pila2 = new PilaEnlazada<Position<E>>();
		boolean found = false;
		if(N1 == N2)
			found = true;
		else {
			try {
				Position<E> pos1 = N1;
				// Charging the first stack
				while(!A.isRoot(pos1)) {
					pila1.push(pos1);
					pos1 = A.parent(pos1);
				}
				pila1.push(A.root());

				Position<E> pos2 = N1;
				// Charging the second stack
				while(!A.isRoot(pos2)) {
					pila2.push(pos2);
					pos2 = A.parent(pos2);
				}
				pila2.push(A.root());

				Position<E> tope1 = pila1.pop();
				Position<E> tope2 = pila2.pop();
				while(tope1 == tope2 && !found) {
					if(!pila1.isEmpty())
						tope1 = pila1.pop();
					if(!pila2.isEmpty())
						tope2 = pila2.pop();
					
					if(tope1.equals(tope2) && pila1.isEmpty() && !pila2.isEmpty()) 
						found = true;
					else {
						if(tope1.equals(tope2) && !pila1.isEmpty() && pila2.isEmpty())
							found = true;
					}
				}
			} catch (InvalidPositionException | BoundaryViolationException | EmptyTreeException | EmptyStackException e) {
				e.printStackTrace();
			}
		}
		return found;
	}
	
	// Ejercicio 2 e)
	// Escriba un método que indique la profundidad de un nodo N de un AB A, e indique el
	// camino que la justifica.
	public static <E> Iterable<Position<E>>  profundidad(BinaryTree<E> T, Position<E> v) {
		PositionList<Position<E>> list = new ListaDoblementeEnlazada<Position<E>>(); 
		Position<E> aux = v;
		try {
			while(aux != T.root()) {
				list.addFirst(aux);
				aux = T.parent(aux);
			}
			list.addFirst(T.root());
		} catch (EmptyTreeException | InvalidPositionException | BoundaryViolationException e) {
			e.printStackTrace();
		}
		return list; // Si queres ver la profundidad simplemente se utiliza list.size() - 1
	}
	
	// Ejercicio 2 f)
	// Escriba un método para hallar la altura de un nodo N de un AB A e indique el camino que la
	// justifica.
	public static <E> int altura(BinaryTree<E> A, Position<E> v) {
		int h = 0;
		try {
			for(Position<E> c : A.children(v))
				h = Math.max(h, 1 + altura(A,c));
		} catch (InvalidPositionException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	/* public static <E> Iterable<Position<E>> caminoAltura(Position<E> p) throws InvalidPositionException {
        BTPosition<E> n = checkPosition(p);
        Position<E> hojaMasLejana = null;
        boolean encontre = false;
        PositionList<Position<E>> l= new ListaDoblementeEnlazada<Position<E>>();
        int alt = altura(p);
        pre(n,l);
        Iterator<Position<E>> it = l.iterator();
        while(it.hasNext() && !encontre) {
            hojaMasLejana = it.next();
            if(isExternal(hojaMasLejana) && profundidad(hojaMasLejana,p)==alt)
                encontre = true;
        }
        return caminoProfundidad(hojaMasLejana,p);
    } */
	
	public static <E> Iterable<Position<E>> alturaCipra(BinaryTree<E> t, Position<E> n){
		PositionList<Position<E>> l = new ListaDE<Position<E>>();
		alturaCipraRec(n,t,l);
		return l;
	}

	private static <E> int alturaCipraRec(Position<E> n, BinaryTree<E> t, PositionList<Position<E>> l) {
		int mayor = 0;
		int aux = 0;
		try {
			if (t.isExternal(n))
				mayor = 1;
			l.addLast(n);
				for (Position<E> h : t.children(n)) {
					aux = 1 + alturaCipraRec((BTNodo<E>) h,t,l);
					if (aux > mayor) {
						for (int i = 0; i < mayor;i++) {
							l.remove(l.first());
						}
						mayor = aux;
					}
					else {
						if (aux <= mayor) {
							for (int j = 1; j < aux; j++) {
								l.remove(l.last());
							}
						}
					}
					aux = 0;
				}
		} catch (InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
		return mayor;
	}
	
	private static <E> void mostrarCaminoAltura(BinaryTree<E> A, Position<E> p) {
		for(Position<E> v : alturaCipra(A,p)) {
			System.out.print(v + " - ");
		}
	}
	
	// Ejercicio 3 b) Equals en Profundidad de un Árbol Binario
	public static <E> boolean equivalentes(Position<E> p1, BinaryTree<E> A1, Position<E> p2, BinaryTree<E> A2) {
		boolean retorno = false;
		if(p1 == null && p2 == null)
			return true;
		else {
			if(p1 == null || p2 == null)
				return false;
			else {
				try {
					if(p1.element().equals(p2.element())) {
						Position<E> sub1, sub2;
						if(A1.hasLeft(p1)) sub1 = A1.left(p1);
						else sub1 = null;

						if(A2.hasLeft(p2)) sub2 = A2.left(p2);
						else sub2 = null;

						retorno = equivalentes(sub1, A1, sub2, A2);
						// Si el subarbol izquierdo es igual, pasamos al derecho
						if(retorno) {
							if(A1.hasRight(p1)) sub1 = A1.right(p1);
							else sub1 = null;

							if(A2.hasRight(p2)) sub2 = A2.right(p2);
							else sub2 = null;

							retorno = equivalentes(sub1, A1, sub2, A2);
						}
					}
				} catch(InvalidPositionException | BoundaryViolationException e) {
					e.printStackTrace();
				}
			}
		}
		return retorno;
	}

	// Ejercicio 3 c)
	// Escriba un método tal que dados dos árboles A y A1 determine si A1 es un subárbol de A
	public static <E> boolean esSubArbol(BinaryTree<E> A1, BinaryTree<E> A) {
		Position<E> raizA1 = null;
		try { raizA1 = A1.root(); } catch(EmptyTreeException e) { e.printStackTrace(); }
		for(Position<E> p : A.positions()) {
			if(equivalentes(raizA1,A1,p,A)) 
				return true;
		}
		return false;
	}
	
	@SuppressWarnings("static-access")
	public static <E> void main(String args []) {
		try {
			ArbolBinario<Integer> arbol = new ArbolBinario<Integer>();
			arbol.createRoot(20);
			Position<Integer> raiz = arbol.root();
			
			Position<Integer> p16 = arbol.addLeft(raiz,16);
			Position<Integer> p64 = arbol.addRight(raiz,64);

			Position<Integer> p6 = arbol.addLeft(p16,6);
			Position<Integer> p18 = arbol.addRight(p16,18);
			
			Position<Integer> p37 = arbol.addLeft(p64,37);
			Position<Integer> p78 = arbol.addRight(p64,78);
			
			arbol.addLeft(p18, 17);
			
			Position<Integer> p50 = arbol.addRight(p37,50);
			/*Position<Integer> p98 */arbol.addRight(p78, 98);
			
			arbol.addRight(p50, 60);
	
			TreePrinter t = new TreePrinter();
			t.mostrarPorNiveles((BTNodo<Integer>) arbol.root());
			
			System.out.println();
			System.out.println("Testeamos la altura de 16, debería ser 2: "+ altura(arbol,p16)); mostrarCaminoAltura(arbol,p16); System.out.println();
			System.out.println("Testeamos la altura de 20, debería ser 4: "+ altura(arbol,raiz)); mostrarCaminoAltura(arbol,raiz); System.out.println(); 
			System.out.println("Testeamos la altura de 64, debería ser 3: "+ altura(arbol,p64)); mostrarCaminoAltura(arbol,p64); System.out.println();
			System.out.println("Testeamos la altura de 78, debería ser 1: "+ altura(arbol,p78)); mostrarCaminoAltura(arbol,p78); System.out.println();
			System.out.println("Testeamos la altura de 6, debería ser 0: "+ altura(arbol,p6)); mostrarCaminoAltura(arbol,p6); System.out.println();
			System.out.println();

			System.out.println();
			System.out.println("Testeamos el árbol espejo: ");
			System.out.println();
			ArbolBinario<Integer> arbolEspejo = (ArbolBinario<Integer>) arbol.mirror();
			t.mostrarPorNiveles((BTNodo<Integer>) arbolEspejo.root());
			
			System.out.println();
			System.out.println();
			/*
			System.out.println("¿Existe un camino entre 16 y 18? Se espera true --> " + existeCamino(p16,p18,arbol));
			System.out.println("¿Existe un camino entre 6 y 98? Se espera false --> " + existeCamino(p6,p98,arbol));
			System.out.println();
			System.out.println("¿Existe un camino entre 18 y 16? Se espera true --> " + existeCamino(p18,p16,arbol));
			System.out.println("¿Existe un camino entre 98 y 6? Se espera false --> " + existeCamino(p98,p6,arbol));
			System.out.println();
			System.out.println("¿Existe un camino entre 16 y 16? Se espera true --> " + existeCamino(p16,p16,arbol));
			*/
			System.out.println();
			System.out.println();
			System.out.println("¿Son los dos árboles iguales? Se espera true: " + equivalentes(raiz,arbol,raiz,arbol));
			System.out.println("¿Son los dos árboles iguales? Se espera false: " + equivalentes(raiz,arbol,arbolEspejo.root(),arbolEspejo));

			ArbolBinario<Integer> subArbol = new ArbolBinario<Integer>();
			
			subArbol.createRoot(16);
			Position<Integer> raizSub = subArbol.root();
			
			Position<Integer> p18S = subArbol.addLeft(raizSub,18);
			subArbol.addRight(raizSub,6);

			arbol.addRight(p18S,17);
			System.out.println("¿A es subárbol de A1? Se espera true: " + esSubArbol(subArbol,arbolEspejo));
			subArbol.addLeft(p18S,43);
			System.out.println("¿A es subárbol de A1? Se espera false: " + esSubArbol(subArbol,arbolEspejo));
			System.out.println("¿A es subárbol de A? Se espera true: " + esSubArbol(subArbol,subArbol));
			
		} catch(EmptyTreeException | InvalidOperationException | InvalidPositionException e) {
			e.printStackTrace();
		}
	}	
}
