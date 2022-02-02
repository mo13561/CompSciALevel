package DataStructures.src;

public class BinaryTree<T> {
    static class Node<K> {
        K value;
        int numericValue;
        int left = -1;
        int right = -1;
        int position;

        public int getLeft() {
            return left;
        }

        public int getRight() {
            return right;
        }

        public Node(K value) {
            this.value = value;
            this.numericValue = findNumericValue(value);
        }

        private int findNumericValue(K value) {
            if (value instanceof Integer) {
                return (Integer) value;
            } else if (value instanceof Double) {
                return ((Double) value).intValue();
            } else if (value instanceof Character) {
                return (int) ((Character) value);
            } else if (value instanceof Boolean) {
                return (Boolean) value ? 1 : 0;
            }
            return -1;
        }

    }
    Node<T>[] nodes;
    int rootPos  = 0;

    public BinaryTree() {
        nodes = new Node[10];
    }

    public BinaryTree(int initSize) {
        nodes = new Node[initSize];
    }

    public void add(T value) throws Exception {
        if (contains(value))
            throw new Exception("This node already exists!");
        Node<T> node = new Node<>(value);
        int nextNode = rootPos;
        Node<T> traversal;
        int verdict;
        do {
            traversal = nodes[nextNode];
            if (node.value instanceof String) {
                verdict = ((String) node.value).compareToIgnoreCase((String) traversal.value);
            } else {
                verdict = (node.numericValue < traversal.numericValue) ? -1 : 1;
            }
            if (verdict == 0) { 
                throw new Exception("The two strings cannot be lexicographically identical");
            } else if (verdict < 0 && traversal.getLeft() != -1) {
                nextNode = traversal.getLeft();
            } else if (verdict > 0 && traversal.getRight() != -1){
                nextNode = traversal.getRight();
            }
        } while (!nodes[nextNode].equals(traversal));
        
    }

    private boolean contains(T value) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == null) continue;
            if (nodes[i].toString().equals(value.toString())) return true;
        }
        return false;
    }
}

