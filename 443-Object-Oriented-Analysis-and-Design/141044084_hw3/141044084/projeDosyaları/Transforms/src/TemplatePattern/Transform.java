package TemplatePattern;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Transform {
    protected ArrayList<Double> items = new ArrayList<Double>();
    protected double item;

    private boolean option = false;

    protected ArrayList<Double> first = new ArrayList<Double>();
    protected ArrayList<Double> second = new ArrayList<Double>();

    /**
     * Template method design pattern
     * yapılacak islerin sirasini belirliyor
     * @throws IOException
     */
    final public void translate() throws IOException {
        readFromFile();
        exec();
        writeToFile();
        hook();
    }

    /**
     * kullanicinin istedigi ek ozellik kontrolu
     * @param _option
     */
    public void setOption(boolean _option){
        option = _option;
    }

    public boolean getOption(){
        return option;
    }

    /**
     * transformlar icin dosyadan okuma methodu (ortak)
     */
    private final void readFromFile(){
        Scanner scan;
        File file = new File("src/data.txt");
        try {
            scan = new Scanner(file);
            while(scan.hasNext())
            {
                items.add(Double.parseDouble(scan.next()));
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * alt sınıflarda doldurulacak donusum methodu
     */
    protected abstract void exec();

    /**
     * transformlar icin dosyaya yazma methodu (ortak)
     * @throws IOException
     */
    private final void writeToFile() throws IOException {
        for(int i = 0 ; i < items.size();i++)
            System.out.println(first.get(i)+" "+second.get(i));

        FileWriter fileWriter = new FileWriter("src/sonuclar.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for (int i = 0 ; i < items.size();i++)
            printWriter.println(first.get(i)+" "+second.get(i));
        printWriter.close();
    }

    /**
     * duruma bağlı ek yapıcak işleri belirtiyor
     */
    protected abstract void hook();
}
