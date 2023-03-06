package src.second;

import lombok.Data;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
@Data
public class Node<K extends Comparable<K>,V extends Comparable<V>> {
    K key;
    V element;
    int h;  //for height
    Node<K,V> leftChild;
    Node<K,V> rightChild;

    public Node() {
        leftChild = null;
        rightChild = null;
        element = null;
        key = null;
        h = 0;
    }

    public Node(K key,V element) {
        leftChild = null;
        rightChild = null;
        this.element = element;
        this.key = key;
        h = 0;
    }

}
