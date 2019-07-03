/**
 * 
 */
//package bil241Hw8;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
/**
 * @author KARACA
 *
 */

public interface Bigram<T>{

    public void readFile(String fileName)throws NoSuchElementException, FileNotFoundException;
    public int numGrams();
    public int numOfGrams(T a,T b);
    public String toString();
}
