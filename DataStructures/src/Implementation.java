package DataStructures.src;

public class Implementation {
    public static void main(String[] args) throws Exception {
        BinaryTree<Integer> b = new BinaryTree<>();
        b.add(25);
        b.add(15);
        b.add(50);
        b.add(10);
        b.add(22);
        b.add(35);
        b.add(70);
        b.add(4);b.add(12);
        b.add(18);
        b.add(24);
        b.add(31);
        b.add(44);
        b.add(66);
        b.add(90);

        System.out.println(b);
        System.out.println(b.preOrder());
        System.out.println(b.postOrder());
        System.out.println(b.inOrder());
        b.remove(25);
        System.out.println(b.preOrder());
        System.out.println(b.postOrder());
        System.out.println(b.inOrder());
    }
}

//6 0 1 2 3 4 5 7 8 9