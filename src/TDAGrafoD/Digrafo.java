package TDAGrafoD;

import java.util.Iterator;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import TDALista.ListaDoblementeEnlazada;
import Interfaces.Edge;
import Interfaces.GraphD;
import Interfaces.Vertex;
import Interfaces.PositionList;

// Digrafo implementado con listas de adyacencia 
public class Digrafo<V,E> implements GraphD<V,E> {
	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertex<V> v : nodos)
			list.addLast(v);
		return list;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Edge<E> e : arcos)
			list.addLast(e);
		return list;
	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vert = checkVertex(v);
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Edge<E> e : vert.getIncidentes()) 
			list.addLast(e);
		return list;
	}

	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vert = checkVertex(v);
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Edge<E> e : vert.getEmergentes()) 
			list.addLast(e);
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
		Vertice<V,E> vert1 = checkVertex(v);
		Vertice<V,E> vert2 = checkVertex(v);
		boolean adjacent = false;
		Iterator<Arco<V,E>> it = arcos.iterator();
		Arco<V,E> aux = null;
		while(it.hasNext() && !adjacent) {
			aux = it.next();
			if(aux.getVertice1() == vert1 && aux.getVertice2() == vert2) 
				adjacent = true;
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
 		Vertice<V,E> toInsert = new Vertice<V,E>(x);
 		nodos.addLast(toInsert);
 		try {
			toInsert.setPosicionEnListaVertices(nodos.last());
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return toInsert;
	}

	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V,E> vert1 = checkVertex(v);
		Vertice<V,E> vert2 = checkVertex(w);
		Arco<V,E> arco = new Arco<V,E>(e,vert1,vert2);
		try {
			vert1.getEmergentes().addLast(arco);
			arco.setPosicionEnEmergentes(vert1.getIncidentes().last());
			vert2.getIncidentes().addLast(arco);
			arco.setPosicionEnIncidentes(vert2.getIncidentes().last());
			arcos.addLast(arco);
			arco.setPosicionEnArcos(arcos.last());
		} catch(EmptyListException o) {
			o.printStackTrace();
		}
		return arco;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vert = checkVertex(v);
		V toReturn = vert.element();
		try {
			for(Arco<V,E> a1 : vert.getEmergentes()) 
				removeEdge(a1);
			for(Arco<V,E> a2 : vert.getIncidentes()) 
				removeEdge(a2);
			nodos.remove(vert.getPosicionEnListaVertices());
		} catch(InvalidEdgeException | InvalidPositionException e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arco = checkEdge(e);
		E toReturn = arco.element();
		Vertice<V,E> vertEmergente = arco.getVertice1();
		Vertice<V,E> vertIncidente = arco.getVertice2();
		try {
			vertEmergente.getEmergentes().remove(arco.getPosicionEnEmergentes());
			vertIncidente.getIncidentes().remove(arco.getPosicionEnIncidentes());
			arcos.remove(arco.getPosicionEnArcos());
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
