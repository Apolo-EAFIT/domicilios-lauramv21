import java.util.ArrayList;

/**
 * Created by Lauramv21 on 6/11/16.
 */

public class Dijkstra {
    private int mCostos[][];
    private int ultimo[];//aqui se guarda desde el origen al destino el ultimo vertice que se visito antes del destino
    private int D[]; //aqui se guardan los costos minimos a cada vertice
    private boolean F[];//vertices visitados
    private int s, n; // s es el origen
    private int[][] matriz;
    private int cont = 0;

    public Dijkstra(int s, Graph g) {
        n = g.size();
        this.s = s;
        mCostos = insMatriz(g);
        ultimo = new int[n];
        D = new int[n];
        F = new boolean[n];
    }

    public void caminoMinimos() {
        // valores iniciales
        for (int i = 0; i < n; i++) {
            F[i] = false;//Inicializa los vertices como no visitados
            D[i] = mCostos[s][i];//pesos directos desde el origen hasta todos los posibles destinos
            ultimo[i] = s;
        }
        F[s] = true;
        D[s] = 0;
        // Pasos para marcar los n-1 vértices
        for (int i = 0; i < n; i++) {
            int v = minimo(); /* selecciona vértice no marcado
             de menor distancia */

            F[v] = true;
            // actualiza distancia de vértices no marcados
            for (int w = 0; w < n; w++) {
                if (!F[w]) {
                    if ((D[v] + mCostos[v][w]) < D[w]) {
                        D[w] = D[v] + mCostos[v][w];
                        ultimo[w] = v;
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println("costo minimo a " + i + ": " + D[i]);
        }
    }

    public int minimo() {
        int mx = 9999;
        int v = 1;
        for (int j = 0; j < n; j++) {
            if (!F[j] && (D[j] <= mx)) {
                mx = D[j];
                v = j;
            }
        }
        return v;
    }

    private int[][] insMatriz(Graph g) {
        int t = g.size();
        mCostos = new int[t][t];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < t; j++) {
                mCostos[i][j] = g.getWeight(i, j);
            }
        }

        return mCostos;
    }

    static ArrayList<Integer> ruta = new ArrayList<Integer>();

    public ArrayList<Integer> ruta(int j) {

        if (j != s) {
            ruta(ultimo[j]);
            if (D[j] != 9999) {
                System.out.println("Se recorrio de " + ultimo[j] + " a " + j);
                ruta.add(ultimo[j]);
                if (!ruta.contains(ultimo[j])) {
                    ruta.add(ultimo[j]);
                }
                if (!ruta.contains(j)) {
                    ruta.add(j);
                }
            } else {
                ruta.clear();
                ruta.add(-1);
            }
            //System.out.println("No es posible llegar a ese destino desde el origen: "+s);
        }
        return ruta;
    }

    public ArrayList<Integer> sacarRepetido(ArrayList<Integer> j) {
        ArrayList<Integer> j2 = new ArrayList<Integer>();
        for (int i = 0; i < j.size(); i++) {
            if (!j2.contains(j.get(i)))
                j2.add(j.get(i));
        }
        return j2;
    }

    public int pesoRuta(ArrayList<Integer> ruta, Graph ciudad) {
        int peso = 0;
        for (int i = 0; i < ruta.size() - 1; i++) {

            int tmpPeso = ciudad.getWeight(ruta.get(i), ruta.get(i + 1));
            System.out.println("Calculo peso entre: " + ruta.get(i) + " y " + ruta.get(i + 1) + " es: " + tmpPeso);
            peso = peso + tmpPeso;
        }
        return peso;
    }

    public ArrayList<Integer> permutaciones(ArrayList<Integer> elem, String act, int n, int r) {
        ArrayList<Integer> permutacion = new ArrayList<Integer>();
        if (n == 1) {
            //permutacion.add(Integer.parseInt(act));
            System.out.println(act);
        } else {
            for (int i = 1; i < r; i++) {
                if (!act.contains(elem.get(i).toString())) { // Controla que no haya repeticiones
                    permutaciones(elem, act + elem.get(i) + ", ", n - 1, r);
                }
            }
        }
        return permutacion;
    }

    private int factorial(int n) {
        int result;

        if (n == 1)
            return 1;

        result = factorial(n - 1) * n;
        return result;
    }

    public int[][] permute(int start, int[] input) {
        int tamFact = factorial((input.length) - 1);
        //System.out.println("Factorial: "+tamFact);
        //System.out.println("Tamaño input: "+ input.length);
        if (start == 1) {
            matriz = new int[tamFact][input.length+1];
        }
        if (start == input.length) {
            //System.out.println(input);
            int[] temp = new int[input.length+1];
            for (int i = 0; i < input.length; i++) {
                temp[i] = input[i];
                System.out.print(input[i]);
            }
            temp[temp.length-1]=temp[0];
            matriz[cont] = temp;
            cont++;
            System.out.println("");
            return matriz;
        }
        for (int i = start; i < input.length; i++) {
            // swapping
            int temp = input[i];
            input[i] = input[start];
            input[start] = temp;
            // swap(input[i], input[start]);

            permute(start + 1, input);
            // swap(input[i],input[start]);

            int temp2 = input[i];
            input[i] = input[start];
            input[start] = temp2;
        }
        return matriz;
    }

    public int pesoCaminoPermutado(ArrayList<Integer> lista, Graph grafo){
        int pesoTotal = 0;
        for (int i = 0; i < lista.size()-1; i++) {
            pesoTotal = pesoTotal + grafo.getWeight(lista.get(i),lista.get(i+1));
        }
        return pesoTotal;
    }
}