package TDAGrafoD;

import TDAMapeo.MapOpenAdressing;
import Interfaces.Position;
import Interfaces.Vertex;

public class VerticeM<V,E> extends MapOpenAdressing<Object, Object> implements Vertex<V> {
	protected V rotulo;
    protected Position<VerticeM<V,E>> posicionEnVertices;
    protected int indice;

    public VerticeM(V rotulo, int indice){
        this.rotulo = rotulo;
        this.indice = indice;
        posicionEnVertices = null;
    }

    public void setPositionListVertex(Position<VerticeM<V,E>> pos) {
    	posicionEnVertices = pos;
    }

    public Position<VerticeM<V,E>> getPositionListVertex() {
        return posicionEnVertices;
    }

    public V element() {
        return rotulo;
    }
    
    public void setRotulo(V rotulo) {
        this.rotulo = rotulo;
    }
    
    public int getIndice(){ 
    	return indice;
    }
    
    public void setIndice(int indice){
        this.indice = indice;
    }
    
    public Position<VerticeM<V,E>> getPosicionEnVertices() {
    	return posicionEnVertices;
    }
    
    public void setPosicionEnVertices(Position<VerticeM<V,E>> p) {
    	posicionEnVertices = p;
    }
}
