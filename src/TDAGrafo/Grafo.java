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
	
	/**
	 * Constructor que crea un objeto de tipo grafo
	 */
	public Grafo() {
		nodos = new ListaDoblementeEnlazada<Vertice<V,E>>();
		arcos = new ListaDoblementeEnlazada<Arco<V,E>>();
	}
	
	/**
	 * Devuelve una colección iterable de vértices.
	 * @return Una colección iterable de vértices.
	 */
	public Iterable<Vertex<V>> vertices() {
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(Vertice<V,E> v : this.nodos) {
			list.addLast(v);
		}
		return list;
	}
	
	/**
	 * Devuelve una colección iterable de arcos.
	 * @return Una colección iterable de arcos.
	 */
	public Iterable<Edge<E>> edges() {
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(Arco<V,E> a: this.arcos) {
			list.addLast(a);
		}
		return list;
	}
	
	/**
	 * Devuelve una colección iterable de arcos incidentes a un vértice v.
	 * @param v Un vértice.
	 * @return Una colección iterable de arcos incidentes a un vértice v.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		Vertice<V,E> vertice = this.checkVertex(v);
		
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
	
		for(Arco<V,E> a : vertice.getAdyacentes() ) {
			list.addLast(a);
		}
		
		return list;
	}
	
	/**
	 * Devuelve el vértice opuesto a un Arco E y un vértice V.
	 * @param v Un vértice
	 * @param e Un arco
	 * @return El vértice opuesto a un Arco E y un vértice V.
	 * @throws InvalidVertexException si el vértice es inválido.
	 * @throws InvalidEdgeException si el arco es inválido.
	 */
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
	
	/**
	 * Devuelve un Arreglo de 2 elementos con lo vértices extremos de un Arco e.
	 * @param  e Un arco
	 * @return Un Arreglo de 2 elementos con los extremos de un Arco e.
	 * @throws InvalidEdgeException si el arco es inválido.
	 */
	public Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException {
		@SuppressWarnings("unchecked")
		Vertex<V> [] extremosDelArco = (Vertice<V,E>[]) new Vertice [2];
		
		Arco<V,E> arco = this.checkEdge(e);
		
		extremosDelArco[0] = arco.getPred();
		extremosDelArco[1] = arco.getSuces();
		
		return extremosDelArco;
	}
	
	/**
	 * Devuelve verdadero si el vértice w es adyacente al vértice v.
	 * @param v Un vértice
	 * @param w Un vértice
	 * @return Verdadero si el vértice w es adyacente al vértice v, falso en caso contrario.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
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
	
	/**
	 * Reemplaza el rótulo de v por un rótulo x.
	 * @param v Un vértice
	 * @param x Rótulo
	 * @return El rótulo anterior del vértice v al reemplazarlo por un rótulo x.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
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
	
	/**
	 * Inserta un nuevo vértice con rótulo x.
	 * @param x rótulo del nuevo vértice
	 * @return Un nuevo vértice insertado.
	 */
	public Vertex<V> insertVertex(V x) {
		Vertice<V,E> v = new Vertice<V,E>(x);
		this.nodos.addLast(v);
		try {
			v.setPosicionEnNodos(this.nodos.last());
		} catch (EmptyListException e) {System.out.println("Algo raro paso");}
		
		return v;
	}
	
	/**
	 * Inserta un nuevo arco con rótulo e, con vértices extremos v y w.
	 * @param v Un vértice
	 * @param w Un vértice
	 * @param e rótulo del nuevo arco.
	 * @return Un nuevo arco.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
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
	
	/**
	 * Remueve un vértice V y retorna su rótulo.
	 * @param v Un vértice
	 * @return rótulo de V.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
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
	
	/**
	 * Remueve un arco e y retorna su rótulo.
	 * @param e Un arco
 	 * @return rótulo de E.
	 * @throws InvalidEdgeException si el arco es inválido.
	 */
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
	
	/**
	 * Verifica si un vertice es valida.
	 * @param v Vertice a verificar.
	 * @return Casteo a un vertice utilizable del vertice pasado por parametro.
	 * @throws InvalidVertexException si el vertice es invalido.
	 */
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
	
	/**
	 * Verifica si un arco es valida.
	 * @param v Arco a verificar.
	 * @return Casteo a un arco utilizable del arco pasado por parametro.
	 * @throws InvalidVertexException si el arco es invalido.
	 */
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
