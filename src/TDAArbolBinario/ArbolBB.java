package TDAArbolBinario;

import java.util.Comparator;

import Interfaces.Stack;
import TDAPila.*;
import Exceptions.EmptyStackException;

public class ArbolBB<E extends Comparable<E>> {
	protected NodoABB<E> root;
	protected Comparator<E> comp;
	
	public ArbolBB(Comparator <E> comp) {
		root = new NodoABB<E>(null,null); // <- nodo dummy
		this.comp = comp;
	}
	
	public NodoABB<E> buscar(E x) {
		return buscarAux(x,root);
	}
	
	private NodoABB<E> buscarAux(E x, NodoABB<E> p) {
		if(p.getRotulo() == null) // found a dummy node
			return p;
		else {
			int c = comp.compare(x,p.getRotulo());
			if(c == 0)
				return p;
			else 
				if(c < 0)
					return buscarAux(x,p.getIzq());
				else
					return buscarAux(x,p.getDer());
		}
	}
	 
	public void expandir(NodoABB<E> p) {
		p.setIzq(new NodoABB<E>(null,p));
		p.setDer(new NodoABB<E>(null,p));
	}
	
	public void eliminar(NodoABB<E> p) {
		if(isExternal(p)) {
			p.setRotulo(null);
			p.setIzq(null);
			p.setDer(null);
		}
		else {
			if(p == root) {
				if(soloTieneHijoIzquierdo(p)) {
					p.getIzq().setPadre(null);
					root = p.getIzq();
					p.setIzq(null);
				}
				else {
					if(soloTieneHijoDerecho(p)) {
						p.getDer().setPadre(null);
						root = p.getDer();
						p.setDer(null);
					}
					else { // Raíz con dos Nodos
						p.setRotulo(eliminarMinimo(p.getDer()));
					}
				}
			}
			else {
				if(soloTieneHijoIzquierdo(p)) {
					if(p.getPadre().getIzq() == p)
						p.getPadre().setIzq(p.getIzq());
					else
						p.getPadre().setDer(p.getIzq());
					p.getIzq().setPadre(p.getPadre());
				}
				else {
					if(soloTieneHijoDerecho(p)) {
						if(p.getPadre().getIzq() == p)
							p.getPadre().setIzq(p.getDer());
						else
							p.getPadre().setDer(p.getDer());
						p.getIzq().setPadre(p.getPadre());
					}
					else
						p.setRotulo(eliminarMinimo(p.getDer()));
				}
			}
		}
	}
	
	public NodoABB<E> getRaiz() {
		return root;
	}
	
	private boolean isExternal(NodoABB<E> p) {
		return p.getIzq().getRotulo() == null && p.getDer().getRotulo() == null;
	}
	
	private boolean soloTieneHijoIzquierdo(NodoABB<E> p) {
		return p.getIzq().getRotulo() != null && p.getDer().getRotulo() == null;
	}
	
	private boolean soloTieneHijoDerecho(NodoABB<E> p) {
		return p.getIzq().getRotulo() == null && p.getDer().getRotulo() != null;
	}
	
	private E eliminarMinimo(NodoABB<E> p) {
		if(p.getIzq().getRotulo() == null) {
			E toReturn = p.getRotulo();
			if(p.getDer().getRotulo() == null) {
				p.setRotulo(null);
				p.setIzq(null);
				p.setDer(null);
			}
			else {
				p.getPadre().setDer(p.getDer()); // Porque a la llamada del metodo le pasamos el derecho (in order)
				p.getDer().setPadre(p.getPadre());			
			}
			return toReturn;
		}
		else 
			return eliminarMinimo(p.getIzq());
	}
	
	// Ejercicio 7
	/* Resuelva el siguiente problema: Dados dos ABB A y B se desea construir un tercer ABB C donde
 	 los elementos de C son aquellos que se hallan en A y no en B. Asuma que la solución al
	 problema es un método de la clase ABB y que A recibe el mensaje. Estime el orden del tiempo
	 de ejecución de su solución justificando apropiadamente.
	*/
	public ArbolBB<E> diferencia(ArbolBB<E> B) {
		ArbolBB<E> C = new ArbolBB<E>(comp);
		diferenciaRec(root,B,C);
		return C;
	}
	
	private void diferenciaRec(NodoABB<E> a, ArbolBB<E> B, ArbolBB<E> C) {
		NodoABB<E> elementoB = B.buscar(a.getRotulo());
		NodoABB<E> aux = null;
		if(elementoB.getRotulo() == null) {
			aux = C.buscar(a.getRotulo()); // <-- Ask
			aux.setRotulo(a.getRotulo());
			C.expandir(aux);
		}
		if(a.getIzq().getRotulo() != null) diferenciaRec(a.getIzq(),B,C);
		if(a.getDer().getRotulo() != null) diferenciaRec(a.getDer(),B,C);
	}	

	// Ejercicio 9
	/* Programe una operación llamada Ejercicio14 que forma parte de la clase árbol binario de
	búsqueda (y por lo tanto tiene acceso a la estructura interna del mismo) que recibe un rótulo
	de nodo y debe retornar el rótulo del predecesor inorder de dicho nodo. Programe todas las
	operaciones auxiliares de árbol binario de búsqueda que use. Calcule el orden del tiempo de
	ejecución de su solución justificando apropiadamente. */
	public E ejercicio14(E rotulo) {
		E toReturn = null;
		Stack<E> pila = new PilaEnlazada<E>();
		PreRotuloRec(pila, root);
		boolean encontre = false;
		try {
			while(!pila.isEmpty() && !encontre) {
				if(pila.top().equals(rotulo)) {
					encontre = true;
				}
				toReturn = pila.pop();
			}			
			if(!pila.isEmpty()) toReturn = pila.pop();
		}catch(EmptyStackException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	private void PreRotuloRec(Stack<E> p, NodoABB<E> nodo) {
		if(nodo.getIzq().getRotulo() != null) PreRotuloRec(p,nodo.getIzq());
		p.push(nodo.getRotulo());
		if(nodo.getDer().getRotulo() != null) PreRotuloRec(p,nodo.getDer());
	}
}
