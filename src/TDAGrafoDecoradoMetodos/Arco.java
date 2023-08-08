package TDAGrafoDecoradoMetodos;

import Interfaces.Position;
import TDAMapeo.MapOpenAdressing;

public class Arco<V,E> extends MapOpenAdressing<Object,Object> implements Edge<E> {
	protected E rotulo;
	protected Vertice<V,E> v1, v2;
	protected Position<Arco<V,E>> posicionEnLvl1, posicionEnLvl2;
	protected Position<Arco<V,E>> posicionEnArcos;
	
	public Arco(E rotulo, Vertice<V,E> v1, Vertice<V,E> v2) {
		this.rotulo = rotulo;
		this.v1 = v1;
		this.v2 = v2;
		posicionEnLvl1 = posicionEnLvl2 = posicionEnArcos = null;
	}
	
	public E element() {
		return rotulo;
	}
	
	public void setRotulo(E rot) {
		rotulo = rot;
	}
	
	public Vertice<V,E> getVertice1() {
		return v1;
	}
	
	public void setVertice1(Vertice<V,E> v) {
		v1 = v;
	}
	
	public Vertice<V,E> getVertice2() {
		return v2;
	}
	
	public void setVertice2(Vertice<V,E> v) {
		v2 = v;
	}
	
	public Position<Arco<V,E>> getPosicionEnLvl1(){
		return posicionEnLvl1;
	}
	
	public Position<Arco<V,E>> getPosicionEnLvl2(){
		return posicionEnLvl2;
	}
	
	public void setPosicionEnLvl1(Position<Arco<V,E>> p) {
		posicionEnLvl1 = p;
    }

	public void setPosicionEnLvl2(Position<Arco<V,E>> p) {
		posicionEnLvl2 = p;
    }
	
	public Position<Arco<V,E>> getPosicionEnArcos() {
		return posicionEnArcos;
	}
	
	public void setPosicionEnArcos(Position<Arco<V,E>> p) {
		posicionEnArcos = p;
	}
	
}
