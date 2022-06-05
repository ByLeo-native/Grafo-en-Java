package TDAGrafo;

import TDALista.Position;

public class Arco<V,E> implements Edge<E> {
	
	private E rotulo;
	private Vertice<V,E> pred, suces;
	private Position<Arco<V,E>> posicionEnArcos;
	private Position<Arco<V,E>> posicionEnlv1, posicionEnlv2;
	
	public Arco(E rotulo, Vertice<V,E> pred, Vertice<V,E> suces) {
		this.rotulo = rotulo;
		this.pred = pred;
		this.suces = suces;
	}
	
	public E element() {
		return rotulo;
	}
	
	public void setElement(E elem) {
		this.rotulo = elem;
	}
	
	public Vertex<V> getPred() {
		return this.pred;
	}
	
	public void setPred( Vertice<V,E> pred) {
		this.pred = pred;
	}
	
	public Vertex<V> getSuces() {
		return this.suces;
	}
	
	public void setSuces( Vertice<V,E> suces) {
		this.suces = suces;
	}
	
	public Position<Arco<V,E>> getPosicionEnlv1() {
		return this.posicionEnlv1;
	}
	
	public void setPosicionEnlv1(Position<Arco<V,E>> pos) {
		this.posicionEnlv1 = pos;
	}
	
	public Position<Arco<V,E>> getPosicionEnlv2() {
		return this.posicionEnlv2;
	}
	
	public void setPosicionEnlv2(Position<Arco<V,E>> pos) {
		this.posicionEnlv2 = pos;
	}
	
	public Position<Arco<V,E>> getPosicionEnArcos() {
		return this.posicionEnArcos;
	}
	
	public void setPosicionEnArcos( Position<Arco<V,E>> pos) {
		this.posicionEnArcos = pos;
	}
	
}
