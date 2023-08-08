package TDAGrafoDecoradoMetodos;

import Interfaces.Position;
import TDAMapeo.MapOpenAdressing;

public class VerticeD<V,E> extends MapOpenAdressing<Object,Object> implements Vertex<V> {
	protected V rotulo;
	protected Position<VerticeD<V,E>> posicionEnListaVertices;
	protected PositionList<ArcoD<V,E>> emergentes, incidentes;
	
	public VerticeD(V rot) {
		rotulo = rot;
		emergentes = new ListaDoblementeEnlazada<ArcoD<V,E>>();
		incidentes = new ListaDoblementeEnlazada<ArcoD<V,E>>();
		posicionEnListaVertices = null;
	}

	public V element() {
		return rotulo;
	}
	
	public void setRotulo(V rot) {
		rotulo = rot;
	}
	
	public Position<VerticeD<V,E>> getPosicionEnListaVertices() {
		return posicionEnListaVertices;
	}
	
	public void setPosicionEnListaVertices(Position<VerticeD<V,E>> p) {
		posicionEnListaVertices = p;
	}
	
	public PositionList<ArcoD<V,E>> getEmergentes() {
		return emergentes;
	}
	
	public void setEmergentes(PositionList<ArcoD<V,E>> e) {
		emergentes = e;
	}
	
	public PositionList<ArcoD<V,E>> getIncidentes() {
		return incidentes;
	}
	
	public void setIncidentes(PositionList<ArcoD<V,E>> i) {
		incidentes = i;
	}
	
	public String toString() {
		return "Vértice " + rotulo;
	}
}
