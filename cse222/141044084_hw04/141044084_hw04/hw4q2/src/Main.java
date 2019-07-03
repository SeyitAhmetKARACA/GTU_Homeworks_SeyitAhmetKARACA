import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
    /**
     * test.csv 'deki elemanlari myQueue ya okur
     * Okuma basarili ise true degil ise false
     * @param _myqueue
     * @return
     * @throws IOException
     */
    public static boolean fileReader(myQueue _myqueue) throws IOException {
        myQueue temp;
        FileReader fileReader = new FileReader(new File("test.csv"));
        BufferedReader bufferedReader= new BufferedReader(fileReader);
        String line;
        String lineParts[];
        while((line = bufferedReader.readLine()) != null){
            temp = new myQueue();
            lineParts =  line.split(",");
            for(int i = 0 ; i < lineParts.length;i++){
                temp.add(lineParts[i]);
            }
            _myqueue.add(temp);
        }

        fileReader.close();
        bufferedReader.close();
        if(_myqueue.size() > 0)
            return true;
        else
            return false;

    }

    /**
     * test.csv deki elemanlari Queue'a okur
     * basarili ise true basarisiz ise false
     * @param _que
     * @return
     * @throws IOException
     */
    public static boolean fileReader(Queue _que) throws IOException {
        Queue temp;
        FileReader fileReader = new FileReader(new File("test.csv"));
        BufferedReader bufferedReader= new BufferedReader(fileReader);
        String line;
        String lineParts[];
        while((line = bufferedReader.readLine()) != null){
            temp = new LinkedList();
            lineParts =  line.split(",");
            for(int i = 0 ; i < lineParts.length;i++){
                temp.add(lineParts[i]);
            }
            _que.add(temp);
        }

        fileReader.close();
        bufferedReader.close();
        if(_que.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * Parametre olarak gelen StringBuilderi dosyaya ekler.
     *
     * @param sb
     * @throws IOException
     */
    public static void fileWriter(StringBuilder sb) throws IOException {
        FileWriter fw = new FileWriter(new File("testResult_2.csv"),true);
        BufferedWriter bw = new BufferedWriter(fw);

        bw.write(sb.toString());

        bw.close();
    }

    public static void main(String[] args) throws IOException {
        /*System.out.println("Hello World!");*/
        myQueue myku = new myQueue();
        myQueue myqueue = new myQueue();
        myQueue tempMyQueue = new myQueue();
        Queue ku = new LinkedList();
        Queue ku2;
        int size,size2;
        StringBuilder sb = new StringBuilder();
        StringBuilder sbMyqueue = new StringBuilder();
        Long startTime,stopTime,result;

        try {/* Recursion queue reverse */

            if (fileReader(ku)) {

                myku.reverseQueue(ku);
                size = ku.size();
                for (int i = 0; i < size; i++) {
                    ku2 = (Queue) ku.remove();
                    myku.reverseQueue(ku2);
                    size2 = ku2.size();
                    for (int j = 0; j < size2; j++) {
                        sb.append(ku2.remove());
                        if (j != size2 - 1)
                            sb.append(",");
                    }
                    sb.append("\n");
                }
            }
            fileWriter(sb);
            {/* myQueue reverse */
            System.out.println("\n\n");

                if (fileReader(myqueue)) {


                    myqueue.reverse();
                    for (int i = 0; i < myqueue.size(); i++) {
                        tempMyQueue = (myQueue) myqueue.get(i);
                        tempMyQueue.reverse();
                        size2 = tempMyQueue.size();
                        for (int j = 0; j < size2; j++) {
                            sbMyqueue.append(tempMyQueue.remove());
                            if (j != size2 - 1)
                                sbMyqueue.append(",");
                        }
                        sbMyqueue.append("\n");
                    }

                }
                fileWriter(sbMyqueue);
            }
        }catch (EOFException e){
            System.err.println(e.getMessage());
        }

    }
}
