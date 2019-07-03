import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by KARACA on 17.05.2017.
 */


public abstract class AbstractGraphExtended extends AbstractGraph{
    public AbstractGraphExtended(int _numV, boolean _directed) {
        super(_numV,_directed);
    }

    /**
     * Dizgeye rasgele edge olusturup ekleyen method
     * @param edgeLimit
     * @return
     */
    public int addRandomEdgesToGraph(int edgeLimit){
        if(edgeLimit <= getNumV()) {
            Random rand = new Random();
            Random rSource = new Random();
            Random rDest = new Random();
            ArrayList<Edge> ae = new ArrayList<Edge>();
            boolean cv = true;
            int randomEdgeNumber = rand.nextInt(edgeLimit);
            int _source, _dest;

            for (int i = 0; i < randomEdgeNumber - 1; i++) {
                _source = rSource.nextInt(edgeLimit);
                _dest = rDest.nextInt(edgeLimit);
                Edge edge = new Edge(_source, _dest);
                for (int j = 0; j < ae.size(); j++)
                    if (ae.get(j).equals(edge))
                        cv = false;

                if (cv && !isEdge(edge.getSource(),edge.getDest())) {
                    ae.add(edge);
                    insert(edge);
                }

                cv = true;
            }


            return ae.size();
        }else
            return 0;
    }

    /** Perform a breadth-first search of a graph.
     post: The array parent will contain the predecessor
     of each vertex in the breadth-first
     search tree.
     @param start The start vertex
     @return The array of parents
     */
    public int[] breadthFirstSearch(int start){
        Queue<Integer> theQueue = new LinkedList< Integer >();
        int[] parent = new int[getNumV()];
        for (int i = 0; i <getNumV(); i++) {
            parent[i] = -1;
        }

        boolean[] identified = new boolean[getNumV()];

        identified[start] = true;
        theQueue.offer(start);

        while (!theQueue.isEmpty()) {
      /* Take a vertex, current, out of the queue.
       (Begin visiting current). */
            int current = theQueue.remove();
      /* Examine each vertex, neighbor, adjacent to current. */
            Iterator<Edge> itr = edgeIterator(current);
            while (itr.hasNext()) {

                Edge edge = itr.next();
                int neighbor = edge.getDest();
                // If neighbor has not been identified
                if (!identified[neighbor]) {
                    // Mark it identified.
                    identified[neighbor] = true;
                    // Place it into the queue.
                    theQueue.offer(neighbor);
          /* Insert the edge (current, neighbor)
             into the tree. */
                    parent[neighbor] = current;
                }
            }
            // Finished visiting current.
        }

        return parent;
    }

    /**
     * Bagli olmayan graphlari array haline cevirip return eden method
     * @return
     */
    public Graph[] getConnectedComponentUndirectedGraph(){

        Set<Edge> edgeSet = new HashSet<>();
        Boolean[] boolArray = new Boolean[getNumV()];
        int gSayisi = 0;
        ListGraph[] gl = null;
        MatrixGraph[] gm = null;

        for(int i = 0 ; i < getNumV() ; i++)
            boolArray[i] = true;

        for(int i=0; i < getNumV() ; i++) {
            if(boolArray[i]) {
                editedBFS(i, boolArray);
                gSayisi++;
            }
        }

        if(this instanceof ListGraph ){
            gl = new ListGraph[gSayisi];
        }else{
            gm = new MatrixGraph[gSayisi];
        }
        int j=0;
        for(int i=0; i < getNumV() ; i++) {
            if(boolArray[i]) {
                edgeSet = editedBFS(i, boolArray);
                if(this instanceof ListGraph ){
                    gl[j] = new ListGraph(getNumV(),isDirected());
                }else{
                    gm[j] = new MatrixGraph(getNumV(),isDirected());
                }
                Iterator<Edge> it = edgeSet.iterator();

                while(it.hasNext()){
                    if(this instanceof ListGraph ){
                        gl[j].insert(it.next());
                    }else{
                        gm[j].insert((Edge) it.next());
                    }
                }
                j++;
            }
        }
        if(this instanceof ListGraph ){
            return gl;
        }else{
            return gm;
        }
    }

    /**
     * Hangi graph uzerinden cagrildi ise onu parametre ile belirtilmis
     * dosyaya yazan method
     * @param fileName
     * @throws IOException
     */
    public void writeGraphToFile(String fileName) throws IOException {
        FileWriter fr = new FileWriter(new File(fileName));
        int sumOfEdge=0;
        Set<Edge> edgeSet = new HashSet<>();
        Boolean[] boolArray = new Boolean[getNumV()];

        for(int i = 0 ; i < getNumV() ; i++)
            boolArray[i] = true;

        for(int i=0; i < getNumV() ; i++) {
            if(boolArray[i]) {
                editedBFS(i, boolArray);
            }
        }
        Edge edge;

        fr.write(getNumV()+"\n");

        for(int i=0; i < getNumV() ; i++) {
            if(boolArray[i]) {
                edgeSet = editedBFS(i, boolArray);
                Iterator<Edge> it = edgeSet.iterator();
                sumOfEdge += edgeSet.size();
                while(it.hasNext()){
                    edge = it.next();
                    fr.write(edge.getSource()+" "+edge.getDest()+"\n");
                }
            }
        }
        fr.close();
        return;
    }

    /**
     * Verilen vertexten aramaya baslayip gezdiklerini isaretleyen yardimci
     * olarak kullandigim method
     * @param start
     * @param tf
     * @return
     */
    private Set editedBFS(int start,Boolean[] tf){

        Queue<Integer> theQueue = new LinkedList< Integer >();

        Set<Edge> setEdge =new HashSet<Edge>();

        int[] parent = new int[getNumV()];
        for (int i = 0; i <getNumV(); i++) {
            parent[i] = -1;
        }

        boolean[] identified = new boolean[getNumV()];

        identified[start] = true;
        theQueue.offer(start);
        while (!theQueue.isEmpty()) {
            int current = theQueue.remove();
      /* Examine each vertex, neighbor, adjacent to current. */
            Iterator<Edge> itr = edgeIterator(current);
            while (itr.hasNext()) {

                Edge edge = itr.next();
                Edge q = new Edge(edge.getDest(),edge.getSource());
                if(!setEdge.contains(q))
                    setEdge.add(edge);

                int neighbor = edge.getDest();
                // If neighbor has not been identified
                if (!identified[neighbor]) {
                    // Mark it identified.
                    tf[neighbor] = false;
                    identified[neighbor] = true;
                    // Place it into the queue.
                    theQueue.offer(neighbor);
                    parent[neighbor] = current;
                }
            }
            // Finished visiting current.
        }
        return setEdge;
    }
}