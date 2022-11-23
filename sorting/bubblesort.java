class bubblesort {
  static void bubble(int a[], int n) {
    int i, j, temp;
    for (i = n - 2; i >= 0; i--) {
      for (j = 0; j <= i; j++) {
        if (a[j] > a[j + 1]) {
          temp = a[j];
          a[j] = a[j + 1];
          a[j + 1] = temp;
        }
      }
    }
  }

  static void printList(int a[], int n) {
    for (int i = 0; i < n; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println();
  }

  public static void main(String args[]) {
    int a[] = { 9, 3, 5, 1, 7, 2, 8, 4, 10, 6 };
    int n = a.length;
    printList(a, n);

    bubble(a, n);
    printList(a, n);
  }
}
