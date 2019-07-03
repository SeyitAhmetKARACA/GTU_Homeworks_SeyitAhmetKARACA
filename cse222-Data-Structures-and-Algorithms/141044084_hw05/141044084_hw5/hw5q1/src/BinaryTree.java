import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by KARACA on 4.04.2017.
 */
public class BinaryTree<E> {
    /**
     * tree nin kok dugumu
     */
    Node<E> root;

    /**
     * no parameter constructor
     */
    public BinaryTree(){
        root = null;
    }

    /**
     * BinaryTree ye complate tree olacak sekilde eleman
     * ekler
     * @param param
     * @return
     */
    public boolean add(E param){
        Node<E> item = new Node<E>(param);
        if(root == null){
            root = item;
            return true;
        }

        Queue<Node<E>> queue = new LinkedList<Node<E>>();
        queue.offer(root);
        while(true){
            Node<E> node = queue.remove();
            if(node.left != null) {
                queue.add(node.left);
            }else{
                node.left = item;
                break;
            }

            if(node.right != null) {
                queue.add(node.right);
            }else{
                node.right = item;
                break;
            }
        }

        return true;
    }

    public void preorder(){
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root,1,sb);
        System.out.print(sb);
    }

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
     * One parameter constructor
     * @param _root agaci olustururken kok dugumu
     */
    public BinaryTree(Node<E> _root){
        root = _root;
    }

    /**
     * Tree yi olusturmak icin gerekli yapi
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
