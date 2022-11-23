// n-1 iterations (outer loop)
// O(n^2)
#include <stdio.h>

void insertionSort(int a[], int n) {
  int i, j, key;
  for (i = 1; i < n; i++) {
    key = a[i];
    j = i - 1;

    /* Move elements of arr[0..i-1], that are greater than key, to one position
     * ahead of their current position */
    while (j >= 0 && key < a[j]) {
      a[j + 1] = a[j];
      --j;
    }
    a[j + 1] = key;
  }
}

int main() {
  int a[10] = {5, 2, 4, 6, 1, 3, 9, 10, 8, 7};
  int size = sizeof(a) / sizeof(int);
  printf("%d\n", size);
  insertionSort(a, 10);
  for (int i = 0; i < 10; i++) {
    printf("%d ", a[i]);
  }
  printf("\n");
}
