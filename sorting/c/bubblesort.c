#include <stdio.h>

void bubble(int a[], int n) {
  int i, j, t;
  for (i = n - 2; i >= 0; i--) {
    for (j = 0; j <= i; j++) {
      if (a[j] > a[j + 1]) {
        t = a[j];
        a[j] = a[j + 1];
        a[j + 1] = t;
      }
    }
  }
} // end function.

int main() {
  int a[10] = {4, 2, 8, 5, 9, 3, 1, 6, 7, 0};
  int i;

  bubble(a, 10);

  printf("Sorted array is: ");
  for (i = 0; i < 10; i++)
    printf("%d ", a[i]);
  printf("\n");
} // end program
