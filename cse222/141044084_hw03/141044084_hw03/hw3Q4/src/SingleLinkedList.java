import java.util.ArrayList;

/**
 *
 *
 * Created by KARACA on 13.03.2017.
 */
public class SingleLinkedList <E> {
    /**
     * SingleLinkedList parametresiz constructor
     */
    public SingleLinkedList(){
        head = new Node<E>(null,null);
        size =0;
        deleted= new ArrayList<Node<E>>();
    }

    private Node<E> head;
    private int size;
    private ArrayList<Node<E>> deleted;


    /**
     * SingleLinkedList Node sinifi
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

    /**
     * SingleLinkedList size'ı 0 ise çalışacak ekleme methodu
     * @param index Kacinci siraya eklenecek bilgisi
     * @param item noda eklenecek bilgi
     */
    private void addFirst(int index, E item){
        head.data = item;
        head.next = null;
        size++;
    }

    /**
     * SingleLinkedList in sonuna eleman ekleme
     * @param node sonrakina eklenecek node
     * @param item son elemanin icerdigi bilgi
     */
    private void addAfter(Node<E> node , E item){
        node.next = new Node<E>(item,node.next);
        size++;
    }

    /**
     * SingleLinkedList in toString metrhodu
     * @return
     */
    public String toString(){
        Node<E> nodeRef = head;
        StringBuilder result = new StringBuilder();
        while(nodeRef != null){
            result.append(nodeRef.data);
            if(nodeRef.next != null){
                result.append(" ");
            }
            nodeRef = nodeRef.next;
        }
        return result.toString();
    }

    /**
     * verilen indexteki node'u verir.
     * i < 0 ise head
     * i > size ise son node'un next ini geri dondurur
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
     * Node iceriği return eder
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
     * SingleLinkedList 2 eleman ekleeyen method
     * @param index
     * @param item
     */
    public void add(int index,E item){
        boolean check=true;
        if(index <0 || index > size){
            throw new IndexOutOfBoundsException(Integer.toString(index));
        }

        for(int i=0;i<deleted.size() && check ;i++){
            if(deleted.get(i).data.toString().equals(item.toString())){

                addNode(deleted.get(i));
                deleted.remove(i);
                check = false;
            }
        }
        if(check  && index == 0){
                addFirst(index, item);

        }else if(check && index != 0) {
            Node<E> node = getNode(index-1);
            addAfter(node, item);
        }
    }

    /**
     * SingleLinkedList e eleman ekleyen method
     * @param node
     */
    private void addNode(Node<E> node){
        if(size != 0){
            Node<E> temp = head;
            temp = getNode(size-1);
            temp.next = node;
            node.next=null;
        }else{
            head.data = node.data;
            head.next = null;
        }
        size++;
    }

    /**
     * SingleLinkedList in size bilgisini verir
     * @return
     */
    public int size(){
        return size;
    }
    public boolean add(E item){
        add(size,item);
        return true;
    }

    /**
     * SingleLinkedListten index ile node siler
     * @param index
     */
    public void remove(int index){
        if(index < 0 || index >= size){
            return;
        }

        if(size != 0){
            if(index == 0){
                deleted.add(head);
                if(size == 1)
                    head.next=null;
                else
                    head = head.next;
            }else if( index == size - 1){
                deleted.add(getNode(index-1).next);
                getNode(index-1).next=null;
            }else{
                Node<E> temp = getNode(index-1);
                deleted.add(temp.next);
                temp.next = temp.next.next;
            }
            size--;
        }
    }

    /**
     * Silinmis node lara ait toString
     * @return
     */
    public String toStringDeleted(){
        StringBuilder result = new StringBuilder();
        for(int i = 0 ; i < deleted.size();i++){
            result.append(deleted.get(i).data +" ");
        }
        return result.toString();
    }
}