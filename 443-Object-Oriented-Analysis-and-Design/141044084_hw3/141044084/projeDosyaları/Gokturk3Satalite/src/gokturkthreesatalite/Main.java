package gokturkthreesatalite;

public class Main {
    public static void main(String[] args) {
        Gokturk gokturk;
        int dataSize = 5;
        int counter = 1;
        int[][] data = new int[dataSize][dataSize];
        for (int i = 0; i < dataSize; i++)
            for (int j = 0; j < dataSize; j++)
                data[i][j] = counter++;

        System.out.println("Data : ");
        for (int i = 0; i < dataSize; i++) {
            for (int j = 0; j < dataSize; j++) {
                System.out.print(data[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("Spirally anti clockwise");
        printSpiral( new SpirallyAntiClockwise(),data);
        System.out.println("Spirally clockwise");
        printSpiral(new SpirallyClockwise(),data);
    }

    /**
     * Gokturk tipinde bir degisken ve veri setini alip ekrana yaziyor
     * @param gokturk gokturk arayuzunu implement etmis bir nesne
     * @param data 2d int array
     */
    public static void printSpiral(Gokturk gokturk ,int[][] data ){
        gokturk.Spiral(data);
        while (gokturk.hasNext()){
            System.out.print(gokturk.next() + " ");
        }
        System.out.println("");
    }
}
