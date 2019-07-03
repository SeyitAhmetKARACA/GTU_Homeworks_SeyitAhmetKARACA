/**
 * Created by KARACA on 13.03.2017.
 */
public class main {
    public static void main(String[] args) {
        SingleLinkedList<Integer> sl = new SingleLinkedList<Integer>();
        for(int i= 0; i < 100 ; i++)
            sl.add(i);

        for(int i= 50 ; i < 100 ; i++)
            sl.remove(i);

        for(int i= 50; i < 75 ; i++)
            sl.add(i);

        System.out.println(sl.toStringDeleted());
        System.out.println(sl.toString());
    }
}
