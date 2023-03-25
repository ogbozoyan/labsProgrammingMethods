package src.fourth;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author ogbozoyan
 * @date 25.03.2023
 */
@Data
public class MyHashMap<K, V> {
    private NodeContainer<K, V>[] nodeContainer;
    private int size;
    private final Integer CAPACITY = (int) Math.pow(2, 10);

    public Integer getNodeCount(int index) {
        return nodeContainer[index].getNodes().size();
    }

    public MyHashMap() {
        this.nodeContainer = new NodeContainer[CAPACITY];
    }

    private int hash(K key) {
        return (key.hashCode() & 0x20642000) % CAPACITY;
    }

    public void put(K key, V val) {
        if (isContainKey(key)) {
            Node<K, V> node = getNode(key);
            assert node != null;
            node.setValue(val);
        } else {
            int hash = hash(key);
            if (nodeContainer[hash] == null) {
                nodeContainer[hash] = new NodeContainer<>();
            }
            nodeContainer[hash].addNode(new Node<>(key, val));
            size++;
        }
    }

    public V get(K key) {
        return isContainKey(key) ? Objects.requireNonNull(getNode(key)).getValue()
                : null;
    }

    public void remove(K key) {
        if (isContainKey(key)) {
            nodeContainer[hash(key)].removeNode(getNode(key));
            size--;
        }
    }

    private boolean isContainKey(K key) {
        int hash = hash(key);
        return !(
                Objects.isNull(nodeContainer[hash])
                        || Objects.isNull(getNode(key))
        );
    }

    private Node<K, V> getNode(K key) {
        int hash = hash(key);
        for (int i = 0; i < nodeContainer[hash].getNodes().size(); i++) {
            Node<K, V> node = nodeContainer[hash].getNodes().get(i);
            if (node.getKey().equals(key)) {
                return node;
            }
        }
        return null;
    }

}

@Data
@EqualsAndHashCode
@NoArgsConstructor
class Node<K, V> {
    private K key;
    private V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

@Data
@NoArgsConstructor
class NodeContainer<K, V> {
    private List<Node<K, V>> nodes;

    public void addNode(Node<K, V> node) {
        if (this.nodes == null) {
            this.nodes = new LinkedList<>();
            this.nodes.add(node);
        }
    }

    public void removeNode(Node<K, V> node) {
        try {
            this.nodes.remove(node);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
