
/* for each (unsorted) partition
set first element as pivot
  storeIndex = pivotIndex+1
  for i = pivotIndex+1 to rightmostIndex
    if ((a[i] < a[pivot]) or (equal but 50% lucky))
      swap(i, storeIndex); ++storeIndex
  swap(pivot, storeIndex-1) */

class quicksort {
  // recursive
  static void quick(int[] a, int low, int high) {
    if (low >= high)
      return;
    int pivot = partition(a, low, high);
    quick(a, low, pivot - 1);
    quick(a, pivot + 1, high);
  }

  static int swaps = 0;

  static int partition(int[] a, int low, int high) {
    // int mid = (low + high) / 2;
    int pivot = a[low]; // or a[mid] or a[high]
    int index = low; // i is the index of the first element > pivot
    int temp;

    for (int j = low + 1; j <= high; j++) {
      // if current element is smaller than pivot
      if (a[j] < pivot) {
        swaps++;
        index++; // make space for smaller element to swap
        temp = a[index];
        a[index] = a[j];
        a[j] = temp;
      }
    }
    // put pivot in the right place (swap with last smallest element)
    swaps++;
    temp = a[index];
    a[index] = a[low];
    a[low] = temp;
    return index;
  }

  public static void main(String[] args) {
    int[] a = { 9, 2, 5, 6, 7 };
    quick(a, 0, a.length - 1);

    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i] + " ");
    }
    System.out.println("\n" + swaps);
  }
}
