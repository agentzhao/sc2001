#include <stdio.h>
#include <stdlib.h>

void quicksort(int a[], int n) {
  if (n <= 1) {
    return;
  }
  int pivot = a[0];
  int storeIndex = 1;

  for (int i = 1; i < n; i++) {
    if (a[i] <= pivot) {
      // swap
      int temp = a[i];
      a[i] = a[storeIndex];
      a[storeIndex] = temp;
      storeIndex++;
    }
  }
  // swap
  int temp = pivot;
  a[0] = a[storeIndex - 1];
  a[storeIndex - 1] = temp;

  quicksort(a, storeIndex - 1);
  quicksort(a + storeIndex, n - storeIndex);
}

int main() {
  int a[] = {3, 4, 1, 2, 5, 9, 6, 7, 8};
  int length = 10;
  quicksort(a, length);
  for (int i = 0; i < length; i++)
    printf("%d ", a[i]);
  printf("\n");
}
