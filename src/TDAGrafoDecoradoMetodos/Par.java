package TDAGrafoDecoradoMetodos;

public class Par<V> {
	private int peso;
	private PositionList<Vertex<V>> caminoMinimo;
	
	public void setPeso(int p) {
		peso = p;
	}
	
	public int getPeso() {
		return peso;
	}
	
	public void setCaminoMinimo(PositionList<Vertex<V>> c) {
		caminoMinimo = c;
	}
	
	public PositionList<Vertex<V>> getCaminoMinimo() {
		return caminoMinimo;
	}
}
