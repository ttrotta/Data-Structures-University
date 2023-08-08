package TDAArbolBinario;

public class MetodosAVL {
	@SuppressWarnings("static-access")
	public static void main(String args []) {
		ArbolAVL<Integer> AVL = new ArbolAVL<Integer>(new Comparador<Integer>());
		AVL.insert(1);
		AVL.insert(5);
		AVL.insert(8);
		AVL.insert(15);
		AVL.insert(10);
		AVL.insert(4);
		AVL.insert(2);
		AVL.insert(19);
		AVL.insert(9);
		AVL.insert(3);
		TreePrinterAVL t = new TreePrinterAVL();
		t.mostrarPorNiveles((NodoAVL<Integer>)AVL.getRaiz());
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		ArbolAVL<Integer> AVL2 = new ArbolAVL<Integer>(new Comparador<Integer>());
		AVL2.insert(44);
		AVL2.insert(17);
		AVL2.insert(32);
		AVL2.insert(78);
		AVL2.insert(88);
		AVL2.insert(50);
		AVL2.insert(48);
		AVL2.insert(62);
	
		TreePrinterAVL t2 = new TreePrinterAVL();
		t2.mostrarPorNiveles((NodoAVL<Integer>)AVL2.getRaiz());
		
	}
}
