package TDAGrafo;

import Interfaces.Vertex;
import Interfaces.Position;
import Interfaces.PositionList;
import TDALista.*;

public class Vertice<V,E> implements Vertex<V> {
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
	
}
