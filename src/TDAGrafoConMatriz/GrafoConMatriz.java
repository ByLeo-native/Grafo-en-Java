package TDAGrafoConMatriz;

import Excepciones.EmptyListException;
import Excepciones.InvalidEdgeException;
import Excepciones.InvalidPositionException;
import Excepciones.InvalidVertexException;
import TDAGrafo.Edge;
import TDAGrafo.Graph;
import TDAGrafo.Vertex;
import TDALista.ListaDoblementeEnlazada;
import TDALista.PositionList;

public class GrafoConMatriz<V,E> implements Graph<V,E> {
	
	protected PositionList<Vertex<V>> vertices;
	protected PositionList<Edge<E>> arcos;
	protected Edge<E> [][] matriz;
	protected int cantidadVertices;
	
	/**
	 * Constructor de un grafo no dirigido que utiliza una matriz de adyacencia.
	 * @param n cantidad maxima de vertices insertables.
	 */
	@SuppressWarnings("unchecked")
	public GrafoConMatriz(int n) {
		this.vertices = new ListaDoblementeEnlazada<Vertex<V>>();
		this.arcos = new ListaDoblementeEnlazada<Edge<E>>();
		this.matriz = (Edge<E>[][]) new Edge [n][n];
		this.cantidadVertices = 0;
	}
	
	public Iterable<Vertex<V>> vertices() {
		return this.vertices;
	}
	
	public Iterable<Edge<E>> edges() {
		return this.arcos;
	}
	
	public Iterable<Edge<E>> incidentEdges(Vertex<V> v) throws InvalidVertexException {
		VerticeConMatriz<V> vertice = this.checkVertice(v);
		int indice = vertice.getIndice();
		PositionList<Edge<E>> list = new ListaDoblementeEnlazada<Edge<E>>();
		for( int j = 0; j < cantidadVertices; j++) {
			if( matriz[indice][j] != null ) {
				list.addLast(matriz[indice][j]);
			}
		}
		return list;
	}
	
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws InvalidVertexException, InvalidEdgeException {
		VerticeConMatriz<V> vertice = this.checkVertice(v);
		ArcoConMatriz<V,E> arco = this.checkArco(e);
		
		VerticeConMatriz<V> verticeOpuesto = null;
		
		if(arco.getPres().equals(vertice)) {
			verticeOpuesto = arco.getSuces();
		} else if(arco.getSuces().equals(vertice)) {
			verticeOpuesto = arco.getPres();
		} else {
			throw new InvalidEdgeException("Ningulo de los extremos corresponde al vertice dado.");
		}
		
		return verticeOpuesto;
	}
	
	public Vertex<V> [] endvertices(Edge<E> e) throws InvalidEdgeException {
		@SuppressWarnings("unchecked")
		Vertex<V> [] a = (VerticeConMatriz<V> []) new VerticeConMatriz[2];
		ArcoConMatriz<V,E> arco = this.checkArco(e);
		a[0] = arco.getPres();
		a[1] = arco.getSuces();
		return a;
	}
	
	public boolean areAdjacent(Vertex<V> v, Vertex<V> w ) throws InvalidVertexException {
		VerticeConMatriz<V> verticeV = this.checkVertice(v);
		VerticeConMatriz<V> verticeW = this.checkVertice(w);
		
		int i = verticeV.getIndice();
		int j = verticeW.getIndice();
		
		return matriz[i][j] != null;
	}
	
	public V replace(Vertex<V> v, V x) throws InvalidVertexException {
		VerticeConMatriz<V> vertice = this.checkVertice(v);
		V rotulo = vertice.element();
		vertice.setRotulo(x);
		return rotulo;
	}
	
	public Vertex<V> insertVertex(V x) {
		VerticeConMatriz<V> vertice = new VerticeConMatriz<V>( x, this.cantidadVertices++);
		
		try {
			this.vertices.addLast(vertice);
			vertice.setPosicionEnVertices(this.vertices.last());
		} catch (EmptyListException e) {}
		
		return vertice;
	}
	
	public Edge<E> insertEdge(Vertex<V> v, Vertex<V> w, E e) throws InvalidVertexException {
		VerticeConMatriz<V> verticeV = this.checkVertice(v);
		VerticeConMatriz<V> verticeW = this.checkVertice(w);
		int fila = verticeV.getIndice();
		int columna = verticeW.getIndice();
		ArcoConMatriz<V,E> arco = new ArcoConMatriz<V,E> ( e, verticeV, verticeW);
		
		this.matriz[fila][columna] = matriz[columna][fila] = arco;
		
		try {
			this.arcos.addLast(arco);
			arco.setPosicionEnArcos(this.arcos.last());
		} catch (EmptyListException e1) {}
		
		return arco;
	}
	
	public V removeVertex(Vertex<V> v) throws InvalidVertexException {
		VerticeConMatriz<V> vertice = this.checkVertice(v);
		int indice = vertice.getIndice();
		
		for(int j = 0; j < cantidadVertices; j++) {
			try {
				ArcoConMatriz<V,E> arco = this.checkArco(this.matriz[indice][j]);
				int fila = arco.getPres().getIndice();
				int columna = arco.getSuces().getIndice();
				this.matriz[fila][columna] = this.matriz[columna][fila] = null;
				this.arcos.remove(arco.getPosicionEnArcos());
				
			} catch (InvalidEdgeException | InvalidPositionException e) {}
			
			matriz[indice][j] = null;
		}
		
		try {
			this.vertices.remove( vertice.getPosicionEnVertices() );
		} catch (InvalidPositionException e) {}
		
		this.cantidadVertices--;
		
		return vertice.element();
	}
	
	public E removeEdge(Edge<E> e) throws InvalidEdgeException {
		ArcoConMatriz<V,E> arco = this.checkArco(e);
		
		try {
			int fila = arco.getPres().getIndice();
			int columna = arco.getSuces().getIndice();
			this.matriz[fila][columna] = this.matriz[columna][fila] = null;
			
			this.arcos.remove(arco.getPosicionEnArcos());
		} catch (InvalidPositionException error) {}
		
		return arco.element();
	}
	
	private VerticeConMatriz<V> checkVertice(Vertex<V> v) throws InvalidVertexException {
		if( v==null) {
			throw new InvalidVertexException("Vertice invalido");
		} else {
			VerticeConMatriz<V> vertice;
			try {
				vertice = (VerticeConMatriz<V>) v;
			} catch (ClassCastException e) {
				throw new InvalidVertexException("Vertice invalido");
			}
			return vertice;
		}
	}
	
	@SuppressWarnings("unchecked")
	private ArcoConMatriz<V,E> checkArco(Edge<E> e) throws InvalidEdgeException {
		if( e==null) {
			throw new InvalidEdgeException("Arco invalido");
		} else {
			ArcoConMatriz<V,E> arco;
			try {
				arco = (ArcoConMatriz<V,E>) e;
			} catch (ClassCastException error) {
				throw new InvalidEdgeException("Arco invalido");
			}
			return arco;
		}
	}
}
