package TDAGrafoDirigido;

import TDAGrafo.Vertex;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class VerticeDirigido <V,E> implements Vertex<V> {

	private V rotulo;
	private Position<VerticeDirigido<V,E>> posicionEnListaVertices;
	private PositionList<ArcoDirigido<V,E>> emergentes, incidentes;

	public VerticeDirigido(V rotulo) {
		this.rotulo = rotulo;
		this.posicionEnListaVertices = null;
		this.emergentes = new ListaDoblementeEnlazada<ArcoDirigido<V,E>>();
		this.incidentes = new ListaDoblementeEnlazada<ArcoDirigido<V,E>>();
	}
	
	public V element() {
		return this.rotulo;
	}
	
	public void setRotulo(V rotulo) {
		this.rotulo = rotulo;
	}
	
	public Position<VerticeDirigido<V,E>> getPosicionEnListaVertices() {
		return this.posicionEnListaVertices;
	}
	
	public void setPosicionEnListaVertices(Position<VerticeDirigido<V,E>> pos) {
		this.posicionEnListaVertices = pos;
	}
	
	public PositionList<ArcoDirigido<V,E>> getEmergentes() {
		return this.emergentes;
	}
	
	public void setEmergentes(ArcoDirigido<V,E> arco) {
		this.emergentes.addLast(arco);
	}
	
	public PositionList<ArcoDirigido<V,E>> getIncidentes() {
		return this.incidentes;
	}
	
	public void setIncidentes(ArcoDirigido<V,E> arco) {
		this.incidentes.addLast(arco);
	}

}
