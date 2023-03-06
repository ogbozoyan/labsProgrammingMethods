package src.second;

import lombok.Data;

/**
 * @author ogbozoyan
 * @date 06.03.2023
 */
@Data
public class MapImpl<K extends Comparable<K>, V extends Comparable<V>> {
    private V value;
    private K key;
    private AVLTree<K, V> tree;

    public boolean getKey(K key) {
        return tree.searchByKey(key);
    }

    public boolean getValue(V value) {
        return tree.searchElement(value);
    }

    public Boolean isEmpty() {
        return tree.checkEmpty();
    }

    public void deleteAll() {
        if (tree == null) {
            throw new RuntimeException("tree not initialized");
        }
        tree.removeAll();
    }

    public void add(K key, V value) {
        if (tree == null) {
            throw new RuntimeException("tree not initialized");
        }
        tree.insertElement(key, value);
    }

    public MapImpl<K, V> copy(MapImpl<K, V> dest) {
        dest.tree = this.tree;
        return dest;
    }

    public MapImpl() {
        tree = new AVLTree<>();
    }

    public MapImpl(K key, V value) {
        this.key = key;
        this.value = value;
        tree = new AVLTree<>();
    }
}
