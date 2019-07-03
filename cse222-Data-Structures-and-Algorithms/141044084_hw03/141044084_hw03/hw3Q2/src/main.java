/**
 * Created by KARACA on 14.03.2017.
 */


public class main {

    public static void main(String[] args){
        SingleLinkedList<String> sl =new SingleLinkedList<String>();
        sl.add("kalem");
        sl.add("silgi");
        sl.add("2");
        sl.add("fare");
        sl.add("telefonu");
        sl.add("yedi");

        SingleLinkedList<Integer> s2 =new SingleLinkedList<Integer>();

        s2.add(1);
        s2.add(2);
        s2.add(3);
        s2.add(4);
        s2.add(5);

        System.out.println(sl.toString());
        System.out.println(sl.reverseToString());
        System.out.println();
        System.out.println(s2.toString());
        System.out.println(s2.reverseToString());

    }
}
