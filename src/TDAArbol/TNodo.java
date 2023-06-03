package TDAArbol;

import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.ListaDE;

public class TNodo<E> implements Position<E> {
	    protected E elem;
	    protected TNodo<E> padre;
	    protected PositionList<TNodo<E>> hijos;

	    public TNodo(E e, TNodo<E> p) {
	        elem = e;
	        padre = p;
	        hijos = new ListaDE<TNodo<E>>();
	    }

	    public TNodo(E elem) {
	        this(elem, null);
	    }

	    public E element() {
	        return elem;
	    }
	    
	    public PositionList<TNodo<E>> getHijos() {
	        return hijos;
	    }
	    
	    public void setElement(E e) {
	        elem = e;
	    }

	    public TNodo<E> getPadre() {
	        return padre;
	    }

	    
	    public void setPadre(TNodo<E> p) {
	        padre = p;
	    }
	}