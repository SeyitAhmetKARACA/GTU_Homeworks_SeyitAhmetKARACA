import java.io.*;
import java.util.Iterator;

/**
 * Created by KARACA on 13.03.2017.
 */

public class main {
    /**
     * toStringIdex ile dosyaya yazar
     * @param msBuilder
     * @throws IOException
     */
    public static void printToFileToStringIndex(myStringBuilder msBuilder) throws IOException {
        long starttime,stopTime,result;
        FileWriter fw = new FileWriter(new File("result1.txt"));
        PrintWriter pw = new PrintWriter(fw);

        starttime = System.currentTimeMillis();
        pw.println(msBuilder.toStringIndex());

        stopTime = System.currentTimeMillis();
        result = stopTime-starttime;
        result /= 1000;
        System.out.println("toStringIndex : "+result);
        pw.close();
        fw.close();
    }

    /**
     * toStringLinked ile dosyaya yazar
     * @param msBuilder
     * @throws IOException
     */
    public static void printToFileToStringLinked(myStringBuilder msBuilder) throws IOException {
        long starttime,stopTime,result;
        FileWriter fw = new FileWriter(new File("result2.txt"));
        PrintWriter pw = new PrintWriter(fw);

        starttime = System.currentTimeMillis();
        pw.println(msBuilder.toStringLinked());

        stopTime = System.currentTimeMillis();
        result = stopTime-starttime;
        result /= 1000;

        System.out.println("toStringLinked : "+result);
        pw.close();
        fw.close();
    }

    /**
     * toStringIter ile dosyaya yazar
     * @param msBuilder
     * @throws IOException
     */
    public static void printToFileToStringIter(myStringBuilder msBuilder) throws IOException {
        long starttime,stopTime,result;
        FileWriter fw = new FileWriter(new File("result3.txt"));
        PrintWriter pw = new PrintWriter(fw);

        starttime = System.currentTimeMillis();
        pw.println(msBuilder.toStringIter());

        stopTime = System.currentTimeMillis();
        result = stopTime-starttime;
        result /= 1000;
        System.out.println("toStringIter : "+result);

        pw.close();
        fw.close();

    }
    public static void main(String[] args) throws IOException {
        myStringBuilder ms = new myStringBuilder();
        FileReader fr = new FileReader(new File("int.txt"));
        BufferedReader br = new BufferedReader(fr);
        String str;
        while ((str = br.readLine()) != null) {
            ms.append(str);
        }
        br.close();
        fr.close();

        printToFileToStringIndex(ms);
        printToFileToStringIter(ms);
        printToFileToStringLinked(ms);

    }

}
