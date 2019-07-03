import java.util.AbstractCollection;
import java.util.Iterator;

/**
 * appendAnything methodunun calisabilmesi icin myAbstractCollection sınıfından
 * extend edilmis siniflarda sinif üzerinde iterator ve add methodları override edilmis
 * olmasi gerekir.appendAnything methodu için gerekli bu iki method implement edilmiş ise
 * kullanılabilir.
 *
 *
 * Created by KARACA on 13.03.2017.
 */

/* Q3 */
public abstract class myAbstractCollection<E> extends AbstractCollection{
    /**
     * myAbstractCollection ve ondan tureyen herhangi bir sinif
     * parametresi alip bu sinifi cagiran sinifa ekler
     * @param other
     */
    public void appendAnything(myAbstractCollection<E> other){
    Iterator<E> otherIter = other.iterator();

        while(otherIter.hasNext()){
            this.add(otherIter.next());
        }
    }
}
