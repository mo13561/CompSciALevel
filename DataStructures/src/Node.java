package DataStructures.src;

public class Node<K> {
    private K value;
    private int numericValue;
    private Node<K> left = null;
    private Node<K> right = null;
    private int position;

    public void setValue(K value) {
        this.value = value;
    }

    public void setNumericValue(int numericValue) {
        this.numericValue = numericValue;
    }

    public void setLeft(Node<K> left) {
        this.left = left;
    }

    public void setRight(Node<K> right) {
        this.right = right;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public int getNumericValue() {
        return numericValue;
    }

    public Node<K> getLeft() {
        return left;
    }

    public Node<K> getRight() {
        return right;
    }

    public Node(K value) {
        this.value = value;
        this.numericValue = findNumericValue(value);
    }

    public K getValue() {
        return value;
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

    public String toString() {
        return this.value + "";
    }

}