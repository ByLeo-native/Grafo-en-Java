package TDAGrafoDirigido;

import java.util.Iterator;

import Excepciones.EmptyListException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;
import TDAGrafo.Edge;
import TDAGrafo.Vertex;
import TDALista.ListaDoblementeEnlazada;
import TDALista.Position;
import TDALista.PositionList;

public class GrafoDirigido<V,E> implements GraphD<V,E> {
	
	protected PositionList<VerticeDirigido<V,E>> nodos;
	protected PositionList<ArcoDirigido<V,E>> arcos;
	
	/**
	 * Constructor que crea un objeto de tipo grafo dirigido
	 */
	public GrafoDirigido() { //Orden(1)
		nodos = new ListaDoblementeEnlazada<VerticeDirigido<V,E>>();
		arcos = new ListaDoblementeEnlazada<ArcoDirigido<V,E>>();
	}
	
	/**
	 * Devuelve una colección iterable de vértices.
	 * @return Una colección iterable de vértices.
	 */
	public Iterable<Vertex<V>> vertices() { //Orden(n)
		PositionList<Vertex<V>> list = new ListaDoblementeEnlazada<Vertex<V>>();
		for(VerticeDirigido<V,E> v : this.nodos) {
			list.addLast(v);
		}
		return list;
	}
	
	/**
	 * Devuelve una colección iterable de arcos.
	 * @return Una colección iterable de arcos.
	 */
	public Iterable<Edge<E>> edges() { //Orden(m)
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for(ArcoDirigido<V,E> a: this.arcos) {
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
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException { //Orden(i)
		VerticeDirigido<V,E> vertice = this.checkVertex(v);
		
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		
		for(ArcoDirigido<V,E> a : vertice.getIncidentes() ) { //	Para todos los arcos que inciden al vertice
			list.addLast(a); // Añado al arco a la lista de arcos incidentes al vertice pasado por parametro
		}
		
		return list;
	}
	
	/**
	 * Devuelve una colección iterable de arcos adyacentes a un vértice v.
	 * @param v Un vértice
	 * @return Una colección iterable de arcos adyacentes a un vértice v.
	 * @throws InvalidVertexException si el vértice es inválido.
	 */
	public Iterable<Edge<E>> succesorEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeDirigido<V,E> vertice = this.checkVertex(v);
		
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		
		for(ArcoDirigido<V,E> a : vertice.getEmergentes()) {
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
		VerticeDirigido<V,E> vertice = this.checkVertex(v);
		ArcoDirigido<V,E> arco = this.checkEdge(e);
		
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
		Vertex<V> [] extremosDelArco = (VerticeDirigido<V,E>[]) new VerticeDirigido [2];
		
		ArcoDirigido<V,E> arco = this.checkEdge(e);
		
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
		
		VerticeDirigido<V,E> vertice1 = this.checkVertex(v);
		VerticeDirigido<V,E> vertice2 = this.checkVertex(w);
		
		Iterator<ArcoDirigido<V,E>> it = this.arcos.iterator();
		
		while(it.hasNext() && !sonAdyacentes) {
			ArcoDirigido<V,E> arcoAExaminar = it.next();
			
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
		VerticeDirigido<V,E> vertice = this.checkVertex(v);
		V rotulo = null;
		boolean seEncontro = false;
		
		Iterator<VerticeDirigido<V,E>> it = this.nodos.iterator();
		VerticeDirigido<V,E> verticeAExaminar = null;
		
		while( it.hasNext() && !seEncontro ) {
			verticeAExaminar = it.next();
			
			if(verticeAExaminar.equals(vertice)) {
				seEncontro = true;
			}
			
		}
		
		if(!seEncontro) {
			throw new InvalidVertexException("Vértice invalido");
		} else {
			VerticeDirigido<V,E> verticeEncontrado = verticeAExaminar;
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
		VerticeDirigido<V,E> v = new VerticeDirigido<V,E>(x);
		this.nodos.addLast(v);
		try {
			v.setPosicionEnListaVertices(this.nodos.last());
		} catch (EmptyListException e) {System.out.println("Algo raro paso");}
		
		return v;
	}
	
	/**
	 * Inserta un nuevo arco con rótulo e, desde un vértice v a un vértice w.
	 * @param v Un vértice
	 * @param w Un vértice
	 * @param e rótulo del nuevo arco.
	 * @return Un nuevo arco insertado desde un vértice V a un vértice W.
	 * @throws InvalidVertexException si uno de los vértices es inválido.
	 */
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeDirigido<V,E> verticeV = this.checkVertex(v);
		VerticeDirigido<V,E> verticeW = this.checkVertex(w);
		
		ArcoDirigido<V,E> nuevoArco = new ArcoDirigido<V,E>( e, verticeV, verticeW);
		
		try {
			verticeV.getEmergentes().addLast(nuevoArco);
			nuevoArco.setPosicionEnEmergentes( verticeV.getEmergentes().last() );
		} catch (EmptyListException e1) {System.out.println("Algo raro paso");}
		
		try {
			verticeW.getIncidentes().addLast(nuevoArco);
			nuevoArco.setPosicionEnIncidentes( verticeW.getIncidentes().last());
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
		VerticeDirigido<V,E> vertice = this.checkVertex(v);
		Position<VerticeDirigido<V,E>> pos = vertice.getPosicionEnListaVertices();
		V rotulo = null;
		try {
			//Para cada arco que incide en el vertice, removerlo.
			for( ArcoDirigido<V,E> arco: vertice.getIncidentes()) {
				this.arcos.remove(arco.getPosicionEnArcos()); //Remuevo al arco de la lista de arcos
				//Obtengo la posicion de los vertices de los extremos del arco
				VerticeDirigido<V, E> verticePred = this.checkVertex(arco.getPred());
				VerticeDirigido<V, E> verticeSuces = this.checkVertex(arco.getSuces());
				//De las posiciones de los vertices, remover al arco de emergentes o incidetes segun corresponda
				verticePred.getEmergentes().remove(arco.getPosicionEnEmergentes());
				verticeSuces.getIncidentes().remove(arco.getPosicionEnIncidentes());
				//Dejo en referencia nula a todos los parametros del arco
				this.limpiarParametrosDeUnArco(arco);
			}
			//Para cada arco de emerge en el vertice, removerlo.
			for( ArcoDirigido<V,E> arco: vertice.getEmergentes()) {
				this.arcos.remove(arco.getPosicionEnArcos()); //Remuevo al arco de la lista de arcos
				//Obtengo la posicion de los vertices de los extremos del arco
				VerticeDirigido<V,E> verticePred = this.checkVertex(arco.getPred());
				VerticeDirigido<V,E> verticeSuces = this.checkVertex(arco.getSuces());
				//De las posiciones de los vertices, remover al arco de emergentes o incidetes segun corresponda
				verticePred.getEmergentes().remove(arco.getPosicionEnEmergentes());
				verticeSuces.getIncidentes().remove(arco.getPosicionEnIncidentes());
				//Dejo en referencia nula a todos los parametros del arco
				this.limpiarParametrosDeUnArco(arco);
			}
			//Elimino de nodos al vertice que se quiere eliminar y guardo el rotulo del vertice
			rotulo = this.nodos.remove(pos).element();
		} catch (InvalidPositionException e) {System.out.println("Algo raro");}
		//Retorno el rotulo del vertice eliminado
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
		ArcoDirigido<V,E> arco = this.checkEdge(e);
		//Obtengo la posicion de los vertices extremos del arco a remover
		VerticeDirigido<V,E> vertPred = (VerticeDirigido<V,E>) arco.getPred();
		VerticeDirigido<V,E> vertSuces = (VerticeDirigido<V,E>) arco.getSuces();
		//Obtengo la posicion del arco en la lista de arcos
		Position<ArcoDirigido<V,E>> pos = arco.getPosicionEnArcos();
		
		E rotulo = null;
		try {
			//Elimino al arco de la lista de emergentes de vertPred
			vertPred.getEmergentes().remove(arco.getPosicionEnEmergentes());
			//Elimino al arco de la lista de incidentes de vertSuces
			vertSuces.getIncidentes().remove(arco.getPosicionEnIncidentes());
			//Remuevo al arco de la lista de arcos y retorno el rotulo
			rotulo = this.arcos.remove(pos).element();
			//Dejo en referencia nula a todos los parametros del arco
			this.limpiarParametrosDeUnArco(arco);
		} catch (InvalidPositionException e1) {System.out.println("Algo raro paso");}
		//Retorno el rotulo del vertice eliminado
		return rotulo;
	}
	
	/**
	 * Verifica si un vertice es valida.
	 * @param v Vertice a verificar.
	 * @return Casteo a un vertice utilizable del vertice pasado por parametro.
	 * @throws InvalidVertexException si el vertice es invalido.
	 */
	@SuppressWarnings("unchecked")
	private VerticeDirigido<V,E> checkVertex(Vertex<V> v) throws InvalidVertexException {
		if( v == null) {
			throw new InvalidVertexException("Vertice invalido");
		} else {
			VerticeDirigido<V,E> nodo = null;
			try {
				nodo = (VerticeDirigido<V,E>) v;
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
	private ArcoDirigido<V,E> checkEdge(Edge<E> e) throws InvalidEdgeException {
		if( e == null) {
			throw new InvalidEdgeException("Arco invalido");
		} else {
			ArcoDirigido<V,E> nodo = null;
			try {
				nodo = (ArcoDirigido<V,E>) e;
			} catch (ClassCastException error) {
				throw new InvalidEdgeException("Arco invalido");
			}
			return nodo;
		}
	}
	
	/**
	 * Establece a null a todos los parametros de un arco.
	 * @param arco Arco al que se modificara.
	 */
	private void limpiarParametrosDeUnArco(ArcoDirigido<V,E> arco) {
		arco.setPosicionEnArcos(null);
		arco.setPosicionEnEmergentes(null);
		arco.setPosicionEnIncidentes(null);
		arco.setPred(null);
		arco.setSuces(null);
		arco.setRotulo(null);
	}
}
