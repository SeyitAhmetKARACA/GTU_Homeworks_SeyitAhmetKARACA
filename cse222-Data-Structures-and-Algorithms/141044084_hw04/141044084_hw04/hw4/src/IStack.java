/**
 * Created by KARACA on 22.03.2017.
 */
public interface IStack<E> {
    boolean isEmpty();
    E pop();
    E push(E data);
    int size();
}
