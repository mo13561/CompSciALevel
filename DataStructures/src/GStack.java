package DataStructures.src;
public class GStack<T> { //Stack made with generics
    private T current;
    private GStack<T> previous;
    private int currentSize;
    private int maxSize;

    public GStack() {
        this.currentSize = 0;
        this.maxSize = 3;
    }

    public GStack(int maxSize) {
        this.currentSize = 0;
        this.maxSize = maxSize;
    }

    public GStack(GStack<T> previous, T value, int maxSize, int currentSize) {
        this.current = value;
        this.maxSize = maxSize;
        this.previous = previous;
        this.currentSize = ++currentSize;
    }

    public void push(T value) throws Exception {
        if (isFull()) {
            throw new Exception("The stack is full, you cannot push more items onto it.");
        } else {
            this.previous = new GStack(previous, this.current, this.maxSize, this.currentSize);
            this.current = value;
            this.currentSize++;
        }
    }

    public void pop() throws Exception{
        if (isEmpty()) {
            throw new Exception("The stack is empty, there is no item to pop");
        } else {
            this.current = previous.current;
            this.previous = previous.previous;
            this.currentSize--;
        }
    }

    public T peek() throws Exception{
        if (isEmpty()) {
            throw new Exception("The stack is empty, there is no item to peek");
        } else {
            return this.current;
        }
    }

    public boolean isEmpty() {
        return this.current == null;
    }

    public boolean isFull() {
        return this.currentSize == this.maxSize;
    }
}
