package TDAGrafoDecoradoMetodos;

import Interfaces.Position;
import TDAMapeo.MapOpenAdressing;

public class ArcoD<V,E> extends  MapOpenAdressing<Object,Object> implements Edge<E> {
	protected E rotulo;
	protected VerticeD<V,E> v1, v2;
	protected Position<ArcoD<V,E>> posicionEnEmergentes;
	protected Position<ArcoD<V,E>> posicionEnIncidentes;
	protected Position<ArcoD<V,E>> posicionEnArcos;
	
	public ArcoD(E rot, VerticeD<V,E> v1, VerticeD<V,E> v2) {
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
	
	public VerticeD<V,E> getVertice1(){
		return v1;
	}
	
	public void setVertice1(VerticeD<V,E> v1) {
		this.v1 = v1;
	}
	
	public VerticeD<V,E> getVertice2(){
		return v2;
	}
	
	public void setVertice2(VerticeD<V,E> v2) {
		this.v2 = v2;
	}
	
	public Position<ArcoD<V,E>> getPosicionEnEmergentes() {
		return posicionEnEmergentes;
	}
	
	public void setPosicionEnEmergentes(Position<ArcoD<V,E>> p) {
		posicionEnEmergentes = p;
	}
	
	public Position<ArcoD<V,E>> getPosicionEnIncidentes() {
		return posicionEnIncidentes;
	}
	
	public void setPosicionEnIncidentes(Position<ArcoD<V,E>> p) {
		posicionEnIncidentes = p;
	}
	
	public Position<ArcoD<V,E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}
	
	public void setPosicionEnArcos(Position<ArcoD<V,E>> p) {
		posicionEnArcos = p;
	}
}

