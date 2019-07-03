/**
 * Created by syucer on 4/24/2017.
 */

import java.util.*;

public class BinaryNavMap<K,V> extends AbstractMap<K,V>
        implements NavigableMap<K,V>, Cloneable, java.io.Serializable
{
    ArrayList<Node<K,V>> nodeArrayList = new ArrayList<Node<K,V>>();

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i= 0 ; i < nodeArrayList.size(); i++){
            sb.append(nodeArrayList.get(i)._key+ "="+ nodeArrayList.get(i)._value);
            if(nodeArrayList.size()-1 != i)
                sb.append(", ");
        }
        sb.append("}");
        return sb.toString();
    }

    private static Node root; // BUNA BAK SONRA !!!!!!!!!!!!
    private boolean f;
    BinaryNavMap(){
        root=  new Node<K,V>();
        f = true;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K,V>> rSet = new TreeSet<Entry<K, V>>();
        for(int i=0; i < rSet.size() ; i++){
            rSet.add(nodeArrayList.get(i));
        }

        return rSet;
    }

    @Override
    public V put(K key,V value){
        Node<K,V> returnValue = new Node<K, V>();
        returnValue._value = value;
        if(f == true){
            firstPut(key,value);
            f = false;
        }else {
            Node<K, V> temp = root;
            returnValue = putHelper(root, key, value);
        }
        nodeArrayList.clear();
        inOrder(root);
        return returnValue._value;
    }


    private void firstPut(K key,V value){
        root.setValue(value);
        root.setKey(key);
    }

    private Node putHelper(Node<K,V> localRoot,K key,V value){
        if(localRoot == null){

            localRoot = new Node<K,V>(key,value);
            return localRoot;
        }

        if(localRoot.compareTo(key) == -1)
            localRoot.left = putHelper(localRoot.left,key,value);
        else if(localRoot.compareTo(key) == 1)
            localRoot.right = putHelper(localRoot.right,key,value);

        return localRoot;
    }

    public void inOrder(Node<K,V> xroot) {
        if(xroot.left != null)
            inOrder(xroot.left);

        nodeArrayList.add(xroot);

        if(xroot.right != null)
            inOrder(xroot.right);
    }
    /**
     * Returns a key-value mapping associated with the greatest key
     * strictly less than the given key, or {@code null} if there is
     * no such key.
     *
     * @param key the key
     * @return an entry with the greatest key less than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> lowerEntry(K key) {
        Entry<K,V> ent =null;
        for(int i=0 ; i < nodeArrayList.size() ; i++){
            if(nodeArrayList.get(i).compareTo(key) == 1){
                ent = nodeArrayList.get(i);
            }
        }

        if(ent == null)
            throw new NullPointerException();

        return ent;
    }

    /**
     * Returns the greatest key strictly less than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key the key
     * @return the greatest key less than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K lowerKey(K key) {
        Entry<K,V> ent =null;
        for(int i=0 ; i < nodeArrayList.size() ; i++){
            if(nodeArrayList.get(i).compareTo(key) == 1){
                ent = nodeArrayList.get(i);
            }
        }

        if(ent == null)
            throw new NullPointerException();

        return ent.getKey();
    }

    /**
     * Returns a key-value mapping associated with the greatest key
     * less than or equal to the given key, or {@code null} if there
     * is no such key.
     *
     * @param key the key
     * @return an entry with the greatest key less than or equal to
     * {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> floorEntry(K key) {
        Entry<K,V> ent =null;
        for(int i=0 ; i < nodeArrayList.size() ; i++){
            if(nodeArrayList.get(i).compareTo(key) == 1 || nodeArrayList.get(i).compareTo(key) == 0){
                ent = nodeArrayList.get(i);
            }
        }

        if(ent == null)
            throw new NullPointerException();

        return ent;
    }

    /**
     * Returns the greatest key less than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key the key
     * @return the greatest key less than or equal to {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K floorKey(K key) {
        Entry<K,V> ent =null;
        for(int i=0 ; i < nodeArrayList.size() ; i++){
            if(nodeArrayList.get(i).compareTo(key) == 1 || nodeArrayList.get(i).compareTo(key) == 0){
                ent = nodeArrayList.get(i);
            }
        }

        if(ent == null)
            throw new NullPointerException();

        return ent.getKey();
    }

    /**
     * Returns a key-value mapping associated with the least key
     * greater than or equal to the given key, or {@code null} if
     * there is no such key.
     *
     * @param key the key
     * @return an entry with the least key greater than or equal to
     * {@code key}, or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> ceilingEntry(K key) {
        Entry<K,V> ent =null;
        for(int i=nodeArrayList.size()-1 ; i >= 0  ; i--){
            if(nodeArrayList.get(i).compareTo(key) == -1 || nodeArrayList.get(i).compareTo(key) == 0){
                ent = nodeArrayList.get(i);
            }
        }

        if(ent == null)
            throw new NullPointerException();

        return ent;
    }

    /**
     * Returns the least key greater than or equal to the given key,
     * or {@code null} if there is no such key.
     *
     * @param key the key
     * @return the least key greater than or equal to {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K ceilingKey(K key) {
        Entry<K,V> ent =ceilingEntry(key);
        return ent.getKey();
    }

    /**
     * Returns a key-value mapping associated with the least key
     * strictly greater than the given key, or {@code null} if there
     * is no such key.
     *
     * @param key the key
     * @return an entry with the least key greater than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public Entry<K, V> higherEntry(K key) {
        Entry<K,V> ent =null;
        for(int i=nodeArrayList.size()-1 ; i >= 0  ; i--){
            if(nodeArrayList.get(i).compareTo(key) == -1){
                ent = nodeArrayList.get(i);
            }
        }

        if(ent == null)
            throw new NullPointerException();

        return ent;
    }

    /**
     * Returns the least key strictly greater than the given key, or
     * {@code null} if there is no such key.
     *
     * @param key the key
     * @return the least key greater than {@code key},
     * or {@code null} if there is no such key
     * @throws ClassCastException   if the specified key cannot be compared
     *                              with the keys currently in the map
     * @throws NullPointerException if the specified key is null
     *                              and this map does not permit null keys
     */
    @Override
    public K higherKey(K key) {
        Entry<K,V> ent =higherEntry(key);
        return ent.getKey();
    }

    /**
     * Returns a key-value mapping associated with the least
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the least key,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> firstEntry() {
        return nodeArrayList.get(0);
    }

    /**
     * Returns a key-value mapping associated with the greatest
     * key in this map, or {@code null} if the map is empty.
     *
     * @return an entry with the greatest key,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> lastEntry() {
        return nodeArrayList.get(nodeArrayList.size()-1);
    }

    /**
     * Removes and returns a key-value mapping associated with
     * the least key in this map, or {@code null} if the map is empty.
     *
     * @return the removed first entry of this map,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> pollFirstEntry() {
        if(nodeArrayList.size() == 0)
            return null;
        else{
            Entry<K,V> ent = nodeArrayList.get(0);
            nodeArrayList.remove(0);
            return ent;
        }
    }

    /**
     * Removes and returns a key-value mapping associated with
     * the greatest key in this map, or {@code null} if the map is empty.
     *
     * @return the removed last entry of this map,
     * or {@code null} if this map is empty
     */
    @Override
    public Entry<K, V> pollLastEntry() {
        if(nodeArrayList.size() == 0)
            return null;
        else{
            Entry<K,V> ent = nodeArrayList.get(nodeArrayList.size()-1);
            nodeArrayList.remove(nodeArrayList.size()-1);
            return ent;
        }
    }

    /**
     * Returns a reverse order view of the mappings contained in this map.
     * The descending map is backed by this map, so changes to the map are
     * reflected in the descending map, and vice-versa.  If either map is
     * modified while an iteration over a collection view of either map
     * is in progress (except through the iterator's own {@code remove}
     * operation), the results of the iteration are undefined.
     * <p>
     * <p>The returned map has an ordering equivalent to
     * <tt>{@link Collections#reverseOrder(Comparator) Collections.reverseOrder}(comparator())</tt>.
     * The expression {@code m.descendingMap().descendingMap()} returns a
     * view of {@code m} essentially equivalent to {@code m}.
     *
     * @return a reverse order view of this map
     */
    @Override
    public NavigableMap<K, V> descendingMap() {
        NavigableMap<K,V> nMap = new TreeMap<K,V>();

        for(int i= nodeArrayList.size()-1 ; i >= 0 ; i--){
            nMap.put(nodeArrayList.get(i)._key,nodeArrayList.get(i)._value);
        }
        return nMap;
    }

    /**
     * Returns a {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a navigable set view of the keys in this map
     */
    @Override
    public NavigableSet<K> navigableKeySet() {
        NavigableSet<K> nSet = new TreeSet<K>();
        for(int i= 0 ; i < nodeArrayList.size() ; i++){
            nSet.add(nodeArrayList.get(i)._key);
        }

        return nSet;
    }

    /**
     * Returns a reverse order {@link NavigableSet} view of the keys contained in this map.
     * The set's iterator returns the keys in descending order.
     * The set is backed by the map, so changes to the map are reflected in
     * the set, and vice-versa.  If the map is modified while an iteration
     * over the set is in progress (except through the iterator's own {@code
     * remove} operation), the results of the iteration are undefined.  The
     * set supports element removal, which removes the corresponding mapping
     * from the map, via the {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear} operations.
     * It does not support the {@code add} or {@code addAll} operations.
     *
     * @return a reverse order navigable set view of the keys in this map
     */
    @Override
    public NavigableSet<K> descendingKeySet() {
        NavigableSet<K> nSet = new TreeSet<K>();
        for(int i= 0 ; i < nodeArrayList.size() ; i++){
            nSet.add(nodeArrayList.get(i)._key);
        }

        return nSet.descendingSet();
    }

    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}.  If {@code fromKey} and
     * {@code toKey} are equal, the returned map is empty unless
     * {@code fromInclusive} and {@code toInclusive} are both true.  The
     * returned map is backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.
     * <p>
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside of its range, or to construct a
     * submap either of whose endpoints lie outside its range.
     *
     * @param fromKey       low endpoint of the keys in the returned map
     * @param fromInclusive {@code true} if the low endpoint
     *                      is to be included in the returned view
     * @param toKey         high endpoint of the keys in the returned map
     * @param toInclusive   {@code true} if the high endpoint
     *                      is to be included in the returned view
     * @return a view of the portion of this map whose keys range from
     * {@code fromKey} to {@code toKey}
     * @throws ClassCastException       if {@code fromKey} and {@code toKey}
     *                                  cannot be compared to one another using this map's comparator
     *                                  (or, if the map has no comparator, using natural ordering).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromKey} or {@code toKey}
     *                                  cannot be compared to keys currently in the map.
     * @throws NullPointerException     if {@code fromKey} or {@code toKey}
     *                                  is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *                                  {@code toKey}; or if this map itself has a restricted
     *                                  range, and {@code fromKey} or {@code toKey} lies
     *                                  outside the bounds of the range
     */
    @Override
    public NavigableMap<K, V> subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive) {
        NavigableMap<K,V> nMap = new TreeMap<K,V>();

        for(int i=nodeArrayList.size()-1 ; i >= 0  ; i--){
            if(nodeArrayList.get(i).compareTo(fromKey) == 0 && fromInclusive ){
                nMap.put(nodeArrayList.get(i)._key,nodeArrayList.get(i)._value);
            }
            if(nodeArrayList.get(i).compareTo(toKey) == 0 && toInclusive ){
                nMap.put(nodeArrayList.get(i)._key,nodeArrayList.get(i)._value);
            }

            if(nodeArrayList.get(i).compareTo(fromKey) == -1 && nodeArrayList.get(i).compareTo(toKey) == 1 ){
                nMap.put(nodeArrayList.get(i)._key,nodeArrayList.get(i)._value);
            }
        }
        //for(int i=nMap.size() ; i > 0  ; i--){
        //    System.out.println(nMap.pollFirstEntry().getKey());
        //}


        return nMap;
    }

    /**
     * Returns a view of the portion of this map whose keys are less than (or
     * equal to, if {@code inclusive} is true) {@code toKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     * <p>
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param toKey     high endpoint of the keys in the returned map
     * @param inclusive {@code true} if the high endpoint
     *                  is to be included in the returned view
     * @return a view of the portion of this map whose keys are less than
     * (or equal to, if {@code inclusive} is true) {@code toKey}
     * @throws ClassCastException       if {@code toKey} is not compatible
     *                                  with this map's comparator (or, if the map has no comparator,
     *                                  if {@code toKey} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code toKey} cannot be compared to keys
     *                                  currently in the map.
     * @throws NullPointerException     if {@code toKey} is null
     *                                  and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *                                  restricted range, and {@code toKey} lies outside the
     *                                  bounds of the range
     */
    @Override
    public NavigableMap<K, V> headMap(K toKey, boolean inclusive) {
        return subMap(nodeArrayList.get(0)._key,true,toKey,inclusive);
    }

    /**
     * Returns a view of the portion of this map whose keys are greater than (or
     * equal to, if {@code inclusive} is true) {@code fromKey}.  The returned
     * map is backed by this map, so changes in the returned map are reflected
     * in this map, and vice-versa.  The returned map supports all optional
     * map operations that this map supports.
     * <p>
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param fromKey   low endpoint of the keys in the returned map
     * @param inclusive {@code true} if the low endpoint
     *                  is to be included in the returned view
     * @return a view of the portion of this map whose keys are greater than
     * (or equal to, if {@code inclusive} is true) {@code fromKey}
     * @throws ClassCastException       if {@code fromKey} is not compatible
     *                                  with this map's comparator (or, if the map has no comparator,
     *                                  if {@code fromKey} does not implement {@link Comparable}).
     *                                  Implementations may, but are not required to, throw this
     *                                  exception if {@code fromKey} cannot be compared to keys
     *                                  currently in the map.
     * @throws NullPointerException     if {@code fromKey} is null
     *                                  and this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *                                  restricted range, and {@code fromKey} lies outside the
     *                                  bounds of the range
     */
    @Override
    public NavigableMap<K, V> tailMap(K fromKey, boolean inclusive) {
        return subMap(fromKey,true,nodeArrayList.get(nodeArrayList.size()-1)._key,inclusive);
    }

    /**
     * Returns the comparator used to order the keys in this map, or
     * {@code null} if this map uses the {@linkplain Comparable
     * natural ordering} of its keys.
     *
     * @return the comparator used to order the keys in this map,
     * or {@code null} if this map uses the natural ordering
     * of its keys
     */
    @Override
    public Comparator<? super K> comparator() {
        // <? super K>   K soru isaretinden turemis olucak
        // <? extends K> ? K dan turemis olucak

        return null;
    }


    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return subMap(nodeArrayList.get(0)._key,true,toKey,true);
    }


    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return headMap(toKey,true);
    }


    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return tailMap(fromKey,true);
    }


    @Override
    public K firstKey() {
        return nodeArrayList.get(0)._key;
    }

    /**
     * Returns the last (highest) key currently in this map.
     *
     * @return the last (highest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    @Override
    public K lastKey() {
        return nodeArrayList.get(nodeArrayList.size()-1)._key;
    }

    private static class Node<K,V> implements Map.Entry<K,V>,Comparable<K>{
        K _key;
        V _value;
        public Node<K,V> right;
        public Node<K,V> left;

        Node(K k,V v){
            left = null;
            right = null;
            _key = k;
            _value = v;
        }
        Node(){
            left = null;
            right = null;
        }


        @Override
        public K getKey() {
            return _key;
        }


        @Override
        public V getValue() {
            return _value;
        }


        @Override
        public V setValue(V value) {
            _value = value;
            return value;
        }

        public K setKey(K key){
            _key = key;
            return _key;
        }

        @Override
        public int compareTo(K o) {
            Object obj = o;
            Object objK = _key;
            if(obj.toString().compareTo(objK.toString()) > 0 ){
                return 1;
            }else if(obj.toString().compareTo(objK.toString()) < 0)
                return -1;
            else
                return 0;
        }
    }

}