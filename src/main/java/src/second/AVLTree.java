package src.second;

import lombok.Data;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
@Data
class AVLTree<K extends Comparable<K>, V extends Comparable<V>> {
    private Node<K, V> rootNode;

    //Constructor to set null value to the rootNode
    public AVLTree() {
        rootNode = null;
    }

    //create removeAll() method to make AVL Tree empty
    public void removeAll() {
        rootNode = null;
    }

    // create checkEmpty() method to check whether the AVL Tree is empty or not
    public boolean checkEmpty() {
        return rootNode == null;
    }

    // create insertElement() to insert element  to the AVL Tree
    public void insertElement(K key, V element) {
        rootNode = insertElement(key, element, rootNode);
    }

    //create getHeight() method to get the height of the AVL Tree
    private int getHeight(Node<K, V> node) {
        return node == null ? -1 : node.getH();
    }

    //create maxNode() method to get the maximum height from left and right node
    private int getMaxHeight(int leftNodeHeight, int rightNodeHeight) {
        return Math.max(leftNodeHeight, rightNodeHeight);
    }


    //create insertElement() method to insert data in the AVL Tree recursively
    private Node<K, V> insertElement(K key, V element, Node<K, V> node) {
        //check whether the node is null or not
        if (node == null)
            node = new Node<>(key, element);
            //insert a node in case when the given element is lesser than the element of the root node
        else if (element.compareTo(node.getElement()) < 0) {
            node.leftChild = insertElement(key, element, node.leftChild);
            if (getHeight(node.leftChild) - getHeight(node.rightChild) == 2)
                if (element.compareTo(node.leftChild.getElement()) < 0)
                    node = rotateWithLeftChild(node);
                else
                    node = doubleWithLeftChild(node);
        } else if (element.compareTo(node.getElement()) > 0) {
            node.rightChild = insertElement(key, element, node.rightChild);
            if (getHeight(node.rightChild) - getHeight(node.leftChild) == 2)
                if (element.compareTo(node.rightChild.getElement()) > 0)
                    node = rotateWithRightChild(node);
                else
                    node = doubleWithRightChild(node);
        }

        node.setH(getMaxHeight(getHeight(node.leftChild), getHeight(node.rightChild)) + 1);
        return node;

    }

    // creating rotateWithLeftChild() method to perform rotation of binary tree node with left child
    private Node<K, V> rotateWithLeftChild(Node<K, V> node2) {
        Node<K, V> node1 = node2.leftChild;
        node2.leftChild = node1.rightChild;
        node1.rightChild = node2;
        node2.setH(getMaxHeight(getHeight(node2.leftChild), getHeight(node2.rightChild)) + 1);
        node1.setH(getMaxHeight(getHeight(node1.leftChild), node2.getH()) + 1);
        return node1;
    }

    // creating rotateWithRightChild() method to perform rotation of binary tree node with right child
    private Node<K, V> rotateWithRightChild(Node<K, V> node1) {
        Node<K, V> node2 = node1.rightChild;
        node1.rightChild = node2.leftChild;
        node2.leftChild = node1;
        node1.setH(getMaxHeight(getHeight(node1.leftChild), getHeight(node1.rightChild)) + 1);
        node2.setH(getMaxHeight(getHeight(node2.rightChild), node1.getH()) + 1);
        return node2;
    }

    //create doubleWithLeftChild() method to perform double rotation of binary tree node. This method first rotate the left child with its right child, and after that, node3 with the new left child
    private Node<K, V> doubleWithLeftChild(Node<K, V> node3) {
        node3.leftChild = rotateWithRightChild(node3.leftChild);
        return rotateWithLeftChild(node3);
    }

    //create doubleWithRightChild() method to perform double rotation of binary tree node. This method first rotate the right child with its left child and after that node1 with the new right child
    private Node<K, V> doubleWithRightChild(Node<K, V> node1) {
        node1.rightChild = rotateWithLeftChild(node1.rightChild);
        return rotateWithRightChild(node1);
    }

    //create getTotalNumberOfNodes() method to get total number of nodes in the AVL Tree
    public int getTotalNumberOfNodes() {
        return getTotalNumberOfNodes(rootNode);
    }

    private int getTotalNumberOfNodes(Node<K, V> head) {
        if (head == null)
            return 0;
        else {
            int length = 1;
            length = length + getTotalNumberOfNodes(head.leftChild);
            length = length + getTotalNumberOfNodes(head.rightChild);
            return length;
        }
    }

    //create searchElement() method to find an element in the AVL Tree
    public boolean searchElement(V element) {
        return searchElement(rootNode, element);
    }

    public boolean searchByKey(K key) {
        return searchByKey(rootNode, key);
    }

    private Boolean searchByKey(Node<K, V> head, K key) {
        K headKey = head.getKey();
        if (head == null) {
            return false;
        }
        if (key.compareTo(headKey) < 0) {
            return searchByKey(head.leftChild, key);
        } else if (key.compareTo(headKey) > 0) {
            return searchByKey(head.rightChild, key);
        } else
            return true;
    }

    private boolean searchElement(Node<K, V> head, V element) {
        V headElement = head.getElement();
        if (head == null) {
            return false;
        }
        if (element.compareTo(headElement) < 0) {
            head = head.leftChild;
            return searchElement(head.leftChild, element);
        } else if (element.compareTo(headElement) > 0) {
            return searchElement(head.rightChild, element);
        } else
            return true;
    }


    // create inorderTraversal() method for traversing AVL Tree in in-order form
    public void inorderTraversal() {
        inorderTraversal(rootNode);
    }

    private void inorderTraversal(Node<K, V> head) {
        if (head != null) {
            inorderTraversal(head.leftChild);
            System.out.print(head.getElement() + " ");
            inorderTraversal(head.rightChild);
        }
    }

    // create preorderTraversal() method for traversing AVL Tree in pre-order form
    public void preorderTraversal() {
        preorderTraversal(rootNode);
    }

    private void preorderTraversal(Node<K, V> head) {
        if (head != null) {
            System.out.print(head.getElement() + " ");
            preorderTraversal(head.leftChild);
            preorderTraversal(head.rightChild);
        }
    }

    // create postorderTraversal() method for traversing AVL Tree in post-order form
    public void postorderTraversal() {
        postorderTraversal(rootNode);
    }

    private void postorderTraversal(Node<K, V> head) {
        if (head != null) {
            postorderTraversal(head.leftChild);
            postorderTraversal(head.rightChild);
            System.out.print(head.getElement() + " ");
        }
    }
}
