import java.io.*;

/**
 * Created by KARACA on 4.04.2017.
 */
public class main {
    public static void main(String[] argv) throws IOException {
        BinarySearchTree bst = new BinarySearchTree();

        bst.add(5);
        bst.add(10);
        bst.add(25);
        bst.add(20);
        bst.add(21);
        bst.add(25);
        bst.add(60);
        bst.add(70);
        bst.add(58);
        bst.add(2);

        bst.levelOrder();


        BinaryTree bt = new BinaryTree();
       /* bt.add(5);
        bt.add(10);
        bt.add(25);
        bt.add(20);
        bt.add(21);
        bt.add(25);
        bt.add(60);
        bt.add(70);
        bt.add(58);
        bt.add(2);*/
        //readFromTxt(bt);
       // readFromTxt(bst);
       // bt.preorder();
        //bst.preorderIterator();

    }



    public static void readFromTxt(BinaryTree bTree) throws IOException {
        FileReader fr = new FileReader(new File("test.txt"));
        BufferedReader br = new BufferedReader(fr);

        String line;
        String[] lineParts;
        while( (line = br.readLine()) != null){
            lineParts = line.split(" ");
            for(int i=0;i< lineParts.length;i++){
                bTree.add(lineParts[i]);
            }
        }
        br.close();
    }

}
