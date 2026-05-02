import java.util.ArrayList;
import java.util.Stack;

public class BST<K extends Comparable<K>, V> implements Iterable<BST<K, V>.Entry> {

    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public class Entry {
        private K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    public int size() {
        return size;
    }

    public void put(K key, V val) {
        Node newNode = new Node(key, val);

        if (root == null) {
            root = newNode;
            size++;
            return;
        }

        Node current = root;

        while (true) {
            int compare = key.compareTo(current.key);

            if (compare < 0) {
                if (current.left == null) {
                    current.left = newNode;
                    size++;
                    return;
                }
                current = current.left;
            }

            else if (compare > 0) {
                if (current.right == null) {
                    current.right = newNode;
                    size++;
                    return;
                }
                current = current.right;
            }

            else {
                current.val = val;
                return;
            }
        }
    }

    public V get(K key) {
        Node current = root;

        while (current != null) {
            int compare = key.compareTo(current.key);

            if (compare == 0) {
                return current.val;
            }

            else if (compare < 0) {
                current = current.left;
            }

            else {
                current = current.right;
            }
        }

        return null;
    }

    public void delete(K key) {
        Node parent = null;
        Node current = root;

        while (current != null && !current.key.equals(key)) {
            parent = current;

            if (key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        if (current == null) {
            return;
        }

        if (current.left == null || current.right == null) {
            Node child;

            if (current.left != null) {
                child = current.left;
            } else {
                child = current.right;
            }

            if (parent == null) {
                root = child;
            }

            else if (parent.left == current) {
                parent.left = child;
            }

            else {
                parent.right = child;
            }
        }

        else {
            Node successorParent = current;
            Node successor = current.right;

            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }

            current.key = successor.key;
            current.val = successor.val;

            if (successorParent.left == successor) {
                successorParent.left = successor.right;
            } else {
                successorParent.right = successor.right;
            }
        }

        size--;
    }

    public java.util.Iterator<Entry> iterator() {
        ArrayList<Entry> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            list.add(new Entry(current.key, current.val));
            current = current.right;
        }

        return list.iterator();
    }
}