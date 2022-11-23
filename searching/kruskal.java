// Compute minimum spanning tree
// greedy algorithm
// adds edges to the tree in order of increasing weight
// if the edge creates a cycle, it is not added
// O(E log E) time

public class kruskal {
  private Queue<Edge> mst = new Queue<Edge>();

  public krukal(EdgeWeightedGraph G){
    MinPQ<Edge> pq = new MinPQ<Edge>(G.edges());

    UF uf = new UF(G.V());
    while (!pq.isEmpty() && mst.size() < G.V() - 1) {
      Edge e = pq.delMin(); // get min weight edge on pq
      int v = e.either();
      int w = e.other(v);
      if (!uf.connected(v, w)) {   // no cycle O(log* |V|)
        uf.union(v, w); // merge sets
        mst.enqueue(e); // add edge to mst
      }
    }
  }

  public Iterable<Edge> edges() {
    return mst;
  };

};
