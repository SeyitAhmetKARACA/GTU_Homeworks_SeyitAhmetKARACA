/**
 * Created by KARACA on 22.03.2017.
 */
public class StackC<E> implements IStack<E> {

    StackC(){
        head = null;
        size = 0;
    }
    private Node<E> head;
    private int size;
    /**
     * Verilen indexteki elemani geri verir.
     * @param index
     * @return
     */
    public E get(int index){
        if(index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        Node<E> temp = head;
        for(int i=0 ; i < index ; i++){
            temp = temp.next;
        }
        return temp.data;
    }
    /**
     * Stack eleman sayisi 0 ise true
     * degil ise false
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Stackten eleman siler
     * @return Son sildigi eleman
     */
    @Override
    public E pop() {
        if(size == 0)
            return null;

        Node<E> temp=head;
        E tempData;
        if(size() >= 3) {
            while (temp.next.next != null) {
                temp = temp.next;
            }

            tempData = temp.next.data;
            temp.next = null;
        }else if(size() == 2){
            tempData = temp.next.data;
            temp.next = null;
        }else{
            tempData = temp.data;
            temp = null;
        }
        size--;
        return tempData;
    }
    /**
     * Stacke eleman ekler
     * @param data
     * @return ekledigi eleman
     */
    @Override
    public E push(E data) {
        Node<E> temp = head;
        if(size == 0)
            head = new Node<E>(data);
        else {
            while(temp.next != null){
                temp = temp.next;
            }
            temp.next = new Node<E>(data);
        }
        size++;
        return data;
    }
    /**
     * Stack'teki eleman bilgisini dondurur
     * @return
     */
    @Override
    public int size() {
        return size;
    }


    private static class Node <E>{
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
}
