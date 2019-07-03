import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by KARACA on 22.03.2017.
 */
public class StackD<E> implements IStack<E>{
    Queue<E> queue;
    public StackD() {
        queue = new LinkedList<E>();
    }
    /**
     * Verilen indexteki elemani geri verir.
     * @param index
     * @return
     */
    public E get(int index){
        if(index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        Iterator<E> iter = queue.iterator();
        E temp=null;
        for(int i=0;i <= index ; i++)
            temp = iter.next();
        return temp;
    }

    /**
     * Stack eleman sayisi 0 ise true
     * degil ise false
     * @return
     */
    @Override
    public boolean isEmpty() {
        return queue.size() == 0;
    }
    /**
     * Stackten eleman siler
     * @return Son sildigi eleman
     */
    @Override
    public E pop(){
        if(size() <= 0)
            return null;
        E temp = remove();
        queue.remove(temp);
        return temp;
    }

    /**
     * Pop icin yardimci method
     * @return
     */
    private E remove(){
        LinkedList<E> a = (LinkedList<E>) queue;
        E temp = a.peekLast();
        a.remove(queue.size()-1);
        return temp;
    }
    /**
     * Stacke eleman ekler
     * @param data
     * @return ekledigi eleman
     */
    @Override
    public E push(E data) {
        queue.add(data);

        return data;
    }
    /**
     * Stack'teki eleman bilgisini dondurur
     * @return
     */
    @Override
    public int size() {
        return queue.size();
    }
}
