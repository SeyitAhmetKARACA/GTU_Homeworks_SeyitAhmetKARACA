package Part2;

import java.io.Serializable;

public interface Graph extends Serializable {
    /**
     *
     * @return Edge Array
     */
    Edge[] getEdges();

    /**
     * It gives how many vertexes in graph
     * @return
     */
    int getV();
}