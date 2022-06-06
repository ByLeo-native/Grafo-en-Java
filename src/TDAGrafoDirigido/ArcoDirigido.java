package TDAGrafoDirigido;

import TDAGrafo.Edge;
import TDAGrafo.Vertex;
import TDALista.Position;

public class ArcoDirigido<V,E> implements Edge<E> {
	private E rotulo;
	private VerticeDirigido<V,E> verticePred, verticeSuces;
	private Position<ArcoDirigido<V,E>> posicionEnEmergentes, posicionEnIncidentes;
	private Position<ArcoDirigido<V,E>> posicionEnArcos;
	
	public ArcoDirigido( E rotulo, VerticeDirigido<V,E> verticePred, VerticeDirigido<V,E> verticeSuces) {
		this.rotulo = rotulo;
		this.verticePred = verticePred;
		this.verticeSuces = verticeSuces;
		this.posicionEnEmergentes = null;
		this.posicionEnIncidentes = null;
		this.posicionEnArcos = null;
	}
	
	public E element() {
		return this.rotulo;
	}
	
	public void setRotulo(E rotulo) {
		this.rotulo = rotulo;
	}
	
	public Vertex<V> getPred() {
		return this.verticePred;
	}
	
	public void setPred(VerticeDirigido<V,E> vertice) {
		this.verticePred = vertice;
	}
	
	public Vertex<V> getSuces() {
		return this.verticeSuces;
	}
	
	public void setSuces(VerticeDirigido<V,E> vertice) {
		this.verticeSuces = vertice;
	}
	
	public Position<ArcoDirigido<V,E>> getPosicionEnEmergentes() {
		return this.posicionEnEmergentes;
	}
	
	public void setPosicionEnEmergentes(Position<ArcoDirigido<V,E>> pos) {
		this.posicionEnEmergentes = pos;
	}
	
	public Position<ArcoDirigido<V,E>> getPosicionEnIncidentes() {
		return  this.posicionEnIncidentes;
	}
	
	public void setPosicionEnIncidentes(Position<ArcoDirigido<V,E>> pos) {
		this.posicionEnIncidentes = pos;
	}
	
	public Position<ArcoDirigido<V,E>> getPosicionEnArcos() {
		return this.posicionEnArcos;
	}
	
	public void setPosicionEnArcos(Position<ArcoDirigido<V,E>> pos) {
		this.posicionEnArcos = pos;
	}
}
