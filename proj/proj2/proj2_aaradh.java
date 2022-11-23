import java.util.*;

public class Dikstra_AdjacencyList {

  static class Edge {
    int source;
    int destination;
    int weight;

    public Edge(int source, int destination, int weight) {
      this.source = source;
      this.destination = destination;
      this.weight = weight;
    }
  }

  static class HeapNode {
    int vertex;
    int distance;
  }

  static class Graph {
    int vertices;

    LinkedList<Edge>[] AdjacencyList;

    Graph(int vertices) {
      this.vertices = vertices;
      AdjacencyList = new LinkedList[vertices];

      for (int i = 0; i < vertices; i++) {
        AdjacencyList[i] = new LinkedList<>();
      }
    }

    public void AddEdge(int source, int destination, int weight) {
      Edge new_edge = new Edge(source, destination, weight);

      AdjacencyList[source].addFirst(new_edge);

      // undirected graph use
      new_edge = new Edge(destination, source, weight);
      AdjacencyList[destination].addFirst(new_edge);
    }

    public void getMinimumDistance(int SourceVertex) {
      int infinity = Integer.MAX_VALUE;
      boolean[] S = new boolean[vertices];

      HeapNode[] heapnodes = new HeapNode[vertices];

      for (int j = 0; j < vertices; j++) {
        heapnodes[j] = new HeapNode();
        heapnodes[j].vertex = j;
        heapnodes[j].distance = infinity;
      }

      heapnodes[SourceVertex].distance = 0;

      MinHeap minheap = new MinHeap(vertices);
      for (int k = 0; k < vertices; k++) {
        minheap.insert(heapnodes[k]);
      }

      while (!minheap.isEmpty()) {
        HeapNode extractNode = minheap.extractMin();

        int extractedVertex = extractNode.vertex;
        S[extractedVertex] = true;

        LinkedList<Edge> list = AdjacencyList[extractedVertex];
        for (int h = 0; h < list.size(); h++) {
          Edge edge = list.get(h);
          int destination = edge.destination;

          if (S[destination] == false) {
            int newkey = heapnodes[extractedVertex].distance + edge.weight;
            int currentKey = heapnodes[destination].distance;

            if (currentKey > newkey) {
              decreasekey(minheap, newkey, destination);
              heapnodes[destination].distance = newkey;
            }

          }
        }
      }
      printDijkstra(heapnodes, SourceVertex);
    }

    public void decreasekey(MinHeap minheap, int newkey, int vertex) {
      int index = minheap.indexes[vertex];

      HeapNode node = minheap.mH[index];
      node.distance = newkey;
      minheap.BubbleUp(index);
    }

    public void printDijkstra(HeapNode[] resultset, int SourceVertex) {
      System.out.println("--- Adjacency List + Min Heap ---");

      for (int k = 0; k < vertices; k++) {
        System.out.println("Vertex: [" + SourceVertex + "," + k + "], Distance: " + resultset[k].distance);
      }
    }

    static class MinHeap {
      int capacity;
      int currentsize;
      HeapNode[] mH;
      int[] indexes;

      public MinHeap(int capacity) {
        this.capacity = capacity;
        mH = new HeapNode[capacity + 1];
        indexes = new int[capacity];

        mH[0] = new HeapNode();
        mH[0].distance = Integer.MIN_VALUE;
        mH[0].vertex = -1;
        currentsize = 0;
      }

      public void display() {
        for (int f = 0; f < currentsize; f++) {
          System.out.println(" " + mH[f].vertex + " distance " + mH[f].distance);
        }
        System.out.println("-----------");
      }

      public void insert(HeapNode x) {
        currentsize++;
        int temp = currentsize;
        mH[temp] = x;
        indexes[x.vertex] = temp;
        BubbleUp(temp);
      }

      public void BubbleUp(int pos) {
        int ptemp = pos / 2;
        int ctemp = pos;

        while (ctemp > 0 && mH[ptemp].distance > mH[ctemp].distance) {
          HeapNode currentnode = mH[ctemp];
          HeapNode parentnode = mH[ptemp];

          indexes[currentnode.vertex] = ptemp;
          indexes[parentnode.vertex] = ctemp;

          swap(ctemp, ptemp);

          ctemp = ptemp;
          ptemp = ptemp / 2;
        }
      }

      public HeapNode extractMin() {
        HeapNode min = mH[1];
        HeapNode lastnode = mH[currentsize];

        indexes[lastnode.vertex] = 1;
        mH[1] = lastnode;
        mH[currentsize] = null;
        sinkDown(1);
        currentsize--;
        return min;
      }

      public void sinkDown(int b) {
        int smallest = b;
        int leftchild = 2 * b;
        int rightchild = 2 * b + 1;

        if (leftchild < heapSize() && mH[smallest].distance > mH[leftchild].distance) {
          smallest = leftchild;
        }
        if (rightchild < heapSize() && mH[smallest].distance > mH[rightchild].distance) {
          smallest = rightchild;
        }

        if (smallest != b) {
          HeapNode smallestnode = mH[smallest];
          HeapNode bnode = mH[b];

          indexes[smallestnode.vertex] = b;
          indexes[bnode.vertex] = smallest;
          swap(b, smallest);
          sinkDown(smallest);
        }
      }

      public void swap(int x, int y) {
        HeapNode temp = mH[x];
        mH[x] = mH[y];
        mH[y] = temp;
      }

      public boolean isEmpty() {
        return currentsize == 0;
      }

      public int heapSize() {
        return currentsize;
      }

    }
  }

  public static void main(String[] args) {
    int source = 0;

    int edges[] = new int[20];
    int density;

    for (int e = 0; e < edges.length; e++) {
      edges[e] = 0;
    }

    int[] graph_size = new int[] { 5, 10, 25, 50, 75, 100, 250, 500, 750,
        1000, 1100, 1250, 1500, 1750, 2000, 2100,
        2250, 2500, 2750, 3000 };

    String[] result = new String[graph_size.length];

    Random random = new Random(2001200110);
    for (int i = 0; i < graph_size.length; i++) {

      Graph graph = new Graph(graph_size[i]);

      for (int j = 0; j < graph_size[i]; j++) {
        for (int k = 0; k < graph_size[i]; k++) {
          int randint = random.nextInt(1, 10000);
          graph.AddEdge(j, k, randint);
          edges[i] = edges[i] + 1;
        }
      }
      long startTime = System.nanoTime();

      graph.getMinimumDistance(source);

      long endTime = System.nanoTime();
      long totalTime = endTime - startTime;

      // 1 second = 1_000_000_000 nano seconds
      double elapsedTimeInSecond = (double) totalTime / 1_000_000_000;

      result[i] = "[" + graph_size[i] + ", " + elapsedTimeInSecond + "]";
      // System.out.println(elapsedTimeInSecond + " seconds");
    }

    for (int y = 0; y < result.length; y++) {
      System.out.println(result[y] + ", Density: " + (edges[y] / (graph_size[y] * (graph_size[y] - 1))));
    }
  }

}
