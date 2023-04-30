package TDACola;

import Interfaces.*;
import TDAPila.*;
import Exceptions.*;

public class Metodos {
	
	// Ejercicio 8
	public static Queue<Stack<Character>> union(Queue<Stack<Character>> cin1, Queue<Stack<Character>> cin2) {
		Queue<Stack<Character>> cout = new ColaEnlazada<Stack<Character>>();
		try {
			while(!cin1.isEmpty() && !cin2.isEmpty()){
				Stack<Character> c1 = cin1.front();
				Stack<Character> c2 = cin2.front();
				if(c1.size() <= c2.size())
					cout.enqueue(cin1.dequeue());
				else
					cout.enqueue(cin2.dequeue());
			}	
			while(!cin1.isEmpty()) {
				cout.enqueue(cin1.dequeue());
			}
			while(!cin2.isEmpty()) {
				cout.enqueue(cin2.dequeue());
			}
		} catch(EmptyQueueException e) {
			e.printStackTrace();
		}
		return cout;
	}
	
	
	// Ejercicio 9 
	public Stack<Integer> PPEaPE(Stack<Stack<Integer>> PPE){
		Stack<Integer> PE = new PilaArreglo<Integer>(100);
		try {
			while(!PPE.isEmpty()) {
				Stack<Integer> aux = PPE.pop();
				while(!aux.isEmpty()) {
					PE.push(aux.pop());
				}
			}
		} catch(EmptyStackException e) {
			e.printStackTrace();
		}
		return invertir(PE); 
	}
		
	public static Stack<Integer> invertir(Stack<Integer> pE) {
		int cant = pE.size();
		PilaArreglo<Integer> temp1 = new PilaArreglo<Integer>(pE.size());
		PilaArreglo<Integer> temp2 = new PilaArreglo<Integer>(pE.size());
		try {
			for(int i=0; i < cant; i++) {
				temp1.push(pE.pop());
			}
			for(int j=0; j < cant; j++) {
				temp2.push(temp1.pop());
			}
			for(int k=0; k < cant; k++) {
				pE.push(temp2.pop());
			}
		} catch(EmptyStackException e) {
			e.printStackTrace();
		}
		return pE;
	}
	
	// Ejercicio 10
	public static boolean reconoce(String cadena) {
		char [] w = cadena.toCharArray();
		ColaEnlazada <Character> cola = new ColaEnlazada<Character>();
		PilaEnlazada <Character> pila = new PilaEnlazada<Character>();
		char charAct = ' ';
		int tamanoW = w.length;
		boolean cumple = tamanoW >= 9;
		int puntero = 0;
		int i;
		try {
			while (puntero < tamanoW && cumple) {
				if (puntero > 0 && w[puntero] == 'x') {
					puntero++;
				}
				i = puntero;
				//Guarda la primera C en la Cola
				while (i < tamanoW && w[i] != 'z' && cumple) {
					charAct = w[i++];			
					cumple = charValido(charAct) && i < tamanoW;
					cola.enqueue(charAct);
					pila.push(charAct);
				}
				if (cumple) {
					pila.push(w[i]); 	// guarda la z
					i -= cola.size();	// i vuelve al origen de la cadena
				} 	
				//segundo recorrido para formar C
				while (cumple && i < tamanoW && w[i] != 'z' ) {
					charAct = w[i++];
					cola.enqueue(charAct);
				}
				puntero = i+1;		//puntero continua a partir de i
				//Como la C cumplio...
				//Empieza a verificar la CC
				while(i < tamanoW && cumple && w[puntero] != 'x') {
					charAct = w[puntero++];
					cumple = charValido(charAct);
					if (charAct == cola.dequeue()) {
						pila.push(charAct);
					}
					else
						cumple = false;
				}
				if(cola.isEmpty()) {
					puntero++;
				}
				else
					cumple = false;
				//Como la CC cumplio...
				//Empieza a verificar el inverso
				while(cumple && puntero < tamanoW && w[puntero] != 'x') {
					charAct = w[puntero++];
					cumple = charValido(charAct) && charAct == pila.pop();
				}
				cumple = pila.isEmpty();
			}
		}
		  catch (EmptyQueueException e) {
			System.out.println(e.getMessage());
			cumple = false;
		} catch (EmptyStackException e) {
			System.out.println(e.getMessage());
			cumple = false;
		}
		return cumple;
		}
		
	private static boolean charValido(char c) {
		return c == 'a' || c == 'b' || c == 'x'|| c == 'z'; 
	}
	
}
