package DataStructures.src;
public class PQueue<T> {//Priority Queue made with a generic linked list
    private int size;
    private int maxSize;
    private Node<T> head;//first item in queue

    public PQueue() {
        this.maxSize = 6;
        this.head = null;
        this.size = 0;
    }

    public PQueue(int maxSize) {
        this.maxSize = maxSize;
        this.head = null;
        this.size = 0;
    }

    public void add(T value) throws Exception {
        if (isEmpty()) {//if it is empty then the 'head' becomes the value
            this.head = new Node<>(value, null);
        } else if (isFull()) {//if it is full, you acn't add any more. obviously.
            throw new Exception("The queue is full, you cannot add more items to it.");
        } else {
            Node<T> traversal = this.head;//object to traverse the list, starting at the first item to be removed next
            boolean notAdded = true;//flag
            while (notAdded) {//could (and should) be a for loop to size, but that would require the use of breaks.
                if ((Integer)traversal.value > (Integer)value) {//if the traversal itself is bigger, then the object before is the value
                    this.head = new Node<>(value, traversal, traversal.before);
                    notAdded = false;
                } else if (traversal.next == null) {//if there is no next item, make one.
                    traversal.next = new Node<>(value, traversal);
                    notAdded = false;
                } else if ((Integer)traversal.next.value > (Integer)value) {//if the next item is bigger, then the item is added before it.
                    traversal.next.before = traversal.next = new Node<>(value, traversal.next, traversal);
                    notAdded = false;
                }
                traversal = traversal.next;//goes to next item
            }
        }
        this.size++;
    }

    public T remove() throws Exception {
        if (isEmpty()) {
            throw new Exception("The queue is empty, you cannot remove any items from it.");
        }
        T value = this.head.value;
        this.head = this.head.next;
        this.size--;
        return value;
    }

    public String toString() {//toString method to display queue
        String response = "" + this.head.value;//making it look nice
        Node<T> traversal = this.head.next;
        for (int i = 0; i < this.size - 1; i++) {
            response += " <- " + traversal.value;
            if (traversal.next != null) {
                traversal = traversal.next;
            }
        }
        return response;
    }

    public boolean isFull() {
        return this.size == this.maxSize;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }
}

class Node<T> {//Node class to make linked list
    T value;
    Node<T> next;
    Node<T> before;

    public Node(T value, Node<T> before) {
        this.value = value;
        this.before = before;
        this.next = null;
    }

    public Node(T value, Node<T> next, Node<T> before) {
        this.value = value;
        this.next = next;
        this.before = before;
    }
}
