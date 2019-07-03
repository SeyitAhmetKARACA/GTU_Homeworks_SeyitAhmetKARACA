import java.util.Iterator;

/**
 * StringBuilder ile aynı işleri yapması amacı ile yazılmıştır.
 * Append fonksiyonu ve 3 farklı şekilde toString methodu vardır.
 * iteratör , singlelinkedlist ve singlelinkedlist in get methodu ile
 * implement edilmiş olarak3 tanedir.
 * Created by KARACA on 13.03.2017.
 */
public class myStringBuilder{

    private SingleLinkedList<String> list;

    public myStringBuilder() {

        list = new SingleLinkedList<String>();
        iter = list.iterator();
    }

    private Iterator<String> iter;

    /**
     * Parametre olarak gelen değişkeni listenin sonuna ekler.
     * @param other
     */
    public void append(String other) {
        list.add(other);
    }

    /**
     * iterator ile implement edilmis toString methodu.
     * @return
     */
    public String toStringIter(){
        String str="";
        while(iter.hasNext()){
            str += iter.next() +" ";
        }
        return str;
    }

    /**
     * listin get mothoduyla implement edilmiş toString.
     * @return
     */
    public String toStringIndex(){
        int i=0;
        String str= "";
        while(i < list.size()){
            str += list.get(i)+" ";
            i++;
        }
        return str;
    }

    /**
     * linkedlistin kendi toStringi ile implement edilmiş toString.
     * @return
     */
    public String toStringLinked(){
        return list.toString();
    }


}
