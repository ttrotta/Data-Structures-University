package TDAArbolBinario;

import TDALista.*;

import java.util.Iterator;

import Interfaces.*;

public class MetodosABB {
	//Ejercicio 8
	/*  Resuelva el siguiente problema: Dado un conjunto S implementado con un ABB y dados tres
	enteros i, j y k, se desean obtener los elementos de S ubicados en las posiciones i, j y k de S de
	acuerdo a su ordenación por <=. Nota: La ordenación de S inducida por <= es una lista [a1, a2,
	…, ai-1, ai, ai+1, …, an] tal que a1 <= a2 <= …<= ai-1 <= ai <= ai+1 <= …<= an. De el tiempo de
	ejecución de su solución justificando apropiadamente. Explique si el método (o los métodos) a
	programar son parte o no de la clase ABB y por qué lo decidió de esa manera. */
	public static <E extends Comparable<E>> void obtenerElem(ArbolBB<E> S, int i, int j, int k) {
		NodoABB<E> aux = S.getRaiz();
		PositionList<NodoABB<E>> lista = new ListaDoblementeEnlazada<NodoABB<E>>();
		int t = 0;
		obtenerElemAux(aux,lista);

		Iterator<NodoABB<E>> it = lista.iterator();
		NodoABB<E> nodoI,nodoJ,nodoK;
		nodoI = nodoJ = nodoK = null;
		boolean encontreI, encontreJ, encontreK;
		encontreI = encontreJ = encontreK = false;
		while(it.hasNext() && !(encontreI && encontreJ && encontreK)) {
			t++;
			aux = it.next();
			if(i == t) {
				encontreI = true;
				nodoI = aux;
			}
			if(j == t) {
				encontreJ = true;
				nodoJ = aux;
			}
			if(k == t) {
				encontreK = true;
				nodoK = aux;
			}
		}
		System.out.println();
		System.out.println(i+": "+nodoI.getRotulo());
		System.out.println(j+": "+nodoJ.getRotulo());
		System.out.println(k+": "+nodoK.getRotulo());
	}
	
	private static <E extends Comparable<E>> void obtenerElemAux(NodoABB<E> a, PositionList<NodoABB<E>> lista) {
		if(a.getIzq().getRotulo() != null) obtenerElemAux(a.getIzq(),lista);
		lista.addLast(a);
		if(a.getDer().getRotulo() != null) obtenerElemAux(a.getDer(),lista);
	}

	public static void imprimirABB(ArbolBB<Integer> t) {
		NodoABB<Integer> aux = t.getRaiz();
		System.out.print("[-");
		imprimirRec(aux);
		System.out.println("]");
	}

	private static <E extends Comparable<E>> void imprimirRec(NodoABB<E> aux) {
		if(aux.getIzq().getRotulo() != null) imprimirRec(aux.getIzq());
		System.out.print(aux.getRotulo()+"-");
		if(aux.getDer().getRotulo() != null) imprimirRec(aux.getDer());
	}


	@SuppressWarnings("static-access")
	public static void main(String args[]) {
		ArbolBB<Integer> ABB = new ArbolBB<Integer>(new Comparador<Integer>());

		NodoABB<Integer> aux = ABB.buscar(110);
		aux.setRotulo(110);
		ABB.expandir(aux);

		aux = ABB.buscar(60);
		aux.setRotulo(60);
		ABB.expandir(aux);

		aux = ABB.buscar(45);
		aux.setRotulo(45);
		ABB.expandir(aux);

		aux = ABB.buscar(10);
		aux.setRotulo(10);
		ABB.expandir(aux);

		aux = ABB.buscar(75);
		aux.setRotulo(75);
		ABB.expandir(aux);

		aux = ABB.buscar(120);
		aux.setRotulo(120);
		ABB.expandir(aux);

		aux = ABB.buscar(150);
		aux.setRotulo(150);
		ABB.expandir(aux);

		aux = ABB.buscar(160);
		aux.setRotulo(160);
		ABB.expandir(aux);

		aux = ABB.buscar(130);
		aux.setRotulo(130);
		ABB.expandir(aux);

		aux = ABB.buscar(115);
		aux.setRotulo(115);
		ABB.expandir(aux);

		aux = ABB.buscar(112);
		aux.setRotulo(112);
		ABB.expandir(aux);

		aux = ABB.buscar(117);
		aux.setRotulo(117);
		ABB.expandir(aux);
		
		System.out.println("                                    ----------------- Imprimiendo árbol ABB -----------------");
		System.out.println();
		TreePrinterABB t = new TreePrinterABB();
		t.mostrarPorNiveles((NodoABB<Integer>)ABB.getRaiz());
		System.out.println();
		System.out.println();
		imprimirABB(ABB);
		System.out.println();
		System.out.println();
		System.out.println("10: "+ABB.ejercicio14(10)+" valor esperado(10)");
		System.out.println("45: "+ABB.ejercicio14(45)+" valor esperado(10)");
		System.out.println("60: "+ABB.ejercicio14(60)+" valor esperado(45)");
		System.out.println("75: "+ABB.ejercicio14(75)+" valor esperado(60)");
		System.out.println("110: "+ABB.ejercicio14(110)+ " valor esperado(75)");
		System.out.println("112: "+ABB.ejercicio14(112)+ " valor esperado(110)");
		System.out.println("115: "+ABB.ejercicio14(115)+ " valor esperado(112)");
		System.out.println("117: "+ABB.ejercicio14(117)+ " valor esperado(115)");
		System.out.println("120: "+ABB.ejercicio14(120)+ " valor esperado(117)");
		System.out.println("130: "+ABB.ejercicio14(130)+ " valor esperado(120)");
		System.out.println("150: "+ABB.ejercicio14(150)+ " valor esperado(130)");
		System.out.println("160: "+ABB.ejercicio14(160)+ " valor esperado(150)");
	}
}

