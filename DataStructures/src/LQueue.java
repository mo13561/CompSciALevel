package DataStructures.src;
public class LQueue<T> { //Linear Queue made with generics
    private LQueue<T> front, rear;
    private T current;
    private LQueue<T> next;
    private int spaceLeft;

    public LQueue() {
        this.spaceLeft = 4;
    }

    public LQueue(int maxSize) {
        this.spaceLeft = maxSize;
    }

    public LQueue(T value, LQueue<T> next) {
        this.current = value;
        this.next = next;
    }

    public void add(T value) throws Exception {
        if (isEmpty()) {
            this.rear = this.front = new LQueue(value, next);
            this.spaceLeft--;
        } else if (isFull()) {
            throw new Exception("Queue is full, you cannot add more");
        } else {
            this.rear.next = this.rear = new LQueue(value, next);
            this.spaceLeft--;
        }
    }

    public T remove() throws Exception {
        T removed;
        if (!isEmpty()) {
            removed = this.front.current;
            this.front = this.front.next;
        } else {
            throw new Exception("You cannot remove a value that does not exist!");
        }
        if (this.front == null) {
            this.rear = null;
        }
        return removed;
    }

    public boolean isEmpty() {
        return this.rear == null;
    }

    public boolean isFull() {
        return this.spaceLeft == 0;
    }
}
