package DataStructures.src;

import DataStructures.src.LinkedList;

public class Implementation {
    public static void main(String[] args) throws Exception {
        LinkedList<Integer> list = new LinkedList<>();
        System.out.println(list.isEmpty());
        // list.pop();
        System.out.println(list.len());
        list.append(1);
        System.out.println(list);
        System.out.println(list.len());
        list.append(4);
        list.append(5);
        System.out.println(list.index(1));
        System.out.println(list.index(4));
        System.out.println(list.index(5));
        // System.out.println(list.index(6));
        list.insert(1, 3);
        System.out.println(list);
        list.append(5);
        System.out.println(list);
        // list.insert(5, 3);
        list.insert(0, 2);
        System.out.println(list);
        list.remove(5);
        System.out.println(list);
        // list.remove(6);
        System.out.println(list.pop(2));
        System.out.println(list);
        // list.pop(4);
        System.out.println(list.isEmpty());
        System.out.println(list.search(4));
        System.out.println(list.search(3));
        System.out.println(list.pop());
        System.out.println(list);
        System.out.println(list.len());
    }
}
