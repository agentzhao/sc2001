// WQUPC
// Weighted Quick Union with Path Compression
// Avoid tall trees
// Keep track of size of each tree (number of objects)
// Balance by linking root of smaller tree to root of larger tree

class WQUPC {

  public int find(int i) {
    while (i != id[i]) {
      id[i] = id[id[i]]; // path compression keeps tree flat
      i = id[i];
    }
    return i;
  }
}
