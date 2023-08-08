package TDAGrafoD;

import Exceptions.EmptyListException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.Edge;
import Interfaces.GraphD;
import Interfaces.PositionList;
import Interfaces.Vertex;
import TDALista.ListaDoblementeEnlazada;

public class DigrafoConMatriz<V,E> implements GraphD<V,E> {
	protected PositionList<VerticeM<V,E>> nodos;
	protected PositionList<ArcoM<V,E>> arcos;
	protected Edge<E> [][] matriz;
	protected int cantidadVertices;
	
	@SuppressWarnings("unchecked")
	public DigrafoConMatriz(int n) {
		nodos = new ListaDoblementeEnlazada<VerticeM<V,E>>();
		arcos = new ListaDoblementeEnlazada<ArcoM<V,E>>();
		matriz = (Edge<E> [][]) new ArcoM[n][n];
		cantidadVertices = 0;
	}
	
	public DigrafoConMatriz() {
		this(100);
	}
	
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(VerticeM<V,E> v : nodos)
			list.addLast(v);
		return list;
	}

	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(ArcoM<V,E> a : arcos)
			list.addLast(a);
		return list;

	}

	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V,E> vert = checkVertex(v);
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		int i = vert.getIndice();
		for(int j = 0; j < cantidadVertices; j++) {
			if(matriz[i][j] != null)
				list.addLast(matriz[i][j]);
		}
		return list;
	}

	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V,E> vert = checkVertex(v);
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		int j = vert.getIndice();
		for(int i = 0; i < cantidadVertices; i++) {
			if(matriz[i][j] != null)
				list.addLast(matriz[i][j]);
		}
		return list;
	}

	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeM<V,E> vert = checkVertex(v);
		ArcoM<V,E> arco = checkEdge(e);
		VerticeM<V,E> toReturn = null;
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
		ArcoM<V,E> arco = checkEdge(e);
		Vertex<V> [] array = (Vertex<V>[]) new Vertex[2];
		array[0] = arco.getVertice1();
		array[1] = arco.getVertice2();
		return array;
	}

	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		VerticeM<V,E> vert1 = checkVertex(v);
		VerticeM<V,E> vert2 = checkVertex(w);
		int i = vert1.getIndice();
		int j = vert2.getIndice();
		return matriz[i][j] != null;
	}

	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeM<V,E> vert = checkVertex(v);
	    V toReturn = vert.element();
	    vert.setRotulo(x);
	    return toReturn;
	}

	public Vertex<V> insertVertex(V x) {
		if(cantidadVertices == matriz.length)
			refactorMatriz();
		VerticeM<V,E> toInsert = new VerticeM<V,E>(x, cantidadVertices++);
		try {
			nodos.addLast(toInsert);
			toInsert.setPosicionEnVertices(nodos.last());
		} catch(EmptyListException e) {
			e.printStackTrace();
		}
		return toInsert;
	}


	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		if(cantidadVertices == matriz.length)
			refactorMatriz();
		VerticeM<V,E> vert1 = checkVertex(v);
		VerticeM<V,E> vert2 = checkVertex(w);
		ArcoM<V,E> toInsert = new ArcoM<V,E>(e,vert1,vert2);
		int i = vert1.getIndice();
		int j = vert2.getIndice();
		try {
			arcos.addLast(toInsert);
			toInsert.setPosicionEnArcos(arcos.last());
			matriz[i][j] = toInsert;
		} catch(EmptyListException e1) {
			e1.printStackTrace();
		}
		return toInsert;
	}

	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V,E> toRemove = checkVertex(v);
		V toReturn = toRemove.element();
		int i = toRemove.getIndice();
		try {
			nodos.remove(toRemove.getPosicionEnVertices());
			for(int j = 0; j < cantidadVertices; j++) {
                //Elimino los arcos emergentes a v
                if(matriz[i][j] != null)
                    removeEdge(matriz[i][j]);
                //Elimino los arcos incidentes a v
                if(matriz[j][i] != null)
                    removeEdge(matriz[j][i]);
            }
			cantidadVertices--;
		} catch(InvalidPositionException | InvalidEdgeException e) {
            e.printStackTrace();
        }
		return toReturn;
	}

	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoM<V,E> toRemove = checkEdge(e);
		E toReturn = toRemove.element();
		int i = toRemove.getVertice1().getIndice();
		int j = toRemove.getVertice2().getIndice();
		try {
			arcos.remove(toRemove.getPosicionEnArcos());
			matriz[i][j] = null;
		} catch(InvalidPositionException e1) {
			e1.printStackTrace();
		}	
		return toReturn;
	}
	
	@SuppressWarnings("unchecked")
	private VerticeM<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeM<V,E> toRet = null;
		if(v == null)
			throw new InvalidVertexException("El vértice no es válido.");
		try {
			toRet = (VerticeM<V,E>) v;
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
	
    @SuppressWarnings("unchecked")
    private void refactorMatriz() {
        int n = matriz.length * 2;
        Edge<E>[][] aux = (Edge<E>[][]) new ArcoM[n][n];
        for(int i = 0; i < matriz.length; i++)
            System.arraycopy(matriz[i], 0, aux[i], 0, matriz[0].length);
        matriz = aux;
    }
}
