package TDAGrafoConMatriz;

import TDAGrafo.Edge;
import TDALista.Position;

public class ArcoConMatriz<V,E> implements Edge<E> {

		private Position<Edge<E>> posicionEnArcos;
		private VerticeConMatriz<V> vertPres, vertSuces;
		private E rotulo;
		
		/**
		 * Constructor de un arco de rotulo de tipo E preparado para utilizarse en un grafo con matriz
		 * @param rotulo Rotulo del arco.	
		 * @param vertPres Posicion del vertice que emerge el arco.
		 * @param vertSuces Posicion del vertice que incide el arco.
		 */
		public ArcoConMatriz(E rotulo, VerticeConMatriz<V> vertPres, VerticeConMatriz<V> vertSuces) {
			this.vertPres = vertPres;
			this.vertSuces = vertSuces;
			this.rotulo = rotulo;
		}
		
		/**
		 * Devuelve el rotulo del arco.
		 * @return rotulo del arco.
		 */
		public E element() {
			return this.rotulo;
		}
		
		/**
		 * Reemplaza el rotulo del arco.
		 * @param rotulo Rotulo
		 */
		public void setRotulo(E rotulo) {
			this.rotulo = rotulo;
		}
		
		/**
		 * Devuelve el vertice del cual emerge el arco.
		 * @return Vertice donde emerge el arco.
		 */
		public VerticeConMatriz<V> getPres() {
			return this.vertPres;
		}
		
		/**
		 * Reemplaza el vertice del cual emerge el arco.
		 * @param vertPres Vertice
		 */
		public void setPres(VerticeConMatriz<V> vertPres) {
			this.vertPres = vertPres;
		}
		
		/**
		 * Devuelve el vertice del cual incide el arco.
		 * @return El vertice donde incide el arco.
		 */
		public VerticeConMatriz<V> getSuces() {
			return this.vertSuces;
		}
		
		/**
		 * Reemplaza el vertice del cual incide el arco.
		 * @param vertSuces El vertice donde incide el arco.
		 */
		public void setSuces(VerticeConMatriz<V> vertSuces) {
			this.vertSuces = vertSuces;
		}
		
		/**
		 * Devuelve la posicion en la que se ubica el arco en la lista de arcos de un grafo.
		 * @return Posicion del arco en un grafo.
		 */
		public Position<Edge<E>> getPosicionEnArcos() {
			return this.posicionEnArcos;
		}
		
		/**
		 * Reemplaza la posicion en el cual se encuentra en una lista de arcos de un grafo.
		 * @param pos Posicion del arco de una lista de un grafo.
		 */
		public void setPosicionEnArcos(Position<Edge<E>> pos) {
			this.posicionEnArcos = pos;
		}
}
