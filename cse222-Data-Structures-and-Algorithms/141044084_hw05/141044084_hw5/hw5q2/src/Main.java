import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by KARACA on 4.04.2017.
 */
public class Main {
    public  static void main(String argv[]) throws IOException {
        try {


        FamilyTree family = null;
        FileReader fr = new FileReader(new File("test.txt"));
        BufferedReader br = new BufferedReader(fr);
        String line;
        String[] lineParts;
        if((line = br.readLine()) != null ){
            lineParts = line.split(",");
            if(lineParts.length != 1)
                throw new IOException();
            family = new FamilyTree(lineParts[0].trim().toLowerCase());
        }

        while((line = br.readLine()) != null ){
            lineParts = line.split(",");
            family.add(lineParts[0].trim().toLowerCase(),lineParts[1].trim().toLowerCase(),lineParts[2].trim().toLowerCase());
        }

        br.close();
        family.preOrderIter();

        System.out.println("");

        family.preOrder();
        }catch (Exception e){
            System.out.println("Ağaca eleman ekleme işleminde hata oluştu.");
        }
    }
}
