package TDAMapeo;

import Exceptions.EmptyQueueException;
import Exceptions.InvalidKeyException;
import Interfaces.Entry;
import Interfaces.Queue;

public class Metodos {
	public static Map<Character,Float> calcularFrecuenciasApariciones(Queue<Character> q) {
		Map<Character,Integer> m = new MapeoConLista<Character,Integer>();
		int cantidadTotalCaracteres = q.size();
		while(!q.isEmpty()) {
			try { 
				char c = q.dequeue();
				if(m.get(c) == null)
					m.put(c, 1);
				else
					m.put(c, m.get(c) + 1);
			} catch(EmptyQueueException | InvalidKeyException e) {
				e.printStackTrace();
			}
		} 
		Map<Character,Float> resultado = new MapeoConLista<Character,Float>();
		for(Entry<Character, Integer> e : m.entries()) {
			try {
				resultado.put(e.getKey(), ((float)e.getValue())/cantidadTotalCaracteres);
			} catch (InvalidKeyException e1) {
				e1.printStackTrace();
			}
		}
		return resultado;
	}
}
