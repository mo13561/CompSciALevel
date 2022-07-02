package DataStructures.src;

public class Implementation {
    public static void main(String[] args) throws Exception {
        LinkedList<LinkedList<Dijkstra.Node>> adj = new LinkedList<LinkedList<Dijkstra.Node>>();
        LinkedList<Dijkstra.Node> A = new LinkedList<>();
        LinkedList<Dijkstra.Node> B = new LinkedList<>();
        LinkedList<Dijkstra.Node> C = new LinkedList<>();
        LinkedList<Dijkstra.Node> D = new LinkedList<>();
        LinkedList<Dijkstra.Node> E = new LinkedList<>();
        LinkedList<Dijkstra.Node> F = new LinkedList<>();
        LinkedList<Dijkstra.Node> G = new LinkedList<>();
        A.append(new Dijkstra.Node(1,4));
        A.append(new Dijkstra.Node(2,2));
        B.append(new Dijkstra.Node(0,4));
        B.append(new Dijkstra.Node(2,3));
        B.append(new Dijkstra.Node(3,7));
        B.append(new Dijkstra.Node(4,4));
        C.append(new Dijkstra.Node(0,2));
        C.append(new Dijkstra.Node(1,3));
        C.append(new Dijkstra.Node(3,3));
        D.append(new Dijkstra.Node(2,3));
        D.append(new Dijkstra.Node(1,7));
        D.append(new Dijkstra.Node(4,1));
        D.append(new Dijkstra.Node(5,2));
        E.append(new Dijkstra.Node(1,4));
        E.append(new Dijkstra.Node(3,1));
        E.append(new Dijkstra.Node(5,5));
        E.append(new Dijkstra.Node(6,7));
        F.append(new Dijkstra.Node(3,2));
        F.append(new Dijkstra.Node(4,5));
        F.append(new Dijkstra.Node(6,5));
        G.append(new Dijkstra.Node(4,7));
        G.append(new Dijkstra.Node(5,5));
        adj.append(A);
        adj.append(B);
        adj.append(C);
        adj.append(D);
        adj.append(E);
        adj.append(F);
        adj.append(G);




//        adj.add(0,1,4);
//        adj.add(0,2,2);
//        adj.add(1,2,3);
//        adj.add(2,3,3);
//        adj.add(1,3,7);
//        adj.add(1,4,4);
//        adj.add(3,4,1);
//        adj.add(4,5,5);
//        adj.add(3,5,2);
//        adj.add(4,6,7);
//        adj.add(5,6,5);
        Dijkstra g = new Dijkstra(adj,0);
        System.out.println(g.PathLength(6));
        System.out.println(g.Path(6));
    }
}

//6 0 1 2 3 4 5 7 8 9