import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Lauramv21 on 6/11/16.
 */
public class Main {
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

            Dijkstra camino = new Dijkstra(0, ciudad);
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
            mapaBuffer.close();
            rutasBuffer.close();
        } catch (FileNotFoundException fn) {
            System.out.println(fn.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
