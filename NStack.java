public class NStack {//Normal Stack With Primitive int Array
    private int[] array;
    private int maxSize = 3;
    private int currentSize;
    private int front;
    private final int rear;

    public NStack() {
        this.array = new int[this.maxSize];
        this.front = -1;
        this.rear = 0;
        this.currentSize = 0;
    }

    public NStack(int maxSize) {
        this.maxSize = maxSize;
        this.array = new int[this.maxSize];
        this.front = -1;
        this.rear = 0;
        this.currentSize = 0;
    }

    public void push(int value) throws Exception {
        if (isEmpty()) {
            front = rear;
        } else if (isFull()) {
            throw new Exception("The stack is full you can't add more values on top of it.");
        } else {
            front++;
        }
        array[front] = value;
    }


    public void pop() throws Exception {
        if (isEmpty()) {
            throw new Exception("The stack is empty there is no value for it to pop.");
        } else {
            front--;
            currentSize--;
        }
    }

    public int peek() {
        return array[front];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public boolean isFull() {
        return currentSize == maxSize;
    }
}
