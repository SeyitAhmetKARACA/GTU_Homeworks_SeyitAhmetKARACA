public class Main {

    public static void main(String[] args) {
        AVLTree<String> avl = new AVLTree<String>();
        avl.add("Nush");
        avl.add("ile");
        avl.add("uslanmayani");
        avl.add("etmeli");
        avl.add("tekdir");
        avl.add("tekdir");
        avl.add("ile");
        avl.add("uslanmayanin");
        avl.add("hakki");
        avl.add("kotektir");
        avl.add("edille");
        avl.add("dakik");
        avl.add("ferc");

        System.out.println(avl.toString());

        avl.remove("Nush");
        avl.remove("ile");
        avl.remove("uslanmayani");
        System.out.println(avl.toString());
    }
}
