package TDAArbolBinario;

public class NodoAVL<E> {
	private NodoAVL<E> padre;
	private E rotulo;
	private int altura;
	private boolean eliminado;
	private NodoAVL<E> izq, der;
	
	public NodoAVL(E rotulo) {
		altura = 0;
		padre = null;
		izq = der = null;
		eliminado = false;
	}
	
	// Getters	
		public E getRotulo() { return rotulo; }
		public NodoAVL<E> getPadre() { return padre; }
		public NodoAVL<E> getIzq() { return izq; }
		public NodoAVL<E> getDer() { return der; }
		public int getAltura() { return altura; }
		public boolean getEliminado() { return eliminado; }
		
		// Setters
		public void setRotulo(E r) { rotulo = r; }
		public void setPadre(NodoAVL<E> p) { padre = p; }
		public void setIzq(NodoAVL<E> i) { izq = i; }
		public void setDer(NodoAVL<E> d) { der = d; }
		public void setAltura(int a) { altura = a; }
		public void setEliminado(boolean b) { eliminado = b; }
		
		public String toString() {
	        return String.valueOf(rotulo);
		}
}
