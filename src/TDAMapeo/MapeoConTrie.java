package TDAMapeo;

import Exceptions.InvalidKeyException;
import Interfaces.*;

public class MapeoConTrie<E> implements Map<String,E> {

	protected NodoTrie<E> raiz;
	protected int size;
	
	public MapeoConTrie() {
		raiz = new NodoTrie<E>(null);
		size = 0;
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E get(String key) throws InvalidKeyException {
		return getAux(key,0,key.length(),raiz);
	}

	public E getAux(String key, int i, int n, NodoTrie<E> p) {
		E ret;
		if (i == n)
			ret = raiz.getImagen();
		else {
			int indice = (int) key.charAt(i) - (int) 'a';
			if (p.getHijo(indice) == null)
				ret = null;
			else
				ret = getAux(key,i+1,n,p.getHijo(indice));
		}
		return ret;
	}
	
	public E put(String key, E value) throws InvalidKeyException {
		return putAux(key,value,0,key.length(),raiz);
	}

	public E putAux(String key, E value, int i, int n, NodoTrie<E> p) {
		if (i < n) {
			int indice = ((int) key.charAt(i)) - ((int) 'a');
			if (p.getHijo(indice) == null)
				p.setHijo(indice, new NodoTrie<E>(p));
			return putAux(key,value,i+1,n,p.getHijo(indice));
		}
		else {
			E imagenVieja = p.getImagen();
			p.setImagen(value);
			return imagenVieja;
		}
	}
	
	public E remove(String key) throws InvalidKeyException {
		return removeAux(key,0,key.length(),raiz,0);
	}
	
	public E removeAux(String key,int i, int n, NodoTrie<E> p, int indiceP) throws InvalidKeyException{
		E ret;
		if (i == n) {
			if (p.getImagen() == null)
				throw new InvalidKeyException("La clave no esta en el mapeo");
			ret = p.getImagen();
			p.setImagen(null);
		}
		else {
			int indice = (int) key.charAt(i) - (int) 'a';
			if (p.getHijo(indice) == null)
				throw new InvalidKeyException("La clave no existe");
			ret = removeAux(key,i+1,n,p.getHijo(indice),indice);
		}
		if (todoNulo(p)) {
			if (p != raiz) {
				p.getPadre().setHijo(indiceP,null);
				p.setPadre(null);
			}
		}
		return ret;
	}
	
	private boolean todoNulo(NodoTrie<E> p) {
		boolean nulo = false;
		if (p.getImagen() == null) {
			nulo = true;
			for (int i = 0; i < 26 && nulo;i++) {
				nulo = p.getHijo(i).getImagen() == null;
			}
		}
		return nulo;
	}

	@Override
	public Iterable<String> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<E> values() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Entry<String, E>> entries() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("hiding")
	private class NodoTrie<E> {
		protected E imagen;
		protected NodoTrie<E> [] hijos;
		protected NodoTrie<E> padre;
		
		@SuppressWarnings("unchecked")
		public NodoTrie(NodoTrie<E> r) {
			hijos = new NodoTrie[26];
			imagen = null;
			padre = r;
		}
		
		public void setImagen(E img) {
			imagen = img;
		}
		
		public E getImagen() {
			return imagen;
		}
		
		public void setHijo(int i, NodoTrie<E> hijo) {
			hijos[i] = hijo;
		}
		
		public NodoTrie<E> getHijo(int i){
			return hijos[i];
		}
		
		public void setPadre(NodoTrie<E> p) {
			padre = p;
		}
		
		public NodoTrie<E> getPadre(){
			return padre;
		}
	}
}
