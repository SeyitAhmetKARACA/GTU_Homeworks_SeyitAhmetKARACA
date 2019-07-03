import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * Created by KARACA on 5.04.2017.
 */
public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> implements Iterable  {

    /**
     * BinarySearchTree 'nin iteratoru
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        return new BSTIterator(root);
    }

    /**
     * Iterator ile preorder agaci gezer
     */
    public void levelOrder(){
        Iterator iter = iterator();
        while(iter.hasNext() == true) {
            System.out.println(iter.next());
        }
    }

    private boolean addReturn;


    @Override
    public boolean add(E item) {
        root = add(root, item);
        return addReturn;
    }

    /**
     * Tree 'ye node ekler
     * @param localRoot : eklenecek node
     * @param item : locakRoot 'un verisi
     * @return
     */
    private Node<E> add(Node<E> localRoot, E item) {
        if (localRoot == null) {
            addReturn = true;
            return new Node<E>(item);
        } else if (item.compareTo(localRoot.data) == 0) {
            addReturn = false;
            return localRoot;
        } else if (item.compareTo(localRoot.data) < 0) {
            localRoot.left = add(localRoot.left, item);
            return localRoot;
        } else {
            localRoot.right = add(localRoot.right, item);
            return localRoot;
        }
    }

    /**
     * BinarySearchTree inner iterator sinifi
     * @param <E>
     */
    private class BSTIterator<E> implements Iterator{
        Node<E> rootIter;
        Queue<Node<E>> queue = new ArrayDeque<Node<E>>();

        BSTIterator(BinaryTree.Node _root){
            rootIter = _root;
            if(queue.isEmpty()){
                queue.add(rootIter);
            }
        }

        @Override
        public boolean hasNext() {
            return rootIter != null;
        }

        @Override
        public E next() {
            Node<E> Temp = queue.remove();

            if(Temp.left != null) {
                queue.add(Temp.left);
            }

            if(Temp.right != null) {
                queue.add(Temp.right);
            }

            return Temp.data;
        }
    }
}