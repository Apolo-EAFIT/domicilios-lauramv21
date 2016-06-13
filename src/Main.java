import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static int[][] matriz;
    private static int cont = 0;
    public static void main(String args[]) {
        try {
            String mapa = args[0];
            FileReader mapaFile = new FileReader(mapa);
            BufferedReader mapaBuffer = new BufferedReader(mapaFile);
            String rutas = args[1];
            FileReader rutasFile = new FileReader(rutas);
            BufferedReader rutasBuffer = new BufferedReader(rutasFile);
            int numNodos = Integer.parseInt(mapaBuffer.readLine());
            Graph ciudad = new Graph(numNodos);
            String lineaMapa;
            while ((lineaMapa = mapaBuffer.readLine()) != null) {
                String[] arrLinea = lineaMapa.split("\\s+");
                int source = Integer.parseInt(arrLinea[0]);
                int destination = Integer.parseInt(arrLinea[1]);
                int weight = Integer.parseInt(arrLinea[2]);
                ciudad.addArc(source, destination, weight);
            }
            int numRepat = Integer.parseInt(rutasBuffer.readLine());
            ArrayList<ArrayList<Integer>> arrRutas = new ArrayList<ArrayList<Integer>>();
            String lineaRutas;
            for (int i = 0; i < numRepat; i++) {
                ArrayList<Integer> unaRuta = new ArrayList<>();
                lineaRutas = rutasBuffer.readLine();
                String[] arrLinea = lineaRutas.split("\\s+");
                int[] arrIntLinea = new int[arrLinea.length];
                for (int j = 0; j < arrLinea.length; j++) {
                    unaRuta.add(Integer.parseInt(arrLinea[j]));
                }
                arrRutas.add(unaRuta);
            }

            for (int i = 0; i < arrRutas.size(); i++) {
                ArrayList<Integer> unaRuta = arrRutas.get(i);
                int[] arrUnaRuta = new int[unaRuta.size()];
                for (int j = 0; j < unaRuta.size(); j++) {
                    arrUnaRuta[j]=unaRuta.get(j);
                }
                int [][] permutacionesRuta = permute(1, arrUnaRuta);
                ArrayList<ArrayList<Integer>> rutaPermutacion = new ArrayList<ArrayList<Integer>>();
                for (int j = 0; j < permutacionesRuta.length; j++) {
                    int[] unaPerm = permutacionesRuta[i];
                    ArrayList<Integer> rutaCompleta = new ArrayList<>();
                    for (int k = 0; k < unaPerm.length-1; k++) {
                        Dijkstra camino = new Dijkstra(k,ciudad);
                        ArrayList<Integer> temp = camino.ruta(k+1);
                        rutaCompleta.addAll(temp);
                    }
                    rutaPermutacion.add(rutaCompleta);
                }
                int[] tamañosTotales = new int[rutaPermutacion.size()];
                for (int j = 0; j < tamañosTotales.length; j++) {
                    tamañosTotales[j]=pesoCaminoPermutado(rutaPermutacion.get(j),ciudad);
                }
                int min = 2147483647; //maximo valor de un entero
                int posMenor = 0;
                for (int j = 0; j < tamañosTotales.length; j++) {
                    int num = tamañosTotales[j];
                    if(num<min){
                        min=num;
                        posMenor=j;
                    }
                }
                ArrayList<Integer> menorRuta = rutaPermutacion.get(posMenor);
                System.out.print("El primer repartidor visitara: ");
                for (int num:menorRuta) {
                    System.out.print(num);
                }
                System.out.println("");
            }
            /**
            camino.caminoMinimos();
            ArrayList<Integer> ruta = camino.ruta(4);
            ruta = camino.sacarRepetido(ruta);
            System.out.println("Ruta:");
            for (int s:ruta) {
                System.out.println(s);
            }
            System.out.println("Peso de la ruta: "+camino.pesoRuta(ruta,ciudad));

            int[] intRuta = new int[ruta.size()];
            for (int i = 0; i < intRuta.length; i++) {
                intRuta[i] = ruta.get(i);
            }

            int[][] matriz = camino.permute(1,intRuta);
            System.out.println("Main:");
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    System.out.print(matriz[i][j]);
                }
                System.out.println("");
             }
             */
            cont = 0;
            mapaBuffer.close();
            rutasBuffer.close();
        } catch (FileNotFoundException fn) {
            System.out.println(fn.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int factorial(int n) {
        int result;

        if (n == 1)
            return 1;

        result = factorial(n - 1) * n;
        return result;
    }

    private static int[][] permute(int start, int[] input) {
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

    private static int pesoCaminoPermutado(ArrayList<Integer> lista, Graph grafo){
        int pesoTotal = 0;
        for (int i = 0; i < lista.size()-1; i++) {
            pesoTotal = pesoTotal + grafo.getWeight(lista.get(i),lista.get(i+1));
        }
        return pesoTotal;
    }

}
