package TDAGrafo;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.*;
import TDALista.*;

public class GrafoNDConMatriz<V,E> implements Graph<V,E> {
	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected Edge<E> [][] matriz;
	protected int cantidadVertices;
	
	@SuppressWarnings("unchecked")
	public GrafoNDConMatriz(int n) {
		vertices = new ListaDoblementeEnlazada<Vertex<V>>();
		arcos = new ListaDoblementeEnlazada<Edge<E>>();
		matriz = (Edge<E>[][]) new ArcoM[n][n];
		cantidadVertices = 0;
	}
	
	public GrafoNDConMatriz() {
		this(100);
	}
	
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertex<V> v : vertices) {
			list.addLast(v);
		}
		return list;
	}
	
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Edge<E> e : arcos) {
			list.addLast(e);
		}
		return list;
	}
	
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V> vv = checkVertex(v);
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		int i = vv.getIndice();
		for(int j=0; j < cantidadVertices; j++) {
			if(matriz[i][j] != null)
				list.addLast(matriz[i][j]);
		}
		return list;
	}
	
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeM<V> vv = checkVertex(v);
		ArcoM<V,E> ee = checkEdge(e);
		Vertex<V> toReturn = null;
		if(ee.getVertice1() == vv)
			toReturn = ee.getVertice2();
		else
			if(ee.getVertice2() == vv)
				toReturn = ee.getVertice1();
		if(toReturn == null)
			throw new InvalidEdgeException("El vértice y el arco no están relacionados.");
		return toReturn;
	}

	@SuppressWarnings("unchecked")
	public Vertex<V>[] endvertices(Edge<E> e) throws InvalidEdgeException {
		Vertex<V> [] array = (Vertex<V> []) new Vertex[2]; // Se podría considerar el check
		ArcoM<V,E> ee = checkEdge(e);
		array[0] = ee.getVertice1();
		array[1] = ee.getVertice2();
		return array;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeM<V> vv = checkVertex(v);
		VerticeM<V> ww = checkVertex(w);
		int i = vv.getIndice();
		int j = ww.getIndice();
		return matriz[i][j] != null;
	}
	
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeM<V> vert = checkVertex(v);
		V toReturn = vert.element();
		vert.setRotulo(x);
		return toReturn;
	}

	public Vertex<V> insertVertex(V x) {
		VerticeM<V> vv = new VerticeM<V>(x,cantidadVertices++);
		try {
			vertices.addLast(vv);
			vv.setPosicionEnVertices(vertices.last());
		} catch (EmptyListException e) {
			e.printStackTrace();
		}
		return vv;
	} 
	
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		if(cantidadVertices == matriz.length)
			refactorMatriz();
		VerticeM<V> vv = checkVertex(v);
		VerticeM<V> ww = checkVertex(w);
		int fila = vv.getIndice();
		int col = ww.getIndice();
		if(fila >= matriz.length || col >= matriz.length) 
            refactorMatriz();
		ArcoM<V,E> arco = new ArcoM<V,E>(e,vv,ww);
		matriz[fila][col] = matriz[col][fila] = arco;
		arcos.addLast(arco);
		try {arco.setPosicionEnArcos(arcos.last());} catch (EmptyListException e1) { e1.printStackTrace();}
		return arco;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V> vv = checkVertex(v);
		V toReturn = vv.element();
		try {
			vertices.remove(vv.getPosicionEnVertices());
			int i = vv.getIndice();
			for(int j=0; j < cantidadVertices; j++) {
				if(matriz[i][j] != null) {
					removeEdge(matriz[i][j]);
				}
			}
		} catch(InvalidPositionException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoM<V,E> ee = checkEdge(e);
		E toReturn = null;
		try {
			int fila = ee.getVertice1().getIndice();
			int col = ee.getVertice2().getIndice();
			matriz[fila][col] = matriz[col][fila] = null;
			arcos.remove(ee.getPosicionEnArcos());
			toReturn = ee.element();
		} catch(InvalidPositionException e1) {
			System.out.println("Error en removeEdge.");
		}
		return toReturn;
	} 
	
	@SuppressWarnings("unchecked")
	private void refactorMatriz() {
		int n = matriz.length * 2;
		Edge<E> [][] aux = (Edge<E> [][]) new ArcoM[n][n];
		for(int i=0; i < matriz.length; i++) {
			System.arraycopy(matriz[i], 0, aux[i], 0, matriz[0].length);
		}
		matriz = aux;
	}
	
	private VerticeM<V> checkVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V> toRet = null;
		if(v == null)
			throw new InvalidVertexException("El vértice no es válido.");
		try {
			toRet = (VerticeM<V>) v;
		} catch(ClassCastException e) {
			throw new InvalidVertexException("No es de tipo Vértice.");
		}
		return toRet;
	}
	
	@SuppressWarnings("unchecked")
	private ArcoM<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoM<V,E> toRet = null;
		if(e == null)
			throw new InvalidEdgeException("El arco no es válido.");
		try {
			toRet = (ArcoM<V,E>) e;
		} catch(ClassCastException e1) {
			throw new InvalidEdgeException("No es de tipo Arco.");
		}
		return toRet;
	}
}
