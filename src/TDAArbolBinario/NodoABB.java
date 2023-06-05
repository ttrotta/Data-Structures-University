package TDAArbolBinario;

public class NodoABB<E extends Comparable<E>> {
	private E rotulo;
	private NodoABB<E> padre, izq, der;
	
	public NodoABB(E r, NodoABB<E> p) {
		rotulo = r;
		padre = p;
		izq = der = null;
	}
	
	// Getters	
	public E getRotulo() { return rotulo; }
	public NodoABB<E> getPadre() { return padre; }
	public NodoABB<E> getIzq() { return izq; }
	public NodoABB<E> getDer() { return der; }
	
	// Setters
	public void setRotulo(E r) { rotulo = r; }
	public void setPadre(NodoABB<E> p) { padre = p; }
	public void setIzq(NodoABB<E> i) { izq = i; }
	public void setDer(NodoABB<E> d) { der = d; }
	
	public String toString() {
        return String.valueOf(rotulo);
	}
 }
