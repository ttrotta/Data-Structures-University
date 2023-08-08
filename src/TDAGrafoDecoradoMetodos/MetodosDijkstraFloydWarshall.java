package TDAGrafoDecoradoMetodos;

import java.util.Scanner;

public class MetodosDijkstraFloydWarshall {
	private static int [][] matriz1 = null;
	private static int [][] matriz2 = new int[9][9];
	private static Scanner sc = new Scanner(System.in);
	
	// -------------- Algoritmo de Warshall -------------- 
	
	public static <V,E> void warshall(int [][] a, int n, int[][] w) {
		copiar(a,w,n,n);
		for(int k=0; k < n; k++) {
			for(int i=0; i < n; i++) {
				for(int j=0; j < n; j++) {
					w[i][j] = Math.max(w[i][j], Math.min(w[i][k], w[k][j]));
				}
			}
		}
	}
	
	public static <V,E> void clausuraTransitiva(int [][] a, int n, int [][] aEstrella) {
		int [][] prod1 = new int[n][n];
		int [][] prod2 = new int[n][n];
		copiar(a,prod1,n,n);
		for(int i=1; i < n; i++) {
			producto(prod1,a,n,n,n,prod2);
			hacerJoin(prod2,prod1,n,n);
		}
		copiar(prod1,aEstrella,n,n);
	}
	
	public static <V,E> void copiar(int [][] a, int[][] b, int n, int m) {
		for(int i=0; i < n; i++) {
			for(int j=0; j < m; j++) {
				b[i][j] = a[i][j];
			}
		}
	}
	
	public static <V,E> void hacerJoin(int [][] a, int [][] b, int n, int m) {
		for(int i=0; i < n; i++) {
			for(int j=0; j < m; j++) {
				b[i][j] = Math.max(b[i][j], a[i][j]);
			}
		}
	}
	
	public static <V,E> void producto(int [][] a, int [][] b, int n, int p, int m, int [][] c) {
		for(int i=0; i < n; i++) {
			for(int j=0; j < m; j++) {
				c[i][j] = 0;
				for(int k=0; k < p; k++) {
					c[i][j] = Math.max(c[i][j], Math.min(a[i][k], b[k][j]));
				}
			}
		}
	}
	
	// -------------- Algoritmo de Floyd -------------- 
	
	public static <V,E> int[][] floyd(int [][] adj, int [][] path) {
		int n = adj.length;
		int [][] ans = new int[n][n];
		copy(ans,adj);
		for(int k=0; k < n; k++) {
			for(int i=0; i < n; i++) {
				for(int j=0; j < n;j++) {
					if(ans[i][k] + ans[i][k] < ans[k][j]) {
						ans[i][j] = ans[i][k] + ans[i][k];
						path[i][j] = path[k][j];
					}
				}
			}
		}
		return ans;
	}
	
	public static <V,E> void copy(int [][] a, int [][] b) {
		for(int i=0; i < a.length; i++) {
			for(int j=0; j < a[0].length; j++) {
				a[i][j] = b[i][j];
			}
		}
	}
	
	// -------------- Algoritmo de Dijkstra -------------- 
	
	public static int [] ahiTeLoMando() {
		return null;
	}
	
	// --------------  Ajenos  -------------- 
	
	public static int [][] definirTamano(int[][] x) {
		System.out.println("Ingresar el tamano de la matriz filas - columnas: ");
		String datosIngresados = sc.next();
		String[] datos = datosIngresados.split("-");
		int filas = Integer.parseInt(datos[0]);
		int columnas = Integer.parseInt(datos[1]);
		x = new int[filas][columnas];
		return x;
	}

	public static void ingresarValores(int[][] x) {
		System.out.println("Matriz original: ");
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[0].length; j++)
				x[i][j] = 0;
		
		x[0][0] = 1;
		x[0][1] = 1;
		x[0][6] = 1;
		x[1][2] = 1;
		x[2][3] = 1;
		x[3][4] = 1;
		x[3][6] = 1;
		x[3][8] = 1;
		x[4][5] = 1;
		x[7][6] = 1;
	}
	
	public static void imprimirMatriz(int[][] x) {
		System.out.println("x|1 2 3 4 5 6 7 8 9");
		System.out.println("-|-----------------");
		for(int i = 0; i<x.length;i++) {
			System.out.print(i+1 + "|");
			for(int j = 0; j<x[0].length;j++)
				System.out.print(x[i][j] + " ");
			System.out.println();
		}
	}
	
	public static void main(String args []) {
		matriz1 = definirTamano(matriz1);
		System.out.println("Filas: "+ matriz1.length);
		System.out.println("Columnas: "+ matriz1[0].length);
		System.out.println();
		ingresarValores(matriz1);
		System.out.println();
		imprimirMatriz(matriz1);
		System.out.println();
		warshall(matriz1, matriz1.length, matriz2);
		System.out.println("Matriz con clausura reflexo-transitiva: ");
		imprimirMatriz(matriz2);
	}
}
