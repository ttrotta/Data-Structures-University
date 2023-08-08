package TDAGrafo;

import Interfaces.Edge;
import Interfaces.Position;

public class ArcoM<V,E> implements Edge<E> {
	protected E rotulo;
	protected VerticeM<V> v1, v2;
	protected Position<Edge<E>> posicionEnArcos;
	
	public ArcoM(E rot, VerticeM<V> v1, VerticeM<V> v2) {
		rotulo = rot;
		this.v1 = v1;
		this.v2 = v2;
		posicionEnArcos = null;
	}
	
	public E element() {
		return rotulo;
	}
	
	public void setRotulo(E rot) {
		rotulo = rot;
	}
	
	public VerticeM<V> getVertice1(){
		return v1;
	}
	
	public void setVertice1(VerticeM<V> v) {
		v1 = v;
	}
	
	public VerticeM<V> getVertice2(){
		return v2;
	}
	
	public void setVertice2(VerticeM<V> v) {
		v2 = v;
	}
	
	public Position<Edge<E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}
	
	public void setPosicionEnArcos(Position<Edge<E>> p) {
		posicionEnArcos = p;
	}
	
}
