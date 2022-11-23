
// Combine mergesort with insertion sort
import java.util.Scanner;

class mergeinsert {
  static int threshold = 1000;

  static long comparisons = 0;

  static void insertionSort(int a[], int left, int right) {
    // in place insertion sort
    for (int i = left + 1; i <= right; i++) {
      int j = i;
      int B = a[i];
      while ((j > left) && (a[j - 1] > B)) {
        a[j] = a[j - 1];
        j--;
        comparisons++;
      }
      a[j] = B;
    }
  }

  static void merge2(int arr[], int l, int m, int r) {
    // merging two sorted arrays without making a new array
    int n1 = m - l + 1;
    int n2 = r - m;

    int smaller = (n1 < n2) ? n1 : n2;

    int index1 = 0;
    int index2 = 0;
    for (int i = 0; i < smaller; i++) {
      if (arr[l + index1] < arr[m + 1 + index2]) {
        comparisons++;
        index1++;
      } else {
        comparisons++;
        int temp = arr[m + 1 + index2];
        for (int j = m + index2; j > l + index1; j--) {
          arr[j] = arr[j - 1];
        }
        arr[l + index1] = temp;
        index1++;
        index2++;
      }
    }
  }

  static void merge(int arr[], int l, int m, int r) {
    // Find sizes of two subarrays to be merged
    int n1 = m - l + 1;
    int n2 = r - m;

    /* Create temp arrays */
    int L[] = new int[n1];
    int R[] = new int[n2];

    /* Copy data to temp arrays */
    for (int i = 0; i < n1; ++i)
      L[i] = arr[l + i];
    for (int j = 0; j < n2; ++j) {
      R[j] = arr[m + 1 + j]; // +1
    }
    /* Merge the temp arrays */

    // Initial indexes of first and second subarrays
    int i = 0, j = 0;

    // Initial index of merged subarray array
    int k = l;
    while (i < n1 && j < n2) {
      comparisons++;
      if (L[i] <= R[j]) {
        arr[k] = L[i];
        i++;
      } else {
        arr[k] = R[j];
        j++;
      }
      k++;
    }

    /* Copy remaining elements of L[] if any */
    while (i < n1) {
      arr[k] = L[i];
      i++;
      k++;
    }

    /* Copy remaining elements of R[] if any */
    while (j < n2) {
      arr[k] = R[j];
      j++;
      k++;
    }
  }

  // hybrid mergesort + insertionsort
  static void hybrid(int arr[], int l, int r) {
    if (l < r) {
      if ((r - l) < threshold) {
        insertionSort(arr, l, r);
        return;
      }
      // Find the middle point
      int m = (l + r) / 2;

      // Sort first and second halves
      hybrid(arr, l, m);
      hybrid(arr, m + 1, r);

      // Merge the sorted halves
      merge(arr, l, m, r);
    }
  }

  // only using mergesort
  static void mergeSort(int arr[], int l, int r) {
    if (l < r) {
      // Find the middle point
      int m = (l + r) / 2;

      // Sort first and second halves
      mergeSort(arr, l, m);
      mergeSort(arr, m + 1, r);

      // Merge the sorted halves
      merge(arr, l, m, r);
    }
  }

  // generate array of random numbers
  static int[] generate(int n, int largest) {
    int arr[] = new int[n];
    for (int i = 0; i < n; i++) {
      arr[i] = (int) (Math.random() * largest);
    }
    return arr;
  }

  static void printArray(int arr[]) {
    int n = arr.length;
    for (int i = 0; i < n; ++i)
      System.out.print(arr[i] + " ");
    System.out.println();
  }

  static void one() {
    int n = 10000000;
    int largest = 10000;
    Scanner sc = new Scanner(System.in);
    // System.out.print("Enter the number of elements: ");
    // n = sc.nextInt();
    // System.out.print("Enter the largest element: ");
    // largest = sc.nextInt();
    System.out.print("Enter the threshold: ");
    threshold = sc.nextInt();
    System.out.println("Running with n = " + n + ", largest = " + largest + ", threshold = " + threshold);
    sc.close();

    int arr[] = generate(n, largest);
    int brr[] = arr.clone();

    System.out.println();

    long startTime = System.currentTimeMillis();
    hybrid(arr, 0, n - 1);
    long endTime = System.currentTimeMillis();
    long totalTime = (endTime - startTime);
    System.out.println("Time taken: " + (float) totalTime / 1000);
    System.out.println("Number of comparisons: " + comparisons);
    // printArray(arr);

    // testing just merge
    System.out.println("--------------------");
    comparisons = 0;
    startTime = System.currentTimeMillis();
    mergeSort(brr, 0, n - 1);
    endTime = System.currentTimeMillis();
    totalTime = (endTime - startTime);
    System.out.println("Time taken: " + (float) totalTime / 1000000000);
    System.out.println("Number of comparisons: " + comparisons);
  }

  static void multiple() {
    int n = 10000000;
    int largest = 10000;
    int arr[] = generate(n, largest);
    System.out.println("Running with n = " + n + ", largest = " + largest);

    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the threshold from: ");
    int from = sc.nextInt();
    System.out.print("Enter the threshold to: ");
    int to = sc.nextInt();
    System.out.print("Enter the threshold step: ");
    int step = sc.nextInt();
    sc.close();
    System.out.println("--------------------");

    long startTime, endTime, totalTime;
    long startTime2, endTime2, totalTime2;
    long temp;

    int brr[] = arr.clone();

    for (int i = from; i <= to; i += step) {
      threshold = i;
      comparisons = 0;

      // hybrid
      startTime = System.nanoTime();
      hybrid(brr, 0, n - 1);
      endTime = System.nanoTime();
      totalTime = (endTime - startTime);
      temp = comparisons;

      // mergesort
      comparisons = 0;
      brr = arr.clone();
      startTime2 = System.nanoTime();
      mergeSort(brr, 0, n - 1);
      endTime2 = System.nanoTime();
      totalTime2 = (endTime2 - startTime2);
      System.out.println("Threshold: " + threshold);
      System.out.printf("Time taken: %6.5f, %9.5f \n", (float) totalTime / 1000000000, (float) totalTime2 / 1000000000);
      System.out.println("Comparisons: " + temp + ", " + comparisons);
      System.out.println("--------------------");
    }
  }

  public static void main(String args[]) {
    multiple();
  }
}
