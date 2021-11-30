public class NLinear {//"Dumb" linear queue made with primitive array. "Dumb" as in "can only have a set number of additions and removals for each instance of the queue"
    private int[] arr;
    private int front, rear, currentSize, spaceLeft;

    public NLinear() {
        this.spaceLeft = 4;
        this.arr = new int[this.spaceLeft];
        this.currentSize = 0;
    }

    public NLinear(int maxSize) {
        this.spaceLeft = maxSize;
        this.arr = new int[this.spaceLeft];
        this.currentSize = 0;
    }

    public void add(int value) throws Exception {
        if (isEmpty()) {
            this.front = this.rear = 0;
        } else if (isFull()) {
            throw new Exception("The queue is full you cannot add more values.");
        } else {
            ++this.rear;
        }
        this.arr[this.rear] = value;
        this.spaceLeft--;
        this.currentSize++;
    }

    public int remove() throws Exception {
        if (isEmpty()) {
            throw new Exception("The queue is empty, you can't remove any more values.");
        }
        return arr[this.front++];
    }

    public boolean isEmpty() {
        return this.currentSize == 0;
    }

    public boolean isFull() {
        return this.spaceLeft == 0;
    }
}
