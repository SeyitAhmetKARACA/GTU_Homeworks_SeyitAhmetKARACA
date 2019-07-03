import java.util.Iterator;
import java.util.Queue;

/**
 * Created by KARACA on 22.03.2017.
 */
public class myQueue<E> extends KWLinkedList<E>{

    /**
     * Index ile hedef elemanin datasini degistirir
     * @param index
     * @param data
     */
    public void set(int index,E data){
        super.set(index,data);
    }

    /**
     * Belirtilen indexteki elemani geri dondurur
     * @param index Position of item to be retrieved
     * @return
     */
    public E get(int index){
        if(index <0 || index >= size())
            throw new ArrayIndexOutOfBoundsException();
        return super.get(index);
    }

    /**
     * Queue objesi alip elemanlarini ters cevirir
     * @param que
     * @return
     */
    public Queue reverseQueue(Queue que){
        Iterator it = que.iterator();
        helper(que,it);
        return que;
    }

    /**
     * reverseQueue 'nun helperi
     * @param que
     * @param iter
     */
    public void helper(Queue<E> que,Iterator<E> iter){
        if(que.size() == 1)
            return;
        else{
            E data = iter.next();
            iter.remove();
            helper(que,iter);
            que.add(data);
            return ;
        }
    }

    /**
     * Sona eleman ekler
     * @param item
     */
    public void add(E item){
        super.addLast(item);
    }


    /**
     * ilk elemani siler
     * @return
     */
    public E remove(){
        if(size() == 0)
            throw new ArrayIndexOutOfBoundsException();
        Iterator it = super.iterator();
        E data = (E) it.next();
        it.remove();
        return data;
    }

    /**
     * KWLinkedList in addLast ini engeller
     * @param item - the item to be added
     */
    @Override
    public void addLast(E item){
        return;
    }

    /**
     * Cagiran myQueue nesnesinin elemanlarini ters cevirir
     */
    public void reverse(){
        int i=0;
        E data,data2;
        while(i <= super.size()-1-i && i != super.size()-1-i ){
            data = super.get(i);
            data2 = super.get(size()-1-i);
            super.set(i,data2);
            super.set(size()-i-1,data);
            i++;
        }

    }

}
