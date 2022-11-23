// union is too expensive
// Trees are flat but too expensive to keep them flat

public class quickfind {
  public int[] id;

  // set id of each object to itself (N)
  public quickunion(int N) {
    id = new int[N];
    for (int i = 0; i < N; i++) {
      id[i] = i;
    }
  }

  // chase parent pointers until reach root (1)
  public int find(int i) {
    return id[i];
  }

  // change all entries with id[p] to id[q] (at most 2N + 2 array accesses)
  public void union(int p, int q) {
    int pid = find(p);
    int qid = find(q);
    for (int i = 0; i < id.length; i++) {
      if (id[i] == pid) {
        id[i] = qid;
      }
    }
  }
}
