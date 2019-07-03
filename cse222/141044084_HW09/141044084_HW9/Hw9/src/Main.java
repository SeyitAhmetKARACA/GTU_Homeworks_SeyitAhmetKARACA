import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File graphData = new File("a.txt");
        MatrixGraph[] localMgArray ;
        Scanner scnr = new Scanner(graphData);
        MatrixGraph mg = (MatrixGraph) AbstractGraphExtended.createGraph(scnr,false,"Matrix");

        /* add random edges*/
        mg.writeGraphToFile("Oncesi.txt");
        mg.addRandomEdgesToGraph(mg.getNumV());
        mg.writeGraphToFile("Sonrasi.txt");

        /*        localMgArray = (MatrixGraph[]) mg.getConnectedComponentUndirectedGraph();

        for(int i=0 ; i < localMgArray.length ; i++){
            localMgArray[i].writeGraphToFile(i+".txt");
        }*/

        //localLgArray = (ListGraph[]) lg.getConnectedComponentUndirectedGraph();
        //dizi = localLgArray[2].breadthFirstSearch(11);
       // lg.getConnectedComponentUndirectedGraph();
     //   lg.q(0);
        //lg.writeGraphToFile("a.txt");


    }
}
