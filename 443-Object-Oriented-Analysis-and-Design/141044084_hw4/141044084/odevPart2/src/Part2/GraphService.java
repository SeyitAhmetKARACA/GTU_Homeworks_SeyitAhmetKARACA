package Part2;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GraphService extends Remote {
    /**
     * Builg graph
     * @param acc Account
     * @param edges Edges
     * @param V vertexlerin sayısı
     * @return constructed graph
     * @throws RemoteException
     * @throws NoEnoughCreditException
     */
    Graph buildGraph(Account acc, Edge[] edges, int V) throws RemoteException, NoEnoughCreditException;

    /**
     * @param acc Account
     * @param g graph
     * @return incidence matrix
     * @throws RemoteException
     * @throws NoEnoughCreditException
     */
    double[][] getIncidenceMatrix(Account acc, Graph g) throws RemoteException, NoEnoughCreditException;

    /**
     *
     * @param acc account
     * @param g graph
     * @return MST
     * @throws RemoteException
     * @throws NoEnoughCreditException
     */
    Graph getMinimumSpanningTree(Account acc, Graph g) throws RemoteException, NoEnoughCreditException;

    /**
     * @param acc Account
     * @param g Graph
     * @return g.toString()
     * @throws RemoteException
     * @throws NoEnoughCreditException
     */
    String graphToString(Account acc, Graph g) throws RemoteException, NoEnoughCreditException;
}