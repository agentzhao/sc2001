#include <stdio.h>

void merge(int a[], int l, int m, int r) {
  int a1 = m - l + 1;
  int a2 = r - m;

  int left[a1], right[a2];
  for (int i = 0; i < a1; i++)
    left[i] = a[l + i];
  for (int j = 0; j < a2; j++)
    right[j] = a[m + 1 + j];

  // merging two arrays
  int i = 0;
  int j = 0;
  int k = l;
  while (i < a1 && j < a2) {
    if (left[i] <= right[j]) {
      a[k] = left[i];
      i++;
    } else {
      a[k] = right[j];
      j++;
    }
    k++;
  }
  // copy remaining elements
  while (i < a1) {
    a[k] = left[i];
    i++;
    k++;
  }
  while (j < a2) {
    a[k] = right[j];
    j++;
    k++;
  }
}

void mergesort(int a[], int l, int r) {
  // empty array
  if (l < r) {
    int m = l + (r - l) / 2;
    mergesort(a, l, m);
    mergesort(a, m + 1, r);

    merge(a, l, m, r);
  }
}

int main() {
  int a[] = {4, 5, 2, 9, 1, 3, 7, 8, 6};
  printf("Before sorting: ");
  for (int i = 0; i < 9; i++)
    printf("%d ", a[i]);

  mergesort(a, 0, 9);
  printf("\nAfter sorting: ");
  for (int i = 0; i < 10; i++) {
    printf("%d ", a[i]);
  }
  printf("\n");
}
