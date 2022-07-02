package DataStructures.src;
import java.util.Comparator;

public class Dijkstra {
    static class Node implements Comparator<Node> {
        public int nodeName;
        public int distFromSrc;
        public Node(int nodeName, int distFromSrc) {
            this.nodeName = nodeName;
            this.distFromSrc = distFromSrc;
        }

        @Override
        public int compare(Node first, Node second) {
            return Integer.compare(first.distFromSrc, second.distFromSrc);
        }
    }

    private int vertexNum;
    private int[] distances; //distances from "source" to each node
    private int[] lastVisited;
    private LinkedList<Integer> visited;
    private PriorityQ<Integer> pq;

    public Dijkstra(LinkedList<LinkedList<Node>> adjList, int src) throws Exception {
        vertexNum = adjList.len();
        distances = new int[vertexNum];
        visited = new LinkedList<>();
        pq = new PriorityQ<>();
        for (int i = 0; i < vertexNum; i++) {
            distances[i] = Integer.MAX_VALUE;
        }
        lastVisited = new int[vertexNum];
        lastVisited[src] = src;
        pq.add(src, 0);
        distances[src] = 0;
        while (visited.len() != vertexNum && !pq.isEmpty()) {
            int minNode = pq.pop();
            if (visited.search(minNode))
                continue;
            int newDist;
            for (int i = 0; i < adjList.getValue(minNode).len(); i++) {
                Node neighbour = adjList.getValue(minNode).getValue(i);
                if (!visited.search(neighbour.nodeName)) {
                    newDist = distances[minNode] + neighbour.distFromSrc;
                    if (newDist < distances[neighbour.nodeName]) {
                        distances[neighbour.nodeName] = newDist;
                        lastVisited[neighbour.nodeName] = minNode;
                    }
                    pq.add(neighbour.nodeName, distances[neighbour.nodeName]);
                }
            }
            visited.append(minNode);
        }
    }

    public int PathLength(int endNode) {
        for (int i = 0 ; i < distances.length; i++) {
            System.out.println(i + "|| distance: " + distances[i]);
        }
        return distances[endNode];
    }

    public String Path(int endNode) {
        String ans = "";
        int x = endNode;
        while (lastVisited[x] != x) {
            ans = lastVisited[x] + " -> " + ans;
            x = lastVisited[x];
        }
        return ans + endNode;
    }
}
