package TDAGrafoD;

import Interfaces.Edge;
import Interfaces.Position;

public class Arco<V,E> implements Edge<E> {
	protected E rotulo;
	protected Vertice<V,E> v1, v2;
	protected Position<Arco<V,E>> posicionEnEmergentes;
	protected Position<Arco<V,E>> posicionEnIncidentes;
	protected Position<Arco<V,E>> posicionEnArcos;
	
	public Arco(E rot, Vertice<V,E> v1, Vertice<V,E> v2) {
		rotulo = rot;
		this.v1 = v1;
		this.v2 = v2;
		posicionEnIncidentes = posicionEnArcos = null;
	}

	public E element() {
		return rotulo;
	}
	
	public void setElement(E rot) {
		rotulo = rot;
	}
	
	public Vertice<V,E> getVertice1(){
		return v1;
	}
	
	public void setVertice1(Vertice<V,E> v1) {
		this.v1 = v1;
	}
	
	public Vertice<V,E> getVertice2(){
		return v2;
	}
	
	public void setVertice2(Vertice<V,E> v2) {
		this.v2 = v2;
	}
	
	public Position<Arco<V,E>> getPosicionEnEmergentes() {
		return posicionEnEmergentes;
	}
	
	public void setPosicionEnEmergentes(Position<Arco<V,E>> p) {
		posicionEnEmergentes = p;
	}
	
	public Position<Arco<V,E>> getPosicionEnIncidentes() {
		return posicionEnIncidentes;
	}
	
	public void setPosicionEnIncidentes(Position<Arco<V,E>> p) {
		posicionEnIncidentes = p;
	}
	
	public Position<Arco<V,E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}
	
	public void setPosicionEnArcos(Position<Arco<V,E>> p) {
		posicionEnArcos = p;
	}
}
