package TDAArbolBinario;

import Exceptions.EmptyTreeException;
import Exceptions.InvalidOperationException;
import Exceptions.InvalidPositionException;
import Interfaces.BTPosition;
import Interfaces.BinaryTree;
import Interfaces.Position;

public class MetodosNotaciones {
	// Ejercicio 4 a)
	// Dado un árbol binario A que contiene una expresión aritmética. Escriba un método que
	// imprima en pantalla la expresión en notación prefija, sufija, e infija con los paréntesis
	// indispensables (ejercicio de parcial).

	public static <E> BinaryTree<Character> crearExpresion(String exp) {
		int indice = operadorPrinc(exp);
		char c = exp.charAt(indice);
		String parte1 = "";
		String parte2 = "";
		BinaryTree<Character> ar = new ArbolBinario<Character>();
		try {
			ar.createRoot(c);
			if(c=='+' || c=='-' || c=='/' || c=='*') {
				parte1 = recortarString(exp,-1,indice);
				parte2 = recortarString(exp,indice,exp.length());
				ar.attach(ar.root(), crearExpresion(parte1), crearExpresion(parte2));
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return ar;
	}
	private static <E> String recortarString(String s, int min,int max)
	{
		String aux = "";
		for(int i=0; i<s.length(); i++) {
			if(i>min && i<max)
				aux+=s.charAt(i);
		}
		return aux;
	}
	private static <E> int operadorPrinc(String exp) {
		boolean encontre = false;
		int c = 0;
		for(int i = 0;i<exp.length() && !encontre;i++) {
			if(exp.charAt(i)=='+' || exp.charAt(i)=='-') {
				encontre = true;
				c = i;
			}
		}
		for(int i = 0;i<exp.length() && !encontre;i++) {
			if(exp.charAt(i)=='*' || exp.charAt(i)=='/') {
				encontre = true;
				c = i;
			}
		}
		return c;
	}
	
	public static <E> void notacionInfija(BinaryTree<E> A) throws EmptyTreeException,InvalidPositionException {
		if(!A.isEmpty())
			System.out.println(notI((BTPosition<E>) A.root()));
	}

	private static <E> String notI(BTPosition<E> r) throws InvalidPositionException {
		String s = "";
		if(r.getLeft()!=null) {
			s+="("+notI(r.getLeft());
		}
		return s;
	}
	
	public static <E> void mostrarNotaciones(BinaryTree<E> T) {
		
	}
	
	// Ejercicio 4 b)
	// Escriba un método que cree el AB de expresión correspondiente a partir del listado de una
    // expresión aritmética que se ingresa por teclado y está formada por dígitos, y operadores de
	// suma, resta, producto y división.
	public static BinaryTree<Character> parse(String exp) {
		BinaryTree<Character> toReturn = new ArbolBinario<Character>();
		BinaryTree<Character> T1 = new ArbolBinario<Character>();
		BinaryTree<Character> T2 = new ArbolBinario<Character>();
		char [] w = exp.toCharArray();
		try {
			if(w.length != 0) {
				if(!contieneOperador(w))
					toReturn.createRoot(w[0]);
				else {
					eliminarPrimerUltimoParentesis(w);
					char operador = ultimoOperador(w);  
					T1 = parse(getLeftExpression(w,operador));
					T2 = parse(getRightExpression(w,operador));
					toReturn.addLeft(T1.root(), operador);
		            toReturn.addRight(T2.root(), operador);
				}	
			}
		}
		catch(InvalidOperationException | InvalidPositionException | EmptyTreeException e) {
			e.printStackTrace();
		}
		return toReturn;
	}
	
	private static String getLeftExpression(char[] exp, char op) {
		int opIndex = -1;
		for (int i = exp.length - 1; i >= 0; i--) {
			if (exp[i] == op) {
				opIndex = i;
				break;
			}
		}

		if (opIndex != -1) {
			return new String(exp, 0, opIndex).trim();
		} else {
			// Operador no encontrado
			return "";
		}
	}
	
	private static String getRightExpression(char[] exp, char op) {
		int opIndex = -1;
		for (int i = exp.length - 1; i >= 0; i--) {
			if (exp[i] == op) {
				opIndex = i;
				break;
			}
		}

		if (opIndex != -1) {
			return new String(exp, 0, opIndex).trim();
		} else {
			// Operador no encontrado
			return "";
		}
	}
	
	private static <E> boolean contieneOperador(char [] w) {
		boolean found = false;
		for(int i = 0; i < w.length && !found; i++) {
			if(esOperador(w[i]))
				found = true;
		}
		return found;
	}
	
	private static <E> char ultimoOperador(char [] w) {
		char toReturn = ' '; 
		boolean wasFound = false;
		int count = 0;
		for(int i = w.length; i <= 0 && !wasFound; i--) {
			if(esParentesisAbierto(w[i])) 
				count++; // 1+2
			if(esParentesisCerrado(w[i])) 
				count--;
			if(count == 0) {
				toReturn = w[i+1];
				wasFound = true;
			}
		}
		return toReturn;
	}
	
	private static <E> void eliminarPrimerUltimoParentesis(char [] w) {
		boolean primeroEncontrado = false;
		boolean segundoEncontrado = false;
		for(int i = 0; i < w.length && !primeroEncontrado; i++) {
			if(esParentesisAbierto(w[i]) && !primeroEncontrado) {
				w[i] = ' ';
				primeroEncontrado = true;
			}
		}
		for(int i = w.length; i <= 0 && !segundoEncontrado; i--) {
			if(esParentesisCerrado(w[i]) && !primeroEncontrado) {
				w[i] = ' ';
				primeroEncontrado = true;
			}
		}
	}
	
	private static <E> boolean esParentesisAbierto(char c) {
		return c == '(';
	}
	
	private static <E> boolean esParentesisCerrado(char c) {
		return c == ')';
	}
	
	private static <E> boolean esOperador(char c) {
		return c == '/' || c == '*' || c == '+' || c == '-';
	}
	
	@SuppressWarnings("static-access")
	public static void main(String args []) {
		BinaryTree<Character> arbolBinario = new ArbolBinario<Character>();
		TreePrinter t = new TreePrinter();
		arbolBinario = crearExpresion("2+3+4");
		try {
			t.mostrarPorNiveles((BTNodo<Character>) arbolBinario.root());
			for(Position<Character> v : arbolBinario.positions())
				System.out.print(v.element() + "  ");
		} catch (EmptyTreeException e) {
			e.printStackTrace();
		}
	}
	
}
