package TDAArbolBinario;

import java.util.Comparator;

public class ArbolBinarioDeBusqueda<E extends Comparable<E>> {
	protected NodoABB<E> root;
	protected Comparator<E> comp;
	
	public ArbolBinarioDeBusqueda(Comparator <E> comp) {
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
}
