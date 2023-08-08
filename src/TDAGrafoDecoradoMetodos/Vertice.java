package TDAGrafoDecoradoMetodos;

import Interfaces.Position;
import TDAMapeo.MapOpenAdressing;

public class Vertice<V,E> extends MapOpenAdressing<Object,Object> implements Vertex<V> {
	protected V rotulo;
	protected PositionList<Arco<V,E>> adyacentes;
	protected Position<Vertice<V,E>> posicionEnNodos;
	
	public Vertice(V rot) {
		rotulo = rot;
		adyacentes = new ListaDoblementeEnlazada<Arco<V,E>>();
		posicionEnNodos = null;
	}
	
	public V element() {
		return rotulo;
	} 
	
	public void setRotulo(V rot) {
		rotulo = rot;
	}
	
	public PositionList<Arco<V,E>> getAdyacentes() {
		return adyacentes;
	}
	
	public void setAdyacentes(PositionList<Arco<V,E>> a) {
		adyacentes = a;
	}
	
	public Position<Vertice<V,E>> getPosicionEnNodos() {
		return posicionEnNodos;
	}
	
	public void setPosicionEnNodos(Position<Vertice<V,E>> p) {
		posicionEnNodos = p;
	}
	
	public String toString() {
	    return rotulo + " ";
	}
}