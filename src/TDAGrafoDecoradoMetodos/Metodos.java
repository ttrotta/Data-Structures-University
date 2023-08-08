package TDAGrafoDecoradoMetodos;

import Exceptions.EmptyListException;
import Exceptions.EmptyQueueException;
import Exceptions.InvalidEdgeException;
import Exceptions.InvalidKeyException;
import Exceptions.InvalidPositionException;
import Exceptions.InvalidVertexException;
import Interfaces.Queue;
import TDACola.ColaEnlazada;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class Metodos {
	private final static Object ESTADO = new Object();
	private final static Object VISITADO = new Object();
	private final static Object NOVISITADO = new Object();	
	private final static Object ARCO_DESCUBRIMIENTO = new Object();
	private final static Object ARCO_RETROCESO = new Object();
	
	// Ejercicio 3
	/* 
	 Implemente en Java un recorrido en profundidad (Depth-first search) sobre un grafo.
	 Analice dos variantes: (i) la operación de recorrido es parte de la clase grafo, y (ii) la
	 operación de recorrido no pertenece a la clase grafo. (La que haré acá) 
	*/
	public static <V,E> void recorridoEnProfundidad(Graph<V,E> G) {
		try {
			for(Vertex<V> v : G.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
			for(Vertex<V> v : G.vertices()) {
				if(v.get(ESTADO) == NOVISITADO) {
					DFS(G,v);
				}
			}
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	private static <V,E> void DFS(Graph<V,E> G, Vertex<V> v) {
		try {
			System.out.print(v.element() + " - ");
			v.put(ESTADO, VISITADO);
			Iterable<Edge<E>> incidentEdges = G.incidentEdges(v);
			for(Edge<E> e : incidentEdges) {
				Vertex<V> vert = G.opposite(v,e);
				if(vert.get(ESTADO) == NOVISITADO) {
					DFS(G,vert);
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}
	
	// Ejercicio 4
	/*
	 Implemente en Java un recorrido en anchura (Breadth-first search) sobre un grafo. Analice
	 dos variantes: (i) la operación de recorrido es parte de la clase grafo, y (ii) la operación de
	 recorrido no pertenece a la clase grafo.
	*/
	public static <V,E> void recorridoEnAnchura(Graph<V,E> G) {
		try {
			for(Vertex<V> v : G.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
			for(Vertex<V> v : G.vertices()) {
				if(v.get(ESTADO) == NOVISITADO) 
					BFS(G,v);
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	private static <V,E> void BFS(Graph<V,E> G, Vertex<V> v) {
		try {
			Queue<Vertex<V>> cola = new ColaEnlazada<Vertex<V>>();
			cola.enqueue(v);
			v.put(ESTADO, VISITADO);
			while(!cola.isEmpty()) {
				Vertex<V> u = cola.dequeue();
				System.out.print(u.element() + " - ");
				for(Edge<E> x : G.incidentEdges(u)) {
					Vertex<V> vert = G.opposite(u,x);
					if(vert.get(ESTADO) == NOVISITADO) {
						vert.put(ESTADO, VISITADO);
						cola.enqueue(vert);
					}
				}
			}
		} catch (InvalidKeyException | EmptyQueueException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		
	}
	
	// Ejercicio 5 a)
	/*
	 Implemente en Java un algoritmo que dado un grafo con arcos pesados y dos vértices A y
	 B, encuentre el camino más económico de A a B. Determine el orden del tiempo de
	 ejecución de su solución considerando la complejidad temporal de la implementación del
	 grafo y de cualquier otra clase que necesitara.
	*/
	public static <V,E> PositionList<Vertex<V>> caminoEconomico(Vertex<V> O, Vertex<V> D, Graph<V,E> G) {
		PositionList<Vertex<V>> caminoAux = new ListaDoblementeEnlazada<Vertex<V>>();
		int pesoAux = 0;
		Par<V> p = new Par<V>();
		p.setPeso(Integer.MAX_VALUE);
		try {	
			for(Vertex<V> v : G.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
		} catch(InvalidKeyException e) {e.printStackTrace();}

		caminoEconomicoAux(O,D,G,p,caminoAux,pesoAux);
		return p.getCaminoMinimo();
	}
	
	private static <V,E> void caminoEconomicoAux(Vertex<V> O, Vertex<V> D, Graph<V,E> G, Par<V> caminoMin, PositionList<Vertex<V>> caminoAux, int pesoAux) {
		try {
			caminoAux.addLast(O);
			O.put(ESTADO, VISITADO);
			Vertex<V> OP;
			for(Edge<E> e : G.incidentEdges(O)) {
				OP = G.opposite(O,e);
				if(OP == D) {
					caminoAux.addLast(OP);
					pesoAux = pesoAux + (int) e.element(); 
					if(pesoAux <= caminoMin.getPeso()) {
						caminoMin.setCaminoMinimo(caminoAux.clone()); 
						caminoMin.setPeso(pesoAux);
					}
					caminoAux.remove(caminoAux.last());
					pesoAux = pesoAux - (int) e.element();
				}
				else {
					if(OP.get(ESTADO) == NOVISITADO)
						caminoEconomicoAux(OP,D,G,caminoMin,caminoAux,pesoAux+(int)e.element());
				}
			}
			caminoAux.remove(caminoAux.last());
			O.put(ESTADO, NOVISITADO);
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
	}
	
	// Ejercicio 5b)
	/*
	 Implemente en Java un algoritmo que dado un grafo y dos vértices A y B, encuentre el
	 camino más corto de A a B. Determine el orden del tiempo de ejecución de su solución
	 considerando la complejidad temporal de la implementación del grafo y de cualquier otra
	 clase que necesitara.
	 */
	public static <V,E> boolean BFSSearch(Graph<V,E> G, Vertex<V> s, Vertex<V> t, Map<Vertex<V>,Vertex<V>> previo) {
		Queue<Vertex<V>> cola = new ColaEnlazada<Vertex<V>>(); 
		marcarNoVisitados(G);
		cola.enqueue(s);
		try {
			s.put(ESTADO,VISITADO);
			while (!cola.isEmpty()) {
				Vertex<V> x = cola.dequeue();
				if(x == t)
					return true;
				for(Edge<E> e : G.incidentEdges(x)) {
					Vertex<V> v = G.opposite(x,e);
					if(v.get(ESTADO) == NOVISITADO) {
						cola.enqueue(v);
						v.put(ESTADO,VISITADO);
						previo.put(v,x);
					}
				}
			}
		} catch (EmptyQueueException | InvalidVertexException | InvalidEdgeException | InvalidKeyException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static <V,E> PositionList<Vertex<V>> recuperar(Vertex<V> s, Vertex<V> t, Map<Vertex<V>,Vertex<V>> previo) {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		Vertex<V> x = t;
		while(x != null) {
			list.addFirst(x);
			x = previo.get(x);
		}
		return list;
	}
		
	// Ejercicio 5c)
	/*
	 Modifique la solución dada en el inciso (b) para hallar todos los caminos de A a B.
	 */
	
	
	public static <V,E> void DFSShellForDG(GraphD<V,E> G) {
		try {
			for(Vertex<V> v : G.vertices()) {
				v.put(ESTADO, NOVISITADO);
			}
			for(Vertex<V> v : G.vertices()) {
				if(v.get(ESTADO) == NOVISITADO) {
					DFSForDG(G,v);
				}
			}
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}
	}
	
	public static <V,E> void DFSForDG(GraphD<V,E> G, Vertex<V> v) {
		try {
			System.out.print(v.element() + " - ");
			v.put(ESTADO, VISITADO);
			Iterable<Edge<E>> emergentes = G.succesorEdges(v);
			for(Edge<E> e : emergentes) {
				Vertex<V> vert = G.opposite(v,e);
				if(vert.get(ESTADO) == NOVISITADO) {
					DFSForDG(G,vert);
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}
	
	// Ejercicio 6b)
	/*
	 Escriba un método que, dado un rótulo R y un digrafo G, encuentre el primer vértice cuyo
	 rótulo es R
	 */
	public static <V,E> Vertex<V> verticeConR(Character R, GraphD<V,E> G) {
		marcarNoVisitadosD(G);
		Vertex<V> toReturn = null;
		boolean found = false;
		Iterator<Vertex<V>> it = G.vertices().iterator();
		while(it.hasNext() && !found) {
			toReturn = it.next();
			if(toReturn.element() == R) 
				found = true;
		}
 		if(!found)
 			toReturn = null;
		return toReturn;	
	}
	
	// Ejercicio 6c)
	/*
	 Escriba un método que reciba un digrafo G y el rótulo R de un vértice, y que elimine de G
	 todos los vértices que encuentre con rótulo R.
	*/
	public static <V,E> void removeVerticesConR(GraphD<V,E> G, Character R) {
		try {
			for (Vertex<V> v : G.vertices()) {
				if (v.element() == R)
					G.removeVertex(v);
			}
		} catch (InvalidVertexException e) {
			e.printStackTrace();
		}
	}
	
	// Ejercicio 6d)
	/*
	 Escriba un método que reciba un digrafo G y un vértice A, y establezca si G es una lista
	 cabeza A. Lógica: Tengo que arrancar desde el vértice A, y si este emerge hacía UN sólo vértice
	 y este otro hacía UN sólo vértice y así, entonces true. Caso contrario falso.
	 */
	public static <V,E> boolean esListaCabeza(GraphD<V,E> G, Vertex<V> A) {
		boolean isHead = true;
		int i = 0;
		try {
			for(Edge<E> e : G.succesorEdges(A)) {
				i++;
				if(i > 1)
					return false;
				esListaCabeza(G,G.opposite(A,e));
			}
		} catch (InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
		return isHead;
	}
	
	// Ejercicio 6e)
	/*
	 Escriba un método que reciba un digrafo G y un vértice A, y establezca si G es un árbol con
	 raíz A. 
	 */
	public static <V,E> boolean esArbol(GraphD<V,E> G, Vertex<V> A) {
		boolean isTree = true;
		BosqueDFS(G,A,ESTADO);
		try {
			for(Edge<E> e : G.edges()) {
				if(e.get(ESTADO) == ARCO_RETROCESO) 
					return false;
			}
		} catch(InvalidKeyException e) {
			e.printStackTrace();
		}
		return isTree;
	}
	
	// Bosque del DFS
	public static <V,E> void BosqueDFS(GraphD<V,E> G, Vertex<V> v, Object k) { 
		try {
			v.put(k, VISITADO);
			for(Edge<E> e : G.succesorEdges(v)) {
				if(e.get(k) == NOVISITADO) {
					Vertex<V> w = G.opposite(v,e);
					System.out.print(w.element() + " ");
					if(w.get(k) == NOVISITADO) {
						e.put(k, ARCO_DESCUBRIMIENTO);
						BosqueDFS(G,w,k);
					}
					else
						e.put(k,ARCO_RETROCESO);
				}
			}
		} catch (InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}
	
	
	// Ejercicio 6f)
	/*
	 Escriba un método que, dado un digrafo, busque cuál es el vértice con mayor grado de 
	 incidencia (es decir, al cual apuntan la mayor cantidad de casos), y luego lo elimine.
	 */
	@SuppressWarnings("unused")
	public static <V,E> void removerGrafoMasIncidentes(GraphD<V,E> G) {
		int mayor = 0;
        int aux = 0;
        Vertex<V> mayorV = null;
        try {
            for(Vertex<V> v : G.vertices()) {
                aux = 0;
                Iterable<Edge<E>> adjacents = G.incidentEdges(v);
                for (Edge<E> ed : adjacents) {
                    aux++;
                }
                if (aux > mayor) {
                    mayor = aux;
                    mayorV = v;
                }
            }
            G.removeVertex(mayorV);
        } catch (InvalidVertexException e) {
            System.out.println(e.getMessage());
        }
	}
	
	// Ejercicio 7a)
	// Escriba en Java un método para determinar si un grafo es conexo. Utilizando DFS
	// decimos que un grafo es conexo si para todo par de vertices existe un camino entre ellos.
	public static <V,E> boolean esConexo(GraphD<V,E> G) {
        int [] arr = {1};
        int size = 0;
        for(@SuppressWarnings("unused") Vertex<V> v : G.vertices()) {
        	size++;
        }
		marcarNoVisitadosD(G);
		marcarNoVisitadosEdgesD(G);
		Iterator<Vertex<V>> it = G.vertices().iterator();
		esConexoDFS(G,it.next(),arr,size);
		return arr[0] == size;
	}
	
	private static <V,E> void esConexoDFS(GraphD<V,E> G, Vertex<V> v, int [] arr,int size) {
		try {
			v.put(ESTADO,VISITADO);
			for(Edge<E> e : G.succesorEdges(v)) {
				Vertex<V> w = G.opposite(v, e);
				if(w.get(ESTADO) == NOVISITADO) {
					arr[0]++;
					if(size >= arr[0])
						esConexoDFS(G,w,arr,size);
				}
			}
		} catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace();
		}
	}
	
	// Ejercicio 7b)
	// Escriba en Java un método que determine las componentes conexas de un grafo.
	public static <V,E> int componentesConexas(GraphD<V,E> G) {
		int cc = 0;
		try {
			for(Vertex<V> v : G.vertices())
				v.put(ESTADO,NOVISITADO);
			for(Vertex<V> v : G.vertices()) {
				if(v.get(ESTADO) == NOVISITADO) {
					cc++;
					DFSShellForDG(G);
				}
			}
		}catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		return cc;
	}
	
	// Ejercicio 7c)
	// Realice un método que elimine un vértice y determine si varió el número de componentes
	// conexas del grafo.
	public static <V,E> boolean MismasComponentesConexas(GraphD<V, E> G, Vertex<V> a){
		boolean mcc = true;
		int cc = componentesConexas(G);
		try {
			G.removeVertex(a);
		} catch (InvalidVertexException e) {
			e.printStackTrace();
		}
		if(cc != componentesConexas(G))
			mcc = false;
		return mcc;
	}
	
	// Método de hallar un ciclo. Agarra el primero que encuentra, no el más corto.
	public static <V,E> boolean hallarCamino(GraphD<V,E> G, Vertex<V> O, Vertex<V> D, PositionList<Vertex<V>> camino) {
		boolean found;
		marcarNoVisitadosD(G);
		try {
			O.put(ESTADO, VISITADO);
			camino.addLast(O);
			if(O == D)
				return true;
			else
				for(Edge<E> e : G.succesorEdges(O)) {
					Vertex<V> v = G.opposite(O, e);
					if(v.get(ESTADO) == NOVISITADO) {
						found = hallarCamino(G,v,D,camino);
						if(found)
							return true;
					}
				}
			camino.remove(camino.last());
		} catch(InvalidKeyException | InvalidVertexException | InvalidEdgeException | InvalidPositionException | EmptyListException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static <V,E> PositionList<Vertex<V>> hallarCiclo(GraphD<V,E> G, Vertex<V> v) {
		PositionList<Vertex<V>> camino = new ListaDoblementeEnlazada<Vertex<V>>();
		boolean found = false;
		try {
			Iterator<Edge<E>> it = G.succesorEdges(v).iterator();
			while(it.hasNext() && !found) {
				Edge<E> e = it.next();
				Vertex<V> w = G.opposite(v,e); 
				found = hallarCamino(G,w,v,camino);
			}
		} catch(InvalidVertexException | InvalidEdgeException e) {
			e.printStackTrace(); 
		}
		PositionList<Vertex<V>> ciclo = new ListaDoblementeEnlazada<Vertex<V>>();
		if(found) {
			ciclo.addLast(v);
			for(Vertex<V> vv : camino) {
				ciclo.addLast(vv);
			}
		}
		return ciclo;
	}
	
	// ------------------------ Métodos Ajenos -------------------------
	
	private static <V,E> void marcarNoVisitados(Graph<V,E> G) {
		for(Vertex<V> v : G.vertices()) {
			try {
				v.put(ESTADO, NOVISITADO);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static <V,E> void marcarNoVisitadosD(GraphD<V,E> G) {
		for(Vertex<V> v : G.vertices()) {
			try {
				v.put(ESTADO, NOVISITADO);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("unused")
	private static <V,E> void marcarNoVisitadosEdges(Graph<V,E> G) {
		for(Edge<E> ee : G.edges()) {
			try {
				ee.put(ESTADO, NOVISITADO);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static <V,E> void marcarNoVisitadosEdgesD(GraphD<V,E> G) {
		for(Edge<E> ee : G.edges()) {
			try {
				ee.put(ESTADO, NOVISITADO);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static <E> void mostrarLista(PositionList<E> list) {
		for(E e : list) {
			System.out.print(e + " --> ");
		}
		System.out.print("FIN ");
	
	}
	
	// ---------------- Tester para los métodos ----------------
	public static void main(String args[]) {
		GrafoND<Integer, Integer> grafo1 = new GrafoND<Integer, Integer>();
		try {
			/* Cargar el primer grafo */
			Vertex<Integer> va = grafo1.insertVertex(10);
			Vertex<Integer> vb = grafo1.insertVertex(20);
			Vertex<Integer> vc = grafo1.insertVertex(30);
			Vertex<Integer> vd = grafo1.insertVertex(40);
			Vertex<Integer> ve = grafo1.insertVertex(50);
			Vertex<Integer> vf = grafo1.insertVertex(60);
			grafo1.insertEdge(va, vb, 1);
			grafo1.insertEdge(vb, vc, 2);
			grafo1.insertEdge(vc, vd, 3);
			grafo1.insertEdge(vb, vd, 4);
			grafo1.insertEdge(vb, ve, 5);
			grafo1.insertEdge(ve, vf, 6);
			grafo1.insertEdge(va, ve, 7);
			grafo1.insertEdge(ve, vd, 8);
			grafo1.insertEdge(vf, vc, 9);
			grafo1.insertEdge(vf, vd, 10);
			System.out.println();
			
			/* Imprimir el grafo 1 en DFS*/
			System.out.println("Grafo (1) DFS: ");
			System.out.print("|- ");
			Metodos.recorridoEnProfundidad(grafo1);
			System.out.print("| ");
			System.out.println();
			System.out.println();
			
			/* Imprimir el grafo 1 en BFS*/
			System.out.println("Grafo (1) BFS: ");
			System.out.print("|- ");
			Metodos.recorridoEnAnchura(grafo1);
			System.out.print("|");
			System.out.println();
			System.out.println();
			
			/* Imprimir el camino más económico del grafo 1 */
			System.out.println("Camino más económico de Grafo (1), primero de 10 a 60, después de 10 a 40: ");
			System.out.print("| ");
			PositionList<Vertex<Integer>> caminoEcon1 = Metodos.caminoEconomico(va,vf,grafo1);
			mostrarLista(caminoEcon1);
			System.out.print("|");
			System.out.println();
			System.out.print("| ");
			PositionList<Vertex<Integer>> caminoEcon2 = Metodos.caminoEconomico(va,vd,grafo1);
			mostrarLista(caminoEcon2);
			System.out.print("|");
			System.out.println();
			
			/* Imprimir el camino más corto del grafo 1 */
			System.out.println("Camino más corto: ");
			Map<Vertex<Integer>, Vertex<Integer>> map = new HashMap<Vertex<Integer>,Vertex<Integer>>();
			PositionList<Vertex<Integer>> listCaminoCorto = new ListaDoblementeEnlazada<Vertex<Integer>>();
			boolean masCorto = Metodos.BFSSearch(grafo1,va,vc,map);
			if(masCorto) 
				listCaminoCorto = Metodos.recuperar(va,vc,map);
			System.out.print("| ");
			for(Vertex<Integer> v : listCaminoCorto) {
				System.out.print(v.element() + " - ");
			}
			System.out.print("|");
			System.out.println();

			
			// ---------------------------------------------------------- <> ----------------------------------------------------------
			
			/* Cargar el segundo grafo */
			GrafoND<Character, Integer> grafo2 = new GrafoND<Character, Integer>();
			Vertex<Character> va1 = grafo2.insertVertex('a');
			Vertex<Character> vb1 = grafo2.insertVertex('b');
			Vertex<Character> vc1 = grafo2.insertVertex('c');
			Vertex<Character> vd1 = grafo2.insertVertex('d');
			Vertex<Character> ve1 = grafo2.insertVertex('e');
			Vertex<Character> vf1 = grafo2.insertVertex('f');
			Vertex<Character> vg1 = grafo2.insertVertex('g');
			Vertex<Character> vh1 = grafo2.insertVertex('h');
			Vertex<Character> vi1 = grafo2.insertVertex('i');
			grafo2.insertEdge(va1, vb1, 1);
			grafo2.insertEdge(va1, vd1, 2);
			grafo2.insertEdge(va1, vc1, 5);
			grafo2.insertEdge(vb1, vh1, 1);
			grafo2.insertEdge(vh1, vc1, 2);
			grafo2.insertEdge(vc1, vi1, 3);
			grafo2.insertEdge(vb1, ve1, 3);
			grafo2.insertEdge(ve1, vf1, 4);
			grafo2.insertEdge(vf1, va1, 7);
			grafo2.insertEdge(vd1, vf1, 3);
			grafo2.insertEdge(vf1, vg1, 6);
			System.out.println();
			System.out.println();
			
			/* Imprimir el grafo 2 en DFS*/
			System.out.println("Grafo (2) DFS: ");
			System.out.print("|- ");
			Metodos.recorridoEnProfundidad(grafo2);
			System.out.print("|");
			System.out.println();
			
			/* Imprimir el grafo 2 en BFS*/
			System.out.println("Grafo (2) BFS: ");
			System.out.print("|- ");
			Metodos.recorridoEnAnchura(grafo2);
			System.out.print("|");
			System.out.println();
			
			/* Imprimir el camino más económico del grafo 2 */
			System.out.println("Camino más económico de Grafo (2): ");
			System.out.print("| ");
			PositionList<Vertex<Character>> caminoEco1 = Metodos.caminoEconomico(va1,vd1,grafo2);
			mostrarLista(caminoEco1);
			System.out.print("|");
			System.out.println();
			System.out.print("| ");
			PositionList<Vertex<Character>> caminoEco2 = Metodos.caminoEconomico(va1,vh1,grafo2);
			mostrarLista(caminoEco2);
			System.out.print("|");
			System.out.println();

			// ---------------------------------------------------------- <> ----------------------------------------------------------
			
			/* Cargar el tercer grafo, siendo este dirigido */
			GraphD<Character, Integer> grafo3 = new Digrafo<Character, Integer>();
			Vertex<Character> vaD = grafo3.insertVertex('a');
			Vertex<Character> vbD = grafo3.insertVertex('b');
			Vertex<Character> vcD = grafo3.insertVertex('c');
			Vertex<Character> vdD = grafo3.insertVertex('d');
			Vertex<Character> veD = grafo3.insertVertex('e');
			Vertex<Character> vfD = grafo3.insertVertex('f');
			Vertex<Character> vgD = grafo3.insertVertex('g');
			Vertex<Character> vhD = grafo3.insertVertex('h');
			Vertex<Character> viD = grafo3.insertVertex('i');
			grafo3.insertEdge(vaD, vbD, 1);
			grafo3.insertEdge(vaD, vdD, 2);
			grafo3.insertEdge(vaD, vcD, 5);
			grafo3.insertEdge(vbD, vhD, 1);
			grafo3.insertEdge(vhD, vcD, 2);
			grafo3.insertEdge(vcD, viD, 3);
			grafo3.insertEdge(vbD, veD, 3);
			grafo3.insertEdge(veD, vfD, 4);
			grafo3.insertEdge(vfD, vgD, 7);
			grafo3.insertEdge(vdD, vfD, 3);
			grafo3.insertEdge(vfD, vaD, 6);
			System.out.println();
			System.out.println();
		
			/* Imprimir el grafo 3 en DFS*/
			System.out.println("Grafo (3) DFS: ");
			System.out.print("|- ");
			Metodos.DFSShellForDG(grafo3);
			System.out.print("|");
			System.out.println();
			Vertex<Character> verticeTested = Metodos.verticeConR(vcD.element(), grafo3);
			System.out.println(verticeTested);
			System.out.println();
			/* Comentado porque borra todo sino.
			Metodos.removeVerticesConR(grafo3, 'a');
			System.out.println("Ahora borramos todos los vertices con rótulo a. No más a, h, ni g.");
			Metodos.DFSShellForDG(grafo3); 
			*/
			boolean isFirstHeadOfAList = Metodos.esListaCabeza(grafo3,veD);
			System.out.println("¿Es cabeza de una lista?  " + isFirstHeadOfAList);
			marcarNoVisitadosD(grafo3);
			marcarNoVisitadosEdgesD(grafo3);
			boolean isTree = Metodos.esArbol(grafo3,vhD);
			System.out.println("¿Es un árbol?  " + isTree);
			System.out.println();
			
			
			boolean conexo1 = Metodos.esConexo(grafo3);
			System.out.println("¿Es conexo?  " + conexo1);
			System.out.println();
			
			Metodos.removeVerticesConR(grafo3, 'c');
			boolean conexo2 = Metodos.esConexo(grafo3);
			System.out.println("¿Es conexo?  " + conexo2);
			System.out.println();
			
			System.out.println("Hallar camino para el grafo 3: ");
			System.out.print("| ");
			PositionList<Vertex<Character>> caminoGrafo = new ListaDoblementeEnlazada<Vertex<Character>>();
			Metodos.hallarCamino(grafo3,vaD,vgD,caminoGrafo);
			mostrarLista(caminoGrafo);
			System.out.print("|");
			System.out.println();
			System.out.println();
			
			System.out.println("Hallar ciclo para el grafo 3: ");
			System.out.print("| ");
			PositionList<Vertex<Character>> primerCiclo = Metodos.hallarCiclo(grafo3,vaD);
			mostrarLista(primerCiclo);
			System.out.print("|");
			System.out.println();
			System.out.println();
			
			System.out.println("Borrando el vértice con más incidentes, (d) del grafo 3: ");
			System.out.print("| ");
			Metodos.removerGrafoMasIncidentes(grafo3);
			Metodos.DFSShellForDG(grafo3);
			System.out.print("|");
			System.out.println();
		} catch (InvalidVertexException e1) {
			e1.printStackTrace();
		}
	}
}
