package DataStructures.src;
public class Hashmap<T> {
    class KeyValue<V> {
        private int key;
        private int position;
        private V value;

        public KeyValue(int key, V value) {
            setKey(key);
            setValue(value);
            setPosition(hashingFunction(key));
        }

        public int hashingFunction(int key) {
            int pos = key % size;
            if (map[pos] != null) pos = hashingFunction(key+1);
            return pos;
        }

        public int getKey() {
            return key;
        }

        public void setKey(int key) {
            this.key = key;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
    private int size = 11;
    private int length = 0;
    KeyValue<T>[] map;

    public Hashmap() {
        this.map = new KeyValue[this.size];
    }

    public Hashmap(int size) {
        this.size = size;
        this.map = new KeyValue[this.size];
    }

    public void add(int key, T value) throws UnsupportedOperationException  {
        if (isFull()) throw new UnsupportedOperationException("There is no empty address");
        if (contains(key)) throw new IllegalArgumentException("The key is already present");
        KeyValue<T> helper = new KeyValue<>(key, value);
        map[helper.position] = helper;
        this.length++;
    }

    public void delete(int key) throws IllegalArgumentException {
        if (!contains(key)) throw new IllegalArgumentException("The given key does not exist.");
        map[posFind(key)] = null;
        this.length--;
        this.rearrange();
    }

    public void rearrange() {
        for (int i = 0; i < this.size; i++) {
            if (map[i] == null) continue;
            int prefPos = map[i].key % this.size;
            while (prefPos != i) {
                if (map[prefPos] == null) {
                    map[prefPos] = map[i];
                    map[i] = null;
                    map[prefPos].setPosition(prefPos);
                    continue;
                }
                prefPos = ++prefPos % this.size;
            }
        }
    }

    public T item(int key) throws IllegalArgumentException {
        if (!contains(key)) throw new IllegalArgumentException("The given key does not exist.");
        return map[posFind(key)].getValue();
    }

    public int posFind(int key) {
        int pos = key % this.size;
        for (int i = 0; i < this.size; i++) {
            if (map[pos] == null) continue;
            if (map[pos].key == key) return pos;
            pos = ++pos % this.size;
        }
        return 0;
    }

    public boolean contains(int key) {
        int pos = key % this.size;
        for (int i = 0; i < this.size; i++) {
            if (map[pos] == null) continue;
            if (map[pos].getKey() == key) return true;
            pos = ++pos % this.size;
        }
        return false;
    }

    public String toString() {
        String display = "";
        for (int i = 0; i < this.size; i++) {
            if (map[i] == null) continue;
            display += "[ " + map[i].getKey() + " : " + map[i].getValue() + " ]";
        }
        return display;
    }

    public boolean isFull() {
        return this.length() == this.size;
    }

    public boolean isEmpty() {
        return this.length() == 0;
    }

    public int length() {
        return this.length;
    }
}
