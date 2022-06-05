package TDAGrafo;

import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class Vertice <V,E> implements Vertex<V> {
	private V rotulo;
	private PositionList<Arco<V,E>> adyacentes;
	private Position<Vertice<V,E>> posicionEnNodos;
	
	public Vertice(V rotulo) {
		this.rotulo = rotulo;
		this.adyacentes = new ListaDoblementeEnlazada<Arco<V,E>>();
	}	
	
	public V element() {
		return this.rotulo;
	}
	
	public void setRotulo(V nuevoRotulo) {
		this.rotulo = nuevoRotulo;
	}
	
	public PositionList<Arco<V,E>> getAdyacentes() {
		return this.adyacentes;
	}
	
	public void setAdyacentes( PositionList<Arco<V,E>> a ) {
		this.adyacentes = a;
	}
	
	public Position<Vertice<V,E>> getPosicionEnNodos() {
		return this.posicionEnNodos;
	}
	
	public void setPosicionEnNodos(Position<Vertice<V,E>> p) {
		this.posicionEnNodos = p;
	}
}
