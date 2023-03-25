package src.third;

import lombok.Data;

import java.util.*;

/**
 * @author ogbozoyan
 * @date 25.03.2023
 */
@Data
public class BTreeMap<K extends Comparable<K>, V extends Comparable<V>> {
    private int modCount;

    private final Map<K, V> map = new HashMap<>();

    private Node<K> root;

    private final int minimumDegree;

    public BTreeMap(int degree) {
        this.minimumDegree = Math.max(degree, 2);
        this.root = new Node<>(this.minimumDegree);
    }

    public BTreeMap() {
        this.minimumDegree = 2;
        this.root = new Node<>(this.minimumDegree);
    }


    public int size() {
        return map.size();
    }


    public boolean isEmpty() {
        return map.isEmpty();
    }


    public boolean isContainsKey(K key) {
        return map.containsKey(key);
    }

    public V get(V key) {

        return map.get(key);
    }

    private V bTreeSearch(Node<K> x, K key) {
        int i = 0;

        while (i < x.size && key.compareTo(x.keys[i]) > 0) {
            ++i;
        }

        if (i < x.size && key.compareTo(x.keys[i]) == 0) {
            return map.get(key);
        } else if (x.isLeaf()) {
            return null;
        } else {
            return bTreeSearch(x.children[i], key);
        }
    }


    public V remove(K key) {
        if (map.containsKey(key)) {
            ++modCount;
            bTreeDeleteKey(root, key);
            return map.remove(key);
        }

        return null;
    }

    public void clear() {
        root = new Node<>(minimumDegree);
        modCount += size();
        map.clear();
    }

    public V put(K key, V value) {
        ++modCount;

        if (map.containsKey(key)) {
            return map.put(key, value);
        }

        bTreeInsertKey(key);
        map.put(key, value);
        return null;
    }

    private void bTreeInsertKey(K key) {
        Node<K> r = root;

        if (r.size == 2 * minimumDegree - 1) {
            Node<K> s = new Node<>(minimumDegree);
            root = s;
            s.makeInternal();
            s.children[0] = r;
            bTreeSplitChild(s, 0);
            bTreeInsertNonFull(s, key);
        } else {
            bTreeInsertNonFull(r, key);
        }
    }

    private void bTreeSplitChild(Node<K> x, int i) {
        Node<K> z = new Node<>(minimumDegree);
        Node<K> y = x.children[i];

        if (!y.isLeaf()) {
            z.makeInternal();
        }

        z.size = minimumDegree - 1;

        for (int j = 0; j < minimumDegree - 1; ++j) {
            z.keys[j] = y.keys[j + minimumDegree];
            y.keys[j + minimumDegree] = null;
        }

        if (!y.isLeaf()) {
            for (int j = 0; j < minimumDegree; ++j) {
                z.children[j] = y.children[j + minimumDegree];
                y.children[j + minimumDegree] = null;
            }
        }

        int oldSizeOfY = y.size;
        y.size = minimumDegree - 1;
        K pushUpKey = y.keys[minimumDegree - 1];

        for (int j = y.size; j < oldSizeOfY; ++j) {
            y.keys[j] = null;
        }

        for (int j = x.size; j >= i; --j) {
            x.children[j + 1] = x.children[j];
        }

        x.children[i + 1] = z;

        for (int j = x.size - 1; j >= i; --j) {
            x.keys[j + 1] = x.keys[j];
        }

        x.keys[i] = pushUpKey;
        x.size++;
    }

    private void bTreeInsertNonFull(Node<K> x, K k) {
        int i = x.size - 1;

        if (x.isLeaf()) {
            while (i >= 0 && k.compareTo(x.keys[i]) < 0) {
                x.keys[i + 1] = x.keys[i];
                --i;
            }

            x.keys[i + 1] = k;
            x.size++;
        } else {
            while (i >= 0 && k.compareTo(x.keys[i]) < 0) {
                --i;
            }

            ++i;

            if (x.children[i].size == 2 * minimumDegree - 1) {
                bTreeSplitChild(x, i);

                if (k.compareTo(x.keys[i]) > 0) {
                    i++;
                }
            }

            bTreeInsertNonFull(x.children[i], k);
        }
    }

    private static <K extends Comparable<K>> Node<K> getMinimumNode(Node<K> x) {
        while (!x.isLeaf()) {
            x = x.children[0];
        }

        return x;
    }

    private static <K extends Comparable<K>> Node<K> getMaximumNode(Node<K> x) {
        while (!x.isLeaf()) {
            x = x.children[x.size];
        }
        return x;
    }

    private void bTreeDeleteKey(Node<K> x, K key) {
        int keyIndex = findKeyIndex(x, key);

        if (keyIndex >= 0) {
            if (x.isLeaf()) {
                removeFromLeafNode(x, keyIndex);
                return;
            }

            Node<K> y = x.children[keyIndex];

            if (y.size >= minimumDegree) {
                Node<K> tmp = getMaximumNode(y);
                K keyPrime = tmp.keys[tmp.size - 1];
                bTreeDeleteKey(y, keyPrime);
                x.keys[keyIndex] = keyPrime;
                return;
            }

            Node<K> z = x.children[keyIndex + 1];

            if (z.size >= minimumDegree) {
                Node<K> tmp = getMinimumNode(z);
                K keyPrime = tmp.keys[0];
                bTreeDeleteKey(z, keyPrime);
                x.keys[keyIndex] = keyPrime;
                return;
            }

            y.keys[y.size] = key;

            for (int i = 0, j = y.size + 1; i != z.size; ++i, ++j) {
                y.keys[j] = z.keys[i];
            }

            if (!y.isLeaf()) {
                for (int i = 0, j = y.size + 1; i != z.size + 1; ++i, ++j) {
                    y.children[j] = z.children[i];
                }
            }

            y.size = 2 * minimumDegree - 1;

            if (!y.isLeaf()) {
                y.children[y.size] = z.children[z.size];
            }

            for (int i = keyIndex + 1; i < x.size; ++i) {
                x.keys[i - 1] = x.keys[i];
                x.children[i] = x.children[i + 1];
            }

            x.children[x.size] = null;
            x.keys[--x.size] = null;
            bTreeDeleteKey(y, key);

            if (x.size == 0) {
                root = y;
            }
        } else {
            int childIndex = -1;

            for (int i = 0; i < x.size; ++i) {
                if (key.compareTo(x.keys[i]) < 0) {
                    childIndex = i;
                    break;
                }
            }

            if (childIndex == -1) {
                childIndex = x.size;
            }

            Node<K> targetChild = x.children[childIndex];

            if (targetChild.size == minimumDegree - 1) {
                if (childIndex > 0
                        && x.children[childIndex - 1].size >= minimumDegree) {

                    if (targetChild.isLeaf()) {
                        Node<K> leftSibling = x.children[childIndex - 1];

                        K lastLeftSiblingKey =
                                leftSibling.keys[leftSibling.size - 1];

                        K keyToPushDown = x.keys[childIndex - 1];
                        x.keys[childIndex - 1] = lastLeftSiblingKey;

                        for (int i = targetChild.size - 1; i >= 0; --i) {
                            targetChild.keys[i + 1] = targetChild.keys[i];
                        }

                        targetChild.size++;
                        targetChild.keys[0] = keyToPushDown;
                        leftSibling.keys[--leftSibling.size] = null;
                    } else {
                        Node<K> leftSibling = x.children[childIndex - 1];

                        K lastLeftSiblingKey =
                                leftSibling.keys[leftSibling.size - 1];

                        Node<K> lastLeftSiblingChild =
                                leftSibling.children[leftSibling.size];

                        K keyToPushDown = x.keys[childIndex - 1];
                        x.keys[childIndex - 1] = lastLeftSiblingKey;

                        targetChild.children[targetChild.size + 1] =
                                targetChild.children[targetChild.size];

                        for (int i = targetChild.size - 1; i >= 0; --i) {
                            targetChild.keys[i + 1] = targetChild.keys[i];
                            targetChild.children[i + 1] =
                                    targetChild.children[i];
                        }

                        targetChild.size++;
                        targetChild.keys[0] = keyToPushDown;
                        targetChild.children[0] = lastLeftSiblingChild;
                        leftSibling.children[leftSibling.size] = null;
                        leftSibling.keys[--leftSibling.size] = null;
                    }
                } else if (childIndex < x.size
                        && x.children[childIndex + 1].size >= minimumDegree) {
                    if (targetChild.isLeaf()) {
                        Node<K> rightSibling = x.children[childIndex + 1];

                        K firstRightSiblingKey = rightSibling.keys[0];

                        K keyToPushDown = x.keys[childIndex];
                        x.keys[childIndex] = firstRightSiblingKey;

                        for (int i = 1; i < rightSibling.size; ++i)
                            rightSibling.keys[i - 1] = rightSibling.keys[i];


                        rightSibling.keys[--rightSibling.size] = null;

                        targetChild.keys[targetChild.size] = keyToPushDown;
                        targetChild.size++;
                    } else {
                        Node<K> rightSibling = x.children[childIndex + 1];

                        K firstRightSiblingKey = rightSibling.keys[0];
                        Node<K> firstRightSiblingChild =
                                rightSibling.children[0];

                        K keyToPushDown = x.keys[childIndex];
                        x.keys[childIndex] = firstRightSiblingKey;

                        for (int i = 1; i < rightSibling.size; ++i) {
                            rightSibling.keys[i - 1] = rightSibling.keys[i];
                            rightSibling.children[i - 1] =
                                    rightSibling.children[i];
                        }

                        rightSibling.children[rightSibling.size - 1] =
                                rightSibling.children[rightSibling.size];
                        rightSibling.children[rightSibling.size] = null;
                        rightSibling.keys[--rightSibling.size] = null;

                        targetChild.keys[targetChild.size] = keyToPushDown;
                        targetChild.children[++targetChild.size] =
                                firstRightSiblingChild;
                    }
                } else if (childIndex > 0) {


                    Node<K> leftSibling = x.children[childIndex - 1];


                    if (targetChild.isLeaf()) {
                        K keyToPushDown = x.keys[childIndex - 1];
                        leftSibling.keys[leftSibling.size] = keyToPushDown;


                        for (int i = 0, j = leftSibling.size + 1;
                             i != targetChild.size; ++i, ++j) {
                            leftSibling.keys[j] = targetChild.keys[i];
                        }

                        leftSibling.size = 2 * minimumDegree - 1;


                        for (int i = childIndex; i < x.size; ++i) {
                            x.keys[i - 1] = x.keys[i];
                            x.children[i] = x.children[i + 1];
                        }

                        x.keys[x.size - 1] = null;
                        x.children[x.size] = null;
                        x.size--;

                        if (x.size == 0) {
                            root = leftSibling;
                        }

                        targetChild = leftSibling;
                    } else {
                        K keyToPushDown = x.keys[childIndex - 1];
                        leftSibling.keys[leftSibling.size] = keyToPushDown;


                        for (int i = 0, j = leftSibling.size + 1; i != targetChild.size; ++i, ++j) {
                            leftSibling.keys[j] = targetChild.keys[i];
                            leftSibling.children[j] =
                                    targetChild.children[i];
                        }

                        leftSibling.size = 2 * minimumDegree - 1;
                        leftSibling.children[leftSibling.size] =
                                targetChild.children[targetChild.size];


                        for (int i = childIndex; i < x.size; ++i) {
                            x.keys[i - 1] = x.keys[i];
                            x.children[i] = x.children[i + 1];
                        }

                        x.keys[x.size - 1] = null;
                        x.children[x.size--] = null;

                        if (x.size == 0) {
                            root = leftSibling;
                        }

                        targetChild = leftSibling;
                    }
                } else {

                    Node<K> rightSibling = x.children[childIndex + 1];


                    if (targetChild.isLeaf()) {

                        K keyToPushDown = x.keys[childIndex];
                        targetChild.keys[targetChild.size] = keyToPushDown;


                        for (int i = 0, j = targetChild.size + 1;
                             i != rightSibling.size;
                             ++i, ++j) {
                            targetChild.keys[j] = rightSibling.keys[i];
                        }

                        targetChild.size = 2 * minimumDegree - 1;


                        for (int i = childIndex + 1; i < x.size; ++i) {
                            x.keys[i - 1] = x.keys[i];
                            x.children[i] = x.children[i + 1];
                        }

                        x.children[x.size] = null;
                        x.keys[--x.size] = null;

                        if (x.size == 0) {
                            root = targetChild;
                        }
                    } else {

                        K keyToPushDown = x.keys[childIndex];
                        targetChild.keys[targetChild.size] = keyToPushDown;


                        for (int i = 0, j = targetChild.size + 1;
                             i != rightSibling.size;
                             ++i, ++j) {
                            targetChild.keys[j] = rightSibling.keys[i];
                            targetChild.children[j] = rightSibling.children[i];
                        }

                        targetChild.size = 2 * minimumDegree - 1;
                        targetChild.children[targetChild.size] =
                                rightSibling.children[rightSibling.size];


                        for (int i = childIndex + 1; i < x.size; ++i) {
                            x.keys[i - 1] = x.keys[i];
                            x.children[i] = x.children[i + 1];
                        }

                        x.children[x.size - 1] = x.children[x.size];
                        x.children[x.size] = null;
                        x.keys[--x.size] = null;

                        if (x.size == 0) {
                            root = targetChild;
                        }
                    }
                }
            }

            bTreeDeleteKey(targetChild, key);
        }
    }

    private void removeFromLeafNode(Node<K> x, int removedKeyIndex) {
        for (int i = removedKeyIndex + 1; i < x.size; ++i) {
            x.keys[i - 1] = x.keys[i];
        }

        x.keys[--x.size] = null;
    }

    private static <K extends Comparable<K>>
    int findKeyIndex(Node<K> x, K key) {
        for (int i = 0; i != x.size; ++i) {
            if (x.keys[i].compareTo(key) == 0) {
                return i;
            }
        }

        return -1;
    }

    private boolean isHealthy(Node<K> node) {
        if (node.size == 0 && node != root) {
            return false;
        }

        int count = 0;

        for (int i = 0; i < node.keys.length; ++i) {
            if (node.keys[i] == null) {
                break;
            } else {
                count++;
            }
        }

        if (node.size != count) {
            return false;
        }

        if (!node.isLeaf()) {
            count = 0;

            for (int i = 0; i < node.children.length; ++i) {
                if (node.children[i] == null) {
                    break;
                } else {
                    count++;
                }
            }

            if (count != node.size + 1) {
                return false;
            }

            for (int i = 0; i <= node.size; ++i) {
                if (!isHealthy(node.children[i])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Data
    private static final class Node<K extends Comparable<K>> {


        private int size;


        private K[] keys;


        private Node<K>[] children;

        final int minimumDegree;

        Node(int minimumDegree) {
            this.minimumDegree = minimumDegree;
            this.keys = (K[]) new Comparable[2 * minimumDegree - 1];
        }

        void makeInternal() {
            this.children = new Node[keys.length + 1];
        }

        private boolean isLeaf() {
            return children == null;
        }
    }
}
