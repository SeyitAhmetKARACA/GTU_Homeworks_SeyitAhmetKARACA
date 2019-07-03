package gokturkthreesatalite;

import java.util.ArrayList;
import java.util.Iterator;

public class SpirallyClockwise implements Gokturk {
    private ArrayList<Integer> arr;
    private Iterator<Integer> iter = null;
    /**
     * Iterator Has next
     * @return
     */
    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }
    /**
     * Iterator next
     * @return
     */
    @Override
    public Object next() {
        return iter.next();
    }
    /**
     * 2 boyutlu int arrayi alıyor ve saatin ters yönünde geziyor
     * ve iterator değişkenini dolduruyor.
     * @param data 2D int array
     */
    @Override
    public void Spiral(int[][] data) {
        arr = new ArrayList<>();
        int m = data.length;
        int n = data[0].length;
        int x = 0;
        int y = 0;
        while (m > 0 && n > 0){
            if(m == 1){
                for (int i = 0; i < n; i++)
                    arr.add(data[y][x++]);
                break;
            }
            else if(n == 1){
                for (int i = 0; i < m; i++)
                    arr.add(data[y++][x]);
                break;
            }
            for (int i = 0; i < n-1; i++)
                arr.add(data[y][x++]);
            for (int i = 0; i < m-1; i++)
                arr.add(data[y++][x]);
            for (int i = 0; i < n-1; i++)
                arr.add(data[y][x--]);
            for (int i = 0; i < m-1; i++)
                arr.add(data[y--][x]);
            x++;
            y++;
            m = m-2;
            n = n-2;
        }
        iter = arr.iterator();
    }
}