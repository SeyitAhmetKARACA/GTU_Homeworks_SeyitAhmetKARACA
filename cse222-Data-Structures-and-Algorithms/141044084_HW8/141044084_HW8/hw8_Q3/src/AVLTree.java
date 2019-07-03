

public class AVLTree <E extends Comparable < E >>
    extends BinarySearchTreeWithRotate < E > {

  /** increase data field */
  private boolean increase;

  /** decrease data field */
  private boolean decrease;

  /** Class to represent an AVL Node. It extends the
      BinaryTree.Node by adding the balance field. */
  private static class AVLNode < E > extends Node < E > {
    /** Constant to indicate left-heavy */
    public static final int LEFT_HEAVY = -1;

    /** Constant to indicate balanced */
    public static final int BALANCED = 0;

    /** Constant to indicate right-heavy */
    public static final int RIGHT_HEAVY = 1;

    /** balance is right subtree height � left subtree height */
    private int balance;

    // Methods
    /** Construct a node with the given item as the data field.
        @param item The data field
     */
    public AVLNode(E item) {
      super(item);
      balance = BALANCED;
    }

    /** Return a string representation of this object.
        The balance value is appended to the contents.
        @return String representation of this object
     */
    public String toString() {
      return balance + ": " + super.toString();
    }
  }

  /** Ekleme methodu.
      @param item : Eklenecek eleman
      @return Eleman eklendi ise true ;
              Eleman zaten agacta var ise false
      @throws ClassCastException Eleman comparable degil ise firlatilacak
   */
  public boolean add(E item) {
    increase = false;
    root = add( (AVLNode < E > ) root, item);
    return addReturn;
  }

  /** Yardimci add method
      @param localRoot alt agaclarin rootlari
      @param item eklenecek eleman
      @return Yeni locakroot
   */
  private AVLNode < E > add(AVLNode < E > localRoot, E item) {
    if (localRoot == null) {
      addReturn = true;
      increase = true;
      return new AVLNode < E > (item);
    }
    /* Eleman zaten agacta ekli ise */
    if (item.compareTo(localRoot.data) == 0) {
      increase = false;
      addReturn = false;
      return localRoot;
    }
    /* Data > eklenecek eleman */
    else if (item.compareTo(localRoot.data) < 0) {
      localRoot.left = add( (AVLNode < E > ) localRoot.left, item);

      if (increase) {
        decrementBalance(localRoot);
        if (localRoot.balance < AVLNode.LEFT_HEAVY) {
          increase = false;
          return rebalanceLeft(localRoot);
        }
      }
      return localRoot; // Rebalance not needed.
    }
    else {  /* Data < eklenecek eleman*/
      localRoot.right = add( (AVLNode < E > ) localRoot.right, item);
      if (increase) {
        incrementBalance(localRoot);
        if (localRoot.balance > AVLNode.RIGHT_HEAVY) {
          return rebalanceRight(localRoot);
        }
        else {
          return localRoot;
        }
      }
      else {
        return localRoot;
      }
    }

  }














  /**   public silme methodu
        @param element Silinecek eleman
        @return silinen eleman
   */
  public E delete(E element) {
    decrease = false;
    root = delete((AVLNode) root,element);
    return element;
  }

  /** Delete yardimci methodu
      @param deleteRoot root olacak eleman
      @param element silinicek eleman
      @return
   */
  private AVLNode delete(AVLNode deleteRoot, E element) {
    if (deleteRoot == null) { // item is not in tree
      return deleteRoot;
    }
    /* silenecek eleman bulundu. */
    if (element.compareTo((E)deleteRoot.data) == 0) {
      /* balance yapiyor*/
      if (deleteRoot.left == null) {
        decrease = true;
        return (AVLNode) deleteRoot.right;
      }else if (deleteRoot.right == null) {
        decrease = true;
        return (AVLNode) deleteRoot.left;

      }else {
        if(deleteRoot.left.right == null) {
          deleteRoot.data = deleteRoot.left.data;
          deleteRoot.left = deleteRoot.left.left;
          incrementBalance(deleteRoot);
          return deleteRoot;
        }else {
          deleteRoot.data = highestNode( (AVLNode) deleteRoot.left);
          if(((AVLNode)deleteRoot.left).balance < AVLNode.LEFT_HEAVY)
            deleteRoot.left = rebalanceLeft((AVLNode) deleteRoot.left);
          if (decrease == true)
            incrementBalance(deleteRoot);
          return deleteRoot;
        }
      }
    } /* deleteRoot un datasi > element */
    else if (element.compareTo((E)deleteRoot.data) < 0) {
      deleteRoot.left = delete((AVLNode) deleteRoot.left, element);
      if (decrease == true) {
        incrementBalance(deleteRoot);
        if (deleteRoot.balance > AVLNode.RIGHT_HEAVY)
          return rebalanceRL( (AVLNode) deleteRoot);
        else
          return deleteRoot;
      }else
        return deleteRoot;
    }else { /* deleteRoot un datasi < element*/
      deleteRoot.right = delete((AVLNode) deleteRoot.right, element);
      if (decrease == true) {
        decrementBalance(deleteRoot);
        if (AVLNode.LEFT_HEAVY > deleteRoot.balance)
          return rebalanceLR(deleteRoot);
        else
          return deleteRoot;
      }else
        return deleteRoot;
    }
  }

  /**
   * En buyuk elemani bulan method
   * @param aNode root olarak kullanilacak eleman
   * @return
   */
  private E highestNode(AVLNode aNode) {
    E rValue;
    if (aNode.right.right == null) {
      rValue = (E)aNode.right.data;
      aNode.right = aNode.right.left;
      decrementBalance(aNode);
      return rValue;
    }else {
      rValue = highestNode( (AVLNode) aNode.right);
      if(((AVLNode) aNode.right).balance < AVLNode.LEFT_HEAVY)
        aNode.right = rebalanceLeft((AVLNode) aNode.right);
      if (decrease)
        decrementBalance(aNode);
      return rValue;
    }
  }

  private void incrementBalance(AVLNode < E > node) {
    node.balance++;
    if (node.balance > AVLNode.BALANCED) {
      increase = true;
      decrease = false;
    }else {
      increase = false;
      decrease = true;
    }
  }

  private AVLNode < E > rebalanceRight(AVLNode < E > localRoot) {
    AVLNode < E > rightChild = (AVLNode < E > ) localRoot.right;
    if (rightChild.balance < AVLNode.BALANCED) {
      AVLNode < E > rightLeftChild = (AVLNode < E > ) rightChild.left;
      if (rightLeftChild.balance > AVLNode.BALANCED) {
        rightChild.balance = AVLNode.BALANCED;
        rightLeftChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.LEFT_HEAVY;
      }
      else if (rightLeftChild.balance < AVLNode.BALANCED) {
        rightChild.balance = AVLNode.RIGHT_HEAVY;
        rightLeftChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.BALANCED;
      }else {
        rightChild.balance = AVLNode.BALANCED;
        rightLeftChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.BALANCED;
      }
      increase = false;
      decrease = true;
      localRoot.right = rotateRight(rightChild);
      return (AVLNode < E > ) rotateLeft(localRoot);
    }else {
      rightChild.balance = AVLNode.BALANCED;
      localRoot.balance = AVLNode.BALANCED;
      increase = false;
      decrease = true;
      return (AVLNode < E > ) rotateLeft(localRoot);
    }
  }

  /**
   * left-right case icin rebalance method
   * @param lRoot
   * @return
   */
  private AVLNode < E > rebalanceLR(AVLNode < E > lRoot) {
    AVLNode leftNode = (AVLNode < E > ) lRoot.left;
    if (leftNode.balance > AVLNode.BALANCED) {
      AVLNode LRChild = (AVLNode) leftNode.right;
      if (LRChild.balance < AVLNode.BALANCED) {
        leftNode.balance = AVLNode.LEFT_HEAVY;
        LRChild.balance = AVLNode.BALANCED;
        lRoot.balance = AVLNode.BALANCED;

      }else if (LRChild.balance > AVLNode.BALANCED) {
        leftNode.balance = AVLNode.BALANCED;
        LRChild.balance = AVLNode.BALANCED;
        lRoot.balance = AVLNode.RIGHT_HEAVY;
      }else {

        leftNode.balance = AVLNode.BALANCED;
        lRoot.balance = AVLNode.BALANCED;
      }
      increase = false;
      decrease = true;
      lRoot.left = rotateLeft(leftNode);
      return (AVLNode < E > ) rotateRight(lRoot);
    }
    if (leftNode.balance < AVLNode.BALANCED) {
      leftNode.balance = AVLNode.BALANCED;
      lRoot.balance = AVLNode.BALANCED;
      increase = false;
      decrease = true;
    }else {
      leftNode.balance = AVLNode.RIGHT_HEAVY;
      lRoot.balance = AVLNode.LEFT_HEAVY;
    }
    return (AVLNode) rotateRight(lRoot);
  }

  /**
   * Right-Left case icin rebalance method
   * @param lRoot
   * @return
   */
  private AVLNode < E > rebalanceRL(AVLNode < E > lRoot) {
    AVLNode < E > rightNode = (AVLNode < E > ) lRoot.right;
    if (rightNode.balance < AVLNode.BALANCED) {
      AVLNode  RLChild = (AVLNode) rightNode.left;
      if (RLChild.balance > AVLNode.BALANCED) {
        rightNode.balance = AVLNode.RIGHT_HEAVY;
        RLChild.balance = AVLNode.BALANCED;
        lRoot.balance = AVLNode.BALANCED;
      }
      else if (RLChild.balance < AVLNode.BALANCED) {
        rightNode.balance = AVLNode.BALANCED;
        RLChild.balance = AVLNode.BALANCED;
        lRoot.balance = AVLNode.LEFT_HEAVY;
      }
      else {
        rightNode.balance = AVLNode.BALANCED;
        lRoot.balance = AVLNode.BALANCED;
      }
      increase = false;
      decrease = true;
      lRoot.right = rotateRight(rightNode);
      return (AVLNode < E > ) rotateLeft(lRoot);
    }
    if (rightNode.balance > AVLNode.BALANCED) {
      rightNode.balance = AVLNode.BALANCED;
      lRoot.balance = AVLNode.BALANCED;
      increase = false;
      decrease = true;
    }else {
      rightNode.balance = AVLNode.LEFT_HEAVY;
      lRoot.balance = AVLNode.RIGHT_HEAVY;
    }
    return (AVLNode < E > ) rotateLeft(lRoot);
  }

  /**
   * Left balance
   * @param localRoot
   * @return
   */
  private AVLNode rebalanceLeft(AVLNode localRoot) {
    AVLNode leftChild = (AVLNode) localRoot.left;
    if (leftChild.balance > AVLNode.BALANCED) {
      AVLNode leftRightChild = (AVLNode) leftChild.right;
      if (leftRightChild.balance < AVLNode.BALANCED) {
        leftChild.balance = AVLNode.BALANCED;
        leftRightChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.RIGHT_HEAVY;
      }
      else {
        leftChild.balance = AVLNode.LEFT_HEAVY;
        leftRightChild.balance = AVLNode.BALANCED;
        localRoot.balance = AVLNode.BALANCED;
      }
      localRoot.left = rotateLeft(leftChild);
    }
    else { //Left-Left case
      leftChild.balance = AVLNode.BALANCED;
      localRoot.balance = AVLNode.BALANCED;
    }
    return (AVLNode) rotateRight(localRoot);
  }

  private void decrementBalance(AVLNode < E > node){
    node.balance--;
    if (node.balance == AVLNode.BALANCED)
      increase = false;
  }
}