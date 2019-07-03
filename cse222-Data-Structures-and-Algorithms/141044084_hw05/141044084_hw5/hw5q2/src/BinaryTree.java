import java.io.Serializable;

/**
 * Created by KARACA on 4.04.2017.
 */
public class BinaryTree<E> {

    Node<E> root;

    public BinaryTree(){
        root = null;
    }

    /**
     * Perform a preorder traversal.
     * @param node The local root
     * @param depth The depth
     * @param sb The string buffer to save the output
     */
    protected void preOrderTraverse(Node<E> node, int depth,StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }

    /**
     * Agacin verileri tutan yapi
     * @param <E>
     */
    protected static class Node<E>{
        protected Node<E> left,right;
        protected E data;

        Node(E _data){
            data = _data;
            left = null;
            right = null;
        }

        @Override
        public String toString(){
            return data.toString();
        }
    }


}
