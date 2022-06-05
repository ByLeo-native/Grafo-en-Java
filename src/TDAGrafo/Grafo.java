package TDAGrafo;

import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class Grafo<V,E> implements Graph<V,E> {
	
	protected PositionList<Vertice<V,E>> nodos;
	protected PositionList<Arco<V,E>> arcos;
	
	public Grafo() {
		nodos = new ListaDoblementeEnlazada<Vertice<V,E>>();
		arcos = new ListaDoblementeEnlazada<Arco<V,E>>();
	}
	
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertice<V,E> v : this.nodos) {
			list.addLast(v);
		}
		return list;
	}
	
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Arco<V,E> a: this.arcos) {
			list.addLast(a);
		}
		return list;
	}
	
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vertice = this.checkVertex(v);
		
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
	
		for(Arco<V,E> a : vertice.getAdyacentes() ) {
			list.addLast(a);
			
		}
		
		return list;
	}
	
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		Vertice<V,E> vertice = this.checkVertex(v);
		Arco<V,E> arco = this.checkEdge(e);
		
		Vertex<V> verticeARetornar = null;
		
		if( arco.getPred().equals(vertice)) {
			verticeARetornar = arco.getSuces();
		} else if ( arco.getSuces().equals(vertice)){
			verticeARetornar = arco.getPred();
		} else {
			throw new InvalidEdgeException("Ninguno de los extremos del arco coincide con el vértice.");
		}
		
		return verticeARetornar;
	}
	
	public Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException {
		@SuppressWarnings("unchecked")
		Vertex<V> [] extremosDelArco = (Vertice<V,E>[]) new Vertice [2];
		
		Arco<V,E> arco = this.checkEdge(e);
		
		extremosDelArco[0] = arco.getPred();
		extremosDelArco[1] = arco.getSuces();
		
		return extremosDelArco;
	}
	
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w) throws InvalidVertexException {
		boolean sonAdyacentes = false;
		
		Vertice<V,E> vertice1 = this.checkVertex(v);
		Vertice<V,E> vertice2 = this.checkVertex(w);
		
		Iterator<Arco<V,E>> it = this.arcos.iterator();
		
		while(it.hasNext() && !sonAdyacentes) {
			Arco<V,E> arcoAExaminar = it.next();
			
			if(arcoAExaminar.getPred().equals(vertice1) && arcoAExaminar.getSuces().equals(vertice2)) {
				sonAdyacentes = true;
			} else if(arcoAExaminar.getPred().equals(vertice2) && arcoAExaminar.getSuces().equals(vertice1)) {
				sonAdyacentes = true;
			}
			
		}
		
		return sonAdyacentes;
	}
	
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		Vertice<V,E> vertice = this.checkVertex(v);
		V rotulo = null;
		boolean seEncontro = false;
		
		Iterator<Vertice<V,E>> it = this.nodos.iterator();
		Vertice<V,E> verticeAExaminar = null;
		
		while( it.hasNext() && !seEncontro ) {
			verticeAExaminar = it.next();
			
			if(verticeAExaminar.equals(vertice)) {
				seEncontro = true;
			}
			
		}
		
		if(!seEncontro) {
			throw new InvalidVertexException("Vértice invalido");
		} else {
			Vertice<V,E> verticeEncontrado = verticeAExaminar;
			//Guardo el rotulo del vertice encontrado
			rotulo = verticeEncontrado.element();
			//Lo reemplazo por el pasado por parametro
			verticeEncontrado.setRotulo(x);
		}
		
		return rotulo;
	}
	
	public Vertex<V> insertVertex(V x) {
		Vertice<V,E> v = new Vertice<V,E>(x);
		this.nodos.addLast(v);
		try {
			v.setPosicionEnNodos(this.nodos.last());
		} catch (EmptyListException e) {System.out.println("Algo raro paso");}
		
		return v;
	}
	
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		Vertice<V,E> verticeV = this.checkVertex(v);
		Vertice<V,E> verticeW = this.checkVertex(w);
		
		Arco<V,E> nuevoArco = new Arco<V,E>( e, verticeV, verticeW);
		
		try {
			verticeV.getAdyacentes().addLast(nuevoArco);
			nuevoArco.setPosicionEnlv1( verticeV.getAdyacentes().last() );
		} catch (EmptyListException e1) {System.out.println("Algo raro paso");}
		
		try {
			verticeW.getAdyacentes().addLast(nuevoArco);
			nuevoArco.setPosicionEnlv2( verticeW.getAdyacentes().last());
		} catch (EmptyListException e2) {System.out.println("Algo raro paso");}
		
		
		try {
			this.arcos.addLast(nuevoArco);
			nuevoArco.setPosicionEnArcos(this.arcos.last());
		} catch (EmptyListException e3) {System.out.println("Algo raro paso");}
		
		return nuevoArco;
	}
	
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vertice = this.checkVertex(v);
		Position<Vertice<V,E>> pos = vertice.getPosicionEnNodos();
		V rotulo = null;
		try {
			for( Arco<V,E> arco: vertice.getAdyacentes()) {
				this.arcos.remove(arco.getPosicionEnArcos());
				
				Vertice<V, E> verticePred = this.checkVertex(arco.getPred());
				Vertice<V, E> verticeSuces = this.checkVertex(arco.getSuces());
				
				verticePred.getAdyacentes().remove(arco.getPosicionEnlv1());
				verticeSuces.getAdyacentes().remove(arco.getPosicionEnlv2());
				
				arco.setElement(null);
				arco.setPosicionEnArcos(null);
				arco.setPosicionEnlv1(null);
				arco.setPosicionEnlv2(null);
				
			}
			
			rotulo = this.nodos.remove(pos).element();
		} catch (InvalidPositionException e) {System.out.println("Algo raro");}
		
		return rotulo;
	}
	
	@SuppressWarnings("unchecked")
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		Arco<V,E> arco = this.checkEdge(e);
		
		Vertice<V,E> vertPred = (Vertice<V,E>) arco.getPred();
		Vertice<V,E> vertSuces = (Vertice<V,E>) arco.getSuces();
		
		Position<Arco<V,E>> pos = arco.getPosicionEnArcos();
		
		E rotulo = null;
		try {
			//Elimino al arco de la lista de adyacentes de vertPred
			vertPred.getAdyacentes().remove(arco.getPosicionEnlv1());
			//Elimino al arco de la lista de adyacentes de vertSuces
			vertSuces.getAdyacentes().remove(arco.getPosicionEnlv2());
			//Remuevo al arco de la lista de arcos y retorno el rotulo
			rotulo = this.arcos.remove(pos).element();
		} catch (InvalidPositionException e1) {System.out.println("Algo raro paso");}
		
		return rotulo;
	}
	
	@SuppressWarnings("unchecked")
	private Vertice<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if( v == null) {
			throw new InvalidVertexException("Vertice invalido");
		} else {
			Vertice<V,E> nodo = null;
			try {
				nodo = (Vertice<V,E>) v;
			} catch (ClassCastException e) {
				throw new InvalidVertexException("Vertice invalido");
			}
			return nodo;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Arco<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		if( e == null) {
			throw new InvalidEdgeException("Arco invalido");
		} else {
			Arco<V,E> nodo = null;
			try {
				nodo = (Arco<V,E>) e;
			} catch (ClassCastException error) {
				throw new InvalidEdgeException("Arco invalido");
			}
			return nodo;
		}
	}
}
