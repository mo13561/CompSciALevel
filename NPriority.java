import java.util.ArrayList;

public class NPriority {//Priority Queue made with ArrayList
    private ArrayList<Integer> queue;
    private int maxSize;

    public NPriority() {
        this.maxSize = 6;
        this.queue = new ArrayList<>();
    }

    public NPriority(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new ArrayList<>();
    }

    public void add(int value) throws Exception {
        if (isEmpty()) {
            this.queue.add(value);
        } else if (isFull()) {
            throw new Exception("The queue is full, you cannot add more items.");
        } else {
            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i) > value) {
                    this.queue.add(i,value);
                    break;
                } else if (i == queue.size() - 1) {
                    this.queue.add(value);
                    break;
                }
            }
        }
    }

    public int remove() throws Exception {
        if (isEmpty()) {
            throw new Exception("The queue is empty, there is nothing to remove");
        } else {
            return this.queue.remove(0);
        }
    }

    public boolean isEmpty() {
        return this.queue.size() == 0;
    }

    public boolean isFull() {
        return this.queue.size() == this.maxSize;
    }
}
