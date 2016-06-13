import java.util.ArrayList;

/**
 * Created by Lauramv21 on 6/11/16.
 */
public class Graph {


    private ArrayList<ArrayList<Integer>> list;
    private ArrayList<Integer> successors;
    private int size;

    public Graph(int size) {
        this.size = size;
        list = new ArrayList<ArrayList<Integer>>();

    }

    public void addArc(int source, int destination, int weight) {
        ArrayList<Integer> arc = new ArrayList<Integer>();

        int i = 0;
        if (list.size() != 0) {
            while (list.get(i) != null) {
                int num = list.get(i).get(0);
                if (num == source) {
                    list.get(i).add(destination);
                    list.get(i).add(weight);
                    return;
                }

                i++;
                if (i == list.size()) {
                    break;
                }

            }
        }
        arc.add(source);
        arc.add(destination);
        arc.add(weight);
        list.add(arc);
        return;

    }

    public int getWeight(int source, int destination) {
        if (source < size() && destination < size()) {
            int i = 0;
            while (i<list.size()&&list.get(i) != null) {

                ArrayList<Integer> subList = list.get(i);

                if (subList.get(0) == source) {
                    int size = subList.size();
                    for (int j = 1; j < size; j++) {
                        if (subList.get(j) == destination) {
                            if (subList.size() != 0) {
                                //j+1

                                return subList.get(j+1);
                            } else {
                                return 9999;
                            }
                        }

                    }

                }
                i++;
            }
        }
        return 9999;

    }

    public ArrayList<Integer> getSuccessors(int vertice) {
        successors = new ArrayList<Integer>();

        int i = 0;
        while (list.get(i) != null) {

            ArrayList<Integer> subList = list.get(i);

            if (subList.get(0) == vertice) {
                int size = subList.size();
                for (int j = 1; j <= size - 2; j += 2) {
                    successors.add(subList.get(j));

                }
                return successors;

            }
            i++;

        }

        return successors;

    }
    public int size() {
        return size;
    }
}
