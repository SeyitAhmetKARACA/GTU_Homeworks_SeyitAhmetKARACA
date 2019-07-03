
import java.util.NavigableMap;
import java.util.Set;


public class Main
{
    public static void main(String args[]){
        final Boolean q1 = Q1Test();
        final Boolean q2 = Q2Test();
        if (q1 == q2 == Boolean.TRUE) {
            System.out.println("Your tests is done. Make sure that you test all methods of class!! " );
            return;
        }
    }
    public static Boolean Q1Test(){

        NavigableMap<String,String> turkey = new BinaryNavMap<String,String>();
        turkey.put("uskudar","istanbul");
        turkey.put("kadıkoy","istanbul");
        turkey.put("cekirge","bursa");
        turkey.put("gebze","kocaeli");
        turkey.put("niksar","tokat");
        turkey.put("kecıoren","ankara");
        turkey.put("aksaray","istanbul");
        turkey.put("foca","izmir");
        turkey.put("manavgat","antalya");
        turkey.put("kahta","adıyaman");
        turkey.put("biga","canakkale");

        System.out.println("The original set odds is \n" + turkey.toString());
        NavigableMap<String,String> m = turkey.subMap("gebze",true,"uskudar",false);
        System.out.println("The ordered set m is " + m);
        System.out.println("The first entry is " +turkey.firstEntry().getKey());

        System.out.println("LowerEntry (Value): "+turkey.lowerEntry("gebze").getValue());
        System.out.println("LowerKey : "+turkey.lowerKey("gebze"));
        System.out.println();


        System.out.println("floorEntry (Value): "+turkey.floorEntry("gebze").getValue());
        System.out.println("floorKey : "+turkey.floorKey("gebze"));
        System.out.println();

        System.out.println("ceilingEntry (Value): "+turkey.ceilingEntry("gebze").getValue());
        System.out.println("ceilingKey : "+turkey.ceilingKey("gebze"));
        System.out.println();

        System.out.println("higherEntry (Value): "+turkey.higherEntry("gebze").getValue());
        System.out.println("higherKey : "+turkey.higherKey("gebze"));
        System.out.println();

        System.out.println("firstEntry (Value): "+turkey.firstEntry().getValue());
        System.out.println();

        System.out.println("lastEntry (Value) : "+turkey.lastEntry().getValue());
        System.out.println();

        System.out.println("pollFirstEntry (Value): "+turkey.pollFirstEntry().getValue());
        System.out.println();

        System.out.println("firstEntry (Value): "+turkey.firstEntry().getValue());
        System.out.println();

        System.out.println("lastEntry (Value): "+turkey.lastEntry().getValue());
        System.out.println("pollLastEntry : "+turkey.pollLastEntry().getKey());
        System.out.println();

        NavigableMap<String,String> desMap = turkey.descendingMap();
        System.out.println("descendingMap : "+desMap);
        System.out.println();

        Set<String> set = turkey.descendingKeySet();
        System.out.println("descendingKeySet : "+set);
        System.out.println();

        set = turkey.navigableKeySet();
        System.out.println("navigableKeySet : "+set);
        System.out.println();

        //you should write more test function to show your solution
        //your test must contain all methods to get full points!!!
        //you also may need to owerwrite some methods to provide BST RULES

        /* *some links to help you

           https://docs.oracle.com/javase/8/docs/api/java/util/NavigableMap.html
           https://docs.oracle.com/javase/8/docs/api/java/util/AbstractMap.html

        * */
        return Boolean.TRUE;

    }
    public static Boolean Q2Test(){
        HashMap<String,String> turkey=new HashTableChaining<String,String>();
        turkey.put("edremit","balikesir");
        turkey.put("edremit","van");
        turkey.put("kemalpasa","bursa");
        turkey.put("kemalpasa","izmir");
        turkey.put("ortakoy","istanbul");//we assume a district
        turkey.put("ortakoy","aksaray");
        turkey.put("ortakoy","corum");
        turkey.put("kecıoren","ankara");
        turkey.put("pinarbasi","kastamonu");
        turkey.put("pinarbasi","kayseri");
        turkey.put("eregli","konya");
        turkey.put("eregli","tekirdag");
        turkey.put("eregli","zonguldak");
        turkey.put("golbasi","adıyaman");
        turkey.put("golbasi","ankara");
        turkey.put("biga","canakkale");

        /* *test all

            V get(Object key);

            V put(K key, V value);

            V remove(Object key);

            int size();

        * */


        return Boolean.TRUE;
    }


}
