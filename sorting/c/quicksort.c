// quicksort

// for each (unsorted) partition
// set first element as pivot
//   storeIndex = pivotIndex+1
//   for i = pivotIndex+1 to rightmostIndex
//     if ((a[i] < a[pivot]) or (equal but 50% lucky))
//       swap(i, storeIndex); ++storeIndex
//   swap(pivot, storeIndex-1)

#include <stdio.h>
#include <stdlib.h>

// int a[] == int *a
void quicksort(int a[], int n) {
  if (n <= 1)
    return;
  int pivot = a[0];
  int storeIndex = 1;
  for (int i = 1; i < n; ++i) {
    if ((a[i] < pivot) || ((a[i] == pivot) && (rand() % 2 == 0))) {
      // if ((a[i] < pivot) || ((a[i] == pivot))) {
      // swap a[i] and a[storeIndex]
      int tmp = a[i];
      a[i] = a[storeIndex];
      a[storeIndex] = tmp;
      ++storeIndex;
    }
  }
  // swap pivot and storeIndex-1
  int tmp = a[0];
  a[0] = a[storeIndex - 1];
  a[storeIndex - 1] = tmp;
  quicksort(a, storeIndex - 1);              // sort first partition
  quicksort(a + storeIndex, n - storeIndex); // sort second partition
}

int main() {
  int a[] = {3, 4, 1, 2, 5, 9, 6, 7, 8};
  int length = 10;
  quicksort(a, length);
  for (int i = 0; i < length; i++)
    printf("%d ", a[i]);
  printf("\n");
}
