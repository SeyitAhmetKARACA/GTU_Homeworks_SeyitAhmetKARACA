import java.util.Iterator;
/**
 * Single linked list iterable sınıfını implement etmiş ve
 * tek yönlü elemanların içerisinde gezen bir sınıftır.
 * Created by KARACA on 13.03.2017.
 */
public class SingleLinkedList <E> implements Iterable<E>{

    public SingleLinkedList(){
        head = new Node<E>(null,null);
        size=0;
    }

    /**
     * SingleLinkedList iterator
     * @return
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> iterNode = head;

            @Override
            public boolean hasNext() {
                if(iterNode == null){
                    return false;
                }else{
                    return true;
                }
            }

            @Override
            public E next() {
                if(hasNext()){
                    E _data = (E)iterNode.data;
                    iterNode = iterNode.next;
                    return _data;
                }
                return null;
            }
        };
    }

    /**
     * Single linked listin tutulduğu yapı
     * @param <E>
     */
    private class Node <E>{
        private E data;
        private Node<E> next;

        private Node(E dataItem){
            data = dataItem;
            next = null;
        }

        private Node(E dataItem,Node<E> nodeRef){
            data = dataItem;
            next = nodeRef;
        }
    }
    private Node<E> head;
    private int size;


    /**
     * Size 0 iken single linked listi ilk elemanını verir.
     * @param item
     */
    private void addFirst(E item){
        head.data = item;
        head.next = null;
        size++;
    }

    /**
     * single linked liste veri ekler.
     * @param node Eklenecek node
     * @param item Node un verisi
     */
    private void addAfter(Node<E> node , E item){
        node.next = new Node<E>(item,node.next);
        size++;
    }

    /**
     * toString methotdu.
     * @return
     */
    public String toString(){
        Node<E> nodeRef = (Node<E>)head;
        String result="";
        while(nodeRef != null){
            result += ""+nodeRef.data;

            if(nodeRef.next != null){
                result +=" ";
            }
            nodeRef = nodeRef.next;
        }
        return result;
    }

    /**
     * indexi verilen nodu geri göndürür.
     * @param index
     * @return
     */
    private Node<E> getNode(int index){
        Node<E> node = head;
        for(int i=0; i < index && node != null ; i++)
            node = node.next;
        return node;
    }

    /**
     * gelen indexteki veriyi geri göndürür.
     * @param index
     * @return
     */
    public E get(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        Node<E> node = getNode(index);
        return node.data;
    }

    /**
     * Single link liste eleman ekleme yapar.
     * @param index
     * @param item
     */
    public void add(int index,E item){
        if(index <0 || index > size){
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }
        if(index == 0){
            addFirst(item);
        }else{
            Node<E> node = getNode(index-1);
            addAfter(node,item);
        }
    }

    /**
     * Size bilgisini geri döndürür.
     * @return
     */
    public int size(){
        return size;
    }

    /**
     * Public olarak gözüken ekleme methodu.
     * @param item
     * @return
     */
    public boolean add(E item){
        add(size,item);
        return true;
    }

    /**
     * toString methodu ile oluşan stringin
     * tam tersi sıra ile recursive ile yapılmış hali.
     * @return
     */
    public String reverseToString(){
        Node<E> temp = head;
        return reverse(temp);
    }
    private String reverse(Node<E> list){
        String str="";
        if(list.next == null) {
            return str+list.data;
        }
        str +=reverse(list.next);
        str +=" "+ list.data;
        return str;
    }

}