import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by KARACA on 22.03.2017.
 */
public class main {

    /**
     * test.csv yi stackD ye okur
     * @param alsd
     * @throws IOException
     */
    public static void readToStackD(ArrayList<StackD> alsd) throws IOException {
        FileReader fr = new FileReader(new File("test.csv"));
        BufferedReader br = new BufferedReader(fr);
        StackD sd;
        String line;
        String linePart[];
        while((line = br.readLine()) != null ){
            sd = new StackD();
            linePart = line.split(",");
            for(int i = 0 ; i < linePart.length;i++){
                sd.push(linePart[i]);
            }
            alsd.add(sd);
        }

        br.close();
        fr.close();
    }


    /**
     * test.csv yi stackA ya okur
     * @param alsd
     * @throws IOException
     */
    public static void readToStackA(ArrayList<StackA> alsd) throws IOException {
        FileReader fr = new FileReader(new File("test.csv"));
        BufferedReader br = new BufferedReader(fr);
        StackA sd;
        String line;
        String linePart[];
        while((line = br.readLine()) != null ){
            sd = new StackA();
            linePart = line.split(",");
            for(int i = 0 ; i < linePart.length;i++){
                sd.push(linePart[i]);
            }
            alsd.add(sd);
        }

        br.close();
        fr.close();
    }


    /**
     * test.csv yi stackC ye okur
     * @param alsd
     * @throws IOException
     */
    public static void readToStackC(ArrayList<StackC> alsd) throws IOException {
        FileReader fr = new FileReader(new File("test.csv"));
        BufferedReader br = new BufferedReader(fr);
        StackC sd;
        String line;
        String linePart[];
        while((line = br.readLine()) != null ){
            sd = new StackC();
            linePart = line.split(",");
            for(int i = 0 ; i < linePart.length;i++){
                sd.push(linePart[i]);
            }
            alsd.add(sd);
        }
        br.close();
        fr.close();
    }

    /**
     * Parametre olarak alinan StringBuilder i testResult_1.csv ye yazar.
     * @param data
     * @throws IOException
     */
    public static void writeToFile(StringBuilder data) throws IOException {
        FileWriter fw = new FileWriter(new File("testResult_1.csv"));
        BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data.toString());
        bw.close();
    }

    /**
     * test.csv yi stackB ye okur
     * @param alsd
     * @throws IOException
     */
    public static void readToStackB(ArrayList<StackB> alsd) throws IOException {
        FileReader fr = new FileReader(new File("test.csv"));
        BufferedReader br = new BufferedReader(fr);
        StackB sd;
        String line;
        String linePart[];
        while((line = br.readLine()) != null ){
            sd = new StackB();
            linePart = line.split(",");
            for(int i = 0 ; i < linePart.length;i++){
                sd.push(linePart[i]);
            }
            alsd.add(sd);
        }
        br.close();
        fr.close();
    }

    public static void main(String [ ] args) throws IOException {
        ArrayList<StackD> sdal = new ArrayList<StackD>();
        ArrayList<StackC> scal = new ArrayList<StackC>();
        ArrayList<StackB> sbal = new ArrayList<StackB>();
        ArrayList<StackA> saal = new ArrayList<StackA>();
        Long startTime,stopTime,result;

        /*  methodlarin push lari burada kullanildi */
        startTime = System.nanoTime();
            readToStackD(sdal);
        stopTime = System.nanoTime();
        result = stopTime-startTime;
        System.out.println("StackD okuma sure :"+result+" nano saniye");

        startTime = System.nanoTime();
            readToStackC(scal);
        stopTime = System.nanoTime();
        result = stopTime-startTime;
        System.out.println("StackC okuma sure :"+result+" nano saniye");

        startTime = System.nanoTime();
            readToStackB(sbal);
        stopTime = System.nanoTime();
        result = stopTime-startTime;
        System.out.println("StackB okuma sure :"+result+" nano saniye");

        startTime = System.nanoTime();
            readToStackA(saal);
        stopTime = System.nanoTime();
        result = stopTime-startTime;
        System.out.println("StackA okuma sure :"+result+" nano saniye");

        System.out.print("\n\n");
        /*  --------------------------------------   */
        /*  Dosyaya yazmak icin olan komutlar        */
        /*  --------------------------------------   */
        int sizeStack;
        StringBuilder sb = new StringBuilder();
        try {
            /*   Stacklerin get-pop methodlari       */
            startTime = System.nanoTime();

            for (int i = 0; i < saal.size(); i++) {
                sb.append(saal.get(i).size() + ",");
                sizeStack = saal.get(i).size();

                for (int j = 0; j < sizeStack; j++) {
                    sb.append(saal.get(i).pop());
                    if (j != sizeStack - 1)
                        sb.append(",");
                }
                sb.append("\n");
            }

            stopTime = System.nanoTime();
            result = stopTime-startTime;
            System.out.println("StackA pop-get sure :"+result+" nano saniye");
            /*---------------------------------------------*/
            startTime = System.nanoTime();

            for (int i = 0; i < sbal.size(); i++) {
                sb.append(sbal.get(i).size() + ",");
                sizeStack = sbal.get(i).size();

                for (int j = 0; j < sizeStack; j++) {
                    sb.append(sbal.get(i).pop());
                    if (j != sizeStack - 1)
                        sb.append(",");
                }
                sb.append("\n");
            }
            stopTime = System.nanoTime();
            result = stopTime-startTime;
            System.out.println("StackB pop-get sure :"+result+" nano saniye");
            /*---------------------------------------------*/
            startTime = System.nanoTime();

            for (int i = 0; i < scal.size(); i++) {
                sb.append(scal.get(i).size() + ",");
                sizeStack = scal.get(i).size();

                for (int j = 0; j < sizeStack; j++) {
                    sb.append(scal.get(i).pop());
                    if (j != sizeStack - 1)
                        sb.append(",");
                }
                sb.append("\n");
            }
            stopTime = System.nanoTime();
            result = stopTime-startTime;
            System.out.println("StackC pop-get sure :"+result+" nano saniye");
            /*---------------------------------------------*/
            startTime = System.nanoTime();

            for (int i = 0; i < sdal.size(); i++) {
                sb.append(sdal.get(i).size() + ",");
                sizeStack = sdal.get(i).size();

                for (int j = 0; j < sizeStack; j++) {
                    sb.append(sdal.get(i).pop());
                    if (j != sizeStack - 1)
                        sb.append(",");
                }
                sb.append("\n");
            }

            stopTime = System.nanoTime();
            result = stopTime-startTime;
            System.out.println("StackD pop-get sure :"+result+" nano saniye");

        /* dosyaya yazdigi komut */
            writeToFile(sb);
        }catch (EOFException e){
            System.err.println(e.getMessage());
        }

    }
}