package DataStructures.src;

public class BinaryTree<T> {
    int size = 0;
    Node<T> root;
    LinkedList<T> nodes;

    public BinaryTree() {
        root = null;
        nodes = new LinkedList<>();
    }

    public void add(T value) throws Exception {
        if (size() == 0) {
            root = new Node<>(value);
        } else {
            Node<T> node = new Node<>(value);
            Node<T> traversal = traverse(this.root, node);
            if (node.getValue() instanceof String && ((String) node.getValue()).compareToIgnoreCase((String) traversal.getValue()) < 0) {
                traversal.setLeft(node);
            } else if (node.getNumericValue() < traversal.getNumericValue()) {
                traversal.setLeft(node);
            } else {
                traversal.setRight(node);
            }
        }
        nodes.append(value);
        this.size++;
    }

    private Node<T> traverse(Node<T> root, Node<T> node) throws Exception {
        if (node.getValue() instanceof String) {
            if (((String) node.getValue()).compareToIgnoreCase((String) root.getValue()) < 0 && root.getLeft() != null) {
                root = traverse(root.getLeft(), node);
            } else if (((String) node.getValue()).compareToIgnoreCase((String) root.getValue()) > 0 && root.getRight() != null) {
                root = traverse(root.getRight(), node);
            } else if (((String) node.getValue()).compareToIgnoreCase((String) root.getValue()) == 0) {
                throw new Exception("You cannot insert an existing node!!!");
            }
        } else {
            if (node.getNumericValue() < root.getNumericValue() && root.getLeft() != null) {
                root = traverse(root.getLeft(), node);
            } else if (node.getNumericValue() > root.getNumericValue() && root.getRight() != null) {
                root = traverse(root.getRight(), node);
            } else if (node.getNumericValue() == root.getNumericValue()) {
                throw new Exception("You cannot insert an existing node!!!");
            }
        }
        return root;
    }

    public void reconstruct(T value) throws Exception {
        if (size() == 0) {
            root = new Node<>(value);
        } else {
            Node<T> node = new Node<>(value);
            Node<T> traversal = traverse(this.root, node);
            if (node.getValue() instanceof String && ((String) node.getValue()).compareToIgnoreCase((String) traversal.getValue()) < 0) {
                traversal.setLeft(node);
            } else if (node.getNumericValue() < traversal.getNumericValue()) {
                traversal.setLeft(node);
            } else {
                traversal.setRight(node);
            }
        }
        this.size++;
    }

    public void remove(T value) throws Exception {
        if (!nodes.search(value))
            throw new Exception("You cannot remove that which does not exist!!!");
        nodes.remove(value);
        if (nodes.len() == 0) {
            this.size = 0;
            root = null;
        } else {
            this.root = null;
            LinkedList<T>.Node front = nodes.getFront();
            this.size = 0;
            while (front != null) {
                reconstruct(front.getValue());
                front = front.getNext();
            }
        }
    }

    public int size() {
        return this.size;
    }

    public String preOrder() throws Exception {
        if (size() == 0)
            throw new Exception("You cannot traverse an empty tree!!!");
        return traversePreOrder(root);
    }

    private String traversePreOrder(Node<T> root) {
        String s = root + " ";
        if (root.getLeft() != null)
            s += traversePreOrder(root.getLeft());
        if (root.getRight() != null)
            s += traversePreOrder(root.getRight());
        return s;
    }

    public String postOrder() throws Exception {
        if (size() == 0)
            throw new Exception("You cannot traverse an empty tree!!!");
        return traversePostOrder(root);
    }

    private String traversePostOrder(Node<T> root) {
        String s = "";
        if (root.getLeft() != null)
            s += traversePostOrder(root.getLeft());
        if (root.getRight() != null)
            s += traversePostOrder(root.getRight());
        s += root + " ";
        return s;
    }

    public String inOrder() throws Exception {
        if (size() == 0)
            throw new Exception("You cannot traverse an empty tree!!!");
        return traverseInOrder(root);
    }

    private String traverseInOrder(Node<T> root) {
        String s = "";
        if (root.getLeft() != null)
            s += traverseInOrder(root.getLeft());
        s += root + " ";
        if (root.getRight() != null)
            s += traverseInOrder(root.getRight());
        return s;
    }
}