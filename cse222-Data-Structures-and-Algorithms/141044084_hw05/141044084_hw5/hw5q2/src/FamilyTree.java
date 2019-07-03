import java.util.Iterator;
import java.util.Stack;

/**
 * Created by KARACA on 4.04.2017.
 */
public class FamilyTree extends BinaryTree<String> implements Iterable<Object> {

    FamilyTree(String data){
        root = (Node) new Node<String>(data);
    }

    public String add(String name,String parentName,String nickname){
        try {
            Node _root = root;
            Node parent;
            String relation = chooseRelation(0,nickname);
            if(relation.equals("true")){
                if(chooseRelation(1,nickname).toString().equals(name)){
                    parent = find(_root,parentName);
                    Node node = new Node(name);
                    addLeftHelper(parent,node); // buraya bak !!!
                }else{
                    parent = find(_root,name,parentName,chooseRelation(1,nickname));
                    if(parent == null){
                        return null;
                    }
                //    System.out.println(parent.data);

                    Node node = new Node(name);
                    addRightHelper(parent,node);
                }
            }else if(relation.equals("false")){
                parent = findIbn(_root,name,parentName,chooseRelation(1,nickname));
                if(parent == null){
                    return null;
                }

                Node node = new Node(name);
                if(parent.left == null)
                    addLeftHelper(parent,node);
                else
                    addRightHelper(parent.left,node);
            }
            return null;
        }catch (Exception e){
            throw new NullPointerException();
        }
    }
    /* ebu - ebeveyn true */

    public void preOrder(){
        StringBuilder sb = new StringBuilder();
        preOrderTraverse(root,1,sb);
        System.out.println(sb);
    }

    public void preOrderIter(){
        Iterator iter = iterator();
        while(iter.hasNext() == true) {
            System.out.println(iter.next());
        }
    }

    @Override
    protected void preOrderTraverse(Node node, int depth, StringBuilder sb){
        if(node != null)
            for (int i = 1; i < depth; i++) {
                sb.append("  ");
            }
        if (node != null) {
            sb.append(node.toString());
            sb.append("\n");
            preOrderTraverse(node.left, depth + 1, sb);
            preOrderTraverse(node.right, depth , sb);
        }
    }

    private Node searchR(Node _node, String _target){
        if(_node == null){
            return null;
        }
        if(_node.data.toString().equals(_target.toString())){
            return _node;
        }else {
            return searchR(_node.right,_target);
        }
    }

    private Node findIbn(Node _root, String _name, String _parentName, String nickname){
        if(_root == null)
            return null;

        Node parent = find(_root,nickname);

        Node result = searchR(parent.left,_parentName);
        if(result == null){
            Node fr = find(parent.left, _name,_parentName,nickname);
            if(fr != null)
                return fr;

            fr = find(parent.right, _name,_parentName,nickname);

            if(fr != null)
                return fr;
        }else{
            return result;
        }
        return null;
    }

    private Node find(Node _root, String _name, String _parentName, String nickname){
        if(_root == null)
            return null;

        Node parent = find(_root,_parentName);
        Node result = searchR(parent.left,nickname);

        if(result == null){
            Node fr = find(parent.left, _name,_parentName,nickname);
            if(fr != null)
                return fr;

            fr = find(parent.right, _name,_parentName,nickname);

            if(fr != null)
                return fr;
        }else{
            return result;
        }
        return null;
    }

    private Node find(Node localRoot, String target) {
        if (localRoot == null) {
            return null;
        }

        if( target.toString().equals(localRoot.toString())){
            return localRoot;
        }
        Node temp = find(localRoot.left,target);
        if(temp != null)
            return temp;
        temp = find(localRoot.right,target);
        if(temp != null)
            return temp;
        return null;
    }

    private String addRightHelper(Node _root, Node _node){
        if(_root.right == null) {

            _root.right = _node;
            return (String) _node.data;
        }else {
            return addRightHelper(_root.right,_node);
        }
    }

    private String addLeftHelper(Node _root, Node _node){
        if(_root.left == null) {
            _root.left = _node;
            return (String) _node.data;
        }else if(_root.left != null){
            return addLeftHelper(_root.left,_node);
        }
        return null;
    }

    /* ebeveyn true */
    private String chooseRelation(int type,String data){
        String[] stringParts;
        stringParts = data.split("-");
        if(type == 0){
            if(stringParts[0].toString().equals("ebu"))
                return "true";
            return "false";
        }else{
            return stringParts[1];
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Object> iterator() {
        return new familyIterator(root);
    }

    private class familyIterator implements Iterator{
        Node rootIter;
        Stack<Node<String>> stack = new Stack<Node<String>>();
        familyIterator(Node _root){
            rootIter = _root;
            if(stack.isEmpty()){
                stack.push(rootIter);
            }
        }

        @Override
        public boolean hasNext() {
            return rootIter != null;

        }

        @Override
        public String next() {

            Node T = stack.pop();

            if(T.right != null) {
                stack.push(T.right);
            }

            if(T.left != null) {
                stack.push(T.left);
            }
            if(stack.isEmpty()) {
                rootIter = null;
            }
            return (String) T.data;
        }
    }
}
