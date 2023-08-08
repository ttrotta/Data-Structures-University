package TDAGrafo;

import Interfaces.Vertex;
import Interfaces.Position;

public class VerticeM<V> implements Vertex<V> {
	protected V rotulo;
	protected int indice;
	protected Position<Vertex<V>> posicionEnVertices;
	
	public VerticeM(V rot, int ind) {
		rotulo = rot;
		indice = ind;
		posicionEnVertices = null;
	}
	
	public V element() {
		return rotulo;
	}
	
	public void setRotulo(V rot) {
		rotulo = rot;
	}
	
	public int getIndice() {
		return indice;
	}
	
	public void setIndice(int ind) {
		indice = ind;
	}
	
	public Position<Vertex<V>> getPosicionEnVertices(){
		return posicionEnVertices;
	}
	
	public void setPosicionEnVertices(Position<Vertex<V>> p) {
		posicionEnVertices = p;
	}
}
