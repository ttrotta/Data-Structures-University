package TDAArbolBinario;

import java.util.Comparator;

public class ArbolAVL<E> {
	NodoAVL<E> raiz;
	Comparator<E> comp;
	
	public ArbolAVL(Comparator<E> comp) {
		raiz = new NodoAVL<E>(null);
		this.comp = comp;
	}
	
	public NodoAVL<E> getRaiz() {
		return raiz;
	}
	
	public void insert(E x) {
		insertAux(raiz,x);
	}
	
	private int max(int i, int j) {
		return i > j ? i : j;
	}
	
	@SuppressWarnings("unused")
	private void insertAux(NodoAVL<E> p, E item) {
		if(p.getRotulo() == null) {
			p.setRotulo(item);
			p.setAltura(1);
			p.setIzq(new NodoAVL<E>(null));
			p.getIzq().setPadre(p);
			p.setDer(new NodoAVL<E>(null));
			p.getDer().setPadre(p);
		}
		else {
			int comparacion = comp.compare(item,p.getRotulo());
			if(comparacion == 0) {
				p.setEliminado(false);
			}
			else if(comparacion < 0) { // Inserto hacia la izquierda
				insertAux(p.getIzq(),item);
				if(Math.abs(p.getIzq().getAltura() - p.getDer().getAltura()) > 1) {
					E x = p.getRotulo();
					E y = p.getIzq().getRotulo();
					E z = p.getIzq().getIzq().getRotulo();
					int compItemY = comp.compare(item,y);
					if(compItemY < 0)
						rotacion1(p);
					else 
						rotacion4(p);
				}
			}
			else { // Inserto hacia la derecha
				insertAux(p.getDer(),item);
				if(Math.abs(p.getIzq().getAltura() - p.getDer().getAltura()) > 1) {
					E x = p.getRotulo();
					E y = p.getDer().getRotulo();
					E z = p.getDer().getDer().getRotulo();
					int compItemY = comp.compare(item,y);
					if(compItemY < 0) // item < y
						rotacion2(p);
					else 
						rotacion3(p);
				}
			}
		}
		p.setAltura(max(p.getIzq().getAltura(),p.getDer().getAltura()) + 1);
	}
	
	// --------- < Para cuando termina en la izquierda > ---------
	
	@SuppressWarnings("unused")
	private void rotacion1(NodoAVL<E> n) { // Rotación simple de izquierda a derecha
		NodoAVL<E> x = n;
		NodoAVL<E> y = n.getIzq();
		NodoAVL<E> z = n.getIzq();
		NodoAVL<E> t1 = z.getIzq();
		NodoAVL<E> t2 = z.getDer();
		NodoAVL<E> t3 = y.getDer();
		NodoAVL<E> t4 = x.getDer();
		
		if(x.getPadre() != null) {
			y.setPadre(x.getPadre());
			if(x.getPadre().getIzq() == x)
				x.getPadre().setIzq(y);
			else
				x.getPadre().setDer(y);
		}
		else {
			y.setPadre(null);
			raiz = y;
		}
		
		t3.setPadre(x);
		x.setIzq(t3);
		x.setPadre(y);
		y.setDer(x);
		
		x.setAltura(x.getAltura()-1);
	}
	
	@SuppressWarnings("unused")
	private void rotacion2(NodoAVL<E> n) {  // Rotación doble de derecha a izquierda
		NodoAVL<E> x = n;
		NodoAVL<E> y = n.getDer();
		NodoAVL<E> z = y.getIzq();
		NodoAVL<E> t1 = x.getIzq();
		NodoAVL<E> t2 = z.getIzq();
		NodoAVL<E> t3 = z.getDer();
		NodoAVL<E> t4 = y.getDer();
		
		if(x.getPadre() != null) {
			z.setPadre(x.getPadre());
			if(x.getPadre().getIzq() == x) 
				x.getPadre().setIzq(z);
			else
				x.getPadre().setDer(z);
		}
		else {
			z.setPadre(null);
			raiz = z;
		}
		
		t3.setPadre(y);
		y.setIzq(t3);
		z.setDer(y);
		t2.setPadre(x);
		x.setDer(t2);
		z.setIzq(x);
		x.setPadre(z);
		y.setPadre(z);
		
		x.setAltura(x.getAltura()-2);
		y.setAltura(y.getAltura()-1);
		z.setAltura(z.getAltura()+1);
	}
	
	// --------- < Para cuando termina en la derecha > ---------
	
	@SuppressWarnings("unused")
	private void rotacion3(NodoAVL<E> n) { // Rotación de derecha a izquierda
		NodoAVL<E> x = n;
		NodoAVL<E> y = n.getDer();
		NodoAVL<E> z = n.getDer();
		NodoAVL<E> t1 = x.getIzq();
		NodoAVL<E> t2 = y.getIzq();
		NodoAVL<E> t3 = z.getIzq();
		NodoAVL<E> t4 = z.getDer();
		
		if(x.getPadre() != null) {
			y.setPadre(x.getPadre());
			if(x.getPadre().getIzq() == x)
				x.getPadre().setIzq(y);
			else
				x.getPadre().setDer(y);
		}
		else {
			y.setPadre(null);
			raiz = y;
		}
		
		t2.setPadre(x);
		x.setDer(t2);
		y.setIzq(x);
		x.setPadre(y);
		
		x.setAltura(x.getAltura()-1);
	}
	
	@SuppressWarnings("unused")
	private void rotacion4(NodoAVL<E> n) { // Rotación doble de izquierda a derecha
		NodoAVL<E> x = n;
		NodoAVL<E> y = n.getIzq();
		NodoAVL<E> z = y.getDer();
		NodoAVL<E> t1 = y.getIzq();
		NodoAVL<E> t2 = z.getIzq();
		NodoAVL<E> t3 = z.getDer();
		NodoAVL<E> t4 = x.getDer();
		
		if(x.getPadre() != null) {
			z.setPadre(x.getPadre());
			if(x.getPadre().getIzq() == x) 
				x.getPadre().setIzq(z);
			else
				x.getPadre().setDer(z);
		}
		else {
			z.setPadre(null);
			raiz = z;
		}
		
		t2.setPadre(y);
		y.setDer(t2);
		z.setIzq(y);
		y.setPadre(z);
		t3.setPadre(x);
		x.setIzq(t3);
		z.setDer(x);
		x.setPadre(z);
		
		x.setAltura(x.getAltura()-2);
		y.setAltura(y.getAltura()-1);
		z.setAltura(z.getAltura()+1);
	}
}
