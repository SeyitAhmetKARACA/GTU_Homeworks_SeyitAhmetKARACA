import java.util.ArrayList;

/**
 * StackA ,ArrayList sinifindan turemis bir sinitir.
 * StackA nin kendine ait olan methodlarinda ArrayListten yararlanilmistir.
 *
 * Created by KARACA on 22.03.2017.
 */
public class StackA<E> extends ArrayList<E> implements IStack<E>{
    /**
     * Stack'ten eleman siler ve sildigi elemani geri donrurur
     * @return
     */
    @Override
    public E pop() {
        if(!isEmpty()){
            E temp = super.get(size()-1);
            super.remove(size()-1);
            return temp;
        }else
            return null;
    }

    /**
     * Stack'e eleman ekler
     * @param data
     * @return
     */
    @Override
    public E push(E data) {
        super.add(data);
        return data;
    }

    /**
     * Stackteki eleman bilgisini dondurur
     * @return
     */
    @Override
    public int size(){
        return super.size();
    }

    /**
     * Stack bos ise true degil ise false dondurur
     * @return
     */
    @Override
    public boolean isEmpty(){
        return super.isEmpty();
    }
}
