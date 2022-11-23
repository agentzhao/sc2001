// Trees can be tall
// find() and connected() too expensive

public class quickunion {
  public int[] id;

  // set id of each object to itself (N)
  public quickunion(int N) {
    id = new int[N];
    for (int i = 0; i < N; i++) {
      id[i] = i;
    }
  }

  // chase parent pointers until reach root (i)
  public int find(int i) {
    while (i != id[i]) {
      i = id[i];
    }
    return i;
  }

  // change root of p to point to root of q (depth of p and q)
  public void union(int p, int q) {
    int i = find(p);
    int j = find(q);
    id[i] = j;
  }
}
