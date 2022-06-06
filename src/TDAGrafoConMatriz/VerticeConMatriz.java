package TDAGrafoConMatriz;

import TDAGrafo.Vertex;
import TDALista.Position;

public class VerticeConMatriz<V> implements Vertex<V> {
	
	private Position<Vertex<V>> posicionEnVertices;
	private V rotulo;
	private int indice;
	
	public VerticeConMatriz(V rotulo, int indice) {
		this.rotulo = rotulo;
		this.indice = indice;
		this.posicionEnVertices = null;
	}
	
	public V element() {
		return this.rotulo;
	}
	
	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}
	
	public int getIndice() {
		return this.indice;
	}
	
	public void setIndice(int indice) {
		this.indice = indice;
	}
	
	public Position<Vertex<V>> getPosicionEnVertices() {
		return this.posicionEnVertices;
	}
	
	public void setPosicionEnVertices(Position<Vertex<V>> pos) {
		this.posicionEnVertices = pos;
	}
	
}
