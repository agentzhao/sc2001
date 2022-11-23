class insertion {
  static void sort(int a[], int n) {
    int i, j, key;
    for (i = 1; i < n; i++) {
      key = a[i];
      j = i - 1;

      // move elements that are greater than key
      while (j >= 0 && a[j] > key) {
        a[j + 1] = a[j];
        j--;
      }
      a[j + 1] = key;
    }
  }

  static void insertionsort(int[] a, int n) {
    for (int i = 1; i < n; i++) {
      for (int j = i; j > 0; j--) {
        // if smaller than previous, swap
        if (a[j] < a[j - 1]) {
          int temp = a[j];
          a[j] = a[j - 1];
          a[j - 1] = temp;
        } else {
          break; // correct position found
        }
      }
    }
  }

  static void printArray(int a[], int n) {
    for (int i = 0; i < n; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

  public static void main(String[] args) {
    // int[] a;
    // a = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
    int[] a = { 5, 2, 9, 1, 3, 8, 4, 7, 6, 10 };
    int size = a.length;
    printArray(a, size);

    insertionsort(a, size);
    printArray(a, size);
  }
}
