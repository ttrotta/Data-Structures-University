package TDAGrafoD;

import Interfaces.Edge;
import Interfaces.Position;
import TDAMapeo.MapOpenAdressing;

public class ArcoM<V,E> extends MapOpenAdressing<Object, Object> implements Edge<E> {
	protected E rotulo;
	protected Position<ArcoM<V,E>> posicionEnArcos;
	protected VerticeM<V,E> v1;
	protected VerticeM<V,E> v2;
	
	public ArcoM(E rot, VerticeM<V,E> vert1, VerticeM<V,E> vert2) {
		rotulo = rot;
		v1 = vert1;
		v2 = vert2;
		posicionEnArcos = null;
	}
	
	public E element() {
		return rotulo;
	}
	
	public void setRotulo(E rot) {
		rotulo = rot;
	}
	
	public Position<ArcoM<V,E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}
	
	public void setPosicionEnArcos(Position<ArcoM<V,E>> p) {
		posicionEnArcos = p;
	}
	
	public VerticeM<V,E> getVertice1() {
		return v1;
	}
	
	public void setVertice1(VerticeM<V,E> v) {
		v1 = v;
	}
	
	public VerticeM<V,E> getVertice2() {
		return v2;
	}
	
	public void setVertice2(VerticeM<V,E> v) {
		v2 = v;
	}
	
}
