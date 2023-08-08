package TDAGrafoDecoradoMetodos;

import java.util.Iterator;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.Position;

public class GrafoND<V,E> implements Graph<V,E> {
	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public GrafoND() {
		nodos = new ListaDoblementeEnlazada<Vertice<V,E>>();
		arcos = new ListaDoblementeEnlazada<Arco<V,E>>();
	}
	
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertice<V,E> v : nodos) {
			list.addLast(v);
		}
		return list;
	}
	
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Arco<V,E> a : arcos) {
			list.addLast(a);;
		}
		return list;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		Vertice<V,E> vert = checkVertex(v);
		for(Arco<V,E> e : vert.getAdyacentes()) {
			list.addLast(e);
		}
		return list;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V,E> vert = checkVertex(v);
		Arco<V,E> arco = checkEdge(e);
		Vertex<V> toReturn = null;
		if(arco.getVertice1() == vert)
			toReturn = arco.getVertice2();
		else
			if(arco.getVertice2() == vert)
				toReturn = arco.getVertice1();
		if(toReturn == null)
			throw new InvalidEdgeException("El arco no es incidente al vértice.");
		return toReturn;
	} 

	@SuppressWarnings("unchecked")
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arco = checkEdge(e);
		Vertex<V> [] array = (Vertex<V>[]) new Vertex[2];
		array[0] = arco.getVertice1();
		array[1] = arco.getVertice2();
		return array;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> ww = checkVertex(w);
		boolean adjacent = false;
		Iterator<Arco<V,E>> it = vv.getAdyacentes().iterator();
		while(it.hasNext() && !adjacent) {
			Arco<V,E> arc = it.next();
			try {
				if(opposite(vv,arc) == ww) 
					adjacent = true;
			} catch (InvalidVertexException | InvalidEdgeException e) {
				e.printStackTrace();
			}
		}
		return adjacent;
	} 

	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V,E> vert = checkVertex(v);
		V toReturn = vert.element();
		vert.setRotulo(x);
		return toReturn;
	}

	public Vertex<V> insertVertex(V x) {
		Vertice<V,E> v = new Vertice<V,E>(x);
		nodos.addLast(v);
		try {
			v.setPosicionEnNodos(nodos.last());
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return v;
	}	

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V,E> vv = checkVertex(v);
		Vertice<V,E> ww = checkVertex(w);
		Arco<V,E> arco = new Arco<V,E>(e,vv,ww);
		try {
			vv.getAdyacentes().addLast(arco);
			arco.setPosicionEnLvl1(vv.getAdyacentes().last());
			ww.getAdyacentes().addLast(arco);
			arco.setPosicionEnLvl2(ww.getAdyacentes().last());
			arcos.addLast(arco);
			arco.setPosicionEnArcos(arcos.last());
		} catch(EmptyListException x) {
			x.printStackTrace();
		}
		return arco;
	} 

    public V removeVertex(Vertex<V> v) throws InvalidVertexException {
        Vertice<V,E> vert = checkVertex(v);
        V toReturn = null;
        try {
            for(Arco<V,E> a : vert.getAdyacentes()){
                removeEdge(a);
            }
            toReturn = nodos.remove(vert.getPosicionEnNodos()).element();
            vert.setRotulo(null);
        } catch (InvalidEdgeException | InvalidPositionException e) {
            throw new RuntimeException(e);
        }
        return toReturn;
    }
	
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> ee = checkEdge(e);
		E toReturn = null;
		Vertice<V,E> v1 = ee.getVertice1();
		Vertice<V,E> v2 = ee.getVertice2();
		try {
			v1.getAdyacentes().remove(ee.getPosicionEnLvl1());
			v2.getAdyacentes().remove(ee.getPosicionEnLvl2());
			Position<Arco<V,E>> pee = ee.getPosicionEnArcos();
			toReturn = arcos.remove(pee).element();
		} catch (InvalidPositionException e1) {
			e1.printStackTrace();
		}
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	private Vertice<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> toRet = null;
		if(v == null)
			throw new InvalidVertexException("El vértice no es válido.");
		try {
			toRet = (Vertice<V,E>) v;
		} catch(ClassCastException e) {
			throw new InvalidVertexException("No es de tipo Vértice.");
		}
		return toRet;
	}
	
	@SuppressWarnings("unchecked")
	private Arco<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> toRet = null;
		if(e == null)
			throw new InvalidEdgeException("El arco no es válido.");
		try {
			toRet = (Arco<V,E>) e;
		} catch(ClassCastException e1) {
			throw new InvalidEdgeException("No es de tipo Arco.");
		}
		return toRet;
	}
}
