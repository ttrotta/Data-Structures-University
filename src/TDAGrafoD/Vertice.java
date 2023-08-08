package TDAGrafoD;

import Interfaces.Position;
import Interfaces.Vertex;
import TDALista.ListaDoblementeEnlazada;
import Interfaces.PositionList;

public class Vertice<V,E> implements Vertex<V> {
	protected V rotulo;
	protected Position<Vertice<V,E>> posicionEnListaVertices;
	protected PositionList<Arco<V,E>> emergentes, incidentes;
	
	public Vertice(V rot) {
		rotulo = rot;
		emergentes = new ListaDoblementeEnlazada<Arco<V,E>>();
		incidentes = new ListaDoblementeEnlazada<Arco<V,E>>();
		posicionEnListaVertices = null;
	}

	public V element() {
		return rotulo;
	}
	
	public void setRotulo(V rot) {
		rotulo = rot;
	}
	
	public Position<Vertice<V,E>> getPosicionEnListaVertices() {
		return posicionEnListaVertices;
	}
	
	public void setPosicionEnListaVertices(Position<Vertice<V,E>> p) {
		posicionEnListaVertices = p;
	}
	
	public PositionList<Arco<V,E>> getEmergentes() {
		return emergentes;
	}
	
	public void setEmergentes(PositionList<Arco<V,E>> e) {
		emergentes = e;
	}
	
	public PositionList<Arco<V,E>> getIncidentes() {
		return incidentes;
	}
	
	public void setIncidentes(PositionList<Arco<V,E>> i) {
		incidentes = i;
	}
}
