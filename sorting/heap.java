// heap: complete binary tree at least through height - 1
// k level has 2^k - 1 elements
// height of heap with n nodes is O(lg n)
public class heap {
  private int heapData[];
  private int heapSize;
  private int heapCapacity;

  // O(n)
  public void constructHeap(int[] arr, heap H) {
    for (int i = arr.length / 2 - 1; i >= 0; i--) {
      heapify(H);
    }
  }

  public void heapSort(int[] arr, int n) {
    for (int i = n; i >= 1; i--) {
      int curMax = getMax(H);
      // as a result, H has i - 1 elements
      deleteMax(H);
      // insert curMax into sorted list
      arr[i] = curMax;
    }
  }

  public void deleteMax(heap H) {
    // copy the rightmost element at the lowest level to k
    int k = H.heapData[H.heapSize];
    // delete the rightmost element at the lowest level
    H.heapSize--;
    fixHeap(H, k);
  }

  public void fixHeapR(heap H, int k) {
    // recursive version
    /*
     * if (H is a left){
     * insert k in root of H
     * } else {
     * compare left child with right child
     * largerSubHeap = larger child of H;
     * if (k >= key of root(largerSubHeap)){
     * insert k in root of H
     * } else {
     * insert root(largerSubHeap) in root of H
     * fixHeap(largerSubHeap, k)
     * }
     * }
     */
  }

  // height of a heap with n nodes is O(log n)
  // worse-case time complexity of fixHeap is O(log n)
  public void fixHeap(heap H, int k) { // iterative
    int root = 1;
    int left = 2 * root;

    while (left <= H.heapSize) {
      // left child should be the larger one
      if (left < H.heapSize && H.heapData[left] < H.heapData[left + 1]) {
        left++;
        if (k >= H.heapData[left]) {
          break;
        }
        H.heapData[root] = H.heapData[left];
        root = left;
        left = 2 * root;
      }
    }
    H.heapData[root] = k;
  }

  // O(n)
  public void heapify(heap H) {
    // if H is not a leaf
    if (H.heapSize > 1) {
      // heapify left and right subtree of Heap H
      heapify(H.left);
      heapify(H.right);
      int k = root(H);
      fixHeap(H, k);
    }
  }

  /* A utility function to print array of size n */
  static void printArray(int arr[]) {
    int n = arr.length;
    for (int i = 0; i < n; ++i)
      System.out.print(arr[i] + " ");
    System.out.println();
  }

  // Driver program
  public static void main(String args[]) {
    int arr[] = { 12, 11, 13, 5, 6, 7 };

    heap h = new heap();
    h.constructHeap(arr, h);
    h.heapSort(arr, arr.length);

    System.out.println("Sorted array is");
    printArray(arr);
  }
}
