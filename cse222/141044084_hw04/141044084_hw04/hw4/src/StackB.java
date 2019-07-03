import java.util.ArrayList;

/**
 * Bu sinif bir tane ArrayList objesinde elemanlarini tutuyor.
 * Methodlarini tuttugu arraylist objesi ile gerceklestiriyor.
 * Created by KARACA on 22.03.2017.
 */
public class StackB<E> implements IStack<E> {
    public StackB(){
        stackList = new ArrayList<E>();
    }

    /**
     * Stackin elemanlarini tutacagi yapi.
     */
    private ArrayList<E> stackList;

    /**
     * Verilen indexteki elemani geri verir.
     * @param index
     * @return
     */
    public E get(int index){
        if(index < 0 || index > size())
            throw new ArrayIndexOutOfBoundsException();

        return stackList.get(index);
    }

    /**
     * Stack eleman sayisi 0 ise true
     * degil ise false
     * @return
     */
    @Override
    public boolean isEmpty() {
        return stackList.size() == 0;
    }

    /**
     * Stackten eleman siler
     * @return Son sildigi eleman
     */
    @Override
    public E pop() {
        if(stackList.isEmpty())
            return null;

        E temp = stackList.get(stackList.size() - 1);
        stackList.remove(size()-1);
        return temp;
    }

    /**
     * Stacke eleman ekler
     * @param data
     * @return ekledigi eleman
     */
    @Override
    public E push(E data) {
        stackList.add(data);
        return data;
    }

    /**
     * Stack'teki eleman bilgisini dondurur
     * @return
     */
    @Override
    public int size() {
        return stackList.size();
    }
}
